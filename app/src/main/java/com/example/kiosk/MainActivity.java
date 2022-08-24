package com.example.kiosk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.view.menu.MenuAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private Fragment_Menu fragment_menu;
    private Fragment_Order fragment_order;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        fragment_menu = new Fragment_Menu();
        fragment_order = new Fragment_Order();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layout_frame, fragment_menu).commitAllowingStateLoss();

        // 호출 버튼 클릭시 화면전환
        Button button_call = (Button) findViewById(R.id.btn_call);
        button_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PopupCall.class);
                startActivity(intent);
            }
        });


    }

    public void clickHandler(View view)
    {
        fragmentTransaction = fragmentManager.beginTransaction();

        switch(view.getId())
        {
            case R.id.btn_go_order:
                fragmentTransaction.replace(R.id.layout_frame, fragment_order).commitAllowingStateLoss();
                break;

            case R.id.btn_back:
                fragmentTransaction.replace(R.id.layout_frame, fragment_menu).commitAllowingStateLoss();
                break;

            case R.id.btn_submit_order:
                Intent intent = new Intent(getApplicationContext(), PopupFinish.class);
                startActivity(intent);
                break;
        }
    }



}