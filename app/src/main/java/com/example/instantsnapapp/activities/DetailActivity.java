package com.example.instantsnapapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import com.bumptech.glide.Glide;
import com.example.instantsnapapp.R;
import com.example.instantsnapapp.models.Post;
import com.parse.ParseUser;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class DetailActivity extends AppCompatActivity {
    public static final String TAG = "DetailActivity";
    private TextView tvUserName;
    private TextView tvPostDesc;
    private ImageView ivProfilePic;
    private ImageView ivPostPic;
    private ParseUser userName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tvUserName = findViewById(R.id.tvUserName);
        tvPostDesc = findViewById(R.id.tvPostDesc);
        ivProfilePic = findViewById(R.id.ivProfilePic);
        ivPostPic = findViewById(R.id.ivPostPic);


        Post posts = getIntent().getParcelableExtra("Posts");

        tvUserName.setText(posts.getUser().getUsername());
        tvPostDesc.setText(posts.getDescription());
        Glide.with(DetailActivity.this)
                .load(posts.getUser().getParseFile("profilePic").getUrl())
                .transform(new CropCircleTransformation())
                .into(ivProfilePic);
        Glide.with(DetailActivity.this)
                .load(posts.getImage().getUrl())
                .into(ivPostPic);

        userName = posts.getUser();

        tvUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (posts.getUser().getUsername().equals(ParseUser.getCurrentUser().getUsername())) {
                    Intent i = new Intent(DetailActivity.this, ProfileActivity.class);
                    i.putExtra("Posts", posts);

                    Pair<View, String> p1 = Pair.create(ivProfilePic, "pic");
                    Pair<View, String> p2 = Pair.create(tvUserName, "userName");

                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(DetailActivity.this, p1, p2);
                    startActivity(i, options.toBundle());
                } else {
                    Intent i = new Intent(DetailActivity.this, UserFeedActivity.class);
                    i.putExtra("Posts", posts);

                    Pair<View, String> p1 = Pair.create(ivProfilePic, "pic");
                    Pair<View, String> p2 = Pair.create(tvUserName, "userName");

                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(DetailActivity.this, p1, p2);
                    startActivity(i, options.toBundle());
                }
            }
        });

        ivProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (posts.getUser().getUsername().equals(ParseUser.getCurrentUser().getUsername())) {
                    Intent i = new Intent(DetailActivity.this, ProfileActivity.class);
                    i.putExtra("Posts", posts);

                    Pair<View, String> p1 = Pair.create(ivProfilePic, "pic");
                    Pair<View, String> p2 = Pair.create(tvUserName, "userName");

                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(DetailActivity.this, p1, p2);
                    startActivity(i, options.toBundle());
                } else {
                    Intent i = new Intent(DetailActivity.this, UserFeedActivity.class);
                    i.putExtra("Posts", posts);

                    Pair<View, String> p1 = Pair.create(ivProfilePic, "pic");
                    Pair<View, String> p2 = Pair.create(tvUserName, "userName");

                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(DetailActivity.this, p1, p2);
                    startActivity(i, options.toBundle());
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
