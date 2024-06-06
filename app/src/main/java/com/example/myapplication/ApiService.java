package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("users/{id}/messages")
    Call<List<Message>> getMessages(@Path("id") String userId);
}
