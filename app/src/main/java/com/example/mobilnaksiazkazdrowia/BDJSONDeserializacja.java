package com.example.mobilnaksiazkazdrowia;

import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

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
    TextViewJakaZawartosc content;


    public BDJSONDeserializacja(Activity activity, String response, AutoCompleteTextView autoCompleteTextView, TextViewJakaZawartosc spinnerContent){
        this.activity=activity;
        this.myResponse = response;
        this.autoCompleteTextView = autoCompleteTextView;
        this.content=spinnerContent;
    }
    @Override
    public void run() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                {
                    try {
                        JSONArray jsonArray = new JSONArray(myResponse);
                        if(content== TextViewJakaZawartosc.POBIERZ_MIASTA){
                            nazwyMiastZczytane = new String[jsonArray.length()];
                            idMiastZczytane = new String[jsonArray.length()];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                nazwyMiastZczytane[i] = jsonObject.getString("nazwaMiasta");
                                idMiastZczytane[i] = jsonObject.getString("idMiasta");
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity.getApplicationContext(), android.R.layout.simple_spinner_item, nazwyMiastZczytane);
                            autoCompleteTextView.setAdapter(adapter);
                            Log.d("TAGTAG", "MIASTA");
                        }
                        else if(content== TextViewJakaZawartosc.POBIERZ_ULICE){
                            nazwyUlicZczytane = new String[jsonArray.length()];
                            idUlicZczytane = new String[jsonArray.length()];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                nazwyUlicZczytane[i] = jsonObject.getString("nazwaUlicy");
                                idUlicZczytane[i] = jsonObject.getString("idUlicy");
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity.getApplicationContext(), android.R.layout.simple_spinner_item, nazwyUlicZczytane);
                            autoCompleteTextView.setAdapter(adapter);
                            Log.d("TAGTAG", "ULICE");
                        }
                        else if(content== TextViewJakaZawartosc.POBIERZ_DANE_OSOBOWE){
                            //Log.d("TAGTAG", "Dane osobowe");

                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            ZalogowanyUzytkownik.ustawEMail(jsonObject.getString("imie"));
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
