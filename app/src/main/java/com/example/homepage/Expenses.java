package com.example.homepage;

import java.util.ArrayList;
import java.util.List;

public class Expenses {
    public List<ItemInfo> itemInfoList;
    public int totalPrice;

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

//    public void addItem(String itemName, double itemPrice) {
//        ExpenseItem newItem = new ExpenseItem(itemName, itemPrice);
//        expenseList.add(newItem);
//    }
    public double calculateTotal() {
        double total = 0;
        for (ItemInfo item : itemInfoList) {
            total += item.getPrice();
        }
        return total;
    }


//    public int calculatePrice(int newPrice) {
//        for (ItemInfo itemInfo : itemInfoList) {
//            newPrice = totalPrice += itemInfo.getPrice();
//            }
//        return newPrice;
//    }

    public int getItemsCount()
    {
        return itemInfoList.size();
    }
}
