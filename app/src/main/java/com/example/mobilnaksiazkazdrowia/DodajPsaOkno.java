package com.example.mobilnaksiazkazdrowia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class DodajPsaOkno extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_zwierze_okno);
        Button dodajPsaButton = findViewById(R.id.dodajPsaButton);
        Spinner plecPsaSpinner = (Spinner) findViewById(R.id.plecPsaSpinner);
        AutoCompleteTextView rasyPsowACTextView = findViewById(R.id.rasaPsaACTextView);
        EditText imiePsaEditText = findViewById(R.id.imiePsaEditText);
        EditText wiekPsaEditText = findViewById(R.id.wiekPsaEditText);
        AutoCompleteTextView rasaPsaACTextView = findViewById(R.id.rasaPsaACTextView);

        String[] plecPsa = new String[2];
        plecPsa[0] ="Pies";
        plecPsa[1]="Suka";

        ArrayAdapter<String> plecPsaAdapter = new ArrayAdapter<String>(DodajPsaOkno.this.getApplicationContext(), android.R.layout.simple_spinner_item, plecPsa);
        plecPsaSpinner.setAdapter(plecPsaAdapter);

        BDKomunikacja bdKomunikacjaTextView = new BDKomunikacja(DodajPsaOkno.this, rasyPsowACTextView, BDKomunikacjaCel.POBIERZ_RASY_PSOW, null);
        bdKomunikacjaTextView.start();

        dodajPsaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imiePsa = imiePsaEditText.getText().toString();
                String rasaPsa = rasaPsaACTextView.getText().toString();
                String plecPsa = plecPsaSpinner.getSelectedItem().toString();
                String wiekPsa = wiekPsaEditText.getText().toString();
                int index =0;
                for(int i=0; i< BDJSONDeserializacja.nazwyRasZczytane.length; i++ )
                {
                if(BDJSONDeserializacja.nazwyRasZczytane[i].equals(rasaPsa))
                    {
                    index=i;
                    }
                }
                String idRasy = BDJSONDeserializacja.idRasZczytane[index];
                Log.d("idOsoby",ZalogowanyUzytkownik.wezIdOsoby() );
                WebView web = new WebView(getApplicationContext());

                web.loadUrl(Linki.zwrocDodawaniePsaFolder()+ "dodajPsa.php?" + "par1=" + imiePsa +  "&par2=" + idRasy +
                        "&par3=" + plecPsa + "&par4=" + wiekPsa + "&par5=" + ZalogowanyUzytkownik.wezIdOsoby());

                imiePsaEditText.setText("");
                rasaPsaACTextView.setText("");
                wiekPsaEditText.setText("");

            }
        });
    }
}