package com.example.mobilnaksiazkazdrowia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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


                OkHttpClient client = new OkHttpClient();
                String url="http://192.168.0.152/ksiazkaZdrowia/Logowanie/zalogujUzytkownika.php?par1="+eMail + "&par2=" + haslo;

                Request request = new Request.Builder().url(url).build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                         String myResponse = response.body().string();


                        Logowanie.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(myResponse.contains("True")){
                                    Toast.makeText(getApplicationContext(), "Pomyslnie zalogowano", Toast.LENGTH_LONG).show();

                                    Intent intent = new Intent(getApplicationContext(),OknoUzytkownika.class);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "Blad", Toast.LENGTH_LONG).show();
                                }
                            }
                        });


                    }
                });
            }
        });
    }
}