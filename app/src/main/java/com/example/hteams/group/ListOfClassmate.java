package com.example.hteams.group;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hteams.R;
import com.example.hteams.model.InviteModel;

import java.util.ArrayList;

public class ListOfClassmate extends AppCompatActivity {
    ListView classmateList;
    ArrayList<InviteModel> inviteModels = new ArrayList<>();
    ArrayList< String > Classmate = new ArrayList < > ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_classmate);

//        initialize
        initiatexml();

        //when click to listview
        choosing();

        Classmate.add("Jonny Sagloria");
        Classmate.add("Novem Lanaban");
        Classmate.add("Jose Renan Gasidan");
        Classmate.add("Angel Macatuhay");
        Classmate.add("Marielle Zabala");
        Classmate.add("Justine Louise Carunungan");
        Classmate.add("Raiden Guillergan");
        Classmate.add("Christie Pelayo");
        Classmate.add("Joricel Roldan");


        CustomAdapter customAdapter = new CustomAdapter();
        classmateList.setAdapter(customAdapter);
    }

    private void choosing() {
        classmateList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(Tutorial.class);
                //variable for getting the value of array list when click
                String link = String.valueOf(Classmate.get(position));
//                Toast.makeText(MainActivity.this,link , Toast.LENGTH_SHORT).show();


                //test1
                //inviteModels.add(new InviteModel(link));

                //TODO test 2
                CreateGroup2 createGroup2 = new CreateGroup2();
                createGroup2.Classmate.add(link);

                Intent intent = new Intent(ListOfClassmate.this,CreateGroup2.class);
                startActivity(intent);
            }
        });
    }

    private void initiatexml() {
        classmateList = findViewById(R.id.classmateList);
    }

    private class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return Classmate.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View viewl = getLayoutInflater().inflate(R.layout.listviewclassmateinvite, null);
            TextView title = viewl.findViewById(R.id.classmate_name);
            title.setText(Classmate.get(position));
            return viewl;
        }
    }


}