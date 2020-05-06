package com.fernandochristyanto.jsonplaceholderclient.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.fernandochristyanto.jsonplaceholderclient.R;
import com.fernandochristyanto.jsonplaceholderclient.model.Post;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private List<Post> posts = Collections.emptyList();
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_post, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder (@NonNull ViewHolder holder, int position) {
        holder.bind(posts.get(position));
    }
    
    @Override
    public int getItemCount () {
        return posts.size();
    }
    
    public void updatePosts (final List<Post> newPosts) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new PostsDiffCallback(this.posts, newPosts));
        this.posts = newPosts;
        diffResult.dispatchUpdatesTo(this);
    }
    
    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView (R.id.tv_title)
        TextView tvTitle;
        @BindView (R.id.tv_body)
        TextView tvBody;
        
        ViewHolder (@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        
        void bind (final Post post) {
            tvTitle.setText(post.getTitle());
            tvBody.setText(post.getBody());
        }
    }
}
