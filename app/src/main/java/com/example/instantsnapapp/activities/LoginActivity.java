package com.example.instantsnapapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.instantsnapapp.R;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "LoginActivity";
    private EditText etUserName;
    private EditText etPassword;
    private Button btnSignUp;
    private Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignIn = findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Sign in was clicked");
                String userName = etUserName.getText().toString();
                String password = etPassword.getText().toString();
                signIn(userName, password);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Sign up was clicked");
                String userName = etUserName.getText().toString();
                String password = etPassword.getText().toString();
                signIn(userName, password);
            }
        });
    }

    private void signIn(String userName, String password){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void signUp(String userName, String password){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
