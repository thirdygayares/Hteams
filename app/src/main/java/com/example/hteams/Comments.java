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
    EditText sitenamefield;
    Button sitebtnemmbedd; // para pag click nung edit text madetect

    //store in sitename
    String NameSite = "Site Name";

    ArrayList<SiteModel> siteModels = new ArrayList<>();

    //name of site
    ArrayList<String> siteName = new ArrayList<>();
    ArrayList<String> cmntsitelist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        Button links = findViewById(R.id.linkbtn);
        linkdialog = new BottomSheetDialog(this);
        createlinksDialog();


        //links button
        links.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linkdialog.show();
                cmntsitelist.add(NameSite);
            }
        });



        list = findViewById(R.id.listbtn);
        list.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){
                Toast.makeText(Comments.this, "List Clicked".toString(), Toast.LENGTH_SHORT).show();
            }
        });
        // end of list


        //capture start
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



    //comment
    private void setupData() {
        int[] siteicon = {R.drawable.meetlogo, R.drawable.githublogo,R.drawable.drivelogo};
        String[] sitename = {"Google Meet","Github","Google Drive","facebook"};

        for(int i = 0; i<siteicon.length;i++){
            siteModels.add(new SiteModel(siteicon[i],sitename[i]));
        }

    }


//pag pinindot ang link dialog
    private void createlinksDialog() {     // for bottomsheet

        View view = getLayoutInflater().inflate(R.layout.bottomsheet, null, false);
        Button submit = view.findViewById(R.id.submitbtn);
        sitenamefield = view.findViewById(R.id.sitenamefield); // call createsitedialog
        EditText name = findViewById(R.id.customenamefield);
        EditText sitelink = findViewById(R.id.linkfield);
        sitebtnemmbedd = findViewById(R.id.sitenamebtn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               linkdialog.dismiss();


            }
        });

        sitelistdialog = new BottomSheetDialog(this);
        createsitesDialog();


        sitenamefield.setText(NameSite);

       sitebtnemmbedd.setOnClickListener(new View.OnClickListener() {    // first click bottom dialog call
            @Override
            public void onClick(View v) {
                sitelistdialog.show();

                //para maghide yung link dialig
                linkdialog.hide();
            }
        });

        linkdialog.setContentView(view);

    }


    //pag pinindot ang site name lalabas to
    //bla
    //bla
    private void createsitesDialog() {
        View view = getLayoutInflater().inflate(R.layout.sitesbottomsheet,null, false);
        sitesRecycler  = view.findViewById(R.id.sitesRecycler);
        setupData();
        SiteAdapter adapter = new SiteAdapter(Comments.this,siteModels,this);
        sitesRecycler.setAdapter(adapter);
        sitesRecycler.setLayoutManager(new LinearLayoutManager(Comments.this));

        //para lumabas
        sitelistdialog.setContentView(view);

    }


    @Override
    public void onItemClick(int pos) {
        Toast.makeText(Comments.this,"Clicked an Item",Toast.LENGTH_SHORT).show();
        sitelistdialog.hide(); //para maghide yung link dialiog

        linkdialog.show();


        //nagaautomatic na magchange
        sitenamefield.setText(siteModels.get(pos).getSitename());

        //dito ka magsasave sa array
        siteName.add(siteModels.get(pos).getSitename());
        Toast.makeText(Comments.this,siteName.toString(),Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onStart() {
        super.onStart();
//        if(siteName.isEmpty()){
//            sitenamefield.setText("Test");
//        }else {
//            sitenamefield.setText(siteName.get(0));siteName.get(0));
//        }

    }
}

