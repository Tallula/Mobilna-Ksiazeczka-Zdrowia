package com.example.mobilnaksiazkazdrowia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WeterynarzOkno extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weterynarz_okno);

        Button zaplanujWizyteButton =(Button) findViewById(R.id.zaplanujWizyteButton);


        BDKomunikacja bdKomunikacja = new BDKomunikacja(WeterynarzOkno.this,null, BDKomunikacjaCel.POBIERZ_DANE_OSOBOWE, ZalogowanyUzytkownik.wezeMail());
        bdKomunikacja.start();

        TextView test = findViewById(R.id.testTextView);

        zaplanujWizyteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),PlanowanieSpotkaniaOkno.class);
               startActivity(intent);
            }
        });



    }






}