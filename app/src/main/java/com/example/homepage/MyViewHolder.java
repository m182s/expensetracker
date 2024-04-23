package com.example.homepage;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder
{
    public TextView itemNameTextView;
    public TextView priceTextView;
    public MyViewHolder(View itemView) {
        super(itemView);
        itemNameTextView = itemView.findViewById(R.id.itemName);
        priceTextView = itemView.findViewById(R.id.price);
    }
}
