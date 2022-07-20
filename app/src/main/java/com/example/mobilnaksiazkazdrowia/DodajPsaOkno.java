package com.example.mobilnaksiazkazdrowia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

public class DodajPsaOkno extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_zwierze_okno);
        Spinner plecPsaSpinner = (Spinner) findViewById(R.id.plecPsaSpinner);
        AutoCompleteTextView rasyPsowACTextView = findViewById(R.id.rasaPsaTextView);

        String[] plecPsa = new String[2];
        plecPsa[0] ="Pies";
        plecPsa[1]="Suka";

        ArrayAdapter<String> plecPsaAdapter = new ArrayAdapter<String>(DodajPsaOkno.this.getApplicationContext(), android.R.layout.simple_spinner_item, plecPsa);
        plecPsaSpinner.setAdapter(plecPsaAdapter);

        BDKomunikacja bdKomunikacjaTextView = new BDKomunikacja(DodajPsaOkno.this, rasyPsowACTextView, BDKomunikacjaCel.POBIERZ_RASY_PSOW, null);
        bdKomunikacjaTextView.start();
    }
}