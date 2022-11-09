package com.example.hteams.Requirements;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FindSection {
    static String TAG = "TAG";
    static FirebaseFirestore firestore;

    static FirebaseAuth firebaseAuth;
    static String  CurrentId;


    public static String getSection(String id){
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        CurrentId = firebaseAuth.getUid();

         String[] Section = new String[1];

        DocumentReference docRef=firestore.collection("students").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>(){
            @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task){
                if(task.isSuccessful()){
                    DocumentSnapshot document=task.getResult();
                    if(document.exists()){
//                       Log.d(TAG,"Document Snapshotdata:"+ document.get("Section").toString());
                        Section[0] = document.get("Section").toString();
                    }
                    else{
                        Log.d(TAG,"Nosuchdocument");
                    }
                }else{
                    Log.d(TAG,"getfailedwith",task.getException());
                }
            }
        });


        return Section[0];
    }





}
