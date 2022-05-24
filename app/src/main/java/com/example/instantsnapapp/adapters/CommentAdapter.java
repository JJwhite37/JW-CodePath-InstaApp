package com.example.instantsnapapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instantsnapapp.R;
import com.example.instantsnapapp.models.Comment;
import com.example.instantsnapapp.models.Post;
import com.parse.ParseException;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{
    public static final String TAG = "CommentAdapter";
    private final Context context;
    private List<Comment> comments;

    public CommentAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }
    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View timelineView = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new CommentAdapter.ViewHolder(timelineView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
        Comment comment = comments.get(position);
        try {
            holder.bind(comment);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivCommentPic;
        private TextView tvCommentUser;
        private TextView tvCommentText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCommentPic = itemView.findViewById(R.id.ivCommentPic);
            tvCommentUser = itemView.findViewById(R.id.tvCommentUser);
            tvCommentText = itemView.findViewById(R.id.tvCommentText);
        }

        public void bind(Comment comments) throws ParseException {
            tvCommentText.setText(comments.getComment());
            tvCommentUser.setText(comments.getUser().fetchIfNeeded().getString("username"));
            Glide.with(context)
                    .load(comments.getUser().fetchIfNeeded().getParseFile("profilePic").getUrl())
                    .transform(new CropCircleTransformation())
                    .into(ivCommentPic);
        }
    }
}
