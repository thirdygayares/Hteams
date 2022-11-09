package com.example.hteams.adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.hteams.R;
import com.example.hteams.Testing.SetProfile;
import com.example.hteams.database.DatabaseHelper;
import com.example.hteams.model.ViewTaskModel;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class ViewTaskAdapter extends RecyclerView.Adapter<ViewTaskAdapter.MyViewHolder> {

    private final ViewTaskInterface viewTaskInterfaces;


    Context context;
    ArrayList<ViewTaskModel> viewTaskModels;

    public ViewTaskAdapter(Context context, ArrayList<ViewTaskModel> viewTaskModels, ViewTaskInterface viewTaskInterfaces){
        this.context = context;
        this.viewTaskModels = viewTaskModels;
        this.viewTaskInterfaces = viewTaskInterfaces;
    }

    @NonNull
    @Override
    public ViewTaskAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType  ) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_task, parent, false);

        return new ViewTaskAdapter.MyViewHolder(view, viewTaskInterfaces);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewTaskAdapter.MyViewHolder holder, int position) {
        holder.name.setText(viewTaskModels.get(position).getParticipantName());
        holder.date.setText(viewTaskModels.get(position).getPostdate());
        holder.viewCount.setText(String.valueOf(viewTaskModels.get(position).getViewcount()));
        holder.description.setText(viewTaskModels.get(position).getDescription());
        holder.likeCount.setText(String.valueOf(viewTaskModels.get(position).getLikecount()));
        holder.dislikecounts.setText(String.valueOf(viewTaskModels.get(position).getDislikecount()));

        SetProfile setProfile = new SetProfile();
        holder.profilephoto.setImageResource(setProfile.profileImage(viewTaskModels.get(position).getImageSource()));

        holder.filesCount.setText(String.valueOf(viewTaskModels.get(position).getFilescount()));

        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        //when the link is 0
        String currentUpdateID = String.valueOf(viewTaskModels.get(position).getUpdatesId());
        Cursor getlinkdata = databaseHelper.getLinkCount(currentUpdateID);
            while(getlinkdata.moveToNext()){
                Log.d("TAG", getlinkdata.getString(0));
                holder.linkscount.setText(getlinkdata.getString(0));
            }
            //hide link cardview when 0 it is
            if(holder.linkscount.getText().toString().equalsIgnoreCase("0")){
                holder.link.setVisibility(View.GONE);
            }


        Cursor getListCount = databaseHelper.getListCount(currentUpdateID);
        while(getListCount.moveToNext()){
            Log.d("TAG", getListCount.getString(0));
            holder.checklistcount.setText(getListCount.getString(0));
        }
        //hide list cardview when 0 it is
        if(holder.checklistcount.getText().toString().equalsIgnoreCase("0")){
            holder.checklist.setVisibility(View.GONE);
        }

        Cursor getFilesCount = databaseHelper.getFilesCount(currentUpdateID);
        while(getFilesCount.moveToNext()){
            Log.d("TAG", getFilesCount.getString(0));
            holder.filesCount.setText(getFilesCount.getString(0));
        }
        //hide link files when 0 it is
        if(holder.filesCount.getText().toString().equalsIgnoreCase("0")){
            holder.files.setVisibility(View.GONE);
        }


        Cursor getImageCount = databaseHelper.getImageCount(currentUpdateID);
        while(getImageCount.moveToNext()){
            Log.d("TAG", getImageCount.getString(0));
            holder.photoscount.setText(getImageCount.getString(0));
        }
        //hide link files when 0 it is
        if(holder.photoscount.getText().toString().equalsIgnoreCase("0")){
            holder.photos.setVisibility(View.GONE);
        }




    }

    @Override
    public int getItemCount() {
        return viewTaskModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, date, viewCount, commentCount, description, filesCount, checklistcount, photoscount,linkscount, likeCount, dislikecounts;
        ImageView profilephoto;
        CardView checklist,files,photos,link;

        public MyViewHolder(@NonNull View itemView, ViewTaskInterface viewTaskInterfaces) {
            super(itemView);
            name = itemView.findViewById(R.id.participant_name);
            date = itemView.findViewById(R.id.date_post);
            viewCount = itemView.findViewById(R.id.viewcount);
            commentCount = itemView.findViewById(R.id.commentcount);
            description = itemView.findViewById(R.id.description);

            likeCount = itemView.findViewById(R.id.buttonLikes);
            dislikecounts = itemView.findViewById(R.id.buttondislike);
            profilephoto = itemView.findViewById(R.id.participant_photo);
            checklist = (MaterialCardView) itemView.findViewById(R.id.checklist);
            files = (MaterialCardView) itemView.findViewById(R.id.files);
            photos = (MaterialCardView) itemView.findViewById(R.id.photos);
            link = (MaterialCardView) itemView.findViewById(R.id.link);
            filesCount = (TextView) itemView.findViewById(R.id.files_countsss);
            checklistcount = (TextView) itemView.findViewById(R.id.checklistcount);
            photoscount = (TextView) itemView.findViewById(R.id.photoscount);
            linkscount = (TextView) itemView.findViewById(R.id.linkscount);



            itemView.setOnClickListener(view -> {
                if(viewTaskInterfaces != null ){
                    int pos = getAdapterPosition();
                    if(pos!= RecyclerView.NO_POSITION){
                        viewTaskInterfaces.onItemClick(pos, "ViewTaskAdapter");
                    }
                }
            });
        }
    }
}
