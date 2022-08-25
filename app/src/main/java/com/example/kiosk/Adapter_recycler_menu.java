package com.example.kiosk;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Adapter_recycler_menu extends RecyclerView.Adapter<Adapter_recycler_menu.ViewHolder> {

    private ArrayList<MenuItem> Menulist = new ArrayList<>();

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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        ShoppingItem shoppingItem = new ShoppingItem();
                        MenuItem menuItem = Menulist.get(pos);
                        shoppingItem.setName(menuItem.getName());
                        shoppingItem.setPrice(menuItem.getPrice());
                        shoppingItem.setNum("1개");

                        boolean check = false;
                        for(int i = 0; i < Adapter_recycler_shopping.ShoppingList.size(); i++){
                            ShoppingItem selected = Adapter_recycler_shopping.ShoppingList.get(i);
                            if(selected.getName().equals(shoppingItem.getName())){
                                check = true;
                                int num = Integer.parseInt(selected.num.substring(0, selected.num.indexOf("개")));
                                System.out.println(num);
                                num++;
                                selected.setNum(num + "개");
                                //System.out.println(selected.getNum());
                                Adapter_recycler_shopping.ShoppingList.set(i, selected);
                                System.out.println(Adapter_recycler_shopping.ShoppingList.get(i).getNum());
                                Fragment_Menu.adapter.notifyDataSetChanged();
                                break;
                            }
                        }

                        if(check == false){
                            Adapter_recycler_shopping.ShoppingList.add(shoppingItem);
                        }
                        Fragment_Menu.adapter.notifyDataSetChanged();
                    }
                }
            });
        }

        void onBind(MenuItem item) {
            food.setImageResource(item.getResourceID());
            name.setText(item.getName());
            price.setText(item.getPrice());
        }
    }
}
