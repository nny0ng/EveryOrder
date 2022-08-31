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
import java.util.Arrays;
import java.util.Dictionary;
import java.util.List;
import java.util.Locale;

public class V_order extends Service {
    // listener: label 3
    // cartListener: 무엇을 추가/삭제 하시겠습니까?
    // checkListener: ~ n개를 장바구니에 추가할까요?
    // completeListener: (장바구니 전체)를 주문하시겠습니까?
    private ArrayList<ShoppingItem> shoppinglist;
    Intent sttIntent;
    SpeechRecognizer mRecognizer;

    public V_order() {
        shoppinglist = new ArrayList<>();
        // STT 설정
        sttIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        sttIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");

        mRecognizer = SpeechRecognizer.createSpeechRecognizer(V_order.this);
        mRecognizer.setRecognitionListener(listener);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // tts: label 3
        int i = 0;
        for(ShoppingItem item : Adapter_recycler_shopping.ShoppingList)
            shoppinglist.add(item);
        SpeakManager.speak("현재 장바구니에는 ", TextToSpeech.QUEUE_FLUSH);
        i += "현재 장바구니에는 ".length();
        if(shoppinglist.size()>=1) {
            for (ShoppingItem item : shoppinglist){
                SpeakManager.speak(item.getName() + item.getNum() + " ", TextToSpeech.QUEUE_ADD);
                i += (item.getName() + item.getNum() + " ").length();
            }
            SpeakManager.speak("가 있습니다.", TextToSpeech.QUEUE_ADD);
            i += "가 있습니다.".length();
        }
        else{
            SpeakManager.speak("아무것도 없습니다.", TextToSpeech.QUEUE_ADD);
            i += "아무것도 없습니다.".length();
        }
        SpeakManager.speak("추가를 원하시면 추가, 삭제를 원하시면 삭제, 주문 확정을 원하시면 확정 이라고 말해주세요", TextToSpeech.QUEUE_ADD);
        i += "추가를 원하시면 추가, 삭제를 원하시면 삭제, 주문 확정을 원하시면 확정 이라고 말해주세요".length();

        try {
            Thread.sleep(i*160); // 초 계산하기
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
            if (service.lastIndexOf("말해주세요") >= 0) {// 있다면
                cart(service.substring(service.indexOf("말해주세요")+5));
            } else {// 없다면
                cart(service);
            }
        }

        public void cart(String matches) {
            Intent intent;
            mRecognizer = SpeechRecognizer.createSpeechRecognizer(V_order.this);
            mRecognizer.setRecognitionListener(cartListener);

            if (matches.contains("추가")) {
                cartListener.tmp ="추가";
                checkListener.tmp ="추가";
                SpeakManager.speak("무엇을 추가하시겠습니까?", TextToSpeech.QUEUE_FLUSH);
                mRecognizer.startListening(sttIntent);
            } else if (matches.contains("삭제")) {
                cartListener.tmp ="삭제";
                checkListener.tmp ="삭제";
                SpeakManager.speak("무엇을 삭제하시겠습니까?", TextToSpeech.QUEUE_FLUSH);
                mRecognizer.startListening(sttIntent);
            } else if (matches.contains("확정")) {
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Service", "FINAL");
                startActivity(intent);

                mRecognizer = SpeechRecognizer.createSpeechRecognizer(V_order.this);
                mRecognizer.setRecognitionListener(completeListener);
                SpeakManager.speak("", TextToSpeech.QUEUE_FLUSH);
                if(shoppinglist.size()>=1) {
                    for (ShoppingItem item : shoppinglist)
                        SpeakManager.speak(item.getName() + item.getNum() + " ", TextToSpeech.QUEUE_ADD);
                }
                else
                    SpeakManager.speak("무", TextToSpeech.QUEUE_ADD);
                SpeakManager.speak("를 주문하시겠습니까?", TextToSpeech.QUEUE_ADD);
                mRecognizer.startListening(sttIntent);
            } else {
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Service", "MAIN");
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
            addToCart(matches.toString().replace(" ","")); // 단어 분류 함수
        }

        private void addToCart(String matches) {
            // 문장 인식하고 단어, 수량 파악해서
            ArrayList<List<String>> list = new ArrayList<>();
            list.add(new ArrayList<>(Fragment_tab1.listname));
            list.get(0).add("돈까스");
            list.get(0).add("치즈돈까스");
            list.get(0).add("로제돈까스");
            list.get(0).add("고구마치즈돈까스");
            list.add(Fragment_tab2.listname);
            list.add(Fragment_tab3.listname);
            list.add(Fragment_tab4.listname);
            
            ArrayList<List<String>> pricelist = new ArrayList<>();
            pricelist.add(new ArrayList<>(Fragment_tab1.listprice));
            pricelist.get(0).add("6900원");
            pricelist.get(0).add("7900원");
            pricelist.get(0).add("9900원");
            pricelist.get(0).add("8900원");
            pricelist.add(Fragment_tab2.listprice);
            pricelist.add(Fragment_tab3.listprice);
            pricelist.add(Fragment_tab4.listprice);

            List<String> foods;
            String food;
            ShoppingItem shoppingItem;
            String count;
            shoppinglist.clear();

            String num;
            //해당 데이터 액티비티로 옮겨서 장바구니에만 넣으면 됨 ㅋㅎㅎㅋㅋㅋㅋ
            //우선은 메뉴마다 한번씩만 말했을 떄 인식되도록
            for(int i=0; i<list.size(); i++){
                foods = list.get(i);
                for(int j=0; j<foods.size(); j++){
                    food = foods.get(j);
                    if(matches.contains(food)){
                        Log.d("CHECK", matches);
                        Log.d("CHECK", food);
                        if(matches.indexOf(food)+food.length()+2>=matches.length())
                            num = "";
                        else
                            num = matches.substring(matches.indexOf(food)+food.length(), matches.indexOf(food)+food.length()+2);

                        if(food.contains("돈까스"))
                            food = food.replace("까", "가");

                        switch(num){
                            case "하나":
                            case "한개":
                            case "1개":
                                count = "1개";
                                break;
                            case "두개":
                            case "2개":
                                count = "2개";
                                break;
                            case "세개":
                            case "3개":
                                count = "3개";
                                break;
                            case "네개":
                            case "4개":
                                count = "4개";
                                break;
                            default:
                                if(tmp.equals("추가"))
                                    count = "1개";
                                else{
                                    count = "10개";
                                    for (int k = 0; k < Adapter_recycler_shopping.ShoppingList.size(); k++) {
                                        ShoppingItem selected = Adapter_recycler_shopping.ShoppingList.get(k);
                                        if (selected.getName().equals(food))
                                            count = selected.getNum();
                                    }
                                }
                        }

                        shoppingItem = new ShoppingItem();
                        shoppingItem.setName(food);
                        shoppingItem.setPrice(pricelist.get(i).get(j));
                        shoppingItem.setNum(count);
                        
                        shoppinglist.add(shoppingItem);
                    }
                }
            }
            
            mRecognizer = SpeechRecognizer.createSpeechRecognizer(V_order.this);
            mRecognizer.setRecognitionListener(checkListener);
            SpeakManager.speak("", TextToSpeech.QUEUE_FLUSH);
            for(ShoppingItem item : shoppinglist)
                SpeakManager.speak(item.getName()+item.getNum()+" ", TextToSpeech.QUEUE_ADD);
            SpeakManager.speak("를 장바구니에 "+ tmp +"할까요?", TextToSpeech.QUEUE_ADD);
            mRecognizer.startListening(sttIntent);
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

    private Listener checkListener = new Listener() {
        @Override
        public void onResults(Bundle results) {
            Intent intent;
            ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            Log.d("STT", String.valueOf(matches));

            int before, after;
            if (matches.toString().contains("네") || matches.toString().contains("예")) { // 후보지 나중에 추가
                //우선은 추가만 되도록! : 삭제는 장바구니에 없는거 삭제하는 경우 고려해야해서..
                if(tmp.equals("추가")) {
                    for (ShoppingItem shoppingItem : shoppinglist) {
                        //장바구니 동기화
                        boolean check = false;
                        for (int k = 0; k < Adapter_recycler_shopping.ShoppingList.size(); k++) {
                            ShoppingItem selected = Adapter_recycler_shopping.ShoppingList.get(k);
                            if (selected.getName().equals(shoppingItem.getName())) {
                                check = true;
                                int num = Integer.parseInt(selected.num.substring(0, selected.num.indexOf("개")));
                                System.out.println(num);
                                num++;
                                selected.setNum(num + "개");
                                //System.out.println(selected.getNum());
                                Adapter_recycler_shopping.ShoppingList.set(k, selected);
                                //System.out.println(Adapter_recycler_shopping.ShoppingList.get(k).getNum());
                                Fragment_Menu.adapter.notifyDataSetChanged();
                                break;
                            }
                        }

                        if (check == false) {
                            Adapter_recycler_shopping.ShoppingList.add(shoppingItem);
                        }
                        Fragment_Menu.adapter.notifyDataSetChanged();
                    }
                }
                else if (tmp.equals("삭제")){
                    for (ShoppingItem shoppingItem : shoppinglist) {
                        //장바구니 동기화
                        boolean check = false;
                        for (int k = 0; k < Adapter_recycler_shopping.ShoppingList.size(); k++) {
                            ShoppingItem selected = Adapter_recycler_shopping.ShoppingList.get(k);
                            if (selected.getName().equals(shoppingItem.getName())) {
                                before = Integer.parseInt(selected.getNum().substring(0, selected.getNum().indexOf("개")));
                                after = Integer.parseInt(shoppingItem.getNum().substring(0, shoppingItem.getNum().indexOf("개")));
                                Log.d("CHECK", String.valueOf(before) + ' ' + String.valueOf(after));
                                //감소 조건

                                if (before - after > 0) {
                                    Log.d("CHECK", "decrease");
                                    ArrayList<ShoppingItem> ShoppingList = Adapter_recycler_shopping.ShoppingList;
                                    selected.setNum(before - after + "개");
                                    ShoppingList.set(k, selected);
                                    Fragment_Menu.adapter.notifyDataSetChanged();
                                }
                                //삭제 조건
                                else {
                                    Log.d("CHECK", "delete");

                                    ArrayList<ShoppingItem> ShoppingList = Adapter_recycler_shopping.ShoppingList;
                                    int size = ShoppingList.size();
                                    for (int i = k + 1; i < size; i++) {
                                        ShoppingList.set(i - 1, ShoppingList.get(i));
                                    }
                                    ShoppingList.remove(size - 1);
                                    Fragment_Menu.adapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }
                }
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
            else
                mRecognizer.startListening(sttIntent);
            // STT data to TTS
            //tts.speak(matches.toString(), TextToSpeech.QUEUE_FLUSH, null);
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

    private Listener completeListener = new Listener() {
        @Override
        public void onResults(Bundle results) {
            Intent intent;
            ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            Log.d("STT", String.valueOf(matches));

            if (matches.toString().contains("네")) { // 후보지 나중에 추가
                SpeakManager.speak("주문이 완료되었습니다 조금만 기다려주세요!", TextToSpeech.QUEUE_FLUSH);
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Service","COMPLETE");
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
                SpeakManager.speak("인식에 실패했습니다", TextToSpeech.QUEUE_FLUSH);
                mRecognizer.startListening(sttIntent);
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