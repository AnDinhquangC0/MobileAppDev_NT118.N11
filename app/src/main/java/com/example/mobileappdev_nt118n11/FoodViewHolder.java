package com.example.mobileappdev_nt118n11;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView item_image;
    TextView item_name, item_type, item_price;
    public FoodViewHolder(@NonNull View itemView) {
        super(itemView);
        item_image = (ImageView) itemView.findViewById(R.id.food_image);
        item_name = (TextView) itemView.findViewById(R.id.food_name);
        item_type = (TextView) itemView.findViewById(R.id.food_type);
        item_price = (TextView) itemView.findViewById(R.id.food_price);
    }

    @Override
    public void onClick(View view) {

    }
}
