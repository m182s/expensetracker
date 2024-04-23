package com.example.homepage;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyListAdapter extends RecyclerView.Adapter<MyViewHolder>
{
    private Context _context;
    private ArrayList<ItemInfo> _itemList;
    public MyListAdapter(Context context, ArrayList<ItemInfo> itemList)
    {
        this._context = context;
        this._itemList = itemList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(_context).inflate(R.layout.list_item_layout, parent, false);
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
