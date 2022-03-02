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

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder>{
    private final Context context;

    public FeedAdapter(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View timelineView = LayoutInflater.from(context).inflate(R.layout.item_posts, parent, false);
        return new ViewHolder(timelineView);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedAdapter.ViewHolder holder, int position) {
        //Tweets tweet = tweets.get(position);
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvUserName;
        private TextView tvPostDesc;
        private ImageView ivProfilePic;
        private ImageView ivPostPic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvPostDesc = itemView.findViewById(R.id.tvPostDesc);
            ivProfilePic = itemView.findViewById(R.id.ivProfilePic);
            ivPostPic = itemView.findViewById(R.id.ivPostPic);
        }

        public void bind() {
            //tvUserName.setText(tweets.body);
            //tvPostDesc.setText("@" + tweets.user.userName);
            //Glide.with(context)
            //        .load(tweets.user.profilePicUrl)
            //        .transform(new CropCircleTransformation())
            //        .into(ivProfilePic);
            //Glide.with(context)
            //        .load(tweets.user.profilePicUrl)
            //        .into(ivPostPic);
        }
    }
}
