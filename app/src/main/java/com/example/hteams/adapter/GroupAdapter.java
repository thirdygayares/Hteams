package com.example.hteams.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.hteams.R;
import com.example.hteams.model.GroupModel;

import java.util.ArrayList;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.MyViewHolder> {

    private final GroupInterface groupInterface;


    Context context;
    ArrayList<GroupModel> groupmodels;

    public GroupAdapter(Context context, ArrayList<GroupModel> groupmodels, GroupInterface groupInterface){
        this.context = context;
        this.groupmodels = groupmodels;
        this.groupInterface = groupInterface;
    }

    @NonNull
    @Override
    public GroupAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType  ) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_group, parent, false);

        return new GroupAdapter.MyViewHolder(view, groupInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupAdapter.MyViewHolder holder, int position) {
        holder.groupName.setText(groupmodels.get(position).getGroupTitle());
        holder.group_description.setText(groupmodels.get(position).getShortDescription());
        holder.teacher.setText(groupmodels.get(position).getProfessor());
        holder.subject.setText(groupmodels.get(position).getSubject());


    }

    @Override
    public int getItemCount() {

        return groupmodels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView groupName, group_description, teacher, subject;

        public MyViewHolder(@NonNull View itemView, GroupInterface groupInterface) {
            super(itemView);
            groupName = itemView.findViewById(R.id.groupName);
            group_description = itemView.findViewById(R.id.group_description);
            teacher = itemView.findViewById(R.id.teacher);
            subject = itemView.findViewById(R.id.subject);



            itemView.setOnClickListener(view -> {
                if(groupInterface != null ){
                    int pos = getAdapterPosition();
                    if(pos!= RecyclerView.NO_POSITION){
                        groupInterface.onItemClick(pos);
                    }

                }
            });
        }
    }


}
