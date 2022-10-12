package com.example.hteams.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hteams.Login;
import com.example.hteams.R;
import com.google.firebase.auth.FirebaseAuth;

public class Profile extends Fragment {
    FirebaseAuth firebaseAuth;
    TextView email, id;
    Button logout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, container, false);

        logout = view.findViewById(R.id.logout);
        email = view.findViewById(R.id.email);
        id = view.findViewById(R.id.id);

        //to know the email and uid
        firebaseAuth = FirebaseAuth.getInstance();

        id.setText(firebaseAuth.getCurrentUser().getUid());
        email.setText(firebaseAuth.getCurrentUser().getEmail());


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder alert = new AlertDialog.Builder( getContext());
                View mView = getLayoutInflater().inflate(R.layout.login_loading, null);
                alert.setView(mView);
                final AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), Login.class));
        }
        });

        return view;
    }


}