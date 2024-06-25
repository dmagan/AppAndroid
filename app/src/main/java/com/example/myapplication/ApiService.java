package com.example.myapplication;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Multipart;
import retrofit2.http.Part;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface ApiService {
    @GET("users/{userId}/messages")
    Call<List<Message>> getMessages(@Path("userId") String userId);

    @POST("users/{userId}/messages")
    Call<Message> sendMessage(@Path("userId") String userId, @Body MessageRequest messageRequest);

    @DELETE("users/{userId}/messages/{messageId}")
    Call<Void> deleteMessage(@Path("userId") String userId, @Path("messageId") String messageId);

    @Multipart
    @POST("users/{userId}/messages")
    Call<Message> sendMessageWithImage(@Path("userId") String userId,
                                       @Part MultipartBody.Part file,
                                       @Part("message") RequestBody message);
}
