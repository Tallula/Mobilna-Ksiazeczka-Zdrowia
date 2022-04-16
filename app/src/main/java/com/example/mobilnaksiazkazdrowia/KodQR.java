package com.example.mobilnaksiazkazdrowia;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class KodQR extends AppCompatActivity {

    public Bitmap wygenerujQR(EditText editText, ImageView QRView){
        MultiFormatWriter writer = new MultiFormatWriter();
        String tresc = editText.getText().toString();
        Bitmap bitmap = null;
        try {
            BitMatrix matrix = writer.encode(tresc, BarcodeFormat.QR_CODE,350,350);
            BarcodeEncoder encoder = new BarcodeEncoder();
            bitmap = encoder.createBitmap(matrix);
            QRView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
