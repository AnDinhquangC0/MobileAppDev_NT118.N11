package com.example.mobileappdev_nt118n11.ui.profile;

import static com.example.mobileappdev_nt118n11.NavigationActivity.UserKey;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mobileappdev_nt118n11.Model.User;
import com.example.mobileappdev_nt118n11.R;
import com.example.mobileappdev_nt118n11.databinding.FragmentHomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    private FragmentHomeBinding binding;
    private FirebaseDatabase database;
    private TextView tvName, tvPhone, tvEmail, tvAddress;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        init(root);

        database = FirebaseDatabase.getInstance();
        database.getReference().child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.child(UserKey).getValue(User.class);
                tvName.setText(user.getName());
                tvEmail.setText(user.getGmail());
                tvAddress.setText(user.getAddress());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return root;
    }

    public void init(View root) {
        tvName = root.findViewById(R.id.tv_profile_name);
        tvPhone = root.findViewById(R.id.tv_profile_phone);
        tvEmail = root.findViewById(R.id.tv_profile_email);
        tvAddress = root.findViewById(R.id.tv_profile_address);
    }
}