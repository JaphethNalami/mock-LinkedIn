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

public class register extends AppCompatActivity {

Button Register;
EditText Username,Email,Password,ConfirmPassword;
TextView Link;
FirebaseAuth mAuth;
ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //binding
        mAuth = FirebaseAuth.getInstance();
        Register = findViewById(R.id.register);
        Email = findViewById(R.id.mail);
        Password = findViewById(R.id.password);
        ConfirmPassword = findViewById(R.id.confirm_password);

        Link = findViewById(R.id.linkedin);
        progressBar = new ProgressDialog(this);

        //home button
        Link.setOnClickListener(View -> {
            Intent intent = new Intent(register.this, login.class);
            startActivity(intent);
        });

        //button click
        Register.setOnClickListener(View ->{

            progressBar.setMessage("Registration in Progress...");
            progressBar.setTitle("Registration");
            progressBar.setCanceledOnTouchOutside(false);
            progressBar.show();


            String email = Email.getText().toString();
            String password = Password.getText().toString();
            String confirmPassword = ConfirmPassword.getText().toString();

            if(!isValidEmail(email)){
                Email.setError("Please enter your email");
                Email.requestFocus();
            } else if (password.isEmpty()|| password.length()<3) {
                Password.setError("Please enter your password");
                Password.requestFocus();
            } else if (confirmPassword.isEmpty()) {
                ConfirmPassword.setError("Please confirm your password");
                ConfirmPassword.requestFocus();
            } else if (!password.equals(confirmPassword)) {
                ConfirmPassword.setError("Passwords do not match");
                ConfirmPassword.requestFocus();
            }  else {
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful(
                                )) {
                                    progressBar.dismiss();
                                    sendUserToNextActivity();
                                    Toast.makeText(register.this, "Registration successful.",
                                            Toast.LENGTH_SHORT).show();
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    progressBar.dismiss();
                                    Toast.makeText(register.this, "Authentication failed.",
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
        Intent intent = new Intent(register.this, details.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}