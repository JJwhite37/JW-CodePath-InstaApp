package com.example.instantsnapapp.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instantsnapapp.R;
import com.example.instantsnapapp.adapters.ProfileAdapter;
import com.example.instantsnapapp.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class UserFeedFragment extends Fragment {
    public static final String TAG = "UserFeedActivity";
    private RecyclerView rvProfileFeed;
    private ImageView ivPicProfile;
    private TextView tvProfileName;
    private List<Post> profilePosts;
    private ProfileAdapter adapter;
    private ParseUser currentUser = ParseUser.getCurrentUser();
    private ParseUser userName;

    public UserFeedFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //setContentView(R.layout.activity_user_feed);

/*
        tvProfileName = view.findViewById(R.id.tvProfileName);
        ivPicProfile = view.findViewById(R.id.ivPicProfile);
        rvProfileFeed =view.findViewById(R.id.rvProfileFeed);

        profilePosts = new ArrayList<>();
        adapter = new ProfileAdapter(getContext(), profilePosts);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        rvProfileFeed.setLayoutManager(gridLayoutManager);
        rvProfileFeed.setAdapter(adapter);

        //Post posts = getIntent().getParcelableExtra("Posts");

        tvProfileName.setText(posts.getUser().getUsername());
        userName = posts.getUser();


        Glide.with(getContext())
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
 */
    }
}
