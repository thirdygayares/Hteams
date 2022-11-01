package com.example.hteams.notification;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hteams.R;
import com.example.hteams.adapter.NotificationAdapter;
import com.example.hteams.adapter.NotificationInterface;
import com.example.hteams.adapter.ViewTaskAdapter;
import com.example.hteams.group.ViewTask;
import com.example.hteams.model.NotificationModel;

import java.util.ArrayList;

public class Notification extends Fragment implements NotificationInterface {
    View view;
    //global variable of recycler view of Notification
    RecyclerView notification_recyclerView;

    //Arraylist of Notication MOdel
    ArrayList<NotificationModel> notificationModels = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_notification, container, false);

        //initialization of xml
        initXml();

        //fill data in array or in firebase
        //TODO firebase manipulation
        notificationRecyclerView();

        return view;
    }

    private void notificationRecyclerView() {
        //calling adapter
        NotificationAdapter adapter = new NotificationAdapter(getContext(), notificationModels, this);
        notification_recyclerView.setAdapter(adapter);
        notification_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //array for notificarion photo
        ArrayList <Integer> Notification_Photo = new ArrayList<Integer>();
        //array for read or unread
        Notification_Photo.add(R.drawable.profile);
        Notification_Photo.add(R.drawable.novem);
        Notification_Photo.add(R.drawable.marielle);

       //status: read or unread
        ArrayList<Boolean> status = new ArrayList<>();
        status.add(true);
        status.add(false);
        status.add(true);

        //type of notication , remindes, join , etc
        ArrayList<String> type = new ArrayList<String>();
        type.add("remind");
        type.add("invite");
        type.add("like");

        //desription of notificaion
        ArrayList<String> contentNotification = new ArrayList<String>();
        contentNotification.add("Thirdy Gayares Remind you to finish the task in FireTera");
        contentNotification.add("Novem Lanaban was Invited you to join in FireTera");
        contentNotification.add("Marielle Zabala Rwas comment to your Updates");

        //date of updates
        ArrayList<String> date = new ArrayList<String>();
        date.add("Oct 22");
        date.add("Nov 1");
        date.add("Oct 31");

        //time of updates
        ArrayList<String> time = new ArrayList<String>();
        time.add("10:00PM");
        time.add("5:00PM");
        time.add("8:00AM");

        for (int i= 0; i<contentNotification.size(); i++){
            notificationModels.add(new NotificationModel(Notification_Photo.get(i),contentNotification.get(i),date.get(i), time.get(i),type.get(i),status.get(i)));
        }

    }

    //clicking of notification
    @Override
    public void onItemClick(int pos) {

    }

    //initialization of xml
    private void initXml() {
        notification_recyclerView = (RecyclerView) view.findViewById(R.id.notication_recycler);
    }


}