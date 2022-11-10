package com.example.hteams.group;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hteams.R;
import com.example.hteams.Testing.SetAvatar;
import com.example.hteams.adapter.GroupInterface;
import com.example.hteams.adapter.GroupPageAdapater;

import com.example.hteams.adapter.TableAdapter;
import com.example.hteams.adapter.taskadapter.DoneAdapter;
import com.example.hteams.adapter.taskadapter.ReadyAdapter;
import com.example.hteams.adapter.taskadapter.ToDoAdapter;
import com.example.hteams.adapter.taskadapter.WorkingAdapter;
import com.example.hteams.database.DatabaseHelper;
import com.example.hteams.model.GroupPageModel;
import com.example.hteams.model.GroupPageModel2;
import com.example.hteams.model.GroupPageParentModel;
import com.example.hteams.model.GroupStorePageModel;
import com.example.hteams.model.TableModel;
import com.example.hteams.model.taskModel.DoneModel;
import com.example.hteams.model.taskModel.ReadyModel;
import com.example.hteams.model.taskModel.TodoModel;
import com.example.hteams.model.taskModel.WorkingModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class GroupPage extends AppCompatActivity implements GroupInterface {

    BottomSheetDialog addTable;
    //child
    ArrayList<GroupPageModel> groupPageModels = new ArrayList<>();
    //parent
    //todo erase unecessary later
    ArrayList<GroupPageParentModel> groupPageParentModels = new ArrayList<>();
    ArrayList<GroupPageModel2> groupPageModels2 = new ArrayList<>();
    ArrayList<TableModel> tableModels = new ArrayList<>();
    ArrayList<TodoModel> todoModels = new ArrayList<>();
    ArrayList<WorkingModel> workingModels = new ArrayList<>();
    ArrayList<DoneModel> doneModels = new ArrayList<>();
    ArrayList<ReadyModel> readyModels = new ArrayList<>();

    public static int tableposition = 0, changecolor = 0;
    static boolean todovisible = true, workingvisible=true, readyvisible = true, donevisible = true;
    CardView Addtask1;
    RelativeLayout displayMode,emptyContainer,firstGroup,secondGroup,fourthgroup,thirdgroup,addTables;
    TextView nameofgroup ;
    ImageView menu,logo;
    Button addfirsttaskbutton;

    //firebase Auth
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    //SQLITE DB
    DatabaseHelper databaseHelper;
    String currentId;
    public static String getGroupID;
    public static  int lastposition;
    public static int getGroupIDInt;
    public static int getTaskID;
    public int getTableID;
    String tableid;

    String TAG = "TAG";


    //adapter
    TableAdapter tableadapter;
    ToDoAdapter todoadapter;
    WorkingAdapter workingAdapter;
    ReadyAdapter readyAdapter;
    DoneAdapter doneAdapter;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_page);


        //process dialog
        progressDialog = new ProgressDialog(GroupPage.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching..");
        progressDialog.show();

        //to know the email and uid
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        //calling database sqlite
        databaseHelper = new DatabaseHelper(GroupPage.this);

        //cyrrent id
        currentId = firebaseAuth.getCurrentUser().getUid();

        // set Group id
        Home home = new Home();
         getGroupID = home.GroupId;

        //inititalization
        displayMode =  (RelativeLayout)findViewById(R.id.displayMode);

        Addtask1 = (CardView)findViewById(R.id.Addtask1);
        menu= findViewById(R.id.menu);
        emptyContainer = (RelativeLayout)findViewById(R.id.emptyContainer);

        //adding button in first Task
        addfirsttaskbutton = (Button)findViewById(R.id.addfirsttaskbutton) ;
        logo  = (ImageView)findViewById(R.id.logo);
        //add table button
        addTables = (RelativeLayout)findViewById(R.id.addTable);
        nameofgroup = (TextView) findViewById(R.id.nameofgroup) ;

        //set group image and Name;
        DislayGroupAndImage();
//        display choices [grid or list view]
        displayMode();
//      menu button
        menu();

        //method for clicking the add Table
        //createTable();//v1
        createTablev2();

        //table recycler view yung nasa taas eto


        //hide the view if empty
        firstGroup = findViewById(R.id.firstGroup);
        firstGroup.setVisibility(View.GONE);
        secondGroup = findViewById(R.id.secondgroup);
        secondGroup.setVisibility(View.GONE);
        fourthgroup = findViewById(R.id.fourthgroup);
        fourthgroup.setVisibility(View.GONE);
        thirdgroup = findViewById(R.id.thirdgroup);
        thirdgroup.setVisibility(View.GONE);

        // setup data for table
        //setupTable();

        //recycler view fo table
        RecyclerView tablerecylerview = findViewById(R.id.tablerecylce);
        tableadapter = new TableAdapter(GroupPage.this, tableModels, this);
        tablerecylerview.setAdapter(tableadapter);
        LinearLayoutManager  HorizontalLayout = new LinearLayoutManager(GroupPage.this, LinearLayoutManager.HORIZONTAL, false);
        tablerecylerview.setLayoutManager(HorizontalLayout);


        //recycler view for to do
        //setupTodo();
        RecyclerView taskRecycler = findViewById(R.id.taskRecycler);
         todoadapter = new ToDoAdapter(GroupPage.this, todoModels, this);
        taskRecycler.setAdapter(todoadapter);
        LinearLayoutManager TableLayoutManager = new LinearLayoutManager(this);
        taskRecycler.setLayoutManager(TableLayoutManager);

        //setupWorking();
        RecyclerView workingRecyler = findViewById(R.id.taskRecycler2);
         workingAdapter = new WorkingAdapter(GroupPage.this, workingModels, this);
        workingRecyler.setAdapter(workingAdapter);
        LinearLayoutManager TableLayoutManager2 = new LinearLayoutManager(this);
        workingRecyler.setLayoutManager(TableLayoutManager2);


        //setupReady();
        RecyclerView readyrecycler = findViewById(R.id.taskRecycler4);
        readyAdapter = new ReadyAdapter(GroupPage.this, readyModels, this);
        readyrecycler.setAdapter(readyAdapter);
        LinearLayoutManager TableLayoutManager3 = new LinearLayoutManager(this);
        readyrecycler.setLayoutManager(TableLayoutManager3);


        //setupDone();
        RecyclerView donerecycler = findViewById(R.id.taskRecycler3);
        doneAdapter = new DoneAdapter(GroupPage.this, doneModels, this);
        donerecycler.setAdapter(doneAdapter);
        LinearLayoutManager TableLayoutManager4 = new LinearLayoutManager(this);
        donerecycler.setLayoutManager(TableLayoutManager4);




    }

    private void DislayGroupAndImage() {

        firestore.collection("groups").document(getGroupID)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                        if(error != null){
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                            Log.e("TAG", error.getMessage());
                        }

                        nameofgroup.setText(value.get("GROUPNAME").toString()); //to get the name of the group
                        String grouppic = value.get("GROUPPHOTO").toString();
                        Picasso.get().load(grouppic).error(R.drawable.ic_profile).into(logo);
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();

                    }
                });


    }

    private void createTablev2() {
    }


    //TODO BOTTOM SHEET add table
    //adding a first task
    private void createTable() {

//        bottom sheet
        View view = getLayoutInflater().inflate(R.layout.bottomsheet_adding_table, null, false);
        EditText tableName = view.findViewById(R.id.tableName);
        Button nextbtn = view.findViewById(R.id.nextbtn);

        addTable = new BottomSheetDialog(this);

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //para maghide yung add table dialog

                if (tableName.length() == 0){
                    tableName.setError("Required");
                    return;
                }else{
                    try {
                        String tablename = tableName.getText().toString();
                       // Boolean addtable = databaseHelper.addTable(tableName.getText().toString(), getGroupID);
                        //getting the count of table para sa position sa group page maayos
                        //checking the last of table table

                        Cursor getpositionbyfindinggroupandtable = databaseHelper.getpositionbyfindinggroupandtable(getGroupID);
                        if(getpositionbyfindinggroupandtable.getCount() == 0){
                           lastposition = 1;
                            Toast.makeText(GroupPage.this, "this is new table", Toast.LENGTH_SHORT).show();
                        }else {
                            while (getpositionbyfindinggroupandtable.moveToNext()) {
                                lastposition = getpositionbyfindinggroupandtable.getInt(0) + 1;
                                Toast.makeText(GroupPage.this, "new position: " + lastposition, Toast.LENGTH_SHORT).show();
                            }
                        }
                        //boolean success = true;
                        boolean success = databaseHelper.addTable(tablename, Integer.parseInt(getGroupID),lastposition);
                        if(success == true){
                            Toast.makeText(GroupPage.this,"success", Toast.LENGTH_SHORT).show();
                            addTable.hide();

                            //getting the table ID
                            Cursor gettingTableID = databaseHelper.selectLastTaskTable();
                            while (gettingTableID.moveToNext()){
//                                Toast.makeText(GroupPage.this,"thiryd balot" + gettingTableID.getString(0), Toast.LENGTH_SHORT).show();
                                tableid = gettingTableID.getString(0);
                            }
                           // gettingTableID.moveToNext();
                            Intent intent = new Intent(GroupPage.this, AddTask.class);
                            intent.putExtra("NEW_TABLE", "true");
                            intent.putExtra("GROUP_ID", getGroupID);
                            intent.putExtra("TABLE_ID", tableid);

                            Log.d("TAG", "lastposition in grouppage" +  lastposition);
                            startActivity(intent);
                        }else{
                            Toast.makeText(GroupPage.this,"failed", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        Toast.makeText(GroupPage.this,"checko" +  e.toString(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }

                }
            }
        });

        addTable.setContentView(view);

        addTables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTable.show();
            }
        });

        addfirsttaskbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTable.show();
            }
        });
    }

    private void menu() {
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });
    }

    private void displayMode() {
        displayMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(v);
            }
        });
    }

    //display mode
    //including grid view and list view

    void showPopupWindow(View view) {
        PopupMenu popup = new PopupMenu(GroupPage.this, view);
        try {
            Field[] fields = popup.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popup);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        popup.getMenuInflater().inflate(R.menu.displaymode, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.listlayout){
                    Toast.makeText(GroupPage.this,"List View",Toast.LENGTH_SHORT).show();
                }else if (id == R.id.gridlayout){
                    Toast.makeText(GroupPage.this,"Grid View",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        popup.show();
    }


    //menu popup in grouppage including view your task, group details ,
    //archived and delete
    void showMenu(View view) {
        PopupMenu popup = new PopupMenu(GroupPage.this, view);
        try {
            Field[] fields = popup.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popup);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        popup.getMenuInflater().inflate(R.menu.grouppagemenu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.groupdetails){
                    Toast.makeText(GroupPage.this,"Group Details",Toast.LENGTH_SHORT).show();
                }else if (id == R.id.events){
                    Toast.makeText(GroupPage.this,"Not Available",Toast.LENGTH_SHORT).show();
                }else if (id == R.id.delete){
                    Toast.makeText(GroupPage.this,"Not Permitted",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        popup.show();
    }

    //set up table dta for recycler view
    private void setupTodo(){
        RelativeLayout recycleContainer = findViewById(R.id.recycleContainer);
        RelativeLayout touchme = findViewById(R.id.touchme);
        ImageView arrow = findViewById(R.id.arrow);
        touchme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to hide and
                if(todovisible == true){
                    recycleContainer.setVisibility(View.GONE);
                    todovisible = false;
                    arrow.setVisibility(View.VISIBLE);
                }else{
                    recycleContainer.setVisibility(View.VISIBLE);
                    todovisible = true;
                    arrow.setVisibility(View.GONE);
                }
            }
        });



        try{
            //TODO ilipat to sa global variable
            String currentIdforTable = tableModels.get(tableposition).getGroupId();
            Cursor getTaskTableforGroupPage = databaseHelper.getTaskTableforGroupPage(getGroupID,currentIdforTable,"TO DO");

            ArrayList<Integer> TaskId = new ArrayList<Integer>();
            ArrayList<Integer> TableId = new ArrayList<Integer>();
            ArrayList<String> NameofTask = new ArrayList<String>();
            ArrayList<String> DueDate = new ArrayList<String>();
            ArrayList<String> partipant_photo = new ArrayList<String>();
            if(getTaskTableforGroupPage.getCount()==0){
                firstGroup.setVisibility(View.GONE);
            }else{
                while(getTaskTableforGroupPage.moveToNext()){
                    firstGroup.setVisibility(View.VISIBLE);
                    TaskId.add(getTaskTableforGroupPage.getInt(0));
                    TableId.add(getTaskTableforGroupPage.getInt(2));
                    NameofTask.add(getTaskTableforGroupPage.getString(4));
                    DueDate.add(getTaskTableforGroupPage.getString(7) + " " + getTaskTableforGroupPage.getString(8) );
                    partipant_photo.add(getTaskTableforGroupPage.getString(3) );
                }

                for(int i = 0; i< NameofTask.size(); i++){
                    todoModels.add(new TodoModel(TaskId.get(i),TableId.get(i),NameofTask.get(i),DueDate.get(i),partipant_photo.get(i) ));
                }
            }
        }catch(Exception e){
            Log.d("TAG", "set up todo model " + e );
        }

        ImageButton addtask = findViewById(R.id.addtask);
        addtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentIdforTable = tableModels.get(tableposition).getGroupId();
                Intent intent = new Intent(GroupPage.this, AddTask.class);
                intent.putExtra("GROUP_ID", getGroupID);
                intent.putExtra("TABLE_ID", currentIdforTable);
                intent.putExtra("POSITION", 1);
                intent.putExtra("NEW_TABLE", "false");
                startActivity(intent);
            }
        });
    }



    private void setupWorking() {
        RelativeLayout recycleContainer = findViewById(R.id.recycleContainer2);
        RelativeLayout touchme = findViewById(R.id.touchme2);
        ImageView arrow = findViewById(R.id.arrow1);
        touchme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to hide and
                if(workingvisible == true){
                    recycleContainer.setVisibility(View.GONE);
                    workingvisible = false;
                    arrow.setVisibility(View.VISIBLE);
                }else{
                    recycleContainer.setVisibility(View.VISIBLE);
                    workingvisible = true;
                    arrow.setVisibility(View.GONE);
                }
            }
        });

        try{
            //TODO ilipat to sa global variable
            String currentIdforTable = tableModels.get(tableposition).getGroupId();
            Cursor getTaskTableforGroupPage = databaseHelper.getTaskTableforGroupPage(getGroupID,currentIdforTable,"Working");

            ArrayList<String> TaskId = new ArrayList<String>();
            ArrayList<String> TableId = new ArrayList<String>();
            ArrayList<String> NameofTask = new ArrayList<String>();
            ArrayList<String> DueDate = new ArrayList<String>();
            ArrayList<String> partipant_photo = new ArrayList<String>();
            if(getTaskTableforGroupPage.getCount() == 0){
                secondGroup.setVisibility(View.GONE);
            }else {
                while (getTaskTableforGroupPage.moveToNext()) {
                    secondGroup.setVisibility(View.VISIBLE);
                    TaskId.add(getTaskTableforGroupPage.getString(0));
                    TableId.add(getTaskTableforGroupPage.getString(2));
                    NameofTask.add(getTaskTableforGroupPage.getString(4));
                    DueDate.add(getTaskTableforGroupPage.getString(7) + " " + getTaskTableforGroupPage.getString(8));
                    partipant_photo.add(getTaskTableforGroupPage.getString(3));
                }

                for (int i = 0; i < NameofTask.size(); i++) {
                    workingModels.add(new WorkingModel(Integer.parseInt(TaskId.get(i)), Integer.parseInt(TableId.get(i)), NameofTask.get(i), DueDate.get(i), partipant_photo.get(i)));
//                workingModels.add(new WorkingModel(partipant_photo.get(i)));
                }
            }
        }catch(Exception e){
            Log.d("TAG", "set up working eroor " + e );
        }
    }

    private void setupDone() {
        RelativeLayout recycleContainer = findViewById(R.id.recycleContainer3);
        RelativeLayout touchme = findViewById(R.id.touchme3);
        ImageView arrow = findViewById(R.id.arrow2);
        touchme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to hide and
                if(donevisible == true){
                    recycleContainer.setVisibility(View.GONE);
                    donevisible = false;
                    arrow.setVisibility(View.VISIBLE);
                }else{
                    recycleContainer.setVisibility(View.VISIBLE);
                    donevisible = true;
                    arrow.setVisibility(View.GONE);
                }
            }
        });

        try{
            //TODO ilipat to sa global variable
            String currentIdforTable = tableModels.get(tableposition).getGroupId();
            Cursor getTaskTableforGroupPage = databaseHelper.getTaskTableforGroupPage(getGroupID,currentIdforTable,"Done");

            ArrayList<String> TaskId = new ArrayList<String>();
            ArrayList<String> TableId = new ArrayList<String>();
            ArrayList<String> NameofTask = new ArrayList<String>();
            ArrayList<String> DueDate = new ArrayList<String>();
            ArrayList<String> partipant_photo = new ArrayList<String>();
            if(getTaskTableforGroupPage.getCount() == 0){
                thirdgroup.setVisibility(View.GONE);
            }else {
                while (getTaskTableforGroupPage.moveToNext()) {
                    thirdgroup.setVisibility(View.VISIBLE);
                    TaskId.add(getTaskTableforGroupPage.getString(0));
                    TableId.add(getTaskTableforGroupPage.getString(2));
                    NameofTask.add(getTaskTableforGroupPage.getString(4));
                    DueDate.add(getTaskTableforGroupPage.getString(7) + " " + getTaskTableforGroupPage.getString(8));
                    partipant_photo.add(getTaskTableforGroupPage.getString(3));
                }

                for (int i = 0; i < NameofTask.size(); i++) {
                    doneModels.add(new DoneModel(Integer.parseInt(TaskId.get(i)), Integer.parseInt(TableId.get(i)), NameofTask.get(i), DueDate.get(i), partipant_photo.get(i)));
                }
            }
        }catch(Exception e){
            Log.d("TAG", "set up working eroor " + e );
        }
    }




    private void setupReady() {
        RelativeLayout recycleContainer = findViewById(R.id.recycleContainer4);
        RelativeLayout touchme = findViewById(R.id.touchme4);
        ImageView arrow = findViewById(R.id.arrow3);
        touchme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to hide and
                if(readyvisible == true){
                    recycleContainer.setVisibility(View.GONE);
                    readyvisible = false;
                    arrow.setVisibility(View.VISIBLE);
                }else{
                    recycleContainer.setVisibility(View.VISIBLE);
                    readyvisible = true;
                    arrow.setVisibility(View.GONE);
                }
            }
        });

        try{
            //TODO ilipat to sa global variable
            String currentIdforTable = tableModels.get(tableposition).getGroupId();
            Cursor getTaskTableforGroupPage = databaseHelper.getTaskTableforGroupPage(getGroupID,currentIdforTable,"Ready");

            ArrayList<String> TaskId = new ArrayList<String>();
            ArrayList<String> TableId = new ArrayList<String>();
            ArrayList<String> NameofTask = new ArrayList<String>();
            ArrayList<String> DueDate = new ArrayList<String>();
            ArrayList<String> partipant_photo = new ArrayList<String>();
            if(getTaskTableforGroupPage.getCount() == 0){
                fourthgroup.setVisibility(View.GONE);
            }else {
                while (getTaskTableforGroupPage.moveToNext()) {
                    fourthgroup.setVisibility(View.VISIBLE);
                    TaskId.add(getTaskTableforGroupPage.getString(0));
                    TableId.add(getTaskTableforGroupPage.getString(2));
                    NameofTask.add(getTaskTableforGroupPage.getString(4));
                    DueDate.add(getTaskTableforGroupPage.getString(7) + " " + getTaskTableforGroupPage.getString(8));
                    partipant_photo.add(getTaskTableforGroupPage.getString(3));
                }

                for (int i = 0; i < NameofTask.size(); i++) {
                    readyModels.add(new ReadyModel(Integer.parseInt(TaskId.get(i)), Integer.parseInt(TableId.get(i)), NameofTask.get(i), DueDate.get(i), partipant_photo.get(i)));
                }
            }
        }catch(Exception e){
            Log.d("TAG", "set up working eroor " + e );
        }


    }

    //set up table data for recycler view
    private void setupTable(){

        try{
            Cursor getAllTable = databaseHelper.getAllTable(getGroupID);

            if(getAllTable.getCount()==0){
                emptyContainer.setVisibility(View.VISIBLE);
            }

            ArrayList<String> tableName = new ArrayList<String>();
            ArrayList<String> tableid = new ArrayList<String>();

            while(getAllTable.moveToNext()){
                tableid.add(getAllTable.getString(0));
                tableName.add(getAllTable.getString(2));
            }

            for(int i = 0; i< tableid.size(); i++){
                tableModels.add(new TableModel(tableid.get(i),tableName.get(i)));
            }

        }catch(Exception e){
            Log.d("TAG", "set up table error " + e );
        }
    }

    //first Table
    private void setupGroupData() {

        try{
            //get All table from SQLITE
            Cursor getAllTable = databaseHelper.getAllTable(getGroupID);
            if(getAllTable.getCount() == 0){
                //TODO hide the recylerview and table

            }else{

                ArrayList<String> TableName = new ArrayList<String>();
                ArrayList<String> TableID = new ArrayList<String>();
                ArrayList<Integer> Position = new ArrayList<Integer>();
                while(getAllTable.moveToNext()){
                    TableID.add(getAllTable.getString(0));
                    TableName.add(getAllTable.getString(2));
                    Position.add(getAllTable.getInt(3));
                }
                for(int i=0; i<TableName.size(); i++){
                    groupPageParentModels.add(new GroupPageParentModel(TableID.get(i),TableName.get(i),Position.get(i)
                    ));
                }

            Toast.makeText(GroupPage.this, "click" + TableName.get(0), Toast.LENGTH_SHORT).show();


            }
        }catch(Exception e){
            emptyContainer.setVisibility(View.VISIBLE);
        }


//        groupPageModels.clear();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if(isFinishing()){
            if (addTable!= null) {
                addTable.dismiss();
                addTable= null;
            }
        }
    }

    @Override
    public void onItemClick(int position, String taskView) {
        switch (taskView){
            case "tableApdater":

                Log.d("TAG", "table position: " + position);
                Log.d("TAG", "table id: " + tableModels.get(position).getGroupId());
                tableposition = position;
                workingModels.clear();
                todoModels.clear();
                readyModels.clear();
                doneModels.clear();
                setupTodo();
                setupWorking();
                setupDone();
                setupReady();

                workingAdapter.notifyDataSetChanged();
                todoadapter.notifyDataSetChanged();
                doneAdapter.notifyDataSetChanged();
                readyAdapter.notifyDataSetChanged();


                //to set the color of selected recycleview of table
                changecolor = position;
                tableadapter.notifyDataSetChanged();

                break;

            case "todo":
                Log.d("TAG", "table id: " + todoModels.get(position).getParticipant_src_photo());
                getGroupIDInt = Integer.parseInt(getGroupID);
                getTaskID = todoModels.get(position).getTaskId();
                getTableID = todoModels.get(position).getTableId();
                Intent addTaskIntent = new Intent(GroupPage.this, ViewTask.class);
                startActivity(addTaskIntent);


                break;

            case "working":
                getGroupIDInt = Integer.parseInt(getGroupID);
                getTaskID = workingModels.get(position).getTaskId();
                getTableID = workingModels.get(position).getTableId();
                Intent addTaskIntent2 = new Intent(GroupPage.this, ViewTask.class);
                startActivity(addTaskIntent2);
                break;

            case "ready":
                getGroupIDInt = Integer.parseInt(getGroupID);
                getTaskID = readyModels.get(position).getTaskId();
                getTableID = readyModels.get(position).getTableId();
                Intent addTaskIntent3 = new Intent(GroupPage.this, ViewTask.class);
                startActivity(addTaskIntent3);
                break;

            case "done":
                getGroupIDInt = Integer.parseInt(getGroupID);
                getTaskID = doneModels.get(position).getTaskId();
                getTableID = doneModels.get(position).getTableId();
                Intent addTaskIntent4= new Intent(GroupPage.this, ViewTask.class);
                startActivity(addTaskIntent4);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}