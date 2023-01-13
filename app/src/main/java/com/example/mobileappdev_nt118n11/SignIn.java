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

import com.example.mobileappdev_nt118n11.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {
    EditText etUsername,etPassword;
    Button btnSignIn;
    TextView tvRegister;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        etPassword =(EditText)findViewById(R.id.et_signin_password);
        etUsername =(EditText) findViewById(R.id.et_signin_username);
        btnSignIn=(Button) findViewById(R.id.btn_Sign_In);
        tvRegister=(TextView)findViewById(R.id.tv_signin_register);

    //Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference table_user =database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                mDialog.setMessage("Please waiting....");
                mDialog.show();


                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(etUsername.getText().toString()).exists()) {
                            mDialog.dismiss();
                            User user = dataSnapshot.child(etUsername.getText().toString()).getValue(User.class);
                            if (user.getPassword().equals(etPassword.getText().toString())) {
                                Toast.makeText(SignIn.this, "Sign in Successfull !", Toast.LENGTH_SHORT).show();
                                Intent Home = new Intent(SignIn.this,NavigationActivity.class);
                                startActivity(Home);
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
        });
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUp = new Intent(SignIn.this,SignUp.class);
                startActivity(signUp);
            }
        });
   }
}
