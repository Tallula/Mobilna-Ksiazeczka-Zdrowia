package com.example.mobilnaksiazkazdrowia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class PlanowanieSpotkaniaOkno extends AppCompatActivity {

    DatePickerDialog.OnDateSetListener onDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planowanie_spotkania_okno);

        Button wygenerujQRButton = (Button)findViewById(R.id.wygenerujQRButton);
        ImageView QRView = (ImageView) findViewById(R.id.kodQRImageView);
        EditText dataEditText = (EditText)findViewById(R.id.dataEditText);
        InputMethodManager manager =(InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE
        );
        manager.hideSoftInputFromWindow(dataEditText.getApplicationWindowToken(),0);

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        dataEditText.setOnClickListener(new View.OnClickListener() {
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
                String date = dayOfMonth + "/" + month + "/"+ year;
                dataEditText.setText(date);

            }
        };

        wygenerujQRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KodQR kodQR = new KodQR();
                kodQR.wygenerujQR(dataEditText, QRView);

            }
        });

    }
}