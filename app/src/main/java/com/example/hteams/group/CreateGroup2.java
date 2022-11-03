package com.example.hteams.group;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CreateGroup2 extends AppCompatActivity implements ChooseParcticipant {
//    ArrayList<FireBaseParticipant> fireBaseParticipants = new ArrayList<>();


    //id of current user
    String cname;
    String SECTION;
    String newCreatedGroup;

    //array lost
    //model
    ArrayList<InviteModel> inviteModels = new ArrayList<>();
    static ArrayList <String> Classmate = new ArrayList<String>(); //Create
    ArrayList<ChooseParticipantModel> chooseParticipantModels = new ArrayList<>();


    // Image of leader
    ImageView profile_leader;
    TextView add;
    Button createbtn;
    TextView currentName;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creategroup2);

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
         //method current user
         //sqlite find name of current User
        Cursor getnameofUser = databaseHelper.getCurrentName(cname);
        Cursor getCurrentImage = databaseHelper.getImageCurrentsUser(cname);

        try {
            getnameofUser.moveToNext();
            currentName.setText(getnameofUser.getString(0));

            //change profile of current user
            getCurrentImage.moveToNext();

            //get the class of setProfile
            SetProfile setProfile = new SetProfile();
            profile_leader.setImageResource(setProfile.profileImage(getCurrentImage.getString(0)));

        }catch (Exception e){
            Toast.makeText(CreateGroup2.this, e.toString(), Toast.LENGTH_SHORT).show();
        }



        //getting section

        //lagyan section name yung dialog para may guide
        Cursor cursor = databaseHelper.getSection(cname);

        try {
            cursor.moveToNext();
            SECTION = cursor.getString(0);
        }catch (Exception e){
            Toast.makeText(CreateGroup2.this, e.toString(), Toast.LENGTH_SHORT).show();
        }


        //TODO CHANGE IMAGE []


        //add when click
        add();
        //creating a group
        //uploading to firebase
        createGroup();
        //Recycler view of invite classmate
        recyclerView = findViewById(R.id.inviteRecycler);
        setupInviteModel();
        recyclerView.setAdapter(inviteAdapteradapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    //method for creating a group
    private void createGroup() {

        createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    //kunin yung input sa create group class
                    Creategroup creategroup = new Creategroup();
                    //generating variables
                    //kukunin na yung data
                    String groupname = creategroup.GroupName, Subject = creategroup.Subject, Professor = creategroup.Professor, Description = creategroup.Description;

                     Classmate.clear();
                        startActivity(new Intent(CreateGroup2.this, MainActivity.class));
                        }
        });
    }


    // method of adding the participant
    //bottomsheet will if add button is click
    private void add() {

        //call the setup data pag nasa loobn kasi kada click sa  add nag aad eh
        setUpChoosingGroup();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //bottomsheet start
                invite_btmsht = new BottomSheetDialog(CreateGroup2.this);

                View view = getLayoutInflater().inflate(R.layout.bottomsheet_invite,null,false);
                TextView college = view.findViewById(R.id.college); //baka magamit
                TextView section = view.findViewById(R.id.section);

                RecyclerView classmateList = view.findViewById(R.id.classmateList);

                //section text
                section.setText(SECTION);
                chooseParticipantAdapter = new ChooseParticipantAdapter(CreateGroup2.this,chooseParticipantModels,CreateGroup2.this);
                classmateList.setAdapter(chooseParticipantAdapter);
                classmateList.setLayoutManager(new LinearLayoutManager(CreateGroup2.this));

                invite_btmsht.setContentView(view);
                invite_btmsht.show();

            }
        });
    }

    private void setUpChoosingGroup() {

        //TODO find section of current user - done
        //TODO get name , id, and image as a whole section ex. kung III-ACDS - lalabas lang mga 3 acds
        //lagyan section name yung dialog para may guide


        Cursor getData = databaseHelper.getData(SECTION);

        try {
           while(getData.moveToNext()){
               chooseParticipantModels.add(new ChooseParticipantModel(getData.getString(0), getData.getString(1),getData.getString(2)));
           }
        }catch (Exception e){
            Toast.makeText(CreateGroup2.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    private void initxml() {
        add = findViewById(R.id.add);
        createbtn = findViewById(R.id.createbtn);
        currentName = findViewById(R.id.currentName);
        profile_leader = (ImageView) findViewById(R.id.profile_leader);
    }


    //adding data in invite classmate interface
    private void setupInviteModel() {
        //dummy datasaasdadw
        //from database data
//        Classmate.add("Thirdy Gayares");

        for(int i=0; i<Classmate.size(); i++){
            inviteModels.add(new InviteModel(Classmate.get(i)
            ));
        }
    }


    @Override
    public void onItemClick(int pos) {

        //saved id of participants
        chooseParticipantModels.get(pos).getID();

        //retrieve image of thei classmate


        inviteModels.add(new InviteModel(chooseParticipantModels.get(pos).getIMAGE(),chooseParticipantModels.get(pos).getNAME()));
        inviteAdapteradapter.notifyItemInserted(inviteModels.size() - 1);
        recyclerView.scrollToPosition(Classmate.size());

        invite_btmsht.dismiss();
    }
}