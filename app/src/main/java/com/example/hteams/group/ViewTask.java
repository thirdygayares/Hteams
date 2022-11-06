package com.example.hteams.group;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.hteams.MainActivity;
import com.example.hteams.R;
import com.example.hteams.Testing.SetProfile;
import com.example.hteams.adapter.AsigneeAdapter;
import com.example.hteams.adapter.GroupPageAdapater;
import com.example.hteams.adapter.ViewTaskAdapter;
import com.example.hteams.adapter.ViewTaskInterface;
import com.example.hteams.database.DatabaseHelper;
import com.example.hteams.model.AssigneeModel;
import com.example.hteams.model.ViewTaskModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;

public class ViewTask extends AppCompatActivity implements ViewTaskInterface,DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener  {
    //array list of view task model
    ArrayList<ViewTaskModel> viewTaskModels = new ArrayList<>();
    //arraylist of assignee model
    ArrayList<AssigneeModel> assigneeModels = new ArrayList<>();
    //Global variable of dialog box
    AlertDialog.Builder alert;
    AlertDialog alertDialog;
    //global variable of AsigneeAdapter adapter
    AsigneeAdapter adapter1;

    RecyclerView viewTask;
    Button postButton,button_asignee;
    ImageView menu_viewtask,participant_photo;
    Button button_status, buttonDeadline;
    TextView taskName,groupName,tableName;

    //GLobal variable for time
    int day, month, year, hour, minute;
    int myday, myMonth, myYear, myHour, myMinute;

    //firebase Auth
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    //SQLITE DB
    DatabaseHelper databaseHelper;
    String currentId;
    int getGroupID;
    int getTaskID;
    int getTableID;

    //pm or am
    static  String pmam = "am";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        //to know the email and uid
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        //calling database sqlite
        databaseHelper = new DatabaseHelper(ViewTask.this);

        //cyrrent id
        currentId = firebaseAuth.getCurrentUser().getUid();

        //creatng object to get the value of Group Id, table Id, and task ID
        GroupPageAdapater groupPageAdapater = new GroupPageAdapater();

        // set Group id
        getGroupID = groupPageAdapater.getIntGroupID;
        //set Task ID
        getTaskID =  groupPageAdapater.getTaskID;
        //set Table ID
        getTableID =  groupPageAdapater.getTableID;

        //initializion of id in xml
        initxml();

        //header
        //toretrieve the name of the group, task name, and what is the table of thos
        header();


        //button for post update
        post();
        //for menu
        menu();
        //status button : dropdown the working,done, inready
        statusButton();
        //Set up posting data
        setUpPostingData();

