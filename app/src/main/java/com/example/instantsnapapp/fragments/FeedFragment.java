package com.example.instantsnapapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.instantsnapapp.EndlessRecyclerViewScrollListener;
import com.example.instantsnapapp.R;
import com.example.instantsnapapp.activities.MainActivity;
import com.example.instantsnapapp.adapters.FeedAdapter;
import com.example.instantsnapapp.models.Post;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class FeedFragment extends Fragment {
    public static final String TAG = "MainActivity";
    private RecyclerView rvFeed;
    private FeedAdapter adapter;
    private List<Post> allPosts;
    private TextView tvUserName;
    private ImageView ivProfilePic;
    private ImageView ivPostPic;
    private SwipeRefreshLayout scFeed;
    private EndlessRecyclerViewScrollListener scrollListener;
    private int skipNum = 1;
    private BottomNavigationView bnMain;


    public FeedFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvFeed = view.findViewById(R.id.rvFeed);
        scFeed = view.findViewById(R.id.scFeed);
        bnMain = view.findViewById(R.id.bnMain);

        allPosts = new ArrayList<>();
        adapter = new FeedAdapter(getContext(), allPosts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvFeed.setLayoutManager(linearLayoutManager);
        rvFeed.setAdapter(adapter);
        retrieveHomeFeed();

        adapter.setOnItemClickListener(new FeedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                //ivPostPic = itemView.findViewById(R.id.ivPostPic);
                //tvUserName = itemView.findViewById(R.id.tvUserName);
                //ivProfilePic = itemView.findViewById(R.id.ivProfilePic);

                Fragment someFragment = new PostDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("Post", allPosts.get(position));
                someFragment.setArguments(bundle);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.flContainer, someFragment );
                transaction.addToBackStack(null);
                transaction.commit();



            }
        });

        scFeed.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveHomeFeed();
            }
        });

        scFeed.setColorSchemeResources(android.R.color.holo_blue_light);

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadNextPosts();
            }
        };
        rvFeed.addOnScrollListener(scrollListener);
    }

    private void loadNextPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.orderByDescending("createdAt");
        query.setSkip(20 * skipNum);
        skipNum++;
        query.setLimit(20);
        query.include(Post.KEY_USER);
        query.include(Post.KEY_IMAGE);
        query.include(Post.KEY_CREATEDAT);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error during query", e);
                    return;
                }
                adapter.addAll(posts);
            }
        });
    }

    private void retrieveHomeFeed() {
        skipNum = 1;
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.orderByDescending("createdAt");
        query.setLimit(20);
        query.include(Post.KEY_USER);
        query.include(Post.KEY_IMAGE);
        query.include(Post.KEY_CREATEDAT);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error during query", e);
                    return;
                }
                adapter.clear();
                adapter.addAll(posts);
                scFeed.setRefreshing(false);
            }
        });
    }
}
