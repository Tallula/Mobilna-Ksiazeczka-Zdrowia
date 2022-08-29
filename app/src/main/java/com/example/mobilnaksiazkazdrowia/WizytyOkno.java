package com.example.mobilnaksiazkazdrowia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class WizytyOkno extends AppCompatActivity {

    private Context context;
    private Object contextOBJ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizyty_okno);
        TextView testWTextView = findViewById(R.id.testWTextView);
        CalendarView wizytyCalendarView = findViewById(R.id.wizytyCalendarView);
        contextOBJ = this;

        Powiadomienia powiadomienie = new Powiadomienia(contextOBJ);
        powiadomienie.createNotificationChannel();

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

//
        SQLiteDatabase baza=openOrCreateDatabase("wizyty.db", Context.MODE_PRIVATE,  null);
        String aktualnaData = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Cursor wynikPrzyszleWizyty = baza.rawQuery ("SELECT dataWizyty FROM wizyty  WHERE dataWizyty > '" + aktualnaData+"';",null);
        wynikPrzyszleWizyty.moveToFirst();
        int ileRekordow=wynikPrzyszleWizyty.getCount();
        String rekord="";
        Wizyty.datyPrzyszlychWizyt = new String[ileRekordow];
        for(int i=0; i<ileRekordow; i++)
        {
            rekord = wynikPrzyszleWizyty.getString(0);
            Wizyty.datyPrzyszlychWizyt[i] = rekord;
            wynikPrzyszleWizyty.moveToNext();
        }

        Cursor wynikPrzyszlaWizyta = baza.rawQuery ("SELECT min(dataWizyty) FROM wizyty  WHERE dataWizyty > '" + aktualnaData+"';",null);
        wynikPrzyszlaWizyta.moveToFirst();
        Wizyty.dataNajblizszejWizyty= wynikPrzyszlaWizyta.getString(0);

        powiadomienie.ustawPowiadomienie(Wizyty.dataNajblizszejWizyty);
        baza.close();

        int day = 29;
        int month = 9;

        Calendar calendar = Calendar.getInstance();
        //calendar.set(Calendar.MONTH, (month - 1));
        //calendar.set(Calendar.DAY_OF_MONTH, day);

        //calendar.add(Calendar.MONTH, (month-1));
       // calendar.set(Calendar.DAY_OF_MONTH, day+1);

       // wizytyCalendarView.setDate(calendar.getTimeInMillis(),true,true);

        //Toast.makeText(getApplicationContext(), wynikPrzyszlaWizyta.getString(0), Toast.LENGTH_LONG).show();
        //Toast.makeText(getApplicationContext(), "NAJBLIZSZA "+ Wizyty.dataNajblizszejWizyty, Toast.LENGTH_LONG).show();

        //daty wizyt do ustawienia w calendarview

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