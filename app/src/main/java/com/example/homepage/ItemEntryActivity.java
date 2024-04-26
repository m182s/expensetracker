package com.example.homepage;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
        - recyclerView (DONE)
        - back button (DONE)
        - Add more than one input (DONE)
        - Try again after failed attempt (DONE)
        - rewrite code into functions for simplicity (DONE)
        - sharedPreferences (DONE)
        - pick a category (DONE)
        - Dropdown for the different categories (DONE)
        - Filter through category (DONE)
        - Show all items when in allCategory (DONE)
        - List estitik (DONE)
        - totalPrice (DONE)
        - Delete and deleteall button (DONE)
        - Change price when itemDeleted (DONE)
        - Set budget
        - change textColor when budget exceeds
        - Adjust code based on Joji's commit

         */

public class ItemEntryActivity extends AppCompatActivity {

    private EditText item, price;
    private ImageButton log, home;
    private ArrayList<Map<String, String>> itemsList;
    int sumPesos;
    boolean _isItemTrue = false;
    boolean _ispriceTrue = false;

    String _itemPrice;
    String _itemName;

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

        item.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == 13))) {
                    _itemName = item.getText().toString();
                    Log.v("Name", "NAME: " + _itemName);
                    if (_itemName.isEmpty()) {
                        CharSequence text = "Enter the name of what you want to add to your budget!";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(ItemEntryActivity.this, text, duration);
                        toast.show();
                    }else {
                        _isItemTrue = true;
                        Log.v("ITEM", "Item State: " + _isItemTrue);
                        checkBothEnterPressed();
                        return true;
                    }

                }
                return false;
            }
        });
        price.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == 13))) {
                    _itemPrice = price.getText().toString();
                    Log.v("Price", "PRICE: " + _itemName);
                    if (_itemPrice.isEmpty()) {
                        CharSequence text = "Enter a price!";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(ItemEntryActivity.this, text, duration);
                        toast.show();
                    } else {
                        sumPesos = Integer.parseInt(_itemPrice);
                        Map<String, String> item = new HashMap<>();

                        if (sumPesos > 100000) { // Try again if not within the number range
                            CharSequence text = "This program only accepts until Php100k";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(ItemEntryActivity.this, text, duration);
                            toast.show();
                        } else if (sumPesos <= 0) {
                            CharSequence text = "Enter a valid price!";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(ItemEntryActivity.this, text, duration);
                            toast.show();
                        } else {
                            _ispriceTrue = true;
                            Log.v("ITEM", "Item State: " + _ispriceTrue);
                            checkBothEnterPressed();
                            Log.v("ITEM", "itempressreached");
                            item.put(_itemName, String.valueOf(sumPesos));
                            itemsList.add(item);
                            return true;
                        }
                    }
                }
                return false;
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
            public void onClick(View v) {
                findViewById(R.id.homeId).setOnClickListener(view -> {
                    Intent intent = new Intent(ItemEntryActivity.this, MainActivity.class);
                    startActivity(intent);
                });
            }
        });
    }
    public void storeIdVar(){
        item = findViewById(R.id.itemId);
        price = findViewById(R.id.priceId);
        log = findViewById(R.id.logId);
        home = findViewById(R.id.homeId);
    }
    public void checkBothEnterPressed() {
        if (_isItemTrue && _ispriceTrue) {
            // Adds data
            CharSequence textFinal = "Item added!";
            int durationFinal = Toast.LENGTH_SHORT;
            Toast toastFinal = Toast.makeText(ItemEntryActivity.this, textFinal, durationFinal);
            toastFinal.show();
            MySharedPreferences myStorage = new MySharedPreferences(getApplicationContext());
            Expenses expenses = new Expenses(myStorage.getMyList());
            Log.v("Spinner", "Input: " + ExpenseSpinner.getSelectedItemPosition());
            int lastID = myStorage.getLastId();
            ItemInfo itemInfo = new ItemInfo(lastID, _itemName, sumPesos, LocalDateTime.now().toString(), "Sample", ExpenseSpinner.getSelectedItemPosition() + 1);
            expenses.addItemInfo(itemInfo);
            myStorage.saveMyList(expenses.itemInfoList);
            myStorage.saveLastId(lastID + 1);
            Intent intent = new Intent(ItemEntryActivity.this, ExpensesDisplayActivity.class);
            Log.v("ITEM", "Item State: " + _isItemTrue);
            Log.v("ITEM", "Item State: " + _ispriceTrue);
            _ispriceTrue = false;
            _isItemTrue = false;
        }
    }
}


