package com.example.hteams.group;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hteams.R;
import com.example.hteams.adapter.GroupAdapter;
import com.example.hteams.adapter.GroupInterface;
import com.example.hteams.adapter.GroupPageAdapater;

import com.example.hteams.adapter.GroupPageAdapter2;
import com.example.hteams.adapter.GroupPageInterface;
import com.example.hteams.model.GroupModel;
import com.example.hteams.model.GroupPageModel;
import com.example.hteams.model.GroupPageModel2;

import java.util.ArrayList;

public class GroupPage extends AppCompatActivity implements GroupPageInterface {

    ArrayList<GroupPageModel> groupPageModels = new ArrayList<>();
    ArrayList<GroupPageModel2> groupPageModels2 = new ArrayList<>();
    TextView Addtask2,Addtask1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_page);

        setupGroupData();
        setupGroupData2();


        Addtask2 = findViewById(R.id.Addtask2);
        Addtask1 = findViewById(R.id.Addtask);

        RecyclerView recyclerView = findViewById(R.id.taskRecycler);
        GroupPageAdapater adapter = new GroupPageAdapater(GroupPage.this, groupPageModels, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(GroupPage.this));

        RecyclerView recyclerView2 = findViewById(R.id.taskRecycler2);
        GroupPageAdapter2 adapter2 = new GroupPageAdapter2(GroupPage.this, groupPageModels2, this);
        recyclerView2.setAdapter(adapter2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(GroupPage.this));


        Addtask1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupPage.this, AddTask.class);
                startActivity(intent);
            }
        });

        Addtask2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupPage.this, AddTask.class);
                startActivity(intent);
            }
        });

    }

    private void setupGroupData() {
        //dummy data
        String[] Title = {"Placeholder 1","PlaceHolder2","PlaceHolder 3","PlaceHolder 4","PlaceHolder 5","PlaceHolder 6"};
        String[] Status = {"Working on It", "Done", "Pending", "Working on It", "Done", "Pending"};
        String[] Deadline = {"Oct 5 , 10:00 am", "Oct 5 , 10:00 am", "Oct 5 , 10:00 am", "Oct 5 , 10:00 am", "Oct 5 , 10:00 am", "Oct 5 , 10:00 am" };


        //from database data
//        ArrayList<String> salesId = new ArrayList<>();
//        ArrayList<String> Time = new ArrayList<>();
//        ArrayList<String> TotalPrice = new ArrayList<>();


        for(int i=0; i<Title.length; i++){
            groupPageModels.add(new GroupPageModel(Title[i],Status[i],Deadline[i]
            ));
        }

    }

    private void setupGroupData2() {
        //dummy data
        String[] Title2 = {"Placeholder 1","PlaceHolder2","PlaceHolder 3","PlaceHolder 4","PlaceHolder 5","PlaceHolder 6"};
        String[] Status2 = {"Working on It", "Done", "Pending", "Working on It", "Done", "Pending"};
        String[] Deadline2 = {"Oct 5 , 10:00 am", "Oct 5 , 10:00 am", "Oct 5 , 10:00 am", "Oct 5 , 10:00 am", "Oct 5 , 10:00 am", "Oct 5 , 10:00 am" };


        //from database data
//        ArrayList<String> salesId = new ArrayList<>();
//        ArrayList<String> Time = new ArrayList<>();
//        ArrayList<String> TotalPrice = new ArrayList<>();


        for(int i=0; i<Title2.length; i++){
            groupPageModels2.add(new GroupPageModel2(Title2[i],Status2[i],Deadline2[i]
            ));
        }

    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(GroupPage.this, "click", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(GroupPage.this, GroupPage.class);
//        intent.putExtra("salesId", historyDataModels.get(position).getSalesID());
//        intent.putExtra("Total", historyDataModels.get(position).getTotalPrice());
//        startActivity(intent);
    }
}