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

public class LinkAdapter extends RecyclerView.Adapter<LinkAdapter.MyViewHolder> {

    private final ViewUpdateInterface viewUpdateInterfaces;
    Context context;
    ArrayList<DisplaySiteModel> displaySiteModels;

    public LinkAdapter(Context context, ArrayList<DisplaySiteModel> displaySiteModels, ViewUpdateInterface viewUpdateInterfaces){
        this.context = context;
        this.displaySiteModels = displaySiteModels;
        this.viewUpdateInterfaces = viewUpdateInterfaces;
    }

    @NonNull
    @Override
    public LinkAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType  ) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerlink, parent, false);

        return new LinkAdapter.MyViewHolder(view, viewUpdateInterfaces);
    }

    @Override
    public void onBindViewHolder(@NonNull LinkAdapter.MyViewHolder holder, int position) {

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

        //get the custom name in view updates
        holder.display_sitename.setText(displaySiteModels.get(position).getCustomsitename());

    }

    @Override
    public int getItemCount() {

        return displaySiteModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView display_site_icon;
        TextView display_sitename;

        public MyViewHolder(@NonNull View itemView, ViewUpdateInterface viewUpdateInterfaces) {
            super(itemView);
            display_site_icon = itemView.findViewById(R.id.link_icon);
            display_sitename = itemView.findViewById(R.id.linkname);


            itemView.setOnClickListener(view -> {
                if(viewUpdateInterfaces != null ){
                    int pos = getAdapterPosition();
                    if(pos!= RecyclerView.NO_POSITION){
                        viewUpdateInterfaces.onItemClick(pos);
                    }

                }
            });
        }
    }
}
