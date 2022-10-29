package com.example.hteams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hteams.adapter.SiteAdapter;
import com.example.hteams.adapter.SiteInterface;
import com.example.hteams.model.SiteModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class Comments extends AppCompatActivity implements SiteInterface {

    BottomSheetDialog linkdialog;
    BottomSheetDialog sitelistdialog;
    Button list;
    Button capture;
    Button files;
    Button records;
    Button update;
    TextView cancel;
    RecyclerView sitesRecycler;

    ArrayList<SiteModel> siteModels = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        initxml();
        setupData();


        Button links = findViewById(R.id.linkbtn);
        linkdialog = new BottomSheetDialog(this);
        createlinksDialog();

        links.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                                         linkdialog.show();
                                     }
                                 }
        );


        list = findViewById(R.id.listbtn);
        list.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){
                Toast.makeText(Comments.this, "List Clicked".toString(), Toast.LENGTH_SHORT).show();
            }
        });
        // end of list


        capture = findViewById(R.id.capturebtn);
        capture.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){

                Toast.makeText(Comments.this, "Capture Clicked".toString(), Toast.LENGTH_SHORT).show();

            }
        });

        // end of capture


        records = findViewById(R.id.recordbtn);
        records.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){
                Toast.makeText(Comments.this, "Record Clicked".toString(), Toast.LENGTH_SHORT).show();
            }
        });
        // end of records

        files = findViewById(R.id.filebtn);
        files.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){
                Toast.makeText(Comments.this, "Files  Clicked".toString(), Toast.LENGTH_SHORT).show();
            }
        });

        // end of files


        update = findViewById(R.id.comment_updatebtn);
        update.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){
                Toast.makeText(Comments.this, "update selected".toString(), Toast.LENGTH_SHORT).show();
            }
        });
        // end of

        cancel = findViewById(R.id.comment_cancelbtn);
        cancel.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){
                Toast.makeText(Comments.this, "Comment cancelled ".toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }




    private void initxml() {
        sitesRecycler  = findViewById(R.id.sitesRecycler);
    }

    private void setupData() {
        int[] siteicon = {R.drawable.meetlogo, R.drawable.githublogo};
        String[] sitename = {"Google Meet","Github"};

        for(int i = 0; i<siteicon.length;i++){
            siteModels  .add(new SiteModel(siteicon[i],sitename[i]));

        }



    }



    private void createlinksDialog() {     // for bottomsheet

        View view = getLayoutInflater().inflate(R.layout.bottomsheet, null, false);
        Button submit = findViewById(R.id.submitbtn);
        EditText sitenamefield = findViewById(R.id.sitenamefield); // call createsitedialog
        EditText name = findViewById(R.id.customenamefield);
        EditText sitelink = findViewById(R.id.linkfield);

        sitelistdialog = new BottomSheetDialog(this);
        createsitesDialog();

        sitenamefield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {sitelistdialog.show();}
        });


        linkdialog.setContentView(view);
    }


    private void createsitesDialog() {
        View view = getLayoutInflater().inflate(R.layout.sitesbottomsheet,null, false);
       //initxml();

        //SiteAdapter adapter = new SiteAdapter(Comments.this,siteModels,this);
       // sitesRecycler.setAdapter(adapter);
       // sitesRecycler.setLayoutManager(new LinearLayoutManager(Comments.this));
    }


    @Override
    public void onItemClick(int pos) {
        Toast.makeText(Comments.this,"Clicked an Item",Toast.LENGTH_SHORT).show();

    }
}

