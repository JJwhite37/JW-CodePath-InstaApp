package com.example.instantsnapapp.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Date;
import java.util.List;

@ParseClassName("Post")
public class Post extends ParseObject {
    public static final String KEY_DESCRIPTION ="Description";
    public static final String KEY_IMAGE ="image";
    public static final String KEY_USER ="user";
    public static final String KEY_CREATEDAT= "createdAt";
    public static final String KEY_LIKELIST="userLiked";

    public Post() {
    }

    public String getDescription() {
        return  getString(KEY_DESCRIPTION);
    }
    public void setDescription(String description){
        put(KEY_DESCRIPTION, description);
    }
    public ParseFile getImage() {
        return  getParseFile(KEY_IMAGE);
    }
    public void setImage(ParseFile parseFile){
        put(KEY_IMAGE, parseFile);
    }
    public ParseUser getUser() {
        return  getParseUser(KEY_USER);
    }
    public void setUser(ParseUser parseUser){
        put(KEY_USER, parseUser);
    }
    public Date getCreatedat() {
        return  getDate(KEY_CREATEDAT);
    }
    public List<String> getLikedList(){
        return getList(KEY_LIKELIST);
    }
    public void setLikedList(List<String> likedList){
        put(KEY_LIKELIST, likedList);
    }


}
