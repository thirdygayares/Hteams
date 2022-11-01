package com.example.hteams.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.hteams.R;
import com.example.hteams.group.GroupPage;
import com.example.hteams.model.NotificationModel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private final NotificationInterface notificationInterfaces;


    Context context;
    ArrayList<NotificationModel> notificationModels;

    public NotificationAdapter(Context context, ArrayList<NotificationModel> notificationModels, NotificationInterface notificationInterfaces){
        this.context = context;
        this.notificationModels = notificationModels;
        this.notificationInterfaces = notificationInterfaces;
    }

    @NonNull
    @Override
    public NotificationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType  ) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_notification, parent, false);

        return new NotificationAdapter.MyViewHolder(view, notificationInterfaces);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.MyViewHolder holder, int position) {


        //check if read or unread
        Boolean status = notificationModels.get(position).getStatus();
        if(status == true){
            holder.background.setBackgroundColor(Color.WHITE);
        }else {
            holder.background.setBackgroundResource(R.color.grey3);

        }

        holder.notificationPhoto.setImageResource(notificationModels.get(position).getNotificationPhoto());
        holder.content.setText(notificationModels.get(position).getContent());
        holder.date.setText(notificationModels.get(position).getDate());
        holder.time.setText(notificationModels.get(position).getTime());

        //if the user click the menu
        holder.notificationmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(v);
            }
        });

    }


    //method when user click notification menu
    void showPopupWindow(View view) {
        PopupMenu popup = new PopupMenu(context.getApplicationContext(), view);
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
        popup.getMenuInflater().inflate(R.menu.notification, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.mark){
                    Toast.makeText(context.getApplicationContext(),"Mark",Toast.LENGTH_SHORT).show();
                }else if (id == R.id.delete){
                    Toast.makeText(context.getApplicationContext(),"Delete",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        popup.show();

    }

    @Override
    public int getItemCount() {

        return notificationModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout background;
        TextView content, date, time;
        ImageView notificationPhoto,notificationmenu;

        public MyViewHolder(@NonNull View itemView, NotificationInterface notificationInterfaces) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.notification_description);
            date = (TextView)itemView.findViewById(R.id.date);
            time = (TextView)itemView.findViewById(R.id.time);
            notificationPhoto = (ImageView) itemView.findViewById(R.id.participant_photo);
            background = (RelativeLayout) itemView.findViewById(R.id.background);
            notificationmenu = (ImageView) itemView.findViewById(R.id.notificationmenu);

            itemView.setOnClickListener(view -> {
                if(notificationInterfaces != null ){
                    int pos = getAdapterPosition();
                    if(pos!= RecyclerView.NO_POSITION){
                        notificationInterfaces.onItemClick(pos);
                    }

                }
            });
        }
    }
}
