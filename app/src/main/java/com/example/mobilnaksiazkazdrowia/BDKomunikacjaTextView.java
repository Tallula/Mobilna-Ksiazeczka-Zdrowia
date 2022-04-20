package com.example.mobilnaksiazkazdrowia;

import android.app.Activity;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BDKomunikacjaTextView extends Thread{

    Activity activity;
    AutoCompleteTextView autoCompleteTextView;
    TextViewJakaZawartosc spinnerContent;
    String url ="";
    int idUlicy;
    static String nazwaWybranegoMiasta;
    public static String idMiasta;

    BDKomunikacjaTextView(Activity aktywnosc, AutoCompleteTextView autoCompleteTextView, TextViewJakaZawartosc content, String nazwaWybranegoMiasta){
        this.activity = aktywnosc;
        this.autoCompleteTextView = autoCompleteTextView;
        this.spinnerContent=content;
        this.nazwaWybranegoMiasta = nazwaWybranegoMiasta;
    }
    @Override
    public void run() {
        OkHttpClient client = new OkHttpClient();

        if(spinnerContent == TextViewJakaZawartosc.MIASTA){

            //url = "http://192.168.0.152/ksiazkaZdrowia/RejestracjaFormularz/czytajMiasta.php";
             url = Linki.zwrocRejestracjaFormularzFolder() + "czytajMiasta.php";

        }
        else if(spinnerContent == TextViewJakaZawartosc.ULICE){

            int index=0;
            for(int i = 0; i< TextViewWypelnij.nazwyMiastZczytane.length; i++)
            {
                if(TextViewWypelnij.nazwyMiastZczytane[i].equals(nazwaWybranegoMiasta)){
                    index=i;
                }
            }
             idMiasta = TextViewWypelnij.idMiastZczytane[index].toString();

           // url = "http://192.168.0.152/ksiazkaZdrowia/RejestracjaFormularz/czytajUlice.php?par1=" + idMiasta;
            url = Linki.zwrocRejestracjaFormularzFolder() + "czytajUlice.php?par1=" + idMiasta;

        }
        Request request = new Request.Builder().url(url).build();
       client.newCall(request).enqueue(new Callback() {
           @Override
           public void onFailure(@NonNull Call call, @NonNull IOException e) {
           }
           @Override
           public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
               final String myResponse = response.body().string();
               TextViewWypelnij spinnerWypelnij = new TextViewWypelnij(activity, myResponse, autoCompleteTextView, spinnerContent);
               spinnerWypelnij.run();
           }
       });
    }
}
