package com.example.kiosk;

import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;

public abstract class Listener implements RecognitionListener {

    public static String tmp = "";

    public Listener(){
        tmp = "";
    }

    @Override
    public void onPartialResults(Bundle bundle) {
        // 부분 인식 결과를 사용할 수 있을 때 호출출
    }

    @Override
    public void onEvent(int i, Bundle bundle) {
        // 향후 이벤트를 추가하기 위해 예약
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        // 말하기 시작할 준비가 되면 호출
    }

    @Override
    public void onBeginningOfSpeech() {
        // 말하기 시작했을 때 호출
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        // 입력받는 소리의 크기를 알려줌
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        // 말을 시작하고 인식이 된 단어를 buffer에 담음
    }

    @Override
    public void onEndOfSpeech() {
        // 말하기를 중지하면 호출
    }

    @Override
    public void onError(int error) {
        // 네트워크 또는 인식 오류가 발생했을 때 호출
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
                break;
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
    }
}
