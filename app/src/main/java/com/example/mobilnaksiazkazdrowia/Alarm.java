package com.example.mobilnaksiazkazdrowia;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.mobilnaksiazkazdrowia.MainActivity;

public class Alarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,i,0);

        NotificationCompat.Builder kreatorPowiadomien = new NotificationCompat.Builder(context,"idKanalu1")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Mobilna Ksiazeczka Zdrowia Psa")
                .setContentText("Jutro czeka Cie wizyta u weterynarza")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat menadzerPowiadomien = NotificationManagerCompat.from(context);
        menadzerPowiadomien.notify(123,kreatorPowiadomien.build());


    }
}
