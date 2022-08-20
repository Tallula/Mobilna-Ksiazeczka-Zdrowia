package com.example.mobilnaksiazkazdrowia;

public class ZalogowanyUzytkownik {


    private static String eMail="";
    private static String imie="";
    private static String nazwisko="";
    private static String numTel="";
    private static String idUzytkownika="";
    private static String typUzytkownika="";
    public static boolean czyIstnieje=false;

    public static void ustawTypUzytkownika(String typUzytkownikaArg){

        typUzytkownika=typUzytkownikaArg;
    }
    public static String wezTypUzytkownika() {
        return typUzytkownika;
    }

    public static void ustawEMail(String eMailArg){

        eMail=eMailArg;
    }
    public static String wezeMail() {
        return eMail;
    }


    public static void ustawImie(String imieArg){

        imie=imieArg;
    }
    public static String wezImie() {
        return imie;
    }


    public static void ustawNazwisko(String nazwiskoArg){

        nazwisko=nazwiskoArg;
    }
    public static String wezNazwisko() {
        return nazwisko;
    }


    public static void ustawNumTel(String numTelArg){

        numTel=numTelArg;
    }
    public static String wezNumTel() {
        return numTel;
    }


    public static void ustawIdUzytkownika(String idUzytkownikaArg){

        idUzytkownika=idUzytkownikaArg;
    }
    public static String wezIdUzytkownika() {
        return idUzytkownika;
    }











}
