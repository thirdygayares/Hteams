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
import com.example.hteams.model.FileModel;

import java.util.ArrayList;

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.MyViewHolder> {

    private final ViewUpdateInterface interfaces;


    Context context;
    ArrayList<FileModel> filemodels;

    public FilesAdapter(Context context, ArrayList<FileModel> filemodels, ViewUpdateInterface interfaces){
        this.context = context;
        this.filemodels = filemodels;
        this.interfaces = interfaces;
    }

    @NonNull
    @Override
    public FilesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType  ) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_files, parent, false);

        return new FilesAdapter.MyViewHolder(view, interfaces);
    }

    @Override
    public void onBindViewHolder(@NonNull FilesAdapter.MyViewHolder holder, int position) {

        String type = filemodels.get(position).getType();
        if(type.equalsIgnoreCase("word")){
            holder.file_icon.setImageResource(R.drawable.word);
        } else  if(type.equalsIgnoreCase("pptx")){
            holder.file_icon.setImageResource(R.drawable.powerpoint);
        } else  if(type.equalsIgnoreCase("pdf")){
            holder.file_icon.setImageResource(R.drawable.pdf);
        }else{
            holder.file_icon.setImageResource(R.drawable.ic_baseline_attach_file_24);
        }


        holder.filename.setText(filemodels.get(position).getFilename());

    }

    @Override
    public int getItemCount() {

        return filemodels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView file_icon;
        TextView filename;


        public MyViewHolder(@NonNull View itemView, ViewUpdateInterface interfaces) {
            super(itemView);
            file_icon = itemView.findViewById(R.id.file_icon);
            filename  = itemView.findViewById(R.id.filename);


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
