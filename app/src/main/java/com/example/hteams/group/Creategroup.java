package com.example.hteams.group;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.hteams.MainActivity;
import com.example.hteams.R;

public class Creategroup extends AppCompatActivity {

    Button nextbtn ;
    ImageButton backbtn;
    EditText grpname, subject,professor,descrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creategroup);
        //initiate
        initxml();
        //back button
        back();
        //next button
        next();
    }

    private void next() {
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
    }
}