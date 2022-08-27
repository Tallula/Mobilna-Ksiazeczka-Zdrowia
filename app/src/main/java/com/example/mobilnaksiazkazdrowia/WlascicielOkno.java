package com.example.mobilnaksiazkazdrowia;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class WlascicielOkno extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wlasciciel_okno);

        Button dodajZwierzeButton = findViewById(R.id.dodajPsaOknoButton);
        Button wygenerujQRPsaButton = findViewById(R.id.wygenerujQRButton);
        Button wizytyOknoButton = findViewById(R.id.wizytyOknoButton);
        Button test2Button = findViewById(R.id.test2Button);
        Spinner wybranyPiesSpinner = (Spinner) findViewById(R.id.wybranyPieSpinner);
        ImageView qrPsaImageView = findViewById(R.id.qrPsaImageView);

        SQLiteDatabase bazaDanychWizyty=openOrCreateDatabase("wizyty.db", Context.MODE_PRIVATE,  null);
        //stworz jesli nie istnieje
        bazaDanychWizyty.execSQL("CREATE TABLE IF NOT EXISTS 'wizyty' (" +
                "idWizyty INTEGER PRIMARY KEY," +
                " imiePsa string," +
                "celWizyty string," +
                "dataWizyty string)");
        Cursor kursor = bazaDanychWizyty.rawQuery ("SELECT max(idWizyty) FROM wizyty ",null);
        int ileWizyt=kursor.getCount();
        kursor.moveToFirst();

        if(ileWizyt==0)
        {
            Wizyty.idWizytyMax="0";
        }
        else
        {
            Wizyty.idWizytyMax = kursor.getString(0);
        }

        bazaDanychWizyty.close();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                BDKomunikacjaPobieranie bdKomunikacjaPobieranie = new BDKomunikacjaPobieranie(WlascicielOkno.this, null, BDKomunikacjaCel.POBIERZ_DANE_O_WIZYTACH, null);
                bdKomunikacjaPobieranie.start();
            }
        }, 50);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                BDKomunikacjaPobieranie bdKomunikacjaPobieranie = new BDKomunikacjaPobieranie(WlascicielOkno.this, null, BDKomunikacjaCel.POBIERZ_DANE_O_ZWIERZETACH, null);
                bdKomunikacjaPobieranie.start();
            }
        }, 100);

        dodajZwierzeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DodajPsaOkno.class);
                startActivity(intent);
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayAdapter<String> wybranyPiesAdapter = new ArrayAdapter<String>(WlascicielOkno.this.getApplicationContext(), android.R.layout.simple_spinner_item, ZwierzetaWlasciciela.imie);
                wybranyPiesSpinner.setAdapter(wybranyPiesAdapter);
            }
        }, 150);

        wygenerujQRPsaButton.setOnClickListener(new View.OnClickListener() {
            int index = 0;
            @Override
            public void onClick(View v) {
                for (int i = 0; i < ZwierzetaWlasciciela.imie.length; i++) {
                    if (ZwierzetaWlasciciela.imie[i].equals(wybranyPiesSpinner.getSelectedItem().toString())) {
                        index=i;
                        break;
                    }
                }
                String idPsa = ZwierzetaWlasciciela.idPsa[index];
                String imiePsa = ZwierzetaWlasciciela.imie[index];
                String rasaPsa = ZwierzetaWlasciciela.rasaPsa[index];
                String daneQR = idPsa + "," + imiePsa + ","+ rasaPsa;

                KodQR kodQR = new KodQR();
                kodQR.wygenerujQR(daneQR, qrPsaImageView);
            }

        });

        wizytyOknoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WizytyOkno.class);
                startActivity(intent);
            }
        });
        test2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase baza=openOrCreateDatabase("wizyty.db", Context.MODE_PRIVATE,  null);

                String aktualnaData = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                Cursor wynik = baza.rawQuery ("SELECT dataWizyty FROM wizyty  WHERE dataWizyty > '" + aktualnaData+"';",null);
                wynik.moveToFirst();
                int ileRekordow=wynik.getCount();

                String rekord="";
                Wizyty.datyPrzyszlychWizyt = new String[ileRekordow];

                for(int i=0; i<ileRekordow; i++)
                {
                    rekord = wynik.getString(0);
                    Wizyty.datyPrzyszlychWizyt[i] = rekord;

                    wynik.moveToNext();
                }

                for(int i=0; i<Wizyty.datyPrzyszlychWizyt.length; i++)
                {
                    if(Wizyty.datyPrzyszlychWizyt[i].compareTo(Wizyty.dataNajblizszejWizyty) < 0)
                    {
                        Wizyty.dataNajblizszejWizyty= Wizyty.datyPrzyszlychWizyt[i];
                    }
                }
                //do tego momentu dziala, nastepnie ustawianie powiadomien dla najblizszej wizyty!!!
                
                //Toast.makeText(getApplicationContext(), Wizyty.dataNajblizszejWizyty, Toast.LENGTH_LONG).show();
               // Toast.makeText(getApplicationContext(), Wizyty.datyPrzyszlychWizyt[0], Toast.LENGTH_LONG).show();
               // Toast.makeText(getApplicationContext(), String.valueOf(Wizyty.datyPrzyszlychWizyt.length), Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(), "AKTUALNA DATA: "+ aktualnaData, Toast.LENGTH_LONG).show();
                baza.close();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
               // BDKomunikacja bdKomunikacja = new BDKomunikacja(WlascicielOkno.this, null, BDKomunikacjaCel.POBIERZ_DANE_O_WIZYTACH, null);
                //bdKomunikacja.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

       // Toast.makeText(getApplicationContext(), "onPause", Toast.LENGTH_LONG).show();

        //SharedPreferences sharedPrefPobierz = this.getPreferences(Context.MODE_PRIVATE);
       // String idWizyt="";

        //SharedPreferences sharedPrefZapisz = this.getPreferences(Context.MODE_PRIVATE);
       // SharedPreferences.Editor editor = sharedPrefZapisz.edit();

        //editor.putString("idWizytyMax",Wizyty.idWizytyMax);



       // editor.apply();
    }
}