package com.example.mobilnaksiazkazdrowia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejestracja);
        Button zarejestrujButton = (Button)findViewById(R.id.zarejestrujButton);

        EditText testEditText = (EditText)findViewById(R.id.testEditText);
        EditText eMailEditText = (EditText) findViewById(R.id.eMailEditText);
        EditText hasloEditText =(EditText)findViewById(R.id.hasloEditText);
        AutoCompleteTextView miastaACTextView=(AutoCompleteTextView) findViewById(R.id.miastaACTextView);
        AutoCompleteTextView uliceACTextView=(AutoCompleteTextView) findViewById(R.id.uliceACTextView);

        Spinner czyWeterynarzSpinner = (Spinner) findViewById(R.id.czyWeterynarzSpinner);

        EditText imieEditText = (EditText)findViewById(R.id.imieEditText);
        EditText nazwiskoEditText = (EditText)findViewById(R.id.nazwiskoEditText);

        String[] czyWeterynarz = new String[2];
        czyWeterynarz[0] = "NIE";
        czyWeterynarz[1]="TAK";

        ArrayAdapter<String> czyWeterynarzAdapter = new ArrayAdapter<String>(Rejestracja.this.getApplicationContext(), android.R.layout.simple_spinner_item, czyWeterynarz);
        czyWeterynarzSpinner.setAdapter(czyWeterynarzAdapter);

        OkHttpClient client = new OkHttpClient();


        BDKomunikacjaNaSpinnery bdKomunikacjaNaSpinnery = new BDKomunikacjaNaSpinnery(Rejestracja.this, miastaACTextView, SpinnerContent.MIASTA, 1);
        bdKomunikacjaNaSpinnery.start();

    miastaACTextView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        uliceACTextView.setText(miastaACTextView.getText().toString());
        }
    });
/*
        miastaACTextView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    BDKomunikacjaNaSpinnery bdKomunikacjaNaSpinnery =
                            new BDKomunikacjaNaSpinnery(Rejestracja.this, uliceACTextView, SpinnerContent.ULICE,position);
                    bdKomunikacjaNaSpinnery.start();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            testEditText.setText("Nic nie wybrano");
            }
        });

*/

        zarejestrujButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient client = new OkHttpClient();
                String url = "http://192.168.0.152/ksiazkaZdrowia/Rejestracja/emailSprawdz.php?par1=" + eMailEditText.getText().toString();
                Request request = new Request.Builder().url(url).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }
                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String myResponse = response.body().string();
                        Rejestracja.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (myResponse.equals("True")) {
                                    Toast.makeText(getApplicationContext(), "Podany adres e-mail juz istnieje", Toast.LENGTH_LONG).show();
                                }
                                else if(myResponse.equals("False"))
                                    {
                                    String eMail = eMailEditText.getText().toString();
                                    String haslo = hasloEditText.getText().toString();
                                    String imie = imieEditText.getText().toString();
                                    String nazwisko = nazwiskoEditText.getText().toString();
                                    //int wybraneMiastoIndex = miastaSpinner.getSelectedItemPosition();
                                    int wybranaUlicaIndex=Integer.parseInt( uliceACTextView.getText().toString());
                                    //String idMiasta = SpinnerWypelnij.idMiastZczytane[wybraneMiastoIndex];
                                    String idUlicy = SpinnerWypelnij.idUlicZczytane[wybranaUlicaIndex];
                                    String czyWeterynarz = czyWeterynarzSpinner.getSelectedItem().toString();
                                    WebView web = new WebView(getApplicationContext());
                                    //web.loadUrl("http://192.168.0.152/ksiazkaZdrowia/Rejestracja/zarejestrujUzytkownika.php?par1=" + eMail + "&par2=" + haslo +
                                          //  "&par3=" + imie + "&par4=" + nazwisko + "&par5=" + idMiasta + "&par6=" + idUlicy + "&par7=" + czyWeterynarz);

                                        Toast.makeText(getApplicationContext(), "Konto zostalo zalozone", Toast.LENGTH_LONG).show();

                                        eMailEditText.setText("");
                                        hasloEditText.setText("");
                                        imieEditText.setText("");
                                        nazwiskoEditText.setText("");
                                        //miastaSpinner.setSelection(0);
                                        //uliceSpinner.setSelection(0);
                                }
                            }
                        });
                    }
                });
            }
        });
    }
}
