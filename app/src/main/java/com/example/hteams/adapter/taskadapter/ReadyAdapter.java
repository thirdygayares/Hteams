package com.example.hteams.adapter.taskadapter;


import android.content.Context;
import android.database.Cursor;
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
import com.example.hteams.adapter.GroupInterface;
import com.example.hteams.database.DatabaseHelper;
import com.example.hteams.group.GroupPage;
import com.example.hteams.model.taskModel.ReadyModel;
import java.util.ArrayList;

public class ReadyAdapter extends RecyclerView.Adapter<ReadyAdapter.MyViewHolder>  {
    Context context;
    DatabaseHelper databaseHelper;
    String imgsrc;
    GroupInterface groupInterface;
    ArrayList<ReadyModel> readyModels;

    public ReadyAdapter(Context context, ArrayList<ReadyModel> readyModels, GroupInterface groupInterface){
        this.context = context;
        this.readyModels = readyModels;
        this.groupInterface = groupInterface;
    }

    @NonNull
    @Override
    public ReadyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType  ) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_group1,parent,false);
        return new ReadyAdapter.MyViewHolder(view, groupInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ReadyAdapter.MyViewHolder holder, int position) {

        ReadyModel groupPage = readyModels.get(position);
//        Log.d("TAG", "The size om group possitionn" + position);
        holder.taskName.setText(readyModels.get(position).getNameofTask());
        holder.duedate.setText(groupPage.getDueDate() + " " );

//        change profile photoofuser
        databaseHelper = new DatabaseHelper(context);
        Cursor getImage = databaseHelper.getImageCurrentsUser(readyModels.get(position).getParticipant_src_photo());
        while(getImage.moveToNext()){
            imgsrc = getImage.getString(0);
        }
        SetProfile setProfiles = new SetProfile();
        holder.participant_photo.setImageResource(setProfiles.profileImage(imgsrc));

    }

    @Override
    public int getItemCount() {
        return readyModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView taskName, duedate;
        ImageView participant_photo,iconstatus;
        ConstraintLayout touchchild;
        public MyViewHolder(@NonNull View itemView,GroupInterface groupInterface) {
            super(itemView);
            taskName = (TextView) itemView.findViewById(R.id.taskName);
            duedate = (TextView) itemView.findViewById(R.id.duedate);
            participant_photo = (ImageView) itemView.findViewById(R.id.participant_photo);
            iconstatus = (ImageView) itemView.findViewById(R.id.iconstatus);
            touchchild = (ConstraintLayout) itemView.findViewById(R.id.touchchild);

            itemView.setOnClickListener(view -> {
                if(groupInterface != null ){
                    int pos = getAdapterPosition();
                    if(pos!= RecyclerView.NO_POSITION){
                        groupInterface.onItemClick(pos, "ready");
                    }
                }
            });
        }
    }


}
