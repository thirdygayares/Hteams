package com.example.hteams.group;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class GroupPage extends AppCompatActivity implements GroupPageInterface {

    ArrayList<GroupPageModel> groupPageModels = new ArrayList<>();
    ArrayList<GroupPageModel2> groupPageModels2 = new ArrayList<>();
    TextView Addtask2;
    CardView Addtask1;
    RelativeLayout displayMode;
    ImageView menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_page);



//        inititalization
        displayMode =  findViewById(R.id.displayMode);
        Addtask2 = findViewById(R.id.Addtask2);
        Addtask1 = findViewById(R.id.Addtask1);
        menu= findViewById(R.id.menu);

//        display choices [grid or list view]
        displayMode();
//      menu button
        menu();

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

        Intent intent = new Intent(GroupPage.this, ViewTask.class);
//     wwwwwwent.putExtra("salesId", historyDataModels.get(position).getSalesID());
//    w   intent.putExtra("Total", historyDataModels.get(position).getTotalPrice());
        startActivity(intent);
    }}