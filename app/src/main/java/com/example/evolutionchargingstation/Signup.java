package com.example.evolutionchargingstation;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
    EditText signupName,signupEmail,signupPassword,signupusername;

    TextView loginRedirectText;
    Button signup_button;
    FirebaseDatabase database;
    DatabaseReference reference;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupName=findViewById(R.id.signup_name);
        signupEmail=findViewById(R.id.signup_email);
        signupusername=findViewById(R.id.signup_username);
        signupPassword=findViewById(R.id.signup_password);
        signup_button=findViewById(R.id.signup_button);
        loginRedirectText=findViewById(R.id.loginRedirectText);


        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database =FirebaseDatabase.getInstance();
                reference=database.getReference("user");

                String name=signupName.getText().toString();
                String email=signupEmail.getText().toString();
                String username=signupusername.getText().toString();
                String password=signupPassword.getText().toString();

                helperclass helperclass=new helperclass(name,email,username,password);
                reference.child(username).setValue(helperclass);

                Toast.makeText(Signup.this, "signup successfully", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Signup.this,Login.class);
                startActivity(intent);
            }
        });
        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Signup.this,Login.class);
                startActivity(intent);
            }
        });
    }
}