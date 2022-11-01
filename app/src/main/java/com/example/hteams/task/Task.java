package com.example.hteams.task;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hteams.R;
import com.example.hteams.adapter.GroupPageAdapater;
import com.example.hteams.adapter.PersonalTaskAdapter;
import com.example.hteams.adapter.PersonalTaskInterface;
import com.example.hteams.group.GroupPage;
import com.example.hteams.group.ViewTask;
import com.example.hteams.model.PersonalTaskModel;

import java.util.ArrayList;

public class Task extends Fragment implements PersonalTaskInterface {

    View view;
    ArrayList <PersonalTaskModel> personalTaskModels = new ArrayList<>();
    RecyclerView taskRecycler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.activity_task, container, false);

         //initialization of xml
        initXml();
        //task Recycler, fill up data
        //TODO firebase manipulation
        personalTaskData();

        return view;
    }

    //lalagay yung recycler view and date
    private void personalTaskData() {
        //calling recycler view
        PersonalTaskAdapter adapter = new PersonalTaskAdapter(getContext(), personalTaskModels, this);
        taskRecycler.setAdapter(adapter);
        taskRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayList<Integer> groupphoto = new ArrayList<Integer>();
        groupphoto.add(R.drawable.avatar1);
        groupphoto.add(R.drawable.profile);

        ArrayList<String> groupName = new ArrayList<String>();
        groupName.add("FireTera");
        groupName.add("Adroit");

        ArrayList<String> taskname = new ArrayList<String>();
        taskname.add("User Requirements");
        taskname.add("Use Case Diagram");

        ArrayList<String> status = new ArrayList<String>();
        status.add("Working on it");
        status.add("Ready");

        ArrayList<String> duedate = new ArrayList<String>();
        duedate.add("Oct 3, 12:00am");
        duedate.add("Oct 10, 3:00pm");

        for(int i=0; i<taskname.size();i++){
            personalTaskModels.add(new PersonalTaskModel(groupphoto.get(i),groupName.get(i),taskname.get(i),status.get(i),duedate.get(i)));
        }

    }

    private void initXml() {
        taskRecycler = view.findViewById(R.id.taskRecycler);
    }

    @Override
    public void onItemClick(int pos) {
        Intent intent = new Intent(getActivity(), ViewTask.class);
        startActivity(intent);
    }
}