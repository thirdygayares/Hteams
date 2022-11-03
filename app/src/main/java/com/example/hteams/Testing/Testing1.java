package com.example.hteams.Testing;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hteams.R;
import com.example.hteams.database.DatabaseHelper;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class Testing1 extends AppCompatActivity {
    ArrayList<Testing1Model> testing1Models = new ArrayList<>();

    //status if pwede na maginput ng data
    int startingCDS = 1;//change 1 if okay na naiput na
    int startingACSAD = 1;//change 1 if okay na naiput na
    int startingSubjectCDS = 1; //change 1 if okay na naiput na
    int startingSubjectACSAD = 1; //change 1 if okay na naiput na
    TextView value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing1);

        value = findViewById(R.id.value);

//        CDS();
//        ACSAD();
        SUBJECTCDS();
        SUBJECTACSAD();

        DatabaseHelper databaseHelper = new DatabaseHelper(Testing1.this);
        Cursor cursor = databaseHelper.getSubject("III-ACDS");
        String data = "";
        try {
            while (cursor.moveToNext()) {
                value.append(cursor.getString(2) + "\n" );
                value.append(cursor.getString(1) + "\n" );
            }

        }catch (Exception e){
            value.setText(e.toString());
        }
    }

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

            professor.add("Professor Gilderoy Lockhart");
            professor.add("Professor Neville Longbottom");
            professor.add("Professor Remus Lupin");
            professor.add("Professor Minerva McGonagall");
            professor.add("Professor Alastor Moody");
            professor.add("Professor Quirinus Quirrell");
            professor.add("Professor Aurora Sinistra");
            professor.add("Professor Horace Slughorn");


            try{
                SubjectModel subjectModel;
                for(int i=0; i<professor.size();i++){
                    subjectModel = new SubjectModel( subject.get(i), professor.get(i),section);
                    DatabaseHelper databaseHelper = new DatabaseHelper(Testing1.this);
                    boolean success = databaseHelper.addSubject(subjectModel);
                }
//                Toast.makeText(Testing1.this, "Success", Toast.LENGTH_SHORT).show();
                value.setText("Success");
            }catch (Exception e){
                Toast.makeText(Testing1.this,  String.valueOf(testing1Models.size()), Toast.LENGTH_SHORT).show();
                value.setText("Failed" + e);
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
                    subjectModel = new SubjectModel( subject.get(i), professor.get(i),section);
                    DatabaseHelper databaseHelper = new DatabaseHelper(Testing1.this);
                    boolean success = databaseHelper.addSubject(subjectModel);
                }
//                Toast.makeText(Testing1.this, "Success", Toast.LENGTH_SHORT).show();
                value.setText("Success");
            }catch (Exception e){
                Toast.makeText(Testing1.this,  String.valueOf(testing1Models.size()), Toast.LENGTH_SHORT).show();
                value.setText("Failed" + e);
            }
        }

    }

