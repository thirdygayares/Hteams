package com.example.hteams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.hteams.adapter.SiteAdapter;
import com.example.hteams.adapter.SiteInterface;
import com.example.hteams.model.SiteModel;

import java.util.ArrayList;

public class Sites extends AppCompatActivity implements SiteInterface {

    RecyclerView sitesRecycler;

    ArrayList<SiteModel> siteModels = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sites);
        initxml();

        SiteAdapter adapter = new SiteAdapter(Sites.this,siteModels,this);
        sitesRecycler.setAdapter(adapter);
        sitesRecycler.setLayoutManager(new LinearLayoutManager(Sites.this));


        setupData();
    }

    private void setupData() {
        int[] siteicon = {R.drawable.meetlogo, R.drawable.githublogo};
        String[] sitename = {"Google Meet","Github"};

        for(int i = 0; i<siteicon.length;i++){
            siteModels  .add(new SiteModel(siteicon[i],sitename[i]));

        }



    }

    private void initxml() {

        sitesRecycler  = findViewById(R.id.sitesRecycler);

    }

    @Override
    public void onItemClick (int position){
        Toast.makeText(Sites.this,"Clicked an Item",Toast.LENGTH_SHORT).show();
    }
}