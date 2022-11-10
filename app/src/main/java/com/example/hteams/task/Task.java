package com.example.hteams.task;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hteams.Display.groupDetails;
import com.example.hteams.R;
import com.example.hteams.adapter.GroupPageAdapater;
import com.example.hteams.adapter.PersonalTaskAdapter;
import com.example.hteams.adapter.PersonalTaskInterface;
import com.example.hteams.database.DatabaseHelper;
import com.example.hteams.group.GroupPage;
import com.example.hteams.group.Updates;
import com.example.hteams.group.ViewTask;
import com.example.hteams.model.PersonalTaskModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Task extends Fragment implements PersonalTaskInterface {

    View view;
    ArrayList <PersonalTaskModel> personalTaskModels = new ArrayList<>();
    RecyclerView taskRecycler;
    RelativeLayout tasknotificationEmpty;
    ArrayList<String> todoArray = new ArrayList<>();
    ArrayList<String> workingArray = new ArrayList<>();
    ArrayList<String> readyArray = new ArrayList<>();
    ArrayList<String> doneArray= new ArrayList<>();

    int all = 0, todocount = 0, workingcount=0, readycount=0, donecount=0 ;

    TextView todo,working,ready,done,All;

    //firebase Auth
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    //SQLITE DB
    DatabaseHelper databaseHelper;
    String currentId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.activity_task, container, false);

        //to know the email and uid
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        //calling database sqlite
        databaseHelper = new DatabaseHelper(getContext());

        //cyrrent id
        currentId = firebaseAuth.getCurrentUser().getUid();

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

        ArrayList<String> groupphoto = new ArrayList<String>();
        ArrayList<String> groupName = new ArrayList<String>();
        ArrayList<String> taskname = new ArrayList<String>();
        ArrayList<String> status = new ArrayList<String>();
        ArrayList<String> duedate = new ArrayList<String>();
        ArrayList<Integer> idtask = new ArrayList<Integer>();
        ArrayList<Integer> id_group = new ArrayList<Integer>();
        ArrayList<Integer> id_table = new ArrayList<Integer>();


        groupDetails groupDetailss = new groupDetails();

        Cursor getPersonaltask = databaseHelper.getPersonalTask(currentId);
        try{
                while(getPersonaltask.moveToNext()){
                    groupphoto.add(getPersonaltask.getString(1));
                    groupName.add(groupDetailss.getGroupName(getContext(),getPersonaltask.getString(1)));
                    taskname.add(getPersonaltask.getString(4));
                    duedate.add(getPersonaltask.getString(7) + ", " +  getPersonaltask.getString(8));
                    idtask.add(getPersonaltask.getInt(0));
                    id_group.add(getPersonaltask.getInt(1));
                    id_table.add(getPersonaltask.getInt(2));


                    status.add(getPersonaltask.getString(5));
                    //TODO COUNTING

                    String statuses = getPersonaltask.getString(5);
                    //count to do, rady and done
                    if(statuses.equalsIgnoreCase("to do")){
                        todocount++;
                    }else if(statuses.equalsIgnoreCase("Ready")){
                        readycount++ ;
                    }else if(statuses.equalsIgnoreCase("Done")){
                        donecount++ ;
                    }else if(statuses.equalsIgnoreCase("Working")){
                        workingcount++ ;
                    }

                    //count all
                    all++;

                }
                for(int i=0; i<taskname.size();i++){
                    personalTaskModels.add(new PersonalTaskModel(idtask.get(i),id_group.get(i),id_table.get(i),groupphoto.get(i),groupName.get(i),taskname.get(i),status.get(i),duedate.get(i)));
                }

        }catch (Exception e){
            Log.d("TAG", "FAILED TO RETRIVED PERSONAL TASK CAUSE " +e );
        }

        done.setText(String.valueOf(donecount));
        working.setText(String.valueOf(workingcount));
        todo.setText(String.valueOf(todocount));
        ready.setText(String.valueOf(readycount));
        All.setText(String.valueOf(all));

        if(personalTaskModels.isEmpty()){
            tasknotificationEmpty.setVisibility(View.VISIBLE);
        }else{
            tasknotificationEmpty.setVisibility(View.GONE);
        }

    }

    private void initXml() {
        taskRecycler = view.findViewById(R.id.taskRecycler);
        tasknotificationEmpty = view.findViewById(R.id.tasknotificationEmpty);
        todo= view.findViewById(R.id.todo);
        working= view.findViewById(R.id.inprogress);
        ready= view.findViewById(R.id.ready);
        done= view.findViewById(R.id.done);
        All= view.findViewById(R.id.all);
    }

    @Override
    public void onItemClick(int pos) {
//        static int getGroupIDInt;
//        static int getTaskID;
//        static int getTableID;
        GroupPage groupPage = new GroupPage();
//        groupPage.getGroupIDInt = personalTaskModels.get(pos).getIdGroup();
//        groupPage.getTaskID = personalTaskModels.get(pos).getIdTask();
//        groupPage.getTableID = personalTaskModels.get(pos).getId_table();
        Intent intent = new Intent(getActivity(), ViewTask.class);
        startActivity(intent);
    }

}