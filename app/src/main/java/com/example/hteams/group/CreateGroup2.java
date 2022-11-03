package com.example.hteams.group;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hteams.MainActivity;
import com.example.hteams.R;
import com.example.hteams.adapter.InviteAdapter;
import com.example.hteams.model.FireBaseParticipant;
import com.example.hteams.model.FirebaseCreateGroup;
import com.example.hteams.model.GroupModel;
import com.example.hteams.model.InviteModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

public class CreateGroup2 extends AppCompatActivity {
//    ArrayList<FireBaseParticipant> fireBaseParticipants = new ArrayList<>();
    static String section;
    String cname;
    //chek kumh error
    static int testingError1 = 0;
    int testingError2 = 0;
    TextView currentName;
    ArrayList<InviteModel> inviteModels = new ArrayList<>();
    static ArrayList <String> Classmate = new ArrayList<String>(); //Create
    String newCreatedGroup;
    //getting the ID 

    TextView add;
    Button createbtn;
    //database
    private FirebaseFirestore Database = FirebaseFirestore.getInstance();
    //adding to the group table in firebase
    private CollectionReference groupRef = Database.collection("groups");

    //adding to the group -> participant
    private DocumentReference groupParticipantRef = Database.collection("groups").document("participant");

    private CollectionReference participantRef = Database.collection("aaa");
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    private DocumentReference db = Database.document("groups/Participants");
    private DocumentSnapshot lastResult;
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


        //add when click
        add();
        //creating a group
        //uploading to firebase
        createGroup();
        //Recycler view of invite classmate
        RecyclerView recyclerView = findViewById(R.id.inviteRecycler);
        setupInviteModel();
        InviteAdapter adapter = new InviteAdapter(this, inviteModels);
        recyclerView.setAdapter(adapter);
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

    private void add() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateGroup2.this, ListOfClassmate.class));
            }
        });
    }

    private void initxml() {
        add = findViewById(R.id.add);
        createbtn = findViewById(R.id.createbtn);
        currentName = findViewById(R.id.currentName);
    }


    //adding data in invite classmate interface
    private void setupInviteModel() {
        //dummy datasaasdadw


        //from database data
//        ArrayList<String> salesId = new ArrayList<>();
//        ArrayList<String> Time = new ArrayList<>();
//        ArrayList<String> TotalPrice = new ArrayList<>();


        for(int i=0; i<Classmate.size(); i++){
            inviteModels.add(new InviteModel(Classmate.get(i)
            ));
        }
    }


}