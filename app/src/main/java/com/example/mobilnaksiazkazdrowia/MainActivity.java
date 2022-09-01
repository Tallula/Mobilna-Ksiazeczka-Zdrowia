package com.example.mobilnaksiazkazdrowia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button zalogujSieButton = (Button) findViewById(R.id.logowanieButton);
        Button rejestracjaButton = (Button) findViewById(R.id.rejestracjaButton);
        Button oProgramieButton = (Button)findViewById(R.id.oProgramieButton);

       // Spinner miastaSpinner =(Spinner) findViewById(R.id.miastoSpinner);
       // EditText test = (EditText) findViewById(R.id.testEditText);


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