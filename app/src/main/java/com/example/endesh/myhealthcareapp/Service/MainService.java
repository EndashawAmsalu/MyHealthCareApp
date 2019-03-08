package com.example.endesh.myhealthcareapp.Service;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.endesh.myhealthcareapp.R;

public class MainService extends AppCompatActivity {
    Button btnStart,btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_service);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
    }

    public void serviceStart(View view) {
        Intent i = new Intent(this, MyService.class);
        startService(i);

    }

    public void serviceStop(View view) {
        Intent i = new Intent(this, MyService.class);
        stopService(i);
    }
}
