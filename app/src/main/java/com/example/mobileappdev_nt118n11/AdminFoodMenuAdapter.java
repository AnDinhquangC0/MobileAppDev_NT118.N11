package com.example.mobileappdev_nt118n11;

import static com.example.mobileappdev_nt118n11.FoodMenuAdapter.StrDecimalFormat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileappdev_nt118n11.Model.Food;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class AdminFoodMenuAdapter extends RecyclerView.Adapter<AdminFoodMenuAdapter.ViewHolder> {

    ArrayList<Food> list;
    Context context;

    public AdminFoodMenuAdapter(ArrayList<Food> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_food_item,parent,false);

        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull AdminFoodMenuAdapter.ViewHolder holder, int position) {

        DecimalFormat decFormat = new DecimalFormat("###,###,###");
        Food foodModel = list.get(position);
        Picasso.get().load(foodModel.getImage()).placeholder(R.drawable.background).into(holder.item_image);
        holder.item_name.setText(foodModel.getName());
        holder.item_type.setText(foodModel.getFoodtype());
        holder.item_price.setText(StrDecimalFormat(foodModel.getPrice()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (foodModel.getId().equals(null) && !foodModel.getId().isEmpty()){
                    Toast.makeText(context, "Không tìm thấy món được chọn!", Toast.LENGTH_SHORT).show();

                }
                else{
                    Intent intent = new Intent(context, AdminUpdatefoodActivity.class);
                    intent.putExtra("idKey", foodModel.getId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView item_image;
        TextView item_name, item_type, item_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_image = (ImageView) itemView.findViewById(R.id.food_image);
            item_name = (TextView) itemView.findViewById(R.id.food_name);
            item_type = (TextView) itemView.findViewById(R.id.food_type);
            item_price = (TextView) itemView.findViewById(R.id.food_price);

        }
    }

    //Ngắt số vd: xxxxxx -> xxx,xxx

}
