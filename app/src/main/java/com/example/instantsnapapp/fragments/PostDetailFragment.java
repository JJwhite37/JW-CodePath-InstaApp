package com.example.instantsnapapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instantsnapapp.R;
import com.example.instantsnapapp.adapters.CommentAdapter;
import com.example.instantsnapapp.adapters.FeedAdapter;
import com.example.instantsnapapp.models.Comment;
import com.example.instantsnapapp.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class PostDetailFragment extends Fragment {
    public static final String TAG = "DetailActivity";
    private TextView tvUserName;
    private TextView tvPostDesc;
    private TextView tvCount;
    private ImageView ivProfilePic;
    private ImageView ivPostPic;
    private ImageView ivHeart;
    private ImageView ivComment;
    private ParseUser postUser;
    private ParseUser curUser;
    private Post posts;
    private int likeCount;
    private List<String> likedList;

    public PostDetailFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        curUser = ParseUser.getCurrentUser();

        tvUserName = view.findViewById(R.id.tvUserName);
        tvPostDesc = view.findViewById(R.id.tvPostDesc);
        tvCount = view.findViewById(R.id.tvCount);
        ivProfilePic = view.findViewById(R.id.ivProfilePic);
        ivPostPic = view.findViewById(R.id.ivPostPic);
        ivHeart = view.findViewById(R.id.ivHeart);
        ivComment = view.findViewById(R.id.ivComment);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            posts = bundle.getParcelable("Post");
        }

        likeCount = posts.getLikedList().size();
        tvCount.setText(String.valueOf(likeCount));

        tvUserName.setText(posts.getUser().getUsername());
        tvPostDesc.setText(posts.getDescription());
        Glide.with(getContext())
                .load(posts.getUser().getParseFile("profilePic").getUrl())
                .transform(new CropCircleTransformation())
                .into(ivProfilePic);
        Glide.with(getContext())
                .load(posts.getImage().getUrl())
                .into(ivPostPic);

        postUser = posts.getUser();

        if (posts.getLikedList().contains(curUser.getUsername())) {
            ivHeart.setImageResource(R.drawable.ufi_heart_active);
        }

        tvUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (posts.getUser().getUsername().equals(ParseUser.getCurrentUser().getUsername())) {
                    Fragment someFragment = new ProfileFragment();
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.flContainer, someFragment );
                    transaction.addToBackStack(null);
                    transaction.commit();
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
                    Fragment someFragment = new ProfileFragment();
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.flContainer, someFragment );
                    transaction.addToBackStack(null);
                    transaction.commit();
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

        ivHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!posts.getLikedList().contains(curUser.getUsername())) {
                    posts.addUnique("userLiked", curUser.getUsername());
                    posts.saveInBackground();
                    likeCount++;
                    tvCount.setText(String.valueOf(likeCount));
                    ivHeart.setImageResource(R.drawable.ufi_heart_active);
                }
            }
        });

        ivComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment someFragment = new CommentFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("Post", posts);
                someFragment.setArguments(bundle);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.flContainer, someFragment );
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }


}
