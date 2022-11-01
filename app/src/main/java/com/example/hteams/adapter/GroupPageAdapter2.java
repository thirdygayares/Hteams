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
        holder.participant_photo.setImageResource(grouppagemodels.get(position).getParticipant_photo());

        //check if working on it, pending, ready , done
        String status_indicatior = holder.status.getText().toString();
        if(status_indicatior.equalsIgnoreCase("done")){
            holder.status.setTextColor(Color.parseColor("#3AAB28"));
            //changing to done icon
            holder.iconstatus.setImageResource(R.drawable.ic_baseline_done_24);
            holder.iconstatus.setColorFilter(new PorterDuffColorFilter(0xFF3AAB28, PorterDuff.Mode.MULTIPLY));
        }else if(status_indicatior.equalsIgnoreCase("working on it")){
            holder.status.setTextColor(Color.parseColor("#3659D7"));
            //changing to working icon
            holder.iconstatus.setImageResource(R.drawable.ic_baseline_work_outline_24);
            holder.iconstatus.setColorFilter(new PorterDuffColorFilter(0xFF3659D7,PorterDuff.Mode.MULTIPLY));
        }else if(status_indicatior.equalsIgnoreCase("todo")){
            holder.status.setTextColor(Color.BLACK);
        }else if(status_indicatior.equalsIgnoreCase("Ready")){
            holder.status.setTextColor(Color.parseColor("#73B9EC"));
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

        public MyViewHolder(@NonNull View itemView, GroupPageInterface grouppageinterfaces) {
            super(itemView);
            taskName = (TextView) itemView.findViewById(R.id.taskName);
            status = (TextView) itemView.findViewById(R.id.status);
            duedate = (TextView) itemView.findViewById(R.id.duedate);
            participant_photo = (ImageView) itemView.findViewById(R.id.participant_photo);
            iconstatus = (ImageView) itemView.findViewById(R.id.iconstatus);




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
