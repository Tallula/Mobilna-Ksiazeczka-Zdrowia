package com.example.mobilnaksiazkazdrowia;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;

public class Powiadomienia {
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private Calendar calendar;
    //Context context;
    Context kontekst;
    Object kontekstObj;
    public Powiadomienia(Object kontekstObj)
    {
        kontekstObj = kontekstObj;
        kontekst=(Context) kontekstObj;
    }
    public  void stworzKanalPowiadomien() {

         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
             CharSequence nazwa = "foxandroidReminderChannel";
             String opis = "Channel For Alarm Manager";
             int poziomWaznosci = NotificationManager.IMPORTANCE_HIGH;
             NotificationChannel kanalPowiadomien = new NotificationChannel("idKanalu1",nazwa,poziomWaznosci);
             kanalPowiadomien.setDescription(opis);

             NotificationManager menadzerPowiadomien = kontekst.getSystemService(NotificationManager.class);
             menadzerPowiadomien.createNotificationChannel(kanalPowiadomien);
         }
    }
    void ustawPowiadomienie(String dataNajblizszejWizyty){

        String[] data = dataNajblizszejWizyty.split("-");
        //Toast.makeText(kontekst, data[0], Toast.LENGTH_SHORT).show();

        calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(data[0]));
        calendar.set(Calendar.MONTH, Integer.parseInt(data[1]));
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(data[1])-1);
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE,30);
        calendar.set(Calendar.SECOND,0);

        alarmManager = (AlarmManager)kontekst.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(kontekst, Alarm.class);

        pendingIntent = PendingIntent.getBroadcast(kontekst,0,intent,0);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,pendingIntent);
        
        }
    }

