package com.example.mobileappdev_nt118n11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import  androidx.appcompat.widget.Toolbar;

import com.example.mobileappdev_nt118n11.Model.Food;
import com.example.mobileappdev_nt118n11.Model.Type;
import com.example.mobileappdev_nt118n11.Model.TypeFood;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class AdminAddfoodActivity extends AppCompatActivity {


    private EditText edName,edPrice,edDecription;
    private TextView tvType;
    private ImageView imgPicture;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    private Button btn_add;
    private Toolbar tb_TypeFood;
    Food newFood;
    FirebaseDatabase database;
    DatabaseReference food;
    FirebaseStorage storage;
    StorageReference storageReference;
    String Type;
    Spinner spnType;
    SpinnerAdapter typeAdapter;
    //ArrayAdapter<Food>
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_addfood);

        edName=(EditText) findViewById(R.id.et_addfood_name);

        edPrice=(EditText) findViewById(R.id.et_addfood_price);
        edDecription=(EditText) findViewById(R.id.et_addfood_descr);
        imgPicture=(ImageView) findViewById(R.id.img_addfood);
        btn_add=(Button) findViewById(R.id.btn_addfood);
        spnType = findViewById(R.id.spinner_type);
        //tb_TypeFood=(Toolbar)findViewById(R.id.tb_addfood_type);

        database=FirebaseDatabase.getInstance();
        food=database.getReference("Food");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        imgPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        typeAdapter = new SpinnerAdapter(this,R.layout.spinner_item_selected, new ArrayList<TypeFood>());
        spnType.setAdapter(typeAdapter);
        spnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        database.getReference().child("Type").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    TypeFood typeModel = dataSnapshot.getValue(TypeFood.class);
                    String pushkey = dataSnapshot.getKey().toString();
                    Log.i("idKey", pushkey);
                    //keyList.add(pushkey);
                    //foodModel.setId(pushkey);
                    typeAdapter.add(typeModel);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });
        //setSupportActionBar(tb_TypeFood);


    }

//    private ArrayList<TypeFood> getTypeList(){
//        ArrayList<TypeFood> typeList = new ArrayList<TypeFood>();
//        typeDB.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    TypeFood typeModel = dataSnapshot.getValue(TypeFood.class);
//                    String pushkey = dataSnapshot.getKey().toString();
//                    Log.i("idKey",pushkey);
//                    //keyList.add(pushkey);
//                    //foodModel.setId(pushkey);
//                    SpinnerAdapter.
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        return typeList;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.typefood_item,menu);
        return true;
    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle item selection
//        switch (item.getItemId()) {
//            case R.id.food:
//                Type="Đồ ăn";
//                return true;
//            case R.id.water:
//                Type="Nước";
//                return true;
//            case R.id.snack:
//                Type="Đồ ăn vặt";
//                return true;
//            case R.id.cake:
//                Type="Bánh";
//                return true;
//            default:
//                Type="Đồ ăn";
//                return true;
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgPicture.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(AdminAddfoodActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Random random=new Random();
                                    int number = random.nextInt();
                                    String IDFood = String.valueOf(number);
                                    newFood = new Food(edName.getText().toString(),uri.toString(),edDecription.getText().toString(),edPrice.getText().toString(),Type);

                                    food.child(IDFood).setValue(newFood);
                                    Intent Management = new Intent(AdminAddfoodActivity.this,ManagementActivity.class);
                                    startActivity(Management);
                                    finish();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AdminAddfoodActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }



}



