package com.example.mobilnaksiazkazdrowia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WizytyOkno extends AppCompatActivity {

    private Context context;
    private Object contextOBJ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizyty_okno);
        TextView testWTextView = findViewById(R.id.testWTextView);
        ListView zadaniaListView = findViewById(R.id.wizytyListView);
        contextOBJ = this;

        Powiadomienia powiadomienie = new Powiadomienia(contextOBJ);
        powiadomienie.stworzKanalPowiadomien();

        BDKomunikacjaWprowadzanie bdKomunikacjaWprowadzanie = new BDKomunikacjaWprowadzanie(WizytyOkno.this, BDKomunikacjaCel.WPROWADZ_NOWE_WIZYTY, null, null);
        bdKomunikacjaWprowadzanie.start();


        SQLiteDatabase baza=openOrCreateDatabase("wizyty.db", Context.MODE_PRIVATE,  null);
        String aktualnaData = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Cursor wynikPrzyszleWizyty = baza.rawQuery ("SELECT dataWizyty FROM wizyty  WHERE dataWizyty > '" + aktualnaData+"';",null);
        wynikPrzyszleWizyty.moveToFirst();
        int ileRekordow=wynikPrzyszleWizyty.getCount();
        String rekord="";
        Wizyta.datyPrzyszlychWizyt = new String[ileRekordow];
        for(int i=0; i<ileRekordow; i++)
        {
            rekord = wynikPrzyszleWizyty.getString(0);
            Wizyta.datyPrzyszlychWizyt[i] = rekord;
            wynikPrzyszleWizyty.moveToNext();
        }

        Cursor wynikPrzyszlaWizyta = baza.rawQuery ("SELECT min(dataWizyty) FROM wizyty  WHERE dataWizyty > '" + aktualnaData+"';",null);
        wynikPrzyszlaWizyta.moveToFirst();
        Wizyta.dataNajblizszejWizyty= wynikPrzyszlaWizyta.getString(0);

        powiadomienie.ustawPowiadomienie(Wizyta.dataNajblizszejWizyty);

        try{
            Cursor idWizyty = baza.rawQuery ("SELECT max(idWizyty) FROM wizyty;",null);
            idWizyty.moveToFirst();
            Wizyta.idWizytyMax= idWizyty.getString(0);
            //dziala tu wszystko
            //Toast.makeText(getApplicationContext(), Wizyta.idWizytyMax, Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

        try{
            Cursor wizyta = baza.rawQuery ("SELECT * FROM wizyty ORDER BY idWizyty DESC;",null);
            wizyta.moveToFirst();
            List<String> listaWizyt= new ArrayList<>();
            ArrayAdapter<String> wizytyAdapter = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_list_item_1, listaWizyt);
            //getCount dziala
            //Toast.makeText(getApplicationContext(), " "+ wizyta.getCount(), Toast.LENGTH_LONG).show();
            for(int i=0; i<wizyta.getCount(); i++)
            {
                listaWizyt.add(wizyta.getString(0) + ". " + wizyta.getString(1) +" - " +
                        wizyta.getString(2));
                wizyta.moveToNext();
            }

            zadaniaListView.setAdapter(wizytyAdapter);
           // Toast.makeText(getApplicationContext(), " "+ wizyta.getCount(), Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

        baza.close();

    }

    @Override
    protected void onResume() {
        super.onResume();

                BDKomunikacjaPobieranie bdKomunikacjaPobieranie = new BDKomunikacjaPobieranie(WizytyOkno.this, null, BDKomunikacjaCel.POBIERZ_DANE_O_WIZYTACH, null);
                bdKomunikacjaPobieranie.start();

    }
}