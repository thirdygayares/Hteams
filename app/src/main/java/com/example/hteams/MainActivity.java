package com.example.hteams;



import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hteams.Testing.SubjectModel;
import com.example.hteams.Testing.Testing1Model;
import com.example.hteams.database.DatabaseHelper;
import com.example.hteams.group.Home;
import com.example.hteams.notification.Notification;
import com.example.hteams.profile.Profile;
import com.example.hteams.task.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String TAG = "jose";
    String thirdy = "thirdy";
    EditText input;
    Button button,Logout;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    DatabaseHelper databaseHelper;

//    list model of students
    ArrayList<Testing1Model> testing1Models = new ArrayList<>();

    int startingCDS = 1;//change 1 if okay na naiput na
    int startingACSAD = 1;//change 1 if okay na naiput na
    int startingSubjectCDS = 1; //change 1 if okay na naiput na
    int startingSubjectACSAD = 1; //change 1 if okay na naiput na



    //bitmap



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(navListener);

        //database check if may laman or walang laman
        databaseHelper = new DatabaseHelper(MainActivity.this);
        Cursor cursor = databaseHelper.getListContents("III-ACSAD");
//        Toast.makeText(MainActivity.this,  String.valueOf(cursor.getCount()), Toast.LENGTH_SHORT).show();
        if(cursor.getCount()==0 ){
            startingCDS = 0;
            startingACSAD = 0;
            startingSubjectCDS = 0;
            startingSubjectACSAD = 0;
//      Toast.makeText(MainActivity.this,  "0", Toast.LENGTH_SHORT).show();
        }else{
            startingCDS = 1;
            startingACSAD = 1;
            startingSubjectCDS = 1;
            startingSubjectACSAD = 1;

//        Toast.makeText(MainActivity.this,  "Dinababasa", Toast.LENGTH_SHORT).show();

        }


        int x = 5;
        Intent intent = getIntent();
