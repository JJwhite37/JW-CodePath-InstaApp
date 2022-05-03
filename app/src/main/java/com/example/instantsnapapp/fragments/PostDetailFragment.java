package com.example.instantsnapapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.instantsnapapp.R;
import com.example.instantsnapapp.models.Post;
import com.parse.ParseUser;

import java.io.File;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class PostDetailFragment extends Fragment {
    public static final String TAG = "DetailActivity";
    private TextView tvUserName;
    private TextView tvPostDesc;
    private ImageView ivProfilePic;
    private ImageView ivPostPic;
    private ParseUser userName;
    private Post posts;

    public PostDetailFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvUserName = view.findViewById(R.id.tvUserName);
        tvPostDesc = view.findViewById(R.id.tvPostDesc);
        ivProfilePic = view.findViewById(R.id.ivProfilePic);
        ivPostPic = view.findViewById(R.id.ivPostPic);


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            posts = bundle.getParcelable("Post");
        }

        tvUserName.setText(posts.getUser().getUsername());
        tvPostDesc.setText(posts.getDescription());
        Glide.with(getContext())
                .load(posts.getUser().getParseFile("profilePic").getUrl())
                .transform(new CropCircleTransformation())
                .into(ivProfilePic);
        Glide.with(getContext())
                .load(posts.getImage().getUrl())
                .into(ivPostPic);

        userName = posts.getUser();

        tvUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (posts.getUser().getUsername().equals(ParseUser.getCurrentUser().getUsername())) {

                } else {
                    Fragment someFragment = new UserFeedFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("Post", posts);
                    someFragment.setArguments(bundle);
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.flContainer, someFragment );
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });

        ivProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (posts.getUser().getUsername().equals(ParseUser.getCurrentUser().getUsername())) {

                } else {

                }
            }
        });

    }
}
