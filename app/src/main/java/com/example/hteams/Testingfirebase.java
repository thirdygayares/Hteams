package com.example.hteams;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Testingfirebase extends AppCompatActivity {

    Button testing;
    private FirebaseFirestore Database = FirebaseFirestore.getInstance();
    private CollectionReference groupRef = Database.collection("testing");
    private CollectionReference participantRef = Database.collection("aaa");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testingfirebase);

        testing = findViewById(R.id.test);

        testing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //start

                Map<String, Object> docData = new HashMap<>();
                docData.put("stringExample", "Hello world!");
                docData.put("booleanExample", true);
                docData.put("numberExample", 3.14159265);
                docData.put("dateExample", new Timestamp(new Date()));
                docData.put("listExample", Arrays.asList(1, 2, 3));
                docData.put("nullExample", null);

                Map<String, Object> nestedData = new HashMap<>();
                nestedData.put("a", 5);
                nestedData.put("b", true);

                docData.put("objectExample", nestedData);

                Database.collection("data").add(docData)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error writing document", e);
                            }
                        });

                //end
            }
        });

    }
}