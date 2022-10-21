package com.example.hteams.group;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.hteams.R;

import java.text.DateFormat;
import java.util.Calendar;


public class AddTask extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText date;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {


            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_task);
            initxml();

            date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Please note that use your package name here
                    com.example.hteams.group.DatePicker mDatePickerDialogFragment;
                    mDatePickerDialogFragment = new com.example.hteams.group.DatePicker();
                    mDatePickerDialogFragment.show(getSupportFragmentManager(), "DATE PICK");
                }
            });
        }







    private void initxml() {
        date =findViewById(R.id.date);
    }


    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(mCalendar.getTime());
        date.setText(selectedDate);
    }
}