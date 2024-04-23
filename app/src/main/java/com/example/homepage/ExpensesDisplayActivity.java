package com.example.homepage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.Text;
import java.util.ArrayList;
import java.util.Map;

public class ExpensesDisplayActivity extends AppCompatActivity {
    ListView AddedItems;
    TextView textChange;
    List<ItemInfo> itemsList;
    ArrayAdapter<ItemInfo> ItemsAdapter;

    Expenses sharedData;
    int total = 0;
    int sumPesos;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // retrieve the JSON String from the Intent
        setIntent(intent);
        String jsonData = getIntent().getStringExtra("receiveKey");

        // increases the total price based on the user's input for the subsequent attempts
        setTextChange();

        // converts the JSON String back to ArrayList<Map<String, String>>
        ArrayList<Map<String, String>> tempitemsList;
        tempitemsList = new Gson().fromJson(jsonData, new TypeToken<ArrayList<HashMap<String, String>>>(){}.getType());
        itemsList.clear();
        itemsList.addAll(sharedData.itemInfoList);
        ItemsAdapter.notifyDataSetChanged();

        AddedItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int listremove, long l){
                new AlertDialog.Builder(ExpensesDisplayActivity.this)
                        .setTitle("Are you sure you want to remove " + itemsList.get(listremove) + "?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog1, int which) {
                                itemsList.remove(listremove);
                                ItemsAdapter.notifyDataSetChanged();
                                Log.v("item", "item removed");
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog2, int which) {
                                dialog2.dismiss();
                            }
                        }).create().show();

                return false;
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        // first iteration of the total price
        setTextChange();

        // back button
        findViewById(R.id.backbutton).setOnClickListener(v -> {
            Intent intent = new Intent(ExpensesDisplayActivity.this, ItemEntryActivity.class);
            startActivity(intent);
        });

        // adds the item and price into the log
        AddedItems = findViewById(R.id.budgetloglist);


        MySharedPreferences myStorage = new MySharedPreferences(getApplicationContext());
        //get List and create expense handler
        Expenses expenses = new Expenses(myStorage.getMyList());


        ItemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, expenses.itemInfoList);
        AddedItems.setAdapter(ItemsAdapter);

        AddedItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int listremove, long l){
                new AlertDialog.Builder(ExpensesDisplayActivity.this)
                        .setTitle("Are you sure you want to remove " + itemsList.get(listremove) + "?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog1, int which) {
                                itemsList.remove(listremove);
                                ItemsAdapter.notifyDataSetChanged();
                                Log.v("item", "item removed");
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog2, int which) {
                                dialog2.dismiss();
                            }
                        }).create().show();

                return false;
            }
        });

    }

    public void setTextChange(){
        sumPesos = getIntent().getIntExtra("itemPrice", total);
        total += sumPesos;
        textChange = findViewById(R.id.totalamount);
        textChange.setText("Total Spent: Php " + total);
        Log.v("w", "Total" + total);
    }
}
