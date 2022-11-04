package com.example.hteams.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hteams.R;
import com.example.hteams.group.Updates;
import com.example.hteams.model.ListDisplayModel;

import java.util.ArrayList;

public class ListDisplayAdapter extends RecyclerView.Adapter<ListDisplayAdapter.MyViewHolder> {

    private final Updates updateListInterface;
    Context context;
    ArrayList<ListDisplayModel> listDisplayModels;

    public ListDisplayAdapter(Context context, ArrayList<ListDisplayModel> listDisplayModels, Updates updateListInterface) {
        this.context = context;
        this.listDisplayModels = listDisplayModels;
        this.updateListInterface = updateListInterface;
    }


    @NonNull
    @Override
    public ListDisplayAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerlist_updates, parent, false);

        return new ListDisplayAdapter.MyViewHolder(view, updateListInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        boolean checked = listDisplayModels.get(position).getChecked();

        if (checked == true){
            holder.checkedicon.setChecked(true);
        }

        holder.checkedicon.setText(listDisplayModels.get(position).getTaskname());


    }

    @Override
    public int getItemCount() {
        return listDisplayModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkedicon;

        public MyViewHolder(@NonNull View itemView, Updates updateListInterface) {
            super(itemView);
            checkedicon = itemView.findViewById(R.id.checked_icon);
            itemView.setOnClickListener(view -> {
                if(updateListInterface != null ){
                    int pos = getAdapterPosition();
                    if(pos!= RecyclerView.NO_POSITION){
                        updateListInterface.onItemClick(pos,"listdialog");
                    }

                }
            });

        }


    }
}
