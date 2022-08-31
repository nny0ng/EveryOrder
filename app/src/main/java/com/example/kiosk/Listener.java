package com.example.kiosk;

import android.content.Intent;
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

}
