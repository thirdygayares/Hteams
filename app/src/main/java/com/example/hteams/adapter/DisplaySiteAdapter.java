package com.example.hteams.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.hteams.R;
import com.example.hteams.model.DisplaySiteModel;

import java.util.ArrayList;

public class DisplaySiteAdapter extends RecyclerView.Adapter<DisplaySiteAdapter.MyViewHolder> {

    private final SiteInterface siteInterfaces;


    Context context;
    ArrayList<DisplaySiteModel> displaySiteModels;

    public DisplaySiteAdapter(Context context, ArrayList<DisplaySiteModel> displaySiteModels, SiteInterface siteInterfaces){
        this.context = context;
        this.displaySiteModels = displaySiteModels;
        this.siteInterfaces = siteInterfaces;
    }

    @NonNull
    @Override
    public DisplaySiteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType  ) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerdisplaysite, parent, false);

        return new DisplaySiteAdapter.MyViewHolder(view, siteInterfaces);
    }

    @Override
    public void onBindViewHolder(@NonNull DisplaySiteAdapter.MyViewHolder holder, int position) {

//        condition changing icon
        //example if google meet pinili syempre lalabas google meet icon
        String iconName = displaySiteModels.get(position).getSiteName();

        if(iconName.equalsIgnoreCase("Google Meet")){
            holder.display_site_icon.setImageResource(R.drawable.googlemeet);
        } else if(iconName.equalsIgnoreCase("Github")){
            holder.display_site_icon.setImageResource(R.drawable.github);
        } else if(iconName.equalsIgnoreCase("Google Drive")){
            holder.display_site_icon.setImageResource(R.drawable.drive);
        }


        holder.display_sitename.setText(displaySiteModels.get(position).getCustomsitename());

    }

    @Override
    public int getItemCount() {

        return displaySiteModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView display_site_icon,display_cancel;
        TextView display_sitename;

        public MyViewHolder(@NonNull View itemView, SiteInterface siteInterfaces) {
            super(itemView);
            display_site_icon = itemView.findViewById(R.id.display_site_icon);
            display_sitename = itemView.findViewById(R.id.display_sitename);
            display_cancel = itemView.findViewById(R.id.display_cancel);

            itemView.setOnClickListener(view -> {
                if(siteInterfaces != null ){
                    int pos = getAdapterPosition();
                    if(pos!= RecyclerView.NO_POSITION){
                        siteInterfaces.onItemClick(pos, "displaysite");
                    }

                }
            });
        }
    }
}
