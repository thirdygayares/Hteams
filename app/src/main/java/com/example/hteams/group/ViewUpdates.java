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
import com.example.hteams.adapter.ImageAdapter;
import com.example.hteams.adapter.Interface;
import com.example.hteams.adapter.ListAdapter;
import com.example.hteams.model.GroupPageModel;
import com.example.hteams.model.ImageModel;
import com.example.hteams.model.ListModel;

import java.util.ArrayList;

public class ViewUpdates extends AppCompatActivity implements Interface {

    RecyclerView imageslides, listrecycle;
    ArrayList<ImageModel> imageModels = new ArrayList<>();
    ArrayList<ListModel> listModels = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_updates);

//        initialization of id in xml
        initxml();
        //recyclerview for Image SLides
        imageSlides();
        //recyclerview for List
        listRecycle();


    }

    private void listRecycle() {

        ListAdapter listAdapter = new ListAdapter(ViewUpdates.this, listModels, this);
        listrecycle.setAdapter(listAdapter);
        listrecycle.setLayoutManager(new LinearLayoutManager(ViewUpdates.this));


        ArrayList<Integer> status = new ArrayList<Integer>();
        status.add(1);
        status.add(1);
        status.add(1);

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

    private void initxml() {
//        image recycle
        imageslides = findViewById(R.id.imageslides);
        //list recycle
        listrecycle = findViewById(R.id.listrecycle);
    }

    @Override
    public void onItemClick(int pos) {
        Intent intent = new Intent(ViewUpdates.this, ViewTask.class);
        startActivity(intent);
    }
}