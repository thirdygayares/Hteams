package com.example.hteams.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.hteams.R;
import com.example.hteams.model.InviteModel;

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

    }

    @Override
    public int getItemCount() {

        return invitemodels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView classmateName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            classmateName = itemView.findViewById(R.id.classmate_name);


        }
    }


}
