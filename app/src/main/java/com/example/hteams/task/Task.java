package com.example.hteams.task;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
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
import com.example.hteams.model.ViewTaskModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.function.LongFunction;

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
    PersonalTaskAdapter adapter;
    TextView todo,working,ready,done,All;

    ProgressDialog progressDialog;

    //firebase Auth
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    //SQLITE DB
    DatabaseHelper databaseHelper;
    String currentId;
    public static String groupname;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.activity_task, container, false);

        //process dialog
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching task...");
        progressDialog.show();

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
        adapter = new PersonalTaskAdapter(getContext(), personalTaskModels, this);
        taskRecycler.setAdapter(adapter);
        taskRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));


        firestore.collection("task")
                .whereEqualTo("id_STUDENTS", currentId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error!=null){
                            Log.d("TAG", error.getMessage());
                        }
                        for(DocumentChange dc: value.getDocumentChanges()){
                            if (dc.getType() == DocumentChange.Type.ADDED) {

                                personalTaskModels.add(new PersonalTaskModel(dc.getDocument().getId(),dc.getDocument().get("id_GROUP").toString(),dc.getDocument().get("id_TABLE").toString(),dc.getDocument().get("id_GROUP").toString(),groupname,dc.getDocument().get("task_NAME").toString(),dc.getDocument().get("status").toString(),dc.getDocument().get("dueDate").toString() + " " + dc.getDocument().get("dueTime").toString()));
//                                viewTaskModels.add(new ViewTaskModel(dc.getDocument().getId(), dc.getDocument().get("ID_STUDENTS").toString(), dc.getDocument().get("ID_STUDENTS").toString(),strCurrentDate,dc.getDocument().get("UPDATES").toString()));
                                adapter.notifyDataSetChanged();
                                if(progressDialog.isShowing())
                                    progressDialog.dismiss();


                            }
                        }

                        if(personalTaskModels.isEmpty()){
                            tasknotificationEmpty.setVisibility(View.VISIBLE);
                        }else{
                            tasknotificationEmpty.setVisibility(View.GONE);
                        }
                    }
                });



        firestore.collection("task")
                .whereEqualTo("id_STUDENTS", currentId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.d("TAG", error.getMessage());
                        }
                        int count = 0;
                        int todocount = 0;
                        int readycount = 0;
                        int workingcount = 0;
                        int donecount = 0;
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                count++;
                                String stats = dc.getDocument().get("status").toString();
                                if (stats.equalsIgnoreCase("TO DO")) {
                                    todocount++;
                                } else if (stats.equalsIgnoreCase("Ready")) {
                                    readycount++;
                                } else if (stats.equalsIgnoreCase("Working")) {
                                    workingcount++;
                                } else if (stats.equalsIgnoreCase("Done")) {
                                    donecount++;
                                }

                            }

                            All.setText(String.valueOf(count));
                            done.setText(String.valueOf(donecount));
                            working.setText(String.valueOf(workingcount));
                            todo.setText(String.valueOf(todocount));
                            ready.setText(String.valueOf(readycount));
                        }
                    }
                });





        done.setText(String.valueOf(donecount));
        working.setText(String.valueOf(workingcount));
        todo.setText(String.valueOf(todocount));
        ready.setText(String.valueOf(readycount));


//        if(personalTaskModels.isEmpty()){
//            tasknotificationEmpty.setVisibility(View.VISIBLE);
//        }else{
//            tasknotificationEmpty.setVisibility(View.GONE);
//        }
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
        groupPage.getGroupIDInt = personalTaskModels.get(pos).getIdGroup();
        groupPage.getTaskID = personalTaskModels.get(pos).getIdTask();
        groupPage.getTableID = personalTaskModels.get(pos).getId_table();
        Intent intent = new Intent(getActivity(), ViewTask.class);
        startActivity(intent);
    }

}