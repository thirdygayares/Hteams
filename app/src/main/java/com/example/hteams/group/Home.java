package com.example.hteams.group;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.hteams.FirebaseTesting;
import com.example.hteams.R;
import com.example.hteams.Requirements.FindSection;
import com.example.hteams.adapter.GroupAdapter;
import com.example.hteams.adapter.GroupInterface;
import com.example.hteams.database.DatabaseHelper;
import com.example.hteams.model.GroupModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment implements GroupInterface {
    String TAG = "TAG";
    ImageView menu,addgroup;
    ArrayList<GroupModel> groupModels = new ArrayList<>();

    //firebase Auth
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;


    //SQLITE DB
    DatabaseHelper databaseHelper;
    String currentId;
    static String Section;
    View view;
    public static String GroupId;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.activity_home, container, false);

        //to know the email and uid
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        //calling database sqlite
         databaseHelper = new DatabaseHelper(getActivity());

        //cyrrent id
        currentId = firebaseAuth.getCurrentUser().getUid();
        addgroup = view.findViewById(R.id.addgroup);
        menu = view.findViewById(R.id.menu);
        menu();

        //To add the group
        //Button

        addgroup();

        setupGroupData();
        RecyclerView recyclerView = view.findViewById(R.id.grouprecyclerview);
        GroupAdapter adapter = new GroupAdapter(getActivity(), groupModels, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        //testing arrayList
        ArrayList<FirebaseTesting> firebaseTestings = new ArrayList<>();

        //getting a documennnt or row in sql
//        DocumentReference docRef = firestore.collection("students").document(currentId);
//
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
////                        FirebaseTesting firebaseTesting = document.toObject(FirebaseTesting.class);
////                        Log.d(TAG, "firebase COllege " + firebaseTesting.getCollege());
//                        firebaseTestings.add(new FirebaseTesting(document.get("College").toString(),document.get("Email").toString(),document.get("ContactNumber").toString(),document.get("Course").toString(),document.get("Section").toString(),document.get("Name").toString()));
//                        Log.d(TAG, "DocumentSnapshot data: " + document.get("College").toString());
//                        Log.d(TAG,"Name: " + firebaseTestings.get(0).getName());
//                    } else {
//                        Log.d(TAG, "No such document");
//                    }
//                } else {
//                    Log.d(TAG, "get failed with ", task.getException());
//                }
//            }
//        });

      //finding a Section


        return view;
    }




    private void addgroup() {
        addgroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Creategroup.class));
            }
        });
    }


    private void setupGroupData() {



        ArrayList<String> GroupID = new ArrayList<>();
        //bago mo makuha yung group need mo muna malman kung accepted nila at syempre parrticipants sila

        try{
            Cursor getMyParticipant = databaseHelper.selectMyGroups(currentId);
            while (getMyParticipant.moveToNext()){
                GroupID.add(getMyParticipant.getString(0));

            }
        }catch(Exception e){
            Toast.makeText(getActivity(), "getting participant" + e, Toast.LENGTH_SHORT).show();
        }

        try{
            LinearLayout emptygroup = view.findViewById(R.id.emptygroup);

            //outputing the groups
//    public GroupModel(int GROUPID, int groupImage, String groupTitle, String shortDescription, String professor, String subject) {
            if(GroupID.isEmpty()){
                emptygroup.setVisibility(View.VISIBLE);
            }     else{

                emptygroup.setVisibility(View.GONE);

                for (int i=0;i<GroupID.size();i++){
                    Cursor getGroups = databaseHelper.myGroup(GroupID.get(i));
                    getGroups.moveToNext();
                    groupModels.add(new GroupModel(getGroups.getString(0),getGroups.getString(1),getGroups.getString(2),getGroups.getString(4),getGroups.getString(5),getGroups.getString(3) ));
                }
            }
        }catch (Exception e){
            Toast.makeText(getActivity(), "getting groups" + e, Toast.LENGTH_SHORT).show();
        }


        }


    @Override
    public void onItemClick(int position, String taskView) {

        Intent intent = new Intent(getActivity(), GroupPage.class);
        intent.putExtra("setGroupId", groupModels.get(position).getGROUPID());
        GroupId = groupModels.get(position).getGROUPID();
//        intent.putExtra("Total", historyDataModels.get(position).getTotalPrice());
        startActivity(intent);
    }

    private void menu() {
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getActivity(), menu);

                // Inflating popup menu from popup_menu.xml file
                popupMenu.getMenuInflater().inflate(R.menu.home_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        // Toast message on menu item clicked
                        Toast.makeText(getActivity(), "You Clicked " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                // Showing the popup menu
                popupMenu.show();
            }
        });

    }

}