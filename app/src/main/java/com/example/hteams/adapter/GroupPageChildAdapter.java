package com.example.hteams.adapter;



import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.hteams.R;
import com.example.hteams.Testing.SetProfile;
import com.example.hteams.group.GroupPage;
import com.example.hteams.model.GroupPageModel;

import java.util.ArrayList;

public class GroupPageChildAdapter extends RecyclerView.Adapter<GroupPageChildAdapter.MyViewHolder> {

    GroupInterface groupInterface;

    ArrayList<GroupPageModel> grouppagemodels;

    public GroupPageChildAdapter(ArrayList<GroupPageModel> grouppagemodels){
        this.grouppagemodels = grouppagemodels;
    }

    @NonNull
    @Override
    public GroupPageChildAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType  ) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_group1,parent,false);
        return new GroupPageChildAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupPageChildAdapter.MyViewHolder holder, int position) {

        GroupPageModel groupPage = grouppagemodels.get(position);

        holder.taskName.setText(groupPage.getNameofTask());
        holder.status.setText(groupPage.getStatus());
        holder.duedate.setText(groupPage.getDueDate());

        SetProfile setProfiles = new SetProfile();
        holder.participant_photo.setImageResource(setProfiles.profileImage(grouppagemodels.get(position).getParticipant_src_photo()));

        //check if working on it, pending, ready , done
        String status_indicatior = holder.status.getText().toString();
        if(status_indicatior.equalsIgnoreCase("done")){
            holder.status.setTextColor(Color.parseColor("#3AAB28"));
            //changing to done icon
            holder.iconstatus.setImageResource(R.drawable.ic_baseline_done_24);
            holder.iconstatus.setColorFilter(new PorterDuffColorFilter(0xFF3AAB28, PorterDuff.Mode.MULTIPLY));
        }else if(status_indicatior.equalsIgnoreCase("working on it") || status_indicatior.equalsIgnoreCase("working") ){
            holder.status.setTextColor(Color.parseColor("#3659D7"));
            //changing to working icon
            holder.iconstatus.setImageResource(R.drawable.ic_baseline_work_outline_24);
            holder.iconstatus.setColorFilter(new PorterDuffColorFilter(0xFF3659D7,PorterDuff.Mode.MULTIPLY));
        }else if(status_indicatior.equalsIgnoreCase("to do")){
            holder.status.setTextColor(Color.BLACK);
        }else if(status_indicatior.equalsIgnoreCase("Ready")){
            holder.status.setTextColor(Color.parseColor("#FF9500"));
            //changing to ready icon
            holder.iconstatus.setImageResource(R.drawable.ic_ready);
            holder.iconstatus.setColorFilter(new PorterDuffColorFilter(0xFF73B9EC,PorterDuff.Mode.MULTIPLY));
        }
    }

    @Override
    public int getItemCount() {
        return grouppagemodels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView taskName, status, duedate;
        ImageView participant_photo,iconstatus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            taskName = (TextView) itemView.findViewById(R.id.taskName);
            status = (TextView) itemView.findViewById(R.id.status);
            duedate = (TextView) itemView.findViewById(R.id.duedate);
            participant_photo = (ImageView) itemView.findViewById(R.id.participant_photo);
            iconstatus = (ImageView) itemView.findViewById(R.id.iconstatus);



        }
    }


}
