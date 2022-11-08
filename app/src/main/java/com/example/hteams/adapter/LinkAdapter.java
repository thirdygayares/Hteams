package com.example.hteams.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

        if(iconName.equalsIgnoreCase("Canva")){
            holder.display_site_icon.setImageResource(R.drawable.canvca);
        } else if(iconName.equalsIgnoreCase("Discord")){
            holder.display_site_icon.setImageResource(R.drawable.discord);
        } else if(iconName.equalsIgnoreCase("Excel")){
            holder.display_site_icon.setImageResource(R.drawable.excel);
        } else if(iconName.equalsIgnoreCase("Facebook")){
            holder.display_site_icon.setImageResource(R.drawable.facebook);
        }else if(iconName.equalsIgnoreCase("Gdrive")){
            holder.display_site_icon.setImageResource(R.drawable.drive);
        }else if(iconName.equalsIgnoreCase("Github")){
            holder.display_site_icon.setImageResource(R.drawable.github);
        }else if(iconName.equalsIgnoreCase("Gmail")){
            holder.display_site_icon.setImageResource(R.drawable.gmail);
        }else if(iconName.equalsIgnoreCase("Gmeet")){
            holder.display_site_icon.setImageResource(R.drawable.meet);
        }else if(iconName.equalsIgnoreCase("Gnotes")){
            holder.display_site_icon.setImageResource(R.drawable.notes);
        }else if(iconName.equalsIgnoreCase("Google")){
            holder.display_site_icon.setImageResource(R.drawable.google);
        }else if(iconName.equalsIgnoreCase("Messenger")){
            holder.display_site_icon.setImageResource(R.drawable.messenger);
        }else if(iconName.equalsIgnoreCase("Moodle")){
            holder.display_site_icon.setImageResource(R.drawable.moodle);
        }else if(iconName.equalsIgnoreCase("Mteams")){
            holder.display_site_icon.setImageResource(R.drawable.teams);
        }else if(iconName.equalsIgnoreCase("Onedrive")){
            holder.display_site_icon.setImageResource(R.drawable.onedrive);
        }else if(iconName.equalsIgnoreCase("Powerpoint")){
            holder.display_site_icon.setImageResource(R.drawable.powerpoint);
        }else if(iconName.equalsIgnoreCase("Telegram")){
            holder.display_site_icon.setImageResource(R.drawable.telegram);
        }else if(iconName.equalsIgnoreCase("Word")){
            holder.display_site_icon.setImageResource(R.drawable.word);
        }else if(iconName.equalsIgnoreCase("Youtube")){
            holder.display_site_icon.setImageResource(R.drawable.youtube);
        }else if(iconName.equalsIgnoreCase("Zoom")){
            holder.display_site_icon.setImageResource(R.drawable.zoom);
        }else {
            holder.display_site_icon.setImageResource(R.drawable.others);
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
                        viewUpdateInterfaces.onItemClick(pos, "link");
                    }

                }
            });
        }
    }
}
