package com.example.hteams.group;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hteams.R;
import com.example.hteams.adapter.DisplaySiteAdapter;
import com.example.hteams.adapter.ListDisplayAdapter;
import com.example.hteams.adapter.SiteAdapter;
import com.example.hteams.adapter.SiteInterface;
import com.example.hteams.adapter.UpdateListAdapter;
import com.example.hteams.model.DisplaySiteModel;
import com.example.hteams.model.ListDisplayModel;
import com.example.hteams.model.SiteModel;
import com.example.hteams.model.UpdateListModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class Updates extends AppCompatActivity implements SiteInterface {

    BottomSheetDialog linkdialog;
    BottomSheetDialog sitelistdialog;
    BottomSheetDialog listupdatedialog;
    Button list;
    Button capture;
    Button files;
    Button records;
    Button comment;
    TextView cancel;
    RecyclerView sitesRecycler;
    Button sitenamefield;
    RecyclerView displaySites; // para pag click nung edit text madetect
    RecyclerView listrecycler;
    RecyclerView createlistrecycler;

    //ito yung card view na indicator title ng link
    CardView link;
    CardView listindicate;

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

    //for array list na gagawa ng list
    ArrayList<UpdateListModel> updateListModels = new ArrayList<>();
    //eto naman array na sa ididisplay na yung list
    ArrayList<ListDisplayModel> listDisplayModels = new ArrayList<>();

    //name of site
    ArrayList<String> siteName = new ArrayList<>();
    DisplaySiteAdapter adapter;

    ArrayList<String> taskname = new ArrayList<>();
    //global variable of ListDisplayAdapter
    ListDisplayAdapter adapter2;

    //global variable for linearlayout to hide id may laman ba
    RelativeLayout listContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updates);
        // widget casting
        Button links = findViewById(R.id.linkbtn);
        // component casting
        listrecycler = findViewById(R.id.display_list_updates);
        sitesRecycler = findViewById(R.id.sitesRecycler);
        displaySites = findViewById(R.id.displayrecyleview); // for display
        adapter = new DisplaySiteAdapter(Updates.this,displaySiteModels,this);
        // Constructor
        linkdialog = new BottomSheetDialog(this);
        sitelistdialog = new BottomSheetDialog(this);
        listupdatedialog = new BottomSheetDialog(this);


        //calling the adapter2
        adapter2 = new ListDisplayAdapter(Updates.this, listDisplayModels, this);

        // method calls
        createlinksDialog();
        setupdatafordisplaySites();
        createlistDialog();
        createsitesDialog();
        //view the list display
        viewListDisplay();



//        condition to hide link material button if no laman
//        paste here if error


        //links button
        links.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linkdialog.show();
                // del this later if err occurs

            }
        });

        list = findViewById(R.id.listbtn);
        list.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){
                listupdatedialog.show();
            }
        });

        capture = findViewById(R.id.capturebtn);
        capture.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){

                Toast.makeText(Updates.this, "Capture Clicked".toString(), Toast.LENGTH_SHORT).show();
            }
        });

        records = findViewById(R.id.recordbtn);
        records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Toast.makeText(Updates.this, "Record Clicked".toString(), Toast.LENGTH_SHORT).show();
            }
        });
        // end of records

        files = findViewById(R.id.filebtn);
        files.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Toast.makeText(Updates.this, "Files  Clicked".toString(), Toast.LENGTH_SHORT).show();
            }
        });
        //comment button
        comment = findViewById(R.id.comment_updatebtn);
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Intent intentupdate = new Intent(Updates.this, ViewTask.class);
                startActivity(intentupdate);
            }
        });
        // end of comment button
        cancel = findViewById(R.id.comment_cancelbtn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Toast.makeText(Updates.this, "Comment cancelled ".toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //viewing the list in updates class
    private void viewListDisplay() {
        //calling adapter and recycler

        listrecycler.setAdapter(adapter2);
        listrecycler.setLayoutManager(new LinearLayoutManager(Updates.this) );

        //testing data

//        // set up data forlist
//        String listname = "Thirdy paayos";
//        //list if checked or unchecked
//        boolean status = true;
//        listDisplayModels.add(new ListDisplayModel(status,listname));

        //the list container hide this if wala naman data
        listContainer = findViewById(R.id.listContainer);

        if(listDisplayModels.isEmpty()){
            listContainer.setVisibility(View.GONE);
        }
    }


    //list dialog[checkboc]
    private void createlistDialog() {

        View view = getLayoutInflater().inflate(R.layout.listbottomsheet_updates,null , false );
        Button submitlist = view.findViewById(R.id.submitlist_btn);
        ImageView addbtn = view.findViewById(R.id.addbtn);
        EditText newlist_edit =view.findViewById(R.id.newlist_edit);
        createlistrecycler = view.findViewById(R.id.create_list);
        CheckBox checked_icon = view.findViewById(R.id.checked_icon);

        //for testing
//        setupdataforlist();

        UpdateListAdapter adapter = new UpdateListAdapter(Updates.this, updateListModels, this);
        createlistrecycler.setAdapter(adapter);
        createlistrecycler.setLayoutManager(new LinearLayoutManager(Updates.this) );


        //kunin ang data sa edit Text
        //get the status if check or unchecked
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean stats = false;
                if(checked_icon.isChecked())
                {
                    stats = true;
                    checked_icon.setChecked(false);
                }else{
                    stats =false;
                }

                updateListModels.add(new UpdateListModel(stats,newlist_edit.getText().toString()));
                adapter.notifyItemInserted(updateListModels.size()-1);
                displaySites.scrollToPosition(updateListModels.size());
                newlist_edit.setText("");

            }
        });


        //when cliking the submit button
        submitlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //erase the content of list
                // TAGALOG: Buburahin baka maisama kasi magdodoble lamang
