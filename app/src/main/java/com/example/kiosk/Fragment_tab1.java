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

public class Fragment_tab1 extends Fragment {

    private Adapter_recycler_menu adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.rv_tab1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new Adapter_recycler_menu();
        recyclerView.setAdapter(adapter);

        getData();
        return view;
    }

    // 돈가스
    private void getData() {
        List<String> listname = Arrays.asList("돈가스", "치즈돈가스", "로제돈가스", "고구마치즈돈가스");
        List<String> listprice = Arrays.asList("6,900원", "7,900원", "9,900원", "8,900원");
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
