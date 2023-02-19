package com.example.mobileappdev_nt118n11;

import static com.example.mobileappdev_nt118n11.FoodMenuAdapter.StrDecimalFormat;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

public class AdminFoodMenuAdapter extends RecyclerView.Adapter<AdminFoodViewHolder> {

    ArrayList<Food> list;
    Context context;

    public AdminFoodMenuAdapter(ArrayList<Food> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AdminFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_food_item,parent,false);

        return new AdminFoodViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull AdminFoodViewHolder holder, int position) {

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
                    intent.putExtra("adminIdKey", foodModel.getId());
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

//    public class AdminFoodViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
//
//        ImageView item_image;
//        TextView item_name, item_type, item_price;
//        ImageButton btn_delete;
//        public AdminFoodViewHolder(@NonNull View itemView) {
//            super(itemView);
//            item_image = (ImageView) itemView.findViewById(R.id.food_image_admin);
//            item_name = (TextView) itemView.findViewById(R.id.food_name_admin);
//            item_type = (TextView) itemView.findViewById(R.id.food_type_admin);
//            item_price = (TextView) itemView.findViewById(R.id.food_price_admin);
//            btn_delete = (ImageButton) itemView.findViewById(R.id.btn_delete_admin);
//            itemView.setOnCreateContextMenuListener(this);
//        }
//
//        @Override
//        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
//            //contextMenu.setHeaderTitle("Select action");
//            contextMenu.add(0,0,getAdapterPosition(),Common.DELETE);
//        }
//    }



}
