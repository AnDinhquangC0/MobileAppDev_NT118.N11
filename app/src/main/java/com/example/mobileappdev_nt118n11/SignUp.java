package com.example.mobileappdev_nt118n11;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.example.mobileappdev_nt118n11.Model.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class SignUp extends AppCompatActivity {
    private TextInputEditText etName, etPassword, etPhone, etEmail, etAddress, etConfirmPassword;
    private Button btnSignUp, btnVerify;
    private TextView tvSignIn;
    private TextInputLayout tilPass;
    private PinView pinView;
    private FirebaseAuth mAuth;
    private String phoneNo, pass, name, address, mail, confirmpass;
    private String codeBySystem;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        etName= findViewById(R.id.et_signup_name);
        etPassword= findViewById(R.id.et_signup_password);
        etConfirmPassword= findViewById(R.id.et_signup_confirmpassword);
        etPhone= findViewById(R.id.et_signup_phone);
        etEmail= findViewById(R.id.et_signup_email);
        etAddress= findViewById(R.id.et_signup_address);
        btnSignUp= findViewById(R.id.btn_sign_up);
        tvSignIn= findViewById(R.id.tv_signup_login);
        btnVerify = findViewById(R.id.btn_signup_verify);
        pinView = findViewById(R.id.pinview_signup);
        tilPass = findViewById(R.id.til_signup_pass);

        beforeSendOTP();


        //Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference table_user =database.getReference("User");


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDialog = new ProgressDialog(SignUp.this);
                mDialog.setMessage("Please waiting....");
                mDialog.show();
                phoneNo = etPhone.getText().toString().trim();
                name = etName.getText().toString().trim();
                pass = etPassword.getText().toString().trim();
                confirmpass = etConfirmPassword.getText().toString().trim();
                address = etAddress.getText().toString().trim();
                mail = etEmail.getText().toString().trim();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Check if already user phone
                        if (name.equals("") || phoneNo.equals("") || pass.equals("") || mail.equals("") || address.equals("")) {
                            mDialog.dismiss();
                            Toast.makeText(SignUp.this, "Bạn cần nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        } else {
                            if(dataSnapshot.child(etPhone.getText().toString()).exists())
                            {
                                mDialog.dismiss();
                                Toast.makeText(SignUp.this, "Số điện thoại này đã được đăng ký", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                if (pass.equals(confirmpass) == false) {
                                    mDialog.dismiss();
                                    Toast.makeText(SignUp.this, "Xác nhận mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                                } else {
                                    sendVerificationCodeToUser("+84" + phoneNo);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signIn = new Intent(SignUp.this,SignIn.class);
                startActivity(signIn);
            }
        });

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codeofUser = pinView.getText().toString().trim();
                if (codeofUser.equals("") == true) {
                    Toast.makeText(SignUp.this, "Bạn cần nhập OTP", Toast.LENGTH_SHORT).show();
                } else {
                    if (codeofUser.length() != 6) {
                        Toast.makeText(SignUp.this, "Cần nhập đủ 6 chữ số", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (codeofUser.equals(codeBySystem)) {
                            User user = new User(name, pass, mail, address);
                            table_user.child(etPhone.getText().toString()).setValue(user);
                            Toast.makeText(SignUp.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            Intent signIn = new Intent(SignUp.this, SignIn.class);
                            startActivity(signIn);
                            finish();
                        }
                        else Toast.makeText(SignUp.this, "Sai", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void sendVerificationCodeToUser(String phone) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phone)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    mDialog.dismiss();
                    Toast.makeText(SignUp.this, "OTP đã được gửi", Toast.LENGTH_SHORT).show();
                    afterSendOTP();
                }
                //sau khi verify thanh cong
                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    if (code != null) {
                        codeBySystem = code;
                    }
                }
                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            };

    private void beforeSendOTP() {
        btnVerify.setVisibility(View.GONE);
        pinView.setVisibility(View.GONE);

        btnSignUp.setVisibility(View.VISIBLE);
        tvSignIn.setVisibility(View.VISIBLE);
        etName.setEnabled(true);
        etPhone.setEnabled(true);
        etPassword.setEnabled(true);
        etEmail.setEnabled(true);
        etAddress.setEnabled(true);
    }

    private void afterSendOTP() {
        btnVerify.setVisibility(View.VISIBLE);
        pinView.setVisibility(View.VISIBLE);

        btnSignUp.setVisibility(View.GONE);
        tvSignIn.setVisibility(View.GONE);
        etName.setEnabled(false);
        etPhone.setEnabled(false);
        etPassword.setEnabled(false);
        etEmail.setEnabled(false);
        etAddress.setEnabled(false);
    }
}

