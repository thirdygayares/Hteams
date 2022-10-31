package com.example.hteams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hteams.adapter.DisplaySiteAdapter;
import com.example.hteams.adapter.SiteAdapter;
import com.example.hteams.adapter.SiteInterface;
import com.example.hteams.group.ViewTask;
import com.example.hteams.model.DisplaySiteModel;
import com.example.hteams.model.SiteModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;

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
    Button sitenamefield;
    RecyclerView displaySites; // para pag click nung edit text madetect

    //ito yung card view na indicator title ng link
    CardView link;

    //    Link array, saving local sitename,custom name,link

    //store in sitename
    String NameSite = "Site Name";

    //Site Name ex. Google meet etc,
    ArrayList <String> site_name = new ArrayList<String>();
    //custom name ex.Watch this guys
    ArrayList <String> custom_name = new ArrayList<String>();
    //link ex.www.google.com
    ArrayList <String> web_link = new ArrayList<String>();



    ArrayList<SiteModel> siteModels = new ArrayList<>();
    ArrayList<DisplaySiteModel> displaySiteModels  = new ArrayList<>();

    //name of site
    ArrayList<String> siteName = new ArrayList<>();
    DisplaySiteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        sitesRecycler = findViewById(R.id.sitesRecycler);
        displaySites = findViewById(R.id.displayrecyleview); // for display
        adapter = new DisplaySiteAdapter(Comments.this,displaySiteModels,this);
        setupdatafordisplaySites();

        Button links = findViewById(R.id.linkbtn);
        linkdialog = new BottomSheetDialog(this);
        createlinksDialog();

//        condition to hide link material button if no laman
        link = findViewById(R.id.link);
        if (custom_name.isEmpty()){
            link.setVisibility(View.GONE);
        }else{
            link.setVisibility(View.VISIBLE);
        }



        //links button
        links.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linkdialog.show();

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


//        start of record
        records = findViewById(R.id.recordbtn);
        records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Toast.makeText(Comments.this, "Record Clicked".toString(), Toast.LENGTH_SHORT).show();
            }
        });
        // end of records

        files = findViewById(R.id.filebtn);
        files.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Toast.makeText(Comments.this, "Files  Clicked".toString(), Toast.LENGTH_SHORT).show();
            }
        });

        // end of files



        //comment button
        update = findViewById(R.id.comment_updatebtn);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
              Intent intentupdate = new Intent(Comments.this, ViewTask.class);
                startActivity(intentupdate);
            }
        });
        // end of comment button

        cancel = findViewById(R.id.comment_cancelbtn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Toast.makeText(Comments.this, "Comment cancelled ".toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupdatafordisplaySites() {
        displaySites.setAdapter(adapter);
        displaySites.setLayoutManager(new LinearLayoutManager(Comments.this)); // important
    }

    //TODO : Pasequence nung mga method

    //pag pinindot ang link dialog
    private void createlinksDialog() {     // for bottomsheet

        View view = getLayoutInflater().inflate(R.layout.bottomsheet, null, false);
        Button submit = view.findViewById(R.id.submitbtn);
        sitenamefield = view.findViewById(R.id.sitenamefield); // call createsitedialog
        EditText name = view.findViewById(R.id.customenamefield);
        EditText sitelink = view.findViewById(R.id.linkfield);


            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //  TODO : error handling pag walang input dapat maglalabas ng error sa gilid na required

                    //sitename array store yung name na text sa editText
                    site_name.add(sitenamefield.getText().toString());
                    //add custom name example "Wactch this"
                    custom_name.add(name.getText().toString());
                    //array of web link saved
                    web_link.add(sitelink.getText().toString());

                    // Toast.makeText(Comments.this,custom_name.toString(),Toast.LENGTH_SHORT).show();
                    // to add in the model and maread sa array
                    displaySiteModels.add(new DisplaySiteModel(name.getText().toString(), sitenamefield.getText().toString()));

                    //to update the content of adapter
                    adapter.notifyItemInserted(custom_name.size()-1);
                    displaySites.scrollToPosition(custom_name.size());

                    //to show the indicator link title
                    link.setVisibility(View.VISIBLE);

                    //to hide the linkdialog
                    linkdialog.dismiss();
                }
            });

        sitelistdialog = new BottomSheetDialog(this);
        createsitesDialog();
        sitenamefield.setText(NameSite);
        sitenamefield.setOnClickListener(new View.OnClickListener() {
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
    private void createsitesDialog() {
        View view = getLayoutInflater().inflate(R.layout.sitesbottomsheet,null, false);
        sitesRecycler  = view.findViewById(R.id.sitesRecycler);
        setupdataforsites();
        SiteAdapter adapter = new SiteAdapter(Comments.this,siteModels,this);
        sitesRecycler.setAdapter(adapter);
        sitesRecycler.setLayoutManager(new LinearLayoutManager(Comments.this));

        //para lumabas
        sitelistdialog.setContentView(view);

    }

    private void setupdataforsites() { // para sa mga sites sa dialog
        int[] siteicon = {R.drawable.meetlogo, R.drawable.githublogo,R.drawable.drivelogo};
        String[] sitename = {"Google Meet","Github","Google Drive","facebook"};

        for(int i = 0; i<siteicon.length;i++){
            siteModels.add(new SiteModel(siteicon[i],sitename[i]));
        }

    }


    @Override
    public void onItemClick(int pos, String tag) {
//        Toast.makeText(Comments.this,"Clicked an Item",Toast.LENGTH_SHORT).show();
//        sitelistdialog.hide(); //para maghide yung link dialiog
//        linkdialog.show();
//        //nagaautomatic na magchange
//        sitenamefield.setText(siteModels.get(pos).getSitename());
//        //dito ka magsasave sa array
//        siteName.add(siteModels.get(pos).getSitename());
//        Toast.makeText(Comments.this,siteName.toString(),Toast.LENGTH_SHORT).show(
//        );

        switch (tag){
            case "displaysite":
                Toast.makeText(Comments.this, "Display adapter",Toast.LENGTH_SHORT).show();
                break;

            case "siteadapter":

                //to copy the click
                sitenamefield.setText(siteModels.get(pos).getSitename());

                //to hide the sitelist dialog
                sitelistdialog.hide();
                //after to choose the link magpopopup  ulit ang link dialog
                linkdialog.show();



                break;
            default:
                Toast.makeText(Comments.this, "default",Toast.LENGTH_SHORT).show();
        }
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

