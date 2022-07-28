package com.example.mobilnaksiazkazdrowia;

public  class Linki{

    public static String adres ="http://192.168.0.171/";
    public static String projekt ="ksiazkazdrowia/";

    public static String rejestracjaFolder ="rejestracja/";
    public static String rejestracjaFormularzFolder ="RejestracjaFormularz/";
    public static String logowanieFolder ="logowanie/";
    public static String dodawaniePsaFormularzFolder ="DodawaniePsaFormularz/";
    public static String dodawaniePsaFolder ="DodawaniePsa/";

    public static String zwrocRejestracjaFolder(){
    return adres + projekt + rejestracjaFolder;
    }

    public static String zwrocLogowanieFolder(){
        return adres + projekt + logowanieFolder;
    }

    public static String zwrocDodawaniePsaFormularzFolder(){ return adres + projekt + dodawaniePsaFormularzFolder; }


    public static String zwrocRejestracjaFormularzFolder(){ return adres + projekt + rejestracjaFormularzFolder;
    }

    public static String zwrocDodawaniePsaFolder(){ return adres + projekt + dodawaniePsaFolder; }

}
