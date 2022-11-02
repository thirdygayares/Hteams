package com.example.hteams.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hteams.R;
import com.example.hteams.model.EventModel;

import java.util.ArrayList;

public class EventAdapter  extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {

    private final EventInterface eventInterfaces ;
    Context context;
    ArrayList<EventModel> eventModels;

    public EventAdapter(Context context, ArrayList<EventModel> eventModels, EventInterface eventInterfaces){
        this.context = context;
        this.eventModels = eventModels;
        this.eventInterfaces = eventInterfaces;
    }


    @NonNull
    @Override
    public EventAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_events, parent, false);
        return new EventAdapter.MyViewHolder(view, eventInterfaces);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.MyViewHolder holder, int position) {
        holder.event_actor.setImageResource(eventModels.get(position).getEvent_Actor());
        holder.event_activity.setText(eventModels.get(position).getEvent_Activity());
        holder.event_date.setText(eventModels.get(position).getEvent_Date());
    }

    @Override
    public int getItemCount() {
        return eventModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView event_actor;
        TextView event_activity;
        TextView event_date;

        public MyViewHolder(@NonNull View itemView, EventInterface eventInterfaces) {
            super(itemView);
            event_actor = itemView.findViewById(R.id.user_avatar);
            event_activity = itemView.findViewById(R.id.status);
            event_date = itemView.findViewById(R.id.date_time_rec);

            itemView.setOnClickListener(view -> {
                if(eventInterfaces != null ){
                    int pos = getAdapterPosition();
                    if(pos!= RecyclerView.NO_POSITION){
                        eventInterfaces.onItemClick(pos);
                    }

                }
            });



        }
    }
}
