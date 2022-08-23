package com.example.kiosk;

import android.widget.ImageView;

public class MenuItem {
    int resourceID;
    String name;
    String price;

    public MenuItem() {
        this.resourceID = resourceID;
        this.name = name;
        this.price = price;
    }

    public int getResourceID() {
        return resourceID;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
