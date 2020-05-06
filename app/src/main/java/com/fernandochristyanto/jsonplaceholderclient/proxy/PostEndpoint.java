package com.fernandochristyanto.jsonplaceholderclient.proxy;

import com.fernandochristyanto.jsonplaceholderclient.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostEndpoint {
    @GET("posts")
    Call<List<Post>> getPosts();
}
