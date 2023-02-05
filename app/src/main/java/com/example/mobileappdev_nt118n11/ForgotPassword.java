package com.example.mobileappdev_nt118n11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ForgotPassword extends AppCompatActivity {

    private EditText etPhone;
    private Button btnNext, btnVerify;
    private ImageButton btnBack;
    private PinView pinView;
    private FirebaseAuth mAuth;

    private String phoneNo;
    private String codeBySystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        init();
        beforSendOTP();
        mAuth = FirebaseAuth.getInstance();


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNo = etPhone.getText().toString().trim();
                if (phoneNo.equals("") == true) {
                    Toast.makeText(ForgotPassword.this, "Bạn cần nhập số điện thoại", Toast.LENGTH_SHORT).show();
                }
                else {
                    sendVerificationCodeToUser("+84" + phoneNo);
                }
            }
        });

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codeofUser = pinView.getText().toString().trim();
                if (codeofUser.equals("") == true) {
                    Toast.makeText(ForgotPassword.this, "Bạn cần nhập OTP", Toast.LENGTH_SHORT).show();
                } else {
                    if (codeofUser.length() != 6) {
                        Toast.makeText(ForgotPassword.this, "Cần nhập đủ 6 chữ số", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (codeofUser.equals(codeBySystem)) {
                            Intent intent = new Intent(ForgotPassword.this, ResetPassword.class);
                            intent.putExtra("PHONE_NO", phoneNo);
                            startActivity(intent);
                        }
                        else Toast.makeText(ForgotPassword.this, "Sai", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ForgotPassword.this, "OTP đã được gửi", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ForgotPassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            };



    private void init() {
        etPhone = findViewById(R.id.et_forgot_phone);
        btnNext = findViewById(R.id.btn_forgot_next);
        btnVerify = findViewById(R.id.btn_forgot_verify);
        pinView = findViewById(R.id.pinview_forgot);
        btnBack = findViewById(R.id.imgbtn_forgot_back);

        etPhone.setText(null);
        etPhone.requestFocus();
    }

    private void beforSendOTP() {
        btnNext.setVisibility(View.VISIBLE);
        btnVerify.setVisibility(View.GONE);
        pinView.setVisibility(View.GONE);
    }

    private void afterSendOTP() {
        btnNext.setVisibility(View.GONE);
        btnVerify.setVisibility(View.VISIBLE);
        pinView.setVisibility(View.VISIBLE);

        etPhone.setEnabled(false);
        pinView.setEnabled(true);
    }
}