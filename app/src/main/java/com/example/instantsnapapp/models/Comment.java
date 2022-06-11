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
    public void setComment(String comment){
        put("comment",comment);
    }
    public ParseObject getPost() {
        return  getParseObject(KEY_POST);
    }
    public void setPost(Post post){
        put("post",post);
    }
    public ParseUser getUser() {
        return  getParseUser(KEY_USER);
    }
    public void setUser(ParseUser user){
        put("user",user);
    }

}
