package com.example.hteams.group;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.hteams.MainActivity;
import com.example.hteams.R;
import com.example.hteams.Sample;
import com.example.hteams.adapter.GroupAdapter;
import com.example.hteams.adapter.GroupInterface;
import com.example.hteams.model.GroupModel;

import java.util.ArrayList;

public class Home extends Fragment implements GroupInterface {

    ImageView menu,addgroup;
    ArrayList<GroupModel> groupModels = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);

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
        //dummy data
        String[] Title = {"FireTera","Adroit","9x3","Cube","Group 1","Driven"};
        String[] Description = {"this is short Description", "this is short Description", "this is short Description", "this is short Description", "this is short Description", "this is short Description" };
        String[] Prof = {"Prof Thirdy Gayares", "Prof Novem Lanaban", "Prof Angel Locsin", "Prof Marian Rivera", "Prof. Alex Castro", "Prof. Jonny Sagloria" };
        String[] subject = {"Elective 1", "Softeng", "Discrete", "Modsimu", "HCI", "Algocom" };
        Integer[] image = {R.drawable.profile,R.drawable.avatar1, R.drawable.groupavatar2,R.drawable.groupavatar3, R.drawable.groupavatar4, R.drawable.groupavatar5};


        //from database data
//        ArrayList<String> salesId = new ArrayList<>();
//        ArrayList<String> Time = new ArrayList<>();
//        ArrayList<String> TotalPrice = new ArrayList<>();

//        TODO Firebase Manipulation



        for(int i=0; i<Title.length; i++){
            groupModels.add(new GroupModel(image[i],Title[i],Description[i],Prof[i],subject[i]
            ));
        }


    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getActivity(), "click", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), GroupPage.class);
//        intent.putExtra("salesId", historyDataModels.get(position).getSalesID());
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