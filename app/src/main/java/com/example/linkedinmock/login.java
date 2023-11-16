package com.example.linkedinmock;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class login extends AppCompatActivity {


    Button login;
    EditText Email, password;
    TextView SignUp,googleLogin;

    private FirebaseAuth mAuth;
    ProgressDialog progressBar;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        login = findViewById(R.id.login);
        SignUp = findViewById(R.id.register);
        Email = findViewById(R.id.username);
        password = findViewById(R.id.password);
        googleLogin = findViewById(R.id.google);
        mAuth = FirebaseAuth.getInstance();
        progressBar = new ProgressDialog(this);



        SignUp.setOnClickListener(View -> {
            Intent intent = new Intent(login.this, register.class);
            startActivity(intent);
        });

        login.setOnClickListener(View -> {

            progressBar.setMessage("Registration in Progress...");
            progressBar.setTitle("Registration");
            progressBar.setCanceledOnTouchOutside(false);
            progressBar.show();


            String email = Email.getText().toString();
            String Password = password.getText().toString();


            if(!isValidEmail(email)){
                Email.setError("Please enter your email");
                Email.requestFocus();
                progressBar.dismiss();
            } else if (Password.isEmpty()|| password.length()<3) {
                password.setError("Please enter your password");
                password.requestFocus();
                progressBar.dismiss();
            }   else {
                mAuth.signInWithEmailAndPassword(email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful(
                        )) {
                            progressBar.dismiss();
                            sendUserToNextActivity();
                            Toast.makeText(login.this, "Login successful.",
                                    Toast.LENGTH_SHORT).show();
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            progressBar.dismiss();
                            Toast.makeText(login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }

        });
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }
    private void sendUserToNextActivity() {
        Intent intent = new Intent(login.this, homepage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



}