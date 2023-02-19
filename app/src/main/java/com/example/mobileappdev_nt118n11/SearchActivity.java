package com.example.mobileappdev_nt118n11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobileappdev_nt118n11.AsyncTask.FBDataBindingAsyncTask;
import com.example.mobileappdev_nt118n11.Model.Food;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

public class SearchActivity extends AppCompatActivity {

    RecyclerView rcvFoodList;
    RecyclerView.LayoutManager layoutManager;
    String searchKeyword;
    ArrayList<Food> recyclerFoodList;
    FirebaseDatabase database;
    DatabaseReference dbRefFoodMenu;
    Database localDB;
    EditText etSearch;
    FoodMenuAdapter recyclerAdapter;
    MaterialSearchBar materialSearchBar;
    ArrayList<String> suggestList;
    FirebaseRecyclerAdapter<Food, FoodViewHolder> searchAdapter;
    FirebaseRecyclerAdapter<Food, FoodViewHolder> menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        database = FirebaseDatabase.getInstance();
        dbRefFoodMenu = database.getReference().child("Food");
        /////////////Local DB
        localDB=new Database(this);
        
        rcvFoodList = findViewById(R.id.rcv_food_menu_search);
        rcvFoodList.setHasFixedSize(true);
        //layoutManager = new LinearLayoutManager(fragmentContext);
        layoutManager = new LinearLayoutManager(this);
        rcvFoodList.setLayoutManager(layoutManager);

        materialSearchBar = (MaterialSearchBar) findViewById(R.id.mtsearchbar);
        loadSuggestion();
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ArrayList<String> suggest = new ArrayList<String>();
                for (String search:suggestList){
                    if (search.toLowerCase(Locale.ROOT).contains(materialSearchBar.getText().toLowerCase(Locale.ROOT)))
                        suggest.add(search);
                }
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled){

                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        loadMenu();
    }
    //tìm món với từ khóa text
    private void startSearch(CharSequence text) {
        Query searchByName = dbRefFoodMenu.orderByChild("Name").equalTo(text.toString());

        FirebaseRecyclerOptions<Food> foodOption = new FirebaseRecyclerOptions.Builder<Food>()
                .setQuery(searchByName,Food.class)
                .build();
        searchAdapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(foodOption) {
            @Override
            protected void onBindViewHolder(@NonNull FoodViewHolder holder, int position, @NonNull Food model) {
                final Food foodModel = model;
                final String key = searchAdapter.getRef(position).getKey();
                Picasso.get().load(foodModel.getImage()).placeholder(R.drawable.background).into(holder.item_image);
                holder.item_name.setText(foodModel.getName());
                holder.item_type.setText(foodModel.getFoodtype());
                holder.item_price.setText(StrDecimalFormat(foodModel.getPrice()));

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(SearchActivity.this, FoodDetailActivity.class);
                        intent.putExtra("idKey", key);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        
                        //////Add Favorites
                        if(localDB.isFavorites(menuAdapter.getRef(position).getKey()))
                        {
                            holder.fav_image.setImageResource(R.drawable.ic_baseline_favorite_24);
                        }
                        ////////////////Click to change state of Favorites
                       holder.fav_image.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               if (!localDB.isFavorites(menuAdapter.getRef(position).getKey())) {
                                   localDB.addToFavorites(menuAdapter.getRef(position).getKey());
                                   holder.fav_image.setImageResource(R.drawable.ic_baseline_favorite_24);
                                   Toast.makeText(getBaseContext(), "" + foodModel.getName() + "was added to Favorites", Toast.LENGTH_SHORT).show();
                               } else {
                                   localDB.addToFavorites(menuAdapter.getRef(position).getKey());
                                   holder.fav_image.setImageResource(R.drawable.ic_baseline_favorite_24);
                                   Toast.makeText(getBaseContext(), "" + foodModel.getName() + "was removed from Favorites", Toast.LENGTH_SHORT).show();
                               }
                           }
                       });
                    }
                });
            }
            @NonNull
            @Override
            public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item,parent,false);
                    return new FoodViewHolder(view);
            }
        };
        searchAdapter.startListening();
        rcvFoodList.setAdapter(searchAdapter);
    }
    // tải menu khi mới mở giao diện search
    private void loadMenu(){
        FirebaseRecyclerOptions<Food> option = new FirebaseRecyclerOptions.Builder<Food>()
                .setQuery(dbRefFoodMenu,Food.class)
                .build();
        menuAdapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(option) {
            @Override
            protected void onBindViewHolder(@NonNull FoodViewHolder holder, int position, @NonNull Food model) {
                final Food foodModel = model;
                final String key = menuAdapter.getRef(position).getKey();
                Picasso.get().load(foodModel.getImage()).placeholder(R.drawable.background).into(holder.item_image);
                holder.item_name.setText(foodModel.getName());
                holder.item_type.setText(foodModel.getFoodtype());
                holder.item_price.setText(StrDecimalFormat(foodModel.getPrice()));

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(SearchActivity.this, FoodDetailActivity.class);
                        intent.putExtra("idKey", key);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item,parent,false);
                return new FoodViewHolder(view);
            }
        };
        menuAdapter.startListening();
        rcvFoodList.setAdapter(menuAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (menuAdapter != null)
            menuAdapter.stopListening();
        if (searchAdapter != null)
            searchAdapter.stopListening();
    }
    // tải gợi ý lên thanh search
    private void loadSuggestion() {
        suggestList = new ArrayList<>();
        //recyclerAdapter = new FoodMenuAdapter(recyclerFoodList, this);
        //new FBDataBindingAsyncTask.execute("Food");
        database.getReference().child("Food").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if (dataSnapshot.exists()) {
                        Food foodItem = dataSnapshot.getValue(Food.class);
//                    String pushkey = dataSnapshot.getKey();
//                    Log.i("key",pushkey);
//                    //keyList.add(pushkey);
//                    foodItem.setId(pushkey);
//                    recyclerFoodList.add(foodItem);
                        suggestList.add(foodItem.getName());
                    }
                    else{
                        Toast.makeText(getBaseContext(), "Không gte đc value trong snapshot!", Toast.LENGTH_SHORT).show();
                    }
                }
                materialSearchBar.setLastSuggestions(suggestList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //Ngắt số vd: xxxxxx -> xxx,xxx
    public static String StrDecimalFormat(String value) {
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
}
