package com.example.mobilnaksiazkazdrowia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


public class WlascicielOkno extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wlasciciel_okno);

        Button dodajZwierzeButton = findViewById(R.id.dodajPsaOknoButton);
        Button wygenerujQRPsaButton = findViewById(R.id.wygenerujQRButton);
        Button testButton = findViewById(R.id.testButton);
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

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String idWizytyMax = sharedPref.getString("idWizytyMax", "");

        if(idWizytyMax.length() == 0)
        {
            Wizyty.idWizytyMax="0";
        }
        else
        {
            Wizyty.idWizytyMax = idWizytyMax;
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                BDKomunikacja bdKomunikacja = new BDKomunikacja(WlascicielOkno.this, null, BDKomunikacjaCel.POBIERZ_WIZYTY_INFO, null);
                bdKomunikacja.start();
            }
        }, 300);

    Log.d("Testy",Wizyty.idWizytyMax);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("idWizytyMax",Wizyty.idWizytyMax);
        editor.apply();
    }
}