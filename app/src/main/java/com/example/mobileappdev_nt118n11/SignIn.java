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

public class SignIn extends AppCompatActivity {
    EditText edtPhone,edtPassword;
    Button btnSignIn;
    TextView txtregister;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtPassword =(EditText)findViewById(R.id.edt_password_si);
        edtPhone =(EditText) findViewById(R.id.edt_name_su);
        btnSignIn=(Button) findViewById(R.id.btn_Sign_Up);
        txtregister=(TextView)findViewById(R.id.txt_register);

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

                        if(dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                            mDialog.dismiss();
                            User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                            if (user.getPassword().equals(edtPassword.getText().toString())) {
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
        txtregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUp = new Intent(SignIn.this,SignUp.class);
                startActivity(signUp);
            }
        });
   }
}
