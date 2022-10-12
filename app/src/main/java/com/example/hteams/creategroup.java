package com.example.hteams;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class creategroup extends AppCompatActivity {

    Button backbtn,nextbtn ;
    EditText grpname, subject,professor,descrip;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creategroup);


    }
}