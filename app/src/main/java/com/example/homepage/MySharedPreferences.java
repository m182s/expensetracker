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

    private SharedPreferences sharedPreferences;
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
}