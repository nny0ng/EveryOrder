package com.example.kiosk;

import static android.speech.tts.TextToSpeech.ERROR;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.ArrayList;
import java.util.Locale;

public class V_menu extends Service {

    public TextToSpeech tts;
    Intent sttIntent;
    SpeechRecognizer mRecognizer;

    public V_menu() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // tts: label 2
        SpeakManager.speak("돈가스, 리조또, 파스타, 음료 중 원하는 분류를 선택하세요. 주문을 원하시면 주문, 직원 호출을 원하시면 호출 이라고 말해주세요", TextToSpeech.QUEUE_FLUSH);

//        try {
//            Thread.sleep(9500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        // STT 설정
        sttIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        sttIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getPackageName());
        sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR");

        mRecognizer = SpeechRecognizer.createSpeechRecognizer(V_menu.this);
        mRecognizer.setRecognitionListener(listener);

        // STT 시작
        mRecognizer.startListening(sttIntent);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // TTS 객체가 남아있다면 실행을 중지하고 메모리에서 제거
        if(tts != null){
            tts.stop();
            tts.shutdown();
            tts = null;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // listener: label 2
    private Listener listener = new Listener(){
        @Override
        public void onReadyForSpeech(Bundle params) {
            super.onReadyForSpeech(params);
            try {
                Thread.sleep(9500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onResults(Bundle results) {
            String service;
            ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            Log.d("STT", String.valueOf(matches));
            service = matches.toString().replaceAll(" ", "");
            if (service.lastIndexOf("말해주세요") >= 0) {
                // 있다면
                speakMenu(service.substring(service.indexOf("말해주세요")+5));
            } else {
                // 없다면
                speakMenu(service);
            }
            speakMenu(matches.toString());

            // STT data to TTS
            //tts.speak(matches.toString(), TextToSpeech.QUEUE_FLUSH, null);
        }

        public void speakMenu (String matches) {
            Intent intent;
            if (matches.contains("돈가스") || matches.contains("돈까스")) {
                SpeakManager.speak("돈가스 메뉴들", TextToSpeech.QUEUE_FLUSH);
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Service", "MENU");
                startActivity(intent);
                stopSelf();
            }
            else if (matches.contains("리조또")) {
                tts.speak("리조또 메뉴들", TextToSpeech.QUEUE_FLUSH, null);
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Service", "MENU");
                startActivity(intent);
                stopSelf();
            }
            else if (matches.contains("파스타")) {
                tts.speak("파스타 메뉴들", TextToSpeech.QUEUE_FLUSH, null);
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Service", "MENU");
                startActivity(intent);
                stopSelf();
            }
            else if (matches.contains("음료")) {
                tts.speak("음료들", TextToSpeech.QUEUE_FLUSH, null);
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
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Service", "MENU");
                startActivity(intent);
                stopSelf();
            }
        }
    };

}