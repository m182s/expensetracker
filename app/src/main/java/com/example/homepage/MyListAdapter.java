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
    private Context _context;
    private ArrayList<ItemInfo> _itemList;
    MySharedPreferences myStorage;
    MyListAdapter ItemsAdapter;

    ItemInfo itemInfo;

    Expenses expenses;
    public MyListAdapter(Context context, ArrayList<ItemInfo> itemList)
    {
        this._context = context;
        this._itemList = itemList;
    }

    public void setDataList(ArrayList<ItemInfo> itemList)
    {
        this._itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(_context).inflate(R.layout.list_item_layout, parent, false);

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                /*
                - get the id of the item based on what was clicked
                - search item in sharedpref
                - delete in itemslist
                - delete in expenses
                - delete in sharedpref
                - notify data set change
                 */

                myStorage.removeDataFromSharedPreferences(itemInfo.getId());
                List<ItemInfo> getItem = myStorage.getMyList();
                getItem.clear();
                expenses.removeItemInfo(itemInfo);
                ItemsAdapter.notifyDataSetChanged();
                return true;
            }
        });
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        Log.v("HiHi","sulod: "+_itemList.size());
        ItemInfo itemInfo = _itemList.get(position);
        holder.itemNameTextView.setText(itemInfo.getItemName());
        holder.priceTextView.setText(Integer.toString(itemInfo.getPrice()));
    }

    @Override
    public int getItemCount()
    {
        return _itemList.size();
    }

}
