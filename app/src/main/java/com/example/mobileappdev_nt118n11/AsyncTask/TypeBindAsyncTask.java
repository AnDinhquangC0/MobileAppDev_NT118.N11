package com.example.mobileappdev_nt118n11.AsyncTask;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TypeBindAsyncTask extends AsyncTask<String, Void, ArrayList<String>> {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    ArrayList<String> list;

//    public TypeBindAsyncTask(ArrayList<String> list) {
//        this.list = list;
//    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<String> doInBackground(String... strings) {
        final ArrayList<String> list = new ArrayList<>();
        database.getReference().child(strings[0]).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.exists()) {
                        String areaName = dataSnapshot.child("Name").getValue(String.class);
                        list.add(areaName);
                        //                    String pushkey = dataSnapshot.getKey();
//                    Log.i("key",pushkey);
//                    //keyList.add(pushkey);
//                    foodItem.setId(pushkey);
//                    recyclerFoodList.add(foodItem);
                        //list.add(type);
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

        return list;
    }

    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        super.onPostExecute(strings);
    }

}