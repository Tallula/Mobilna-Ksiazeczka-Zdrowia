package com.example.mobilnaksiazkazdrowia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Logowanie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logowanie);

        EditText hasloLogowanieEditText = findViewById(R.id.hasloLogowanieEditText);
        EditText eMailLogowanieEditText = findViewById(R.id.eMailLogowanieEditText);
        Button zalogujButton = findViewById(R.id.zalogujButton);

        zalogujButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String eMail = eMailLogowanieEditText.getText().toString();
            String haslo = hasloLogowanieEditText.getText().toString();

                BDKomunikacjaPobieranie bdKomunikacjaPobieranie = new BDKomunikacjaPobieranie(Logowanie.this,null, BDKomunikacjaCel.POBIERZ_JAKI_UZYTKOWNIK, eMail+","+haslo);
                bdKomunikacjaPobieranie.start();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(ZalogowanyUzytkownik.typUzytkownika.equals("Wlasciciel")){
                               // Toast.makeText(getApplicationContext(),"WLA", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(getApplicationContext(),WlascicielOkno.class);
                                startActivity(intent);
                           // Toast.makeText(getApplicationContext(), BDKomunikacjaPobieranie.url, Toast.LENGTH_SHORT).show();

                        }
                        else if(ZalogowanyUzytkownik.typUzytkownika.equals("Weterynarz")){
                           // Toast.makeText(getApplicationContext(), "WET", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(),WeterynarzOkno.class);
                            startActivity(intent);
                            //Toast.makeText(getApplicationContext(), BDKomunikacjaPobieranie.url, Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            //Toast.makeText(getApplicationContext(), BDKomunikacjaPobieranie.url, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, 1000);

            }
        });
    }
}