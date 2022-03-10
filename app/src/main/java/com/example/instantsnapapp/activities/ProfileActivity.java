package com.example.instantsnapapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instantsnapapp.R;
import com.example.instantsnapapp.adapters.FeedAdapter;
import com.example.instantsnapapp.adapters.ProfileAdapter;
import com.example.instantsnapapp.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class ProfileActivity extends AppCompatActivity {
    public static final String TAG = "ProfileActivity";
    private ImageView ivNewPost;
    private ImageView ivHome;
    private Button btnSignout;
    private RecyclerView rvProfile;
    private ImageView ivPicProfile;
    private TextView tvProfileName;
    private List<Post> profilePosts;
    private ProfileAdapter adapter;
    private ParseUser currentUser = ParseUser.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnSignout = findViewById(R.id.btnSignout);
        ivNewPost = findViewById(R.id.ivNewPost);
        ivHome = findViewById(R.id.ivHome);
        tvProfileName = findViewById(R.id.tvProfileName);
        ivPicProfile = findViewById(R.id.ivPicProfile);
        rvProfile = findViewById(R.id.rvProfile);

        profilePosts = new ArrayList<>();
        adapter = new ProfileAdapter(this, profilePosts);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ProfileActivity.this, 3);
        rvProfile.setLayoutManager(gridLayoutManager);
        rvProfile.setAdapter(adapter);
        retrieveProfileFeed();

        tvProfileName.setText(currentUser.getUsername());

        Glide.with(ProfileActivity.this)
                .load(currentUser.getParseFile("profilePic").getUrl())
                .transform(new CropCircleTransformation())
                .into(ivPicProfile);


        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                //ParseUser currentUser = ParseUser.getCurrentUser();
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

    private void retrieveProfileFeed() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.whereEqualTo("user",currentUser);
        query.orderByDescending("createdAt");
        query.include(Post.KEY_IMAGE);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error during query", e);
                    return;
                }
                profilePosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
