package com.example.hteams.Display;


import android.content.Context;
import android.database.Cursor;

import com.example.hteams.database.DatabaseHelper;

public class groupDetails {
    DatabaseHelper databaseHelper;
    public int image(String image){

        int x=0 ;
        return x;
    }

    public String groupname(String name){
        String x= "0" ;
        return x;
    }

    public String partcipantName(Context context,String id){
        databaseHelper = new DatabaseHelper(context);
        String participant = "";

        Cursor getName = databaseHelper.getNameImageParticipant(id);
        while(getName.moveToNext()){
            participant = getName.getString(1);
        }
        return participant;
    }

    public String participantImage(Context context,String id){
        databaseHelper = new DatabaseHelper(context);
        String image = "";
        Cursor getName = databaseHelper.getNameImageParticipant(id);
        while(getName.moveToNext()){
            image = getName.getString(0);
        }
        return image;
    }


}
