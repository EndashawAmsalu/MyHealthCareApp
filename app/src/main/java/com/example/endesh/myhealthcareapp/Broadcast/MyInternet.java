package com.example.endesh.myhealthcareapp.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.endesh.myhealthcareapp.R;

public class MyInternet extends AppCompatActivity {

    private WifiManager wifiManager;
    private Switch wifiSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_internet);
        wifiSwitch = findViewById(R.id.wifi_switch);;


        wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    wifiManager.setWifiEnabled(true);
                    wifiSwitch.setText("WiFi is on you can now seek for online help");
                }else {
                    wifiManager.setWifiEnabled(false);
                    wifiSwitch.setText("Hoops WiFi is off! put it on");
                }
            }
        });

    }



    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);

        registerReceiver(wifiStateReceiver,intentFilter);


    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(wifiStateReceiver);
    }
    private BroadcastReceiver wifiStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int wifeStateExtract = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                    WifiManager.WIFI_STATE_UNKNOWN);

            switch (wifeStateExtract){
                case WifiManager.WIFI_STATE_ENABLED:
                    wifiManager.setWifiEnabled(true);
                    wifiSwitch.setText("You are connected go ahead and seek for online help");
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    wifiManager.setWifiEnabled(false);
                    wifiSwitch.setText("WiFi is off turn on your wifi before seeking for online help");


            }

        }
    };

    public void internetHelp(View view) {
        WebView webview = new WebView(this);
        setContentView(webview);
        webview.loadUrl("https://yourdoctors.online/");
    }

}
