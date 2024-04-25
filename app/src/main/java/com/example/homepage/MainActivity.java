package com.example.homepage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public Expenses _itemLists;
    ArrayList<ItemInfo> itemsList;
    TextView budgetText, totalToday;

    ExpensesDisplayActivity priceText;

    EditText budgetChange;
    int changeBudget;
    int _targetBudget;
    MySharedPreferences _myStorage;

    @Override
    protected void onResume() {
        super.onResume();
        reloadData();
    }

    public void reloadData(){
        double currentTotal = 0;
        _itemLists = new Expenses(_myStorage.getMyList());

        currentTotal = _itemLists.calculateTotal();
        totalToday.setText("Today's Total: PHP " + String.valueOf(currentTotal));
        _targetBudget = _myStorage.getMyBudget();
        budgetText.setText("Today's Budget: PHP " + String.valueOf(_targetBudget));
        Log.v("budget", "BUDGET:" + _targetBudget);

        LinearLayout totalcolor = findViewById(R.id.totalbg);
        if(currentTotal > _targetBudget){
            totalcolor.setBackgroundColor(Color.RED);
        }
        else{
            totalcolor.setBackgroundColor(0x29335C);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        totalToday = findViewById(R.id.todaystotal);
        _itemLists = new Expenses();

        LinearLayout search = findViewById(R.id.layoutSearch);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ItemEntryActivity.class);
                startActivity(intent);
                Log.v("Move", "MOVED");
            }
        });

        LinearLayout layout = findViewById(R.id.layoutExpenses);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExpensesDisplayActivity.class);
                startActivity(intent);
            }
        });

        _myStorage = new MySharedPreferences(getApplicationContext());

        budgetChange = findViewById(R.id.budgetchange);
        budgetText = findViewById(R.id.budgettext);
        budgetChange.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

//                event.getKeyCode();
//                Log.v("Budget", "Budget changed to " + keyCode);
                if(keyCode == 66) {
                    String budget = budgetChange.getText().toString();
                    changeBudget = Integer.parseInt(budget);
                    _myStorage.saveMyBudget(changeBudget);
                    budgetText.setText("Today's Budget: PHP " + budget);
                    reloadData();
                }
                return false;
            }
        });

    }
}

