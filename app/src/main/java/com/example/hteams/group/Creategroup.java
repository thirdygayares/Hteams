package com.example.hteams.group;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.hteams.MainActivity;
import com.example.hteams.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

public class Creategroup extends AppCompatActivity {
    static String GroupName ="", Subject  = "", Professor = "", Description = "";

    Button nextbtn ;
    ImageButton backbtn;
    EditText grpname, subject,professor,descrip;
    static int ctrl = 0;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    String cname;
    static ArrayList< String > ClassHolder = new ArrayList < > ();
    static String section;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creategroup);
        //initiate

        //to know the email and uid
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();


        String cname = firebaseAuth.getCurrentUser().getUid();
        DocumentReference documentReference2 = firestore.collection("students").document(cname);
        documentReference2.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                section =  value.getString("Section");
//                Toast.makeText(ListOfClassmate.this, section, Toast.LENGTH_SHORT).show();
            }
        });




        initxml();
        //back button
        back();
        //next button
        next();
        //firebase

    }

    private void firebase() {



        firestore.collection("students")
                .whereEqualTo("Section",section)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            //Toast.makeText(ListOfClassmate.this, "Successful", Toast.LENGTH_SHORT).show();
                            for(QueryDocumentSnapshot documentSnapshot :  task.getResult()){
//                                Toast.makeText(Creategroup.this, documentSnapshot.getString("Name"), Toast.LENGTH_SHORT).show();
                                String documentId = documentSnapshot.getString("Name");
                                ClassHolder.add(documentId);
                            }
                        }
                    }
                });
    }


    private void next() {
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(ctrl == 0) {
                    firebase();
                    ctrl++;
                }


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

        grpname = findViewById(R.id.input_name);
        subject = findViewById(R.id.input_subject);
        professor = findViewById(R.id.input_professor);
        descrip = findViewById(R.id.input_description);



    }

    @Override
    public void onStart() {
        super.onStart();

    }
}