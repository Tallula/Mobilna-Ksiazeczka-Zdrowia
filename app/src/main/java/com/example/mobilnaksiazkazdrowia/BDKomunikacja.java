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

public class BDKomunikacja extends Thread{

    Activity activity;
    AutoCompleteTextView autoCompleteTextView;
    BDKomunikacjaCel bdKomunikacjaCel;
    String url ="";

    static String arg;
    public static String idMiasta;

    BDKomunikacja(Activity aktywnosc, AutoCompleteTextView autoCompleteTextView, BDKomunikacjaCel bdKomunikacjaCel, String arg){
        this.activity = aktywnosc;
        this.autoCompleteTextView = autoCompleteTextView;
        this.bdKomunikacjaCel=bdKomunikacjaCel;
        this.arg = arg;
    }

    @Override
    public void run() {
        OkHttpClient client = new OkHttpClient();

        switch(bdKomunikacjaCel){
            case POBIERZ_MIASTA:
                url = Linki.zwrocRejestracjaFormularzFolder() + "czytajMiasta.php";
                break;
            case POBIERZ_ULICE:
                int index=0;
                for(int i = 0; i< BDJSONDeserializacja.nazwyMiastZczytane.length; i++)
                {
                    if(BDJSONDeserializacja.nazwyMiastZczytane[i].equals(arg)){
                        index=i;
                    }
                }
                idMiasta = BDJSONDeserializacja.idMiastZczytane[index];
                url = Linki.zwrocRejestracjaFormularzFolder() + "czytajUlice.php?par1=" + idMiasta;
                break;
            case POBIERZ_DANE_OSOBOWE:
                url = Linki.zwrocLogowanieFolder() + "czytajDaneOsoby.php?par1="+ arg ;
                break;

        }
        /*
        if(textViewContent == TextViewJakaZawartosc.POBIERZ_MIASTA){

            //url = "http://192.168.0.152/ksiazkaZdrowia/RejestracjaFormularz/czytajMiasta.php";
             url = Linki.zwrocRejestracjaFormularzFolder() + "czytajMiasta.php";

        }
        else if(textViewContent == TextViewJakaZawartosc.POBIERZ_ULICE){

            int index=0;
            for(int i = 0; i< BDJSONDeserializacja.nazwyMiastZczytane.length; i++)
            {
                if(BDJSONDeserializacja.nazwyMiastZczytane[i].equals(arg)){
                    index=i;
                }
            }
             idMiasta = BDJSONDeserializacja.idMiastZczytane[index].toString();

           // url = "http://192.168.0.152/ksiazkaZdrowia/RejestracjaFormularz/czytajUlice.php?par1=" + idMiasta;
            url = Linki.zwrocRejestracjaFormularzFolder() + "czytajUlice.php?par1=" + idMiasta;

        }
        //if(textViewContent ==TextViewJakaZawartosc.POBIERZ_DANE_OSOBOWE)
        else if(textViewContent ==TextViewJakaZawartosc.POBIERZ_DANE_OSOBOWE){

            //url = Linki.zwrocRejestracjaFormularzFolder() + "czytajDaneOsoby.php?par1='" + arg + "'";
            url = Linki.zwrocLogowanieFolder() + "czytajDaneOsoby.php?par1="+ arg ;
            Log.d("url", url);
        }
         */

        Request request = new Request.Builder().url(url).build();
       client.newCall(request).enqueue(new Callback() {
           @Override
           public void onFailure(@NonNull Call call, @NonNull IOException e) {
           }
           @Override
           public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
               final String myResponse = response.body().string();

               BDJSONDeserializacja JSONDeserializacja = new BDJSONDeserializacja(activity, myResponse, autoCompleteTextView, bdKomunikacjaCel);
               JSONDeserializacja.run();
           }
       });







    }
}
