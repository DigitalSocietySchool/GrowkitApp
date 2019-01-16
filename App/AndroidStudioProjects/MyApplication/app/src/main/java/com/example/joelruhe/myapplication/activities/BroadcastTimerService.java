package com.example.joelruhe.myapplication.activities;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

public class BroadcastTimerService extends Service {

    private final static String TAG = "BroadcastService";

    public static final String COUNTDOWN_BR = "your_package_name.countdown_br";
    Intent bi = new Intent(COUNTDOWN_BR);

    CountDownTimer cdt = null;
    int progress;
    int id;
    int counter;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        cdt.cancel();
        Log.i(TAG, "Timer cancelled");
        super.onDestroy();
    }

    void startTimer(int id, int plantArray[][]) {
        int sec = plantArray[id][0];
        progress = 0;

        counter = sec;

        cdt = new CountDownTimer(sec * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                counter--;
                bi.putExtra("countdown", counter);
                progress++;
                bi.putExtra("progress", progress);
                sendBroadcast(bi);
            }

            @Override
            public void onFinish() {
            }
        };

        cdt.start();
    }

    int[][] getPlantData() {
        //These values will be pulled from the database!
        int plantArray[][] = {
                {10, 34, 35, 50},
                {20, 45, 34, 30},
                {30, 20, 40, 11},
                {15, 32, 28, 55},
                {10, 44, 7, 29}
        };

        return plantArray;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        id = intent.getIntExtra("UserID", 0);

        Log.i(TAG, "Starting timer...");

        startTimer(id, getPlantData());

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}
