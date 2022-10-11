package com.example.hteams;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    Button login;
    EditText input_userid,input_password;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    TextView errorlogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialize xml
        initxml();
        firebaseAuth = FirebaseAuth.getInstance();
        //button login
        login();

    }

    private void login() {

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder alert = new AlertDialog.Builder(Login.this);
                View mView = getLayoutInflater().inflate(R.layout.login_loading, null);
                alert.setView(mView);
                final AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
                //intialize the value of textbox
                String emaiL, passworD;
                emaiL = String.valueOf(input_userid.getText());
                passworD = String.valueOf(input_password.getText());

                if (emaiL.isEmpty()) {
                    alertDialog.dismiss();
                    input_userid.setError("Email is Required");
                    return;
                }

                if (passworD.isEmpty()) {
                    alertDialog.dismiss();
                    input_password.setError("Password is Required");
                    return;
                }

                //Start ProgressBar first (Set visibility VISIBLE)
                if (!emaiL.equals("") && !passworD.equals("")) {

                    firebaseAuth.signInWithEmailAndPassword(emaiL, passworD).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            //when login is successful
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
////                            SharedPreferences.Editor editor = sharedPreferences.edit();
//                            DocumentReference documentReference = firestore.collection("users").document(firebaseAuth.getCurrentUser().getUid());
//                            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                                @Override
//                                public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                    firebaseAuth.getCurrentUser().getUid();
//                                    firebaseAuth.getCurrentUser().getEmail();
//                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                                    finish();
////                                    editor.putString(STOREID, firebaseAuth.getCurrentUser().getUid());
////                                    editor.putString(EMAIL, firebaseAuth.getCurrentUser().getEmail());
////                                    editor.putString(CATEGORY, documentSnapshot.getString("Category"));
////                                    editor.putString(STORENAME, documentSnapshot.getString("StoreName"));
////                                    editor.apply();
////
////
////                                    CategoryCheck = sharedPreferences.getString(CATEGORY, null);
////
////                                    if(CategoryCheck.equals("1")) {
////                                        startActivity(new Intent(getApplicationContext(), PointofSaleNodata.class));
////                                        finish();
////                                    }
////
////                                    if(CategoryCheck.equals("2")) {
////                                        startActivity(new Intent(getApplicationContext(), com.example.smartbusiness.category2.ui.Category2.class));
////                                        finish();
////                                    }
////
////                                    if (CategoryCheck.equals("3")) {
////                                        startActivity(new Intent(getApplicationContext(), Home.class));
////                                        finish();
////                                    }
//                                    Toast.makeText(Login.this, "we are loggin in", Toast.LENGTH_SHORT).show();
//                                }
//                            }).addOnFailureListener(e -> Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show());

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            alertDialog.dismiss();
                            errorlogin.setVisibility(View.VISIBLE);
                            errorlogin.setText(e.getMessage());
                        }
                    });

                } else {
                    alertDialog.dismiss();
                    errorlogin.setVisibility(View.VISIBLE);
                    errorlogin.setText("All Fields are Required");
//                    alertDialog.dismiss();
//                    Toast.makeText(Login.this, "All Fields are Required", Toast.LENGTH_SHORT).show();
                }


        //sa button
            }
        });

    }

    private void updateUI(FirebaseUser user) {
    }


    private void initxml() {
        input_userid = findViewById(R.id.input_studentId);
        input_password = findViewById(R.id.input_password);
        login = findViewById(R.id.login);
        errorlogin = findViewById(R.id.errorLogin);
    }

    //android Behavior

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        return;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }


}