//                listDisplayModels.clear();
//              option 1
//                int count = listDisplayModels.size();
//                listDisplayModels.clear();
//
//                for (int x =0; x<=count; x++ ){
//                    adapter2.notifyItemRemoved(x);
//                }


//                saving data to listdisplay

                for(int i=0;i<updateListModels.size();i++){
                    listDisplayModels.add(new ListDisplayModel(updateListModels.get(i).getChecked(), updateListModels.get(i).getTaskname()));
                }


                adapter2.notifyItemInserted(listDisplayModels.size()-1);
                displaySites.scrollToPosition(listDisplayModels.size());

                listContainer.setVisibility(View.VISIBLE);
                listupdatedialog.dismiss();

                //option 2
                updateListModels.clear();

            }
        });


        listupdatedialog.setContentView(view);

    }


    //testing na lagyan ng data
    //nevermind this
    private void setupdataforlist() {
        // set up data forlist

        String listname = "Thirdy paayos";
        //list if checked or unchecked
        boolean status = true;

        updateListModels.add(new UpdateListModel(status,listname));

    }


    //TODO : Pasequence nung mga method
    //pag pinindot ang link dialog
    private void createlinksDialog() { // for bottomsheet

        View view = getLayoutInflater().inflate(R.layout.linkbottomsheet_updates, null, false);
        Button submit = view.findViewById(R.id.submitbtn);
        sitenamefield = view.findViewById(R.id.sitenamefield); // call createsitedialog
        EditText name = view.findViewById(R.id.customenamefield);
        EditText sitelink = view.findViewById(R.id.linkfield);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                 /*
                    else if (custom_name == null || custom_name.equals("")){
                        Toast.makeText(getBaseContext(),"Custom Name is empty",Toast.LENGTH_LONG).show();
                    }
                    else if (web_link == null || web_link.equals(" ")){
                        Toast.makeText(getBaseContext(),"Link is required",Toast.LENGTH_LONG).show();
                    }

                  */

                //  TODO : error handling pag walang input dapat maglalabas ng error sa gilid na required-status: done
                //sitename array store yung name na text sa editText

                site_name.add(sitenamefield.getText().toString());
                custom_name.add(name.getText().toString());
                web_link.add(sitelink.getText().toString());

                // Toast.makeText(Comments.this,custom_name.toString(),Toast.LENGTH_SHORT).show();
                // to add in the model and maread sa array
                displaySiteModels.add(new DisplaySiteModel(name.getText().toString(), sitenamefield.getText().toString()));
                //to update the content of adapter
                adapter.notifyItemInserted(custom_name.size() - 1);
                displaySites.scrollToPosition(custom_name.size());
                //to show the indicator link title
                link.setVisibility(View.VISIBLE);

                //to hide the linkdialog
                linkdialog.dismiss();
            }

        });


        //  createsitesDialog();
        sitenamefield.setText(NameSite);
        sitenamefield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sitelistdialog.show();
                //para maghide yung link dialig
                linkdialog.hide();
            }
        });
        // dito ko nilipat
        link = findViewById(R.id.link);
        if (custom_name.isEmpty()){
            link.setVisibility(View.GONE);
        }else{
            link.setVisibility(View.VISIBLE);
        }
        linkdialog.setContentView(view);
    }

    //pag pinindot ang site name lalabas to
    private void createsitesDialog() {
        View view = getLayoutInflater().inflate(R.layout.sitesbottomsheet,null, false);
        sitesRecycler  = view.findViewById(R.id.sitesRecycler);
        setupdataforsites();
        SiteAdapter adapter = new SiteAdapter(Updates.this,siteModels,this);
        sitesRecycler.setAdapter(adapter);
        sitesRecycler.setLayoutManager(new LinearLayoutManager(Updates.this));

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

    private void setupdatafordisplaySites() {
        displaySites.setAdapter(adapter);
        displaySites.setLayoutManager(new LinearLayoutManager(Updates.this)); // important
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
                Toast.makeText(Updates.this, "Display adapter",Toast.LENGTH_SHORT).show();
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
//                Toast.makeText(Updates.this, "default",Toast.LENGTH_SHORT).show();
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