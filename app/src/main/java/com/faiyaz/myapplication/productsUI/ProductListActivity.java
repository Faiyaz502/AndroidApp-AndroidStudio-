package com.faiyaz.myapplication.productsUI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faiyaz.myapplication.R;
import com.faiyaz.myapplication.dbUtil.ProductAdapter;
import com.faiyaz.myapplication.dbUtil.ProductUtil;
import com.faiyaz.myapplication.entity.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ProductListActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private ProductUtil productDao;

    private FloatingActionButton fabAdd;

    private List<Product> productList;




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_list);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        recyclerView = findViewById(R.id.recyclerView);
        fabAdd = findViewById(R.id.fabAdd);
        productDao = new ProductUtil(this);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fabAdd.setOnClickListener(v->{


            Intent intent = new Intent(ProductListActivity.this,ProductAddActivity.class);

            startActivity(intent);



        });








    }


    private void loadProduct(){

        productList = productDao.getAllProducts();
        adapter = new ProductAdapter(this,productList);

        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProduct();
    }
}