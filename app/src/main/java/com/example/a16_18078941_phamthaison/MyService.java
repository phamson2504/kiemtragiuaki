package com.example.a16_18078941_phamthaison;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;


public class MyService extends Service {
    public static MediaPlayer mp;
    @Override

    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        if(bundle != null){

        }
        return START_NOT_STICKY;
    }


    public class MyBinder extends Binder{
        public MyService getService(){
            return MyService.this;
        };
    }
    public void nghenhac(){
        mp = MediaPlayer.create(
                this, R.raw.gaclaiaulo);
        mp.setLooping(false);
        mp.start();
    }
    public void Tnghenhac(){
        mp.stop();
    }
}
