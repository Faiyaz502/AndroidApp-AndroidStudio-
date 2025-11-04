package com.faiyaz.myapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.faiyaz.myapplication.productsUI.ProductAddActivity;
import com.faiyaz.myapplication.productsUI.ProductListActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    EditText etUsername , etPassword;

    Button btnLogin;

    Button btnChooseColor;

    Button btnSignup;

    Button Add ;

    Button All ;




    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPass);
        btnLogin = findViewById(R.id.etLogin);

        FloatingActionButton fab = findViewById(R.id.fab1);

        btnSignup = findViewById(R.id.signup);


        Add = findViewById(R.id.add);
        All = findViewById(R.id.all);



        Add.setOnClickListener(v->{

            Intent intent = new Intent(MainActivity.this, ProductAddActivity.class);
            startActivity(intent);

        });





        btnSignup.setOnClickListener(v->{

//            Toast.makeText(MainActivity.this,"Signup !!!!!",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(intent);

        });


        All.setOnClickListener(v->{

//            Toast.makeText(MainActivity.this,"Signup !!!!!",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
            startActivity(intent);

        });





        fab.setOnClickListener(v ->{

            Toast.makeText(MainActivity.this,"Calling !!!!!",Toast.LENGTH_SHORT).show();

        });




        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();


                if(username.isEmpty()||password.isEmpty()){

//                    Toast.makeText(MainActivity.this,"Please fill the All field ",Toast.LENGTH_SHORT).show();
                    new AlertDialog.Builder(MainActivity.this).setTitle("Filling Field")
                            .setMessage("are you sure to you want to delte this?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton("Yes",((dialog, which) -> {
                                Toast.makeText(MainActivity.this, "deleted",Toast.LENGTH_SHORT).show();
                            })).setNegativeButton("No",((dialog, which) -> {
                                Snackbar.make(findViewById(android.R.id.content),"Message Not deleted",Snackbar.LENGTH_SHORT).show();
                            })).show();

                } else if (username.equals("admin")&&password.equals("1234")) {
                    Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                }else{

//                    Snackbar.make(findViewById(android.R.id.content),"Message deleted",Snackbar.LENGTH_SHORT)
//                            .setAction("Undo", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    Toast.makeText(MainActivity.this,"Action Undo ",Toast.LENGTH_SHORT).show();
//                                }
//                            })
//                            .show();


                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.custom_toast,null);

                    TextView text = layout.findViewById(R.id.toast_text);
                    text.setText("Hellow Fahim ! Custom Toast Works");

                    Toast toast= new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();




                }


            }
        });







        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}