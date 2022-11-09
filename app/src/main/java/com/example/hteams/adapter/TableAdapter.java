package com.example.hteams.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.hteams.R;
import com.example.hteams.Testing.SetProfile;
import com.example.hteams.group.GroupPage;
import com.example.hteams.model.TableModel;
import com.google.android.material.card.MaterialCardView;

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

        //to set the color
        GroupPage groupPage = new GroupPage();
        if(position == groupPage.changecolor){
            holder.colorstate.setBackgroundColor(Color.WHITE);
            holder.tableName.setTextColor(Color.BLUE);
            holder.tableName.setMaxWidth(1000);
        }else{
            holder.colorstate.setBackgroundColor(Color.parseColor("#99C9EC"));
            holder.tableName.setTextColor(Color.parseColor("#F6EFF1"));
            holder.tableName.setMaxWidth(300);
        }

    }

    @Override
    public int getItemCount() {
        return tablemodels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tableName;
        RelativeLayout colorstate;

        public MyViewHolder(@NonNull View itemView, GroupInterface groupInterface) {
            super(itemView);
            tableName = (TextView) itemView.findViewById(R.id.tableName);
            colorstate = (RelativeLayout) itemView.findViewById(R.id.colorstate);
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
