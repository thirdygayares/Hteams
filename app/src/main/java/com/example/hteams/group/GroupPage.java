package com.example.hteams.group;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hteams.R;
import com.example.hteams.Testing.SetAvatar;
import com.example.hteams.Testing.SetProfile;
import com.example.hteams.adapter.GroupAdapter;
import com.example.hteams.adapter.GroupInterface;
import com.example.hteams.adapter.GroupPageAdapater;

import com.example.hteams.adapter.GroupPageAdapter2;
import com.example.hteams.adapter.GroupPageInterface;
import com.example.hteams.database.DatabaseHelper;
import com.example.hteams.model.GroupModel;
import com.example.hteams.model.GroupPageModel;
import com.example.hteams.model.GroupPageModel2;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class GroupPage extends AppCompatActivity implements GroupPageInterface {

    BottomSheetDialog addTable;

    ArrayList<GroupPageModel> groupPageModels = new ArrayList<>();
    ArrayList<GroupPageModel2> groupPageModels2 = new ArrayList<>();
    CardView Addtask2;
    CardView Addtask1;
    RelativeLayout displayMode,emptyContainer,firstGroup,secondGroup,addTables;
    TextView nameofgroup ;
    ImageView menu,logo;
    Button addfirsttaskbutton;


    //firebase Auth
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    //SQLITE DB
    DatabaseHelper databaseHelper;
    String currentId;
    String getGroupID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_page);


        //to know the email and uid
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        //calling database sqlite
        databaseHelper = new DatabaseHelper(GroupPage.this);

        //cyrrent id
        currentId = firebaseAuth.getCurrentUser().getUid();

        // set Group id
         getGroupID = String.valueOf(getIntent().getStringExtra("setGroupId"));

//        Toast.makeText(GroupPage.this, getGroupID, Toast.LENGTH_SHORT).show();


//        inititalization
        displayMode =  (RelativeLayout)findViewById(R.id.displayMode);
        Addtask2 = (CardView) findViewById(R.id.Addtask2);
        Addtask1 = (CardView)findViewById(R.id.Addtask1);
        menu= findViewById(R.id.menu);
        emptyContainer = (RelativeLayout)findViewById(R.id.emptyContainer);
        firstGroup = (RelativeLayout)findViewById(R.id.firstGroup);
        secondGroup = (RelativeLayout)findViewById(R.id.secondGroup);
