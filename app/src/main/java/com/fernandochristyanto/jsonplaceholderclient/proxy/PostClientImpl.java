package com.fernandochristyanto.jsonplaceholderclient.proxy;

import com.fernandochristyanto.jsonplaceholderclient.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class PostClientImpl implements PostClient {
    private final PostEndpoint postEndpoint;
    
    public PostClientImpl(Retrofit retrofit) {
        postEndpoint = retrofit.create(PostEndpoint.class);
    }
    
    
    public Call<List<Post>> getPosts() {
        return postEndpoint.getPosts();
    }
}
