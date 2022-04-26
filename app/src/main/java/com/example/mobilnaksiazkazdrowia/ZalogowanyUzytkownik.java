package com.example.mobilnaksiazkazdrowia;

public class ZalogowanyUzytkownik {




    private static String eMail="";
    private static String imie="";
    private static String nazwisko="";
    private static String numTel="";
    private static String idOsoby="";

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

    public static void ustawIdOsoby(String idOsobyArg){

        idOsoby=idOsobyArg;
    }
    public static String wezIdOsoby() {
        return idOsoby;
    }











}
