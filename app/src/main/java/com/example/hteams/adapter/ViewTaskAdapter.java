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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewTaskAdapter extends RecyclerView.Adapter < ViewTaskAdapter.MyViewHolder > {

    private final ViewTaskInterface viewTaskInterfaces;

    Context context;
    ArrayList < ViewTaskModel > viewTaskModels;

    public ViewTaskAdapter(Context context, ArrayList < ViewTaskModel > viewTaskModels, ViewTaskInterface viewTaskInterfaces) {
        this.context = context;
        this.viewTaskModels = viewTaskModels;
        this.viewTaskInterfaces = viewTaskInterfaces;
    }

    @NonNull
    @Override
    public ViewTaskAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_task, parent, false);

        return new ViewTaskAdapter.MyViewHolder(view, viewTaskInterfaces);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewTaskAdapter.MyViewHolder holder, int position) {

        holder.date.setText(viewTaskModels.get(position).getPostdate());
        holder.viewCount.setText(String.valueOf(viewTaskModels.get(position).getViewcount()));
        holder.description.setText(viewTaskModels.get(position).getDescription());
        holder.likeCount.setText(String.valueOf(viewTaskModels.get(position).getLikecount()));
        holder.dislikecounts.setText(String.valueOf(viewTaskModels.get(position).getDislikecount()));

        FirebaseFirestore firestore;
        firestore = FirebaseFirestore.getInstance();
        firestore.collection("students").document(viewTaskModels.get(position).getImageSource())
                .get()
                .addOnCompleteListener(new OnCompleteListener < DocumentSnapshot > () {
                    @Override
                    public void onComplete(@NonNull Task < DocumentSnapshot > task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            Picasso.get().load(document.get("image").toString()).error(R.drawable.ic_profile).into(holder.profilephoto);
                            holder.name.setText(document.get("Name").toString());
                        }
                    }
                });

        holder.filesCount.setText(String.valueOf(viewTaskModels.get(position).getFilescount()));

        String currentUpdateID = String.valueOf(viewTaskModels.get(position).getUpdatesId());

        holder.linkscount.setText("2");

        CollectionReference countNumber = firestore.collection("links");
        Query query = countNumber.whereEqualTo("ID_UPDATES", viewTaskModels.get(position).getUpdatesId());

        query
                .get().addOnCompleteListener(new OnCompleteListener < QuerySnapshot > () {
                    @Override
                    public void onComplete(@NonNull Task < QuerySnapshot > task) {

                        if (task.isSuccessful()) {
                            int count = 0;
                            for (DocumentSnapshot document: task.getResult()) {
                                count++;
                            }
                            holder.linkscount.setText(String.valueOf(count));
                            if (holder.linkscount.getText().toString().equalsIgnoreCase("0")) {
                                holder.link.setVisibility(View.GONE);
                            }

                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });

        CollectionReference countchecklits = firestore.collection("Lists");
        Query query1 = countchecklits.whereEqualTo("ID_UPDATES", viewTaskModels.get(position).getUpdatesId());

        query1
                .get().addOnCompleteListener(new OnCompleteListener < QuerySnapshot > () {
                    @Override
                    public void onComplete(@NonNull Task < QuerySnapshot > task) {

                        if (task.isSuccessful()) {
                            int count = 0;
                            for (DocumentSnapshot document: task.getResult()) {
                                count++;
                            }
                            holder.checklistcount.setText(String.valueOf(count));
                            if (holder.checklistcount.getText().toString().equalsIgnoreCase("0")) {
                                holder.checklist.setVisibility(View.GONE);
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });

        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        //when the link is 0

        Cursor getlinkdata = databaseHelper.getLinkCount(currentUpdateID);
        while (getlinkdata.moveToNext()) {
            Log.d("TAG", getlinkdata.getString(0));
            holder.linkscount.setText(getlinkdata.getString(0));
        }
        //hide link cardview when 0 it is
        //            if(holder.linkscount.getText().toString().equalsIgnoreCase("0")){
        //                holder.link.setVisibility(View.GONE);
        //            }

        Cursor getListCount = databaseHelper.getListCount(currentUpdateID);
        while (getListCount.moveToNext()) {
            Log.d("TAG", getListCount.getString(0));
            holder.checklistcount.setText(getListCount.getString(0));
        }
        //hide list cardview when 0 it is
        //        if(holder.checklistcount.getText().toString().equalsIgnoreCase("0")){
        //            holder.checklist.setVisibility(View.GONE);
        //        }

        Cursor getFilesCount = databaseHelper.getFilesCount(currentUpdateID);
        while (getFilesCount.moveToNext()) {
            Log.d("TAG", getFilesCount.getString(0));
            holder.filesCount.setText(getFilesCount.getString(0));
        }
        //hide link files when 0 it is
        if (holder.filesCount.getText().toString().equalsIgnoreCase("0")) {
            holder.files.setVisibility(View.GONE);
        }

        Cursor getImageCount = databaseHelper.getImageCount(currentUpdateID);
        while (getImageCount.moveToNext()) {
            Log.d("TAG", getImageCount.getString(0));
            holder.photoscount.setText(getImageCount.getString(0));
        }
        //hide link files when 0 it is
        if (holder.photoscount.getText().toString().equalsIgnoreCase("0")) {
            holder.photos.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return viewTaskModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, date, viewCount, commentCount, description, filesCount, checklistcount, photoscount, linkscount, likeCount, dislikecounts;
        ImageView profilephoto;
        CardView checklist, files, photos, link;

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
                if (viewTaskInterfaces != null) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        viewTaskInterfaces.onItemClick(pos, "ViewTaskAdapter");
                    }
                }
            });
        }
    }
}