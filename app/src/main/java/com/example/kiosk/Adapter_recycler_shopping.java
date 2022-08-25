package com.example.kiosk;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_recycler_shopping extends RecyclerView.Adapter<Adapter_recycler_shopping.ViewHolder> {

    public static ArrayList<ShoppingItem> ShoppingList = new ArrayList<>();

    interface OnItemClickListener{
        void onMinusClick(View view, int position); // 마이너스 버튼
        void onPlusClick(View view, int position); // 마이너스 버튼
        void onCancelClick(View view, int position); // x 버튼
    }

    private OnItemClickListener mListener = null;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

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
        Button btn_minus;
        Button btn_plus;
        Button btn_cancel;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.tv_shopping_name);
            price = (TextView)itemView.findViewById(R.id.tv_shopping_price);
            num = (TextView)itemView.findViewById(R.id.tv_shopping_num);

            btn_minus = (Button)itemView.findViewById(R.id.btn_minus);
            btn_plus = (Button)itemView.findViewById(R.id.btn_plus);
            btn_cancel = (Button)itemView.findViewById(R.id.btn_cancel);

            btn_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(mListener != null){
                            mListener.onMinusClick(view, pos);
                        }
                    }
                }
            });

            btn_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(mListener != null){
                            mListener.onPlusClick(view, pos);
                        }
                    }
                }
            });

            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(mListener != null){
                            mListener.onCancelClick(view, pos);
                        }
                    }
                }
            });

        }

        void onBind(ShoppingItem item) {
            name.setText(item.getName());
            price.setText(item.getPrice());
            num.setText(item.getNum());
        }
    }
}
