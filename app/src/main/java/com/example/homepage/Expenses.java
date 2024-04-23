package com.example.homepage;

import java.util.ArrayList;
import java.util.List;

public class Expenses {
    public List<ItemInfo> itemInfoList;

    // Constructor
    public Expenses() {
        itemInfoList = new ArrayList<>();
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
}
