package com.example.mobileappdev_nt118n11;

import static com.example.mobileappdev_nt118n11.FoodDetailActivity.getDecimalFormattedString;

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
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.mobileappdev_nt118n11.Model.Food;
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
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.UUID;

public class AdminUpdatefoodActivity extends AppCompatActivity {

    private EditText edName, edType,edPrice,edDecription;
    private ImageView imgPicture;
    private Uri filePath;
    private Toolbar tb_TypeFood;
    private final int PICK_IMAGE_REQUEST = 71;
    private Button btn_update,btn_delete,btn_upload;
    Food UpdateFood;
    FirebaseDatabase database;
    DatabaseReference food;
    FirebaseStorage storage;
    StorageReference storageReference;
    String id,url;
    String Type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_updatefood);
        edName=(EditText) findViewById(R.id.et_updatefood_name);
        edDecription=(EditText) findViewById(R.id.et_updatefood_descr);
        edPrice=(EditText) findViewById(R.id.et_updatefood_price);
        tb_TypeFood=(Toolbar) findViewById(R.id.tb_update_typefood);
        imgPicture=(ImageView) findViewById(R.id.img_updatefood);
        btn_update=(Button) findViewById(R.id.btn_updatefood);
        btn_delete=(Button) findViewById(R.id.btn_deletefood);
        btn_upload=(Button)findViewById(R.id.btn_uploadpicture);
        database=FirebaseDatabase.getInstance();
        food=database.getReference("Food");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        Intent intent = getIntent();
        id = intent.getStringExtra("adminIdKey");
        //
        setSupportActionBar(tb_TypeFood);
        database = FirebaseDatabase.getInstance();
        database.getReference().child("Food").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (id.equals(dataSnapshot.getKey().toString())) {
                        UpdateFood = dataSnapshot.getValue(Food.class);
                        Picasso.get().load(UpdateFood.getImage()).placeholder(R.drawable.background).into(imgPicture);
                        edName.setText(UpdateFood.getName());
                        edPrice.setText(UpdateFood.getPrice());
                        edDecription.setText(UpdateFood.getDescr());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        imgPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

            }
        });
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage(UpdateFood);
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UpdateFood.setName(edName.getText().toString());
                UpdateFood.setFoodtype(Type);
                UpdateFood.setDescr(edDecription.getText().toString());
                UpdateFood.setPrice(edPrice.getText().toString());
                food.child(id).setValue(UpdateFood);
                Intent Management = new Intent(AdminUpdatefoodActivity.this,ManagementActivity.class);
                startActivity(Management);
                finish();
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                food.child(id).removeValue();
                Intent Management = new Intent(AdminUpdatefoodActivity.this,ManagementActivity.class);
                startActivity(Management);
                finish();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.typefood_item,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.food:
                Type="Đồ ăn";
                return true;
            case R.id.water:
                Type="Nước";
                return true;
            case R.id.snack:
                Type="Đồ ăn vặt";
                return true;
            case R.id.cake:
                Type="Bánh";
                return true;
            default:
                return true;
        }
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
    private void uploadImage(Food UpdateFood) {

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
                            Toast.makeText(AdminUpdatefoodActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    UpdateFood.setImage(uri.toString());
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AdminUpdatefoodActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
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
