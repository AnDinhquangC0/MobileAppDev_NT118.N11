package com.example.mobileappdev_nt118n11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FoodDetailActivity extends AppCompatActivity {

    FirebaseDatabase database;
    ImageView ivImageDetail;
    TextView tvNameDetail, tvTypeDetail, tvPriceDetail, tvDescrDetail;
    Food foodDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        ivImageDetail = (ImageView) findViewById(R.id.food_image_detail);
        tvNameDetail = (TextView) findViewById(R.id.food_name_detail);
        tvTypeDetail = (TextView) findViewById(R.id.food_type_detail);
        tvPriceDetail = (TextView) findViewById(R.id.food_price_detail);
        tvDescrDetail = (TextView) findViewById(R.id.food_description_detail);

        Intent intent = getIntent();
        String id = intent.getStringExtra("key");

        Log.i("Key in food detail",id);

        database = FirebaseDatabase.getInstance();
        database.getReference().child("Food").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (id.equals(dataSnapshot.getKey().toString())) {
                        foodDetail = dataSnapshot.getValue(Food.class);
                        Log.i("Check", "get into compare");
                        Log.i("Name", foodDetail.getName().toString());
                        Log.i("Price", foodDetail.getPrice().toString());
                        Log.i("Image", foodDetail.getImage().toString());
                        Log.i("Type", foodDetail.getFoodtype().toString());
                        Log.i("Descr", foodDetail.getDescr().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Here the foodDetail is null, cannot get
        Log.i("Name", foodDetail.getName().toString());
        Log.i("Price", foodDetail.getPrice().toString());
        Log.i("Image", foodDetail.getImage().toString());
        Log.i("Type", foodDetail.getFoodtype().toString());
        Log.i("Descr", foodDetail.getDescr().toString());

//        Picasso.get().load(foodDetail.getImage()).placeholder(R.drawable.background).into(ivImageDetail);
//        tvNameDetail.setText(foodDetail.getName());
//        tvTypeDetail.setText(foodDetail.getFoodtype());
//        tvPriceDetail.setText(foodDetail.getPrice());
//        tvDescrDetail.setText(foodDetail.getDescr());
    }
}