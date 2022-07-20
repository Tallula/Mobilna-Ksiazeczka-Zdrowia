package com.example.mobilnaksiazkazdrowia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class WlascicielOkno extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wlasciciel_okno);
        TextView test = (TextView)findViewById(R.id.textView3);


       // Log.d("NUM", ZalogowanyUzytkownik.wezeMail()); //dziala
        Button dodajZwierzeButton = (Button)findViewById(R.id.dodajZwierzeButton);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                BDKomunikacja bdKomunikacja = new BDKomunikacja(WlascicielOkno.this, null, BDKomunikacjaCel.POBIERZ_DANE_O_ZWIERZETACH, null);
                bdKomunikacja.start();
            }
        }, 500);

        dodajZwierzeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DodajPsaOkno.class);
                startActivity(intent);
            }
        });
/*
        dodajZwierzeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(
                        WlascicielOkno.this
                );
                intentIntegrator.setPrompt("test");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.initiateScan();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode,resultCode,data
        );
        KodQR odczytQR = new KodQR();
        odczytQR.odczytQR(intentResult, WlascicielOkno.this);
*/
    }


}