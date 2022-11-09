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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hteams.Display.groupDetails;
import com.example.hteams.Login;
import com.example.hteams.R;
import com.example.hteams.Testing.SetProfile;
import com.google.firebase.auth.FirebaseAuth;

public class Profile extends Fragment {
    FirebaseAuth firebaseAuth;
    TextView email, id, name;
    ImageView logo;
    Button logout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, container, false);

        logout = view.findViewById(R.id.logout);
        email = view.findViewById(R.id.email);
        name = view.findViewById(R.id.name);
        logo =  view.findViewById(R.id.logo);
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

        //profile picture
        String currentid = firebaseAuth.getCurrentUser().getUid();
        groupDetails group = new groupDetails();
        SetProfile setProfile = new SetProfile();
        logo.setImageResource(setProfile.profileImage(group.participantImage(getContext(), currentid)));
        name.setText(group.partcipantName(getContext(), firebaseAuth.getCurrentUser().getUid()));
        return view;
    }
}