//    private void ACSAD() {
//        if(startingACSAD == 0) {
//            ArrayList<String> id = new ArrayList<String>();
//            ArrayList<String> studentImage = new ArrayList<String>();
//            ArrayList<String> name = new ArrayList<String>();
//            ArrayList<String> email = new ArrayList<String>();
//            String course = "Computer Science";
//            String section = "III-ACSAD";
//            String college = "CCIS";
//
//
//            id.add("MO4mQ0DgqOT2zsTfza9mtf3N4At1");
//            id.add("ZacQHcZQWVSS8IlpicR61e0kJto1");
//            id.add("Rj3OaCAMNHX4Hx29aHZAx1Z6xwg2");
//            id.add("Fqesc4O3fTelJXarP8HO9y8QqCy2");
//            id.add("9aD47dFuNog249Kiw6HL4BmJ2ot1");
//            id.add("fBsEV450g0buDyZezMkPYRF4zuv1");
//            id.add("KYcoRsFlNLf4UWf2z6udHxone143");
//            id.add("R92WzsKyxxXoDarNheQFq6sNMF72");
//            id.add("pXDBlmR5piPNcHqvx1CL2WfZq6J2");
//            id.add("6XGsSoBQf1TEOs4S0TOZxAPbFVt1");
//
//
//            studentImage.add("acds_catindig");
//            studentImage.add("acds_dantes");
//            studentImage.add("acds_despujol");
//            studentImage.add("acds_esquivas");
//            studentImage.add("acds_euyoropa");
//            studentImage.add("acds_macaraeg");
//            studentImage.add("acds_millares");
//            studentImage.add("acds_nallos");
//            studentImage.add("acds_taruc");
//            studentImage.add("acds_vitug");
//
//
//            name.add("Johnny Catindig");
//            name.add("Hernandes Dantes");
//            name.add("Ari Despujol");
//            name.add("Davis Esquivas");
//            name.add("Carmen Euyoropa");
//            name.add("Tatum Macaraeg");
//            name.add("Xiomarys Millares");
//            name.add("Austin Nallos");
//            name.add("Eufemia Taruc");
//            name.add("Anita Vitug");
//
//
//
//            email.add("johnny@umak.edu.ph");
//            email.add("dantes@umak.edu.ph");
//            email.add("despusol@umak.edu.ph");
//            email.add("esquivas@umak.edu.ph");
//            email.add("euyoropa@umak.edu.ph");
//            email.add("macaraeg@umak.edu.ph");
//            email.add("millares@umak.edu.ph");
//            email.add("nallos@umak.edu.ph");
//            email.add("taruc@umak.edu.ph");
//            email.add("vitug@umak.edu.ph");
//
//
//            try{
//                Testing1Model test;
//                for(int i=0; i<id.size();i++){
//                    test = new Testing1Model(id.get(i), studentImage.get(i),name.get(i),email.get(i),section,course,college );
//                    DatabaseHelper databaseHelper = new DatabaseHelper(Testing1.this);
//                    boolean success = databaseHelper.addStudents(test);
//                }
////                Toast.makeText(Testing1.this, "Success", Toast.LENGTH_SHORT).show();
//                value.setText("Success");
//            }catch (Exception e){
//                Toast.makeText(Testing1.this,  String.valueOf(testing1Models.size()), Toast.LENGTH_SHORT).show();
//                value.setText("Failed" + e);
//            }
//        }
//
//
//        }
//
//    private void CDS() {
//        if(startingCDS == 0){
//            ArrayList<String> id = new ArrayList<String>();
//            ArrayList<String> studentImage = new ArrayList<String>();
//            ArrayList<String> name = new ArrayList<String>();
//            ArrayList<String> email = new ArrayList<String>();
//            String course = "Computer Science";
//            String section = "III-ACDS";
//            String college = "CCIS";
//
//            id.add("Bt1p3ewNStbrnpPySWUBDwdnCAo2");
//            id.add("LOejXCJoPNXViEbNWqcRuHFWq7X2");
//            id.add("eqOFgfG3RIfZPsrC7NIuIwmDFxt2");
//            id.add("Qw9YfU3QLqYEUuBfRkFRNb4evJh2");
//            id.add("ND2I3Z5gyjXX5hgwEs8h5zEjHCh2");
//            id.add("DmIWZ8daT8Q9GTjykgx1zPfdvhj1");
//            id.add("ptjs67U2nIdtVVN9jWg3Il1P1h13");
//            id.add("YKXOR0OWPEYIJJ7AE5B6H4Efh8h2");
//            id.add("z9aZaM97R1MaBN80T30SDzcdkBk1");
//            id.add("LTGgitcP8xQvgKVmFafmztNlZ822");
//            id.add("13UepTNELdNXuaY8PCmUqbjn9fj2");
//            id.add("SM5ephLRNXdpOjhpHakGwV1M5Ov2");
//            id.add("JjaudB56Mhg9v81rLjRzCIC4js92");
//            id.add("TrGuZtsCaXMXK3T5F2hkk6YwP4j2");
//            id.add("5e17yAUqmnR7IVjUsoppgijjI1m2");
//            id.add("Ehb4bWNnJ4cpMb7wT8udliwD07V2");
//            id.add("33GL1ck7L2buSvi1zle2ce8GJPw2");
//            id.add("gdhcLtd6IoONMU6ROgDg4JwSRIF3");
//            id.add("BO0TnPnqYnc6egdv3rQKQkDw9Ou1");
//            id.add("DAl89sUZpoPV8CiprrI0Xlr3vyC2");
//            id.add("sPWYzxkjn0ThohOOrt5LPcAMONm2");
//            id.add("cXN3cJ6smWWwNoYUM3uceQIRc5z2");
//            id.add("DTnVf7XyySdGC1yYriwAhFF059R2");
//            id.add("C56TFZLcANUWTHizp1IQ0woZzxY2");
//
//            studentImage.add("acds_abrugar");
//            studentImage.add("acds_acosta");
//            studentImage.add("acds_andres");
//            studentImage.add("acds_bayquen");
//            studentImage.add("acds_carlos");
//            studentImage.add("acds_carunungan");
//            studentImage.add("acds_dedil");
//            studentImage.add("acds_ersolada");
//            studentImage.add("acds_gasidan");
//            studentImage.add("acds_gayares");
//            studentImage.add("acds_guillergan");
//            studentImage.add("acds_lanaban");
//            studentImage.add("acds_legaspi");
//            studentImage.add("acds_lizardo");
//            studentImage.add("acds_macatuhay");
//            studentImage.add("acds_ochoa");
//            studentImage.add("acds_pelayo");
//            studentImage.add("acds_robles");
//            studentImage.add("acds_roldan");
//            studentImage.add("acds_sagloria");
//            studentImage.add("acds_santiago");
//            studentImage.add("acds_valenzuela");
//            studentImage.add("acds_vinoya");
//            studentImage.add("acds_zabala");
//
//            name.add("Ronabel Abrugar");
//            name.add("Ryan Jay Acosta");
//            name.add("Ron Francis Andres");
//            name.add("Howard Bayquen");
//            name.add("Athaleen Carlos");
//            name.add("Justine Louise Carunungan");
//            name.add("Mhallonie kim dedil");
//            name.add("Reynan Ersolada");
//            name.add("Jose Renan Gasidan");
//            name.add("Jose Gayares III");
//            name.add("Raiden Guillergan");
//            name.add("Novem Jan Ydzel Lanaban");
//            name.add("John Simon Legaspi");
//            name.add("Honey Lei Lizardo");
//            name.add("Angel Kaye Macatuhay");
//            name.add("Carl Dominic Ochoa");
//            name.add("Christine Joy Pelayo");
//            name.add("John Alex Robles");
//            name.add("Joricel Roldan");
//            name.add("Jonny Sagloria");
//            name.add("Lyko Miguel Santiago");
//            name.add("Ronnielyn Valenzuela");
//            name.add("Shaina Vinoya");
//            name.add("Marielle Zabala");
//
//            email.add("rabrugar.k11832936@umak.edu.ph");
//            email.add("racosta.a12034766@umak.edu.ph");
//            email.add("randres.k11829786@umak.edu.ph");
//            email.add("hbayquen.k11831480@umak.edu.ph");
//            email.add("acarlos.k11833152@umak.edu.ph");
//            email.add("jcarunungan.k11830199@umak.edu.ph");
//            email.add("mdedil.a12032659@umak.edu.ph");
//            email.add("rersolada.a12034774@umak.edu.ph");
//            email.add("jgasidan.k11832117@umak.edu.ph");
//            email.add("jgayares.a12034879@umak.edu.ph");
//            email.add("rguillergan.k11833666@umak.edu.ph");
//            email.add("nlanaban.a12032676@umak.edu.ph");
//            email.add("jlegaspi.k11832742@umak.edu.ph");
//            email.add("hlizardo.a12032660@umak.edu.ph");
//            email.add("amacatuhay.k11831669@umak.edu.ph");
//            email.add("cochoa.k11832103@umak.edu.ph");
//            email.add("cpelayo.k11723427@umak.edu.ph");
//            email.add("jrobles.a12035738@umak.edu.ph");
//            email.add("jroldan.a12034796@umak.edu.ph");
//            email.add("jsagloria.a12033986@umak.edu.ph");
//            email.add("lsantiago.a12034882@umak.edu.ph");
//            email.add("rvalenzuela.a12032655@umak.edu.ph");
//            email.add("svinoya.k11835262@umak.edu.ph");
//            email.add("mzabala.k11834159@umak.edu.ph");
//
//
//
////       public Testing1Model(String ID_STUDENTS, String STUDENTS_IMAGE, String NAME, String EMAIL, String SECTION, String COURSE, String COLLEGE) {
//
//
//
//            try{
//                Testing1Model test;
//                for(int i=0; i<id.size();i++){
//                    test = new Testing1Model(id.get(i), studentImage.get(i),name.get(i),email.get(i),section,course,college );
//                    DatabaseHelper databaseHelper = new DatabaseHelper(Testing1.this);
//                    boolean success = databaseHelper.addStudents(test);
//                }
////                Toast.makeText(Testing1.this, "Success", Toast.LENGTH_SHORT).show();
//                value.setText("Success");
//            }catch (Exception e){
//               Toast.makeText(Testing1.this,  String.valueOf(testing1Models.size()), Toast.LENGTH_SHORT).show();
//                value.setText("Failed" + e);
//            }
//
//
//        }
//
//    }

}