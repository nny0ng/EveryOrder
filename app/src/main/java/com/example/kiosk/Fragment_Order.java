package com.example.kiosk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Fragment_Order extends Fragment {
    private Adapter_recycler_order adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        RecyclerView rv_order = view.findViewById(R.id.rv_order);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rv_order.setLayoutManager(linearLayoutManager);

        adapter = new Adapter_recycler_order();
        rv_order.setAdapter(adapter);

        int sum = 0;
        ArrayList<ShoppingItem> orderList = Adapter_recycler_shopping.ShoppingList;
        for(int i = 0; i < orderList.size(); i++){
            adapter.addItem(orderList.get(i));
            int price = Integer.parseInt(orderList.get(i).getPrice().substring(0, orderList.get(i).getPrice().indexOf("원")));
            int num = Integer.parseInt(orderList.get(i).getNum().substring(0, orderList.get(i).getNum().indexOf("개")));

            sum += (price * num);
        }

        TextView sum_total = (TextView)view.findViewById(R.id.tv_total_price);
        sum_total.setText("총 금액: " + sum + "원");

        return view;
    }
}
