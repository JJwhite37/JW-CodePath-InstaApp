package com.example.instantsnapapp.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Comment")
public class Comment extends ParseObject {
    public static final String KEY_COMMENT ="comment";
    public static final String KEY_POST ="post";
    public static final String KEY_USER ="user";

    public Comment() {

    }

    public String getComment() {
        return  getString(KEY_COMMENT);
    }
    public ParseObject getPost() {
        return  getParseObject(KEY_POST);
    }
    public ParseUser getUser() {
        return  getParseUser(KEY_USER);
    }

}
