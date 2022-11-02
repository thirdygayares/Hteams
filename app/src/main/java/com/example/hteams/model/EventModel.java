package com.example.hteams.model;

public class EventModel {

    int event_Actor;
    String event_Activity;
    String event_Date;

    public EventModel(int event_Actor, String event_Activity,String event_Date) {
        this.event_Actor = event_Actor;
        this.event_Activity = event_Activity;
        this.event_Date = event_Date;
    }

    public int getEvent_Actor() {
        return event_Actor;
    }

    public String getEvent_Activity() {
        return event_Activity;
    }

    public String getEvent_Date() {
        return event_Date;
    }
}
