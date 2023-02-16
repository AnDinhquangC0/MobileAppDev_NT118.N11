package com.example.mobileappdev_nt118n11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileappdev_nt118n11.Model.Food;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

public class AdminAddfoodActivity extends AppCompatActivity {


    private EditText edName,edPrice,edDecription;
    private TextView tvType;
    private ImageView imgPicture;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    private Button btn_add;
    Food newFood;
    FirebaseDatabase database;
    DatabaseReference food;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_addfood);

        edName=(EditText) findViewById(R.id.et_addfood_name);
        tvType=(TextView) findViewById(R.id.tv_addfood_Type);
        edPrice=(EditText) findViewById(R.id.et_addfood_price);
        edDecription=(EditText) findViewById(R.id.et_addfood_descr);
        imgPicture=(ImageView) findViewById(R.id.img_addfood);
        btn_add=(Button) findViewById(R.id.btn_addfood);

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
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });
        tvType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Typefood = new Intent(AdminAddfoodActivity.this,AdminTypefoodActivity.class);
                startActivity(Typefood);

            }
        });

    }

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
                                    newFood = new Food(edName.getText().toString(),uri.toString(),edDecription.getText().toString(),edPrice.getText().toString(),"Đồ ăn");

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



