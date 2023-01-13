package com.example.mobileappdev_nt118n11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    RecyclerView rcvFoodList;
    RecyclerView.LayoutManager layoutManager;
    String searchKeyword;
    ArrayList<Food> recyclerFoodList;
    FirebaseDatabase database;
    EditText etSearch;
    FoodMenuAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        recyclerFoodList = new ArrayList<>();
        recyclerAdapter = new FoodMenuAdapter(recyclerFoodList, this);

        rcvFoodList = (RecyclerView) findViewById(R.id.rcv_food_menu_search);
        rcvFoodList.setHasFixedSize(true);
        //layoutManager = new LinearLayoutManager(fragmentContext);
        layoutManager = new LinearLayoutManager(this);
        rcvFoodList.setLayoutManager(layoutManager);
        //rcvFoodList.addItemDecoration(new DividerItemDecoration(rcvFoodList.getContext(), DividerItemDecoration.VERTICAL));
        rcvFoodList.setAdapter(recyclerAdapter);
        etSearch = (EditText) findViewById(R.id.et_search_bar_search);

        Intent getSearchKeyWord = getIntent();
        searchKeyword = getSearchKeyWord.getStringExtra("searchKey");
        etSearch.setText(searchKeyword);
        etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (i == KeyEvent.KEYCODE_ENTER)) {
                    etSearch.getText().toString();
                }
                return false;
            }
        });

        database = FirebaseDatabase.getInstance();
        database.getReference().child("Food").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Food foodModel = dataSnapshot.getValue(Food.class);
                    String pushkey = dataSnapshot.getKey();
                    Log.i("key",pushkey);
                    //keyList.add(pushkey);
                    foodModel.setId(pushkey);
                    if(foodModel.getName().contains(searchKeyword)){
                        recyclerFoodList.add(foodModel);
                    }
                }
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}