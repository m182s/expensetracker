package com.example.homepage;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MySharedPreferences {

    private static final String PREF_NAME = "MyAppPreferences";
    private static final String KEY_MY_LIST = "myList";

    private static final String KEY_LAST_ID = "lastID";
    private static final String KEY_MAIN_CT = "MainCT";

    private static final String KEY_BUDGET = "getBudget";

    private final SharedPreferences sharedPreferences;
    SharedPreferences.Editor editPref;
    private Gson gson;

    public MySharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editPref = sharedPreferences.edit();
        gson = new Gson();
    }

    // Method to save a List<MyObject> to SharedPreferences
    public void saveMyList(List<ItemInfo> myList) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(myList);
        editor.putString(KEY_MY_LIST, json);
        editor.apply();
    }

    public void clearMyList() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public void saveLastId(int lastID) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_LAST_ID, lastID);
        editor.apply();
    }

    public void saveMainCt(int MainID) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_MAIN_CT, MainID);
        editor.apply();
    }

    public int getMainCategory() {
        int MainCategory;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        MainCategory = sharedPreferences.getInt(KEY_MAIN_CT, 1);
        editor.apply();
        return MainCategory;
    }

    public int getLastId() {
        int LastID;
        //SharedPreferences.Editor editor = sharedPreferences.edit();
        LastID = sharedPreferences.getInt(KEY_LAST_ID, 1);
        //editor.apply();

        return LastID;
    }

    public List<ItemInfo> removeDataFromSharedPreferences(ItemInfo iteminfo) {
        Expenses expenses = new Expenses(getMyList());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ItemInfo temp = expenses.searchById(iteminfo.getId());
        expenses.removeItemInfo(temp);
        saveMyList(expenses.itemInfoList);
        return expenses.itemInfoList;
    }

    // Method to retrieve a List<MyObject> from SharedPreferences
    public List<ItemInfo> getMyList() {
        String json = sharedPreferences.getString(KEY_MY_LIST, null);
        Log.v("WOrked", "Task: " + json);
        if (json != null && !json.isEmpty()) {
            Log.v("WOrked", "worked 2: " + json);
            Type type = new TypeToken<List<ItemInfo>>() {
            }.getType();
            return gson.fromJson(json, type);
        }
        return new ArrayList<>(); // Return empty list if no data found
    }

    public void saveMyBudget(int saveBudget){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_BUDGET, saveBudget);
        editor.apply();
        Log.v("Save", "SAVED" +String.valueOf(saveBudget));
    }

    public int getMyBudget(){
        int getBudget = 0;
        //SharedPreferences.Editor editor = sharedPreferences.edit();
        getBudget = sharedPreferences.getInt(KEY_BUDGET, getBudget);
        //editor.apply();
        Log.v("Save", "GET" +String.valueOf(getBudget));
        return getBudget;

    }
}