package com.example.hteams.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.hteams.R;
import com.example.hteams.model.SiteModel;

import java.util.ArrayList;

public class SiteAdapter extends RecyclerView.Adapter<SiteAdapter.MyViewHolder> {

    private final SiteInterface siteInterfaces;


    Context context;
    ArrayList<SiteModel> siteModels;

    public SiteAdapter(Context context, ArrayList<SiteModel> siteModels, SiteInterface siteInterfaces){
        this.context = context;
        this.siteModels = siteModels;
        this.siteInterfaces = siteInterfaces;
    }

    @NonNull
    @Override
    public SiteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType  ) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclersites, parent, false);

        return new SiteAdapter.MyViewHolder(view, siteInterfaces);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.siteIcon.setImageResource(siteModels.get(position).getSiteicon());
        holder.siteName.setText(siteModels.get(position).getSitename());
        String nameGetter = siteModels.get(position).getSitename();
        holder.cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Toast.makeText(v.getContext(),nameGetter,Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public int getItemCount() {

        return siteModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView siteName;
        ImageView siteIcon, cancelbtn ;

        public MyViewHolder(@NonNull View itemView, SiteInterface siteInterfaces) {
            super(itemView);
            siteIcon= itemView.findViewById(R.id.site_icon);
            siteName = itemView.findViewById(R.id.sitename);
            cancelbtn = itemView.findViewById(R.id.cancelsite);



            itemView.setOnClickListener(view -> {
                if(siteInterfaces != null ){
                    int pos = getAdapterPosition();
                    if(pos!= RecyclerView.NO_POSITION){
                        siteInterfaces.onItemClick(pos);
                    }

                }
            });
        }
    }