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
import com.example.hteams.model.ViewTaskModel;

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
        holder.filesCount.setText("With " + viewTaskModels.get(position).getFilescount() + " files");
        holder.likeCount.setText(String.valueOf(viewTaskModels.get(position).getLikecount()));
        holder.dislikecounts.setText(String.valueOf(viewTaskModels.get(position).getDislikecount()));
        holder.profilephoto.setImageResource(viewTaskModels.get(position).getProfilepicture());

    }

    @Override
    public int getItemCount() {

        return viewTaskModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, date, viewCount, commentCount, description, filesCount, likeCount, dislikecounts;
        ImageView profilephoto;


        public MyViewHolder(@NonNull View itemView, ViewTaskInterface viewTaskInterfaces) {
            super(itemView);
            name = itemView.findViewById(R.id.participant_name);
            date = itemView.findViewById(R.id.date_post);
            viewCount = itemView.findViewById(R.id.viewcount);
            commentCount = itemView.findViewById(R.id.commentcount);

            description = itemView.findViewById(R.id.description);
            filesCount = itemView.findViewById(R.id.files_count);
            likeCount = itemView.findViewById(R.id.buttonLikes);
            dislikecounts = itemView.findViewById(R.id.buttondislike);
            profilephoto = itemView.findViewById(R.id.participant_photo);

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
