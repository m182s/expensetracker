package com.example.homepage;

import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.Transliterator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyListAdapter extends RecyclerView.Adapter<MyViewHolder>
{
    private ExpensesDisplayActivity _ownerActivity;
    private Context _context;
    private ArrayList<ItemInfo> _itemList;
    MySharedPreferences myStorage;
    MyListAdapter ItemsAdapter;

    ItemInfo itemInfo;
    private boolean isDeleteVisible = false;

    Expenses expenses;
    public MyListAdapter(Context context, ArrayList<ItemInfo> itemList, ExpensesDisplayActivity owner)
    {
        this._context = context;
        this._itemList = itemList;
        this._ownerActivity = owner;
    }

    public void setDataList(ArrayList<ItemInfo> itemList)
    {
        this._itemList = itemList;
    }

    public void showDeleteIcon(){
        isDeleteVisible = true;

    }
    public void removeDeleteIcon(){
        isDeleteVisible = false;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(_context).inflate(R.layout.list_item_layout, parent, false);

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return true;
            }
        });
        return new MyViewHolder(view,_ownerActivity);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        Log.v("HiHi","sulod: "+_itemList.size());
        ItemInfo itemInfo = _itemList.get(position);
        holder.itemNameTextView.setText(itemInfo.getItemName());
        holder.priceTextView.setText(Integer.toString(itemInfo.getPrice()));

        if (isDeleteVisible){
            holder.iconView.setVisibility(View.VISIBLE);
            Log.v("Log", "Passed Visible");

        }
        else {
            holder.iconView.setVisibility(View.GONE);
            Log.v("Log", "Passed Gone");
        }
    }

    @Override
    public int getItemCount()
    {
        return _itemList.size();
    }

}
