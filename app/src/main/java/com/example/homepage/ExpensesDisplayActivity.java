package com.example.homepage;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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

public class ExpensesDisplayActivity extends AppCompatActivity implements DeleteListener{


    RecyclerView recyclerView;
    TextView textChange;
    ArrayList<ItemInfo> itemsList;

    MyListAdapter ItemsAdapter;
    Spinner ExpenseSpinner;
    Expenses expenses;

    int _targetBudget;
    MySharedPreferences myStorage;
    Button removeData;

    private boolean isActive;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        expenses.itemInfoList.clear();
        expenses.itemInfoList.addAll((List<ItemInfo>) myStorage.getMyList());
        itemsList.clear();
        itemsList.addAll(expenses.itemInfoList);
        ItemsAdapter.setDataList(itemsList);
        ItemsAdapter.notifyDataSetChanged();
        Log.v("List", "NEW LIST: " + itemsList);
        reloadData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        // total price
        ArrayListCustomization();

        // back button
        findViewById(R.id.backbutton).setOnClickListener(v -> {
            Intent intent = new Intent(ExpensesDisplayActivity.this, ItemEntryActivity.class);
            startActivity(intent);
        });

        // adds the item and price into the log
        recyclerView = findViewById(R.id.budgetloglist);
        myStorage = new MySharedPreferences(getApplicationContext());

        itemsList = new ArrayList<>();
        //get List and create expense handler
        reloadFromStorage();
        ItemsAdapter = new MyListAdapter(getApplicationContext(),itemsList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(ItemsAdapter);

        Button removeAllButton  = findViewById(R.id.deleteall);
        removeAllButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // 1. Instantiate an AlertDialog.Builder with its constructor.
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                // 2. Chain together various setter methods to set the dialog characteristics.
                builder.setMessage("Are you sure you want to clear all record?")

                        .setTitle("Delete All Record")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                myStorage.clearMyList();
                                reloadFromStorage();
                                refreshDisplay();
                                calculateTotalPrice();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancels the dialog.
                            }
                        })
                ;

                // 3. Get the AlertDialog.
                AlertDialog dialog = builder.create();
                dialog.show();
                Button nbutton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                nbutton.setTextColor(Color.BLACK);
                Button pbutton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                pbutton.setTextColor(Color.BLACK);

            }
        });
        //delete button
        removeData = findViewById(R.id.showdeleteiconbutton);
        removeData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipSwitch();
                processClick();
            }
        });
    }

    public void flipSwitch (){
        isActive = !isActive;
    }
    public void processClick(){
        if(isActive){
            ItemsAdapter.showDeleteIcon();
            refreshDisplay();
            Log.v("Icon", "Icon added");
        }
        else{
            ItemsAdapter.removeDeleteIcon();
            refreshDisplay();
            Log.v("Icon", "Icon removed");
        }

    }

    public void calculateTotalPrice() {
        String changeText = "Total Spent: Php " + Double.toString(calculateCategory());
        textChange = findViewById(R.id.totalamount);
        textChange.setText(changeText);
    }
    public double calculateCategory() {
        double price = 0;
        for (ItemInfo priceList : itemsList) {
            price += priceList.getPrice();
        }
        return price;
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
                List<ItemInfo> tmpList;

                if(position == 0){
                    tmpList = expenses.itemInfoList;
                }
                else {
                    tmpList = expenses.searchbyCategory(position);
                }
                Log.v("itemsList", "Search: " + tmpList);
                itemsList.clear();
                itemsList.addAll(tmpList);
                Log.v("List", "NEW LIST: " + tmpList.size());
                calculateTotalPrice();
                ItemsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
    }

    @Override
    public void onDelete(int position)
    {
        ItemInfo delItemInfo= itemsList.get(position);
        itemsList.remove(position);
        expenses.itemInfoList = myStorage.removeDataFromSharedPreferences(delItemInfo);
        calculateTotalPrice();
        refreshDisplay();
    }

    public void refreshDisplay()
    {
        reloadData();
        recyclerView.setAdapter(null);
        recyclerView.getRecycledViewPool().clear();
        recyclerView.setAdapter(ItemsAdapter);
        ItemsAdapter.notifyDataSetChanged();
    }

    public void reloadFromStorage()
    {
        expenses = new Expenses((List<ItemInfo>) myStorage.getMyList());
        itemsList.clear();
        itemsList.addAll(expenses.itemInfoList);
    }

    public void reloadData(){
        double currentTotal = 0;
        textChange = findViewById(R.id.totalamount);
        expenses = new Expenses(myStorage.getMyList());
        currentTotal = expenses.calculateTotal();
        textChange.setText("Today's Total: PHP " + String.valueOf(currentTotal));
        _targetBudget = myStorage.getMyBudget();

        if(currentTotal > _targetBudget){
            textChange.setTextColor(Color.RED);
        }
        else{
            textChange.setTextColor(Color.WHITE);
        }
    }
}
