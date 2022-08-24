package com.example.kiosk;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class V_call extends Service {
    public V_call() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}