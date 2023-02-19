package com.example.mobileappdev_nt118n11.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import com.example.mobileappdev_nt118n11.Model.Food;
import com.example.mobileappdev_nt118n11.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FoodBindAsyncTask extends AsyncTask<ArrayList<String>, Void, Integer> {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    Context context;
    Spinner spn;

    public FoodBindAsyncTask(Context context, Spinner spn) {
        this.spn = spn;
        this.context = context;
    }

    @Override
    protected Integer doInBackground(ArrayList<String>... arrayLists) {

        ArrayList<String> list = arrayLists[0];
        String id = list.get(list.size() - 1);
        list.remove(list.size() - 1);
        database.getReference().child("Food").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (id.equals(dataSnapshot.getKey().toString())) {
                        //final int pos;
                        Food UpdateFood = dataSnapshot.getValue(Food.class);
//                        Log.i("idKey food", id);
//                        pos = list.indexOf(UpdateFood.getFoodtype());
//
////                        for (int i=0; i<areasAdapter.getCount(); i++){
////                            if (areasAdapter.getItem(i).equals(UpdateFood.getFoodtype())){
////                                spnType.setSelection(i);
////                            }
////                        }
//                        //int pos = areasAdapter.getPosition(UpdateFood.getFoodtype());
//                        Log.i("pos", String.valueOf(pos));
                        //Log.i("size of aray adapter", String.valueOf(areasAdapter.getCount()));
//                        for (int i=0; i<areasAdapter.getCount(); i++){
//                            Log.i("type" + String.valueOf(i), areasAdapter.getItem(i).toString());
//                        }
                        spn.setSelection(list.indexOf(UpdateFood.getFoodtype()));
                        Log.i("current value in async", UpdateFood.getFoodtype());

                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//            Log.i("idKey food", id);
//           int  pos = list.indexOf(UpdateFood.getFoodtype());
//
////                        for (int i=0; i<areasAdapter.getCount(); i++){
////                            if (areasAdapter.getItem(i).equals(UpdateFood.getFoodtype())){
////                                spnType.setSelection(i);
////                            }
////                        }
//            //int pos = areasAdapter.getPosition(UpdateFood.getFoodtype());
//                        Log.i("pos", String.valueOf(pos));
                        return -1;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        //spn.setSelection(integer);
    }
}