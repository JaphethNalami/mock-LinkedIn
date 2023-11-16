package com.example.linkedinmock;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class details extends AppCompatActivity {

    Button button;
    EditText firstname,lastname,Bio,Skill,Phone;
    ImageView profilepic;
    TextView upload;
     DatabaseReference rootdatabaseref;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mUser = mAuth.getCurrentUser();

    //image upload
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;

    //image upload
    public void selectImage(View view) {
        // Create an Intent to pick an image
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the selected image URI
            Uri selectedImageUri = data.getData();

            try {
                // Convert the URI to a Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);

                // Set the Bitmap to the ImageView
                profilepic.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Spinner spinner = (Spinner) findViewById(R.id.gender_array);
// Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.gender_array,
                android.R.layout.simple_spinner_item
        );
// Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner.
        spinner.setAdapter(adapter);

        //binding
        button = findViewById(R.id.save);
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        Bio = findViewById(R.id.bio);
        Skill = findViewById(R.id.skills);
        Phone = findViewById(R.id.phone);
        profilepic = findViewById(R.id.imageView);
        upload = findViewById(R.id.upload);

        String email = getIntent().getStringExtra("email");

        rootdatabaseref = FirebaseDatabase.getInstance().getReference();


        button.setOnClickListener(View->{
            String fname = firstname.getText().toString();
            String lname= lastname.getText().toString();
            String genderArray = spinner.getSelectedItem().toString();
            String bio = Bio.getText().toString();
            String skill = Skill.getText().toString();
            String phone = Phone.getText().toString();
            String image = profilepic.toString();

            // Add your conditions here
            if(fname.isEmpty() || lname.isEmpty() || bio.isEmpty() || skill.isEmpty() || phone.isEmpty() || image.isEmpty()){
                Toast.makeText(details.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                return;
            }else if(phone.length()<10){
                Toast.makeText(details.this, "Phone number must be 10 characters", Toast.LENGTH_SHORT).show();
                return;
            }
            if(mUser != null){
                DatabaseReference userref = rootdatabaseref.child("Users").child(mUser.getUid());

                userref.child("firstname").setValue(fname);
                userref.child("lastname").setValue(lname);
                userref.child("bio").setValue(bio);
                userref.child("skills").setValue(skill);
                userref.child("phone").setValue(phone);
                userref.child("gender").setValue(genderArray);
                userref.child("image").setValue(image);

                Intent intent = new Intent(details.this, homepage.class);
                startActivity(intent);

            }
            else{
                Toast.makeText(details.this, "Please login first", Toast.LENGTH_SHORT).show();

            }
        });

    }
}

