package com.example.hteams.group;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.hteams.R;
import com.example.hteams.adapter.InviteAdapter;
import com.example.hteams.model.InviteModel;

import java.util.ArrayList;

public class CreateGroup2 extends AppCompatActivity {

    ArrayList<InviteModel> inviteModels = new ArrayList<>();
    static ArrayList <String> Classmate = new ArrayList<String>(); //Create Array
    TextView add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creategroup2);

        //initialization
        initxml();

        //add when click
        add();

        //Recycler view of invite classmate

        RecyclerView recyclerView = findViewById(R.id.inviteRecycler);
        setupInviteModel();
        InviteAdapter adapter = new InviteAdapter(this, inviteModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void add() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateGroup2.this, ListOfClassmate.class));
            }
        });
    }

    private void initxml() {
        add = findViewById(R.id.add);
    }


    //adding data in invite classmate interface
    private void setupInviteModel() {
        //dummy datasaasdadw


        //from database data
//        ArrayList<String> salesId = new ArrayList<>();
//        ArrayList<String> Time = new ArrayList<>();
//        ArrayList<String> TotalPrice = new ArrayList<>();


        for(int i=0; i<Classmate.size(); i++){
            inviteModels.add(new InviteModel(Classmate.get(i)
            ));
        }
    }




}