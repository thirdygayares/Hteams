package com.example.hteams.adapter;



import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.example.hteams.R;
import com.example.hteams.Testing.SetProfile;
import com.example.hteams.group.GroupPage;
import com.example.hteams.model.GroupPageModel;

import java.util.ArrayList;

public class GroupPageChildAdapter extends RecyclerView.Adapter<GroupPageChildAdapter.MyViewHolder>  {

    GroupInterface groupInterface;

    ArrayList<GroupPageModel> grouppagemodels;

    public GroupPageChildAdapter(ArrayList<GroupPageModel> grouppagemodels, GroupInterface groupInterface){
        this.grouppagemodels = grouppagemodels;
        this.groupInterface = groupInterface;
    }

    @NonNull
    @Override
    public GroupPageChildAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType  ) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_group1,parent,false);
        return new GroupPageChildAdapter.MyViewHolder(view, groupInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupPageChildAdapter.MyViewHolder holder, int position) {

        GroupPageModel groupPage = grouppagemodels.get(position);
//        Log.d("TAG", "The size om group possitionn" + position);
        holder.taskName.setText(grouppagemodels.get(position).getNameofTask());
        holder.status.setText(groupPage.getStatus());
        holder.duedate.setText(groupPage.getDueDate() + " " );

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

//        holder.touchchild.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("TAG", "you touch me as a gift " + holder.getAdapterPosition());
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return grouppagemodels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView taskName, status, duedate;
        ImageView participant_photo,iconstatus;
        ConstraintLayout touchchild;
        public MyViewHolder(@NonNull View itemView,GroupInterface groupInterface) {
            super(itemView);
            taskName = (TextView) itemView.findViewById(R.id.taskName);
            status = (TextView) itemView.findViewById(R.id.status);
            duedate = (TextView) itemView.findViewById(R.id.duedate);
            participant_photo = (ImageView) itemView.findViewById(R.id.participant_photo);
            iconstatus = (ImageView) itemView.findViewById(R.id.iconstatus);
            touchchild = (ConstraintLayout) itemView.findViewById(R.id.touchchild);

            itemView.setOnClickListener(view -> {
                if(groupInterface != null ){
                    int pos = getAdapterPosition();
                    if(pos!= RecyclerView.NO_POSITION){
                        groupInterface.onItemClick(pos, "taskView");
                    }
                }
            });
        }
    }


}
