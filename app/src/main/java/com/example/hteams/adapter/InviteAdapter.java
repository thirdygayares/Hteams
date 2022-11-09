package com.example.hteams.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.hteams.R;
import com.example.hteams.Testing.SetProfile;
import com.example.hteams.model.InviteModel;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class InviteAdapter extends RecyclerView.Adapter<InviteAdapter.MyViewHolder> {

    Context context;
    ArrayList<InviteModel> invitemodels;

    public InviteAdapter(Context context, ArrayList<InviteModel> invitemodels){
        this.context = context;
        this.invitemodels = invitemodels;

    }

    @NonNull
    @Override
    public InviteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType  ) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_invite, parent, false);

        return new InviteAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InviteAdapter.MyViewHolder holder, int position) {
        holder.classmateName.setText(invitemodels.get(position).getName());

        SetProfile setProfile = new SetProfile();
//        holder.classmate_image.setImageResource(setProfile.profileImage(invitemodels.get(position).getImage()));
        Picasso.get().load(invitemodels.get(position).getImage()).error(R.drawable.ic_profile).into(holder.classmate_image);

    }

    @Override
    public int getItemCount() {

        return invitemodels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView classmateName;
        ImageView classmate_image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            classmateName = (TextView) itemView.findViewById(R.id.classmate_name);
            classmate_image = (ImageView) itemView.findViewById(R.id.classmate_image);

        }
    }


}
