package com.example.mobileappdev_nt118n11.AsyncTask;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.mobileappdev_nt118n11.Model.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FBDataBindingAsyncTask extends AsyncTask<String, Void, ArrayList<String>> {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();



    @Override
    protected ArrayList<String> doInBackground(String... strings) {
        ArrayList<String> foodNameList = new ArrayList<>();
        database.getReference().child(strings[0]).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.exists()) {
                        Food foodItem = dataSnapshot.getValue(Food.class);
//                    String pushkey = dataSnapshot.getKey();
//                    Log.i("key",pushkey);
//                    //keyList.add(pushkey);
//                    foodItem.setId(pushkey);
//                    recyclerFoodList.add(foodItem);
                        foodNameList.add(foodItem.getName());
                    } else {
                        Log.i("FBBinding", "Không gte đc value trong snapshot!");
                    }
                }
                //materialSearchBar.setLastSuggestions(recyclerFoodList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return foodNameList;
    }

    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        super.onPostExecute(strings);
    }

}
