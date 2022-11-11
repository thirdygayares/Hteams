package com.example.hteams.adapter;



import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.hteams.R;
import com.example.hteams.Testing.SetAvatar;
import com.example.hteams.model.PersonalTaskModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PersonalTaskAdapter extends RecyclerView.Adapter<PersonalTaskAdapter.MyViewHolder> {

    private final PersonalTaskInterface personalTaskInterfaces;


    Context context;
    ArrayList<PersonalTaskModel> personalTaskModels;

    public PersonalTaskAdapter(Context context, ArrayList<PersonalTaskModel> personalTaskModels, PersonalTaskInterface personalTaskInterfaces){
        this.context = context;
        this.personalTaskModels = personalTaskModels;
        this.personalTaskInterfaces = personalTaskInterfaces;
    }

    @NonNull
    @Override
    public PersonalTaskAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType  ) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_personal_task, parent, false);

        return new PersonalTaskAdapter.MyViewHolder(view, personalTaskInterfaces);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonalTaskAdapter.MyViewHolder holder, int position) {

        SetAvatar setAvatar = new SetAvatar();
       // holder.groupPhoto.setImageResource(setAvatar.setAvatar(personalTaskModels.get(position).getGroupImage()));
        holder.groupName.setText(personalTaskModels.get(position).getGroupName());
        holder.taskName.setText(personalTaskModels.get(position).getNameofTask());
        holder.status.setText(personalTaskModels.get(position).getStatus());
        holder.duedate.setText(personalTaskModels.get(position).getDueDate());

        FirebaseFirestore firestore;
        firestore = FirebaseFirestore.getInstance();



        firestore.collection("groups").document(personalTaskModels.get(position).getIdGroup())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot.exists()) {
                                holder.groupName.setText(documentSnapshot.get("GROUPNAME").toString());
                                Picasso.get().load(documentSnapshot.get("GROUPPHOTO").toString()).error(R.drawable.ic_profile).into(holder.groupPhoto);
                            }
                        }
                    }
                });





        String status_indicatior = holder.status.getText().toString();
        if(status_indicatior.equalsIgnoreCase("done")){
            holder.status.setTextColor(Color.parseColor("#3AAB28"));
            //changing to done icon
        }else if(status_indicatior.equalsIgnoreCase("working on it")){
            holder.status.setTextColor(Color.parseColor("#3659D7"));
            //changing to working icon
        }else if(status_indicatior.equalsIgnoreCase("to do")){
            holder.status.setTextColor(Color.BLACK);
        }else if(status_indicatior.equalsIgnoreCase("Ready")){
            holder.status.setTextColor(Color.parseColor("#FF9500"));
            //changing to ready icon

        }

    }

    @Override
    public int getItemCount() {

        return personalTaskModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView taskName, status, duedate,groupName;
        ImageView groupPhoto;

        public MyViewHolder(@NonNull View itemView, PersonalTaskInterface personalTaskInterfaces) {
            super(itemView);
            groupPhoto = (ImageView) itemView.findViewById(R.id.groupPhoto);
            groupName = (TextView) itemView.findViewById(R.id.groupname);
            taskName = (TextView) itemView.findViewById(R.id.taskName);
            status = (TextView) itemView.findViewById(R.id.status);
            duedate = (TextView) itemView.findViewById(R.id.duedate);


            itemView.setOnClickListener(view -> {
                if(personalTaskInterfaces != null ){
                    int pos = getAdapterPosition();
                    if(pos!= RecyclerView.NO_POSITION){
                        personalTaskInterfaces.onItemClick(pos);
                    }

                }
            });
        }
    }


}
