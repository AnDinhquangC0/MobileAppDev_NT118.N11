package com.example.mobileappdev_nt118n11.ui.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileappdev_nt118n11.CartAdapter;
import com.example.mobileappdev_nt118n11.Database.Database;
import com.example.mobileappdev_nt118n11.Model.Food;
import com.example.mobileappdev_nt118n11.Model.Order;
import com.example.mobileappdev_nt118n11.Model.Request;
import com.example.mobileappdev_nt118n11.R;
import com.example.mobileappdev_nt118n11.ui.profile.Phone;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class CartFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Food> recyclerFoodList;
    FirebaseDatabase database;
    DatabaseReference request;
    TextView tvTotalPrice;
    Button btnPurchase;
    ArrayList<Order> cartList = new ArrayList<>();
    CartAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_cart, container, false);

        database = FirebaseDatabase.getInstance();
        request = database.getReference("Request");

        recyclerView = (RecyclerView) root.findViewById(R.id.rcv_cart);
        recyclerView.setHasFixedSize(true);
        //layoutManager = new LinearLayoutManager(fragmentContext);
        layoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(layoutManager);

        tvTotalPrice = (TextView) root.findViewById(R.id.tv_total);
        btnPurchase = (Button) root.findViewById(R.id.btnthanhtoan);
        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Tạo chi tiết hóa đơn
                //Request orderRequest = new Request(Phone.Key_Phone);
            }
        });
        loadCartFood();
        return root;
    }

    private void loadCartFood() {

        cartList = new Database(getActivity().getBaseContext()).getCart();
        adapter = new CartAdapter(cartList,getActivity().getBaseContext());
        recyclerView.setAdapter(adapter);

        //Tính tổng tiền
        int total = 0;
        for (Order order: cartList)
            total += (Integer.parseInt(order.getPrice())) * Integer.parseInt(order.getQuantity());

        tvTotalPrice.setText(StrDecimalFormat(Integer.toString(total)));
    }

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

}