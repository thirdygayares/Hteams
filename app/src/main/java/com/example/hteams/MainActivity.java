package com.example.hteams;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hteams.group.Home;
import com.example.hteams.group.ListOfClassmate;
import com.example.hteams.notification.Notification;
import com.example.hteams.profile.Profile;
import com.example.hteams.task.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputLayout;
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
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    String TAG = "jose";
    String thirdy = "thirdy";
    EditText input;
    Button button,Logout;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(navListener);


        int x = 5;
        Intent intent = getIntent();
//        x = Integer.parseInt(intent.getStringExtra("going"));

        Fragment selectedFragment = new Home();

        if (x == 0) {
            bottomNav.setSelectedItemId(R.id.nav_home);

            selectedFragment = new Task();
        }else if(x == 1){
            bottomNav.setSelectedItemId(R.id.nav_task);
            selectedFragment = new Notification();
        }else if(x == 2){
            bottomNav.setSelectedItemId(R.id.nav_notification);
            selectedFragment = new Profile();
        }else if(x == 3){
            bottomNav.setSelectedItemId(R.id.nav_profile);
            selectedFragment = new Profile();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                selectedFragment).commit();

        firebaseGettingData();

    }


    private NavigationBarView.OnItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;
                switch (item.getItemId()){
                    case R.id.nav_home:
                        selectedFragment = new Home();
                        break;
                    case R.id.nav_task:
//                            Toast.makeText(MainActivity.this, "stocks", Toast.LENGTH_SHORT).show();
                        selectedFragment = new Task();
                        break;
                    case R.id.nav_notification:
                        selectedFragment = new Notification();
                        break;
                    case R.id.nav_profile:
                        selectedFragment = new Profile();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                return true;
            };





    private void firebaseGettingData() {


    }


}