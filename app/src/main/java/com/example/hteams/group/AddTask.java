package com.example.hteams.group;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.util.Log;
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
import com.example.hteams.model.FirebaseModel.FirebaseAddTAsk;
import com.example.hteams.model.GroupPageModel;
import com.example.hteams.model.SQLITEADDTASKMODEL;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;

public class AddTask extends AppCompatActivity implements ViewTaskInterface, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    //arraylist of assignee model
    ArrayList < AssigneeModel > assigneeModels = new ArrayList < > ();
    ArrayList < GroupPageModel > groupPageModels = new ArrayList < > ();
    //Global variable of dialog box
    AlertDialog.Builder alert;
    AlertDialog alertDialog;
    //global variable of AsigneeAdapter adapter
    AsigneeAdapter adapter1;

    //global variable in final date
    //TODO sa firebase kasama year ah
    String finalDate = "";
    String participantName = "";
    String taskName = "";
    String status = "TO DO";
    int classmatePhoto = R.drawable.groupavatar3;

    //GLobal variable for time
    int day, month, year, hour, minute;
    int myday, myMonth, myYear, myHour, myMinute;

    //pm or am
    static String pmam = "am";

    Button date, participant, addTask;
    EditText input_task;

    //firebase Auth
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    //SQLITE DB
    DatabaseHelper databaseHelper;
    String currentId;
    String getGroupID;
    String getTableId;


    //for getting the uid when clikc the choose participant
    String participantID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        initxml();

        firebaseAuth = FirebaseAuth.getInstance();  //to know the email and uid
        firestore = FirebaseFirestore.getInstance();

        databaseHelper = new DatabaseHelper(AddTask.this);   //calling database sqlite

        currentId = firebaseAuth.getCurrentUser().getUid(); //cyrrent id

        Home home = new Home();   // set Group id
        getGroupID = home.GroupId;
        Log.d("TAG", "ADD TASK GROUP ID " + getGroupID);

        getTableId = String.valueOf(getIntent().getStringExtra("TABLE_ID"));
        Log.d("TAG", "ADD TASK Table ID " + getTableId);

        adapter1 = new AsigneeAdapter(AddTask.this, assigneeModels, this); //set adapter
        setupAssigne();//setup data for participants

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTask.this, AddTask.this, year, month, day);

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
                TextView cancel = (TextView) mView.findViewById(R.id.cancel);// initialize cancel in xml
                RecyclerView participant = (RecyclerView) mView.findViewById(R.id.recyler_assigned);  // initialize recycler view in xml
                //setting adapter and model
                //assigned Recycler View
                participant.setAdapter(adapter1);
                participant.setLayoutManager(new LinearLayoutManager(AddTask.this));
                alert.setView(mView);
                alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(true); //user can touch in outside
                cancel.setOnClickListener(view -> alertDialog.dismiss());   //set cancel on dialog box
                alertDialog.show();
            }
        });

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input_task.length() == 0) {
                    input_task.setError("Required");
                } else {
                    taskName = input_task.getText().toString();
                    groupPageModels.add(new GroupPageModel(taskName, status, finalDate, classmatePhoto));
                    try {
                        SQLITEADDTASKMODEL sqliteaddtaskmodels = null;

                        String duedate = dateConverter(myMonth) + " " + myday;   //GET TIME AND DATE
                        String dueTIme = timeConverter(myHour) + ":" + myMinute + " " + pmam;

                        FirebaseAddTAsk firebaseAddTAsk;
                        firebaseAddTAsk = new FirebaseAddTAsk(getGroupID, getTableId, participantID, taskName, status, duedate, dueTIme);


                        CollectionReference task = firestore.collection("task");

                        task.add(firebaseAddTAsk)
                                .addOnSuccessListener(new OnSuccessListener < DocumentReference > () {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Intent intent = new Intent(AddTask.this, GroupPage.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("TAG", e.toString());
                                    }
                                });

                    } catch (Exception e) {
                        //                        Toast.makeText(AddTask.this,  "etor: " + e, Toast.LENGTH_SHORT).show();
                        Log.d("TAG", e.toString());
                    }
                }
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

    static String dateConverter(int month) {
        String MonthConverter = "";
        if (month == 0) {
            MonthConverter = "Jan";
        } else if (month == 1) {
            MonthConverter = "Feb";
        } else if (month == 2) {
            MonthConverter = "Mar";
        } else if (month == 3) {
            MonthConverter = "Apr";
        } else if (month == 4) {
            MonthConverter = "May";
        } else if (month == 5) {
            MonthConverter = "Jun";
        } else if (month == 6) {
            MonthConverter = "Jul";
        } else if (month == 7) {
            MonthConverter = "Aug";
        } else if (month == 8) {
            MonthConverter = "Sep";
        } else if (month == 9) {
            MonthConverter = "Oct";
        } else if (month == 10) {
            MonthConverter = "Nov";
        } else if (month == 11) {
            MonthConverter = "Dec";
        }

        return MonthConverter;
    }

    static int timeConverter(int time) {
        if (time == 1) {
            time = 1;
            pmam = "am";
        } else if (time == 2) {
            time = 2;
            pmam = "am";
        } else if (time == 3) {
            time = 3;
            pmam = "am";
        } else if (time == 4) {
            time = 4;
            pmam = "am";
        } else if (time == 5) {
            time = 5;
            pmam = "am";
        } else if (time == 6) {
            time = 6;
            pmam = "am";
        } else if (time == 7) {
            time = 7;
            pmam = "am";
        } else if (time == 8) {
            time = 8;
            pmam = "am";
        } else if (time == 9) {
            time = 9;
            pmam = "am";
        } else if (time == 10) {
            time = 10;
            pmam = "am";
        } else if (time == 11) {
            time = 11;
            pmam = "am";
        } else if (time == 12) {
            time = 12;
            pmam = "pm";
        } else if (time == 13) {
            time = 1;
            pmam = "pm";
        } else if (time == 14) {
            time = 2;
            pmam = "pm";
        } else if (time == 15) {
            time = 3;
            pmam = "pm";
        } else if (time == 16) {
            time = 4;
            pmam = "pm";
        } else if (time == 17) {
            time = 5;
            pmam = "pm";
        } else if (time == 18) {
            time = 6;
            pmam = "pm";
        } else if (time == 19) {
            time = 7;
            pmam = "pm";
        } else if (time == 20) {
            time = 8;
            pmam = "pm";
        } else if (time == 21) {
            time = 9;
            pmam = "pm";
        } else if (time == 22) {
            time = 10;
            pmam = "pm";
        } else if (time == 23) {
            time = 11;
            pmam = "pm";
        } else if (time == 0) {
            time = 12;
            pmam = "am";
        } else {
            pmam = "error";
        }

        return time;
    }

    //set up data for assignee
    //TODO firebase manipulation (UPDATE)
    private void setupAssigne() {
        //TODO: if the user is current leader it indicator or show you

        try {

            //Paano lumabas lang yung mga partcipant na nag accept lang
            //first get to the student ID by finding by getGroupID

            CollectionReference getParticipant = firestore.collection("participant");
            getParticipant
                    .whereEqualTo("GROUPID", getGroupID)
                    //                    .whereEqualTo("Accepted",true)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener < QuerySnapshot > () {
                        @Override
                        public void onComplete(@NonNull Task < QuerySnapshot > task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document: task.getResult()) {
                                    String participantid = document.get("StudentID").toString(); //para masave yung id niya at yun ang masave
                                    Log.d("TAG", "participants " + document.get("StudentID").toString());
                                    DocumentReference getNameAndImage = firestore.collection("students").document(document.get("StudentID").toString());
                                    getNameAndImage
                                            .addSnapshotListener(new EventListener < DocumentSnapshot > () {
                                                @Override
                                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                                    if (error != null) {
                                                        Log.d("TAG", "error retrieving files " + error.getMessage());
                                                    }
                                                    assigneeModels.add(new AssigneeModel(value.get("Name").toString(), participantid, value.get("image").toString()));
                                                    adapter1.notifyDataSetChanged();
                                                }
                                            });
                                }
                            } else {
                                Log.d("TAG", "participants error");
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("TAG", "participants " + e);
                        }
                    });
        } catch (Exception e) {
            Toast.makeText(AddTask.this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    private void initxml() {
        date = (Button) findViewById(R.id.date);
        participant = (Button) findViewById(R.id.participant);
        addTask = (Button) findViewById(R.id.addTask);
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

                Log.d("TAG", "PARTIIPANT ID " + participantID);

                // to set a photo
                // participant_photo.setImageResource(assigneeModels.get(position).getImage());
                alertDialog.dismiss();
                break;

        }
    }
}