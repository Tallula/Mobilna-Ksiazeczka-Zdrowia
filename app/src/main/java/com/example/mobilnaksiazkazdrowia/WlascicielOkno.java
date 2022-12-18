package com.example.mobilnaksiazkazdrowia;

import android.app.AlarmManager;
import android.app.PendingIntent;
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

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;



public class WlascicielOkno extends AppCompatActivity {

    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

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
            Wizyta.idWizytyMax="0";
        }
        else
        {
            Wizyta.idWizytyMax = kursor.getString(0);
        }
        bazaDanychWizyty.close();




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

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                BDKomunikacjaWprowadzanie bdKomunikacjaWprowadzanie =
                        new BDKomunikacjaWprowadzanie(WlascicielOkno.this,
                                BDKomunikacjaCel.WPROWADZ_NOWE_WIZYTY, null, null);
                bdKomunikacjaWprowadzanie.start();
            }
        },50);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                BDKomunikacjaWprowadzanie bdKomunikacjaWprowadzanie =
                        new BDKomunikacjaWprowadzanie(WlascicielOkno.this,
                                BDKomunikacjaCel.WPROWADZ_NOWE_WIZYTY,null,null);
                bdKomunikacjaWprowadzanie.start();
            }
        }, 300);
    }

}