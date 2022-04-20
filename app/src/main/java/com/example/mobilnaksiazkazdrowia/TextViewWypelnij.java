package com.example.mobilnaksiazkazdrowia;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TextViewWypelnij implements Runnable {
    Activity activity;
    String myResponse;
    String ciagJSON="";
    public static String[] idMiastZczytane;
    public static String[] idUlicZczytane;
    public static String[] nazwyMiastZczytane;
    public static String[] nazwyUlicZczytane;
    AutoCompleteTextView autoCompleteTextView;
    TextViewJakaZawartosc content;

    public TextViewWypelnij(Activity activity, String response, AutoCompleteTextView autoCompleteTextView, TextViewJakaZawartosc spinnerContent){
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
                        if(content== TextViewJakaZawartosc.MIASTA){
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
                        else if(content== TextViewJakaZawartosc.ULICE){
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
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        });

    }
}
