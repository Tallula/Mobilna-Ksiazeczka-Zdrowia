package com.example.mobilnaksiazkazdrowia;

import android.app.Activity;
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
    Spinner spinner;
    SpinnerContent spinnerContent;
    String url ="";
    int idUlicy;
    BDKomunikacjaNaSpinnery(Activity aktywnosc, Spinner spinnerZewn, SpinnerContent content,int idUlicy){
        this.activity = aktywnosc;
        this.spinner = spinnerZewn;
        this.spinnerContent=content;
        this.idUlicy = idUlicy;
    }
    @Override
    public void run() {
        OkHttpClient client = new OkHttpClient();
        if(spinnerContent == SpinnerContent.MIASTA){
             url = "http://192.168.0.152/ksiazkaZdrowia/Rejestracja/czytajMiasta.php";

        }
        else if(spinnerContent == SpinnerContent.ULICE){
            url = "http://192.168.0.152/ksiazkaZdrowia/Rejestracja/czytajUlice.php?par1="+SpinnerWypelnij.idMiastZczytane[idUlicy];
        }
        Request request = new Request.Builder().url(url).build();
       client.newCall(request).enqueue(new Callback() {
           @Override
           public void onFailure(@NonNull Call call, @NonNull IOException e) {
           }
           @Override
           public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
               final String myResponse = response.body().string();
               SpinnerWypelnij spinnerWypelnij = new SpinnerWypelnij(activity, myResponse, spinner, spinnerContent);
               spinnerWypelnij.run();
           }
       });
    }
}