//        x = Integer.parseInt(intent.getStringExtra("going"));

        Fragment selectedFragment = new Home();

        if (x == 0) {
            bottomNav.setSelectedItemId(R.id.nav_home);

            selectedFragment = new Task();
        }else if(x == 1){
            bottomNav.setSelectedItemId(R.id.nav_task);
            selectedFragment = new Notification();
        }else if(x == 2){
            bottomNav.setSelectedItemId(R.id.nav_notification);
            selectedFragment = new Profile();
        }else if(x == 3){
            bottomNav.setSelectedItemId(R.id.nav_profile);
            selectedFragment = new Profile();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                selectedFragment).commit();

        firebaseGettingData();

        //method data list of data
        dataEntry();
    }



    private NavigationBarView.OnItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;
                switch (item.getItemId()){
                    case R.id.nav_home:
                        selectedFragment = new Home();
                        break;
                    case R.id.nav_task:
//                            Toast.makeText(MainActivity.this, "stocks", Toast.LENGTH_SHORT).show();
                        selectedFragment = new Task();
                        break;
                    case R.id.nav_notification:
                        selectedFragment = new Notification();
                        break;
                    case R.id.nav_profile:
                        selectedFragment = new Profile();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                return true;
            };

    private void firebaseGettingData() {


    }


    private void dataEntry() {
        CDS();
        ACSAD();
        SUBJECTCDS();
        SUBJECTACSAD();
    }

    //if the database is empty tsaka palang magpeperform eto
    private void SUBJECTACSAD() {

        if(startingSubjectACSAD == 0) {
            ArrayList<String> subject = new ArrayList<String>();
            ArrayList<String> professor = new ArrayList<String>();
            String section = "III-ACSAD";

            subject.add("DATASTRU");
            subject.add("DATMIN");
            subject.add("CALCSS");
            subject.add("MODPHY");
            subject.add("GECW");
            subject.add("GEE");
            subject.add("GEUS");
            subject.add("GERPH");

            professor.add("Gilderoy Lockhart");
            professor.add("Neville Longbottom");
            professor.add("Remus Lupin");
            professor.add("Minerva McGonagall");
            professor.add("Alastor Moody");
            professor.add("Quirinus Quirrell");
            professor.add("Aurora Sinistra");
            professor.add("Horace Slughorn");


            try{
                SubjectModel subjectModel;
                for(int i=0; i<professor.size();i++){
                    subjectModel = new SubjectModel( subject.get(i), professor.get(i),section);
                    DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                    boolean success = databaseHelper.addSubject(subjectModel);
                }
//                Toast.makeText(Testing1.this, "Success", Toast.LENGTH_SHORT).show();
//                value.setText("Success");
            }catch (Exception e){
                Toast.makeText(MainActivity.this,  String.valueOf(e), Toast.LENGTH_SHORT).show();
//                value.setText("Failed" + e);
            }
        }

    }

    private void SUBJECTCDS() {
        if(startingSubjectCDS == 0) {
            ArrayList<String> subject = new ArrayList<String>();
            ArrayList<String> professor = new ArrayList<String>();
            String section = "III-ACDS";


            subject.add("Elective 1");
            subject.add("HCI");
            subject.add("SOFTENG 1");
            subject.add("IAS");
            subject.add("DISCSTRU 2");
            subject.add("ALGOCOM");
            subject.add("OPERSYS");
            subject.add("MODSIMU");


            professor.add("Eddie Jessup");
            professor.add("Robert Langdon");
            professor.add("William Dyer");
            professor.add("Gervase Fen");
            professor.add("Digory Kirke");
            professor.add("James Dunworthy");
            professor.add("Abraham Van Helsing");
            professor.add("Hari Seldon");


            try{
                SubjectModel subjectModel;
                for(int i=0; i<professor.size();i++){
                    subjectModel = new SubjectModel(subject.get(i), professor.get(i),section);
                    DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                    boolean success = databaseHelper.addSubject(subjectModel);
                }
//                Toast.makeText(Testing1.this, "Success", Toast.LENGTH_SHORT).show();
//                value.setText("Success");
            }catch (Exception e){
                Toast.makeText(MainActivity.this,  String.valueOf(testing1Models.size()), Toast.LENGTH_SHORT).show();
//                value.setText("Failed" + e);
            }
        }

    }

    private void ACSAD() {
        if(startingACSAD == 0) {
                ArrayList<String> id = new ArrayList<String>();
                ArrayList<String> studentImage = new ArrayList<String>();
                ArrayList<String> name = new ArrayList<String>();
                ArrayList<String> email = new ArrayList<String>();
                ArrayList<byte[]> studentProfile = new ArrayList<>();
                String course = "Computer Science";
                String section = "III-ACSAD";
                String college = "CCIS";

                id.add("MO4mQ0DgqOT2zsTfza9mtf3N4At1");
                id.add("ZacQHcZQWVSS8IlpicR61e0kJto1");
                id.add("Rj3OaCAMNHX4Hx29aHZAx1Z6xwg2");
                id.add("Fqesc4O3fTelJXarP8HO9y8QqCy2");
                id.add("9aD47dFuNog249Kiw6HL4BmJ2ot1");
                id.add("fBsEV450g0buDyZezMkPYRF4zuv1");
                id.add("KYcoRsFlNLf4UWf2z6udHxone143");
                id.add("R92WzsKyxxXoDarNheQFq6sNMF72");
                id.add("pXDBlmR5piPNcHqvx1CL2WfZq6J2");
                id.add("6XGsSoBQf1TEOs4S0TOZxAPbFVt1");

                //change this
                studentImage.add("acsad_catindig");
                studentImage.add("acds_dantes");
                studentImage.add("acsad_despujol");
                studentImage.add("acsad_esquivas");
                studentImage.add("acsad_euyoropa");
                studentImage.add("acsad_macaraeg");
                studentImage.add("acsad_millares");
                studentImage.add("acsad_nallos");
                studentImage.add("acsad_taruc");
                studentImage.add("acsad_vitug");

                Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(),R.drawable.acsad_catindig);
                ByteArrayOutputStream byteArray1 = new ByteArrayOutputStream();
                bitmap1.compress(Bitmap.CompressFormat.PNG, 75,byteArray1);
                byte[] img1 = byteArray1.toByteArray();


                Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(),R.drawable.acsad_dantes);
                ByteArrayOutputStream byteArray2 = new ByteArrayOutputStream();
                bitmap2.compress(Bitmap.CompressFormat.PNG, 75,byteArray2);
                byte[] img2 = byteArray2.toByteArray();

                Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(),R.drawable.acsad_despujol);
                ByteArrayOutputStream byteArray3 = new ByteArrayOutputStream();
                bitmap3.compress(Bitmap.CompressFormat.PNG, 75,byteArray3);
                byte[] img3 = byteArray3.toByteArray();

                Bitmap bitmap4 = BitmapFactory.decodeResource(getResources(),R.drawable.acsad_esquivas);
                ByteArrayOutputStream byteArray4 = new ByteArrayOutputStream();
                bitmap4.compress(Bitmap.CompressFormat.PNG, 75,byteArray4);
                byte[] img4 = byteArray4.toByteArray();

                Bitmap bitmap5 = BitmapFactory.decodeResource(getResources(),R.drawable.acsad_euyoropa);
                ByteArrayOutputStream byteArray5 = new ByteArrayOutputStream();
                bitmap5.compress(Bitmap.CompressFormat.PNG, 75,byteArray5);
                byte[] img5 = byteArray5.toByteArray();

                Bitmap bitmap6 = BitmapFactory.decodeResource(getResources(),R.drawable.acsad_macaraeg);
                ByteArrayOutputStream byteArray6 = new ByteArrayOutputStream();
                bitmap6.compress(Bitmap.CompressFormat.PNG, 75,byteArray6);
                byte[] img6 = byteArray6.toByteArray();

            Bitmap bitmap7 = BitmapFactory.decodeResource(getResources(),R.drawable.acsad_millares);
            ByteArrayOutputStream byteArray7 = new ByteArrayOutputStream();
            bitmap7.compress(Bitmap.CompressFormat.PNG, 75,byteArray7);
            byte[] img7 = byteArray7.toByteArray();

            Bitmap bitmap8 = BitmapFactory.decodeResource(getResources(),R.drawable.acsad_nallos);
            ByteArrayOutputStream byteArray8 = new ByteArrayOutputStream();
            bitmap8.compress(Bitmap.CompressFormat.PNG, 75,byteArray8);
            byte[] img8 = byteArray8.toByteArray();

            Bitmap bitmap9 = BitmapFactory.decodeResource(getResources(),R.drawable.acsad_taruc);
            ByteArrayOutputStream byteArray9 = new ByteArrayOutputStream();
            bitmap9.compress(Bitmap.CompressFormat.PNG, 75,byteArray9);
            byte[] img9 = byteArray9.toByteArray();

            Bitmap bitmap10 = BitmapFactory.decodeResource(getResources(),R.drawable.acsad_vitug);
            ByteArrayOutputStream byteArray10 = new ByteArrayOutputStream();
            bitmap10.compress(Bitmap.CompressFormat.PNG, 75,byteArray10);
            byte[] img10 = byteArray10.toByteArray();

            studentProfile.add(img1);
            studentProfile.add(img2);
            studentProfile.add(img3);
            studentProfile.add(img4);
            studentProfile.add(img5);
            studentProfile.add(img6);
            studentProfile.add(img7);
            studentProfile.add(img8);
            studentProfile.add(img9);
            studentProfile.add(img10);

            name.add("Johnny Catindig");
                name.add("Hernandes Dantes");
                name.add("Ari Despujol");
                name.add("Davis Esquivas");
                name.add("Carmen Euyoropa");
                name.add("Tatum Macaraeg");
                name.add("Xiomarys Millares");
                name.add("Austin Nallos");
                name.add("Eufemia Taruc");
                name.add("Anita Vitug");

                email.add("johnny@umak.edu.ph");
                email.add("dantes@umak.edu.ph");
                email.add("despusol@umak.edu.ph");
                email.add("esquivas@umak.edu.ph");
                email.add("euyoropa@umak.edu.ph");
                email.add("macaraeg@umak.edu.ph");
                email.add("millares@umak.edu.ph");
                email.add("nallos@umak.edu.ph");
                email.add("taruc@umak.edu.ph");
                email.add("vitug@umak.edu.ph");


                try{
                    Testing1Model test;
                    for(int i=0; i<id.size();i++){
                        test = new Testing1Model(id.get(i), studentProfile.get(i),name.get(i),email.get(i),section,course,college );
                        DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                        boolean success = databaseHelper.addStudents(test);
                    }
    //                Toast.makeText(Testing1.this, "Success", Toast.LENGTH_SHORT).show();
    //                value.setText("Success");
                }catch (Exception e){
                    Toast.makeText(MainActivity.this,  String.valueOf(testing1Models.size()), Toast.LENGTH_SHORT).show();
    //                value.setText("Failed" + e);
                }
        }


    }

    private void CDS() {
        if(startingCDS == 0){
            ArrayList<String> id = new ArrayList<String>();
            ArrayList<String> studentImage = new ArrayList<String>();
            ArrayList<String> name = new ArrayList<String>();
            ArrayList<String> email = new ArrayList<String>();
            ArrayList<byte[]> studentProfile = new ArrayList<>();
            String course = "Computer Science";
            String section = "III-ACDS";
            String college = "CCIS";

            id.add("Bt1p3ewNStbrnpPySWUBDwdnCAo2");
            id.add("LOejXCJoPNXViEbNWqcRuHFWq7X2");
            id.add("eqOFgfG3RIfZPsrC7NIuIwmDFxt2");
            id.add("Qw9YfU3QLqYEUuBfRkFRNb4evJh2");
            id.add("ND2I3Z5gyjXX5hgwEs8h5zEjHCh2");
            id.add("DmIWZ8daT8Q9GTjykgx1zPfdvhj1");
            id.add("ptjs67U2nIdtVVN9jWg3Il1P1h13");
            id.add("YKXOR0OWPEYIJJ7AE5B6H4Efh8h2");
            id.add("z9aZaM97R1MaBN80T30SDzcdkBk1");
            id.add("LTGgitcP8xQvgKVmFafmztNlZ822");
            id.add("13UepTNELdNXuaY8PCmUqbjn9fj2");
            id.add("SM5ephLRNXdpOjhpHakGwV1M5Ov2");
            id.add("JjaudB56Mhg9v81rLjRzCIC4js92");
            id.add("TrGuZtsCaXMXK3T5F2hkk6YwP4j2");
            id.add("5e17yAUqmnR7IVjUsoppgijjI1m2");
            id.add("Ehb4bWNnJ4cpMb7wT8udliwD07V2");
            id.add("33GL1ck7L2buSvi1zle2ce8GJPw2");
            id.add("gdhcLtd6IoONMU6ROgDg4JwSRIF3");
            id.add("BO0TnPnqYnc6egdv3rQKQkDw9Ou1");
            id.add("DAl89sUZpoPV8CiprrI0Xlr3vyC2");
            id.add("sPWYzxkjn0ThohOOrt5LPcAMONm2");
            id.add("cXN3cJ6smWWwNoYUM3uceQIRc5z2");
            id.add("DTnVf7XyySdGC1yYriwAhFF059R2");
            id.add("C56TFZLcANUWTHizp1IQ0woZzxY2");

            studentImage.add("acds_abrugar");
            studentImage.add("acds_acosta");
            studentImage.add("acds_andres");
            studentImage.add("acds_bayquen");
            studentImage.add("acds_carlos");
            studentImage.add("acds_carunungan");
            studentImage.add("acds_dedil");
            studentImage.add("acds_ersolada");
            studentImage.add("acds_gasidan");
            studentImage.add("acds_gayares");
            studentImage.add("acds_guillergan");
            studentImage.add("acds_lanaban");
            studentImage.add("acds_legaspi");
            studentImage.add("acds_lizardo");
            studentImage.add("acds_macatuhay");
            studentImage.add("acds_ochoa");
            studentImage.add("acds_pelayo");
            studentImage.add("acds_robles");
            studentImage.add("acds_roldan");
            studentImage.add("acds_sagloria");
            studentImage.add("acds_santiago");
            studentImage.add("acds_valenzuela");
            studentImage.add("acds_vinoya");
            studentImage.add("acds_zabala");


            Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(),R.drawable.acds_abrugar);
            ByteArrayOutputStream byteArray1 = new ByteArrayOutputStream();
            bitmap1.compress(Bitmap.CompressFormat.PNG, 75,byteArray1);
            byte[] img1 = byteArray1.toByteArray();


            Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(),R.drawable.acds_acosta);
            ByteArrayOutputStream byteArray2 = new ByteArrayOutputStream();
            bitmap2.compress(Bitmap.CompressFormat.PNG, 75,byteArray2);
            byte[] img2 = byteArray2.toByteArray();

            Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(),R.drawable.acds_andres);
            ByteArrayOutputStream byteArray3 = new ByteArrayOutputStream();
            bitmap3.compress(Bitmap.CompressFormat.PNG, 75,byteArray3);
            byte[] img3 = byteArray3.toByteArray();

            Bitmap bitmap4 = BitmapFactory.decodeResource(getResources(),R.drawable.acds_bayquen);
            ByteArrayOutputStream byteArray4 = new ByteArrayOutputStream();
            bitmap4.compress(Bitmap.CompressFormat.PNG, 75,byteArray4);
            byte[] img4 = byteArray4.toByteArray();

            Bitmap bitmap5 = BitmapFactory.decodeResource(getResources(),R.drawable.acds_carlos);
            ByteArrayOutputStream byteArray5 = new ByteArrayOutputStream();
            bitmap5.compress(Bitmap.CompressFormat.PNG, 75,byteArray5);
            byte[] img5 = byteArray5.toByteArray();

            Bitmap bitmap6 = BitmapFactory.decodeResource(getResources(),R.drawable.acds_carunungan);
            ByteArrayOutputStream byteArray6 = new ByteArrayOutputStream();
            bitmap6.compress(Bitmap.CompressFormat.PNG, 75,byteArray6);
            byte[] img6 = byteArray6.toByteArray();

            Bitmap bitmap7 = BitmapFactory.decodeResource(getResources(),R.drawable.acds_dedil);
            ByteArrayOutputStream byteArray7 = new ByteArrayOutputStream();
            bitmap7.compress(Bitmap.CompressFormat.PNG, 75,byteArray7);
            byte[] img7 = byteArray7.toByteArray();

            Bitmap bitmap8 = BitmapFactory.decodeResource(getResources(),R.drawable.acds_ersolada);
            ByteArrayOutputStream byteArray8 = new ByteArrayOutputStream();
            bitmap8.compress(Bitmap.CompressFormat.PNG, 75,byteArray8);
            byte[] img8 = byteArray8.toByteArray();


            Bitmap bitmap100 = BitmapFactory.decodeResource(getResources(),R.drawable.acds_gasidan);
            ByteArrayOutputStream byteArray100 = new ByteArrayOutputStream();
            bitmap100.compress(Bitmap.CompressFormat.PNG, 75,byteArray100);
            byte[] img9 = byteArray100.toByteArray();


            Bitmap bitmap10 = BitmapFactory.decodeResource(getResources(),R.drawable.acds_gayares);
            ByteArrayOutputStream byteArray10 = new ByteArrayOutputStream();
            bitmap10.compress(Bitmap.CompressFormat.PNG, 75,byteArray10);
            byte[] img10 = byteArray10.toByteArray();


            Bitmap bitmap11 = BitmapFactory.decodeResource(getResources(),R.drawable.acds_guillergan);
            ByteArrayOutputStream byteArray11 = new ByteArrayOutputStream();
            bitmap11.compress(Bitmap.CompressFormat.PNG, 75,byteArray11);
            byte[] img11 = byteArray11.toByteArray();


            Bitmap bitmap12 = BitmapFactory.decodeResource(getResources(),R.drawable.acds_lanaban);
            ByteArrayOutputStream byteArray12 = new ByteArrayOutputStream();
            bitmap12.compress(Bitmap.CompressFormat.PNG, 75,byteArray12);
            byte[] img12 = byteArray12.toByteArray();

            Bitmap bitmap13 = BitmapFactory.decodeResource(getResources(),R.drawable.acds_legaspi);
            ByteArrayOutputStream byteArray13 = new ByteArrayOutputStream();
            bitmap13.compress(Bitmap.CompressFormat.PNG, 75,byteArray13);
            byte[] img13 = byteArray13.toByteArray();

            Bitmap bitmap14 = BitmapFactory.decodeResource(getResources(),R.drawable.acds_lizardo);
            ByteArrayOutputStream byteArray14 = new ByteArrayOutputStream();
            bitmap14.compress(Bitmap.CompressFormat.PNG, 75,byteArray14);
            byte[] img14 = byteArray14.toByteArray();

            Bitmap bitmap15 = BitmapFactory.decodeResource(getResources(),R.drawable.acds_macatuhay);
            ByteArrayOutputStream byteArray15 = new ByteArrayOutputStream();
            bitmap15.compress(Bitmap.CompressFormat.PNG, 75,byteArray15);
            byte[] img15 = byteArray15.toByteArray();

            Bitmap bitmap16 = BitmapFactory.decodeResource(getResources(),R.drawable.acds_ochoa);
            ByteArrayOutputStream byteArray16 = new ByteArrayOutputStream();
            bitmap6.compress(Bitmap.CompressFormat.PNG, 75,byteArray16);
            byte[] img16 = byteArray16.toByteArray();

            Bitmap bitmap17 = BitmapFactory.decodeResource(getResources(),R.drawable.acds_pelayo);
            ByteArrayOutputStream byteArray17 = new ByteArrayOutputStream();
            bitmap17.compress(Bitmap.CompressFormat.PNG, 75,byteArray17);
            byte[] img17 = byteArray17.toByteArray();

            Bitmap bitmap18 = BitmapFactory.decodeResource(getResources(),R.drawable.acds_robles);
            ByteArrayOutputStream byteArray18 = new ByteArrayOutputStream();
            bitmap18.compress(Bitmap.CompressFormat.PNG, 75,byteArray18);
            byte[] img18 = byteArray18.toByteArray();


            Bitmap bitmap19 = BitmapFactory.decodeResource(getResources(),R.drawable.acds_roldan);
            ByteArrayOutputStream byteArray19 = new ByteArrayOutputStream();
            bitmap19.compress(Bitmap.CompressFormat.PNG, 75,byteArray19);
            byte[] img19 = byteArray19.toByteArray();


            Bitmap bitmap20 = BitmapFactory.decodeResource(getResources(),R.drawable.acds_sagloria);
            ByteArrayOutputStream byteArray20 = new ByteArrayOutputStream();
            bitmap20.compress(Bitmap.CompressFormat.PNG, 75,byteArray20);
            byte[] img20 = byteArray20.toByteArray();


            Bitmap bitmap21 = BitmapFactory.decodeResource(getResources(),R.drawable.acds_santiago);
            ByteArrayOutputStream byteArray21 = new ByteArrayOutputStream();
            bitmap21.compress(Bitmap.CompressFormat.PNG, 75,byteArray21);
            byte[] img21 = byteArray21.toByteArray();

            Bitmap bitmap22 = BitmapFactory.decodeResource(getResources(),R.drawable.acds_valenzuela);
            ByteArrayOutputStream byteArray22 = new ByteArrayOutputStream();
            bitmap22.compress(Bitmap.CompressFormat.PNG, 75,byteArray22);
            byte[] img22 = byteArray22.toByteArray();


            Bitmap bitmap23 = BitmapFactory.decodeResource(getResources(),R.drawable.acds_vinoya);
            ByteArrayOutputStream byteArray23 = new ByteArrayOutputStream();
            bitmap23.compress(Bitmap.CompressFormat.PNG, 75,byteArray23);
            byte[] img23 = byteArray23.toByteArray();


            Bitmap bitmap24 = BitmapFactory.decodeResource(getResources(),R.drawable.acds_zabala);
            ByteArrayOutputStream byteArray24 = new ByteArrayOutputStream();
            bitmap24.compress(Bitmap.CompressFormat.PNG, 75,byteArray24);
            byte[] img24 = byteArray24.toByteArray();



            name.add("Ronabel Abrugar");
            name.add("Ryan Jay Acosta");
            name.add("Ron Francis Andres");
            name.add("Howard Bayquen");
            name.add("Athaleen Carlos");
            name.add("Justine Louise Carunungan");
            name.add("Mhallonie kim dedil");
            name.add("Reynan Ersolada");
            name.add("Jose Renan Gasidan");
            name.add("Jose Gayares III");
            name.add("Raiden Guillergan");
            name.add("Novem Jan Ydzel Lanaban");
            name.add("John Simon Legaspi");
            name.add("Honey Lei Lizardo");
            name.add("Angel Kaye Macatuhay");
            name.add("Carl Dominic Ochoa");
            name.add("Christine Joy Pelayo");
            name.add("John Alex Robles");
            name.add("Joricel Roldan");
            name.add("Jonny Sagloria");
            name.add("Lyko Miguel Santiago");
            name.add("Ronnielyn Valenzuela");
            name.add("Shaina Vinoya");
            name.add("Marielle Zabala");

            email.add("rabrugar.k11832936@umak.edu.ph");
            email.add("racosta.a12034766@umak.edu.ph");
            email.add("randres.k11829786@umak.edu.ph");
            email.add("hbayquen.k11831480@umak.edu.ph");
            email.add("acarlos.k11833152@umak.edu.ph");
            email.add("jcarunungan.k11830199@umak.edu.ph");
            email.add("mdedil.a12032659@umak.edu.ph");
            email.add("rersolada.a12034774@umak.edu.ph");
            email.add("jgasidan.k11832117@umak.edu.ph");
            email.add("jgayares.a12034879@umak.edu.ph");
            email.add("rguillergan.k11833666@umak.edu.ph");
            email.add("nlanaban.a12032676@umak.edu.ph");
            email.add("jlegaspi.k11832742@umak.edu.ph");
            email.add("hlizardo.a12032660@umak.edu.ph");
            email.add("amacatuhay.k11831669@umak.edu.ph");
            email.add("cochoa.k11832103@umak.edu.ph");
            email.add("cpelayo.k11723427@umak.edu.ph");
            email.add("jrobles.a12035738@umak.edu.ph");
            email.add("jroldan.a12034796@umak.edu.ph");
            email.add("jsagloria.a12033986@umak.edu.ph");
            email.add("lsantiago.a12034882@umak.edu.ph");
            email.add("rvalenzuela.a12032655@umak.edu.ph");
            email.add("svinoya.k11835262@umak.edu.ph");
            email.add("mzabala.k11834159@umak.edu.ph");



//       public Testing1Model(String ID_STUDENTS, String STUDENTS_IMAGE, String NAME, String EMAIL, String SECTION, String COURSE, String COLLEGE) {

            try{
                Testing1Model test;
                for(int i=0; i<id.size();i++){
                    test = new Testing1Model(id.get(i), studentProfile.get(i),name.get(i),email.get(i),section,course,college );
                    DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                    boolean success = databaseHelper.addStudents(test);
                }
//                Toast.makeText(Testing1.this, "Success", Toast.LENGTH_SHORT).show();
//                value.setText("Success");
            }catch (Exception e){
                Toast.makeText(MainActivity.this,  String.valueOf(testing1Models.size()), Toast.LENGTH_SHORT).show();
//                value.setText("Failed" + e);
            }


        }

    }









}