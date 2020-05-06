package com.fernandochristyanto.jsonplaceholderclient.proxy;

import com.fernandochristyanto.jsonplaceholderclient.model.Post;
import com.fernandochristyanto.jsonplaceholderclient.providers.RetrofitProvider;

import java.util.List;

import retrofit2.Call;

public class PostClientImpl implements PostClient {
    private PostEndpoint postEndpoint = RetrofitProvider.getRetrofit().create(PostEndpoint.class);
    
    public Call<List<Post>> getPosts() {
        return postEndpoint.getPosts();
    }
}
