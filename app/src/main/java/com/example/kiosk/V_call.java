package com.example.kiosk;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;

public class V_call extends Service {
    public V_call() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        SpeakManager.speak("직원을 호출했습니다. 잠시만 기다려주세요!", TextToSpeech.QUEUE_FLUSH);
        Intent nextIntent;
        nextIntent = new Intent(getApplicationContext(), MainActivity.class);
        nextIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        nextIntent.putExtra("Service", "MAIN");
        startActivity(nextIntent);
        stopSelf();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}