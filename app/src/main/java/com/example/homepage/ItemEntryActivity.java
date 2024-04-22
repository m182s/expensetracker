package com.example.homepage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*TODO
        - Listview (DONE)
        - Mapping (DONE)
        - back button (DONE)
        - Add more than one input (DONE)
        - Total price (DONE)
        - Try again after failed attempt (DONE)
        - Delete logs (HALF DONE)
        - rewrite code into functions for simplicity (DONE)
        - Dropdown for the different categories
        - Smart fill feature
        - Database
            - Food, Item, Travel, Others Categories
            - Log Database
        - The previous day Log
        - Daily, Weekly, Monthly Budget
         */

public class ItemEntryActivity extends AppCompatActivity {

    private TextView item, price;
    private Button add, log;
    private ArrayList<Map<String, String>> itemsList;
    int sumPesos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adder);
        storeIdVar();

        itemsList = new ArrayList<Map<String, String>>();

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
                        String jsonData = new Gson().toJson(itemsList);
                        Intent intent = new Intent(ItemEntryActivity.this, ExpensesDisplayActivity.class);
                        intent.putExtra("receiveKey", jsonData);
                        intent.putExtra("itemPrice", sumPesos);
//                      startActivity(intent);
                    }
                }
            }
        });
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.logId).setOnClickListener(v -> {
                    String jsonData = new Gson().toJson(itemsList);
                    Intent intent = new Intent(ItemEntryActivity.this, ExpensesDisplayActivity.class);
                    intent.putExtra("receiveKey", jsonData);
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
    }
}
