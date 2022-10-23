package com.example.hteams;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.nio.InvalidMarkException;

public class Comments extends AppCompatActivity {

    BottomSheetDialog linkdialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);


        ImageButton links = findViewById(R.id.linkbtn);
        linkdialog = new BottomSheetDialog(this);
        createDialog();

          links.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  linkdialog.show();
              }
          });
    }

    private void createDialog() {

        View view = getLayoutInflater().inflate(R.layout.bottomsheet,null,false);
        Button submit = findViewById(R.id.submitbtn);
        EditText site = findViewById(R.id.sitenamefield);
        EditText name = findViewById(R.id.customenamefield);
        EditText sitelink = findViewById(R.id.linkfield);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linkdialog.dismiss();
                Toast.makeText(Comments.this,name.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });

        linkdialog.setContentView(view);
    }
}