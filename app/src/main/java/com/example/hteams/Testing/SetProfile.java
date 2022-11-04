package com.example.hteams.Testing;

import com.example.hteams.R;

public class SetProfile {

    public int profileImage(String src){

        int x = R.drawable.ic_profile;

        if(src.equalsIgnoreCase("acsad_catindig")){
            x = R.drawable.acsad_catindig;
        }else if(src.equalsIgnoreCase("acds_dantes")){
            x = R.drawable.acsad_dantes;
        }else if(src.equalsIgnoreCase("acsad_despujol")){
            x = R.drawable.acsad_despujol;
        }else if(src.equalsIgnoreCase("acsad_esquivas")){
            x = R.drawable.acsad_esquivas;
        }else if(src.equalsIgnoreCase("acsad_euyoropa")){
            x = R.drawable.acsad_euyoropa;
        }else if(src.equalsIgnoreCase("acsad_macaraeg")){
            x = R.drawable.acsad_macaraeg;
        }else if(src.equalsIgnoreCase("acsad_millares")){
            x = R.drawable.acsad_millares;
        }else if(src.equalsIgnoreCase("acsad_nallos")){
            x = R.drawable.acsad_nallos;
        }else if(src.equalsIgnoreCase("acsad_taruc")){
            x = R.drawable.acsad_taruc;
        }else if(src.equalsIgnoreCase("acsad_vitug")){
            x = R.drawable.acsad_vitug;
        } else if(src.equalsIgnoreCase("acds_abrugar")){
            x = R.drawable.acds_abrugar;
        } else if(src.equalsIgnoreCase("acds_acosta")){
            x = R.drawable.acds_acosta;
        }else if(src.equalsIgnoreCase("acds_andres")){
            x = R.drawable.acds_andres;
        }else if(src.equalsIgnoreCase("acds_bayquen")){
            x = R.drawable.acds_bayquen;
        }else if(src.equalsIgnoreCase("acds_carlos")){
            x = R.drawable.acds_carlos;
        }else if(src.equalsIgnoreCase("acds_carunungan")){
            x = R.drawable.acds_carunungan;
        }else if(src.equalsIgnoreCase("acds_dedil")){
            x = R.drawable.acds_dedil;
        }else if(src.equalsIgnoreCase("acds_ersolada")){
            x = R.drawable.acds_ersolada;
        }else if(src.equalsIgnoreCase("acds_gasidan")){
            x = R.drawable.acds_gasidan;
        }else if(src.equalsIgnoreCase("acds_gayares")){
            x = R.drawable.acds_gayares;
        }else if(src.equalsIgnoreCase("acds_guillergan")){
            x = R.drawable.acds_guillergan;
        }else if(src.equalsIgnoreCase("acds_lanaban")){
            x = R.drawable.acds_lanaban;
        }else if(src.equalsIgnoreCase("acds_legaspi")){
            x = R.drawable.acds_legaspi;
        }else if(src.equalsIgnoreCase("acds_lizardo")){
            x = R.drawable.acds_lizardo;
        }else if(src.equalsIgnoreCase("acds_macatuhay")){
            x = R.drawable.acds_macatuhay;
        }else if(src.equalsIgnoreCase("acds_ochoa")){
            x = R.drawable.acds_ochoa;
        }else if(src.equalsIgnoreCase("acds_pelayo")){
            x = R.drawable.acds_pelayo;
        }else if(src.equalsIgnoreCase("acds_robles")){
            x = R.drawable.acds_robles;
        }else if(src.equalsIgnoreCase("acds_roldan")){
            x = R.drawable.acds_roldan;
        }else if(src.equalsIgnoreCase("acds_sagloria")){
            x = R.drawable.acds_sagloria;
        }else if(src.equalsIgnoreCase("acds_santiago")){
            x = R.drawable.acds_santiago;
        }else if(src.equalsIgnoreCase("acds_valenzuela")){
            x = R.drawable.acds_valenzuela;
        }else if(src.equalsIgnoreCase("acds_vinoya")){
            x = R.drawable.acds_vinoya;
        }else if(src.equalsIgnoreCase("acds_zabala")){
            x = R.drawable.acds_zabala;
        }else{
            x = R.drawable.ic_profile;
        }

        return x;
    };

}