//        adding button in first Task
        addfirsttaskbutton = (Button)findViewById(R.id.addfirsttaskbutton) ;
        logo  = (ImageView)findViewById(R.id.logo);
        //add table button
        addTables = (RelativeLayout)findViewById(R.id.addTable);
        nameofgroup = (TextView) findViewById(R.id.nameofgroup) ;

        //set group image and Name;
        Cursor getDisplayGroupandImage = databaseHelper.DisplayGroupDetails(getGroupID);
        try {getDisplayGroupandImage.moveToLast();
            String setGroupavatar = getDisplayGroupandImage.getString(0);
            String setGRoupName = getDisplayGroupandImage.getString(1);
            SetAvatar setAvatar = new SetAvatar();
            logo.setImageResource(setAvatar.setAvatar(setGroupavatar));
            nameofgroup.setText(setGRoupName);
        }
        catch(Exception e){
//            Toast.makeText(GroupPage.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
//        display choices [grid or list view]
        displayMode();
//      menu button
        menu();

        //method for clicking the add Table
        createTable();


        //first Recycler View
        RecyclerView recyclerView = findViewById(R.id.taskRecycler);
        GroupPageAdapater adapter = new GroupPageAdapater(GroupPage.this, groupPageModels, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(GroupPage.this));

        //fill data [frontend and backend work for 1st table
        setupGroupData();

        //second Recycler View
        RecyclerView recyclerView2 = findViewById(R.id.taskRecycler2);
        GroupPageAdapter2 adapter2 = new GroupPageAdapter2(GroupPage.this, groupPageModels2, this);
        recyclerView2.setAdapter(adapter2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(GroupPage.this));

        //fill data [frontend and backend work for 1st table
        setupGroupData2();

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

        //if empty the mnodel the expty container will show
        //pag bago palang yung wala pang task nangyayare.
        if(groupPageModels.isEmpty()){
            emptyContainer.setVisibility(View.VISIBLE);
            firstGroup.setVisibility(View.GONE);
            secondGroup.setVisibility(View.GONE);
        }else{
            emptyContainer.setVisibility(View.GONE);
            firstGroup.setVisibility(View.VISIBLE);
        }

        if(groupPageModels2.isEmpty()){
            secondGroup.setVisibility(View.GONE);
        }

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
                        boolean success = databaseHelper.addTable(tablename, Integer.parseInt(getGroupID));
                        if(success == true){
                            Toast.makeText(GroupPage.this,"success", Toast.LENGTH_SHORT).show();
                            addTable.hide();

                            //getting the table ID
                            Cursor gettingTableID = databaseHelper.selectLastTaskTable();
                            gettingTableID.moveToNext();
                            Intent intent = new Intent(GroupPage.this, AddTask.class);
                            intent.putExtra("GROUP_ID", getGroupID);
                            intent.putExtra("TABLE_ID", gettingTableID.getString(0));
                            startActivity(intent);


                        }else{
                            Toast.makeText(GroupPage.this,"failed", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        Toast.makeText(GroupPage.this, e.toString(), Toast.LENGTH_SHORT).show();
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
                }else if (id == R.id.viewyourtask){
                    Toast.makeText(GroupPage.this,"View Your Task",Toast.LENGTH_SHORT).show();
                }else if (id == R.id.groupmates){
                    Toast.makeText(GroupPage.this,"View Your Task",Toast.LENGTH_SHORT).show();
                }else if (id == R.id.events){
                    Toast.makeText(GroupPage.this,"View Your Task",Toast.LENGTH_SHORT).show();
                }else if (id == R.id.archive){
                    Toast.makeText(GroupPage.this,"View Your Task",Toast.LENGTH_SHORT).show();
                }else if (id == R.id.delete){
                    Toast.makeText(GroupPage.this,"View Your Task",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        popup.show();
    }

    //first Table
    private void setupGroupData() {
        //dummy data
//        String[] Title = {"Placeholder 1","PlaceHolder2","PlaceHolder 3","PlaceHolder 4","PlaceHolder 5","PlaceHolder 6"};
//        String[] Status = {"Working on It", "Done", "TODO", "TODO", "Ready", "Working on It"};
//        String[] Deadline = {"Oct 5 , 10:00 am", "Oct 5 , 10:00 am", "Oct 5 , 10:00 am", "Oct 5 , 10:00 am", "Oct 5 , 10:00 am", "Oct 5 , 10:00 am" };

        ArrayList<String> Title = new ArrayList<String>();
        Title.add("User Requirements");
        Title.add("Use Case Diagram");
        Title.add("ERD Diagram");

        ArrayList<String> Status = new ArrayList<String>();
        Status.add("Done");
        Status.add("Ready");
        Status.add("Working on It");

        ArrayList<String> Deadline = new ArrayList<String>();
        Deadline.add("Oct 10, 10:00am");
        Deadline.add("Nov 2, 8:00pm");
        Deadline.add("Dec 9, 12:00am");

        ArrayList<Integer> ProfilePhoto = new ArrayList<Integer>();
        ProfilePhoto.add(R.drawable.profile);
        ProfilePhoto.add(R.drawable.marielle);
        ProfilePhoto.add(R.drawable.novem);


        for(int i=0; i<Title.size(); i++){
            groupPageModels.add(new GroupPageModel(Title.get(i),Status.get(i),Deadline.get(i),ProfilePhoto.get(i)
            ));
        }
//        groupPageModels.clear();
    }

    private void setupGroupData2() {

        ArrayList<String> Title = new ArrayList<String>();
        Title.add("User Updates");
//        Title.add("Flow Chart");
//        Title.add("ERD Diagram");

        ArrayList<String> Status = new ArrayList<String>();
        Status.add("Done");
//        Status.add("Ready");
//        Status.add("Working on It");

        ArrayList<String> Deadline = new ArrayList<String>();
        Deadline.add("Oct 10, 10:00am");
//        Deadline.add("Nov 2, 8:00pm");
//        Deadline.add("Dec 9, 12:00am");

        ArrayList<Integer> ProfilePhoto = new ArrayList<Integer>();
//        ProfilePhoto.add(R.drawable.profile);
        ProfilePhoto.add(R.drawable.marielle);
        ProfilePhoto.add(R.drawable.novem);

        for(int i=0; i<Title.size(); i++){
            groupPageModels2.add(new GroupPageModel2(Title.get(i),Status.get(i),Deadline.get(i),ProfilePhoto.get(i)
            ));
//            groupPageModels2.clear();
        }
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(GroupPage.this, "click", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(GroupPage.this, ViewTask.class);
//     wwwwwwent.putExtra("salesId", historyDataModels.get(position).getSalesID());
//    w   intent.putExtra("Total", historyDataModels.get(position).getTotalPrice());
        startActivity(intent);
    }}