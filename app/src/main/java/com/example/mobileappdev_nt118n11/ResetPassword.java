package com.example.mobileappdev_nt118n11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class ResetPassword extends AppCompatActivity {

    private String phoneNo;
    private EditText etPass, etConfirmPass;
    private Button btnSave;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        init();
        getFromIntent();
        firebaseDatabase = FirebaseDatabase.getInstance();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = etPass.getText().toString().trim();
                String confirmpass = etConfirmPass.getText().toString().trim();
                if (pass.equals("") || confirmpass.equals("")) {
                    Toast.makeText(ResetPassword.this, "Bạn cần nhập đầy đủ!", Toast.LENGTH_SHORT).show();
                } else {
                    if (pass.equals(confirmpass) == false) {
                        Toast.makeText(ResetPassword.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    }
                    else Toast.makeText(ResetPassword.this, "Đã đặt lại mật khẩu", Toast.LENGTH_SHORT).show();
                    firebaseDatabase.getReference().child("User").child(phoneNo).child("Password").setValue(pass);
                    Intent intent = new Intent(ResetPassword.this, SignIn.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void getFromIntent() {
        phoneNo = getIntent().getStringExtra("PHONE_NO");
    }

    private void init() {
        etPass = findViewById(R.id.et_reset_password);
        etConfirmPass = findViewById(R.id.et_reset_confirmpassword);
        btnSave = findViewById(R.id.btn_reset);
    }
}