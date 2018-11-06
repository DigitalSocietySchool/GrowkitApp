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

    CountDownTimer mCountDownTimer = null;

    Context context;
    int totalSeconds;

    BroadcastTimerService(Context ctx) {
        context = ctx;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG, "Starting timer...");

        //totalSeconds = context.getIntent("ID", 0);

        mCountDownTimer = new CountDownTimer(totalSeconds * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                Log.i(TAG, "Countdown seconds remaining: " + millisUntilFinished / 1000);
                bi.putExtra("countdown", millisUntilFinished);
                sendBroadcast(bi);
            }

            @Override
            public void onFinish() {
                Log.i(TAG, "Timer finished");
            }
        };

        mCountDownTimer.start();

        /*progressBar = findViewById(R.id.progressBar);

        mCounterTimer = new CountDownTimer(totalSeconds * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                counter++;
                textCounter.setText(String.valueOf(counter));
                progressBar.setProgress(counter * 10000 / (totalSeconds * 100));
            }

            public void onFinish() {
                counter++;
                textCounter.setText(R.string.finish_harvest_time);
                progressBar.setProgress(100);
            }
        };
        mCounterTimer.start();*/

    }

    @Override
    public void onDestroy() {

        mCountDownTimer.cancel();
        Log.i(TAG, "Timer cancelled");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}
