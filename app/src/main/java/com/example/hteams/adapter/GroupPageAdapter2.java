package com.example.hteams.adapter;



import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.hteams.R;
import com.example.hteams.model.GroupPageModel2;

import java.util.ArrayList;

public class GroupPageAdapter2 extends RecyclerView.Adapter<GroupPageAdapter2.MyViewHolder> {

    private final GroupPageInterface grouppageinterfaces;


    Context context;
    ArrayList<GroupPageModel2> grouppagemodels;

    public GroupPageAdapter2(Context context, ArrayList<GroupPageModel2> grouppagemodels, GroupPageInterface grouppageinterfaces){
        this.context = context;
        this.grouppagemodels = grouppagemodels;
        this.grouppageinterfaces = grouppageinterfaces;
    }

    @NonNull
    @Override
    public GroupPageAdapter2.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType  ) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_group1, parent, false);

        return new GroupPageAdapter2.MyViewHolder(view, grouppageinterfaces);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupPageAdapter2.MyViewHolder holder, int position) {
        holder.taskName.setText(grouppagemodels.get(position).getNameofTask());
        holder.status.setText(grouppagemodels.get(position).getStatus());
        holder.duedate.setText(grouppagemodels.get(position).getDueDate());

        if(holder.status.getText().toString().equalsIgnoreCase("done")){
            holder.status.setTextColor(Color.GREEN);
        }else if(holder.status.getText().toString().equalsIgnoreCase("pending")){
            holder.status.setTextColor(Color.RED);
        }

    }

    @Override
    public int getItemCount() {

        return grouppagemodels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView taskName, status, duedate;

        public MyViewHolder(@NonNull View itemView, GroupPageInterface grouppageinterfaces) {
            super(itemView);
            taskName = itemView.findViewById(R.id.taskName);
            status = itemView.findViewById(R.id.status);
            duedate = itemView.findViewById(R.id.duedate);




            itemView.setOnClickListener(view -> {
                if(grouppageinterfaces != null ){
                    int pos = getAdapterPosition();
                    if(pos!= RecyclerView.NO_POSITION){
                        grouppageinterfaces.onItemClick(pos);
                    }

                }
            });
        }
    }


}
