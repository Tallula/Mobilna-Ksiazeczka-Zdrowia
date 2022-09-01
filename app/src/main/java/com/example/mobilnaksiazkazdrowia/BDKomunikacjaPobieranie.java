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

public class BDKomunikacjaPobieranie extends Thread{

    Activity activity;
    AutoCompleteTextView daneACTextView;
    BDKomunikacjaCel bdKomunikacjaCel;
    String url ="";
    static String arg;
    public static String idMiasta;

    BDKomunikacjaPobieranie(Activity activity, AutoCompleteTextView daneACTextView, BDKomunikacjaCel bdKomunikacjaCel, String arg){
        this.activity = activity;
        this.daneACTextView = daneACTextView;
        this.bdKomunikacjaCel=bdKomunikacjaCel;
        this.arg = arg;
    }
    @Override
    public void run() {
        OkHttpClient client = new OkHttpClient();
        switch(bdKomunikacjaCel){
            case POBIERZ_CZY_UZYTKOWNIK_ISTNIEJE:
               url= Linki.zwrocRejestracjaFolder() + "sprawdzCzyIstnieje.php?par1=" + arg;
                break;
            case POBIERZ_JAKI_UZYTKOWNIK:
                String[] dane = arg.split(",");
                url = Linki.zwrocLogowanieFolder()+ "zalogujUzytkownika.php?par1=" +dane[0] + "&par2=" + dane[1];
                break;
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

            case POBIERZ_DANE_O_ZWIERZETACH:
                url = Linki.zwrocLogowanieFolder() + "czytajDaneZwierzecia.php?par1=" + ZalogowanyUzytkownik.wezIdUzytkownika();
                break;
            case POBIERZ_RASY_PSOW:
                url = Linki.zwrocDodawaniePsaFormularzFolder() + "czytajRasy.php";
                break;
            case POBIERZ_DANE_O_WIZYTACH:
                url = Linki.zwrocPobieranieWizytyFolder() + "czytajWizyty.php?par1=" + ZalogowanyUzytkownik.wezIdUzytkownika() + "&par2="+ Wizyty.idWizytyMax ;
                break;
        }

        Request request = new Request.Builder().url(url).build();
       client.newCall(request).enqueue(new Callback() {
           @Override
           public void onFailure(@NonNull Call call, @NonNull IOException e) {
           }
           @Override
           public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
               final String wynik = response.body().string();
               BDJSONDeserializacja JSONDeserializacja = new BDJSONDeserializacja
                       (activity, wynik, daneACTextView, bdKomunikacjaCel);
               JSONDeserializacja.run();
           }
       });

    }
}
