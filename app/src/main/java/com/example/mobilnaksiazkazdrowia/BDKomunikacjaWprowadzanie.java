package com.example.mobilnaksiazkazdrowia;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.webkit.WebView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

public class BDKomunikacjaWprowadzanie extends Thread{

    Activity aktywnosc;
    BDKomunikacjaCel bdKomunikacjaCel;
    TextView test;



    BDKomunikacjaWprowadzanie(Activity aktywnosc, BDKomunikacjaCel bdKomunikacjaCel, TextView test){
        this.aktywnosc = aktywnosc;
        this.bdKomunikacjaCel=bdKomunikacjaCel;
        this.test = test;

    }
    @Override
    public void run(){
        aktywnosc.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch(bdKomunikacjaCel){
                    case WPROWADZ_NOWE_WIZYTY:
                        SQLiteDatabase bazaDanychWizyty=aktywnosc.openOrCreateDatabase("wizyty.db", Context.MODE_PRIVATE,  null);
                        for(int i=0; i<Wizyty.idWizyty.length;i++)
                        {
                            bazaDanychWizyty.execSQL("INSERT INTO wizyty(idWizyty,imiePsa, celWizyty, dataWizyty) VALUES "
                                    + "('" +Wizyty.idWizyty[i]+ "','" +Wizyty.imiePsa[i] + "','"+Wizyty.celWizyty[i]  + "','"+Wizyty.dataWizyty[i] + "')");
                        }
                        bazaDanychWizyty.close();
                    break;
                }
            }
        });
    }
}
