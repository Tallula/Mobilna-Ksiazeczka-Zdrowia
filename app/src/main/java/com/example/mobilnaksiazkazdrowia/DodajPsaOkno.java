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
        EditText imiePsaEditText = findViewById(R.id.imiePsaEditText);
        EditText wiekPsaEditText = findViewById(R.id.wiekPsaEditText);
        AutoCompleteTextView rasaPsaACTextView = findViewById(R.id.rasaPsaACTextView);

        String[] plecPsa = new String[2];
        plecPsa[0] ="Samiec";
        plecPsa[1]="Samica";

        ArrayAdapter<String> plecPsaAdapter = new ArrayAdapter<String>(DodajPsaOkno.this.getApplicationContext(), android.R.layout.simple_spinner_item, plecPsa);
        plecPsaSpinner.setAdapter(plecPsaAdapter);

        BDKomunikacjaPobieranie bdKomunikacjaTextView = new BDKomunikacjaPobieranie(DodajPsaOkno.this, rasaPsaACTextView, BDKomunikacjaCel.POBIERZ_RASY_PSOW, null);
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
                Log.d("idOsoby",ZalogowanyUzytkownik.idUzytkownika );
                WebView web = new WebView(getApplicationContext());

               // web.loadUrl(Linki.zwrocDodawaniePsaFolder()+ "dodajPsa.php?" + "par1=" + imiePsa +  "&par2=" + idRasy +
                       // "&par3=" + plecPsa + "&par4=" + wiekPsa + "&par5=" + ZalogowanyUzytkownik.wezIdUzytkownika());

                String[] argumenty= {imiePsa, idRasy, plecPsa, wiekPsa, ZalogowanyUzytkownik.idUzytkownika};

                BDKomunikacjaWprowadzanie bdKomunikacjaWprowadzanie =
                        new BDKomunikacjaWprowadzanie(DodajPsaOkno.this,  BDKomunikacjaCel.WPROWADZ_PSA,
                                                    new WebView(getApplicationContext()), argumenty);
                bdKomunikacjaWprowadzanie.start();

                imiePsaEditText.setText("");
                rasaPsaACTextView.setText("");
                wiekPsaEditText.setText("");
            }
        });
    }
}