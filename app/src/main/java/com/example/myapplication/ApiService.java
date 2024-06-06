package com.example.myapplication;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @GET("users/{id}/messages")
    Call<List<Message>> getMessages(@Path("id") String userId);

    @POST("users/{id}/messages")
    Call<Message> sendMessage(@Path("id") String userId, @Body MessageRequest messageRequest);

    @DELETE("users/{id}/messages/{messageId}")
    Call<Void> deleteMessage(@Path("id") String userId, @Path("messageId") String messageId);
}
