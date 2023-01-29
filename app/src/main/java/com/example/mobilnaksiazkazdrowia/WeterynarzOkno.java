package com.example.mobilnaksiazkazdrowia;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.print.PrintManager;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class WeterynarzOkno extends AppCompatActivity {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static String[] badanyPiesInfo;
    public boolean czyWyswietlicHistorieLeczenia=false;
    Button zaplanujWizyteButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weterynarz_okno);

         zaplanujWizyteButton =(Button)findViewById(R.id.zapiszWizyteButton);
        Button odczytajQRPsaButton =(Button) findViewById(R.id.odczytajQRPsaButton);
        Button odczytajQRFaktury = (Button)findViewById(R.id.odczytajQRFakturyButton);
        Button wydrukujFaktureButton = (Button)findViewById(R.id.wydrukujFaktureButton);
        WebView fakturaWebView =  (WebView)findViewById(R.id.fakturaWebView);

        zaplanujWizyteButton.setEnabled(false);

                PrintManager printManager = (PrintManager) WeterynarzOkno.this.getSystemService(Context.PRINT_SERVICE);

        TextView test = findViewById(R.id.testowy);

        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }

        odczytajQRPsaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(
                        WeterynarzOkno.this
                );
                intentIntegrator.setPrompt("Zeskanuj kod QR badanego psa");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(QRPomoc.class);
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
        odczytajQRFaktury.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(
                        WeterynarzOkno.this
                );
                intentIntegrator.setPrompt("Zeskanuj kod QR widoczny na fakturze");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(QRPomoc.class);
                intentIntegrator.initiateScan();
                czyWyswietlicHistorieLeczenia=true;
            }

        });

        wydrukujFaktureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Faktura faktura = new Faktura(fakturaWebView, printManager);
                KodQR kodQR = new KodQR();

                try{
                    Bitmap fakturaBitMap= kodQR.wygenerujQR(badanyPiesInfo[0]);
                    kodQR.zapiszQR(fakturaBitMap);

                }catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            faktura.wydrukuj();
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
        zaplanujWizyteButton.setEnabled(true);
        //Toast.makeText(getApplicationContext(), WeterynarzOkno.badanyPiesInfo[0], Toast.LENGTH_LONG).show();
        if(czyWyswietlicHistorieLeczenia)
        {
            BDKomunikacjaPobieranie bdKomunikacjaPobieranie = new BDKomunikacjaPobieranie(WeterynarzOkno.this, null,
                    BDKomunikacjaCel.POBIERZ_HISTORIE_LECZENIA,badanyPiesInfo[0]);
            bdKomunikacjaPobieranie.start();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try{
                        //ilosc sie zgadza - przerobic na nazwy
                        Toast.makeText(getApplicationContext(), Wizyta.celeOdbytychWizyt.length+"", Toast.LENGTH_LONG).show();

                    }catch( Exception e){
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }

             }
             }, 550);

            czyWyswietlicHistorieLeczenia=false;
        }

    }

}