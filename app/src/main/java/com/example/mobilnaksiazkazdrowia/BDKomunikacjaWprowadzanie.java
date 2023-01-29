package com.example.mobilnaksiazkazdrowia;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

public class BDKomunikacjaWprowadzanie extends Thread{

    Activity aktywnosc;
    BDKomunikacjaCel bdKomunikacjaCel;
    WebView phpUruchom;
    String[] argumenty;

    BDKomunikacjaWprowadzanie(Activity aktywnosc, BDKomunikacjaCel bdKomunikacjaCel, WebView phpUruchom, String [] argumenty){
        this.aktywnosc = aktywnosc;
        this.bdKomunikacjaCel=bdKomunikacjaCel;
        this.phpUruchom = phpUruchom;
        this.argumenty = argumenty;

    }
    @Override
    public void run(){
        aktywnosc.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch(bdKomunikacjaCel){
                    case WPROWADZ_NOWE_WIZYTY:
                            SQLiteDatabase bazaDanychWizyty=aktywnosc.openOrCreateDatabase("wizyty"+ZalogowanyUzytkownik.idUzytkownika+".db", Context.MODE_PRIVATE,  null);
                            for(int i = 0; i< Wizyta.idWizyty.length; i++)
                            {
                                bazaDanychWizyty.execSQL("INSERT INTO wizyty(idWizyty,imiePsa, celWizyty, dataWizyty) VALUES "
                                        + "('" + Wizyta.idWizyty[i]+ "','" + Wizyta.imiePsa[i] + "','"+ Wizyta.celWizyty[i]  + "','"+ Wizyta.dataWizyty[i] + "')");
                            }
                            bazaDanychWizyty.close();
                    break;
                    case WPROWADZ_ZAPLANOWANA_WIZYTE:
                        phpUruchom.loadUrl(Linki.zwrocDodawanieWizytyFolder()+ "dodajWizyte.php?" + "par1=" + argumenty[0] +  "&par2=" + argumenty[1] +
                                "&par3=" + argumenty[2] + "&par4=0&par5=" + argumenty[3]+ "&par6=" + ZalogowanyUzytkownik.idUzytkownika);
                        break;
                    case ZAREJESTRUJ_UZYTKOWNIKA:
                        phpUruchom.loadUrl(Linki.zwrocRejestracjaFolder() + "zarejestrujUzytkownika.php?" +
                                "par1=" + argumenty[0] + "&par2=" + argumenty[1] + "&par3=+ "+ argumenty[2]+ "&par4="+argumenty[3]+
                                "&par5="+ argumenty[4]+ "&par6="+argumenty[5]+"&par7=" + argumenty[6]);
                        break;
                    case WPROWADZ_PSA:
                        phpUruchom.loadUrl(Linki.zwrocDodawaniePsaFolder()+ "dodajPsa.php?" + "par1=" + argumenty[0] +  "&par2=" + argumenty[1] +
                                 "&par3=" + argumenty[2] + "&par4=" + argumenty[3] + "&par5=" + argumenty[4]);
                        break;

                }
            }
        });
    }
}
