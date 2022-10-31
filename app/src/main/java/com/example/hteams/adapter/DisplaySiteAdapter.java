package com.example.hteams.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.example.hteams.Comments;
import com.example.hteams.R;
import com.example.hteams.model.DisplaySiteModel;

public class DisplaySiteAdapter extends RecyclerView.Adapter <DisplaySiteAdapter.MyViewHolder> {

    private final SiteInterface SiteInterface;


    Context context;
    ArrayList<DisplaySiteModel>  dislaySiteModels;
    public DisplaySiteAdapter(Context context, ArrayList<DisplaySiteModel> displaySiteModels, Comments SiteInterface){
        this.context = context;
        this.dislaySiteModels = displaySiteModels;
        this.SiteInterface = SiteInterface;
    }

    @NonNull
    @Override
    public DisplaySiteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerdisplaysite, parent, false);

        return new DisplaySiteAdapter.MyViewHolder(view,SiteInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull DisplaySiteAdapter.MyViewHolder holder, int position) {
        holder.displaysiteicon.setImageResource(dislaySiteModels.get(position).getSiteicon());
        holder.sitecustomname.setText(dislaySiteModels.get(position).getCustomsitename());

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        EditText sitecustomname;
        ImageView displaysiteicon, cancel ;

        public MyViewHolder(@NonNull View itemView, SiteInterface SiteInterface) {
            super(itemView);

            sitecustomname = itemView.findViewById(R.id.display_sitename);
            displaysiteicon = itemView.findViewById(R.id.display_site_icon);
            cancel = itemView.findViewById(R.id.display_cancel);

            itemView.setOnClickListener(view -> {
                if(SiteInterface != null){
                    int pos =getAdapterPosition();
                    if(pos !=RecyclerView.NO_POSITION){
                        SiteInterface.onItemClick(pos);
                    }
                }

            });
        }
    }
}
