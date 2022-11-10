package com.example.hteams.group;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.hteams.Display.groupDetails;
import com.example.hteams.MainActivity;
import com.example.hteams.R;
import com.example.hteams.Testing.SetProfile;
import com.example.hteams.adapter.AsigneeAdapter;
import com.example.hteams.adapter.GroupPageAdapater;
import com.example.hteams.adapter.ViewTaskAdapter;
import com.example.hteams.adapter.ViewTaskInterface;
import com.example.hteams.adapter.ViewUpdateInterface;
import com.example.hteams.database.DatabaseHelper;
import com.example.hteams.model.AssigneeModel;
import com.example.hteams.model.FirebaseModel.FirebaseRetriveGroup;
import com.example.hteams.model.ViewTaskModel;
import com.example.hteams.model.taskModel.TodoModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
    String getGroupID;
    String getTaskID ;
    String getTableID;

    //countiing indicator
    int imagecount ;
    String date;
    public static int updatesId;

    //pm or am
    static  String pmam = "am";

    ProgressDialog progressDialog;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        //process dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching data...");
        progressDialog.show();


        //to know the email and uid
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        //calling database sqlite
        databaseHelper = new DatabaseHelper(ViewTask.this);

        //cyrrent id
        currentId = firebaseAuth.getCurrentUser().getUid();

        //creatng object to get the value of Group Id, table Id, and task ID
        GroupPage groupPage = new GroupPage();

        //comment ko muna for testing

        // set Group id
        getGroupID = groupPage.getGroupIDInt;
        //Log.d("View",getGroupID);
        //set Task ID
        getTaskID =  groupPage.getTaskID;
        //Log.d("View",getTaskID);
        //set Table ID
        getTableID =  groupPage.getTableID;
        //Log.d("View", "TABLE ID: " + getTableID);

        //initializion of id in xml
        initxml();

        //header
        //toretrieve the name of the group, task name, and what is the table of thos
        header();

        //change assignedtomstatus,and deadline base on database
        TaskRetrievesData();


        //button for post update
        post();
        //for menu
        menu();
        //status button : dropdown the working,done, inready
        statusButton();
        //Set up posting data
        //setUpPostingData();
        adapter1 = new AsigneeAdapter(ViewTask.this, assigneeModels, this);
        //button_asignee lalabas yung dialog box when click assignee to but only leader make this
        buttonAssign();
        //setUpDatafor assignee
        //TODO FIREBASE MANIPULATION
        setupAssigne();
        //TODO deadline
        deadlineCalendar();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void TaskRetrievesData() {
        firestore.collection("task").document(getTaskID)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                                Log.e("TAG", error.getMessage());
                        }

                        String status_indicatior = value.get("status").toString();
                        if(status_indicatior.equalsIgnoreCase("done")){
                            button_status.setBackgroundColor(Color.parseColor("#3AAB28"));
                            button_status.setText("DONE");
                            button_status.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
                            button_status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_done_24, 0, 0, 0);
                        }
                        else if(status_indicatior.equalsIgnoreCase("working")){
                            button_status.setBackgroundColor(Color.parseColor("#3659D7"));
                            button_status.setText("WORKING");
                            button_status.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
                            button_status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_work_outline_24, 0, 0, 0);
                        }
                        else if(status_indicatior.equalsIgnoreCase("TO DO")){
                            button_status.setBackgroundColor(Color.BLACK);
                            button_status.setText("TO DO");
                            button_status.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
                            button_status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_todo, 0, 0, 0);

                        }
                        else if(status_indicatior.equalsIgnoreCase("Ready")){
                            button_status.setBackgroundColor(Color.parseColor("#FF9500"));
                            button_status.setText("READY");
                            button_status.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
                            button_status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_ready, 0, 0, 0);
                        }

                        buttonDeadline.setText(value.get("dueDate").toString() + " "  + value.get("dueTime").toString());

                        firestore.collection("students").document(value.get("id_STUDENTS").toString())
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()){
                                            DocumentSnapshot document=task.getResult();
                                            if(document.exists()){
                                                button_asignee.setText( document.get("Name").toString());
                                                Picasso.get().load(document.get("image").toString()).error(R.drawable.ic_profile).into(participant_photo);

                                                if (progressDialog.isShowing())
                                                    progressDialog.dismiss();
                                            }
                                            else{
                                                Log.d("TAG","Nosuchdocument");
                                            }
                                        }

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });
                    }

                });


       // Cursor getTaskName = databaseHelper.getTaskName(getTaskID);

