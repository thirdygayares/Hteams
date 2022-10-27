package com.example.hteams.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.hteams.R;
import com.example.hteams.model.SiteModel;

import java.util.ArrayList;

public class SiteAdapter extends RecyclerView.Adapter<SiteAdapter.MyViewHolder> {

    private final SiteInterface siteaInterfaces;


    Context context;
    ArrayList<SiteModel> siteModels;

    public SiteAdapter(Context context, ArrayList<SiteModel> siteModels, SiteInterface siteaInterfaces){
        this.context = context;
        this.siteModels = siteModels;
        this.siteaInterfaces = siteaInterfaces;
    }

    @NonNull
    @Override
    public SiteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType  ) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_group, parent, false);

        return new SiteAdapter.MyViewHolder(view, siteaInterfaces);
    }

    @Override
    public void onBindViewHolder(@NonNull SiteAdapter.MyViewHolder holder, int position) {
        holder.groupName.setText(siteModels.get(position).getGroupTitle());
        holder.group_description.setText(siteModels.get(position).getShortDescription());
        holder.teacher.setText(siteModels.get(position).getProfessor());
        holder.subject.setText(siteModels.get(position).getSubject());


    }

    @Override
    public int getItemCount() {

        return siteModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView groupName, group_description, teacher, subject;

        public MyViewHolder(@NonNull View itemView, SiteInterface SiteInterface) {
            super(itemView);
            groupName = itemView.findViewById(R.id.groupName);
            group_description = itemView.findViewById(R.id.group_description);
            teacher = itemView.findViewById(R.id.teacher);
            subject = itemView.findViewById(R.id.subject);



            itemView.setOnClickListener(view -> {
                if(siteaInterfaces != null ){
                    int pos = getAdapterPosition();
                    if(pos!= RecyclerView.NO_POSITION){
                        siteaInterfaces.onItemClick(pos);
                    }

                }
            });
        }
    }