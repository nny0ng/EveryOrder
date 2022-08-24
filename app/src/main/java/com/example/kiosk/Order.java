package com.example.kiosk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Order extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        processIntent(intent);
        super.onNewIntent(intent);
    }

    private void processIntent(Intent intent) {
        Intent serviceIntent;
        if (intent != null) {
            String Service = intent.getStringExtra("Service");
            if (Service.equals("ORDER")) {
                serviceIntent  = new Intent(getApplicationContext(), V_order.class);
                startService(serviceIntent);
            }
        }
    }
}