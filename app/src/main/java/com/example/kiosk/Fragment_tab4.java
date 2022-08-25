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

public class Fragment_tab4 extends Fragment {

    public static List<String> listname = Arrays.asList("사이다", "콜라", "자몽에이드");
    private Adapter_recycler_menu adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab4, container, false);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.rv_tab4);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new Adapter_recycler_menu();
        recyclerView.setAdapter(adapter);

        getData();
        return view;
    }

    // 음료
    private void getData() {
        List<String> listprice = Arrays.asList("2000원", "2000원", "4000원");
        List<Integer> listResId = Arrays.asList(
                R.drawable.cider,
                R.drawable.cola,
                R.drawable.grapefruitade
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
