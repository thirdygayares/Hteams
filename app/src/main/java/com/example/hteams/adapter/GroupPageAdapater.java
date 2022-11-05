package com.example.hteams.adapter;



import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.hteams.MainActivity;
import com.example.hteams.R;
import com.example.hteams.database.DatabaseHelper;
import com.example.hteams.group.AddTask;
import com.example.hteams.group.GroupPage;
import com.example.hteams.group.Home;
import com.example.hteams.model.GroupPageModel;
import com.example.hteams.model.GroupPageParentModel;

import java.util.ArrayList;

public class GroupPageAdapater extends RecyclerView.Adapter<GroupPageAdapater.MyViewHolder> {


    Context context;
    ArrayList<GroupPageModel> grouppagemodels = new ArrayList<>();
    ArrayList<GroupPageParentModel> groupPageParentModels;
    DatabaseHelper databaseHelper;
    private String tag;
    String imgsrc = "";

    public GroupPageAdapater(Context context,  ArrayList<GroupPageParentModel> groupPageParentModels){
        this.context = context;
        this.groupPageParentModels = groupPageParentModels;

    }

    @NonNull
    @Override
    public GroupPageAdapater.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType  ) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_group_page_parent, parent, false);
        return new GroupPageAdapater.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupPageAdapater.MyViewHolder holder, int position) {

        GroupPageParentModel model = groupPageParentModels.get(position);

        boolean isExpandable = model.isExpandable();
        holder.taskRecycler.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
        holder.addTaskContainer.setVisibility(isExpandable ? View.VISIBLE : View.GONE);

        if (isExpandable){
            holder.touchme.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.arrow.setVisibility(View.GONE);
        }else{
            holder.touchme.setBackgroundColor(Color.parseColor("#FFFAFA"));
            holder.arrow.setVisibility(View.VISIBLE);

        }

      holder.TableName.setText(groupPageParentModels.get(position).getTableName());

        GroupPageChildAdapter groupPageChildAdapter = new GroupPageChildAdapter(grouppagemodels);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        holder.taskRecycler.setAdapter(groupPageChildAdapter);
        holder.taskRecycler.setLayoutManager(linearLayoutManager);
        //what if yung table id is 1;

        int crt = position, database = 1;
        database = database -1;

            ArrayList<String> x = new ArrayList<String>();
//        to erase the model
        grouppagemodels.clear();

        Home home = new Home();
        String groupid = home.GroupId;
//       Toast.makeText(context, "countid na " + groupid, Toast.LENGTH_SHORT ).show();
        // retreieveing the task
        databaseHelper = new DatabaseHelper(context);
        //for loop try natin[1]

        Cursor countTable = databaseHelper.getCountAllTable(groupid);
         countTable.moveToNext();
//         testing
         //Toast.makeText(context, "count" + countTable.getString(0), Toast.LENGTH_SHORT ).show();

//        for(int i = 1; i<=Integer.parseInt(countTable.getString(0)); i++){
//            Toast.makeText(context, "Group ID ay" + groupid, Toast.LENGTH_SHORT ).show();
         Cursor getTask = databaseHelper.getTaskTable(String.valueOf(position + 1), groupid);
//        Toast.makeText(context, "countid a " + String.valueOf(position + 1), Toast.LENGTH_SHORT ).show();
//        Toast.makeText(context, "countid na " + groupid, Toast.LENGTH_SHORT ).show();
          try{
              while(getTask.moveToNext()){
                  Cursor getImage = databaseHelper.getImageCurrentsUser(getTask.getString(3));
                  while(getImage.moveToNext()){
                      imgsrc = getImage.getString(0);
                  }
                  grouppagemodels.add(new GroupPageModel(getTask.getString(4), getTask.getString(5),getTask.getString(7), imgsrc));
                   //find picture
              }
          }catch (Exception e){
              Toast.makeText(context, "error" + e, Toast.LENGTH_SHORT ).show();
          }

//        }
            //check kung nareread ang group ID
            //Toast.makeText(context, groupPage.getGroupID, Toast.LENGTH_SHORT ).show();


            //hide and view of a task
            holder.touchme.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean nakaopen = false;
                    model.setExpandable(!model.isExpandable());
//                    grouppagemodels = model.getNestedList();
                    notifyItemChanged(holder.getAdapterPosition());
//                    if(nakaopen == false){
//                        holder.taskRecycler.setVisibility(View.GONE);
//                        holder.addTaskContainer.setVisibility(View.GONE);
//                        nakaopen = true;
//                    } else{
//                        holder.taskRecycler.setVisibility(View.VISIBLE);
//                        holder.addTaskContainer.setVisibility(View.VISIBLE);
//                        nakaopen = false;
//                    }

                }
            });

          //when add task
        holder.addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddTask.class);
                intent.putExtra("GROUP_ID", groupid);
                intent.putExtra("TABLE_ID", String.valueOf(holder.getAdapterPosition() + 1));
                intent.putExtra("NEW_TABLE", "false");
                v.getContext().startActivity(intent);


            }
        });

    }


    @Override
    public int getItemCount() {
        return groupPageParentModels.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView TableName;
        Button addTaskButton;
        RecyclerView taskRecycler;
        LinearLayout addTaskContainer;
        RelativeLayout touchme;
        ImageView arrow;

        public MyViewHolder(@NonNull View itemView ) {
            super(itemView);

            TableName = (TextView) itemView.findViewById(R.id.Table) ;
            addTaskButton =(Button) itemView.findViewById(R.id.addtask);
            taskRecycler = (RecyclerView) itemView.findViewById(R.id.taskRecycler);
            touchme = (RelativeLayout) itemView.findViewById(R.id.touchme);
            addTaskContainer = (LinearLayout) itemView.findViewById(R.id.addTaskContainer);
            arrow = (ImageView) itemView.findViewById(R.id.arrow);


        }
    }


}
