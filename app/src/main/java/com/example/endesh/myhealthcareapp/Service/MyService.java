package com.example.endesh.myhealthcareapp.Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.Toast;

//import com.example.endesh.myhealthcareapp.R;

public class MyService extends Service {
    MediaPlayer myPlayer;
    public MyService(){
    }
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("not yet implemeyed");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this,"onCreate in service is called",Toast.LENGTH_LONG).show();
        myPlayer = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        myPlayer.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        myPlayer.stop();
        Toast.makeText(this,"onDestroy in service is called",Toast.LENGTH_SHORT).show();

    }
}
