package com.example.hteams.group;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hteams.MainActivity;
import com.example.hteams.R;
import com.example.hteams.Testing.SetAvatar;
import com.example.hteams.adapter.AvatarAdapter;
import com.example.hteams.adapter.SiteAdapter;
import com.example.hteams.adapter.SubjectlistAdapter;
import com.example.hteams.adapter.SubjectlistInterface;
import com.example.hteams.database.DatabaseHelper;
import com.example.hteams.model.AvatarModel;
import com.example.hteams.model.SubjectlistModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Creategroup extends AppCompatActivity  implements SubjectlistInterface {
    static String GroupName ="", Subject  = "", Professor = "", Description = "";

    Button nextbtn ;
    Button cont;
    ImageButton backbtn;
    EditText grpname ,descrip;
    TextView professor;
    Button subject;
    static int ctrl = 0;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    String cname;
    ImageView choose_avatar;

    //bottomsheet Dialog
    BottomSheetDialog subjectlist, chooseavatarbtmsheet;
    RecyclerView subjectrecycler;

    //for upliading image
    public Uri imageUri;

    //model
    //model for subject
    ArrayList<SubjectlistModel> subjectlistModel = new ArrayList<>();
    //model for avatar
    ArrayList<AvatarModel> avatarModels = new ArrayList<>();

    static ArrayList< String > ClassHolder = new ArrayList < > ();
    static ArrayList< String > id = new ArrayList < > ();
    static String section;
    //choices avatar value
    static String choicesAvatar = "avatar1";
    static String avatarFirebase = "https://firebasestorage.googleapis.com/v0/b/hteams-4b7e1.appspot.com/o/groups%2Fprofile%2Fgroupavatar1.png?alt=media&token=547cdbf8-52eb-4440-b62d-ec5535320828"; //means going this to the firebase
    String SubjectName = "Select Subject";

    //SQLITE DATABASE
    DatabaseHelper databaseHelper;
    Cursor cursor;
    String SubjectID; //current section

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creategroup);

        //Database Initialization
        databaseHelper = new DatabaseHelper(Creategroup.this);

        //get the mapermission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        //to know the email and uid
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        subjectlist = new BottomSheetDialog(this);
        chooseavatarbtmsheet = new BottomSheetDialog(this);

        String cname = firebaseAuth.getCurrentUser().getUid();

        //getting the section of current user
        //getting to SQLITE DATABASE
        cursor = databaseHelper.getSection(cname);

        try {
            cursor.moveToNext();
            section = cursor.getString(0);
        }catch (Exception e){
            Toast.makeText(Creategroup.this, e.toString(), Toast.LENGTH_SHORT).show();
        }

        //initiate
        initxml();
        //back button
        back();
        //next button
        next();
        //firebase

        subjectBottomSheet();  //when click the subject




        avatarBottomSheet();  //showing when click the choose Group avatar

    }
    private void next() {
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GroupName = grpname.getText().toString();
                Description = descrip.getText().toString();


                startActivity(new Intent(Creategroup.this, CreateGroup2.class));
            }
        });
    }

    private void back() {
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Creategroup.this, MainActivity.class));
            }
        });
    }

    private void initxml() {
        backbtn = findViewById(R.id.back_option);
        nextbtn = findViewById(R.id.nextbtn);
        choose_avatar = (ImageView) findViewById(R.id.choose_avatar);
        grpname = findViewById(R.id.input_name);
        professor = findViewById(R.id.input_professor);
        descrip = findViewById(R.id.input_description);
    }

    private void subjectBottomSheet() {
        View view = getLayoutInflater().inflate(R.layout.bottomsdialog_subject_creategroup,null,false);
        subjectrecycler = view.findViewById(R.id.subject_recycler);
         subject = findViewById(R.id.subject_generate);


         subject = findViewById(R.id.subject_generate);

         setupdataforsubjects();//for sqlites
        setupdataforsubjectsFirebase();
         SubjectlistAdapter adapter = new SubjectlistAdapter(Creategroup.this,subjectlistModel,this);
         subjectrecycler.setAdapter(adapter);
         subjectrecycler.setLayoutManager(new LinearLayoutManager(Creategroup.this));

         subject.setOnClickListener(new View.OnClickListener() {  // defective method
            @Override
            public void onClick(View v) {
                subjectlist.setContentView(view);
                subjectlist.show();
            }
        });
    }

    private void setupdataforsubjectsFirebase() {

    }

    //set up nang ilalabas sa subject dialog
    private void setupdataforsubjects() {
        ArrayList<String> subjectname = new ArrayList<String>();
        ArrayList<String> subjectId = new ArrayList<String>();

        //gettimh the subject list from sqlite database
        Cursor cursor = databaseHelper.getSubject(section);

        try {
            while (cursor.moveToNext()) {
                subjectname.add(cursor.getString(1));
                subjectId.add(cursor.getString(0));
            }

        }catch (Exception e){
            Toast.makeText(Creategroup.this, e.toString(), Toast.LENGTH_SHORT).show();
        }

          for(int i = 0;i<subjectname.size();i++){
              subjectlistModel.add(new SubjectlistModel(subjectId.get(i), subjectname.get(i)));
          }
    }


    //method for bottomsheet of avatar
    private void avatarBottomSheet() {
        View view = getLayoutInflater().inflate(R.layout.bottomsheet_avatar,null,false);

        RecyclerView avatarchoices = view.findViewById(R.id.avatarchoices);

        setupforAvatar();
        AvatarAdapter adapter = new AvatarAdapter(Creategroup.this,avatarModels,this);
        avatarchoices.setAdapter(adapter);
        avatarchoices.setLayoutManager(new GridLayoutManager(Creategroup.this, 4));

        choose_avatar.setOnClickListener(new View.OnClickListener() {  // defective method
            @Override
            public void onClick(View v) {
                chooseavatarbtmsheet.setContentView(view);
                chooseavatarbtmsheet.show();
            }
        });
    }

    private void setupforAvatar() {
        ArrayList<String> avatarName = new ArrayList<String>();
        ArrayList<Integer> avatarImage = new ArrayList<Integer>();

        avatarName.add("chooseavatar");
        avatarName.add("avatar1");
        avatarName.add("groupavatar2");
        avatarName.add("groupavatar3");
        avatarName.add("groupavatar4");
        avatarName.add("groupavatar5");
        avatarName.add("groupavatar6");
        avatarName.add("groupavatar7");
        avatarName.add("groupavatar8");
        avatarName.add("groupavatar9");
        avatarName.add("groupavatar10");
        avatarName.add("groupavatar11");
        avatarName.add("groupavatar12");
        avatarName.add("groupavatar13");
        avatarName.add("groupavatar14");
        avatarName.add("groupavatar15");
        avatarName.add("groupavatar16");
        avatarName.add("groupavatar17");
        avatarName.add("groupavatar18");
        avatarName.add("groupavatar19");
        avatarName.add("groupavatar20");

        avatarImage.add(R.drawable.chooseavatar);
        avatarImage.add(R.drawable.avatar1);
        avatarImage.add(R.drawable.groupavatar2);
        avatarImage.add(R.drawable.groupavatar3);
        avatarImage.add(R.drawable.groupavatar4);
        avatarImage.add(R.drawable.groupavatar5);
        avatarImage.add(R.drawable.groupavatar6);
        avatarImage.add(R.drawable.groupavatar7);
        avatarImage.add(R.drawable.groupavatar8);
        avatarImage.add(R.drawable.groupavatar9);
        avatarImage.add(R.drawable.groupavatar10);
        avatarImage.add(R.drawable.groupavatar11);
        avatarImage.add(R.drawable.groupavatar12);
        avatarImage.add(R.drawable.groupavatar13);
        avatarImage.add(R.drawable.groupavatar14);
        avatarImage.add(R.drawable.groupavatar15);
        avatarImage.add(R.drawable.groupavatar16);
        avatarImage.add(R.drawable.groupavatar17);
        avatarImage.add(R.drawable.groupavatar18);
        avatarImage.add(R.drawable.groupavatar19);
        avatarImage.add(R.drawable.groupavatar20);


        for(int i = 0;i<avatarName.size();i++){
            avatarModels.add(new AvatarModel(avatarName.get(i), avatarImage.get(i)));
        }
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    public void changepicture(){
        SetAvatar setAvatars = new SetAvatar();
        choose_avatar.setImageResource(setAvatars.setAvatar(choicesAvatar));
    }

    @Override
    public void onItemClick(int pos, String adapter) {

        switch (adapter) {
            case "subject":
                    //showing the professor
                    LinearLayout prof_container = findViewById(R.id.prof_container);
                    prof_container.setVisibility(View.VISIBLE);

                    //to get the current professor
                    cursor = databaseHelper.getProfessor(subjectlistModel.get(pos).getSubjectId());

                    try {
                        while (cursor.moveToNext()) {
                            //professor.setText("test");
                            Professor =  cursor.getString(0);
                            professor.setText("Prof. " + Professor);

                        }

                    } catch (Exception e) {
                        Toast.makeText(Creategroup.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }

                    //chaging the value of nuttontext
                    Subject = subjectlistModel.get(pos).getSubjectname();
                    subject.setText(Subject);
                    subject.setTextColor(Color.BLACK);

                    //bottom sheet dialog will hide
                    subjectlist.dismiss();
                    break;

                    case "avatar":
                        //to retrieve the image choices of user
                         choicesAvatar = avatarModels.get(pos).getAvatarName();

                        //set the avatar
//                        Toast.makeText(Creategroup.this, choicesAvatar, Toast.LENGTH_SHORT).show();
                        if(choicesAvatar.equalsIgnoreCase("chooseavatar")){
                            choosePicture();

//                            Toast.makeText(Creategroup.this, "Coming Soon", Toast.LENGTH_SHORT).show();
                        }else if(choicesAvatar.equalsIgnoreCase("avatar1")){
                            changepicture();
                            avatarFirebase = "https://firebasestorage.googleapis.com/v0/b/hteams-4b7e1.appspot.com/o/groups%2Fprofile%2Fgroupavatar1.png?alt=media&token=547cdbf8-52eb-4440-b62d-ec5535320828";
                        }else if(choicesAvatar.equalsIgnoreCase("groupavatar2")){
                            changepicture();
                            avatarFirebase ="https://firebasestorage.googleapis.com/v0/b/hteams-4b7e1.appspot.com/o/groups%2Fprofile%2Fgroupavatar2.png?alt=media&token=b2abc18c-45ed-430f-b8c0-00a8cdfae297 ";
                        }else if(choicesAvatar.equalsIgnoreCase("groupavatar3")){
                            changepicture();
                            avatarFirebase ="https://firebasestorage.googleapis.com/v0/b/hteams-4b7e1.appspot.com/o/groups%2Fprofile%2Fgroupavatar3.png?alt=media&token=dfb30258-564b-494f-8665-ba0450365c48";
                        }else if(choicesAvatar.equalsIgnoreCase("groupavatar4")){
                            changepicture();
                            avatarFirebase ="https://firebasestorage.googleapis.com/v0/b/hteams-4b7e1.appspot.com/o/groups%2Fprofile%2Fgroupavatar4.png?alt=media&token=29435815-fc2e-481f-9c32-ea3cb6c1237f";
                        }else if(choicesAvatar.equalsIgnoreCase("groupavatar5")){
                            changepicture();
                            avatarFirebase ="https://firebasestorage.googleapis.com/v0/b/hteams-4b7e1.appspot.com/o/groups%2Fprofile%2Fgroupavatar5.png?alt=media&token=c4ba0059-78cf-48ef-b56f-7a2099d5548c";
                        }else if(choicesAvatar.equalsIgnoreCase("groupavatar6")){
                            changepicture();
                            avatarFirebase ="https://firebasestorage.googleapis.com/v0/b/hteams-4b7e1.appspot.com/o/groups%2Fprofile%2Fgroupavatar6.png?alt=media&token=5e57b32d-10a2-44be-af50-31985bf5c03c";
                        }else if(choicesAvatar.equalsIgnoreCase("groupavatar7")){
                            changepicture();
                            avatarFirebase ="https://firebasestorage.googleapis.com/v0/b/hteams-4b7e1.appspot.com/o/groups%2Fprofile%2Fgroupavatar7.png?alt=media&token=7a202d6e-ecdc-4324-b9f9-7d282dc05db6";
                        }else if(choicesAvatar.equalsIgnoreCase("groupavatar8")){
                            changepicture();
                            avatarFirebase ="https://firebasestorage.googleapis.com/v0/b/hteams-4b7e1.appspot.com/o/groups%2Fprofile%2Fgroupavatar8.png?alt=media&token=a6544bfe-6dde-4dab-b984-f6fec4a0534b";
                        }else if(choicesAvatar.equalsIgnoreCase("groupavatar9")){
                            changepicture();
                            avatarFirebase ="https://firebasestorage.googleapis.com/v0/b/hteams-4b7e1.appspot.com/o/groups%2Fprofile%2Fgroupavatar9.png?alt=media&token=962d1967-7ca5-4469-920b-2482c7a9ca59";
                        }else if(choicesAvatar.equalsIgnoreCase("groupavatar10")){
                            changepicture();
                            avatarFirebase ="https://firebasestorage.googleapis.com/v0/b/hteams-4b7e1.appspot.com/o/groups%2Fprofile%2Fgroupavatar10.png?alt=media&token=f3f26fce-ab51-45e1-83dc-de6b81eb3b0c";
                        }else if(choicesAvatar.equalsIgnoreCase("groupavatar11")){
                            changepicture();
                            avatarFirebase ="https://firebasestorage.googleapis.com/v0/b/hteams-4b7e1.appspot.com/o/groups%2Fprofile%2Fgroupavatar11.png?alt=media&token=50c6a39d-83c0-4643-8843-c1577d523335";
                        }else if(choicesAvatar.equalsIgnoreCase("groupavatar12")){
                            changepicture();
                            avatarFirebase ="https://firebasestorage.googleapis.com/v0/b/hteams-4b7e1.appspot.com/o/groups%2Fprofile%2Fgroupavatar12.png?alt=media&token=32a831a7-2386-4615-9720-db4794b75631";
                        }else if(choicesAvatar.equalsIgnoreCase("groupavatar13")){
                            changepicture();
                            avatarFirebase ="https://firebasestorage.googleapis.com/v0/b/hteams-4b7e1.appspot.com/o/groups%2Fprofile%2Fgroupavatar13.png?alt=media&token=ee5bee37-c57a-4b9c-bcae-ac2614a615c8";
                        }else if(choicesAvatar.equalsIgnoreCase("groupavatar14")){
                            changepicture();
                            avatarFirebase ="https://firebasestorage.googleapis.com/v0/b/hteams-4b7e1.appspot.com/o/groups%2Fprofile%2Fgroupavatar14.png?alt=media&token=4cf17c92-03e7-4c48-be28-ed9d84da6215";
                        }else if(choicesAvatar.equalsIgnoreCase("groupavatar15")){
                            changepicture();
                            avatarFirebase ="https://firebasestorage.googleapis.com/v0/b/hteams-4b7e1.appspot.com/o/groups%2Fprofile%2Fgroupavatar15.png?alt=media&token=2fe4269a-73a4-43c1-ab42-cb3979158991";
                        }else if(choicesAvatar.equalsIgnoreCase("groupavatar16")){
                            changepicture();
                            avatarFirebase ="https://firebasestorage.googleapis.com/v0/b/hteams-4b7e1.appspot.com/o/groups%2Fprofile%2Fgroupavatar16.png?alt=media&token=c16c9deb-1c8d-4e98-8068-567420278473";
                        }else if(choicesAvatar.equalsIgnoreCase("groupavatar17")){
                            changepicture();
                            avatarFirebase ="https://firebasestorage.googleapis.com/v0/b/hteams-4b7e1.appspot.com/o/groups%2Fprofile%2Fgroupavatar17.png?alt=media&token=f6d3a53d-cd8b-4835-8750-db9903984a59";
                        }else if(choicesAvatar.equalsIgnoreCase("groupavatar18")){
                            changepicture();
                            avatarFirebase ="https://firebasestorage.googleapis.com/v0/b/hteams-4b7e1.appspot.com/o/groups%2Fprofile%2Fgroupavatar18.png?alt=media&token=447807ed-205d-42d5-b0cf-659f370c56cc";
                        }else if(choicesAvatar.equalsIgnoreCase("groupavatar19")){
                            changepicture();
                            avatarFirebase ="https://firebasestorage.googleapis.com/v0/b/hteams-4b7e1.appspot.com/o/groups%2Fprofile%2Fgroupavatar19.png?alt=media&token=d5cfd285-1b74-401a-9b5f-9b32bf52c820";
                        }else if(choicesAvatar.equalsIgnoreCase("groupavatar20")){
                            changepicture();
                            avatarFirebase ="https://firebasestorage.googleapis.com/v0/b/hteams-4b7e1.appspot.com/o/groups%2Fprofile%2Fgroupavatar20.png?alt=media&token=43e84875-90ef-4a38-a7e6-e6120b7bae58";
                        } else{
                           Toast.makeText(Creategroup.this, "Something bad", Toast.LENGTH_SHORT).show();
                        }


//                        choose_avatar.setImageResource(imagetry);



                        chooseavatarbtmsheet.dismiss();
                        break;

        }

    }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageUri = data.getData();
            choose_avatar.setImageURI(imageUri);
            uploadPhoto();
        }
    }

    private void uploadPhoto() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading Image");
        progressDialog.show();

        //getting the access
        //final StorageReference Imagename = Folder.child("groups/userupload" + imageUri.getLastPathSegment());

        final String randomKey = UUID.randomUUID().toString();
        StorageReference mountainImagesRef = storageReference.child("groups/userupload/" + randomKey);

       final StorageReference Imagename = storageReference.child("groups/userupload/" + imageUri.getLastPathSegment());

        mountainImagesRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Snackbar.make(findViewById(android.R.id.content), "Image Uploaded", Snackbar.LENGTH_LONG).show();

                        mountainImagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
//                                Log.d("TAG", "REFERENCE " + uri);
                                avatarFirebase = String.valueOf(uri);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("TAG", "FDAILUERE CAUSE " + e);
                            }
                        });




                       // https://firebasestorage.googleapis.com/v0/b/hteams-4b7e1.appspot.com/o/groups%2Fuserupload%2F24094929-257f-4346-9413-a47970344b94?alt=media&token=9611bb2e-a370-4cc4-a30c-4b141f870f22
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(Creategroup.this, "Failed to uplaoad", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                        progressDialog.setMessage("Progress: " + (int) progressPercent + "%");
                    }
                });


// While the file names are the same, the references point to different files
//        mountainsRef.getName().equals(mountainImagesRef.getName());    // true
//        mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false
    }
}