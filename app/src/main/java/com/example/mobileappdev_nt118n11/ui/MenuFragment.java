package com.example.mobileappdev_nt118n11.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.mobileappdev_nt118n11.AdminAddfoodActivity;
import com.example.mobileappdev_nt118n11.FoodMenuAdapter;
import com.example.mobileappdev_nt118n11.Model.Food;
import com.example.mobileappdev_nt118n11.R;
import com.example.mobileappdev_nt118n11.databinding.FragmentHomeBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MenuFragment extends Fragment {

    private FragmentHomeBinding binding;
    RecyclerView rcvFoodList;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Food> recyclerFoodList;
    FirebaseDatabase database;
    EditText etSearch;
    FoodMenuAdapter recyclerAdapter;
    FloatingActionButton btn_addfood;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

            View root = inflater.inflate(R.layout.fragment_menu, container, false);
            btn_addfood=(FloatingActionButton)root.findViewById(R.id.btn_add_food) ;

            recyclerFoodList = new ArrayList<>();
            recyclerAdapter = new FoodMenuAdapter(recyclerFoodList, getActivity().getApplicationContext());

            rcvFoodList = (RecyclerView) root.findViewById(R.id.rcv_admin_menu);

            rcvFoodList.setHasFixedSize(true);
            //layoutManager = new LinearLayoutManager(fragmentContext);
            layoutManager = new LinearLayoutManager(getActivity().getBaseContext());
            rcvFoodList.setLayoutManager(layoutManager);
            //rcvFoodList.addItemDecoration(new DividerItemDecoration(rcvFoodList.getContext(), DividerItemDecoration.VERTICAL));
            rcvFoodList.setAdapter(recyclerAdapter);

            database = FirebaseDatabase.getInstance();
            database.getReference().child("Food").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Food foodModel = dataSnapshot.getValue(Food.class);
                        String pushkey = dataSnapshot.getKey().toString();
                        Log.i("idKey",pushkey);
                        //keyList.add(pushkey);
                        foodModel.setId(pushkey);
                        recyclerFoodList.add(foodModel);
                    }
                    recyclerAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            btn_addfood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent addfood = new Intent(getActivity(), AdminAddfoodActivity.class);
                    startActivity(addfood);
                }
            });
            return root;

    }


}