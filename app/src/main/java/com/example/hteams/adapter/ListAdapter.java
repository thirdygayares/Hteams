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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    private final ViewUpdateInterface interfaces;
    DatabaseHelper databaseHelper;
    Boolean ssstatus;
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
        final String status = listModels.get(position).getStatus();
        if (status.equalsIgnoreCase("true")){
            holder.statusicon.setChecked(true);
        }

        holder.statusicon.setText(listModels.get(holder.getAdapterPosition()).getTasktitle());
        databaseHelper = new DatabaseHelper(context);
        holder.statusicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(holder.statusicon.isChecked() == true){
                    ssstatus = true;
                }else{
                    ssstatus = false;
                }

                FirebaseFirestore firestore;
                firestore = FirebaseFirestore.getInstance();

                firestore.collection("Lists").document(listModels.get(holder.getAdapterPosition()).getListsId())
                        .update("STATUS", ssstatus).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                    Log.d("TAG", "SUCCESS");
                            }
                        });


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
