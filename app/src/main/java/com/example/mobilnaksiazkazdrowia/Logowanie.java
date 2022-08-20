package com.example.mobilnaksiazkazdrowia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

                BDKomunikacja bdKomunikacja = new BDKomunikacja(Logowanie.this,null, BDKomunikacjaCel.POBIERZ_JAKI_UZYTKOWNIK, eMail+","+haslo);
                bdKomunikacja.start();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(ZalogowanyUzytkownik.wezTypUzytkownika().equals("Wlasciciel")){
                            Intent intent = new Intent(getApplicationContext(),WlascicielOkno.class);
                            startActivity(intent);
                        }
                        else if(ZalogowanyUzytkownik.wezTypUzytkownika().equals("Weterynarz")){
                            Intent intent = new Intent(getApplicationContext(),WeterynarzOkno.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "E-Mail lub haslo bledne. Sprobuj ponownie", Toast.LENGTH_LONG).show();
                        }
                    }
                }, 1000);

            }
        });
    }
}