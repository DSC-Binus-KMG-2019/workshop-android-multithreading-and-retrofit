package com.fernandochristyanto.jsonplaceholderclient.app;

import com.fernandochristyanto.jsonplaceholderclient.model.Post;
import com.fernandochristyanto.jsonplaceholderclient.proxy.PostClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MainPresenter implements MainContract.Presenter {
    private final MainContract.View view;
    private final PostClient postClient;
    
    public MainPresenter(MainContract.View view, PostClient postClient) {
        this.view = view;
        this.postClient = postClient;
    }
    
    @Override
    public void getPosts () {
        final Call<List<Post>> postsCall = postClient.getPosts();
        
        // Asynchronously send the request and notify callback of its response
        postsCall.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse (Call<List<Post>> call, Response<List<Post>> response) {
                if(response.body() != null) {
                    final List<Post> posts = response.body();
                    Timber.d("Fetched %d posts", posts.size());
                    view.showPosts(posts);
                }
            }
    
            @Override
            public void onFailure (Call<List<Post>> call, Throwable t) {
                Timber.e(t);
                view.showError(t.getMessage());
            }
        });
    }
}
