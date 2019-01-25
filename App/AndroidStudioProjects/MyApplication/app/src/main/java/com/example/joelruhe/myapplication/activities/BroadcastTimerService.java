package com.example.joelruhe.myapplication.activities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.joelruhe.myapplication.R;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import java.util.logging.Filter;

public class BroadcastTimerService extends Service {

    static long TIME_LIMIT;
    CountDownTimer Count;
    int progress;

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        //registerReceiver(new UserStopServiceReceiver(),  new IntentFilter(USER_STOP_SERVICE_REQUEST));

        TIME_LIMIT = intent.getIntExtra("secondsId", 0);

        final int id = intent.getIntExtra("Id", 0);

        int clicked = intent.getIntExtra("clicked", 0);

        if (clicked == 2) {
            Count = new CountDownTimer(TIME_LIMIT * 1000 * 60 * 60 * 24, 1000) {
                String time2;
                public void onTick(long millisUntilFinished) {
                    Intent i = new Intent("COUNTDOWN_UPDATED");

                    long days = millisUntilFinished / (24 * 60 * 60 * 1000);
                    long hours = millisUntilFinished / (60 * 60 * 1000) % 24;
                    long minutes = millisUntilFinished / (60 * 1000) % 60;
                    long seconds = millisUntilFinished / 1000 % 60;
                    String time = String.format("%02d:%02d:%02d:%02d", days, hours, minutes, seconds);
                    i.putExtra("countdown", time + " until harvest!");

                    TIME_LIMIT = intent.getIntExtra("secondsId", 0);
                    long ti = TIME_LIMIT * 1000 * 60 * 60 * 24;
                    long daysUp = (ti - millisUntilFinished)  / (24 * 60 * 60 * 1000);
                    long hoursUp = (ti - millisUntilFinished)  / (60 * 60 * 1000) % 24;
                    long minutesUp = (ti - millisUntilFinished)  / (60 * 1000) % 60;
                    long secondsUp = (ti - millisUntilFinished) / 1000 % 60;

                    progress++;

                    time2 = String.format("%02d:%02d:%02d:%02d", daysUp, hoursUp, minutesUp, secondsUp);
                    i.putExtra("harvestStarted", "Planted " + time2 + " ago");
                    i.putExtra("progress", secondsUp);
                    i.putExtra("showReset", 2);
                    i.putExtra("id", id);
                    i.putExtra("progress", progress);

                    sendBroadcast(i);
                }

                public void onFinish() {
                    Intent i = new Intent("COUNTDOWN_UPDATED");
                    i.putExtra("countdown", "Completed harvest!");
                    i.putExtra("harvestStarted", "Started " + time2 + " ago");
                    i.putExtra("hideReset", 1);
                    i.putExtra("id", id);
                    sendBroadcast(i);
                    cancel();
                }
            };

            Count.start();
        }

        startForeground(1, new Notification());
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //sendBroadcast(new Intent(USER_STOP_SERVICE_REQUEST));
        Intent i = new Intent("COUNTDOWN_UPDATED");
        i.putExtra("hideReset", 1);
        i.putExtra("hideResetView", 1);
        sendBroadcast(i);
        if (Count != null)
        Count.cancel();
    }
}