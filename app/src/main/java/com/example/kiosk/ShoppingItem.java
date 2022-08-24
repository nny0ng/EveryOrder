package com.example.kiosk;

public class ShoppingItem {
    String name;
    String price;
    String num;

    public ShoppingItem() {
        this.name = name;
        this.price = price;
        this.num = num;
    }

    public String getNum() {
        return num;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
