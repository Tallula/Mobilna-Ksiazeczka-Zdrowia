package com.example.mobilnaksiazkazdrowia;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.util.Base64;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weterynarz_okno);
       // NfcAdapter nfcAdapter= NfcAdapter.getDefaultAdapter(this);
        Tag tag;

        Button zaplanujWizyteButton =findViewById(R.id.zapiszWizyteButton);
        Button odczytajQRPsaButton = findViewById(R.id.odczytajQRPsaButton);
        Button zeskanujRFIDButton = findViewById(R.id.zeskanujRFIDButton);
        Button wydrukujFaktureButton = findViewById(R.id.wydrukujFaktureButton);
        WebView fakturaWebView =  findViewById(R.id.fakturaWebView);

        PrintManager printManager = (PrintManager) WeterynarzOkno.this.getSystemService(Context.PRINT_SERVICE);

        TextView test = findViewById(R.id.zaplanujWizyteTextView);

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

        zeskanujRFIDButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        wydrukujFaktureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

        Faktura faktura = new Faktura(fakturaWebView, printManager);
        KodQR kodQR = new KodQR();
               kodQR.zapiszQR(kodQR.wygenerujQR(badanyPiesInfo[0]));
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

    }

}