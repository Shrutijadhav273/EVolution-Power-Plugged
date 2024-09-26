package com.example.evolutionchargingstation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Login extends AppCompatActivity {
    EditText loginUser,loginPassword;
    Button loginButton;
    TextView signupRedirectText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginUser=findViewById(R.id.log_name);
        loginPassword=findViewById(R.id.log_password);
        signupRedirectText=findViewById(R.id.signupRedirectText);
        loginButton=findViewById(R.id.log_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateUser() | !validatePassword())
                {

                }else{
                    checkUser();
                }

            }
        });

        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Login.this,Signup.class);
                startActivity(intent);
            }
        });
    }

    public boolean validateUser()
    {
        String val=loginUser.getText().toString();
        if(val.isEmpty()){
            loginUser.setError("username cannot empty");
            return false;
        }
        else {
            loginUser.setError(null);
            return true;
        }
    }

    public boolean validatePassword()
    {
        String val=loginPassword.getText().toString();
        if(val.isEmpty()){
            loginPassword.setError("password cannot empty");
            return false;
        }
        else {
            loginPassword.setError(null);
            return true;
        }
    }

    public void checkUser()
    {
        String userUsername = loginUser.getText().toString().trim();
        String userPassword = loginPassword.getText().toString().trim();

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("user");

        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    loginUser.setError(null);
                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);

                    if(Objects.equals(passwordFromDB,userPassword))
                    {
                        loginUser.setError(null);
                        Intent intent =new Intent(Login.this, MainActivity1.class);
                        startActivity(intent);
                    }
                    else {
                        loginPassword.setError("invalid credential");
                        loginPassword.requestFocus();
                    }

                }
                else
                {
                    loginUser.setError("user does not exits");
                    loginUser.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}