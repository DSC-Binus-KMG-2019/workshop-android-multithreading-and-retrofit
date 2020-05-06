package com.fernandochristyanto.jsonplaceholderclient.providers;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RetrofitProvider {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    
    private static Retrofit retrofit = null;
    
    public static synchronized Retrofit getRetrofit () {
        if (retrofit == null) {
            // Initialize retrofit
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
