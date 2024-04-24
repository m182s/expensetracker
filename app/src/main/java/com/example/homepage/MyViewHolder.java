package com.example.homepage;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder
{
    private DeleteListener deleteListener;
    public TextView itemNameTextView;
    public TextView priceTextView;

    public ImageView iconView;
    public MyViewHolder(View itemView, DeleteListener listener) {
        super(itemView);
        deleteListener = listener;
        itemNameTextView = itemView.findViewById(R.id.itemName);
        priceTextView = itemView.findViewById(R.id.price);
        iconView = itemView.findViewById(R.id.del_icon);

        iconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               deleteListener.onDelete(getAdapterPosition());
            }
        });
    }
}
