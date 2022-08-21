package com.example.mobilnaksiazkazdrowia;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

public class BDKomunikacjaWprowadzanie extends Thread{

    Activity activity;
    BDKomunikacjaCel bdKomunikacjaCel;
    TextView test;
    BDKomunikacjaWprowadzanie(Activity activity, BDKomunikacjaCel bdKomunikacjaCel, TextView test){
        this.activity = activity;
        this.bdKomunikacjaCel=bdKomunikacjaCel;
        this.test = test;

    }
    @Override
    public void run(){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch(bdKomunikacjaCel){
                    case WPROWADZ_NOWE_WIZYTY:

                        test.setText(String.valueOf(Wizyty.idWizyty.length)); //ile nowych wizyt

                        SQLiteDatabase bazaDanychWizyty=activity.openOrCreateDatabase("wizyty.db", Context.MODE_PRIVATE,  null);

                        for(int i=0; i<Wizyty.idWizyty.length;i++)
                        {
                            bazaDanychWizyty.execSQL("INSERT INTO wizyty(idWizyty,imiePsa, celWizyty, dataWizyty) VALUES "
                                    + "('" +Wizyty.idWizyty[i]+ "','" +Wizyty.imiePsa[i] + "','"+Wizyty.celWizyty[i]  + "','"+Wizyty.dataWizyty[i] + "')");
                        }
                        bazaDanychWizyty.close();

                        //dziala
                    break;
                }


            }
        });
    }
}
