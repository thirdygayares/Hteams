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
import com.example.hteams.Testing.SetProfile;
import com.example.hteams.model.AssigneeModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AsigneeAdapter extends RecyclerView.Adapter<AsigneeAdapter.MyViewHolder> {

    private final ViewTaskInterface viewTaskInterfaces;

    Context context;
    ArrayList<AssigneeModel> assigneeModels;

    public AsigneeAdapter(Context context, ArrayList<AssigneeModel> assigneeModels, ViewTaskInterface viewTaskInterfaces){
        this.context = context;
        this.assigneeModels = assigneeModels;
        this.viewTaskInterfaces = viewTaskInterfaces;
    }

    @NonNull
    @Override
    public AsigneeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType  ) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_assigned_to, parent, false);
        return new AsigneeAdapter.MyViewHolder(view, viewTaskInterfaces);
    }

    @Override
    public void onBindViewHolder(@NonNull AsigneeAdapter.MyViewHolder holder, int position) {
        holder.name.setText(assigneeModels.get(position).getName());

//        TODO if leader is current user it indicates you is visible
//        if(assigneeModels.get(position).getName().equalsIgnoreCase("Thirdy Gayares")){
//            holder.you.setVisibility(View.VISIBLE);
//        }

        Picasso.get().load(assigneeModels.get(position).getImgsrc()).error(R.drawable.ic_profile).into(holder.profilephoto);
    }

    @Override
    public int getItemCount() {
        return assigneeModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, you;
        ImageView profilephoto;

        public MyViewHolder(@NonNull View itemView, ViewTaskInterface viewTaskInterfaces) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.classmate_name);
            profilephoto = (ImageView) itemView.findViewById(R.id.classmate_image);
            you = (TextView) itemView.findViewById(R.id.you);

            itemView.setOnClickListener(view -> {
                if(viewTaskInterfaces != null ){
                    int pos = getAdapterPosition();
                    if(pos!= RecyclerView.NO_POSITION){
                        viewTaskInterfaces.onItemClick(pos, "AssigneeAdapter");
                    }
                }
            });
        }
    }
}
