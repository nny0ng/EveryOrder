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
        // STT 설정
        sttIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        sttIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getPackageName());
        sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR");

        mRecognizer = SpeechRecognizer.createSpeechRecognizer(V_menu.this);
        mRecognizer.setRecognitionListener(listener);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // tts: label 2
        if(intent.getStringExtra("FROM").equals("MAIN"))
            SpeakManager.speak("돈가스, 리조또, 파스타, 음료 중 원하는 분류를 선택하세요. 주문을 원하시면 주문, 직원 호출을 원하시면 호출 이라고 말해주세요", TextToSpeech.QUEUE_FLUSH);
        else
            SpeakManager.speak("돈가스, 리조또, 파스타, 음료 중 원하는 분류를 선택하세요. 주문을 원하시면 주문, 직원 호출을 원하시면 호출 이라고 말해주세요", TextToSpeech.QUEUE_ADD);

        try {
            Thread.sleep(10500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
//        @Override
//        public void onReadyForSpeech(Bundle params) {
//            super.onReadyForSpeech(params);
//            try {
//                Thread.sleep(10500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        @Override
        public void onResults(Bundle results) {
            String service;
            mRecognizer.stopListening();
            ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            Log.d("menuSTT", String.valueOf(matches));
            service = matches.toString().replaceAll(" ", "");
            if (service.lastIndexOf("말해주세요") >= 0) {
                // 있다면
                speakMenu(service.substring(service.indexOf("말해주세요")+5));
            } else {
                // 없다면
                speakMenu(service);
            }
            //speakMenu(matches.toString());

            // STT data to TTS
            //tts.speak(matches.toString(), TextToSpeech.QUEUE_FLUSH, null);
        }

        public void speakMenu (String matches) {
            Intent intent;
            if(mRecognizer!=null){
                mRecognizer.destroy();
                mRecognizer.cancel();
                mRecognizer = null;
            }
            if (matches.contains("돈가스") || matches.contains("돈까스")) {
                SpeakManager.speak("돈까스에는 ",TextToSpeech.QUEUE_FLUSH);
                for(int i=0; i<Fragment_tab1.listname.size(); i++){
                    SpeakManager.speak(Fragment_tab1.listname.get(i).replace("가","까")+',',TextToSpeech.QUEUE_ADD);
                }
                SpeakManager.speak("가 있습니다",TextToSpeech.QUEUE_ADD);
                SpeakManager.returnObject().playSilence(2000, TextToSpeech.QUEUE_ADD, null);
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Service", "MENU");
                intent.putExtra("FROM", "MENU");
                startActivity(intent);
                stopSelf();
            }
            else if (matches.contains("리조또")) {
                SpeakManager.speak("리조또에는 ",TextToSpeech.QUEUE_FLUSH);
                for(int i=0; i<Fragment_tab1.listname.size(); i++){
                    SpeakManager.speak(Fragment_tab2.listname.get(i)+',',TextToSpeech.QUEUE_ADD);
                }
                SpeakManager.speak("가 있습니다",TextToSpeech.QUEUE_ADD);
                SpeakManager.returnObject().playSilence(2000, TextToSpeech.QUEUE_ADD, null);
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Service", "MENU");
                intent.putExtra("FROM", "MENU");
                startActivity(intent);
                stopSelf();
            }
            else if (matches.contains("파스타")) {
                SpeakManager.speak("파스타에는 ",TextToSpeech.QUEUE_FLUSH);
                for(int i=0; i<Fragment_tab1.listname.size(); i++){
                    SpeakManager.speak(Fragment_tab3.listname.get(i)+',',TextToSpeech.QUEUE_ADD);
                }
                SpeakManager.speak("가 있습니다",TextToSpeech.QUEUE_ADD);
                SpeakManager.returnObject().playSilence(2000, TextToSpeech.QUEUE_ADD, null);
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Service", "MENU");
                startActivity(intent);
                stopSelf();
            }
            else if (matches.contains("음료")) {
                SpeakManager.speak("음료에는 ",TextToSpeech.QUEUE_FLUSH);
                for(int i=0; i<Fragment_tab1.listname.size(); i++){
                    SpeakManager.speak(Fragment_tab4.listname.get(i)+',',TextToSpeech.QUEUE_ADD);
                }
                SpeakManager.speak("가 있습니다",TextToSpeech.QUEUE_ADD);
                SpeakManager.returnObject().playSilence(2000, TextToSpeech.QUEUE_ADD, null);
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Service", "MENU");
                intent.putExtra("FROM", "MENU");
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
                SpeakManager.speak("직원을 호출했습니다. 잠시만 기다려주세요!", TextToSpeech.QUEUE_FLUSH);
                SpeakManager.returnObject().playSilence(2000, TextToSpeech.QUEUE_ADD, null);
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Service", "MAIN");
                startActivity(intent);
                stopSelf();
            }
            else {
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Service", "MENU");
                intent.putExtra("FROM", "MENU");
                startActivity(intent);
                stopSelf();
            }
        }

        @Override
        public void onError(int error) {
            String message;
            switch (error) {
                case SpeechRecognizer.ERROR_AUDIO:
                    message = "오디오 에러";
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    message = "클라이언트 에러";
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    message = "퍼미션 없음";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    message = "네트워크 에러";
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    message = "네트웍 타임아웃";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    message = "찾을 수 없음";
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    message = "RECOGNIZER가 바쁨";
                    return;
                case SpeechRecognizer.ERROR_SERVER:
                    message = "서버가 이상함";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    message = "말하는 시간초과";
                    break;
                default:
                    message = "알 수 없는 오류임";
                    break;
            }
            Log.d("listener message", String.valueOf(message));
            mRecognizer.startListening(sttIntent);
        }
    };

}