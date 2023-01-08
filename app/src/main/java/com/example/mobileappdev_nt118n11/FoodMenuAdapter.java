package com.example.mobileappdev_nt118n11;

import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FoodMenuAdapter extends RecyclerView.Adapter<FoodMenuAdapter.ViewHolder> {

    List<FoodMenuModel> list;

    @NonNull
    @Override
    public FoodMenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodMenuAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name, type, price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            image = (ImageView) itemView.findViewById(R.id.food_image);
            name = (TextView) itemView.findViewById(R.id.food_name);
            type = (TextView) itemView.findViewById(R.id.food_type);
            price = (TextView) itemView.findViewById(R.id.food_name);

        }
    }
}
