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
import androidx.navigation.Navigation;

import com.example.mobileappdev_nt118n11.Model.User;
import com.example.mobileappdev_nt118n11.ui.profile.Phone;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {
    EditText etUsername,etPassword;
    Button btnSignIn;
    TextView tvRegister, tvForgot;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        etPassword =(EditText)findViewById(R.id.et_signin_password);
        etUsername =(EditText) findViewById(R.id.et_signin_username);
        btnSignIn=(Button) findViewById(R.id.btn_Sign_In);
        tvRegister=(TextView)findViewById(R.id.tv_signin_register);
        tvForgot = findViewById(R.id.tv_signin_forgot);

    //Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference table_user =database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                mDialog.setMessage("Please waiting....");
                mDialog.show();

                if(etUsername.getText().toString().equals("118118118")&&etPassword.getText().toString().equals("NT118.N11"))
                {
                    Toast.makeText(SignIn.this, "Sign in with Admin Successfull !", Toast.LENGTH_SHORT).show();
                    Intent Management = new Intent(SignIn.this,ManagementActivity.class);
                    startActivity(Management);
                }
                else{
                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child(etUsername.getText().toString()).exists()) {
                                mDialog.dismiss();
                                User user = dataSnapshot.child(etUsername.getText().toString()).getValue(User.class);
                                if (user.getPassword().equals(etPassword.getText().toString())) {
                                    Toast.makeText(SignIn.this, "Sign in Successfull !", Toast.LENGTH_SHORT).show();
                                    Intent Home = new Intent(SignIn.this,NavigationActivity.class);
                                    Phone.Key_Phone=etUsername.getText().toString().trim();
                                    Common.currentUser = user;
                                    startActivity(Home);
                                    finish();
                                } else
                                    Toast.makeText(SignIn.this, "Sign in Faile !", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(SignIn.this,"User not exist",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }
        });
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUp = new Intent(SignIn.this,SignUp.class);
                startActivity(signUp);
            }
        });

        tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignIn.this,ForgotPassword.class);
                startActivity(intent);
            }
        });
   }
}
