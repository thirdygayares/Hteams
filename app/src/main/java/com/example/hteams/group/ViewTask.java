package com.example.hteams.group;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.hteams.MainActivity;
import com.example.hteams.R;
import com.example.hteams.adapter.ViewTaskAdapter;
import com.example.hteams.adapter.ViewTaskInterface;
import com.example.hteams.model.ViewTaskModel;
import com.google.android.material.button.MaterialButton;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class ViewTask extends AppCompatActivity implements ViewTaskInterface {
    ArrayList<ViewTaskModel> viewTaskModels = new ArrayList<>();
    RecyclerView viewTask;
    Button postButton;
    ImageView menu_viewtask;
    Button button_status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        //initializion of id in xml
        initxml();

        //button for post update
        post();

        //for menu
        menu();

        //status button : dropdown the working,done, inready
        statusButton();


        //Set up posting data
        setUpPostingData();


    }



    //when you click post button
    private void post() {
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewTask.this, Updates.class);
                startActivity(intent);
            }
        });
    }


    private void menu() {
        menu_viewtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(v);
            }
        });
    }


    //method when user click view task menu
    void showPopupWindow(View view) {
        PopupMenu popup = new PopupMenu(ViewTask.this, view);
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
        popup.getMenuInflater().inflate(R.menu.viewtask, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.remind){
                    Toast.makeText(ViewTask.this,"Remind",Toast.LENGTH_SHORT).show();
                }else if (id == R.id.delete){
                    Toast.makeText(ViewTask.this,"Delete",Toast.LENGTH_SHORT).show();
                }else if (id == R.id.home){
                    Intent intent = new Intent(ViewTask.this, MainActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });
        popup.show();

    }



//    status button start
    private void statusButton() {
        button_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStatus(v);
            }
        });
    }

    //method when user click view status menu
    void showStatus(View view) {
        PopupMenu popup = new PopupMenu(ViewTask.this, view);
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
        popup.getMenuInflater().inflate(R.menu.status, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.todo){
//                    Toast.makeText(ViewTask.this,"Todo",Toast.LENGTH_SHORT).show();
                    button_status.setBackgroundColor(Color.BLACK);
                    button_status.setText("TODO");
                    button_status.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
                    button_status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_todo, 0, 0, 0);

                }else if (id == R.id.working){
                    button_status.setBackgroundColor(Color.parseColor("#3659D7"));
                    button_status.setText("WORKING");
                    button_status.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
                    button_status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_work_outline_24, 0, 0, 0);
                }else if (id == R.id.done){
                    button_status.setBackgroundColor(Color.parseColor("#3AAB28"));
                    button_status.setText("DONE");
                    button_status.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
                    button_status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_done_24, 0, 0, 0);
                }else if (id == R.id.ready){
                    button_status.setBackgroundColor(Color.parseColor("#73B9EC"));
                    button_status.setText("READY");
                    button_status.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
                    button_status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_ready, 0, 0, 0);
                }


                return false;
            }
        });
        popup.show();

    }





    //end when user click view task menu

    //initialization of xml
    private void initxml() {
        viewTask = (RecyclerView) findViewById(R.id.viewtaskposting);
        postButton = (Button) findViewById(R.id.postButton);
        menu_viewtask = (ImageView) findViewById(R.id.menu_viewtask);
        button_status = (Button) findViewById(R.id.button_status);
    }


    private void setUpPostingData() {

        //posting updates Recycler View
        ViewTaskAdapter adapter = new ViewTaskAdapter(ViewTask.this, viewTaskModels, this);
        viewTask.setAdapter(adapter);
        viewTask.setLayoutManager(new LinearLayoutManager(ViewTask.this));

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