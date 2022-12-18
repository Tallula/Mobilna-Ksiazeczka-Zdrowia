package com.example.mobilnaksiazkazdrowia;


import android.widget.ArrayAdapter;

import android.app.Activity;

import android.widget.AutoCompleteTextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BDJSONDeserializacja implements Runnable {
    Activity activity;
    String wynik;

    public static String[] idMiastZczytane;
    public static String[] idUlicZczytane;
    public static String[] nazwyMiastZczytane;
    public static String[] nazwyUlicZczytane;
    public static String[] nazwyRasZczytane;
    public static String[] idRasZczytane;
    String czyUzytkownikIstnieje = "";

    AutoCompleteTextView daneACTextView;
    BDKomunikacjaCel bdKomunikacjaCel;

    public BDJSONDeserializacja(Activity activity, String wynik, AutoCompleteTextView daneACTextView, BDKomunikacjaCel bdKomunikacjaCel){
        this.activity=activity;
        this.wynik = wynik;
        this.daneACTextView = daneACTextView;
        this.bdKomunikacjaCel=bdKomunikacjaCel;
    }
    @Override
    public void run() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                {
                    try {
                        JSONArray wynikJSONTab = new JSONArray(wynik);
                        switch(bdKomunikacjaCel){
                            case POBIERZ_CZY_UZYTKOWNIK_ISTNIEJE:
                                JSONObject jsonObject1 = wynikJSONTab.getJSONObject(0);
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
                                JSONObject jsonZalogowanyUzytkownik = wynikJSONTab.getJSONObject(0);
                                //ZalogowanyUzytkownik.ustawTypUzytkownika(jsonZalogowanyUzytkownik.getString("rodzajUzytkownika"));

                                //ZalogowanyUzytkownik.ustawIdUzytkownika(jsonZalogowanyUzytkownik.getString("idUzytkownika"));

                                ZalogowanyUzytkownik.typUzytkownika=jsonZalogowanyUzytkownik.getString("rodzajUzytkownika");
                                ZalogowanyUzytkownik.idUzytkownika=jsonZalogowanyUzytkownik.getString("idUzytkownika");
                                break;
                            case POBIERZ_MIASTA:
                                nazwyMiastZczytane = new String[wynikJSONTab.length()];
                                idMiastZczytane = new String[wynikJSONTab.length()];
                                for (int i = 0; i < wynikJSONTab.length(); i++) {
                                    JSONObject jsonObject = wynikJSONTab.getJSONObject(i);
                                    nazwyMiastZczytane[i] = jsonObject.getString("nazwaMiasta");
                                    idMiastZczytane[i] = jsonObject.getString("idMiasta");
                                }
                                ArrayAdapter<String> adapterMiasta = new ArrayAdapter<String>
                                        (activity.getApplicationContext(),
                                                android.R.layout.simple_spinner_item, nazwyMiastZczytane);
                                daneACTextView.setAdapter(adapterMiasta);
                                break;
                            case POBIERZ_ULICE:
                                nazwyUlicZczytane = new String[wynikJSONTab.length()];
                                idUlicZczytane = new String[wynikJSONTab.length()];
                                for (int i = 0; i < wynikJSONTab.length(); i++) {
                                    JSONObject jsonObject = wynikJSONTab.getJSONObject(i);
                                    nazwyUlicZczytane[i] = jsonObject.getString("nazwaUlicy");
                                    idUlicZczytane[i] = jsonObject.getString("idUlicy");
                                }
                                ArrayAdapter<String> adapterUlice = new ArrayAdapter<String>
                                        (activity.getApplicationContext(),
                                                android.R.layout.simple_spinner_item, nazwyUlicZczytane);
                                daneACTextView.setAdapter(adapterUlice);
                                break;
                            case POBIERZ_DANE_OSOBOWE:
                                if(wynikJSONTab.length()>0)
                                {
                                    for (int i = 0; i < wynikJSONTab.length(); i++) {
                                        JSONObject jsonObject = wynikJSONTab.getJSONObject(i);
                                        //ZalogowanyUzytkownik.ustawIdUzytkownika(jsonObject.getString("idOsoby"));
                                        //ZalogowanyUzytkownik.ustawImie(jsonObject.getString("imie"));
                                        //ZalogowanyUzytkownik.ustawNazwisko(jsonObject.getString("nazwisko"));
                                       // ZalogowanyUzytkownik.ustawNumTel(jsonObject.getString("numerTelefonu"));

                                        ZalogowanyUzytkownik.idUzytkownika=jsonObject.getString("idOsoby");
                                        ZalogowanyUzytkownik.imie=jsonObject.getString("imie");
                                        ZalogowanyUzytkownik.nazwisko =jsonObject.getString("nazwisko");
                                         ZalogowanyUzytkownik.numTel=jsonObject.getString("numerTelefonu");
                                    }
                                }
                                break;
                            case POBIERZ_DANE_O_ZWIERZETACH:
                                ZwierzetaWlasciciela.imie = new String[wynikJSONTab.length()];
                                ZwierzetaWlasciciela.idPsa = new String[wynikJSONTab.length()];
                                ZwierzetaWlasciciela.rasaPsa = new String[wynikJSONTab.length()];

                                for (int i = 0; i < wynikJSONTab.length(); i++) {
                                    JSONObject jsonObject = wynikJSONTab.getJSONObject(i);
                                    ZwierzetaWlasciciela.imie[i] = jsonObject.getString("imie");
                                    ZwierzetaWlasciciela.idPsa[i] = jsonObject.getString("idPsa");
                                    ZwierzetaWlasciciela.rasaPsa[i] = jsonObject.getString("nazwaRasy");
                                }
                                break;
                            case POBIERZ_RASY_PSOW:
                                nazwyRasZczytane = new String[wynikJSONTab.length()];
                                idRasZczytane = new String[wynikJSONTab.length()];
                                for (int i = 0; i < wynikJSONTab.length(); i++) {
                                    JSONObject jsonObject = wynikJSONTab.getJSONObject(i);
                                    nazwyRasZczytane[i] = jsonObject.getString("nazwa");
                                    idRasZczytane[i] = jsonObject.getString("idRasy");
                                }
                                ArrayAdapter<String> adapterRasy = new ArrayAdapter<String>
                                        (activity.getApplicationContext(), android.R.layout.simple_spinner_item, nazwyRasZczytane);
                                daneACTextView.setAdapter(adapterRasy);
                                break;
                            case POBIERZ_DANE_O_WIZYTACH:
                                Wizyta.dataWizyty = new String[wynikJSONTab.length()];
                                Wizyta.imiePsa = new String[wynikJSONTab.length()];
                                Wizyta.celWizyty = new String[wynikJSONTab.length()];
                                Wizyta.idWizyty = new String[wynikJSONTab.length()];

                                for (int i = 0; i < wynikJSONTab.length(); i++) {
                                    JSONObject jsonObject = wynikJSONTab.getJSONObject(i);
                                    Wizyta.imiePsa[i] = jsonObject.getString("imie");
                                    Wizyta.celWizyty[i] = jsonObject.getString("cel");
                                    Wizyta.dataWizyty[i] = jsonObject.getString("dataWizyty");
                                    Wizyta.idWizyty[i] = jsonObject.getString("idWizyty");
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
