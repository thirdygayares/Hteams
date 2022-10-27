package com.example.hteams;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.nio.InvalidMarkException;

public class Comments extends AppCompatActivity {

    BottomSheetDialog linkdialog;

    ImageButton list;
    ImageButton capture;
    ImageButton files;
    ImageButton records;
    Button update;
    TextView cancel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        list = findViewById(R.id.listbtn);
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Comments.this,"List Clicked".toString(),Toast.LENGTH_SHORT).show();
            }
        });

        capture = findViewById(R.id.capturebtn);
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(Comments.this,"Capture Clicked".toString(),Toast.LENGTH_SHORT).show();

            }
        });

        records = findViewById(R.id.recordbtn);
        records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Comments.this,"Record Clicked".toString(),Toast.LENGTH_SHORT).show();
            }
        });

        files = findViewById(R.id.filebtn);
        files.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         Toast.makeText(Comments.this,"Files  Clicked".toString(),Toast.LENGTH_SHORT).show();
                                     }
        }
        );

        update =  findViewById(R.id.comment_updatebtn);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Comments.this,"update selected".toString(),Toast.LENGTH_SHORT).show();
            }
        });




        ImageButton links = findViewById(R.id.linkbtn);
        linkdialog = new BottomSheetDialog(this);
        createDialog();

          links.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  linkdialog.show();
              }
          });


        cancel = findViewById(R.id.comment_cancelbtn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Comments.this,"Comment cancelled ".toString(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void createDialog() {

        View view = getLayoutInflater().inflate(R.layout.bottomsheet,null,false);
        Button submit = findViewById(R.id.submitbtn);
        EditText site = findViewById(R.id.sitenamefield);
        EditText name = findViewById(R.id.customenamefield);
        EditText sitelink = findViewById(R.id.linkfield);



        linkdialog.setContentView(view);



    }


}