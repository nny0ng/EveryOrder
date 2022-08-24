package com.example.kiosk;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_recycler_shopping extends RecyclerView.Adapter<Adapter_recycler_shopping.ViewHolder> {

    public static ArrayList<ShoppingItem> ShoppingList = new ArrayList<>();

    @NonNull
    @Override
    public Adapter_recycler_shopping.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Item 을 하나하나 보여주는 함수
        holder.onBind(ShoppingList.get(position));
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수
        return ShoppingList.size();
    }

    void addItem(ShoppingItem item) {
        ShoppingList.add(item);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView price;
        TextView num;
        RecyclerView shopping_list;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.tv_shopping_name);
            price = (TextView)itemView.findViewById(R.id.tv_shopping_price);
            num = (TextView)itemView.findViewById(R.id.tv_shopping_num);
        }

        void onBind(ShoppingItem item) {
            name.setText(item.getName());
            price.setText(item.getPrice());
            num.setText("1개");
        }
    }
}
