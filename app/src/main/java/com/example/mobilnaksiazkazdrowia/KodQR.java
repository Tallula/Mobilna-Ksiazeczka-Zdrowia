package com.example.mobilnaksiazkazdrowia;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class KodQR extends AppCompatActivity {

    public Bitmap wygenerujQR(String tresc, ImageView QRView){
        MultiFormatWriter writer = new MultiFormatWriter();
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

    public Bitmap wygenerujQR(String tresc){
        MultiFormatWriter writer = new MultiFormatWriter();
        Bitmap bitmap = null;
        try {
            BitMatrix matrix = writer.encode(tresc, BarcodeFormat.QR_CODE,350,350);
            BarcodeEncoder encoder = new BarcodeEncoder();
            bitmap = encoder.createBitmap(matrix);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public void zapiszQR(Bitmap bitmap){
        try{
        File outFile = new File("/storage/emulated/0/Download/APLIKACJA/","kodQRBadanegoPsa.bmp");
        FileOutputStream  ouS = new FileOutputStream(outFile);
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,ouS);
        }catch(Exception e){
        e.printStackTrace();
        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT);
        }
    }

    public void odczytQR(IntentResult wynik, Activity aktywnosc){

        if(wynik.getContents()!=null){
            AlertDialog.Builder builder =new AlertDialog.Builder( aktywnosc );
            builder.setTitle("Rezultat");
            builder.setMessage(wynik.getContents());

            WeterynarzOkno.badanyPiesInfo = wynik.getContents().split(",");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }});
            builder.show();

        }  else
        {
            Toast.makeText(getApplicationContext(), "Nie zeskanowales nic!!!", Toast.LENGTH_SHORT);
        }

    }

}
