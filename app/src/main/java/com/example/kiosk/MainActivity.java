package com.example.kiosk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Adapter_recycler_menu adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        getData();

    }

    private void init() {
        RecyclerView recyclerView =  findViewById(R.id.rv_menu);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new Adapter_recycler_menu();
        recyclerView.setAdapter(adapter);
    }

    private void getData() {
        List<String> listname = Arrays.asList("돈가스", "치즈돈가스", "로제돈가스", "고구마치즈돈가스");
        List<String> listprice = Arrays.asList("6,900", "7,900", "9,900", "8,900");
        List<Integer> listResId = Arrays.asList(
                R.drawable.pork,
                R.drawable.cheesepork,
                R.drawable.rosepork,
                R.drawable.gochipork
        );

        for (int i = 0; i < listname.size(); i++) {
            // 각 List의 값들을 data 객체에 set
            MenuItem item = new MenuItem();
            item.setResourceID(listResId.get(i));
            item.setName(listname.get(i));
            item.setPrice(listprice.get(i));

            // 각 값이 들어간 data를 adapter에 추가
            adapter.addItem(item);
        }

        // adapter의 값이 변경되었다는 것을 알려줌
        adapter.notifyDataSetChanged();

    }
}