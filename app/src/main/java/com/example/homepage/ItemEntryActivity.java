package com.example.homepage;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*TODO
        - Listview (DONE)
        - Mapping (DONE)
        - back button (DONE)
        - Add more than one input (DONE)
        - Try again after failed attempt (DONE)
        - rewrite code into functions for simplicity (DONE)
        - Dropdown for the different categories (DONE)
        - Filter through category (DONE)
        - Total price (DONE)
        - List estitik (DONE)
        - Delete logs
        - all categories (DONE)

         */

public class ItemEntryActivity extends AppCompatActivity {

    private TextView item, price;
    private Button add, log, home;
    private ArrayList<Map<String, String>> itemsList;
    int sumPesos;
    MyListAdapter ItemsAdapter;
    Spinner ExpenseSpinner;

    MySharedPreferences myStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adder);
        storeIdVar();
        itemsList = new ArrayList<Map<String, String>>();
        ExpenseSpinner = findViewById(R.id.selectcategory);

        ArrayList<String> category = new ArrayList<>();
        category.add("Travel");
        category.add("Food");
        category.add("Fashion");
        category.add("Others");

        ExpenseSpinner.setSelection(0);

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, category);
        ExpenseSpinner.setAdapter(categoryAdapter);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String itemName = item.getText().toString();
                String itemPrice = price.getText().toString();

                if (itemPrice.isEmpty()) {
                    CharSequence text = "Enter a price!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(ItemEntryActivity.this, text, duration);
                    toast.show();
                }
                else {
                    sumPesos = Integer.parseInt(itemPrice);

                    Map<String, String> item = new HashMap<>();

                    if (sumPesos > 100000) { // Try again if not within the number range
                        CharSequence text = "This program only accepts until Php100k";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(ItemEntryActivity.this, text, duration);
                        toast.show();
                    } else if (sumPesos <= 0) {
                        CharSequence text = "Enter a price!";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(ItemEntryActivity.this, text, duration);
                        toast.show();

                    } else if (itemName.isEmpty()) {
                        CharSequence text = "Enter the name of what you want to add to your budget!";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(ItemEntryActivity.this, text, duration);
                        toast.show();
                    } else {
                        item.put(itemName, String.valueOf(sumPesos));
                        itemsList.add(item);
                        CharSequence text = "Item added!";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(ItemEntryActivity.this, text, duration);
                        toast.show();
                        // Adds data
                        MySharedPreferences myStorage = new MySharedPreferences(getApplicationContext());
                        Expenses expenses = new Expenses(myStorage.getMyList());
                        Log.v("Spinner", "Input: " + ExpenseSpinner.getSelectedItemPosition());
                        int lastID = myStorage.getLastId();
                        ItemInfo itemInfo = new ItemInfo(lastID, itemName, sumPesos, LocalDateTime.now().toString(), "Sample", ExpenseSpinner.getSelectedItemPosition() + 1);
                        expenses.addItemInfo(itemInfo);
                        Log.v("HiHi","now: "+ LocalDateTime.now()+" Sample: " + expenses.searchById(1).toString());
                        myStorage.saveMyList(expenses.itemInfoList);
                        myStorage.saveLastId(lastID + 1);
                        Intent intent = new Intent(ItemEntryActivity.this, ExpensesDisplayActivity.class);
                    }
                }
            }
        });
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.logId).setOnClickListener(v -> {
                    Intent intent = new Intent(ItemEntryActivity.this, ExpensesDisplayActivity.class);
                    startActivity(intent);
                });
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.homeId).setOnClickListener(v -> {
                    Intent intent = new Intent(ItemEntryActivity.this, MainActivity.class);
                    startActivity(intent);
                });
            }
        });
    }
    public void storeIdVar(){
        item = findViewById(R.id.itemId);
        price = findViewById(R.id.priceId);
        add = findViewById(R.id.addId);
        log = findViewById(R.id.logId);
        home = findViewById(R.id.homeId);
    }
}
