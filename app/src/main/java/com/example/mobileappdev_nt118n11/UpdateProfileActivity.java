package com.example.mobileappdev_nt118n11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobileappdev_nt118n11.Model.User;
import com.example.mobileappdev_nt118n11.ui.profile.Phone;
import com.example.mobileappdev_nt118n11.ui.profile.ProfileFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateProfileActivity extends AppCompatActivity {

    private EditText etName, etEmail, etAddress, etPhone;
    private Button btnUpdate;
    private String key;
    private DatabaseReference UpdateUser;
    private FirebaseDatabase database;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        init();
        key = Phone.Key_Phone;
        database = FirebaseDatabase.getInstance();
        UpdateUser = database.getReference("User");
        database.getReference().child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.child(key).getValue(User.class);
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
                user.setName(etName.getText().toString());
                user.setAddress(etAddress.getText().toString());
                user.setGmail(etEmail.getText().toString());
                UpdateUser.child(key).setValue(user);
                Toast.makeText(UpdateProfileActivity.this, "Đổi thông tin thành công!", Toast.LENGTH_SHORT).show();
                //this.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
                dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
                dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));

            }
        });


    }

    public void init() {
        etName = (EditText) findViewById(R.id.et_profile_name);
        etPhone = (EditText) findViewById(R.id.et_profile_phone);
        etPhone.setEnabled(false);
        etEmail = (EditText) findViewById(R.id.et_profile_email);
        etAddress = (EditText) findViewById(R.id.et_profile_address);
        btnUpdate = (Button) findViewById(R.id.btn_profile_Update);
    }
}