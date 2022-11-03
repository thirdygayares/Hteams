package com.example.hteams.group;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hteams.MainActivity;
import com.example.hteams.R;
import com.example.hteams.model.FireBaseParticipant;
import com.example.hteams.model.InviteModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ListOfClassmate extends AppCompatActivity {
    ListView classmateList;
    ArrayList<InviteModel> inviteModels = new ArrayList<>();
//    static  ArrayList< String > Classmate = new ArrayList < > ();
//    static ArrayList< String > holder = new ArrayList < > ();
    FirebaseAuth firebaseAuth;
    static String section;
    TextView college;
    FirebaseFirestore firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_classmate);

//        initialize
        initiatexml();

        //firebase

        firebaseGettingdata();




        //to know the email and uid
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();


        //choose section
        //cyrrent name
        String cname = firebaseAuth.getCurrentUser().getUid();





        choosing();


        MainActivity xx = new MainActivity();

        CustomAdapter customAdapter = new CustomAdapter();
        classmateList.setAdapter(customAdapter);

    }

    private void firebaseGettingdata() {

    }


    private void choosing() {
        classmateList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(Tutorial.class);
                //variable for getting the value of array list when click

                Creategroup xx = new Creategroup();
                String link = String.valueOf(xx.ClassHolder.get(position));
//                Toast.makeText(MainActivity.this,link , Toast.LENGTH_SHORT).show();

                Toast.makeText(ListOfClassmate.this,String.valueOf(xx.id.get(position)), Toast.LENGTH_SHORT).show();


                //passing data to group2  class
                CreateGroup2 createGroup2 = new CreateGroup2();
                createGroup2.Classmate.add(link);

                Intent intent = new Intent(ListOfClassmate.this,CreateGroup2.class);
                startActivity(intent);
            }
        });
    }

    private void initiatexml() {
        classmateList = findViewById(R.id.classmateList);
        college = findViewById(R.id.college);
    }

    private class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {

            Creategroup xx = new Creategroup();
            return xx.ClassHolder.size();
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
            Creategroup xx = new Creategroup();

            title.setText(xx.ClassHolder.get(position));
            return viewl;
        }
    }




    @Override
    public void onStart() {
        super.onStart();


    }

}


