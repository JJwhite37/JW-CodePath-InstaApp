package com.example.instantsnapapp.fragments;

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
import com.example.instantsnapapp.adapters.FeedAdapter;
import com.example.instantsnapapp.adapters.ProfileAdapter;
import com.example.instantsnapapp.models.Post;
import com.example.instantsnapapp.models.User;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcels;

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
    private Post posts;
    private User user;
    private Button btnAddFriend;
    public String profileUserName;
    public String profilePicUrl;
    private ParseUser profileUser;

    public UserFeedFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        return inflater.inflate(R.layout.fragment_user_feed, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvProfileName = view.findViewById(R.id.tvProfileName);
        ivPicProfile = view.findViewById(R.id.ivPicProfile);
        rvProfileFeed =view.findViewById(R.id.rvProfileFeed);
        btnAddFriend = view.findViewById(R.id.btnAddFriend);
        //check if bundle is coming from detail post or friendlist.
        Bundle bundle = this.getArguments();
        if (bundle.getParcelable("Post") != null) {
            posts = bundle.getParcelable("Post");
            profileUserName = posts.getUser().getUsername();
            profilePicUrl = posts.getUser().getParseFile("profilePic").getUrl();
            profileUser = posts.getUser();
        }
        else if (bundle.getParcelable("User") != null) {
            user = Parcels.unwrap(bundle.getParcelable("User"));
            profileUserName = user.userName;
            profilePicUrl = user.profilePicUrl;
            profileUser = user.userId;
        }

        ParseUser user = ParseUser.getCurrentUser();
        List<ParseUser> friendList = user.getList("friendsList");
        if(friendList.size() != 0) {
            if (friendList.get(0) != null) {
                for (int i = 0; i < friendList.size(); i++) {
                    try {
                        if (friendList.get(i).fetchIfNeeded().getString("username").equals(profileUserName)) {
                            btnAddFriend.setText("Already Friends");
                            break;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        profilePosts = new ArrayList<>();
        adapter = new ProfileAdapter(getContext(), profilePosts);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        rvProfileFeed.setLayoutManager(gridLayoutManager);
        rvProfileFeed.setAdapter(adapter);

        tvProfileName.setText(profileUserName);

        Glide.with(getContext())
                .load(profilePicUrl)
                .transform(new CropCircleTransformation())
                .into(ivPicProfile);

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
    }

    private void retrieveProfileFeed() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.whereEqualTo("user", profileUser);
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

        btnAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser user = ParseUser.getCurrentUser();
                if (!user.getList("friendsList").contains(profileUser)) {
                    user.addUnique("friendsList", profileUser);
                    user.saveInBackground();
                    btnAddFriend.setText("Already Friends");
                }
            }
        });
    }
}
