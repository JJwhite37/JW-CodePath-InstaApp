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

import com.example.instantsnapapp.R;
import com.example.instantsnapapp.models.Post;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class CommentFragment extends Fragment {
    public static final String TAG = "CommentFragment";
    private Post posts;
    private Button btnPost;
    private EditText etComment;

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

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            posts = bundle.getParcelable("Post");
        }

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
    private void saveComment(ParseUser user, Post posts, String comment){
        ParseObject newComment = new ParseObject("Comment");
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
                Fragment someFragment = new PostDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("Post", posts);
                someFragment.setArguments(bundle);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.flContainer, someFragment );
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}
