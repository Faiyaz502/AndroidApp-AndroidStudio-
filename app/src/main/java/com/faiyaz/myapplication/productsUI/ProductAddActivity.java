package com.faiyaz.myapplication.productsUI;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.faiyaz.myapplication.MainActivity;
import com.faiyaz.myapplication.R;
import com.faiyaz.myapplication.dbUtil.ProductUtil;
import com.faiyaz.myapplication.entity.Product;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductAddActivity extends AppCompatActivity {

        private ProductUtil productUtil = new ProductUtil(this);
    private Product product;


    private ImageView img;

    private Uri selectedImageUri;

    private Uri cameraImageUri;

    private ActivityResultLauncher<Intent> imagePickLauncher ;

    private  ActivityResultLauncher<String> permissionLauncher;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_add);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        EditText name ;
        EditText email ;
        EditText quantity ;
        EditText price ;
        Button btnAddProduct;
        Button backBtn;


        name = findViewById(R.id.etPName);
        email = findViewById(R.id.etEmail);
        quantity = findViewById(R.id.etQuantity);
        price = findViewById(R.id.etPrice);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        backBtn = findViewById(R.id.back);
        img = findViewById(R.id.uploadImage);


        //Permission launcher

        permissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if(isGranted) openCamera();
                    else Toast.makeText(this,"Camera Permission Denied", Toast.LENGTH_SHORT).show();
                });

        imagePickLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null && result.getData().getData() != null) {
                        selectedImageUri = result.getData().getData();

                        // Persist permission
                        final int takeFlags = result.getData().getFlags() &
                                (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        getContentResolver().takePersistableUriPermission(selectedImageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);

                        img.setImageURI(selectedImageUri);
                    } else if (result.getResultCode() == RESULT_OK && cameraImageUri != null) {
                        selectedImageUri = cameraImageUri;
                        img.setImageURI(selectedImageUri);
                    }
                });

        // 📸 Image click dialog
        img.setOnClickListener(v -> showImageSourceDialog());

        // Check if editing
        int productId = getIntent().getIntExtra("ProductID", -1);
        if (productId != -1) {

            product = productUtil.getProductById(productId);
            if (product != null) {
                System.out.println(product);
                name.setText(product.getName());
                email.setText(product.getEmail());
                price.setText(String.valueOf(product.getPrice()));
                quantity.setText(String.valueOf(product.getQuantity()));
                if (product.getImageUri() != null) {
                    selectedImageUri = Uri.parse(product.getImageUri());
                    img.setImageURI(selectedImageUri);
                }
            }


        }







        btnAddProduct.setOnClickListener(v -> {

            String Name = name.getText().toString().trim();
            String Email = email.getText().toString().trim();
            String Price = price.getText().toString();
            String Quantity = quantity.getText().toString();




            saveProduct(Name,Email,Price,Quantity);


            System.out.println(Name+Email+Quantity+Price);

        });

        backBtn.setOnClickListener(v->{


            Intent intent = new Intent(ProductAddActivity.this, MainActivity.class);

            startActivity(intent);


        });










    }



    private void saveProduct(String name , String email, String quantity ,String price){

        int Quantity = Integer.parseInt(quantity);
        Double Price = Double.parseDouble(price);


        if(this.product == null){
            this.product = new Product(0,name,email,Price,Quantity,selectedImageUri != null ? selectedImageUri.toString() : null);
            long id =  productUtil.insert(product);
            if(id>0){
                Toast.makeText(this,"Product Added",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Insert Failed",Toast.LENGTH_SHORT).show();
            }

        }else {

            product.setImageUri(selectedImageUri != null ? selectedImageUri.toString() : null);


        }






    }



    private void showImageSourceDialog() {
        String[] options = {"Camera", "Gallery"};
        new AlertDialog.Builder(this)
                .setTitle("Select Image Source")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        // Camera
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                == PackageManager.PERMISSION_GRANTED) {
                            openCamera();
                        } else {
                            permissionLauncher.launch(Manifest.permission.CAMERA);
                        }
                    } else {
                        // Gallery
                        Intent galleryIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                        galleryIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        galleryIntent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                        galleryIntent.setType("image/*");
                        imagePickLauncher.launch(galleryIntent);
                    }
                })
                .show();
    }

    // 📸 Open Camera
    private void openCamera() {
        try {
            File photoFile = createImageFile();
            cameraImageUri = FileProvider.getUriForFile(
                    this,
                    getPackageName() + ".provider",
                    photoFile
            );
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraImageUri);
            imagePickLauncher.launch(cameraIntent);
        } catch (IOException e) {
            Toast.makeText(this, "Error opening camera", Toast.LENGTH_SHORT).show();
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "IMG_" + timeStamp;
        File storageDir = getExternalFilesDir(null);
        return File.createTempFile(fileName, ".jpg", storageDir);
    }



}