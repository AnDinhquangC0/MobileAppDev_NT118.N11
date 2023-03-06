package com.example.mobileappdev_nt118n11.ui.favourite;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileappdev_nt118n11.CartAdapter;
import com.example.mobileappdev_nt118n11.Common;
import com.example.mobileappdev_nt118n11.Database.Database;
import com.example.mobileappdev_nt118n11.FavoriteAdapter;
import com.example.mobileappdev_nt118n11.FoodMenuAdapter;
import com.example.mobileappdev_nt118n11.Model.Favorites;
import com.example.mobileappdev_nt118n11.Model.Food;
import com.example.mobileappdev_nt118n11.Model.Order;
import com.example.mobileappdev_nt118n11.R;
import com.example.mobileappdev_nt118n11.ui.profile.Phone;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FavouriteFragment extends Fragment {

    RecyclerView rcvFavoriteList;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Food> recyclerFavoriteList;
    FirebaseDatabase database;
    //EditText etSearch;
    FavoriteAdapter recyclerAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_favourite, container, false);

        recyclerFavoriteList = new ArrayList<>();

        rcvFavoriteList = (RecyclerView) root.findViewById(R.id.rcv_favorite);
        rcvFavoriteList.setHasFixedSize(true);
        //layoutManager = new LinearLayoutManager(fragmentContext);
        layoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        rcvFavoriteList.setLayoutManager(layoutManager);
        //rcvFoodList.addItemDecoration(new DividerItemDecoration(rcvFoodList.getContext(), DividerItemDecoration.VERTICAL));

        loadFavorite();
        return root;
    }

    private void loadFavorite() {
        recyclerFavoriteList = new Database(getActivity().getBaseContext()).getAllFavorites();
        recyclerAdapter = new FavoriteAdapter(recyclerFavoriteList, getActivity().getBaseContext());
        recyclerAdapter.notifyDataSetChanged();
        rcvFavoriteList.setAdapter(recyclerAdapter);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

    }
    // tạo context menu cho adapter, khi chọn xóa sẽ xóa món
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getTitle().equals(Common.DELETE))
            deleteFavorite(item.getOrder());
        return super.onContextItemSelected(item);
    }

    private void deleteFavorite(int pos) {
        recyclerFavoriteList.remove(pos);
        new Database(getActivity().getBaseContext()).cleanFavorite();

        for (Food item: recyclerFavoriteList){
            new Database(getActivity().getBaseContext()).addToFavorites(item);
        }
        //Tải lại cart sau khi xóa
        loadFavorite();
        //loadTotal(cartList);
    }

    @Override
    public void onStop() {
        super.onStop();
        database = FirebaseDatabase.getInstance();
        DatabaseReference favoriteTable = database.getReference()
                .child("Favorite").child(Phone.getKey_Phone());

        for (Food item : recyclerFavoriteList) {
            Favorites favoriteFood = new Favorites(item.getName(), item.getImage(), item.getDescr(), item.getPrice(), item.getFoodtype());
            favoriteTable.child(item.getId()).setValue(favoriteFood);
        }
//        favoriteTable.child(Phone.getKey_Phone()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    if (dataSnapshot.getKey().toString().equals(Phone.getKey_Phone())) {
//                        favoriteTable.child(Phone.Key_Phone).removeValue();
//                        for (Food item : recyclerFavoriteList) {
//                            Favorites favoriteFood = new Favorites(item.getName(), item.getImage(), item.getDescr(), item.getPrice(), item.getFoodtype());
//                            favoriteTable.child(Phone.Key_Phone).child(item.getId()).setValue(favoriteFood);
//                        }
//                    } else {
//                        for (Food item : recyclerFavoriteList) {
//                            Favorites favoriteFood = new Favorites(item.getName(), item.getImage(), item.getDescr(), item.getPrice(), item.getFoodtype());
//                            favoriteTable.child(Phone.Key_Phone).child(item.getId()).setValue(favoriteFood);
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

    }
}

