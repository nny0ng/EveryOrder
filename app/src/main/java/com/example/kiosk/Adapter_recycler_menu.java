package com.example.kiosk;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_recycler_menu extends RecyclerView.Adapter<Adapter_recycler_menu.ViewHolder> {

    private ArrayList<MenuItem> Menulist;

    @NonNull
    @Override
    public Adapter_recycler_menu.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Item 을 하나하나 보여주는 함수
        holder.onBind(Menulist.get(position));
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수
        return Menulist.size();
    }

    void addItem(MenuItem item) {
        Menulist.add(item);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView food;
        TextView name;
        TextView price;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            food = (ImageView)itemView.findViewById(R.id.iv_menu_food);
            name = (TextView) itemView.findViewById(R.id.tv_menu_name);
            price = (TextView) itemView.findViewById(R.id.tv_menu_price);
        }

        void onBind(MenuItem item) {
            food.setImageResource(item.getResourceID());
            name.setText(item.getName());
            price.setText(item.getPrice());
        }
    }
}
