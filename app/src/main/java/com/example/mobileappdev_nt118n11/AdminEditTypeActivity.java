package com.example.mobileappdev_nt118n11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobileappdev_nt118n11.Model.TypeFood;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminEditTypeActivity extends AppCompatActivity {

    private EditText etId, etName;
    private Button btnSave;
    private FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_type);

        //init
        etId = findViewById(R.id.et_edittype_id);
        etId.setEnabled(false);
        etName = findViewById(R.id.et_edittype_name);
        btnSave = findViewById(R.id.btn_savetype);

        //getIntent
        String ID = getIntent().getStringExtra("TYPE_ID");

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference().child("Type").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    TypeFood typeFood = dataSnapshot.getValue(TypeFood.class);
                    if (dataSnapshot.getKey().equals(ID)) {
                        etId.setText(ID);
                        etName.setText(typeFood.getName());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseDatabase.getReference().child("Type").child(ID).setValue(new TypeFood(etName.getText().toString().trim()));
                Intent intent = new Intent(AdminEditTypeActivity.this, ManagementActivity.class);
                startActivity(intent);
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