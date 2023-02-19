package com.example.mobileappdev_nt118n11;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mobileappdev_nt118n11.Database.Database;
import com.example.mobileappdev_nt118n11.Model.Food;
import com.example.mobileappdev_nt118n11.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Common {

    public static final String DELETE="Xóa";
    public static User currentUser;
    //Ngắt số vd: xxxxxx -> xxx,xxx
    public static String StrDecimalFormat(String value)
    {
        StringTokenizer lst = new StringTokenizer(value, ".");
        String str1 = value;
        String str2 = "";
        if (lst.countTokens() > 1)
        {
            str1 = lst.nextToken();
            str2 = lst.nextToken();
        }
        String str3 = "";
        int i = 0;
        int j = -1 + str1.length();
        if (str1.charAt( -1 + str1.length()) == '.')
        {
            j--;
            str3 = ".";
        }
        for (int k = j;; k--)
        {
            if (k < 0)
            {
                if (str2.length() > 0)
                    str3 = str3 + "." + str2;
                return str3;
            }
            if (i == 3)
            {
                str3 = "." + str3;
                i = 0;
            }
            str3 = str1.charAt(k) + str3;
            i++;
        }

    }

    public static ArrayList<String> getType(){
        ArrayList<String> foodNameList = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().child("Type").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.exists()) {
                        String foodItem = dataSnapshot.child("Name").getValue(String.class);
//                    String pushkey = dataSnapshot.getKey();
//                    Log.i("key",pushkey);
//                    //keyList.add(pushkey);
//                    foodItem.setId(pushkey);
//                    recyclerFoodList.add(foodItem);
                        foodNameList.add(foodItem);
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
}
