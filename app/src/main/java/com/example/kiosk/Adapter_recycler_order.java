package com.example.kiosk;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_recycler_order extends RecyclerView.Adapter<Adapter_recycler_order.ViewHolder> {

    private ArrayList<ShoppingItem> OrderList = new ArrayList<>();

    @NonNull
    @Override
    public Adapter_recycler_order.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Item 을 하나하나 보여주는 함수
        holder.onBind(OrderList.get(position));
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수
        return OrderList.size();
    }

    void addItem(ShoppingItem item) {
        OrderList.add(item);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView price;
        TextView num;
        TextView total;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.tv_order_name);
            price = (TextView)itemView.findViewById(R.id.tv_order_price);
            num = (TextView)itemView.findViewById(R.id.tv_order_num);
            total = (TextView)itemView.findViewById(R.id.tv_order_total);
        }

        void onBind(ShoppingItem item) {
            name.setText(item.getName());
            price.setText(item.getPrice());
            num.setText(item.getNum());

            int price = Integer.parseInt(item.getPrice().substring(0, item.getPrice().indexOf("원")));
            int num = Integer.parseInt(item.getNum().substring(0, item.getNum().indexOf("개")));
            int total_price = price * num;
            total.setText(total_price + "원");
        }
    }
}
