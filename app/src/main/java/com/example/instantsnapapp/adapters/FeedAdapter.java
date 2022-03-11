package com.example.instantsnapapp.adapters;

import android.content.Context;
import android.util.Log;
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
import com.example.instantsnapapp.models.User;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder>{
    public static final String TAG = "FeedAdapter";
    private final Context context;
    private List<Post> posts;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public FeedAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View timelineView = LayoutInflater.from(context).inflate(R.layout.item_posts, parent, false);
        return new ViewHolder(timelineView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedAdapter.ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Post> postList) {
        posts.addAll(postList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvUserName;
        //private TextView tvPostDesc;
        private ImageView ivProfilePic;
        private ImageView ivPostPic;

        public ViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            //tvPostDesc = itemView.findViewById(R.id.tvPostDesc);
            ivProfilePic = itemView.findViewById(R.id.ivProfilePic);
            ivPostPic = itemView.findViewById(R.id.ivPostPic);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }

        public void bind(Post posts) {
            tvUserName.setText(posts.getUser().getUsername());
            //tvPostDesc.setText(posts.getDescription());
            Glide.with(context)
                    .load(posts.getUser().getParseFile("profilePic").getUrl())
                    .transform(new CropCircleTransformation())
                    .into(ivProfilePic);
            Glide.with(context)
                    .load(posts.getImage().getUrl())
                    .into(ivPostPic);
        }
    }
}
