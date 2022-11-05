package com.example.hteams.group;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.hteams.R;
import com.example.hteams.adapter.AsigneeAdapter;
import com.example.hteams.adapter.ViewTaskInterface;
import com.example.hteams.database.DatabaseHelper;
import com.example.hteams.model.AssigneeModel;
import com.example.hteams.model.GroupPageModel;
import com.example.hteams.model.SQLITEADDTASKMODEL;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;


public class AddTask extends AppCompatActivity implements ViewTaskInterface,DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    //arraylist of assignee model
    ArrayList<AssigneeModel> assigneeModels = new ArrayList<>();
    ArrayList<GroupPageModel> groupPageModels = new ArrayList<>();
    //Global variable of dialog box
    AlertDialog.Builder alert;
    AlertDialog alertDialog;
    //global variable of AsigneeAdapter adapter
    AsigneeAdapter adapter1;

    //global variable in final date
    //TODO sa firebase kasama year ah
    String finalDate ="";
    String participantName ="";
    String taskName = "";
    String status = "TO DO";
    int classmatePhoto = R.drawable.groupavatar3;

    //GLobal variable for time
    int day, month, year, hour, minute;
    int myday, myMonth, myYear, myHour, myMinute;

    //pm or am
    static  String pmam = "am";

    Button date, participant,addTask;
    EditText input_task;


    //firebase Auth
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    //SQLITE DB
    DatabaseHelper databaseHelper;
    String currentId;
    String getGroupID;
    String getTableId;
    String newTable = "false";

    int position;

    //for getting the uid when clikc the choose participant
    String participantID;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        initxml();

        //to know the email and uid
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        //calling database sqlite
        databaseHelper = new DatabaseHelper(AddTask.this);

        //cyrrent id
        currentId = firebaseAuth.getCurrentUser().getUid();

        // set Group id
        getGroupID = String.valueOf(getIntent().getStringExtra("GROUP_ID"));

        //for testing
//        getGroupID = "1";

        getTableId = String.valueOf(getIntent().getStringExtra("TABLE_ID"));

        //check if dumaan sa new table
        newTable = String.valueOf(getIntent().getStringExtra("NEW_TABLE"));



        //fore testing
//        getTableId = "1";

//        Toast.makeText(AddTask.this, getGroupID + " " + getTableId, Toast.LENGTH_SHORT).show();




        //set adapter
        adapter1 = new AsigneeAdapter(AddTask.this, assigneeModels, this);
        //setup data for participants
        setupAssigne();

            date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar calendar = Calendar.getInstance();
                    year = calendar.get(Calendar.YEAR);
                    month = calendar.get(Calendar.MONTH);
                    day = calendar.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(AddTask.this, AddTask.this,year, month,day);

                    // set maximum date to be selected as today
                    datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                    datePickerDialog.show();
                }
            });

            //whem click the select a particpant
        participant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //remove incase the error
                alert = new AlertDialog.Builder(AddTask.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_assigned, null);
//                initialize cancel in xml
                TextView cancel = (TextView) mView.findViewById(R.id.cancel);
