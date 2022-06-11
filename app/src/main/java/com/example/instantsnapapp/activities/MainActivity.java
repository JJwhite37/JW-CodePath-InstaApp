package com.example.instantsnapapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.instantsnapapp.R;
import com.example.instantsnapapp.fragments.FeedFragment;
import com.example.instantsnapapp.fragments.FriendsListFragment;
import com.example.instantsnapapp.fragments.PostFragment;
import com.example.instantsnapapp.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    final FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bnMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bnMain = findViewById(R.id.bnMain);
        fragmentManager.beginTransaction().replace(R.id.flContainer,new FeedFragment()).commit();

        bnMain.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()){
                    case R.id.bnHome:
                        fragment = new FeedFragment();
                        break;
                    case R.id.bnPost:
                        fragment = new PostFragment();
                        break;
                    case R.id.bnProfile:
                        fragment = new ProfileFragment();
                        break;
                    case R.id.bnFriendList:
                        fragment = new FriendsListFragment();
                        break;
                    default:
                        fragment = new FeedFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer,fragment).commit();
                return true;
            }
        });
    }
}