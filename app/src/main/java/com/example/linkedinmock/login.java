package com.example.linkedinmock;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class login extends AppCompatActivity {

    // This is the login page. It is the first page that the user sees when they open the app.
    // It is a simple page with a logo, a text field for the user to enter their email or phone number,
    // a text field for the user to enter their password, and a button to log in.
    // There is also a link to the sign up page if the user does not have an account.

    Button loginButton, signUpButton;
    EditText emailOrPhone, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // This is the login button. When the user clicks on it, it will log them in.
        loginButton = findViewById(R.id.login);
        signUpButton = findViewById(R.id.register);

    }
}