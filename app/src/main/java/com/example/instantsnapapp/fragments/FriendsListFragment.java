package com.example.instantsnapapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instantsnapapp.R;
import com.example.instantsnapapp.adapters.FeedAdapter;
import com.example.instantsnapapp.adapters.FriendAdapter;
import com.example.instantsnapapp.adapters.ProfileAdapter;
import com.example.instantsnapapp.models.Post;
import com.example.instantsnapapp.models.User;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class FriendsListFragment extends Fragment {
    public static final String TAG = "FriendsListFragment";
    private RecyclerView rvFriendList;
    private List<User> Friends;
    private FriendAdapter adapter;

    public FriendsListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        return inflater.inflate(R.layout.fragment_friends_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        rvFriendList = view.findViewById(R.id.rvFriendList);

        Friends = new ArrayList<>();
        adapter = new FriendAdapter(getContext(), Friends);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvFriendList.setLayoutManager(linearLayoutManager);
        rvFriendList.setAdapter(adapter);
        try {
            retrieveFriends();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void retrieveFriends() throws ParseException {
        ParseUser user = ParseUser.getCurrentUser();
        List<ParseUser> friendList = user.getList("friendsList");
        Friends.addAll(User.fromFriendList(friendList));
        adapter.notifyDataSetChanged();
    }
}
