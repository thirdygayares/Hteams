package com.example.hteams.group;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hteams.MainActivity;
import com.example.hteams.R;
import com.example.hteams.Testing.SetProfile;
import com.example.hteams.Testing.Testing1Model;
import com.example.hteams.adapter.AvatarAdapter;
import com.example.hteams.adapter.ChooseParcticipant;
import com.example.hteams.adapter.ChooseParticipantAdapter;
import com.example.hteams.adapter.InviteAdapter;
import com.example.hteams.database.DatabaseHelper;
import com.example.hteams.model.ChooseParticipantModel;
import com.example.hteams.model.FireBaseParticipant;
import com.example.hteams.model.FirebaseCreateGroup;
import com.example.hteams.model.GroupModel;
import com.example.hteams.model.InviteModel;
import com.example.hteams.model.SQLITECREATEGROUPMODEL;
import com.example.hteams.model.SQLITEPARTICIPANTMODEL;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.google.firebase.firestore.WriteBatch;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CreateGroup2 extends AppCompatActivity implements ChooseParcticipant {
//    ArrayList<FireBaseParticipant> fireBaseParticipants = new ArrayList<>();

    String TAG = "TAG";
    //id of current user
    String cname;
    String SECTION;
    String newCreatedGroup;
    static String Section; //To get the value of section

    //array lost
    //model
    ArrayList<InviteModel> inviteModels = new ArrayList<>();
    InviteModel uploadtofirebase;
    static ArrayList <String> Classmate = new ArrayList<String>(); //Create
    ArrayList<ChooseParticipantModel> chooseParticipantModels = new ArrayList<>();

    View view;
    // Image of leader
    ImageView profile_leader;
    TextView add;
    Button createbtn;
    TextView currentName;
    TextView section;

    RecyclerView recyclerView;
    //bottomsheet
   // -> bottom sheet when click the add participant
    BottomSheetDialog invite_btmsht;


    //Adapter ginlobal variable ko siya para mahide
    ChooseParticipantAdapter chooseParticipantAdapter;
    InviteAdapter inviteAdapteradapter;

    //firebase Auth
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    //SQLITE DB
    DatabaseHelper databaseHelper;
    Cursor getnameofUser,getCurrentImage;
    String currentUserString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creategroup2);


        view = getLayoutInflater().inflate(R.layout.bottomsheet_invite,null,false);
        section  = view.findViewById(R.id.section);
        invite_btmsht = new BottomSheetDialog(CreateGroup2.this);        //bottomsheet start
        invite_btmsht.setContentView(view);

        //initialization
        initxml();

        //to know the email and uid
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        //cyrrent name
         cname = firebaseAuth.getCurrentUser().getUid();

        //calling the adapter
        inviteAdapteradapter = new InviteAdapter(this, inviteModels);


        //calling sqlite database
        databaseHelper = new DatabaseHelper(CreateGroup2.this);

        /*picture getting from sqlite
         //method current user
         //sqlite find name of current User
         getnameofUser = databaseHelper.getCurrentName(cname);
         getCurrentImage = databaseHelper.getImageCurrentsUser(cname);

        try {
            getnameofUser.moveToNext();
            currentName.setText(getnameofUser.getString(0));
            currentUserString =getnameofUser.getString(0);
            //change profile of current user
            getCurrentImage.moveToNext();

            //get the class of setProfile
            SetProfile setProfile = new SetProfile();
            profile_leader.setImageResource(setProfile.profileImage(getCurrentImage.getString(0)));

        }catch (Exception e){
            Toast.makeText(CreateGroup2.this, e.toString(), Toast.LENGTH_SHORT).show();
        }


         */


        //getting section
        /*section name SQLITE
        //lagyan section name yung dialog para may guide
        Cursor cursor = databaseHelper.getSection(cname);
        try {
            cursor.moveToNext();
            SECTION = cursor.getString(0);
        }catch (Exception e){
            Toast.makeText(CreateGroup2.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
         */

        //setUpChoosingGroup();//SQLITE
        findSection();//FIREBASE
        //TODO CHANGE IMAGE []
        add();//add when click
        leaderpictureAndName();
        createGroup(); //uploading to firebase   //creating a group


        recyclerView = findViewById(R.id.inviteRecycler);  //Recycler view of invite classmate
        recyclerView.setAdapter(inviteAdapteradapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void leaderpictureAndName() {
    }


    //method for creating a group
    private void createGroup() {
        Creategroup creategroup = new Creategroup();
        String groupname = creategroup.GroupName, Subject = creategroup.Subject, Professor = creategroup.Professor, Description = creategroup.Description, GroupPhoto = creategroup.avatarFirebase;
        Log.d("TAG", GroupPhoto);

        createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //kunin yung input sa create group class
                //generating variables
                //kukunin na yung data


                //creating batch
                CollectionReference groupCollectionRef = firestore.collection("groups");
                WriteBatch batch = firestore.batch();



                Date d = new Date();
                Map<String, Object> addgroup = new HashMap<>();
                addgroup.put("GROUPNAME", groupname);
                addgroup.put("SUBJECT", Subject);
                addgroup.put("PROFESSOR", Professor);
                addgroup.put("Description", Description);
                addgroup.put("GROUPPHOTO", GroupPhoto);
                addgroup.put("LEADERID", cname);
                addgroup.put("CREATOR", cname);
                addgroup.put("CREATED", d.toString());


//                uploadtofirebase = new InviteModel(inviteModels.get(x).getId(), false);
                //   batch.set(groupCollectionRef.document(), addgroup);
                groupCollectionRef.
                        add(addgroup)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("TAG", "reference " + documentReference.getId());
                                CollectionReference testing = firestore.collection("groups").document(documentReference.getId()).collection("participant");

                                //add the leader from the group to be a participant
                                //hindi kasi siya nsasali sa list
                                inviteModels.add(new InviteModel("Thirdy","Thirdu",cname));

                                for(int x=0; x < inviteModels.size(); x++){ // to get all the invited participant
                                    Map<String, Object> listofparticipant = new HashMap<>();//map for participant
                                    listofparticipant.put("StudentID",inviteModels.get(x).getId());
                                    listofparticipant.put("GROUPID",documentReference.getId());
                                    //condition na pag leader ka autonated ka dapat na accpeted
                                    Boolean accepted;
                                    if(inviteModels.get(x).getId().equalsIgnoreCase(cname)){
                                        accepted = true;
                                    }else {
                                        accepted = true; //change this later
                                    }

                                    listofparticipant.put("Accepted",accepted);

                                    testing.add(listofparticipant).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Log.d("TAG", "referenc sub " + documentReference.getId());

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d("TAG", "sub batch " + e);
                                        }
                                    });

                                    CollectionReference participant = firestore.collection("participant"); //try na hiwalay ito
                                    participant.add(listofparticipant).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Log.d("TAG", "participant sub " + documentReference.getId());

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d("TAG", "sub batch " + e);
                                        }
                                    });


                                }

                                Toast.makeText(CreateGroup2.this,addgroup.get("GROUPNAME") + " Created", Toast.LENGTH_SHORT ).show();
                                startActivity(new Intent(CreateGroup2.this, MainActivity.class)); //Goto Home page
                               // Snackbar.make(findViewById(android.R.id.content), addgroup.get("GROUPNAME") + " Created", Snackbar.LENGTH_LONG).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("TAG", "weeror batch " + e);
                            }
                        });

            }
        });
    }











    private void add() {    // method of adding the participant

        add.setOnClickListener(new View.OnClickListener() { //bottomsheet will if add button is click
            @Override
            public void onClick(View v) {
                TextView college = view.findViewById(R.id.college); //baka magamit
                RecyclerView classmateList = view.findViewById(R.id.classmateList);
                chooseParticipantAdapter = new ChooseParticipantAdapter(CreateGroup2.this,chooseParticipantModels,CreateGroup2.this);
                classmateList.setAdapter(chooseParticipantAdapter);
                classmateList.setLayoutManager(new LinearLayoutManager(CreateGroup2.this));
                invite_btmsht.show();
            }
        });
    }

    private void findSection() {
        DocumentReference docRef=firestore.collection("students").document(cname);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>(){
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task){
                if(task.isSuccessful()){
                    DocumentSnapshot document=task.getResult();
                    if(document.exists()){
                        currentName.setText(document.get("Name").toString()); //change the name of the leader ex. Thirdy Gayares
                        Picasso.get().load(document.get("image").toString()).error(R.drawable.ic_profile).resize(400,400).into(profile_leader);//change the photo of profile_leader
//                       Log.d(TAG,"Document Snapshotdata:"+ document.get("Section").toString());
                        //Getting the section of Current user to find his classmate
                        Section = document.get("Section").toString();
                        section.setText(Section);
//                        Log.d(TAG,Section);
                        Firebasegetlassmate();
                    }
                    else{
                        Log.d(TAG,"Nosuchdocument");
                    }
                }else{
                    Log.d(TAG,"getfailedwith",task.getException());
                }
            }
        });

    }

    public void Firebasegetlassmate(){
        firestore.collection("students")
                .whereEqualTo("Section", Section)
                .whereNotEqualTo(FieldPath.documentId(), cname)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.get("Name"));
