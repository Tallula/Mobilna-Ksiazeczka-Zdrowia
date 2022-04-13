package com.example.mobilnaksiazkazdrowia;

import android.app.Activity;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BDKomunikacjaNaSpinnery extends Thread{

    Activity activity;
    AutoCompleteTextView autoCompleteTextView;
    SpinnerContent spinnerContent;
    String url ="";
    int idUlicy;
    static String nazwaWybranegoMiasta;

    BDKomunikacjaNaSpinnery(Activity aktywnosc, AutoCompleteTextView autoCompleteTextView, SpinnerContent content){
        this.activity = aktywnosc;
        this.autoCompleteTextView = autoCompleteTextView;
        this.spinnerContent=content;
    }
    @Override
    public void run() {
        OkHttpClient client = new OkHttpClient();

        if(spinnerContent == SpinnerContent.MIASTA){
             url = "http://192.168.0.152/ksiazkaZdrowia/Rejestracja/czytajMiasta.php";
        }
        else if(spinnerContent == SpinnerContent.ULICE){
            url = "http://192.168.0.152/ksiazkaZdrowia/Rejestracja/czytajUlice.php?par1=2";
        }
        Request request = new Request.Builder().url(url).build();
       client.newCall(request).enqueue(new Callback() {
           @Override
           public void onFailure(@NonNull Call call, @NonNull IOException e) {
           }
           @Override
           public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
               final String myResponse = response.body().string();
               SpinnerWypelnij spinnerWypelnij = new SpinnerWypelnij(activity, myResponse, autoCompleteTextView, spinnerContent);
               spinnerWypelnij.run();
           }
       });
    }
}
