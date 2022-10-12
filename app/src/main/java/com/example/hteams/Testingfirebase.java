package com.example.hteams;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

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

                // Update one field, creating the document if it does not already exist.
                Map<String, Object> data = new HashMap<>();
                data.put("capital", false);

                Database.collection("cities").document("BJ")
                        .set(data, SetOptions.merge());

             //end
            }
        });

    }
}