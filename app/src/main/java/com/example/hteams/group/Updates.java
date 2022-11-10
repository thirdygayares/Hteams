package com.example.hteams.group;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.vo.UpdateMethod;


import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.example.hteams.database.DatabaseHelper;
import com.example.hteams.model.DisplaySiteModel;
import com.example.hteams.model.ListDisplayModel;
import com.example.hteams.model.SiteModel;
import com.example.hteams.model.UpdateListModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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
    EditText cmntfield;
    RecyclerView sitesRecycler;
    Button sitenamefield;
    RecyclerView displaySites; // para pag click nung edit text madetect
    RecyclerView listrecycler;
    RecyclerView createlistrecycler;
    CardView link; //ito yung card view na indicator title ng link

    RelativeLayout listContainer;   //global variable for linearlayout to hide id may laman ba

    //Array List
    ArrayList <String> site_name = new ArrayList<String>();   //Site Name ex. Google meet etc,
    ArrayList <String> custom_name = new ArrayList<String>();   //custom name ex.Watch this guys
    ArrayList <String> web_link = new ArrayList<String>();     //link ex.www.google.com
    ArrayList<SiteModel> siteModels = new ArrayList<>(); //site Models
    ArrayList<DisplaySiteModel> displaySiteModels  = new ArrayList<>(); //display site Models
    ArrayList<UpdateListModel> updateListModels = new ArrayList<>();    //for array list na gagawa ng list
    ArrayList<ListDisplayModel> listDisplayModels = new ArrayList<>();  //eto naman array na sa ididisplay na yung list
    ArrayList<String> siteName = new ArrayList<>();    //name of site

    //adapter
    DisplaySiteAdapter adapter;
    ListDisplayAdapter adapter2;  //global variable of ListDisplayAdapter

    //firebase Auth
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;


    //SQLITE DB
    DatabaseHelper databaseHelper;
    String NameSite = "Site Name";   //store in sitename
    String groupId, taskId;
    String currentId;

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
        cmntfield = (EditText) findViewById(R.id.cmntfield);
        //calling the adapter2
        adapter2 = new ListDisplayAdapter(Updates.this, listDisplayModels, this);

        //to know the email and uid
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // method calls
        createlinksDialog();
        setupdatafordisplaySites();
        createlistDialog();
        createsitesDialog();
        //view the list display
        viewListDisplay();



        //calling database sqlite
        databaseHelper = new DatabaseHelper(Updates.this);

        //cyrrent id
        currentId = firebaseAuth.getCurrentUser().getUid();

        GroupPage groupPage = new GroupPage();

        //TODO: I COMMENT THIS BECAUSE IM TESTING
        groupId = groupPage.getGroupID;
        taskId = groupPage.getTaskID;


        Log.d("TAG", "current id: " + currentId);
        Log.d("TAG", "group id in updates page: " + groupId);
        Log.d("TAG", "task id in updates page: " + taskId);


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
                Toast.makeText(Updates.this, "Coming Soon".toString(), Toast.LENGTH_SHORT).show();
            }
        });

        records = findViewById(R.id.recordbtn);
        records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Toast.makeText(Updates.this, "Coming Soon".toString(), Toast.LENGTH_SHORT).show();
            }
        });
        // end of records

        files = findViewById(R.id.filebtn);
        files.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Toast.makeText(Updates.this, "Coming Soon".toString(), Toast.LENGTH_SHORT).show();
            }
        });
        //comment button
        comment = findViewById(R.id.comment_updatebtn);
        comment.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick (View v){
                UpdateMethod();
            }
        });
        // end of comment button
        cancel = findViewById(R.id.comment_cancelbtn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
               Intent intent = new Intent(Updates.this, ViewTask.class);
               startActivity(intent);
            }
        });

        //retrieving the taskName
        TextView taskname = findViewById(R.id.taskname);

        firestore.collection("task")
                .document(taskId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                taskname.setText(document.get("task_NAME").toString());
                            } else {
                                Log.d("TAG", "Nosuchdocument");
                            }
                        }else{
                            Log.d("TAG","not successful");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG",e.getMessage());
                    }
                });
    }

