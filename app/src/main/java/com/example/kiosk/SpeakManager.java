package com.example.kiosk;

import android.app.Activity;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class SpeakManager {

    private static TextToSpeech speaker;

    public static void setup(Activity activity) {
        if(speaker == null) {
            speaker = new TextToSpeech(activity.getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS)
                        speaker.setLanguage(Locale.getDefault());
                }
            });
        }
    }

    public static void speak(String toBeSpoken, int mode) {
        speaker.speak(toBeSpoken, mode, null, "0000000");
    }

    public static void pause() {
        speaker.stop();
        speaker.shutdown();
    }
}