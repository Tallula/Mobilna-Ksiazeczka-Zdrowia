package com.example.mobilnaksiazkazdrowia;

import android.app.Activity;
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
                        if(bdKomunikacjaCel== BDKomunikacjaCel.POBIERZ_MIASTA){
                            nazwyMiastZczytane = new String[jsonArray.length()];
                            idMiastZczytane = new String[jsonArray.length()];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                nazwyMiastZczytane[i] = jsonObject.getString("nazwaMiasta");
                                idMiastZczytane[i] = jsonObject.getString("idMiasta");
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity.getApplicationContext(), android.R.layout.simple_spinner_item, nazwyMiastZczytane);
                            autoCompleteTextView.setAdapter(adapter);
                        }
                        else if(bdKomunikacjaCel== BDKomunikacjaCel.POBIERZ_ULICE){
                            nazwyUlicZczytane = new String[jsonArray.length()];
                            idUlicZczytane = new String[jsonArray.length()];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                nazwyUlicZczytane[i] = jsonObject.getString("nazwaUlicy");
                                idUlicZczytane[i] = jsonObject.getString("idUlicy");
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity.getApplicationContext(), android.R.layout.simple_spinner_item, nazwyUlicZczytane);
                            autoCompleteTextView.setAdapter(adapter);
                        }
                        else if(bdKomunikacjaCel== BDKomunikacjaCel.POBIERZ_DANE_OSOBOWE){

                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            ZalogowanyUzytkownik.ustawIdOsoby(jsonObject.getString("idOsoby"));
                            ZalogowanyUzytkownik.ustawImie(jsonObject.getString("imie"));
                            ZalogowanyUzytkownik.ustawNazwisko(jsonObject.getString("nazwisko"));
                            ZalogowanyUzytkownik.ustawNumTel(jsonObject.getString("numerTelefonu"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        });

    }
}
