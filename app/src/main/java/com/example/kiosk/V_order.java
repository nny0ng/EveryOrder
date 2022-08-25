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
import java.util.Dictionary;
import java.util.List;
import java.util.Locale;

public class V_order extends Service {
    // listener: label 3
    // cartListener: 무엇을 추가/삭제 하시겠습니까?
    // checkListener: ~ n개를 장바구니에 추가할까요?
    // completeListener: (장바구니 전체)를 주문하시겠습니까?

    public TextToSpeech tts;
    Intent sttIntent;
    SpeechRecognizer mRecognizer;

    public V_order() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // tts: label 3
        SpeakManager.speak("현재 장바구니에는 이민지, 이선호가 있습니다. 추가를 원하시면 추가, 삭제를 원하시면 삭제, 주문 확정을 원하시면 확정 이라고 말해주세요", TextToSpeech.QUEUE_FLUSH);

        try {
            Thread.sleep(9500); // 초 계산하기
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // STT 설정
        sttIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        sttIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");

        mRecognizer = SpeechRecognizer.createSpeechRecognizer(V_order.this);
        mRecognizer.setRecognitionListener(listener);

        // STT 시작
        mRecognizer.startListening(sttIntent);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // TTS 객체가 남아있다면 실행을 중지하고 메모리에서 제거
        if (tts != null) {
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

    private Listener listener = new Listener() {
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
                cart(service.substring(service.indexOf("말해주세요")+5));
            } else {
                // 없다면
                cart(service);
            }
            cart(matches.toString());
            // STT data to TTS
            //tts.speak(matches.toString(), TextToSpeech.QUEUE_FLUSH, null);
        }

        public void cart(String matches) {
            Intent intent;
            mRecognizer = SpeechRecognizer.createSpeechRecognizer(V_order.this);
            mRecognizer.setRecognitionListener(cartListener);

            if (matches.contains("추가")) {
                cartListener.tmp ="추가";
                SpeakManager.speak("무엇을 추가하시겠습니까?", TextToSpeech.QUEUE_FLUSH);
                mRecognizer.startListening(sttIntent);
            } else if (matches.contains("삭제")) {
                cartListener.tmp ="삭제";
                SpeakManager.speak("무엇을 삭제하시겠습니까?", TextToSpeech.QUEUE_FLUSH);
                mRecognizer.startListening(sttIntent);
            } else if (matches.contains("확정")) {
                mRecognizer = SpeechRecognizer.createSpeechRecognizer(V_order.this);
                mRecognizer.setRecognitionListener(completeListener);
                SpeakManager.speak("(장바구니 전체)를 주문하시겠습니까?", TextToSpeech.QUEUE_FLUSH);
                mRecognizer.startListening(sttIntent);
            } else {
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Service", "MAIN");
                startActivity(intent);
                stopSelf();
            }
        }
    };

    private Listener cartListener = new Listener(){
        @Override
        public void onReadyForSpeech(Bundle params) {
            super.onReadyForSpeech(params);
            try {
                Thread.sleep(5500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onResults(Bundle results) {
            ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            Log.d("STT", String.valueOf(matches));
            addToCart(matches.toString()); // 단어 분류 함수
            // STT data to TTS
            //tts.speak(matches.toString(), TextToSpeech.QUEUE_FLUSH, null);
        }

        private void addToCart(String matches) {
            // 문장 인식하고 단어, 수량 파악해서
            ArrayList<List<String>> list = new ArrayList<>();
            list.add(Fragment_tab1.listname);
            list.add(Fragment_tab2.listname);
            list.add(Fragment_tab3.listname);
            list.add(Fragment_tab4.listname);

            //해당 데이터 액티비티로 옮겨서 장바구니에만 넣으면 됨 ㅋㅎㅎㅋㅋㅋㅋ
            for(List<String> foods : list){
                for(String food : foods){
                    if(matches.contains(food)){

                    }
                }
            }


            mRecognizer = SpeechRecognizer.createSpeechRecognizer(V_order.this);
            mRecognizer.setRecognitionListener(checkListener);
            SpeakManager.speak("~ 1개를 장바구니에 "+ tmp +"할까요?", TextToSpeech.QUEUE_FLUSH);
            mRecognizer.startListening(sttIntent);
        }
    };

    private Listener checkListener = new Listener() {
        @Override
        public void onResults(Bundle results) {
            Intent intent;
            ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            Log.d("STT", String.valueOf(matches));

            if (matches.toString().contains("네")) { // 후보지 나중에 추가
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Service", "ORDER");
                startActivity(intent);
                stopSelf();
            }
            else if (matches.toString().contains("아니요")) { // 후보지 나중에 추가
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Service", "MAIN");
                startActivity(intent);
                stopSelf();
            }
            // STT data to TTS
            //tts.speak(matches.toString(), TextToSpeech.QUEUE_FLUSH, null);
        }
    };

    private Listener completeListener = new Listener() {
        @Override
        public void onResults(Bundle results) {
            Intent intent;
            ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            Log.d("STT", String.valueOf(matches));

            if (matches.toString().contains("네")) { // 후보지 나중에 추가
                SpeakManager.speak("주문이 완료되었습니다 조금만 기다려주세요!", TextToSpeech.QUEUE_FLUSH);
                intent = new Intent(getApplicationContext(), Start.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                stopSelf();
            }
            else if (matches.toString().contains("아니요")) { // 후보지 나중에 추가
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Service", "MAIN");
                startActivity(intent);
                stopSelf();
            }
            else {
                tts.speak("인식에 실패했습니다", TextToSpeech.QUEUE_FLUSH, null);
                mRecognizer.startListening(sttIntent);
            }
        }
    };
}