//                initialize recycler view in xml
                RecyclerView participant = (RecyclerView) mView.findViewById(R.id.recyler_assigned);
                //setting adapter and model
                //assigned Recycler View
                participant.setAdapter(adapter1);
                participant.setLayoutManager(new LinearLayoutManager(AddTask.this));
                alert.setView(mView);
                alertDialog = alert.create();
                //user can touch in outside
                alertDialog.setCanceledOnTouchOutside(true);
                //set cancel on dialog box
                cancel.setOnClickListener(view -> alertDialog.dismiss());
                alertDialog.show();
            }
        });


        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(input_task.length()==0){
                    input_task.setError("Required");
                }else{
                    taskName = input_task.getText().toString();
                    groupPageModels.add(new GroupPageModel(taskName,status,finalDate,classmatePhoto));

                    try{


                    SQLITEADDTASKMODEL sqliteaddtaskmodels = null;
                    //GET TIME AND DATE
                    //dateConverter(myMonth)) + " " + myday + ", " + timeConverter(myHour)
//                        public SQLITEADDTASKMODEL(int ID_GROUP, int ID_TABLE, String ID_STUDENTS, String TASK_NAME, String STATUS, String dueDate, String dueTime)
                    String duedate = dateConverter(myMonth) + " " + myday;
                    String dueTIme = String.valueOf(timeConverter(myHour));
                        //ganto logic niyan pag dumaan sa add task hindi magbabago posisyon niya
                        //pero pag dumaan sa add table magbabago position
                        Cursor getCounttable = databaseHelper.getCountAllTable(getGroupID);
                        getCounttable.moveToNext();
                        //Toast.makeText(AddTask.this, String.valueOf(getCounttable.getInt(0) + 1),Toast.LENGTH_SHORT).show();

                        if(newTable.equalsIgnoreCase("false")) {
                            // Toast.makeText(AddTask.this, "false", Toast.LENGTH_SHORT).show();
                            position = getCounttable.getInt(0);
                            Toast.makeText(AddTask.this, String.valueOf(getCounttable.getInt(0)), Toast.LENGTH_SHORT).show();

                        }else if(newTable.equalsIgnoreCase("true")){
                            //Toast.makeText(AddTask.this, "false", Toast.LENGTH_SHORT).show();
                            position = getCounttable.getInt(0) + 1;
                            Toast.makeText(AddTask.this, String.valueOf(getCounttable.getInt(0) + 1), Toast.LENGTH_SHORT).show();
                        }
                    sqliteaddtaskmodels = new SQLITEADDTASKMODEL(Integer.parseInt(getGroupID),Integer.parseInt(getTableId),participantID,taskName,status, duedate , dueTIme,position);
                   boolean success = databaseHelper.addTask(sqliteaddtaskmodels);
                        if(success == true){
                            Toast.makeText(AddTask.this, "success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddTask.this, GroupPage.class);
                            intent.putExtra("setGroupId", getGroupID);
                            startActivity(intent);

                        }else{
                            Toast.makeText(AddTask.this, "failed", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        Toast.makeText(AddTask.this, "eto ba yun" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
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
        TimePickerDialog timePickerDialog = new TimePickerDialog(AddTask.this, AddTask.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        myHour = hourOfDay;
        myMinute = minute;
        //TODO dapat same yung date sa local and firebase para maayos ang display
        //complete set para masave sa fireabase kahit di na view yung year masasave pa rin sa firevase
        //buttonDeadline.setText("Year: " + myYear + " " + "Month: " + myMonth + " " + "Day: " + myday + " " + "Hour: " + myHour + " " + "Minute: " + myMinute);

        finalDate = String.valueOf(dateConverter(myMonth)) + " " + myday + ", " + timeConverter(myHour) + ":" + minute + " " + pmam;

        date.setText(finalDate);

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



    //set up data for assignee
    //TODO firebase manipulation (UPDATE)
    private void setupAssigne() {
        //TODO: if the user is current leader it indicator or show you

        try {
                ArrayList<String> profilePhoto = new ArrayList<String>();
                ArrayList<String> classmateName = new ArrayList<String>();
                ArrayList<String> Id_Student = new ArrayList<String>();

                //Paano lumabas lang yung mga partcipant na nag accept lang
                //first get to the student ID by finding by getGroupID
                Cursor getOnlyParticipant = databaseHelper.getParticipant(getGroupID);
                while(getOnlyParticipant.moveToNext()){
                        Id_Student.add(getOnlyParticipant.getString(0));


                    //(2) get name and image of participant
                        Cursor getNameandIamge = databaseHelper.getNameImageParticipant(getOnlyParticipant.getString(0));
                            getNameandIamge.moveToNext();
                            profilePhoto.add(getNameandIamge.getString(0));
                            classmateName.add(getNameandIamge.getString(1));
                }
                for (int i = 0; i < classmateName.size(); i++) {
                        assigneeModels.add(new AssigneeModel(classmateName.get(i) , Id_Student.get(i),profilePhoto.get(i)));
                }
        }catch (Exception e){
            Toast.makeText(AddTask.this, e.toString(),Toast.LENGTH_SHORT ).show();
        }

    }


    private void initxml() {
        date = (Button) findViewById(R.id.date);
        participant = (Button) findViewById(R.id.participant);
        addTask  = (Button) findViewById(R.id.addTask);
        input_task = (EditText) findViewById(R.id.input_task);
    }


    @Override
    public void onItemClick(int pos, String assignee_adapter) {
        switch (assignee_adapter) {
            case "AssigneeAdapter":
                participant.setText(assigneeModels.get(pos).getName());
                participantName = assigneeModels.get(pos).getName();
                classmatePhoto = assigneeModels.get(pos).getImage();
                participantID = assigneeModels.get(pos).getSTUDENT_ID();


                // to set a photo
               // participant_photo.setImageResource(assigneeModels.get(position).getImage());
                alertDialog.dismiss();
                break;

        }
    }
}