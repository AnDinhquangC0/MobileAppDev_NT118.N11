package com.example.mobileappdev_nt118n11.ui.cart;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.firebase.ui.auth.util.ui.BucketedTextChangeListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

public class CartFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Food> recyclerFoodList;
    FirebaseDatabase database;
    DatabaseReference requestsTable;
    public static TextView tvTotalPrice;
    Button btnPurchase;
    TextView tvDelete, tvNotify, tvTitle, tvSymbol;
    ImageView imgNotify;
    ArrayList<Order> cartList = new ArrayList<>();
    CartAdapter adapter;
    TextInputEditText etAddr;
    String currentDateTime;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_cart, container, false);
        currentDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
        database = FirebaseDatabase.getInstance();
        requestsTable = database.getReference("Request");

        tvSymbol = root.findViewById(R.id.tv_symbol);
        tvTitle = root.findViewById(R.id.tv_title);
        tvNotify = root.findViewById(R.id.tv_notifycation_cart);
        imgNotify = root.findViewById(R.id.img_notifycation_cart);
        recyclerView = (RecyclerView) root.findViewById(R.id.rcv_cart);
        recyclerView.setHasFixedSize(true);
        //layoutManager = new LinearLayoutManager(fragmentContext);
        layoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(layoutManager);

        etAddr = (TextInputEditText) root.findViewById(R.id.et_address_cart);
        etAddr.setText(Common.currentUser.getAddress());
        etAddr.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (etAddr.getText().toString().isEmpty() || etAddr.getText().toString().replace(" ","").isEmpty())
                        etAddr.setText(Common.currentUser.getAddress());
                }
            }
        });
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
        btnPurchase = (Button) root.findViewById(R.id.btn_submit_order);
        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Tạo chi tiết hóa đơn
                if (cartList.size() > 0){
                    makeRequest();
                }
                else
                {
                    Toast.makeText(getActivity().getBaseContext(),"Giỏ hàng không có sản phẩm!", Toast.LENGTH_SHORT).show();
                }

            }
        });


        loadCartFood();
        loadTotal(cartList);
        return root;
    }



    private void makeRequest(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle("Xác nhận đặt hàng");
        builder.setMessage("Bạn xác nhận đặt đơn hàng này?");
        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Request orderRequest = new Request(
                                Phone.Key_Phone,
                                Common.currentUser.getName(),
                                etAddr.getText().toString(),
                                String.valueOf(getTotal(cartList)),
                                cartList,
                                currentDateTime
                        );
                        DatabaseReference newRequestRef = requestsTable.push();
                        String idRequest = newRequestRef.getKey();
                        requestsTable.child(idRequest).setValue(orderRequest);
//                String order_number = String.valueOf(System.currentTimeMillis());
//                request.child(order_number).setValue(request);
                        new Database(getActivity().getBaseContext()).cleanCart(Phone.Key_Phone);
                        Toast.makeText(getActivity().getBaseContext(),"Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
                        cartList.clear();
                        loadCartFood();
                        loadTotal(cartList);
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

    private void loadCartFood() {
        cartList = new Database(getActivity().getBaseContext()).getCart(Phone.Key_Phone);
        if (cartList.isEmpty()){
            tvDelete.setVisibility(View.GONE);
            imgNotify.setVisibility(View.VISIBLE);
            tvNotify.setVisibility(View.VISIBLE);
            btnPurchase.setVisibility(View.GONE);
            tvTotalPrice.setVisibility(View.GONE);
            tvTitle.setVisibility(View.GONE);
            tvSymbol.setVisibility(View.GONE);
        } else {
            tvDelete.setVisibility(View.VISIBLE);
            imgNotify.setVisibility(View.GONE);
            tvNotify.setVisibility(View.GONE);
            btnPurchase.setVisibility(View.VISIBLE);
            tvTotalPrice.setVisibility(View.VISIBLE);
            tvTitle.setVisibility(View.VISIBLE);
            tvSymbol.setVisibility(View.VISIBLE);
        }
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
        int total = getTotal(list);
        tvTotalPrice.setText(StrDecimalFormat(Integer.toString(total)));
    }

    public int getTotal(ArrayList<Order> list){
        int total = 0;
        for (Order order: list)
            total += (Integer.valueOf(order.getPrice())) * Integer.valueOf(order.getQuantity());
        return total;
    }


    // tạo context menu cho adapter, khi chọn xóa sẽ xóa món
    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

    }
    // tạo context menu cho adapter, khi chọn xóa sẽ xóa món
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getTitle().equals(Common.DELETE))
            deleteCart(item.getOrder());
        return super.onContextItemSelected(item);
    }
    //hàm xóa 1 món khỏi database
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