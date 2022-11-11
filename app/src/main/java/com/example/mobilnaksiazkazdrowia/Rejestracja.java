package com.example.mobilnaksiazkazdrowia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import okhttp3.OkHttpClient;


public class Rejestracja extends AppCompatActivity {


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

        BDKomunikacjaPobieranie bdKomunikacjaTextView =
                new BDKomunikacjaPobieranie(Rejestracja.this, miastaACTextView, BDKomunikacjaCel.POBIERZ_MIASTA, null);
        bdKomunikacjaTextView.start();

        miastaACTextView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            BDKomunikacjaPobieranie bdKomunikacjaPobieranie =
                    new BDKomunikacjaPobieranie(Rejestracja.this, uliceACTextView, BDKomunikacjaCel.POBIERZ_ULICE, miastaACTextView.getText().toString());
            bdKomunikacjaPobieranie.start();
        }
        });

        zarejestrujButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient client = new OkHttpClient();
                BDKomunikacjaPobieranie bdKomunikacjaPobieranie = new BDKomunikacjaPobieranie(Rejestracja.this, null, BDKomunikacjaCel.POBIERZ_CZY_UZYTKOWNIK_ISTNIEJE, eMailEditText.getText().toString());
                bdKomunikacjaPobieranie.start();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(Rejestracja.czyZarejestrowac)
                        {
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
                            idUlicy = BDJSONDeserializacja.idUlicZczytane[index];

                            String[] argumenty = {eMail,haslo,imie,nazwisko,BDKomunikacjaPobieranie.idMiasta,idUlicy,czyWeterynarz};

                            BDKomunikacjaWprowadzanie bdKomunikacjaWprowadzanie =
                                    new BDKomunikacjaWprowadzanie(Rejestracja.this,  BDKomunikacjaCel.ZAREJESTRUJ_UZYTKOWNIKA,new WebView(getApplicationContext()), argumenty);
                            bdKomunikacjaWprowadzanie.start();

                            //web.loadUrl(Linki.zwrocRejestracjaFolder() + "zarejestrujUzytkownika.php?" +
                                 //   "par1=" + eMail + "&par2=" + haslo + "&par3=+ "+ imie+ "&par4="+nazwisko+
                                  //  "&par5="+ BDKomunikacjaPobieranie.idMiasta + "&par6="+idUlicy+"&par7=" + czyWeterynarz);

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
