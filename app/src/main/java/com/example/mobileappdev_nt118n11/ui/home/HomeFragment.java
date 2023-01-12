package com.example.mobileappdev_nt118n11.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileappdev_nt118n11.Food;
import com.example.mobileappdev_nt118n11.FoodMenuAdapter;
import com.example.mobileappdev_nt118n11.FoodMenuViewHolder;
import com.example.mobileappdev_nt118n11.R;
import com.example.mobileappdev_nt118n11.databinding.FragmentHomeBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    RecyclerView rcvFoodList;
    //public ArrayList<String> keyList;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Food> recyclerFoodList;
    FirebaseDatabase database;
    DatabaseReference foodListDB;

//    private Context fragmentContext;

    public HomeFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
//        fragmentContext = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        try {
            View root = inflater.inflate(R.layout.fragment_home, container, false);

            database = FirebaseDatabase.getInstance();
            recyclerFoodList = new ArrayList<>();
            FoodMenuAdapter recyclerAdapter = new FoodMenuAdapter(recyclerFoodList, getActivity().getApplicationContext());

            rcvFoodList = (RecyclerView) root.findViewById(R.id.recycler_food_menu);
            rcvFoodList.setHasFixedSize(true);
            //layoutManager = new LinearLayoutManager(fragmentContext);
            layoutManager = new LinearLayoutManager(getActivity().getBaseContext());
            rcvFoodList.setLayoutManager(layoutManager);
            //rcvFoodList.addItemDecoration(new DividerItemDecoration(rcvFoodList.getContext(), DividerItemDecoration.VERTICAL));
            rcvFoodList.setAdapter(recyclerAdapter);


            database.getReference().child("Food").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Food foodModel = dataSnapshot.getValue(Food.class);
                        String pushkey = dataSnapshot.getKey().toString();
                        Log.i("key",pushkey);
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
//        database = FirebaseDatabase.getInstance();
//        recyclerFoodList = new ArrayList<>();
//        FoodMenuAdapter recyclerAdapter = new FoodMenuAdapter(recyclerFoodList, getActivity().getApplicationContext());
//
//        rcvFoodList = (RecyclerView) getActivity().findViewById(R.id.recycler_food_menu);
//        rcvFoodList.setHasFixedSize(true);
//        //layoutManager = new LinearLayoutManager(fragmentContext);
//        layoutManager = new LinearLayoutManager(getActivity().getBaseContext());
//        rcvFoodList.setLayoutManager(layoutManager);
//        //rcvFoodList.addItemDecoration(new DividerItemDecoration(rcvFoodList.getContext(), DividerItemDecoration.VERTICAL));
//        rcvFoodList.setAdapter(recyclerAdapter);
//
//
//        database.getReference().child("Food").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    Food foodModel = dataSnapshot.getValue(Food.class);
//                    recyclerFoodList.add(foodModel);
//                }
//                recyclerAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });



//        FirebaseRecyclerOptions<Food> option =  new FirebaseRecyclerOptions.Builder<Food>()
//                .setQuery(FirebaseDatabase.getInstance().getReference().child("Food"), Food.class)
//                .build();
//        loadMenu();
    }

}