package com.example.hteams.group;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
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
import com.example.hteams.model.ChooseParticipantModel;
import com.example.hteams.model.FirebaseModel.FirebaseRetriveGroup;
import com.example.hteams.model.GroupModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment implements GroupInterface {
    String TAG = "TAG";
    ImageView menu, addgroup;
    ArrayList < GroupModel > groupModels = new ArrayList < > ();
    ArrayList < FirebaseRetriveGroup > firebaseRetriveGroups;
    //firebase Auth
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    public static ArrayList < String > GroupID = new ArrayList < > (); //bago mo makuha yung group need mo muna malman kung accepted nila at syempre parrticipants sila

    //SQLITE DB
    DatabaseHelper databaseHelper;
    String currentId;
    static String Section;
    public static int limitation = 0;
    View view;
    GroupAdapter adapter;
    public String GroupId;
    ProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_home, container, false);

        //process dialog
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching groups...");
        progressDialog.show();

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

        firebaseRetriveGroups = new ArrayList < > ();

        addgroup();

        setupGroupData();

        //       getGroup();

        //        Testing for recyclerview

        RecyclerView recyclerView = view.findViewById(R.id.grouprecyclerview);
        adapter = new GroupAdapter(getActivity(), firebaseRetriveGroups, Home.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        EventChangeListener();

        //Log.d("TAG",groupModels.get(0).getGroupTitle());

        return view;
    }


    //all groups retrive
    private void EventChangeListener() {
        firestore.collection("groups")

                .addSnapshotListener(new EventListener < QuerySnapshot > () {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null) {
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                            Log.e("TAG", error.getMessage());
                        }

                        for (DocumentChange dc: value.getDocumentChanges()) {

                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                firebaseRetriveGroups.add(new FirebaseRetriveGroup("sample", dc.getDocument().get("GROUPPHOTO").toString(), dc.getDocument().get("GROUPNAME").toString(), dc.getDocument().get("GROUPNAME").toString(), dc.getDocument().get("PROFESSOR").toString(), dc.getDocument().get("SUBJECT").toString()));
                            }

                            adapter.notifyDataSetChanged();
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    }
                });
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

        //Getting the data group from firebase
        CollectionReference mygroup = firestore.collection("participant"); //getting muna yung participant
        mygroup.whereEqualTo("StudentID", currentId)
                .get()
                .addOnCompleteListener(new OnCompleteListener < QuerySnapshot > () {
                    @Override
                    public void onComplete(@NonNull Task < QuerySnapshot > task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot: task.getResult()) {
                                if (documentSnapshot.exists()) {
                                    Log.d("TAG", documentSnapshot.getId() + "=>" + documentSnapshot.get("GROUPID"));
                                    GroupID.add(documentSnapshot.get("GROUPID").toString());
                                    LinearLayout emptygroup = view.findViewById(R.id.emptygroup); //outputing the groups
                                    if (GroupID.isEmpty()) {
                                        emptygroup.setVisibility(View.VISIBLE);
                                    } else {
                                        emptygroup.setVisibility(View.GONE);
                                        //getGroup();
                                    }
                                } else {
                                    Log.d("TAG", "not existed");
                                }
                            }
                        } else {
                            Log.d("TAG", "alab YOU");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", "ERROR GETTING GROUP" + e);
                    }
                });

    }

    private void getGroup() {
        Log.d("TAG", "GROUP ID ->" + GroupID.size());
        //            Log.d("TAG ", "TESTING GROUP ID" + GroupID.get(i));
        //finding a group
        CollectionReference ouputTheGroup = firestore.collection("groups");

        for (int x = 0; x < GroupID.size(); x++) {
            DocumentReference outputmygroup = ouputTheGroup.document(GroupID.get(x));
            outputmygroup
                    .get()
                    .addOnCompleteListener(new OnCompleteListener < DocumentSnapshot > () {
                        @Override
                        public void onComplete(@NonNull Task < DocumentSnapshot > task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    DocumentSnapshot documentReference = task.getResult();
                                    groupModels.add(new GroupModel("sample group ID", documentReference.get("GROUPPHOTO").toString(), documentReference.get("GROUPNAME").toString(), documentReference.get("Description").toString(), documentReference.get("PROFESSOR").toString(), documentReference.get("SUBJECT").toString()));

                                    //adapter.notifyDataSetChanged();
                                } else {
                                    Log.d("TAG", "No such as document");
                                }
                            }
                        }

                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("TAG", "Gorup erro ay " + e);
                        }
                    });
        }

        limitation++;

    }

    @Override
    public void onItemClick(int position, String taskView) {

        Log.d("TAG", "Ang size mo ay: " + firebaseRetriveGroups.get(position).getGroupTitle());

        Intent intent = new Intent(getActivity(), GroupPage.class);
        intent.putExtra("setGroupId", firebaseRetriveGroups.get(position).getGROUPID());
        //        GroupId = groupModels.get(position).getGROUPID();
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