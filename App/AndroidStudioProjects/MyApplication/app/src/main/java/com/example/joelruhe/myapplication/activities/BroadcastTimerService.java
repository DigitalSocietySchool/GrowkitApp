package com.example.joelruhe.myapplication.activities;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;

public class BroadcastTimerService extends Service {

    static long TIME_LIMIT;
    CountDownTimer Count;
    int progress;

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        // get the time for each specific plant
        TIME_LIMIT = intent.getIntExtra("secondsId", 0);

        // if timer clicked start timer on the right plant id
        final int id = intent.getIntExtra("Id", 0);

        int clicked = intent.getIntExtra("clicked", 0);

        if (clicked == 2) {
            Count = new CountDownTimer(TIME_LIMIT * 1000 * 60 * 60 * 24, 1000) {
                String time2;
                @SuppressLint("DefaultLocale")
                public void onTick(long millisUntilFinished) {
                    Intent i = new Intent("COUNTDOWN_UPDATED");

                    long days = millisUntilFinished / (24 * 60 * 60 * 1000);
                    long hours = millisUntilFinished / (60 * 60 * 1000) % 24;
                    long minutes = millisUntilFinished / (60 * 1000) % 60;
                    long seconds = millisUntilFinished / 1000 % 60;
                    @SuppressLint("DefaultLocale") String time = String.format("%02d:%02d:%02d:%02d", days, hours, minutes, seconds);
                    i.putExtra("countdown", time + " until harvest!");

                    TIME_LIMIT = intent.getIntExtra("secondsId", 0);
                    long ti = TIME_LIMIT * 1000 * 60 * 60 * 24;
                    long daysUp = (ti - millisUntilFinished)  / (24 * 60 * 60 * 1000);
                    long hoursUp = (ti - millisUntilFinished)  / (60 * 60 * 1000) % 24;
                    long minutesUp = (ti - millisUntilFinished)  / (60 * 1000) % 60;
                    long secondsUp = (ti - millisUntilFinished) / 1000 % 60;

                    progress++;

                    time2 = String.format("%02d:%02d:%02d:%02d", daysUp, hoursUp, minutesUp, secondsUp);

                    // save the harvest started in the service
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
        Intent i = new Intent("COUNTDOWN_UPDATED");

        // save the hide reset in the right plant page
        i.putExtra("hideReset", 1);
        i.putExtra("hideResetView", 1);
        sendBroadcast(i);
        if (Count != null)
        Count.cancel();
    }
}