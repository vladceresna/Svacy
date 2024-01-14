package com.rula.svacy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Timer timer = new Timer();
    Intent intent = new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                intent.setClass(MainActivity.this, WebActivity.class);
                intent.setData(Uri.parse("https://www.google.com/"));
                startActivity(intent);
            }
        }, 2500);
    }
}