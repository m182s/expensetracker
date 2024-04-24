package com.example.homepage;

import com.google.gson.Gson;

import java.time.LocalDateTime;

public class ItemInfo {
    private int id;
    private String itemName;
    private int price;
    private String dateTime;
    private String remarks;
    private int category;

    // Constructor
    public ItemInfo(int id, String itemName, int price, String dateTime, String remarks, int myCategory) {
        this.id = id;
        this.itemName = itemName;
        this.price = price;
        this.dateTime = dateTime;
        this.remarks = remarks;
        this.category = myCategory;
    }


    // Getters and setters

    public int getCategory() {
        return category;
    }

    public void setCategory(int myCategory) {
        this.category = myCategory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString()
    {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
