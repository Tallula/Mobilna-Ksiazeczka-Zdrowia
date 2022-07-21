package com.example.mobilnaksiazkazdrowia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

public class DodajPsaOkno extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_zwierze_okno);
        Button dodajPsaButton = findViewById(R.id.dodajPsaButton);
        Spinner plecPsaSpinner = (Spinner) findViewById(R.id.plecPsaSpinner);
        AutoCompleteTextView rasyPsowACTextView = findViewById(R.id.rasaPsaTextView);

        String[] plecPsa = new String[2];
        plecPsa[0] ="Pies";
        plecPsa[1]="Suka";

        ArrayAdapter<String> plecPsaAdapter = new ArrayAdapter<String>(DodajPsaOkno.this.getApplicationContext(), android.R.layout.simple_spinner_item, plecPsa);
        plecPsaSpinner.setAdapter(plecPsaAdapter);

        BDKomunikacja bdKomunikacjaTextView = new BDKomunikacja(DodajPsaOkno.this, rasyPsowACTextView, BDKomunikacjaCel.POBIERZ_RASY_PSOW, null);
        bdKomunikacjaTextView.start();

        dodajPsaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WebView web = new WebView(getApplicationContext());

                web.loadUrl(Linki.zwrocRejestracjaFolder() + "zarejestrujUzytkownika.php?" +
                        "par1=" + eMail + "&par2=" + haslo + "&par3=+ "+ imie+ "&par4="+nazwisko+
                        "&par5="+ BDKomunikacja.idMiasta + "&par6="+idUlicy+"&par7=" + czyWeterynarz);
            }
        });
    }
}