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
import com.example.hteams.Testing.SetProfile;
import com.example.hteams.model.ChooseParticipantModel;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class ChooseParticipantAdapter extends RecyclerView.Adapter<ChooseParticipantAdapter.MyViewHolder> {

    private final ChooseParcticipant chooseParticipants;

    Context context;
    ArrayList<ChooseParticipantModel> chooseParticipantsModel;

    public ChooseParticipantAdapter(Context context, ArrayList<ChooseParticipantModel> chooseParticipantsModel, ChooseParcticipant chooseParticipants) {
        this.context = context;
        this.chooseParticipantsModel = chooseParticipantsModel;
        this.chooseParticipants = chooseParticipants;


    }

    @NonNull
    @Override
    public ChooseParticipantAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType  ) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerviewclassmateinvite, parent, false);
        return new ChooseParticipantAdapter.MyViewHolder(view,chooseParticipants);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.classmateName.setText(chooseParticipantsModel.get(position).getNAME());

        SetProfile setProfiles = new SetProfile();
//        holder.classmate_image.setImageResource(setProfiles.profileImage(chooseParticipantsModel.get(position).getIMAGE()));
        Picasso.get().load(chooseParticipantsModel.get(position).getIMAGE()).error(R.drawable.ic_profile).resize(400,400).centerCrop().into(holder.classmate_image);
    }

    @Override
    public int getItemCount() {
        return chooseParticipantsModel.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView classmateName;
        ImageView classmate_image;

        public MyViewHolder(View itemview, ChooseParcticipant chooseParticipants) {
            super(itemview);
            classmateName = (TextView) itemview.findViewById(R.id.classmate_name);
           classmate_image= (ImageView) itemview.findViewById(R.id.classmate_image);


            itemView.setOnClickListener(view -> {
                if(chooseParticipants != null ){
                    int pos = getAdapterPosition();
                    if(pos!= RecyclerView.NO_POSITION){
                        chooseParticipants.onItemClick(pos);
                    }

                }
            });
        }
    }
}




