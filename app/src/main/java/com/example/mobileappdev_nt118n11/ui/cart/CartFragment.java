package com.example.mobileappdev_nt118n11.ui.cart;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.mobileappdev_nt118n11.Model.Food;
import com.example.mobileappdev_nt118n11.Model.Order;
import com.example.mobileappdev_nt118n11.Model.Request;
import com.example.mobileappdev_nt118n11.R;
import com.example.mobileappdev_nt118n11.ui.profile.Phone;
import com.google.android.material.textfield.TextInputEditText;
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
    public static TextView tvTotalPrice;
    Button btnPurchase;
    TextView tvDelete;
    ArrayList<Order> cartList = new ArrayList<>();
    CartAdapter adapter;
    TextInputEditText etAddr;

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

        etAddr = (TextInputEditText) root.findViewById(R.id.et_address_cart);
        etAddr.setText(Common.currentUser.getAddress());
        tvDelete = (TextView) root.findViewById(R.id.tv_delete_cart);
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartList.clear();
                new Database(getActivity().getBaseContext()).cleanCart(Phone.Key_Phone);
                //Tải lại cart sau khi xóa
                loadCartFood();
                loadTotal(cartList);
            }
        });
        tvTotalPrice = (TextView) root.findViewById(R.id.tv_total);
        btnPurchase = (Button) root.findViewById(R.id.btnthanhtoan);
        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Tạo chi tiết hóa đơn
                //Request orderRequest = new Request(Phone.Key_Phone);
//                String order_number = String.valueOf(System.currentTimeMillis());
//                request.child(order_number).setValue(request);
                //new Database(getActivity().getBaseContext()).cleanCart(Phone.Key_Phone);
            }
        });
        loadCartFood();
        loadTotal(cartList);
        return root;
    }

    private void loadCartFood() {

        cartList = new Database(getActivity().getBaseContext()).getCart(Phone.Key_Phone);
        adapter = new CartAdapter(cartList,getActivity().getBaseContext());
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);


//        int total = 0;
//        for (Order order: cartList)
//            total += (Integer.parseInt(order.getPrice())) * Integer.parseInt(order.getQuantity());
//
//        tvTotalPrice.setText(StrDecimalFormat(Integer.toString(total)));
    }

    //Tính tổng tiền
    public void loadTotal(ArrayList<Order> list){
        int total = 0;
        for (Order order: list)
            total += (Integer.parseInt(order.getPrice())) * Integer.parseInt(order.getQuantity());

        tvTotalPrice.setText(StrDecimalFormat(Integer.toString(total)));
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getTitle().equals(Common.DELETE))
            deleteCart(item.getOrder());
        return super.onContextItemSelected(item);
    }

    private void deleteCart(int pos){
        cartList.remove(pos);
        new Database(getActivity().getBaseContext()).cleanCart(Phone.Key_Phone);

        for (Order item: cartList){
            new Database(getActivity().getBaseContext()).addToCart(item);
        }
        //Tải lại cart sau khi xóa
        loadCartFood();
        loadTotal(cartList);
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