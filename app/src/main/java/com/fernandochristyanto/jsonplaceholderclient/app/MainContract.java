package com.fernandochristyanto.jsonplaceholderclient.app;

import com.fernandochristyanto.jsonplaceholderclient.model.Post;

import java.util.List;

public interface MainContract {
    interface View {
        void showPosts(List<Post> posts);
        void showError(String errMsg);
    }
    
    interface Presenter {
        void getPosts();
    }
}
