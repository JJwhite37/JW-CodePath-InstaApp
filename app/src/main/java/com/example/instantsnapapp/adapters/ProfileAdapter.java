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
import com.example.instantsnapapp.models.Post;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {
    public static final String TAG = "ProfileAdapter";
    private final Context context;
    private List<Post> posts;
    private FeedAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(FeedAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public ProfileAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }
    @NonNull
    @Override
    public ProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View timelineView = LayoutInflater.from(context).inflate(R.layout.item_profile_posts, parent, false);
        return new ProfileAdapter.ViewHolder(timelineView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileAdapter.ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivProfilePost;

        public ViewHolder(@NonNull View itemView, final FeedAdapter.OnItemClickListener listener) {
            super(itemView);
            ivProfilePost = itemView.findViewById(R.id.ivProfilePost);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }

        public void bind(Post posts) {
            Glide.with(context)
                    .load(posts.getImage().getUrl())
                    .into(ivProfilePost);
        }
    }
}
