package com.example.hteams.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.hteams.R;
import com.example.hteams.model.ListModel;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    private final ViewUpdateInterface interfaces;


    Context context;
    ArrayList<ListModel> listModels;

    public ListAdapter(Context context, ArrayList<ListModel> listModels, ViewUpdateInterface interfaces){
        this.context = context;
        this.listModels = listModels;
        this.interfaces = interfaces;
    }

    @NonNull
    @Override
    public ListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType  ) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycle_list, parent, false);
        return new ListAdapter.MyViewHolder(view, interfaces);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.MyViewHolder holder, int position) {

        //check if check or uncheck sa that checkbox
        int status = listModels.get(position).getStatus();
        if (status == 0){
            holder.statusicon.setChecked(true);
        }

        holder.statusicon.setText(listModels.get(position).getTasktitle());

    }

    @Override
    public int getItemCount() {

        return listModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        CheckBox statusicon;


        public MyViewHolder(@NonNull View itemView, ViewUpdateInterface interfaces) {
            super(itemView);
            statusicon = itemView.findViewById(R.id.statusicon);





            itemView.setOnClickListener(view -> {
                if(interfaces != null ){
                    int pos = getAdapterPosition();
                    if(pos!= RecyclerView.NO_POSITION){
                        interfaces.onItemClick(pos);
                    }

                }
            });
        }
    }
}
