package com.example.mobileappdev_nt118n11.ui;

import static com.example.mobileappdev_nt118n11.Common.StrDecimalFormat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.mobileappdev_nt118n11.AdminAddfoodActivity;
import com.example.mobileappdev_nt118n11.AdminFoodMenuAdapter;
import com.example.mobileappdev_nt118n11.AdminFoodViewHolder;
import com.example.mobileappdev_nt118n11.AdminUpdatefoodActivity;
import com.example.mobileappdev_nt118n11.Common;
import com.example.mobileappdev_nt118n11.FoodDetailActivity;
import com.example.mobileappdev_nt118n11.FoodMenuAdapter;
import com.example.mobileappdev_nt118n11.FoodViewHolder;
import com.example.mobileappdev_nt118n11.Model.Food;
import com.example.mobileappdev_nt118n11.R;
import com.example.mobileappdev_nt118n11.SearchActivity;
import com.example.mobileappdev_nt118n11.databinding.FragmentHomeBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MenuFragment extends Fragment {

    private FragmentHomeBinding binding;
    RecyclerView rcvFoodList;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Food> recyclerFoodList;
    FirebaseDatabase database;
    EditText etSearch;
    FirebaseRecyclerAdapter recyclerAdapter;
    FloatingActionButton btn_addfood;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

            View root = inflater.inflate(R.layout.fragment_menu, container, false);
            btn_addfood=(FloatingActionButton)root.findViewById(R.id.btn_add_food) ;

            recyclerFoodList = new ArrayList<>();
            //recyclerAdapter = new AdminFoodMenuAdapter(recyclerFoodList, getActivity().getApplicationContext());

            rcvFoodList = (RecyclerView) root.findViewById(R.id.rcv_admin_menu);

            rcvFoodList.setHasFixedSize(true);
            //layoutManager = new LinearLayoutManager(fragmentContext);
            layoutManager = new LinearLayoutManager(getActivity().getBaseContext());
            rcvFoodList.setLayoutManager(layoutManager);
            //rcvFoodList.addItemDecoration(new DividerItemDecoration(rcvFoodList.getContext(), DividerItemDecoration.VERTICAL));
            rcvFoodList.setAdapter(recyclerAdapter);

            database = FirebaseDatabase.getInstance();
//            database.getReference().child("Food").addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                        Food foodModel = dataSnapshot.getValue(Food.class);
//                        String pushkey = dataSnapshot.getKey().toString();
//                        Log.i("idKey",pushkey);
//                        //keyList.add(pushkey);
//                        foodModel.setId(pushkey);
//                        recyclerFoodList.add(foodModel);
//                    }
//                    recyclerAdapter.notifyDataSetChanged();
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
            btn_addfood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent addfood = new Intent(getActivity(), AdminAddfoodActivity.class);
                    startActivity(addfood);
                }
            });
            loadFoodList();
            return root;

    }

    private void loadFoodList(){
        Query searchByName = database.getReference("Food");
                //.orderByChild("Name");

        FirebaseRecyclerOptions<Food> foodOption = new FirebaseRecyclerOptions.Builder<Food>()
                .setQuery(searchByName,Food.class)
                .build();
        recyclerAdapter = new FirebaseRecyclerAdapter<Food, AdminFoodViewHolder>(foodOption) {
            @Override
            protected void onBindViewHolder(@NonNull AdminFoodViewHolder holder, int position, @NonNull Food model) {
                final Food foodModel = model;
                final String key = recyclerAdapter.getRef(position).getKey();
                Picasso.get().load(foodModel.getImage()).placeholder(R.drawable.background).into(holder.item_image);
                holder.item_name.setText(foodModel.getName());
                holder.item_type.setText(foodModel.getFoodtype());
                holder.item_price.setText(StrDecimalFormat(foodModel.getPrice()));
                holder.btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteFood(key);
                    }
                });

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity().getBaseContext(), AdminUpdatefoodActivity.class);
                        intent.putExtra("adminIdKey", key);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getActivity().getBaseContext().startActivity(intent);
                    }
                });
            }
            @NonNull
            @Override
            public AdminFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_food_item,parent,false);
                return new AdminFoodViewHolder(view);
            }

            @Override
            public int getItemCount() {
                return super.getItemCount();
            }
        };
        recyclerAdapter.startListening();
        rcvFoodList.setAdapter(recyclerAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (recyclerAdapter != null)
            recyclerAdapter.stopListening();
    }

    @Override
    public void onResume(){
        super.onResume();
        loadFoodList();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getTitle().equals(Common.DELETE))
            deleteFood(recyclerAdapter.getRef(item.getOrder()).getKey());
        return super.onContextItemSelected(item);
    }

    private void deleteFood(String key) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle("Xóa món ăn");
        builder.setMessage("Bạn có muốn xóa món này khỏi cơ sở dữ liệu?");
        builder.setPositiveButton("Xác nhận",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        database.getReference("Food").child(key).removeValue();
                    }
                });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}