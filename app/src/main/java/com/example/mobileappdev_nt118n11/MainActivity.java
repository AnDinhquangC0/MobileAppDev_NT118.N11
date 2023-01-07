package com.example.mobileappdev_nt118n11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnSignIn, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignIn=(Button)findViewById(R.id.btn_SignIn);
        btnSignUp=(Button)findViewById(R.id.btn_SignUp);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signIn =new Intent(MainActivity.this,SignIn.class);
                startActivity(signIn);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUp =new Intent(MainActivity.this,SignUp.class);
                startActivity(signUp);
            }
        });
    }

}