package com.example.instantsnapapp.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class FriendsListFragment extends Fragment {
    public static final String TAG = "FriendsListFragment";
    private RecyclerView rvFriendList;
    private List<User> friends;
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

        friends = new ArrayList<>();
        adapter = new FriendAdapter(getContext(), friends);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvFriendList.setLayoutManager(linearLayoutManager);
        rvFriendList.setAdapter(adapter);
        try {
            retrieveFriends();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        adapter.setOnItemClickListener(new FriendAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                //ivPostPic = itemView.findViewById(R.id.ivPostPic);
                //tvUserName = itemView.findViewById(R.id.tvUserName);
                //ivProfilePic = itemView.findViewById(R.id.ivProfilePic);

                Fragment someFragment = new UserFeedFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("User", Parcels.wrap(friends.get(position)));
                someFragment.setArguments(bundle);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.flContainer, someFragment );
                transaction.addToBackStack(null);
                transaction.commit();



            }
        });

    }

    private void retrieveFriends() throws ParseException {
        ParseUser user = ParseUser.getCurrentUser();
        List<ParseUser> friendList = user.getList("friendsList");
        System.out.print(friendList);
        if(friendList.size() != 0) {
            if (friendList.get(0) != null) {
                friends.addAll(User.fromFriendList(friendList));
                adapter.notifyDataSetChanged();
            }
        }
    }

}
