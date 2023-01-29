package com.example.mobilnaksiazkazdrowia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WizytyOkno extends AppCompatActivity {

    private Context context;
    private Object contextOBJ;
    int ileRekordow;
    ListView zadaniaListView;

    TextView testWTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizyty_okno);
         testWTextView = findViewById(R.id.testWTextView);
         zadaniaListView = findViewById(R.id.wizytyListView);
        contextOBJ = this;




        //tutaj


            }
            @Override
            protected void onPause() {
                super.onPause();

                BDKomunikacjaPobieranie bdKomunikacjaPobieranie = new BDKomunikacjaPobieranie(WizytyOkno.this, null, BDKomunikacjaCel.POBIERZ_DANE_O_WIZYTACH, null);
                bdKomunikacjaPobieranie.start();
            }
    @Override
    protected void onResume() {
        super.onResume();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
        try{
            SQLiteDatabase baza=openOrCreateDatabase("wizyty"+ZalogowanyUzytkownik.idUzytkownika+".db", Context.MODE_PRIVATE,  null);
            String aktualnaData = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            Cursor wynikPrzyszleWizyty = baza.rawQuery ("SELECT dataWizyty FROM wizyty  WHERE dataWizyty >= '" + aktualnaData+"';",null);
            wynikPrzyszleWizyty.moveToFirst();
            ileRekordow=wynikPrzyszleWizyty.getCount();
            String rekord="";

            if(ileRekordow>0){
                Wizyta.datyPrzyszlychWizyt = new String[ileRekordow];
                for(int i=0; i<ileRekordow; i++)
                {
                    rekord = wynikPrzyszleWizyty.getString(0);
                    Wizyta.datyPrzyszlychWizyt[i] = rekord;
                    wynikPrzyszleWizyty.moveToNext();
                }
            }

            Cursor wynikPrzyszlaWizyta = baza.rawQuery ("SELECT min(dataWizyty) FROM wizyty  WHERE dataWizyty > '" + aktualnaData+"';",null);
            wynikPrzyszlaWizyta.moveToFirst();
            Wizyta.dataNajblizszejWizyty= wynikPrzyszlaWizyta.getString(0);

            if(Wizyta.dataNajblizszejWizyty!=null){
                Powiadomienia powiadomienie = new Powiadomienia(contextOBJ);
                powiadomienie.stworzKanalPowiadomien();
                powiadomienie.ustawPowiadomienie(Wizyta.dataNajblizszejWizyty);
                //Toast.makeText(getApplicationContext(), "POWIADOMIENIE USTAWIONE", Toast.LENGTH_LONG).show();
            }

            Cursor idWizyty = baza.rawQuery ("SELECT max(idWizyty) FROM wizyty;",null);
            idWizyty.moveToFirst();
            if (idWizyty.getString(0) != null) {
                Wizyta.idWizytyMax= idWizyty.getString(0);
                //Toast.makeText(getApplicationContext(), "NIE NULL", Toast.LENGTH_LONG).show();
            }
            else
            {
                // Toast.makeText(getApplicationContext(), " NULL", Toast.LENGTH_LONG).show();
                Wizyta.idWizytyMax="0";
            }

            //dziala tu wszystko
            // Toast.makeText(getApplicationContext(), "Id wizyty: " + Wizyta.idWizytyMax, Toast.LENGTH_LONG).show();

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "BLAD " +Wizyta.dataNajblizszejWizyty+" "+ e.toString() , Toast.LENGTH_LONG).show();
        }

        try{
            SQLiteDatabase baza=openOrCreateDatabase("wizyty"+ZalogowanyUzytkownik.idUzytkownika+".db", Context.MODE_PRIVATE,  null);
            Cursor wizyta = baza.rawQuery ("SELECT * FROM wizyty ORDER BY idWizyty DESC;",null);
            wizyta.moveToFirst();
            List<String> listaWizyt= new ArrayList<>();
            ArrayAdapter<String> wizytyAdapter = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_list_item_1, listaWizyt);
            //getCount dziala
            //Toast.makeText(getApplicationContext(), "TO DZIALA: "+ wizyta.getCount(), Toast.LENGTH_LONG).show();
            for(int i=0; i<wizyta.getCount(); i++)
            {
                listaWizyt.add(wizyta.getString(0) + ". " + wizyta.getString(1) +" - " +
                        wizyta.getString(2));
                wizyta.moveToNext();
            }
            zadaniaListView.setAdapter(wizytyAdapter);
            baza.close();
            // Toast.makeText(getApplicationContext(), " "+ wizyta.getCount(), Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "BLAD 2", Toast.LENGTH_LONG).show();
        }

    }
}, 650);
    }
}