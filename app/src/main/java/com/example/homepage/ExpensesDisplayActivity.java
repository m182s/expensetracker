package com.example.homepage;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ExpensesDisplayActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    TextView textChange;
    ArrayList<ItemInfo> itemsList;
    //ArrayAdapter<ItemInfo> ItemsAdapter;
    MyListAdapter ItemsAdapter;
    Spinner ExpenseSpinner;
    Expenses expenseTracker, expenses, totalPrice;
    int newPrice;
    MySharedPreferences myStorage;

    Button removeData;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // retrieve the JSON String from the Intent
//        setIntent(intent);
//        String jsonData = getIntent().getStringExtra("receiveKey");

        // increases the total price based on the user's input for the subsequent attempts
        //setTextChange();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //myStorage = new MySharedPreferences(getApplicationContext());
        expenses.itemInfoList.clear();
        expenses.itemInfoList.addAll((List<ItemInfo>) myStorage.getMyList());
        itemsList.clear();
        itemsList.addAll(expenses.itemInfoList);
        ItemsAdapter.setDataList(itemsList);
        ItemsAdapter.notifyDataSetChanged();
        Log.v("List", "NEW LIST: " + itemsList);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        // first iteration of the total price
        setTextChange();
        ArrayListCustomization();

        // back button
        findViewById(R.id.backbutton).setOnClickListener(v -> {
            Intent intent = new Intent(ExpensesDisplayActivity.this, ItemEntryActivity.class);
            startActivity(intent);
        });

        // adds the item and price into the log
        recyclerView = findViewById(R.id.budgetloglist);
        myStorage = new MySharedPreferences(getApplicationContext());
        //get List and create expense handler
        expenses = new Expenses((List<ItemInfo>) myStorage.getMyList());
        itemsList = new ArrayList<>();

        itemsList.addAll(expenses.itemInfoList);
        ItemsAdapter = new MyListAdapter(getApplicationContext(),itemsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(ItemsAdapter);

//        new MySharedPreferences().putString

        //delete button
        removeData = findViewById(R.id.deletelogs);
        removeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myStorage.clearMyList();
                itemsList.clear();
                expenses.itemInfoList.clear();
                ItemsAdapter.notifyDataSetChanged();
                Log.v("List", "NEW LIST: " + itemsList);
                CharSequence text = "Data cleared!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(ExpensesDisplayActivity.this, text, duration);
                toast.show();

            }
        });

//        AddedItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int listremove, long l){
//                new AlertDialog.Builder(ExpensesDisplayActivity.this)
//                        .setTitle("Are you sure you want to remove " + itemsList.get(listremove) + "?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog1, int which) {
//                                itemsList.remove(listremove);
//                                ItemsAdapter.notifyDataSetChanged();
//                                Log.v("item", "item removed");
//                            }
//                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog2, int which) {
//                                dialog2.dismiss();
//                            }
//                        }).create().show();
//
//                return false;
//            }
//        });


    }

    public void setTextChange(){
//        expenseTracker = new Expenses();
//        expenseTracker.addItemInfo();
//        double totalSpent = expenseTracker.calculateTotal();
//        Log.v("Price", "Total" + totalSpent);
    }

    public void ArrayListCustomization(){
        ExpenseSpinner = findViewById(R.id.spinnerid);
        ArrayList<String> category = new ArrayList<>();
        category.add("All");
        category.add("Travel");
        category.add("Food");
        category.add("Fashion");
        category.add("Others");

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, category);
        ExpenseSpinner.setAdapter(categoryAdapter);

        ExpenseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { //stores the category of the items selected
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItem = (String) parentView.getItemAtPosition(position);
                Log.v("itemsList", "Number: " + itemsList);
                List<ItemInfo> tmpList = expenses.searchbyCategory(position);
                Log.v("itemsList", "Search: " + tmpList);
                itemsList.clear();
                itemsList.addAll(tmpList);
                Log.v("List", "NEW LIST: " + tmpList.size());
                ItemsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
    }
}