//    update
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void UpdateMethod() {
        try{

            HashMap<String,Object> createupdates = new HashMap<>();
            createupdates.put("ID_TASK", taskId);
            createupdates.put("ID_GROUP", groupId);
            createupdates.put("ID_STUDENTS", currentId);
            createupdates.put("UPDATES", cmntfield.getText().toString());
            LocalDate dateObj = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String date = dateObj.format(formatter);
            createupdates.put("CREATED", date);


            firestore.collection("updates").
                    add(createupdates).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("TAG", documentReference.getId());


                            if(displaySiteModels.isEmpty()){//check if empyu
                                Log.d("TAG","Link is empty");
                            }else{

                                for(int i = 0;i<displaySiteModels.size();i++){
                                    HashMap<String,Object> createLinks = new HashMap<>();
                                    createLinks.put("ID_UPDATES" , documentReference.getId());
                                    createLinks.put("ID_GROUPS" ,groupId);
                                    createLinks.put("CUSTOMNAME" ,displaySiteModels.get(i).getCustomsitename());
                                    createLinks.put("WEBLINK" ,displaySiteModels.get(i).getLink());
                                    createLinks.put("SITENAME" ,displaySiteModels.get(i).getSiteName());

                                    firestore.collection("links")
                                            .add(createLinks).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    Log.d("TAG", "SUCCESS ADD LINKS");
                                                }
                                            });
                                }
                            }

                            //list save
                            if(listDisplayModels.isEmpty()){
                                Log.d("TAG","List  is empty");
                            }else{
                                for(int x = 0; x<listDisplayModels.size(); x++){
                                    //send list data
                                    HashMap<String,Object> createList = new HashMap<>();
                                    createList.put("ID_UPDATES" , documentReference.getId());
                                    createList.put("ID_GROUPS" ,groupId);
                                    createList.put("LISTNAME" ,listDisplayModels.get(x).getTaskname());
                                    createList.put("STATUS" ,listDisplayModels.get(x).getChecked());

                                    firestore.collection("Lists")
                                            .add(createList).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    Log.d("TAG", "SUCCESS ADD LISTS");
                                                }
                                            });

                                }
                            }

                            //send logs
                            //hashmap for logs
                            HashMap < String, Object > logs = new HashMap < > ();
                            logs.put("groupId", groupId);
                            logs.put("students_id", currentId);
                            logs.put("Message", "post an update from the task  " );
                            logs.put("task", taskId);
                            SimpleDateFormat s = new SimpleDateFormat("ddMMyyyyhhmmss");
                            String format = s.format(new Date());
                            logs.put("date", format);

                            //add data to logs
                            firestore.collection("logs").add(logs).addOnSuccessListener(new OnSuccessListener < DocumentReference > () {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d("TAG", "SUCCESS LOG");
                                }
                            });


                            //hashmap for notification
                            HashMap < String, Object > notification = new HashMap < > ();
                            notification.put("id", documentReference.getId());
                            notification.put("students_id", currentId);
                            notification.put("Message", " posted an update to the task" );
                            notification.put("type", "update");
                            notification.put("status", false);
                            SimpleDateFormat s2 = new SimpleDateFormat("ddMMyyyyhhmmss");
                            String format2 = s2.format(new Date());
                            notification.put("date", format2);

                            //add data to logs
                            firestore.collection("notification").add(notification).addOnSuccessListener(new OnSuccessListener < DocumentReference > () {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d("TAG", "SUCCESS notification");
                                }
                            });

                            Intent intent = new Intent(Updates.this,ViewTask.class);
                            startActivity(intent);
                            finish();



                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("TAG",e.getMessage());
                        }
                    });


        }catch (Exception e){
            Log.d("TAG", "failed to update: " + e);

        }
    }

    //viewing the list in updates class
    private void viewListDisplay() {
        //calling adapter and recycler
        listrecycler.setAdapter(adapter2);
        listrecycler.setLayoutManager(new LinearLayoutManager(Updates.this) );
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

                if(newlist_edit.length() == 0){
                    newlist_edit.setError("Required");
                }else{
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



            }
        });


        //when cliking the submit button
        submitlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(updateListModels.isEmpty()){
                    newlist_edit.setError("Required");
                }else{

                    //  saving data to listdisplay

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

            }
        });



        listupdatedialog.setContentView(view);

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

                if(name.length() == 0){
                    name.setError("Required");
                }

               else if(sitelink.length() == 0){
                    sitelink.setError("Required");
                }



                else{
                    if(sitenamefield.getText().toString().equalsIgnoreCase("Site Name")){
                        sitenamefield.setText("Others");
                    }
                    //  TODO : error handling pag walang input dapat maglalabas ng error sa gilid na required-status: done
                    //sitename array store yung name na text sa editText
                    site_name.add(sitenamefield.getText().toString());
                    custom_name.add(name.getText().toString());
                    web_link.add(sitelink.getText().toString());
                    // Toast.makeText(Comments.this,custom_name.toString(),Toast.LENGTH_SHORT).show();
                    // to add in the model and maread sa array
                    displaySiteModels.add(new DisplaySiteModel(name.getText().toString(), sitenamefield.getText().toString(), sitelink.getText().toString()));
                    //to update the content of adapter
                    adapter.notifyItemInserted(custom_name.size() - 1);
                    displaySites.scrollToPosition(custom_name.size());
                    //to show the indicator link title
                    link.setVisibility(View.VISIBLE);
                    name.setText("");
                    sitelink.setText("");
                    //to hide the linkdialog
                    linkdialog.dismiss();
                }
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
        sitelistdialog.setCancelable(false);
        //para lumabas
        sitelistdialog.setContentView(view);
    }

    private void setupdataforsites() { // para sa mga sites sa dialog
        int[] siteicon = {R.drawable.others, R.drawable.canvca, R.drawable.discord,R.drawable.excel,R.drawable.facebook,R.drawable.drive,R.drawable.github,R.drawable.gmail,R.drawable.meet,R.drawable.notes, R.drawable.google,R.drawable.messenger,R.drawable.moodle,R.drawable.teams,R.drawable.onedrive,R.drawable.powerpoint,R.drawable.telegram,R.drawable.word,R.drawable.youtube,R.drawable.zoom};
        String[] sitename = {"Others","Canva","Discord","Excel","Facebook","Gdrive","Github","Gmail","Gmeet","Gnotes","Google","Messenger","Moodle","Mteams","Onedrive","Powerpoint","Telegram","Word","Youtube","Zoom"};

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


        switch (tag){
            case "displaysite":
//                Toast.makeText(Updates.this, "Display adapter",Toast.LENGTH_SHORT).show();
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