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

public class Fragment_tab3 extends Fragment {

    public static List<String> listname = Arrays.asList("크림리조또", "로제리조또", "계란불고기볶음밥", "매콤치즈볶음밥");
    public static List<String> listprice = Arrays.asList("8900원", "8900원", "8900원", "9900원");
    private Adapter_recycler_menu adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab3, container, false);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.rv_tab3);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new Adapter_recycler_menu();
        recyclerView.setAdapter(adapter);

        getData();
        return view;
    }

    // 리조또
    private void getData() {
        List<Integer> listResId = Arrays.asList(
                R.drawable.creambap,
                R.drawable.rosebap,
                R.drawable.steakeggbap,
                R.drawable.spicycheesebap
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
