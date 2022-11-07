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
import com.example.hteams.model.TableModel;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.MyViewHolder>  {

    Context context;
    ArrayList<TableModel> tablemodels;
    GroupInterface groupInterface;

    public TableAdapter(Context context, ArrayList<TableModel> tablemodels,GroupInterface groupInterface){
        this.context = context;
        this.tablemodels = tablemodels;
        this.groupInterface = groupInterface;

    }

    @NonNull
    @Override
    public TableAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType  ) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyler_view_table, parent, false);

        return new TableAdapter.MyViewHolder(view,groupInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull TableAdapter.MyViewHolder holder, int position) {
        holder.tableName.setText(tablemodels.get(position).getTableName());
    }

    @Override
    public int getItemCount() {
        return tablemodels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tableName;


        public MyViewHolder(@NonNull View itemView, GroupInterface groupInterface) {
            super(itemView);
            tableName = (TextView) itemView.findViewById(R.id.tableName);

            itemView.setOnClickListener(view -> {
                if(groupInterface != null ){
                    int pos = getAdapterPosition();
                    if(pos!= RecyclerView.NO_POSITION){
                        groupInterface.onItemClick(pos, "tableApdater");
                    }

                }
            });

        }
    }


}
