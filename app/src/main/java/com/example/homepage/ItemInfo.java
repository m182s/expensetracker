package com.example.homepage;

import java.time.LocalDateTime;

public class ItemInfo {
    private int id;
    private String itemName;
    private int price;
    private LocalDateTime dateTime;
    private String remarks;

    // Constructor
    public ItemInfo(int id, String itemName, int price, LocalDateTime dateTime, String remarks) {
        this.id = id;
        this.itemName = itemName;
        this.price = price;
        this.dateTime = dateTime;
        this.remarks = remarks;
    }

    // Getters and setters
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
