package com.example.mobilnaksiazkazdrowia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.TextView;

public class WizytyOkno extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizyty_okno);
        TextView testWTextView = findViewById(R.id.testWTextView);
        CalendarView wizytyCalendarView = findViewById(R.id.wizytyCalendarView);

        BDKomunikacjaWprowadzanie bdKomunikacjaWprowadzanie = new BDKomunikacjaWprowadzanie(WizytyOkno.this, BDKomunikacjaCel.WPROWADZ_NOWE_WIZYTY, testWTextView);
        bdKomunikacjaWprowadzanie.start();

       // testWTextView.setText(Wizyty.idWizyty.length);

      wizytyCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
          @Override
          public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
              testWTextView.setText(year+ "/" + month + "/"+ dayOfMonth);
          }
      });
    //dodac wizyty do kalendarza





                //bazaDanychWizyty.execSQL("INSERT INTO wizyty( imiePsa) VALUES('" +ZalogowanyUzytkownik.wezIdUzytkownika()+ "')");
                //nowe wizyty max
       // SQLiteDatabase bazaDanychWizyty=openOrCreateDatabase("wizyty.db", Context.MODE_PRIVATE,  null);

        //bazaDanychWizyty.execSQL("INSERT INTO wizyty( imiePsa) VALUES('" +Wizyty.idWizyty.length+ "')");
        //bazaDanychWizyty.close();
               // int idWizytyMax = Integer.parseInt(Wizyty.idWizytyMax);
               // int idWizytyMaxTemp=0;
/*
                for(int i=0; i<Wizyty.idWizyty.length; i++){
                    idWizytyMaxTemp= Integer.parseInt(Wizyty.idWizyty[i]);
                    if(idWizytyMaxTemp>idWizytyMax)
                    {
                        idWizytyMax=idWizytyMaxTemp;
                    }
                }


 */
                //Wizyty.idWizytyMax = String.valueOf(idWizytyMax);






                //idWizytyMaxTemp = Integer.parseInt(jsonObject.getString("idWizyty"));

                //bazaDanychWizyty.execSQL("INSERT INTO wizyty( imiePsa) VALUES('" +jsonObject.getString("idWizyty")+ "')");
                // if(idWizytyMax<idWizytyMaxTemp)
                //{
                // idWizytyMax = Integer.parseInt(jsonObject.getString("idWizyty"));
                //}
                // bazaDanychWizyty.execSQL("INSERT INTO wizyty(idWizyty, imiePsa, celWizyty, dataWizyty) VALUES " +
                // "('"+ Wizyty.idWizyty[i] + "','" + Wizyty.imiePsa[i]+"','" + Wizyty.celWizyty[i]+ "','" + Wizyty.dataWizyty[i]+ "')");
                // bazaDanychWizyty.execSQL("INSERT INTO wizyty( imiePsa) VALUES('" +jsonArray.length()+ "')");
                //int idWizytyMax = Integer.parseInt(Wizyty.idWizytyMax);

    }
}