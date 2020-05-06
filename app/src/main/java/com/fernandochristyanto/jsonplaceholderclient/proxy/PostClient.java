package com.fernandochristyanto.jsonplaceholderclient.proxy;

import com.fernandochristyanto.jsonplaceholderclient.model.Post;

import java.util.List;

import retrofit2.Call;

public interface PostClient {
    Call<List<Post>> getPosts();
}
