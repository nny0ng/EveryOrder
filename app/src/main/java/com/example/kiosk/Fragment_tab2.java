package com.example.kiosk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class Fragment_tab2 extends Fragment {

    public static List<String> listname = Arrays.asList("로제파스타", "알리오올리오", "대구파스타", "스테이크로제파스타");
    public static List<String> listprice = Arrays.asList("9900원", "7900원", "12900원", "12900원");
    private Adapter_recycler_menu adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.rv_tab2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new Adapter_recycler_menu();
        recyclerView.setAdapter(adapter);

        getData();
        return view;
    }

    // 파스타
    private void getData() {

        List<Integer> listResId = Arrays.asList(
                R.drawable.rosepasta,
                R.drawable.alliopasta,
                R.drawable.daegupasta,
                R.drawable.steakrosepasta
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