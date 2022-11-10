package com.example.hteams.group;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hteams.Display.groupDetails;
import com.example.hteams.R;
import com.example.hteams.Testing.SetProfile;
import com.example.hteams.adapter.FilesAdapter;
import com.example.hteams.adapter.ImageAdapter;
import com.example.hteams.adapter.LinkAdapter;
import com.example.hteams.adapter.ViewUpdateInterface;
import com.example.hteams.adapter.ListAdapter;
import com.example.hteams.database.DatabaseHelper;
import com.example.hteams.model.DisplaySiteModel;
import com.example.hteams.model.FileModel;
import com.example.hteams.model.ImageModel;
import com.example.hteams.model.ListModel;
import com.example.hteams.model.ViewTaskModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ViewUpdates extends AppCompatActivity implements ViewUpdateInterface {

    RecyclerView imageslides, listrecycle, filesrecycler, linkrecyler;
    ArrayList<ImageModel> imageModels = new ArrayList<>();
    ArrayList<ListModel> listModels = new ArrayList<>();
    ArrayList<FileModel> fileModels = new ArrayList<>();
    ArrayList<DisplaySiteModel> displaySiteModels = new ArrayList<>();

    ImageView backicon,participant_photo;
    TextView taskName,participant_name,date_post,description;
    //firebase Auth
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    //SQLITE DB
    DatabaseHelper databaseHelper;
    String currentId;
    String date;//date of the updates when post
    String getGroupID;
    String getTaskID;
    String getUpdatesId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_updates);

        //to know the email and uid
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        //calling database sqlite
        databaseHelper = new DatabaseHelper(ViewUpdates.this);

        //cyrrent id
        currentId = firebaseAuth.getCurrentUser().getUid();

        //creatng object to get the value of Group Id, table Id, and task ID
        GroupPage groupPage = new GroupPage();

        //comment ko muna for testing

        // set Group id
        getGroupID = groupPage.getGroupIDInt;
        //set Task ID
        getTaskID =  groupPage.getTaskID;

        //creating object for calling update ID
        ViewTask viewTask = new ViewTask();
        getUpdatesId = viewTask.updatesId;





        //TODO : NOTE code fill the method

        //initialization of id in xml
        initxml();
        //recyclerview for List
        listRecycle();
        //recyclerview for Image SLides
        imageSlides();
        //recyclerview for files
        filesRecycle();
        //recyclerview for link
        linkRecycle();
        //backicon click
        backIcon();
        //retrieve tasktitle
        retrieveTaskTitle();

        //set The kung sino ang nagpost at date kung kaylan pinost and yung description nung pinost
        retrieveCredentialsPost();





    }


    private void retrieveCredentialsPost() {

        firestore.collection("updates").document(getUpdatesId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            date_post.setText(documentSnapshot.get("CREATED").toString()); //date
                            description.setText(documentSnapshot.get("UPDATES").toString());


                            firestore.collection("students").document(documentSnapshot.get("ID_STUDENTS").toString())
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener < DocumentSnapshot > () {
                                        @Override
                                        public void onComplete(@NonNull Task < DocumentSnapshot > task) {
                                            if (task.isSuccessful()) {
                                                DocumentSnapshot document = task.getResult();
                                                Picasso.get().load(document.get("image").toString()).error(R.drawable.ic_profile).into(participant_photo);
                                                participant_name.setText(document.get("Name").toString());
                                            }
                                        }
                                    });


                        }else{
                            //code here
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }

    private void retrieveTaskTitle() {

        firestore.collection("task").document(getTaskID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            taskName.setText(document.get("task_NAME").toString());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });

    }

    private void backIcon() {
        backicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewUpdates.this, ViewTask.class);
                startActivity(intent);
            }
        });
    }


    private void listRecycle() {

        ListAdapter listAdapter = new ListAdapter(ViewUpdates.this, listModels, this);
        listrecycle.setAdapter(listAdapter);
        listrecycle.setLayoutManager(new LinearLayoutManager(ViewUpdates.this));

        try{
            Cursor getListData = databaseHelper.getListData(String.valueOf(getUpdatesId));

            ArrayList<Integer> taskid = new ArrayList<Integer>();
            ArrayList<Integer> status = new ArrayList<Integer>();
            ArrayList<String> nameTask = new ArrayList<String>();

            while (getListData.moveToNext()){
                status.add(getListData.getInt(4));
                nameTask.add(getListData.getString(3));
                taskid.add(getListData.getInt(0));
            }

            for(int i=0;i<status.size();i++){
                listModels.add(new ListModel(taskid.get(i),status.get(i), nameTask.get(i)));
            }

//        gone the visibility if the list is empty
            if(nameTask.isEmpty()){
                LinearLayout listContainer = findViewById(R.id.listContainer);
                listContainer.setVisibility(View.GONE);
            }
        }catch (Exception e){
            Log.e("TAG","List error retrieving because " + e );
        }


    }


    //start of image container
    private void imageSlides() {
        ImageAdapter imageAdapters = new ImageAdapter(ViewUpdates.this, imageModels, this);
        imageslides.setAdapter(imageAdapters);
        imageslides.setLayoutManager(new GridLayoutManager(ViewUpdates.this, 3));

        ArrayList<Integer> imageholder = new ArrayList<Integer>();
//        imageholder.add(R.drawable.sample);
//        imageholder.add(R.drawable.profile);
//        imageholder.add(R.drawable.novem);
//        imageholder.add(R.drawable.marielle);


        for(int i=0;i<imageholder.size();i++){
            imageModels.add(new ImageModel(imageholder.get(i)));
        }

        //        gone the visibility if the list is empty
        if(imageholder.isEmpty()){
            LinearLayout listContainer = findViewById(R.id.imagecontainer);
            listContainer.setVisibility(View.GONE);
        }

    }
    //end of image container


    //recyclervire for files
    private void filesRecycle() {
        FilesAdapter filesAdapter = new FilesAdapter(ViewUpdates.this, fileModels, this);
        filesrecycler.setAdapter(filesAdapter);
        filesrecycler.setLayoutManager(new LinearLayoutManager(ViewUpdates.this));


        ArrayList<String> filename = new ArrayList<String>();
//        filename.add("Jose Rizal");
//        filename.add("Thirdy Gayares v1");
//        filename.add("ang loro");
//        filename.add("read me");

        ArrayList<String> filetype = new ArrayList<String>();
//        filetype.add("pdf");
//        filetype.add("word");
//        filetype.add("pptx");
//        filetype.add("txt");

        for(int i=0;i<filename.size();i++){
            fileModels.add(new FileModel(filename.get(i), filetype.get(i)));
        }

        // gone the visibility if the files is empty
        if(filename.isEmpty()){
            LinearLayout filesContainer = findViewById(R.id.filesContainer);
            filesContainer.setVisibility(View.GONE);
        }
    }

    //end of files recycle

    // start of link container
    private void linkRecycle() {
        LinkAdapter filesAdapter = new LinkAdapter(ViewUpdates.this, displaySiteModels, this);
        linkrecyler.setAdapter(filesAdapter);
        linkrecyler.setLayoutManager(new LinearLayoutManager(ViewUpdates.this));


        ArrayList<String> customLinkName = new ArrayList<String>();
        ArrayList<String> sitename = new ArrayList<String>();
        ArrayList<String> link = new ArrayList<String>();

        Cursor getLinkData = databaseHelper.getLinkData(String.valueOf(getUpdatesId));

        try{
            while (getLinkData.moveToNext()){
                customLinkName.add(getLinkData.getString(3));
                sitename.add(getLinkData.getString(5));
                link.add(getLinkData.getString(4));

            }
                for(int i=0;i<customLinkName.size();i++){
                    displaySiteModels.add(new DisplaySiteModel(customLinkName.get(i), sitename.get(i), link.get(i)));
                }

        }catch (Exception e){
            Log.d("TAG", "LINK ERROR RETRIEVING CAUSE " + e);
        }


        // gone the visibility if the files is empty
        if(sitename.isEmpty()){
            LinearLayout LinksContainer = findViewById(R.id.LinksContainer);
            LinksContainer.setVisibility(View.GONE);
        }
    }
    //end of link container

    //onclick manipulation
    @Override
    public void onItemClick(int pos, String list) {
        switch (list){
            case "link":
                Intent intent = new Intent(ViewUpdates.this, WebViewLik.class);
                intent.putExtra("Web", displaySiteModels.get(pos).getLink());
                startActivity(intent);
                break;
        }


    }

    private void initxml() {
//        image recycle
        imageslides = (RecyclerView) findViewById(R.id.imageslides);
        //list recycle
        listrecycle = (RecyclerView) findViewById(R.id.listrecycle);
        //file recycle
        filesrecycler =(RecyclerView) findViewById(R.id.filesrecycler);
        //link recycle
        linkrecyler = (RecyclerView) findViewById(R.id.linkrecycler);
        //backicon
        backicon = (ImageView) findViewById(R.id.backicon);
        //task Name
        taskName = (TextView) findViewById(R.id.taskNames);
        //Participant photo
        participant_photo = (ImageView) findViewById(R.id.participant_photo);
        //participant name
        participant_name = (TextView) findViewById(R.id.participant_name);
        //date post
        date_post = (TextView) findViewById(R.id.date_post);
        //description
        description = (TextView) findViewById(R.id.description);

    }

}