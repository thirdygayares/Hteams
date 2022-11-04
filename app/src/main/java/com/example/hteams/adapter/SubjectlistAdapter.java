package com.example.hteams.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hteams.R;
import com.example.hteams.model.SubjectlistModel;


import java.util.ArrayList;

public class SubjectlistAdapter extends RecyclerView.Adapter<SubjectlistAdapter.MyViewHolder> {

    private final SubjectlistInterface subjectlistInterface;

    Context context;
    ArrayList<SubjectlistModel> subjectlistModel;

    public SubjectlistAdapter(Context context, ArrayList<SubjectlistModel> subjectlistModel, SubjectlistInterface subjectlistInterface) {
        this.context = context;
        this.subjectlistModel = subjectlistModel;
        this.subjectlistInterface = subjectlistInterface;


    }

    @NonNull
    @Override
    public SubjectlistAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType  ) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_subjects_creategroup, parent, false);
        return new SubjectlistAdapter.MyViewHolder(view,subjectlistInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.subjectname.setText(subjectlistModel.get(position).getSubjectname());

    }

    @Override
    public int getItemCount() {
        return subjectlistModel.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView subjectname;

        public MyViewHolder(View itemview, SubjectlistInterface subjectlistInterface) {
            super(itemview);
            subjectname = itemview.findViewById(R.id.subject_name);

            itemView.setOnClickListener(view -> {
                if(subjectlistInterface != null ){
                    int pos = getAdapterPosition();
                    if(pos!= RecyclerView.NO_POSITION){
                        subjectlistInterface.onItemClick(pos, "subject");
                    }

                }
            });
        }
    }
}




