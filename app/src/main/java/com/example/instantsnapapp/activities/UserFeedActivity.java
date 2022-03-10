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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instantsnapapp.R;
import com.example.instantsnapapp.adapters.ProfileAdapter;
import com.example.instantsnapapp.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class UserFeedActivity extends AppCompatActivity {
    public static final String TAG = "UserFeedActivity";
    private RecyclerView rvProfileFeed;
    private ImageView ivPicProfile;
    private TextView tvProfileName;
    private List<Post> profilePosts;
    private ProfileAdapter adapter;
    private ParseUser currentUser = ParseUser.getCurrentUser();
    private ParseUser userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feed);


        tvProfileName = findViewById(R.id.tvProfileName);
        ivPicProfile = findViewById(R.id.ivPicProfile);
        rvProfileFeed = findViewById(R.id.rvProfileFeed);

        profilePosts = new ArrayList<>();
        adapter = new ProfileAdapter(this, profilePosts);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(UserFeedActivity.this, 3);
        rvProfileFeed.setLayoutManager(gridLayoutManager);
        rvProfileFeed.setAdapter(adapter);

        Post posts = getIntent().getParcelableExtra("Posts");

        tvProfileName.setText(posts.getUser().getUsername());
        userName = posts.getUser();


        Glide.with(UserFeedActivity.this)
                .load(posts.getUser().getParseFile("profilePic").getUrl())
                .transform(new CropCircleTransformation())
                .into(ivPicProfile);

        retrieveProfileFeed();
    }

    private void retrieveProfileFeed() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.whereEqualTo("user", userName);
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
