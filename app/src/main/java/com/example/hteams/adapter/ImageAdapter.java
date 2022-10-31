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
import com.example.hteams.model.ImageModel;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {

    private final Interface interfaces;


    Context context;
    ArrayList<ImageModel> imagemodels;

    public ImageAdapter(Context context, ArrayList<ImageModel> imagemodels, Interface interfaces){
        this.context = context;
        this.imagemodels = imagemodels;
        this.interfaces = interfaces;
    }

    @NonNull
    @Override
    public ImageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType  ) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_image, parent, false);

        return new ImageAdapter.MyViewHolder(view, interfaces);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.MyViewHolder holder, int position) {
        holder.imageholder.setImageResource(imagemodels.get(position).getImage());
        holder.imageholder.setScaleType(ImageView.ScaleType.CENTER_CROP);

    }

    @Override
    public int getItemCount() {

        return imagemodels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageholder;


        public MyViewHolder(@NonNull View itemView, Interface interfaces) {
            super(itemView);
            imageholder = itemView.findViewById(R.id.imageholder);



            itemView.setOnClickListener(view -> {
                if(interfaces != null ){
                    int pos = getAdapterPosition();
                    if(pos!= RecyclerView.NO_POSITION){
                        interfaces.onItemClick(pos);
                    }

                }
            });
        }
    }
}
