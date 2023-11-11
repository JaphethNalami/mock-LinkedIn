package com.example.linkedinmock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class login extends AppCompatActivity {


    Button login;
    EditText emailOrPhone, password;
    TextView SignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        login = findViewById(R.id.login);
        SignUp = findViewById(R.id.register);
        emailOrPhone = findViewById(R.id.username);
        password = findViewById(R.id.password);



        SignUp.setOnClickListener(View -> {
            Intent intent = new Intent(login.this, register.class);
            startActivity(intent);
        });

    }
}