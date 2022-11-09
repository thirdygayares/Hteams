package com.example.hteams.adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.hteams.R;
import com.example.hteams.database.DatabaseHelper;
import com.example.hteams.model.ListModel;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    private final ViewUpdateInterface interfaces;
    DatabaseHelper databaseHelper;
    String ssstatus;
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
        final int[] status = {listModels.get(position).getStatus()};
        if (status[0] == 0){
            holder.statusicon.setChecked(true);
        }

        holder.statusicon.setText(listModels.get(holder.getAdapterPosition()).getTasktitle());
        databaseHelper = new DatabaseHelper(context);
        holder.statusicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(holder.statusicon.isChecked() == true){
                    ssstatus = "0";
                }else{
                    ssstatus = "1";
                }
                try {
                    boolean updateStatus = databaseHelper.updateListStatus(String.valueOf(listModels.get(holder.getAdapterPosition()).getTaskId()),ssstatus );
                    if(updateStatus == true){
                        Log.d("TAG", "SUCCESSFULLY EDIT UPDATE LIST");
                        Log.d("TAG", "id" + listModels.get(holder.getAdapterPosition()).getTaskId());
                        Log.d("TAG", "Ang status ay " + ssstatus);
                    }else {
                        Log.d("TAG", "FDAILED EDIT UPDATE LIST");
                    }

                }catch (Exception e){
                    Log.d("TAG", "CANNOT EDIT UPDATE LIST CAUSE " + e);
                }

            }
        });


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
                        interfaces.onItemClick(pos, "list");
                    }

                }
            });
        }
    }
}
