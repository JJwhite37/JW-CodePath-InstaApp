package com.example.instantsnapapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instantsnapapp.R;
import com.example.instantsnapapp.adapters.CommentAdapter;
import com.example.instantsnapapp.models.Comment;
import com.example.instantsnapapp.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class CommentFragment extends Fragment {
    public static final String TAG = "CommentFragment";
    private Post posts;
    private Button btnPost;
    private EditText etComment;
    private List<Comment> allComments;
    private RecyclerView rvComments;
    private CommentAdapter commentAdapter;

    // add in loading bar.
    public CommentFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        return inflater.inflate(R.layout.fragment_comment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ParseUser curentUser = ParseUser.getCurrentUser();

        btnPost= view.findViewById(R.id.btnPost);
        etComment= view.findViewById(R.id.etComment);
        rvComments = view.findViewById(R.id.rvComments);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            posts = bundle.getParcelable("Post");
        }

        allComments = new ArrayList<>();
        commentAdapter = new CommentAdapter(getContext(), allComments);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvComments.setLayoutManager(linearLayoutManager);
        rvComments.setAdapter(commentAdapter);
        retrieveComments();

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = etComment.getText().toString();

                if (comment.isEmpty()) {
                    Toast.makeText(getContext(), "no characters where typed", Toast.LENGTH_LONG).show();
                    return;
                } if(comment.length() > 100) {
                    Toast.makeText(getContext(), "Too many characters typed", Toast.LENGTH_LONG).show();
                    return;
                }
                saveComment(curentUser,posts,comment);
            }
        });
    }
    private void retrieveComments() {
        ParseQuery<Comment> query = ParseQuery.getQuery(Comment.class);
        query.whereEqualTo("post", posts);
        query.orderByAscending("createdAt");
        query.include("user");
        query.include("comment");
        query.include("post");
        query.findInBackground(new FindCallback<Comment>() {
            @Override
            public void done(List<Comment> comments, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error during query", e);
                    return;
                }
                allComments.addAll(comments);
                commentAdapter.notifyDataSetChanged();
            }
        });
    }

    private void saveComment(ParseUser user, Post posts, String comment){

        ParseObject newComment = ParseObject.create("Comment");
        newComment.put("comment",comment);
        newComment.put("post",posts);
        newComment.put("user",user);
        newComment.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error during Post", e);
                    return;
                }
                Log.i(TAG, "Comment was a success");
                Toast.makeText(getContext(), "Comment was successful", Toast.LENGTH_SHORT).show();
                allComments.clear();
                commentAdapter.notifyDataSetChanged();
                retrieveComments();
            }
        });
    }
}
