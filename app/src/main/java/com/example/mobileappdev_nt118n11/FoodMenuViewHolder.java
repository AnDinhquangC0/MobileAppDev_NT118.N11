package com.example.mobileappdev_nt118n11;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileappdev_nt118n11.Interface.ItemClickListener;
import com.makeramen.roundedimageview.RoundedImageView;

public class FoodMenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tvFoodName, tvFoodType, tvFoodPrice;
    public RoundedImageView rivFoodImage;

    private ItemClickListener itemClickListener;
    public FoodMenuViewHolder(@NonNull View itemView) {
        super(itemView);
        tvFoodName = (TextView) itemView.findViewById(R.id.food_name);
        tvFoodType = (TextView) itemView.findViewById(R.id.food_type);
        tvFoodPrice = (TextView) itemView.findViewById(R.id.food_price);
        rivFoodImage = (RoundedImageView) itemView.findViewById(R.id.food_image);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
