package com.example.mobilnaksiazkazdrowia;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class WeterynarzOkno extends AppCompatActivity {

    public static String badanyPies="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weterynarz_okno);

        Button zaplanujWizyteButton =(Button) findViewById(R.id.zapiszWizyteButton);
        Button odczytajQRPsaButton =(Button) findViewById(R.id.odczytajQRPsaButton);

        TextView test = findViewById(R.id.testTextView);

        odczytajQRPsaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IntentIntegrator intentIntegrator = new IntentIntegrator(
                        WeterynarzOkno.this
                );
                intentIntegrator.setPrompt("Zeskanuj kod QR badanego psa");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.initiateScan();
            }

        });

        zaplanujWizyteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),PlanowanieSpotkaniaOkno.class);
               startActivity(intent);

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode , int resultCode , @Nullable Intent data ) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode,resultCode,data
        );
        KodQR odczytQR = new KodQR();
        odczytQR.odczytQR(intentResult, WeterynarzOkno.this);

    }


}