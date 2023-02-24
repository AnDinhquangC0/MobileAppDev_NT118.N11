package com.example.mobileappdev_nt118n11.ui.profile;

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
import com.example.mobileappdev_nt118n11.R;
import com.example.mobileappdev_nt118n11.SignIn;
import com.example.mobileappdev_nt118n11.UpdateProfileActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangePasswordActivity extends AppCompatActivity {
    private Button btn_changepass;
    private EditText ed_password, ed_newpassword, ed_confimpassword;
    private FirebaseDatabase database;
    String Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        btn_changepass= (Button)findViewById(R.id.btn_change_password);
        ed_password=(EditText) findViewById(R.id.et_changepassword_password);
        ed_newpassword=(EditText) findViewById(R.id.et_changepassword_newpassword);
        ed_confimpassword=(EditText) findViewById(R.id.et_changepassword_confimpassword);



        database = FirebaseDatabase.getInstance();
        database.getReference().child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.child(Phone.Key_Phone).getValue(User.class);
                Password= user.getPassword();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        btn_changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ed_password.getText().toString().equals(Password))
                {
                    if(ed_newpassword.getText().toString().equals(ed_confimpassword.getText().toString()))
                    {
                        database.getReference().child("User").child(Phone.Key_Phone).child("Password").setValue(ed_newpassword.getText().toString());
                        Toast.makeText(ChangePasswordActivity.this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                        dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
                        dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));                    }
                    else
                    {
                        Toast.makeText(ChangePasswordActivity.this, "Mật khẩu không khớp.", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(ChangePasswordActivity.this, "Mật khẩu hiện tại không đúng.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    };
}