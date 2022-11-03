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
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
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
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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
    String cname;
    ImageView choose_avatar;

    //bottomsheet Dialog
    BottomSheetDialog subjectlist, chooseavatarbtmsheet;

    RecyclerView subjectrecycler;

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

        subjectBottomSheet();  // not showing(defective)

        //showing when click the choose Group avatar
        avatarBottomSheet();

    }
    private void next() {
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GroupName = grpname.getText().toString();
                Subject = subject.getText().toString();
                Professor = professor.getText().toString();
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

         setupdataforsubjects();
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

    @Override
    public void onItemClick(int pos, String adapter) {

        switch (adapter) {
            case "subject":
                    //showing the professor
                    LinearLayout prof_container = findViewById(R.id.prof_container);
                    prof_container.setVisibility(View.VISIBLE);
                    cursor = databaseHelper.getProfessor(subjectlistModel.get(pos).getSubjectId());

                    try {
                        while (cursor.moveToNext()) {
                            //professor.setText("test");
                            professor.setText("Prof. " + cursor.getString(0));

                        }

                    } catch (Exception e) {
                        Toast.makeText(Creategroup.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }

                    //chaging the value of nuttontext
                    subject.setText(subjectlistModel.get(pos).getSubjectname());
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
                            Toast.makeText(Creategroup.this, "Coming Soon", Toast.LENGTH_SHORT).show();
                        }else{
                            SetAvatar setAvatars = new SetAvatar();
                            choose_avatar.setImageResource(setAvatars.setAvatar(choicesAvatar));

                        }




//                        choose_avatar.setImageResource(imagetry);



                        chooseavatarbtmsheet.dismiss();
                        break;

        }

    }
}