//                                               chooseParticipantModels.add(new ChooseParticipantModel(getData.getString(0), getData.getString(1),getData.getString(2)));
                                chooseParticipantModels.add(new ChooseParticipantModel(document.getId().toString(),document.get("Name").toString(),document.get("image").toString() ));
//                                chooseParticipantModels.removeIf(n -> (n.getID() == cname));
//                                Log.d("TAG", "Ang cname ay " + cname);
                            }
                        } else{
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }




//    private void setUpChoosingGroup() {
//        //TODO find section of current user - done
//        //TODO get name , id, and image as a whole section ex. kung III-ACDS - lalabas lang mga 3 acds
//        //lagyan section name yung dialog para may guide
//        Cursor getData = databaseHelper.getData(SECTION);
//
//        try {
//           while(getData.moveToNext()){
//               chooseParticipantModels.add(new ChooseParticipantModel(getData.getString(0), getData.getString(1),getData.getString(2)));
//           }
//        }catch (Exception e){
//            Toast.makeText(CreateGroup2.this, e.toString(), Toast.LENGTH_SHORT).show();
//        }
//
//    }


    private void initxml() {
        add = findViewById(R.id.add);
        createbtn = findViewById(R.id.createbtn);
        currentName = findViewById(R.id.currentName);

        profile_leader = (ImageView) findViewById(R.id.profile_leader);
    }





    @Override
    public void onItemClick(int pos) {
        //saved id of participants
        String thisistheirID = chooseParticipantModels.get(pos).getID();

        // add id in invitemodle
        //inviteModels.add(new InviteModel(thisistheirID));
                //Toast.makeText(CreateGroup2.this,thisistheirID, Toast.LENGTH_SHORT).show();
        //retrieve image of thei classmate
        inviteModels.add(new InviteModel(chooseParticipantModels.get(pos).getIMAGE(),chooseParticipantModels.get(pos).getNAME(), chooseParticipantModels.get(pos).getID()));
        inviteAdapteradapter.notifyItemInserted(inviteModels.size() - 1);
        recyclerView.scrollToPosition(Classmate.size());
        chooseParticipantModels.remove(pos);
        chooseParticipantAdapter.notifyItemRemoved(pos);
        invite_btmsht.dismiss();
    }
}