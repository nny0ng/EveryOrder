package com.example.kiosk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PopupCall extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_call);

        Button btn1 = (Button) findViewById(R.id.btn_callstaff);
        Button btn2 = (Button) findViewById(R.id.btn_waterpaper);
        Button btn3 = (Button) findViewById(R.id.btn_water);
        Button btn4 = (Button) findViewById(R.id.btn_dish);
        Button btn5 = (Button) findViewById(R.id.btn_back);

        btn1.setOnClickListener(this::onClick);
        btn2.setOnClickListener(this::onClick);
        btn3.setOnClickListener(this::onClick);
        btn4.setOnClickListener(this::onClick);
        btn5.setOnClickListener(this::onClick);
    }

    // 호출 -> 서비스 선택시 팝업
    @Override
    public void onClick(View view) {

        Intent intent = new Intent(getApplicationContext(), PopupFinishcall.class);

        switch (view.getId()){
            case R.id.btn_callstaff: // 직원 호출
                startActivity(intent);
                finish();
                break;
            case R.id.btn_waterpaper: // 물티슈
                startActivity(intent);
                finish();
                break;
            case R.id.btn_water: // 물
                startActivity(intent);
                finish();
                break;
            case R.id.btn_dish: // 앞접시
                startActivity(intent);
                finish();
                break;
            case R.id.btn_back: // 뒤로가기
                finish();
                break;
        }
    }
}