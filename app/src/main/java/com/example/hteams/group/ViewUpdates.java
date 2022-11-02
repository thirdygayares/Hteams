package com.example.hteams.group;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.hteams.R;
import com.example.hteams.adapter.DisplaySiteAdapter;
import com.example.hteams.adapter.FilesAdapter;
import com.example.hteams.adapter.ImageAdapter;
import com.example.hteams.adapter.LinkAdapter;
import com.example.hteams.adapter.ViewUpdateInterface;
import com.example.hteams.adapter.ListAdapter;
import com.example.hteams.model.DisplaySiteModel;
import com.example.hteams.model.FileModel;
import com.example.hteams.model.ImageModel;
import com.example.hteams.model.ListModel;

import java.util.ArrayList;

public class ViewUpdates extends AppCompatActivity implements ViewUpdateInterface {

    RecyclerView imageslides, listrecycle, filesrecycler, linkrecyler;
    ArrayList<ImageModel> imageModels = new ArrayList<>();
    ArrayList<ListModel> listModels = new ArrayList<>();
    ArrayList<FileModel> fileModels = new ArrayList<>();
    ArrayList<DisplaySiteModel> displaySiteModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_updates);

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

    }



    private void listRecycle() {

        ListAdapter listAdapter = new ListAdapter(ViewUpdates.this, listModels, this);
        listrecycle.setAdapter(listAdapter);
        listrecycle.setLayoutManager(new LinearLayoutManager(ViewUpdates.this));

        ArrayList<Boolean> status = new ArrayList<Boolean>();
        status.add(true);
        status.add(true);
        status.add(true);

        ArrayList<String> nameTask = new ArrayList<String>();
        nameTask.add("Search the capability");
        nameTask.add("Gumawa ng tama");
        nameTask.add("sumosobra ka na");

        for(int i=0;i<status.size();i++){
            listModels.add(new ListModel(status.get(i), nameTask.get(i)));
        }

//        gone the visibility if the list is empty
        if(nameTask.isEmpty()){
            LinearLayout listContainer = findViewById(R.id.listContainer);
            listContainer.setVisibility(View.GONE);
        }
    }


    //start of image container
    private void imageSlides() {
        ImageAdapter imageAdapters = new ImageAdapter(ViewUpdates.this, imageModels, this);
        imageslides.setAdapter(imageAdapters);
        imageslides.setLayoutManager(new GridLayoutManager(ViewUpdates.this, 3));

        ArrayList<Integer> imageholder = new ArrayList<Integer>();
        imageholder.add(R.drawable.sample);
        imageholder.add(R.drawable.profile);
        imageholder.add(R.drawable.novem);
        imageholder.add(R.drawable.marielle);


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
        filename.add("Jose Rizal");
        filename.add("Thirdy Gayares v1");
        filename.add("ang loro");
        filename.add("read me");

        ArrayList<String> filetype = new ArrayList<String>();
        filetype.add("pdf");
        filetype.add("word");
        filetype.add("pptx");
        filetype.add("txt");

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
        customLinkName.add("Kindly Enter to meet you guys");
        customLinkName.add("Our Introduction file v1");
        customLinkName.add("Repositories in our app");
        customLinkName.add("Drive for this task");

        ArrayList<String> sitename = new ArrayList<String>();
        sitename.add("Google Meet");
        sitename.add("Google Drive");
        sitename.add("Github");
        sitename.add("Google Drive");

        for(int i=0;i<customLinkName.size();i++){
            displaySiteModels.add(new DisplaySiteModel(customLinkName.get(i), sitename.get(i)));
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
    public void onItemClick(int pos) {
        Intent intent = new Intent(ViewUpdates.this, ViewTask.class);
        startActivity(intent);
    }


    private void initxml() {
//        image recycle
        imageslides = findViewById(R.id.imageslides);
        //list recycle
        listrecycle = findViewById(R.id.listrecycle);
        //file recycle
        filesrecycler = findViewById(R.id.filesrecycler);
        //link recycle
        linkrecyler = findViewById(R.id.linkrecycler);
    }

}