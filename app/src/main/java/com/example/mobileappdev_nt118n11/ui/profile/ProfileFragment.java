package com.example.mobileappdev_nt118n11.ui.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileappdev_nt118n11.Model.User;
import com.example.mobileappdev_nt118n11.R;
import com.example.mobileappdev_nt118n11.SignIn;
import com.example.mobileappdev_nt118n11.UpdateProfileActivity;
import com.example.mobileappdev_nt118n11.databinding.FragmentHomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    private FragmentHomeBinding binding;
    private String key;
    private FirebaseDatabase database;
    private EditText etName, etEmail, etAddress, etPhone;
    private Button btnUpdate, btnChangePass;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        init(root);


        key = Phone.Key_Phone;
        database = FirebaseDatabase.getInstance();
        database.getReference().child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.child(key).getValue(User.class);
                etPhone.setText(key);
                etName.setText(user.getName());
                etEmail.setText(user.getGmail());
                etAddress.setText(user.getAddress());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent UpdateFrofile = new Intent(getActivity(), UpdateProfileActivity.class);
                startActivity(UpdateFrofile);
            }
        });
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ChangePass = new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(ChangePass);
            }
        });
        return root;
    }

    public void init(View root) {
        etName = root.findViewById(R.id.et_profile_name);
        etPhone = root.findViewById(R.id.et_profile_phone);
        etEmail = root.findViewById(R.id.et_profile_email);
        etAddress = root.findViewById(R.id.et_profile_address);
        btnUpdate =root.findViewById(R.id.btn_profile_Edit);
        btnChangePass=root.findViewById(R.id.btn_profile_ChangePass);
        etPhone.setEnabled(false);
        etAddress.setEnabled(false);
        etName.setEnabled(false);
        etEmail.setEnabled(false);
    }
}