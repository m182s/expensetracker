package com.example.homepage;

import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Expenses {
    private Activity owner;
    public List<ItemInfo> itemInfoList;

    public MySharedPreferences myShared;

    // Constructor
    public Expenses() {
        itemInfoList = new ArrayList<>();
    }

    public Expenses(List<ItemInfo> items) {
        itemInfoList = items;
    }

    // Method to add an ItemInfo to the list
    public void addItemInfo(ItemInfo itemInfo) {
        itemInfoList.add(itemInfo);
    }

    // Method to remove an ItemInfo from the list
    public void removeItemInfo(ItemInfo itemInfo) {
        itemInfoList.remove(itemInfo);
    }

    // Method to search for an ItemInfo by ID
    public ItemInfo searchById(int id) {
        for (ItemInfo itemInfo : itemInfoList) {
            if (itemInfo.getId() == id) {
                return itemInfo;
            }
        }
        return null; // ID not found
    }
    // Method to search for ItemInfos by itemName
    public List<ItemInfo> searchByItemName(String itemName) {
        List<ItemInfo> resultList = new ArrayList<>();
        for (ItemInfo itemInfo : itemInfoList) {
            if (itemInfo.getItemName().equals(itemName)) {
                resultList.add(itemInfo);
            }
        }
        return resultList;
    }
    public List<ItemInfo> searchbyCategory(int category) {
        List<ItemInfo> resultList = new ArrayList<>();
        for (ItemInfo itemInfo : itemInfoList) {
            if (itemInfo.getCategory() == category) {
                resultList.add(itemInfo);
            }
        }
        return resultList;
    }

    public void deleteById(int itemId) {
        List<ItemInfo> resultList = new ArrayList<>();
        for (ItemInfo itemInfo : itemInfoList) {
            if (itemInfo.getId() == itemId) {
                resultList.add(itemInfo);
            }
        }
        itemInfoList.removeAll(resultList);
        CharSequence text = "Item(s) deleted! count:"+resultList.size();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(owner, text, duration);
        toast.show();
    }

    // Method to search for ItemInfos by remarks
    public List<ItemInfo> searchByRemarks(String remarks) {
        List<ItemInfo> resultList = new ArrayList<>();
        for (ItemInfo itemInfo : itemInfoList) {
            if (itemInfo.getRemarks().equals(remarks)) {
                resultList.add(itemInfo);
            }
        }
        return resultList;
    }
    public double calculateTotal() {
        double price = 0;
        for (ItemInfo priceList : itemInfoList) {
            price += priceList.getPrice();
        }
        return price;
    }
    public int getItemsCount()
    {
        return itemInfoList.size();
    }
    public void setOwner(Activity act)
    {
        owner=act;
    }
}
