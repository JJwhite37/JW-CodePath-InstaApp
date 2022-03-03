package com.example.instantsnapapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.instantsnapapp.R;
import com.parse.ParseUser;

public class ProfileActivity extends AppCompatActivity {
    private ImageView ivNewPost;
    private ImageView ivHome;
    private Button btnSignout;
    private TextView tvProfileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnSignout = findViewById(R.id.btnSignout);
        ivNewPost = findViewById(R.id.ivNewPost);
        ivHome = findViewById(R.id.ivHome);
        tvProfileName = findViewById(R.id.tvProfileName);


        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser();
                Intent i = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        ivNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, PostActivity.class);
                startActivity(i);
            }
        });
    }
}
