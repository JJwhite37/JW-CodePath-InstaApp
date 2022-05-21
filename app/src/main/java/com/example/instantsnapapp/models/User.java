package com.example.instantsnapapp.models;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class User{

    public String userName;
    public String profilePicUrl;

    public User(){

    }

    public static User fromFriend(ParseUser friend) throws ParseException {
        User user = new User();

        user.userName = friend.fetchIfNeeded().getString("username");
        user.profilePicUrl = friend.fetchIfNeeded().getParseFile("profilePic").getUrl();

        return user;
    }

    public static List<User> fromFriendList(List<ParseUser> friendList) throws ParseException {
        List<User> users = new ArrayList<User>();
        for(int i = 0; i <friendList.size(); i++) {
            users.add(fromFriend(friendList.get(i)));
        }
        return users;
    }

}
