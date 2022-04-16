package com.example.mobilnaksiazkazdrowia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WeterynarzOkno extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weterynarz_okno);

        Button zaplanujWizyteButton =(Button) findViewById(R.id.zaplanujWizyteButton);

        zaplanujWizyteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),PlanowanieSpotkaniaOkno.class);
                startActivity(intent);
            }
        });



    }






}