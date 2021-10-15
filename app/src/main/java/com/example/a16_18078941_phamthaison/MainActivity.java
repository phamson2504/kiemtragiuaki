package com.example.a16_18078941_phamthaison;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    private NotificationManagerCompat notificationManagerCompat;
    private MyService myService;
    private boolean isBound = false;
    private ServiceConnection serviceConnection;
    private boolean isConnected;
    private SeekBar seekBar;
    private Handler handler;
    TextView tenBH;
    ImageView imageView,play,textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play = findViewById(R.id.play);
        imageView = findViewById(R.id.detalAnh);
        textView = findViewById(R.id.imageView);
        seekBar = findViewById(R.id.seekBar);
        tenBH=findViewById(R.id.detailname);
        handler =  new Handler();


        Animation animation1 =
                AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.aniblink);
        textView.startAnimation(animation1);
        connectService();
        this.notificationManagerCompat = NotificationManagerCompat.from(this);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myService.mp!=null &&myService.mp.isPlaying()){
                    myService.Tnghenhac();

                    play.setImageResource(R.drawable.p1);
                }else{

                    myService.nghenhac();
                    seekBar.setMax(myService.mp.getDuration());
                    play.setImageResource(R.drawable.p4);
                    isBound = false;
                    update();
                    sendOnChannel1();
                }
            }
        });


    }
    private void connectService() {

        Intent intent = new Intent(this, MyService.class);

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MyService.MyBinder myBinder = (MyService.MyBinder) service;

                myService = myBinder.getService();
                isConnected = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                isConnected = false;
                myService = null;
            }
        };
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }
    private void update() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                seekBar.setProgress(myService.mp.getCurrentPosition());
                handler.postDelayed(this, 500);
            }
        }, 500);
    }
    private void sendOnChannel1()  {
        String title = this.tenBH.getText().toString();
        Bitmap bitmap =BitmapFactory.decodeResource(getResources(),R.drawable.gaclaiaulo);
        Notification notification = new NotificationCompat.Builder(this, NotificationApp.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.icon_notify2)
                .setContentTitle(title)
                .setLargeIcon(bitmap)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();


        int notificationId = 1;
        this.notificationManagerCompat.notify(notificationId, notification);
    }
}