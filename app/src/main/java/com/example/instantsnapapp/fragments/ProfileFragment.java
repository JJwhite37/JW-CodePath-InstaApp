package com.example.instantsnapapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instantsnapapp.R;
import com.example.instantsnapapp.activities.LoginActivity;
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

public class ProfileFragment extends Fragment {
    public static final String TAG = "ProfileActivity";
    private Button btnSignout;
    private RecyclerView rvProfile;
    private ImageView ivPicProfile;
    private TextView tvProfileName;
    private List<Post> profilePosts;
    private ProfileAdapter adapter;
    private ParseUser currentUser = ParseUser.getCurrentUser();

    public ProfileFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnSignout = view.findViewById(R.id.btnSignout);
        tvProfileName = view.findViewById(R.id.tvProfileName);
        ivPicProfile = view.findViewById(R.id.ivPicProfile);
        rvProfile = view.findViewById(R.id.rvProfile);


        profilePosts = new ArrayList<>();
        adapter = new ProfileAdapter(getContext(), profilePosts);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        rvProfile.setLayoutManager(gridLayoutManager);
        rvProfile.setAdapter(adapter);
        retrieveProfileFeed();

        adapter.setOnItemClickListener(new FeedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Fragment someFragment = new PostDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("Post", profilePosts.get(position));
                someFragment.setArguments(bundle);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.flContainer, someFragment );
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        tvProfileName.setText(currentUser.getUsername());

        Glide.with(getContext())
                .load(currentUser.getParseFile("profilePic").getUrl())
                .transform(new CropCircleTransformation())
                .into(ivPicProfile);


        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                //ParseUser currentUser = ParseUser.getCurrentUser();
                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
            }
        });

        ivPicProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment someFragment = new ProfilePicFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.flContainer, someFragment );
                transaction.addToBackStack(null);
                transaction.commit();
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