        adapter1 = new AsigneeAdapter(ViewTask.this, assigneeModels, this);
        //button_asignee lalabas yung dialog box when click assignee to but only leader make this
        buttonAssign();
        //setUpDatafor assignee
        //TODO FIREBASE MANIPULATION
        setupAssigne();
        //TODO deadline
        deadlineCalendar();
    }


    private void header() {
//        Log.d("TAG", "task id in header" + getTaskID);
        Cursor getTaskName = databaseHelper.getTaskName(getTaskID);
        try{
            if(getTaskName.getCount() == 0){
//                Log.d("TAG", getTaskName.getString(4));
                Log.d("TAG", "walang laman");
            }
            while(getTaskName.moveToNext()){
            taskName.setText(getTaskName.getString(4));
                Log.d("TAG", getTaskName.getString(4));
            }

        }catch (Exception e){
            Log.d("TAG", "getting taskid in View Task" + e );
        }

        //getting group name from sqlite
        Cursor getGroupName = databaseHelper.myGroup(String.valueOf(getGroupID));
        try{
            if(getGroupName.getCount() == 0){
                Log.d("TAG", "walang laman");
            }
            while(getGroupName.moveToNext()){
                groupName.setText(getGroupName.getString(2));
            }

        }catch (Exception e){
            Log.d("TAG", "getting groupid in View Task" + e );
        }


        //getting table name from sqlite
        Cursor getTableName = databaseHelper.getTableName(getTableID);
        Log.d("TAG", "table id in view task" + getTableID);
        try{
            if(getTableName.getCount() == 0){
                Log.d("TAG", "walang laman");
            }
            while(getTableName.moveToNext()){
                tableName.setText(getTableName.getString(2));
            }

        }catch (Exception e){
            Log.d("TAG", "getting groupid in View Task" + e );
        }

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

    //menu in view task
//    TODO 1. Remind Participant
//    TODO 2. delete task
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
                    button_status.setText("TO DO");
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
                    button_status.setBackgroundColor(Color.parseColor("#FF9500"));
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

//    set up data for post or updates
//    TODO firebase manipulation
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

    //method when click assignee button to assign a group members
    private void buttonAssign() {
        button_asignee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert = new AlertDialog.Builder(ViewTask.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_assigned, null);

//                initialize cancel in xml
                TextView cancel = (TextView) mView.findViewById(R.id.cancel);
//                initialize recycler view in xml
                RecyclerView participant = (RecyclerView) mView.findViewById(R.id.recyler_assigned);
                //setting adapter and model
                //assigned Recycler View
                participant.setAdapter(adapter1);
                participant.setLayoutManager(new LinearLayoutManager(ViewTask.this));




                alert.setView(mView);
                alertDialog = alert.create();
                //user can touch in outside
                alertDialog.setCanceledOnTouchOutside(true);
                //set cancel on dialog box
                cancel.setOnClickListener(view -> alertDialog.dismiss());
                alertDialog.show();
            }
        });
    }

    //set up data for assignee
    //TODO firebase manipulation (UPDATE)
    private void setupAssigne() {
        //                TODO: if the user is current leader it indicator or show you
        ArrayList<String> profilePhoto = new ArrayList<String>();
        profilePhoto.add("acds_gayares");
        profilePhoto.add("acds_zabala");
        profilePhoto.add("acds_lanaban");

        ArrayList<String> classmateName = new ArrayList<String>();
        classmateName.add("Thirdy Gayares");
        classmateName.add("Marielle Zabala");
        classmateName.add("Novem Lanaban");

        ArrayList<String> students_id = new ArrayList<String>();
        students_id.add("T");
        students_id.add("Marielle Zabala");
        students_id.add("Novem Lanaban");


         for(int i=0; i<classmateName.size();i++){
            assigneeModels.add(new AssigneeModel(classmateName.get(i), students_id.get(i), profilePhoto.get(i)));
        }

    }

    //due date
    //TODO firebase manipulation (UPDATE)
    private void deadlineCalendar() {
        buttonDeadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(ViewTask.this, ViewTask.this,year, month,day);

                // set maximum date to be selected as today
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        myYear = year;
        myday = day;
        myMonth = month;
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(ViewTask.this, ViewTask.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        myHour = hourOfDay;
        myMinute = minute;
        //TODO dapat same yung date sa local and firebase para maayos ang display
        //complete set para masave sa fireabase kahit di na view yung year masasave pa rin sa firevase
        //buttonDeadline.setText("Year: " + myYear + " " + "Month: " + myMonth + " " + "Day: " + myday + " " + "Hour: " + myHour + " " + "Minute: " + myMinute);

        String MonthCobnvert = String.valueOf(dateConverter(myMonth));

        buttonDeadline.setText( MonthCobnvert + " " + myday + ", " + timeConverter(myHour) + ":" + minute + " " + pmam );

    }

   static String dateConverter(int month){
        String MonthConverter = "";
        if(month == 0){
            MonthConverter = "Jan";
       }else if (month == 1) {
            MonthConverter = "Feb";
        }else if (month == 2) {
            MonthConverter = "Mar";
        }else if (month == 3) {
            MonthConverter = "Apr";
        }else if (month == 4) {
            MonthConverter = "May";
        }else if (month == 5) {
            MonthConverter = "Jun";
        }else if (month == 6) {
            MonthConverter = "Jul";
        }else if (month == 7) {
            MonthConverter = "Aug";
        }else if (month == 8) {
            MonthConverter = "Sep";
        }else if (month == 9) {
            MonthConverter = "Oct";
        }else if (month == 10) {
            MonthConverter = "Nov";
        }else if (month == 11) {
            MonthConverter = "Dec";
        }

       return MonthConverter;
   }


   static int timeConverter(int time){
       if(time == 1){
           time = 1;
           pmam = "am";
       }else if(time == 2){
           time = 2;
           pmam = "am";
       }else if(time == 3){
           time = 3;
           pmam = "am";
       }else if(time == 4){
           time = 4;
           pmam = "am";
       }else if(time == 5){
           time = 5;
           pmam = "am";
       }else if(time == 6){
           time = 6;
           pmam = "am";
       }else if(time == 7){
           time = 7;
           pmam = "am";
       }else if(time == 8){
           time = 8;
           pmam = "am";
       }else if(time == 9){
           time = 9;
           pmam = "am";
       }else if(time == 10){
           time = 10;
           pmam = "am";
       }else if(time == 11){
           time = 11;
           pmam = "am";
       }else if(time == 12){
           time = 12;
           pmam = "pm";
       }else if(time == 13){
           time = 1;
           pmam = "pm";
       }else if(time == 14){
           time = 2;
           pmam = "pm";
       }else if(time == 15){
           time = 3;
           pmam = "pm";
       }else if(time == 16){
           time = 4;
           pmam = "pm";
       }else if(time == 17){
           time = 5;
           pmam = "pm";
       }else if(time == 18){
           time = 6;
           pmam = "pm";
       }else if(time == 19){
           time = 7;
           pmam = "pm";
       }else if(time == 20){
           time = 8;
           pmam = "pm";
       }else if(time == 21){
           time = 9;
           pmam = "pm";
       }else if(time == 22){
           time = 10;
           pmam = "pm";
       }else if(time == 23){
           time = 11;
           pmam = "pm";
       }else if(time == 0){
           time = 12;
           pmam = "am";
       }else{
           pmam = "error";
       }



       return time;
   }



    //clicking the recycler view
    @Override
    public void onItemClick(int position, String assignee_adapter) {
        Intent intent;
        switch (assignee_adapter){
            case "AssigneeAdapter":
                button_asignee.setText(assigneeModels.get(position).getName());
                SetProfile setProfiles = new SetProfile();
                participant_photo.setImageResource(setProfiles.profileImage(assigneeModels.get(position).getImgsrc()));
                alertDialog.dismiss();
                break;

            case "ViewTaskAdapter":
                intent = new Intent(ViewTask.this, ViewUpdates.class);
                startActivity(intent);
                break;
            default:
                Toast.makeText(ViewTask.this, "default",Toast.LENGTH_SHORT).show();
        }

    }

    //initialization of xml
    private void initxml() {
        viewTask = (RecyclerView) findViewById(R.id.viewtaskposting);
        postButton = (Button) findViewById(R.id.postButton);
        menu_viewtask = (ImageView) findViewById(R.id.menu_viewtask);
        participant_photo =  (ImageView) findViewById(R.id.participant_photo);
        button_status = (Button) findViewById(R.id.button_status);
        button_asignee = (Button) findViewById(R.id.button_asignee);
        buttonDeadline = (Button) findViewById(R.id.buttonDeadline);
        taskName = (TextView) findViewById(R.id.taskName);
        groupName = (TextView) findViewById(R.id.groupName);
        tableName = (TextView) findViewById(R.id.tableName);
    }



}