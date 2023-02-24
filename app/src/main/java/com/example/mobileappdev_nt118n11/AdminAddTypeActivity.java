package com.example.mobileappdev_nt118n11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileappdev_nt118n11.Model.TypeFood;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminAddTypeActivity extends AppCompatActivity {

    private EditText etId, etName;
    private TextView tvNotify;
    private Button btnAdd;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_type);

        //init
        etId = findViewById(R.id.et_addtype_id);
        etName = findViewById(R.id.et_addtype_name);
        btnAdd = findViewById(R.id.btn_addtype);
        tvNotify = findViewById(R.id.tv_type_notify);
        tvNotify.setVisibility(View.GONE);

        etId.setText("");
        etName.setText("");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addType();
                dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
                dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
            }
        });

    }

    private void addType() {
        mDialog = new ProgressDialog(AdminAddTypeActivity.this);
        mDialog.setMessage("Please waiting....");
        mDialog.show();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Type");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (etId.getText().toString().trim().equals("") == true || etName.getText().toString().trim().equals("") == true) {
                    mDialog.dismiss();
                    Toast.makeText(AdminAddTypeActivity.this, "Bạn cần nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if (snapshot.child(etId.getText().toString().trim()).exists()) {
                        mDialog.dismiss();
                        tvNotify.setVisibility(View.VISIBLE);
                    } else {
                        mDialog.dismiss();
                        TypeFood typeFood = new TypeFood(etName.getText().toString());
                        databaseReference.child(etId.getText().toString()).setValue(typeFood);
//                        Intent intent = new Intent(AdminAddTypeActivity.this, ManagementActivity.class);
//                        startActivity(intent);
//                        finish();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }
}