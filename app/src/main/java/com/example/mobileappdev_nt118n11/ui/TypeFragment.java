package com.example.mobileappdev_nt118n11.ui;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.mobileappdev_nt118n11.AdminAddTypeActivity;
import com.example.mobileappdev_nt118n11.Model.TypeFood;
import com.example.mobileappdev_nt118n11.R;
import com.example.mobileappdev_nt118n11.SignIn;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TypeFragment extends Fragment {

    private RecyclerView rcvType;
    private FloatingActionButton fbtnAdd;
    TypeAdapter typeAdapter;
    private FirebaseDatabase database;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_type, container, false);

        rcvType = root.findViewById(R.id.rcv_admin_type);
        typeAdapter = new TypeAdapter(getActivity().getBaseContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.VERTICAL, false);
        rcvType.setLayoutManager(linearLayoutManager);

        typeAdapter.setData(getListType());
        rcvType.setAdapter(typeAdapter);

        //Add new Type
        fbtnAdd = root.findViewById(R.id.fbtn_type_add);
        fbtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getBaseContext(), AdminAddTypeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        return root;
    }

    private ArrayList<TypeFood> getListType() {
        ArrayList<TypeFood> list = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        database.getReference().child("Type").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    TypeFood typeFood = dataSnapshot.getValue(TypeFood.class);
                    String key = dataSnapshot.getKey();
                    typeFood.setId(key);
                    list.add(typeFood);
                }
                typeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return list;
    }

    @Override
    public void onResume() {
        super.onResume();
        typeAdapter.setData(getListType());

    }
}