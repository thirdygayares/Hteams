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
import com.example.hteams.group.ViewTask;
import com.example.hteams.model.GroupPageModel;
import com.example.hteams.model.GroupPageParentModel;
import com.example.hteams.model.GroupStorePageModel;

import java.util.ArrayList;

public class GroupPageAdapater extends RecyclerView.Adapter<GroupPageAdapater.MyViewHolder> implements GroupInterface {

    GroupPageInterface groupPageInterface;
    Context context;
    ArrayList<GroupPageModel> grouppagemodels = new ArrayList<>();
    ArrayList<GroupStorePageModel> groupStorePageModels = new ArrayList<>();
    ArrayList<GroupPageParentModel> groupPageParentModels;
    DatabaseHelper databaseHelper;
    private String tag;
    String imgsrc = "";

    //to get the taskid,tableid,and group id
    public static int getIntGroupID;
    public static int getTaskID;
    public static int getTableID;

    public GroupPageAdapater(Context context,  ArrayList<GroupPageParentModel> groupPageParentModels, GroupPageInterface groupPageInterface){
        this.context = context;
        this.groupPageParentModels = groupPageParentModels;
        this.groupPageInterface = groupPageInterface;

    }

    public GroupPageAdapater() {

    }

    @NonNull
    @Override
    public GroupPageAdapater.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType  ) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_group_page_parent, parent, false);
        return new GroupPageAdapater.MyViewHolder(view,groupPageInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupPageAdapater.MyViewHolder holder, int position) {

        GroupPageParentModel model = groupPageParentModels.get(position);

        holder.TableName.setText(groupPageParentModels.get(position).getTableName());

        GroupPageChildAdapter groupPageChildAdapter = new GroupPageChildAdapter(grouppagemodels, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        holder.taskRecycler.setAdapter(groupPageChildAdapter);
        holder.taskRecycler.setLayoutManager(linearLayoutManager);
        //what if yung table id is 1;

//        to erase the model
//        grouppagemodels.clear();

        Home home = new Home();
        String groupid = home.GroupId;
        //getting group ID
        getIntGroupID = Integer.parseInt(groupid);
//       Toast.makeText(context, "countid na " + groupid, Toast.LENGTH_SHORT ).show();
        // retreieveing the task
        databaseHelper = new DatabaseHelper(context);
        //for loop try natin[1]


         Cursor getTask = databaseHelper.getTaskTable(String.valueOf(position + 1), groupid);
          try{
              while(getTask.moveToNext()){
//                  Log.d("TAG","get task: " + getTask.moveToNext());
                  Cursor getImage = databaseHelper.getImageCurrentsUser(getTask.getString(3));
                  while(getImage.moveToNext()){
                      imgsrc = getImage.getString(0);
                  }
                  grouppagemodels.add(new GroupPageModel(getTask.getInt(0),getTask.getInt(1),getTask.getInt(2),getTask.getString(4), getTask.getString(5),getTask.getString(7) + " " + getTask.getString(8), imgsrc, getTask.getInt(9)));
                  //testing method 1
                  groupStorePageModels.add(new GroupStorePageModel(getTask.getInt(0)));
                  //find picture
              }
          }catch (Exception e){
              Toast.makeText(context, "error err" + e, Toast.LENGTH_SHORT ).show();
          }
            //check kung nareread ang group ID
            //Toast.makeText(context, groupPage.getGroupID, Toast.LENGTH_SHORT ).show();
            //hide and view of a task
            holder.touchme.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean nakaopen = false;
                    model.setExpandable(!model.isExpandable());
                    if(!model.isExpandable()){
                        holder.recycleContainer.setVisibility(View.GONE);
                        holder.touchme.setBackgroundColor(Color.parseColor("#FFFAFA"));
                        holder.arrow.setVisibility(View.VISIBLE);
                    }else {
                        holder.recycleContainer.setVisibility(View.VISIBLE);
                        model.setExpandable(true);
                        holder.touchme.setBackgroundColor(Color.parseColor("#ffffff"));
                        holder.arrow.setVisibility(View.INVISIBLE);
                    }
//                    notifyItemChanged(holder.getAdapterPosition());
                }
            });

        holder.recycleContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "the table size is" + holder.getAdapterPosition());
            }
        });


          //when add task
        holder.addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddTask.class);
                intent.putExtra("GROUP_ID", groupid);
                intent.putExtra("TABLE_ID", String.valueOf(holder.getAdapterPosition() + 1));
                intent.putExtra("POSITION", String.valueOf(groupPageParentModels.get(holder.getAdapterPosition()).getPosition()));
                Log.d("TAG","The value of position in group page adapter" + groupPageParentModels.get(holder.getAdapterPosition()).getPosition());
                intent.putExtra("NEW_TABLE", "false");
                v.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return groupPageParentModels.size();
    }

    @Override
    public void onItemClick(int position, String taskView) {
        switch(taskView){
            case "taskView":
                try{
                View v;
                    Log.d("TAG","SIZE: " +   grouppagemodels.size());
                    Log.d("TAG","POSITION: " +   position);
                    Log.d("TAG","Task id: " +   groupStorePageModels.size());




//                    Log.d("TAG","TABLE ID: " + getIntGroupID);
//                    Log.d("TAG","TASK ID: " + getTaskID);
//                    Log.d("TAG","GROUP ID: " +getTableID);

                   // Intent intent = new Intent(context, ViewTask.class);
                   //context.startActivity(intent);

                }catch (Exception e){
                    Log.d("TAG", "clicking task" + e);
                }

                break;
        }
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView TableName;
        Button addTaskButton;
        RecyclerView taskRecycler;
        LinearLayout addTaskContainer,tabletouch;
        RelativeLayout touchme,recycleContainer;
        ImageView arrow;


        public MyViewHolder(@NonNull View itemView, GroupPageInterface groupPageInterface ) {
            super(itemView);
            TableName = (TextView) itemView.findViewById(R.id.Table) ;
            addTaskButton =(Button) itemView.findViewById(R.id.addtask);
            taskRecycler = (RecyclerView) itemView.findViewById(R.id.taskRecycler);
            touchme = (RelativeLayout) itemView.findViewById(R.id.touchme);
            recycleContainer = (RelativeLayout) itemView.findViewById(R.id.recycleContainer);
            addTaskContainer = (LinearLayout) itemView.findViewById(R.id.addTaskContainer);
            tabletouch = (LinearLayout) itemView.findViewById(R.id.tabletouch);
            arrow = (ImageView) itemView.findViewById(R.id.arrow);

            itemView.setOnClickListener(view -> {
                if(groupPageInterface != null ){
                    int pos = getAdapterPosition();
                    if(pos!= RecyclerView.NO_POSITION){
                        groupPageInterface.onItemClick(pos);
                    }
                }
            });
        }
    }


}
