package com.example.mobilnaksiazkazdrowia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;


public class WlascicielOkno extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wlasciciel_okno);

        Button dodajZwierzeButton = findViewById(R.id.dodajPsaOknoButton);
        Button wygenerujQRPsaButton = findViewById(R.id.wygenerujQRButton);
        Button testButton = findViewById(R.id.testButton);
        Button test2Button = findViewById(R.id.test2Button);
        Spinner wybranyPiesSpinner = (Spinner) findViewById(R.id.wybranyPieSpinner);
        ImageView qrPsaImageView = findViewById(R.id.qrPsaImageView);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                BDKomunikacja bdKomunikacja = new BDKomunikacja(WlascicielOkno.this, null, BDKomunikacjaCel.POBIERZ_DANE_O_ZWIERZETACH, null);
                bdKomunikacja.start();
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
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TEST", "BUTTON");
                /*
                SQLiteDatabase baza=openOrCreateDatabase("wizyty.db", Context.MODE_PRIVATE,  null);
                //stworz jesli nie istnieje
                    baza.execSQL("CREATE TABLE IF NOT EXISTS 'wizyty' (" +
                        "idWizyty INTEGER PRIMARY KEY," +
                        " imiePsa string," +
                        "celWizyty string," +
                        "dataWizyty string)");
                    //baza.execSQL("INSERT INTO sklep (nazwa) VALUES ( 'testowa')");
                    Cursor c = baza.rawQuery ("SELECT * FROM wizyty",null);
                    int ileWizyt=c.getCount();
                   if(ileWizyt==0) {
                       BDKomunikacja bdKomunikacja = new BDKomunikacja(WlascicielOkno.this, null, BDKomunikacjaCel.POBIERZ_DANE_O_WIZYTACH, null);
                       bdKomunikacja.start();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < Wizyty.idWizyty.length; i++) {
                                          baza.execSQL("INSERT INTO wizyty (idWizyty, imiePsa, celWizyty, dataWizyty) VALUES  " +
                                         "('" + c.getString(0) + "', " + c.getString(1) + "', " +
                                         c.getString(2) + "', " + c.getString(3) + "')");
                                }
                            }
                     }, 250);
                 }
                //baza.close();
                //Toast.makeText(getApplicationContext(), String.valueOf(ileWizyt), Toast.LENGTH_LONG).show();
                */
            }

        });
        test2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TEST2", "BUTTON2");
                //String row="";

               // SQLiteDatabase baza=openOrCreateDatabase("baza1.db", Context.MODE_PRIVATE,  null);
                //ursor c = baza.rawQuery ("SELECT * FROM sklep;",null);
                //int ile=c.getCount();

               // Toast.makeText(getApplicationContext(), c.getString(0), Toast.LENGTH_LONG).show();
               // for(int i=0; i<ile; i++)
                //{
                   // c.moveToNext();
                //}

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String rekordDB="";
        SQLiteDatabase baza=openOrCreateDatabase("wizyty.db", Context.MODE_PRIVATE,  null);
        //stworz jesli nie istnieje
        baza.execSQL("CREATE TABLE IF NOT EXISTS 'wizyty' (" +
                "idWizyty INTEGER PRIMARY KEY," +
                " imiePsa string," +
                "celWizyty string," +
                "dataWizyty string)");
        Cursor kursor = baza.rawQuery ("SELECT idWizyty FROM wizyty LIMIT 1",null);
        int ileWizyt=kursor.getCount();
        kursor.moveToFirst();
       // baza.execSQL("INSERT INTO wizyty(idWizyty, imiePsa, celWizyty, dataWizyty) VALUES " +
            //    "('1','gutek','szczepienie','2020-02-02')");
        if(ileWizyt==0)
        {
            Wizyty.idWizytyMax="0";
            Toast.makeText(getApplicationContext(), String.valueOf(ileWizyt), Toast.LENGTH_LONG).show();
        }
        else
        {
            Wizyty.idWizytyMax = kursor.getString(0);
            Toast.makeText(getApplicationContext(), Wizyty.idWizytyMax, Toast.LENGTH_LONG).show();
        }

        baza.close();

        //BDKomunikacja bdKomunikacja = new BDKomunikacja(WlascicielOkno.this, null, BDKomunikacjaCel.POBIERZ_DANE_O_WIZYTACH, null);
       // bdKomunikacja.start();

        // Toast.makeText(getApplicationContext(), Wizyty.idWizyty.length, Toast.LENGTH_LONG).show();

        //Cursor c = baza.rawQuery ("SELECT * FROM wizyty",null);

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

        //zapis do pliku - przerobic
      //  for (int i = 0; i < Wizyty.idWizyty.length; i++) {
           // idWizyt = sharedPrefPobierz.getString("idWizyt", "");
           // editor.putString("idWizyt",idWizyt + "," +Wizyty.idWizyty[i]);
       // }

       // editor.apply();
    }
}