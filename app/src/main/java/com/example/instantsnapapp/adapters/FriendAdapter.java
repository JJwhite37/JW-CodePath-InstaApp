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
import com.example.instantsnapapp.models.User;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder>{
    public static final String TAG = "FriendAdapter";
    private final Context context;
    private List<User> users;

    private FriendAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(FriendAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public FriendAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }
    @NonNull
    @Override
    public FriendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View timelineView = LayoutInflater.from(context).inflate(R.layout.item_friend, parent, false);
        return new FriendAdapter.ViewHolder(timelineView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendAdapter.ViewHolder holder, int position) {
        User user = users.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void addAll(List<User> friendsList) {
        users.addAll(friendsList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivFriendPic;
        private TextView tvfriendName;

        public ViewHolder(@NonNull View itemView, final FriendAdapter.OnItemClickListener listener) {
            super(itemView);
            ivFriendPic = itemView.findViewById(R.id.ivFriendPic);
            tvfriendName = itemView.findViewById(R.id.tvfriendName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }

        public void bind(User users) {
            tvfriendName.setText(users.userName);
            Glide.with(context)
                    .load(users.profilePicUrl)
                    .transform(new CropCircleTransformation())
                    .into(ivFriendPic);
        }
    }
}
