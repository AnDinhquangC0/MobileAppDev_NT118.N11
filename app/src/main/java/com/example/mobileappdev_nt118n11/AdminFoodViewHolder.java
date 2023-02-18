package com.example.mobileappdev_nt118n11;

import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdminFoodViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

    public ImageView item_image;
    public TextView item_name;
    public TextView item_type;
    public TextView item_price;
    public ImageButton btn_delete;
    public AdminFoodViewHolder(@NonNull View itemView) {
        super(itemView);
        item_image = (ImageView) itemView.findViewById(R.id.food_image_admin);
        item_name = (TextView) itemView.findViewById(R.id.food_name_admin);
        item_type = (TextView) itemView.findViewById(R.id.food_type_admin);
        item_price = (TextView) itemView.findViewById(R.id.food_price_admin);
        btn_delete = (ImageButton) itemView.findViewById(R.id.btn_delete_admin);
        itemView.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        //contextMenu.setHeaderTitle("Select action");
        contextMenu.add(0,0,getAdapterPosition(),Common.DELETE);
    }
}