//        while(getTaskName.moveToNext()){
//
//
//
//            groupDetails groupDetail = new groupDetails();
//            button_asignee.setText(groupDetail.partcipantName(ViewTask.this,getTaskName.getString(3)));
//            button_status.setText(getTaskName.getString(5));
//            //condition
//            String status_indicatior = getTaskName.getString(5);
//            if(status_indicatior.equalsIgnoreCase("done")){
//                button_status.setBackgroundColor(Color.parseColor("#3AAB28"));
//                button_status.setText("DONE");
//                button_status.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
//                button_status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_done_24, 0, 0, 0);
//            }
//            else if(status_indicatior.equalsIgnoreCase("working")){
//                button_status.setBackgroundColor(Color.parseColor("#3659D7"));
//                button_status.setText("WORKING");
//                button_status.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
//                button_status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_work_outline_24, 0, 0, 0);
//            }
//            else if(status_indicatior.equalsIgnoreCase("to do")){
//                button_status.setBackgroundColor(Color.BLACK);
//                button_status.setText("TO DO");
//                button_status.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
//                button_status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_todo, 0, 0, 0);
//
//            }
//            else if(status_indicatior.equalsIgnoreCase("Ready")){
//                button_status.setBackgroundColor(Color.parseColor("#FF9500"));
//                button_status.setText("READY");
//                button_status.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
//                button_status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_ready, 0, 0, 0);
//            }
//
//            SetProfile setProfile = new SetProfile();
//            participant_photo.setImageResource(setProfile.profileImage(groupDetail.participantImage(ViewTask.this,getTaskName.getString(3))));
//            buttonDeadline.setText(getTaskName.getString(7) + " " + getTaskName.getString(8));
//
//        }


    }


    private void header() {

        //getting groupname
        firestore.collection("groups")
                .document(getGroupID).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                groupName.setText(document.get("GROUPNAME").toString());
                            } else {
                                Log.d("TAG", "Nosuchdocument");
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

        //getting table name
        firestore.collection("table").document(getTableID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                tableName.setText(document.get("TABLE_NAME").toString());
                            } else {
                                Log.d("TAG", "Nosuchdocument");
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

        //getting task name
        firestore.collection("task").document(getTaskID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                taskName.setText(document.get("task_NAME").toString());
                            } else {
                                Log.d("TAG", "Nosuchdocument");
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });


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
                    Toast.makeText(ViewTask.this,"Remind Succesfully",Toast.LENGTH_SHORT).show();
                }else if (id == R.id.delete){
                    Toast.makeText(ViewTask.this,"No Permission",Toast.LENGTH_SHORT).show();
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
                String status = null;
                if(id == R.id.todo){
//                    Toast.makeText(ViewTask.this,"Todo",Toast.LENGTH_SHORT).show();
                    button_status.setBackgroundColor(Color.BLACK);
                    button_status.setText("TO DO");
                    status = "TO DO";
                    button_status.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
                    button_status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_todo, 0, 0, 0);

                }else if (id == R.id.working){
                    status = "Working";
                    button_status.setBackgroundColor(Color.parseColor("#3659D7"));
                    button_status.setText("WORKING");
                    button_status.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
                    button_status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_work_outline_24, 0, 0, 0);
                }else if (id == R.id.done){
                    button_status.setBackgroundColor(Color.parseColor("#3AAB28"));
                    button_status.setText("DONE");
                    status = "Done";

                    button_status.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
                    button_status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_done_24, 0, 0, 0);
                }else if (id == R.id.ready){
                    button_status.setBackgroundColor(Color.parseColor("#FF9500"));
                    button_status.setText("READY");
                    status = "Ready";

                    button_status.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
                    button_status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_ready, 0, 0, 0);
                }

                Boolean update = databaseHelper.updateStatus(String.valueOf(getTaskID),status );


                return false;
            }
        });
        popup.show();
    }




    private void counting() {
        int imagecountfilecountlistcountlinkcount ;
        //imagecounting

        try {
            for(int i = 0;i<viewTaskModels.size();i++){
                Cursor getImageCount = databaseHelper.getImageCount(String.valueOf(viewTaskModels.get(i).getUpdatesId()));
                while(getImageCount.moveToNext()){
                    imagecount = getImageCount.getInt(0);
                    Log.d("TAG", "imagecount is  " + imagecount );

                }


            }

        }catch (Exception e){
            Log.e("TAG", "Something " + e);
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
        ArrayList<String> classmateName = new ArrayList<String>();
        ArrayList<String> students_id = new ArrayList<String>();


        //TODO getting the participant from a group
        try{
            Cursor getParticipant =  databaseHelper.getParticipant(String.valueOf(getGroupID));
            groupDetails groupDetail = new groupDetails();
            SetProfile setProfile = new SetProfile();

            while (getParticipant.moveToNext()){
                String studentsId =      getParticipant.getString(0);
                profilePhoto.add(groupDetail.participantImage(ViewTask.this,studentsId));
                classmateName.add(groupDetail.partcipantName(ViewTask.this,studentsId));
              students_id.add(getParticipant.getString(0));
            }

        }catch (Exception e){
            Log.d("TAG",  e.toString());
        }

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
        myday = dayOfMonth;
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

        String EditTime = timeConverter(myHour) + ":" + minute + " " + pmam;

        Boolean update = databaseHelper.updateDue(String.valueOf(getTaskID),MonthCobnvert + " " + myday, EditTime );


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
                Boolean update = databaseHelper.updateParticipant(String.valueOf(getTaskID), String.valueOf(assigneeModels.get(position).getSTUDENT_ID()));
                if(update == true){
                    Log.d("TAG", "Change Successfully");
                }else{
                    Log.d("TAG", "Error Changing Participant");
                }
                alertDialog.dismiss();
                break;

            case "ViewTaskAdapter":
                intent = new Intent(ViewTask.this, ViewUpdates.class);
                startActivity(intent);
                updatesId = viewTaskModels.get(position).getUpdatesId();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}