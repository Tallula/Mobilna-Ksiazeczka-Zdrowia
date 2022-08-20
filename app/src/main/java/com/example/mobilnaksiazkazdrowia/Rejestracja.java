package com.example.mobilnaksiazkazdrowia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Rejestracja extends AppCompatActivity {

    String ciagJSON="";
    String[] idMiastZczytane;
    String[] nazwyMiastZczytane;
    String[] nazwyUlicZczytane;
    public static boolean czyZarejestrowac=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejestracja);
        Button zarejestrujButton = findViewById(R.id.zarejestrujButton);

        EditText eMailEditText = findViewById(R.id.eMailEditText);
        EditText hasloEditText =findViewById(R.id.hasloEditText);
        AutoCompleteTextView miastaACTextView= findViewById(R.id.miastaACTextView);
        AutoCompleteTextView uliceACTextView= findViewById(R.id.uliceACTextView);
        Spinner czyWeterynarzSpinner =  findViewById(R.id.czyWeterynarzSpinner);

        EditText imieEditText = findViewById(R.id.imieEditText);
        EditText nazwiskoEditText = findViewById(R.id.nazwiskoEditText);
        String[] czyWeterynarz = new String[2];
        czyWeterynarz[0] ="NIE";
        czyWeterynarz[1]="TAK";

        ArrayAdapter<String> czyWeterynarzAdapter = new ArrayAdapter<String>(Rejestracja.this.getApplicationContext(), android.R.layout.simple_spinner_item, czyWeterynarz);
        czyWeterynarzSpinner.setAdapter(czyWeterynarzAdapter);

        BDKomunikacja bdKomunikacjaTextView = new BDKomunikacja(Rejestracja.this, miastaACTextView, BDKomunikacjaCel.POBIERZ_MIASTA, null);
        bdKomunikacjaTextView.start();

        miastaACTextView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            BDKomunikacja bdKomunikacja = new BDKomunikacja(Rejestracja.this, uliceACTextView, BDKomunikacjaCel.POBIERZ_ULICE, miastaACTextView.getText().toString());
            bdKomunikacja.start();
        }
        });

        zarejestrujButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient client = new OkHttpClient();
            //poprawic rejestracje

                BDKomunikacja bdKomunikacja = new BDKomunikacja(Rejestracja.this, null, BDKomunikacjaCel.POBIERZ_CZY_UZYTKOWNIK_ISTNIEJE, eMailEditText.getText().toString());
                bdKomunikacja.start();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(Rejestracja.czyZarejestrowac)
                        {
                            Log.d("CZY ZAREJESTROWAC: ", "TAK"); //dziala

                            String eMail = eMailEditText.getText().toString();
                            String haslo = hasloEditText.getText().toString();
                            String imie = imieEditText.getText().toString();
                            String nazwisko = nazwiskoEditText.getText().toString();
                            String idUlicy = "";

                            String czyWeterynarz = czyWeterynarzSpinner.getSelectedItem().toString();

                            WebView web = new WebView(getApplicationContext());
                            int index=0;
                            for(int i = 0; i< BDJSONDeserializacja.nazwyUlicZczytane.length; i++)
                            {
                                if(BDJSONDeserializacja.nazwyUlicZczytane[i].equals(uliceACTextView.getText().toString())){
                                    index=i;
                                    break;
                                }
                            }
                            idUlicy = BDJSONDeserializacja.idUlicZczytane[index].toString();

                            web.loadUrl(Linki.zwrocRejestracjaFolder() + "zarejestrujUzytkownika.php?" +
                                    "par1=" + eMail + "&par2=" + haslo + "&par3=+ "+ imie+ "&par4="+nazwisko+
                                    "&par5="+ BDKomunikacja.idMiasta + "&par6="+idUlicy+"&par7=" + czyWeterynarz);
                            Toast.makeText(getApplicationContext(), "Konto zostalo zalozone", Toast.LENGTH_LONG).show();
                            miastaACTextView.setText("");
                            uliceACTextView.setText("");
                            eMailEditText.setText("");
                            hasloEditText.setText("");
                            imieEditText.setText("");
                            nazwiskoEditText.setText("");
                        }
                        else
                        {
                            Log.d("CZY ZAREJESTROWAC: ", "NIE"); //dziala
                            Toast.makeText(getApplicationContext(), "Konto juz istnieje", Toast.LENGTH_LONG).show();
                            miastaACTextView.setText("");
                            uliceACTextView.setText("");
                            eMailEditText.setText("");
                            hasloEditText.setText("");
                            imieEditText.setText("");
                            nazwiskoEditText.setText("");
                        }
                    }
                }, 200);

            }
        });
    }
}
