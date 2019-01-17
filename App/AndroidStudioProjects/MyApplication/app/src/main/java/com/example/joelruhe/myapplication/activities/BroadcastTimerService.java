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

import java.util.concurrent.TimeUnit;
import java.util.logging.Filter;

public class BroadcastTimerService extends Service {

    static long TIME_LIMIT;
    CountDownTimer Count;
    final public static String USER_STOP_SERVICE_REQUEST = "USER_STOP_SERVICE";

    public class UserStopServiceReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            Count.onFinish();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        registerReceiver(new UserStopServiceReceiver(),  new IntentFilter(USER_STOP_SERVICE_REQUEST));

        TIME_LIMIT = intent.getIntExtra("secondsId", 0);
        final int id = intent.getIntExtra("Id", 0);

        int clicked = intent.getIntExtra("clicked", 0);

        if (clicked == 2) {
            Count = new CountDownTimer(TIME_LIMIT * 1000, 1000) {
                public void onTick(long millisUntilFinished) {
                    long seconds = millisUntilFinished / 1000;
                    String time = String.format("%02d:%02d", (seconds % 3600) / 60, (seconds % 60));

                    Intent i = new Intent("COUNTDOWN_UPDATED");
                    i.putExtra("countdown", time);
                    i.putExtra("showReset", 2);
                    i.putExtra("id", id);
                    sendBroadcast(i);
                }

                public void onFinish() {
                    Intent i = new Intent("COUNTDOWN_UPDATED");
                    i.putExtra("countdown", "Completed!");
                    i.putExtra("hideReset", 1);
                    i.putExtra("id", id);
                    sendBroadcast(i);
                    if(Count != null) {
                        Count.cancel();
                        Count = null;
                    }
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sendBroadcast(new Intent(USER_STOP_SERVICE_REQUEST));
    }
}