package com.example.mobilnaksiazkazdrowia;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BDJSONDeserializacja implements Runnable {
    Activity activity;
    String myResponse;

    public static String[] idMiastZczytane;
    public static String[] idUlicZczytane;
    public static String[] nazwyMiastZczytane;
    public static String[] nazwyUlicZczytane;
    public static String[] nazwyRasZczytane;
    public static String[] idRasZczytane;
    String czyUzytkownikIstnieje = "";

    AutoCompleteTextView autoCompleteTextView;
    BDKomunikacjaCel bdKomunikacjaCel;

    public BDJSONDeserializacja(Activity activity, String response, AutoCompleteTextView autoCompleteTextView, BDKomunikacjaCel bdKomunikacjaCel){
        this.activity=activity;
        this.myResponse = response;
        this.autoCompleteTextView = autoCompleteTextView;
        this.bdKomunikacjaCel=bdKomunikacjaCel;
    }
    @Override
    public void run() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                {
                    try {
                        JSONArray jsonArray = new JSONArray(myResponse);
                        switch(bdKomunikacjaCel){
                            case POBIERZ_CZY_UZYTKOWNIK_ISTNIEJE:
                                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                czyUzytkownikIstnieje = jsonObject1.getString("czyIstnieje");
                                if(czyUzytkownikIstnieje.equals("TAK"))
                                {
                                    Rejestracja.czyZarejestrowac=false;
                                }
                                else
                                {
                                    Rejestracja.czyZarejestrowac=true;
                                }
                                break;
                            case POBIERZ_JAKI_UZYTKOWNIK:
                                JSONObject jsonZalogowanyUzytkownik = jsonArray.getJSONObject(0);
                                ZalogowanyUzytkownik.ustawTypUzytkownika(jsonZalogowanyUzytkownik.getString("rodzajUzytkownika"));
                                ZalogowanyUzytkownik.ustawIdUzytkownika(jsonZalogowanyUzytkownik.getString("idUzytkownika"));
                                break;
                            case POBIERZ_MIASTA:
                                nazwyMiastZczytane = new String[jsonArray.length()];
                                idMiastZczytane = new String[jsonArray.length()];
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    nazwyMiastZczytane[i] = jsonObject.getString("nazwaMiasta");
                                    idMiastZczytane[i] = jsonObject.getString("idMiasta");
                                }
                                ArrayAdapter<String> adapterMiasta = new ArrayAdapter<String>(activity.getApplicationContext(), android.R.layout.simple_spinner_item, nazwyMiastZczytane);
                                autoCompleteTextView.setAdapter(adapterMiasta);
                                break;
                            case POBIERZ_ULICE:
                                nazwyUlicZczytane = new String[jsonArray.length()];
                                idUlicZczytane = new String[jsonArray.length()];
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    nazwyUlicZczytane[i] = jsonObject.getString("nazwaUlicy");
                                    idUlicZczytane[i] = jsonObject.getString("idUlicy");
                                }
                                ArrayAdapter<String> adapterUlice = new ArrayAdapter<String>(activity.getApplicationContext(), android.R.layout.simple_spinner_item, nazwyUlicZczytane);
                                autoCompleteTextView.setAdapter(adapterUlice);
                                break;
                            case POBIERZ_DANE_OSOBOWE:
                                if(jsonArray.length()>0)
                                {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                                        ZalogowanyUzytkownik.ustawIdUzytkownika(jsonObject.getString("idOsoby"));
                                        ZalogowanyUzytkownik.ustawImie(jsonObject.getString("imie"));
                                        ZalogowanyUzytkownik.ustawNazwisko(jsonObject.getString("nazwisko"));
                                        ZalogowanyUzytkownik.ustawNumTel(jsonObject.getString("numerTelefonu"));
                                    }
                                }
                                break;
                            case POBIERZ_DANE_O_ZWIERZETACH:
                                ZwierzetaWlasciciela.imie = new String[jsonArray.length()];
                                ZwierzetaWlasciciela.idPsa = new String[jsonArray.length()];
                                ZwierzetaWlasciciela.rasaPsa = new String[jsonArray.length()];

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    ZwierzetaWlasciciela.imie[i] = jsonObject.getString("imie");
                                    ZwierzetaWlasciciela.idPsa[i] = jsonObject.getString("idPsa");
                                    ZwierzetaWlasciciela.rasaPsa[i] = jsonObject.getString("nazwaRasy");
                                }
                                break;
                            case POBIERZ_RASY_PSOW:
                                nazwyRasZczytane = new String[jsonArray.length()];
                                idRasZczytane = new String[jsonArray.length()];
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    nazwyRasZczytane[i] = jsonObject.getString("nazwa");
                                    idRasZczytane[i] = jsonObject.getString("idRasy");
                                }
                                ArrayAdapter<String> adapterRasy = new ArrayAdapter<String>(activity.getApplicationContext(), android.R.layout.simple_spinner_item, nazwyRasZczytane);
                                autoCompleteTextView.setAdapter(adapterRasy);
                                break;
                            case POBIERZ_DANE_O_WIZYTACH:
                                Wizyty.dataWizyty = new String[jsonArray.length()];
                                Wizyty.imiePsa = new String[jsonArray.length()];
                                Wizyty.celWizyty = new String[jsonArray.length()];
                                Wizyty.idWizyty = new String[jsonArray.length()];

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    Wizyty.imiePsa[i] = jsonObject.getString("imie");
                                    Wizyty.celWizyty[i] = jsonObject.getString("cel");
                                    Wizyty.dataWizyty[i] = jsonObject.getString("dataWizyty");
                                    Wizyty.idWizyty[i] = jsonObject.getString("idWizyty");
                                }
                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
