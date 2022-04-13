package com.example.mobilnaksiazkazdrowia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button zalogujSieButton = (Button) findViewById(R.id.logowanieButton);
        Button rejestracjaButton = (Button) findViewById(R.id.rejestracjaButton);
        Button oProgramieButton = (Button)findViewById(R.id.oProgramieButton);

       // Spinner miastaSpinner =(Spinner) findViewById(R.id.miastoSpinner);
        EditText test = (EditText) findViewById(R.id.testEditText);


        zalogujSieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Logowanie.class);
                startActivity(intent);
            }
        });

        rejestracjaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),Rejestracja.class);
                startActivity(intent);
            }
        });

        oProgramieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),OProgramie.class);
                startActivity(intent);
            }
        });
    }
}