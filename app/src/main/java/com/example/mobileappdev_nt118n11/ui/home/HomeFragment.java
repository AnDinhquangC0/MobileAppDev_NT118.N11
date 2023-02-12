package com.example.mobileappdev_nt118n11.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileappdev_nt118n11.Model.Food;
import com.example.mobileappdev_nt118n11.FoodMenuAdapter;
import com.example.mobileappdev_nt118n11.R;
import com.example.mobileappdev_nt118n11.databinding.FragmentHomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    RecyclerView rcvFoodList;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Food> recyclerFoodList;
    FirebaseDatabase database;
    EditText etSearch;
    FoodMenuAdapter recyclerAdapter;

//    private Context fragmentContext;

    public HomeFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        try {
            View root = inflater.inflate(R.layout.fragment_home, container, false);

            recyclerFoodList = new ArrayList<>();
            recyclerAdapter = new FoodMenuAdapter(recyclerFoodList, getActivity().getApplicationContext());

            rcvFoodList = (RecyclerView) root.findViewById(R.id.rcv_home_food_menu);
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
            return root;
        }catch (Exception e) {
            Log.e("HomeFragment", "onCreateView", e);
            throw e;}
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}