package com.example.mobilnaksiazkazdrowia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class PlanowanieSpotkaniaOkno extends AppCompatActivity {

    DatePickerDialog.OnDateSetListener onDateSetListener;
    String dataWizyty="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planowanie_spotkania_okno);

        Button zapiszWizyteButton = (Button)findViewById(R.id.zapiszWizyteButton);

        EditText terminWizytyEditText = (EditText)findViewById(R.id.terminWizytyEditText);
        EditText celWizytyEditText = (EditText)findViewById(R.id.celWizytyEditText);
        EditText opisWizytyEditText = (EditText)findViewById(R.id.opisWizytyEditText);

        InputMethodManager manager =(InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE
        );
        manager.hideSoftInputFromWindow(terminWizytyEditText.getApplicationWindowToken(),0);

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        terminWizytyEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        PlanowanieSpotkaniaOkno.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        onDateSetListener,year,month,day );
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month +1;
                dataWizyty = year + "-" + month + "-"+ dayOfMonth;
                terminWizytyEditText.setText(dataWizyty);
            }
        };
        zapiszWizyteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //zapis wizyty
                String celWizyty = celWizytyEditText.getText().toString();
                String opisWizyty = opisWizytyEditText.getText().toString();
                String idBadanegoPsa = WeterynarzOkno.badanyPiesInfo[0];

                celWizytyEditText.setText("");
                opisWizytyEditText.setText("");
                terminWizytyEditText.setText("");

                WebView web = new WebView(getApplicationContext());

               // web.loadUrl(Linki.zwrocDodawanieWizytyFolder()+ "dodajWizyte.php?" + "par1=" + dataWizyty +  "&par2=" + celWizyty +
                 //      "&par3=" + opisWizyty + "&par4=0&par5=" + idBadanegoPsa+ "&par6=" + ZalogowanyUzytkownik.wezIdUzytkownika());
                //w razie w

                String[] argumenty = {dataWizyty,celWizyty,opisWizyty,idBadanegoPsa };
                BDKomunikacjaWprowadzanie bdKomunikacjaWprowadzanie =
                        new BDKomunikacjaWprowadzanie(PlanowanieSpotkaniaOkno.this,  BDKomunikacjaCel.WPROWADZ_ZAPLANOWANA_WIZYTE,new WebView(getApplicationContext()), argumenty);
                bdKomunikacjaWprowadzanie.start();
            }
        });


    }
}