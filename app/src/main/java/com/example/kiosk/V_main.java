package com.example.kiosk;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.widget.Toast;

import static android.speech.tts.TextToSpeech.ERROR;

import androidx.core.app.ActivityCompat;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class V_main extends Service {

    Intent sttIntent;
    SpeechRecognizer mRecognizer;
    public boolean done = false;

    public V_main() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // tts: label 1
        SpeakManager.speak("해당 프로그램은 터치+음성입니다. 터치하면 메뉴가 추가되니 주의해주세요 ,,,메뉴를 듣고싶으시면 메뉴, 주문을 하려면 주문, 직원 호출을 원하시면 호출 이라고 말해주세요", TextToSpeech.QUEUE_FLUSH);

        sttIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        sttIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getPackageName());
        sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR");

        mRecognizer = SpeechRecognizer.createSpeechRecognizer(V_main.this);
        mRecognizer.setRecognitionListener(listener);
        mRecognizer.startListening(sttIntent);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // listener: label 1
    private Listener listener = new Listener() {
        @Override
        public void onResults(Bundle results) {
            String service;
            ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            Log.d("main STT", String.valueOf(matches));
            service = matches.toString().replaceAll(" ", "");
            if (service.indexOf("말해주세요") >= 0) {
                // 있다면
                changeService(service.substring(service.indexOf("말해주세요")+5));
            } else {
                // 없다면
                changeService(service);
            }
        }

        public void changeService(String matches) {
            Intent intent;
            if (matches.contains("메뉴")) {
                Log.d("change service menu", String.valueOf(matches));
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Service", "MENU");
                startActivity(intent);
                stopSelf();
            }
            else if (matches.contains("주문")) {
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Service", "ORDER");
                startActivity(intent);
                stopSelf();
            }
            else if (matches.contains("호출")) {
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Service", "CALL");
                startActivity(intent);
                stopSelf();
            }
            else {
                // 무한 반복
                mRecognizer.startListening(sttIntent);
            }
        }
    };
}