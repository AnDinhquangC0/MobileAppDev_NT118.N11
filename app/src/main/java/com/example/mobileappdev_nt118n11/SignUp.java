package com.example.mobileappdev_nt118n11;

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

public class SignUp extends AppCompatActivity {
    EditText edtPhone, edtName, edtPassword,edtGmail,edtAddress;
    Button btnSignUp;
    TextView txtSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtName=(EditText) findViewById(R.id.edt_name_su);
        edtPassword=(EditText) findViewById(R.id.edt_password_su);
        edtPhone=(EditText) findViewById(R.id.edt_phone_su);
        edtGmail=(EditText) findViewById(R.id.edt_gmail_su);
        edtAddress=(EditText) findViewById(R.id.edt_address_su);
        btnSignUp=(Button) findViewById(R.id.btn_Sign_Up);
        txtSignIn=(TextView)findViewById(R.id.txt_sign_in);

        //Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference table_user =database.getReference("User");


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                mDialog.setMessage("Please waiting....");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Check if already user phone
                        if(dataSnapshot.child(edtPhone.getText().toString()).exists())
                        {
                            mDialog.dismiss();
                            Toast.makeText(SignUp.this, "Phone Number already register !", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            mDialog.dismiss();
                            User user =new User(edtName.getText().toString(),edtPassword.getText().toString(),edtGmail.getText().toString(),edtAddress.getText().toString());
                            table_user.child(edtPhone.getText().toString()).setValue(user);
                            Toast.makeText(SignUp.this, "Sign Up Successfully !", Toast.LENGTH_SHORT).show();
                            Intent signIn = new Intent(SignUp.this,SignIn.class);
                            startActivity(signIn);
                            finish();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signIn = new Intent(SignUp.this,SignIn.class);
                startActivity(signIn);
            }
        });
    }
}
