package com.example.instantsnapapp.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.List;

@ParseClassName("User")
public class User extends ParseObject {
    public static final String KEY_FRIENDSLIST ="friendsList";

    public User() {

    }

    public List<String> getFriendsList() {
        return  getList(KEY_FRIENDSLIST);
    }

}
