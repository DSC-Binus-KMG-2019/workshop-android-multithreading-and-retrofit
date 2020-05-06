package com.fernandochristyanto.jsonplaceholderclient.app;

import androidx.recyclerview.widget.DiffUtil;

import com.fernandochristyanto.jsonplaceholderclient.model.Post;

import java.util.List;

public class PostsDiffCallback extends DiffUtil.Callback {
    private final List<Post> oldList;
    private final List<Post> newList;
    
    public PostsDiffCallback (List<Post> oldList, List<Post> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }
    
    @Override
    public int getOldListSize () {
        return oldList.size();
    }
    
    @Override
    public int getNewListSize () {
        return newList.size();
    }
    
    @Override
    public boolean areItemsTheSame (int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
    }
    
    @Override
    public boolean areContentsTheSame (int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}