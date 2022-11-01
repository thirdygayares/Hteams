package com.example.hteams.adapter;



import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.hteams.R;
import com.example.hteams.model.PersonalTaskModel;

import java.util.ArrayList;

public class PersonalTaskAdapter extends RecyclerView.Adapter<PersonalTaskAdapter.MyViewHolder> {

    private final PersonalTaskInterface personalTaskInterfaces;


    Context context;
    ArrayList<PersonalTaskModel> personalTaskModels;

    public PersonalTaskAdapter(Context context, ArrayList<PersonalTaskModel> personalTaskModels, PersonalTaskInterface personalTaskInterfaces){
        this.context = context;
        this.personalTaskModels = personalTaskModels;
        this.personalTaskInterfaces = personalTaskInterfaces;
    }

    @NonNull
    @Override
    public PersonalTaskAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType  ) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_personal_task, parent, false);

        return new PersonalTaskAdapter.MyViewHolder(view, personalTaskInterfaces);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonalTaskAdapter.MyViewHolder holder, int position) {
        holder.groupPhoto.setImageResource(personalTaskModels.get(position).getGroupImage());
        holder.groupName.setText(personalTaskModels.get(position).getGroupName());
        holder.taskName.setText(personalTaskModels.get(position).getNameofTask());
        holder.status.setText(personalTaskModels.get(position).getStatus());
        holder.duedate.setText(personalTaskModels.get(position).getDueDate());

        if(holder.status.getText().toString().equalsIgnoreCase("done")){
            holder.status.setTextColor(Color.GREEN);
        }else if(holder.status.getText().toString().equalsIgnoreCase("pending")){
            holder.status.setTextColor(Color.RED);
        }


    }

    @Override
    public int getItemCount() {

        return personalTaskModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView taskName, status, duedate,groupName;
        ImageView groupPhoto;

        public MyViewHolder(@NonNull View itemView, PersonalTaskInterface personalTaskInterfaces) {
            super(itemView);
            groupPhoto = (ImageView) itemView.findViewById(R.id.groupPhoto);
            groupName = (TextView) itemView.findViewById(R.id.groupname);
            taskName = (TextView) itemView.findViewById(R.id.taskName);
            status = (TextView) itemView.findViewById(R.id.status);
            duedate = (TextView) itemView.findViewById(R.id.duedate);


            itemView.setOnClickListener(view -> {
                if(personalTaskInterfaces != null ){
                    int pos = getAdapterPosition();
                    if(pos!= RecyclerView.NO_POSITION){
                        personalTaskInterfaces.onItemClick(pos);
                    }

                }
            });
        }
    }


}
