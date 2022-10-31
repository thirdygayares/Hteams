package com.example.hteams.group;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hteams.R;
import com.example.hteams.adapter.ViewTaskAdapter;
import com.example.hteams.adapter.ViewTaskInterface;
import com.example.hteams.model.ViewTaskModel;

import java.util.ArrayList;

public class ViewTask extends AppCompatActivity implements ViewTaskInterface {
    ArrayList<ViewTaskModel> viewTaskModels = new ArrayList<>();
    RecyclerView viewTask;
    Button postButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        //initializion of id in xml
        initxml();

        //button for post update
        post();

        //posting updates Recycler View
        ViewTaskAdapter adapter = new ViewTaskAdapter(ViewTask.this, viewTaskModels, this);
        viewTask.setAdapter(adapter);
        viewTask.setLayoutManager(new LinearLayoutManager(ViewTask.this));

        //Set up posting data
        setUpPostingData();


    }

    private void post() {
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewTask.this, Updates.class);
                startActivity(intent);
            }
        });
    }

    private void initxml() {
        viewTask = findViewById(R.id.viewtaskposting);
        postButton = findViewById(R.id.postButton);
    }


    private void setUpPostingData() {
        //dummy data
        int[] Profile = {R.drawable.marielle, R.drawable.profile, R.drawable.novem};
        String[] participant = {"Marielle Zabala","Thirdy Gayares","Novem Lanaban"};
        String[] DatePost = {"Oct 10", "Oct 29", "Sep 25"};
        int[] ViewCount = {2,3,4};
        int[] commentCount = {1,2,3};
        String[] description = {"Hi ako si Marielle , Pacheckk","Sorry di ko pa nagagawa","Gagawa ako ng bagong Model"};
        int[] filesCount = {2,1,8};
        int[] LikeCount = {4,6,2};
        int[] Dislike = {10,1,8};

        //from database data
//        ArrayList<String> salesId = new ArrayList<>();
//        ArrayList<String> Time = new ArrayList<>();
//        ArrayList<String> TotalPrice = new ArrayList<>();


        for(int i=0; i<participant.length; i++){
            viewTaskModels.add(new ViewTaskModel(Profile[i],participant[i],DatePost[i],ViewCount[i],commentCount[i],description[i],filesCount[i],LikeCount[i],Dislike[i]
            ));
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(ViewTask.this, ViewUpdates.class);
        startActivity(intent);
    }
}