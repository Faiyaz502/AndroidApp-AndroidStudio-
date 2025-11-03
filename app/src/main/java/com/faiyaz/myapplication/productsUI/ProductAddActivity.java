package com.faiyaz.myapplication.productsUI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.faiyaz.myapplication.MainActivity;
import com.faiyaz.myapplication.R;
import com.faiyaz.myapplication.dbUtil.ProductUtil;
import com.faiyaz.myapplication.entity.Product;
import com.google.android.material.color.utilities.QuantizerCelebi;

public class ProductAddActivity extends AppCompatActivity {

    ProductUtil productUtil = new ProductUtil(this);
    private Product product;


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

        name = findViewById(R.id.etName);
        email = findViewById(R.id.etEmail);
        quantity = findViewById(R.id.etQuantity);
        price = findViewById(R.id.etPrice);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        backBtn = findViewById(R.id.back);


        btnAddProduct.setOnClickListener(v->{

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
            this.product = new Product(0,name,email,Price,Quantity);
            long id =  productUtil.insert(product);
            if(id>0){
                Toast.makeText(this,"Product Added",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Insert Failed",Toast.LENGTH_SHORT).show();
            }

        }






    }
}