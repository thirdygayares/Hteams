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
import com.example.hteams.model.AvatarModel;


import java.util.ArrayList;

public class AvatarAdapter extends RecyclerView.Adapter<AvatarAdapter.MyViewHolder> {

    private final SubjectlistInterface subjectlistInterface;

    Context context;
    ArrayList<AvatarModel> avatarModels;

    public AvatarAdapter(Context context, ArrayList<AvatarModel> avatarModels, SubjectlistInterface subjectlistInterface) {
        this.context = context;
        this.avatarModels = avatarModels;
        this.subjectlistInterface = subjectlistInterface;


    }

    @NonNull
    @Override
    public AvatarAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType  ) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_avatar, parent, false);
        return new AvatarAdapter.MyViewHolder(view,subjectlistInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.avatar.setImageResource(avatarModels.get(position).getImageSource());

    }

    @Override
    public int getItemCount() {
        return avatarModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView avatar;

        public MyViewHolder(View itemview, SubjectlistInterface subjectlistInterface) {
            super(itemview);
            avatar = itemview.findViewById(R.id.avatar);

            itemView.setOnClickListener(view -> {
                if(subjectlistInterface != null ){
                    int pos = getAdapterPosition();
                    if(pos!= RecyclerView.NO_POSITION){
                        subjectlistInterface.onItemClick(pos, "avatar");
                    }

                }
            });
        }
    }
}




