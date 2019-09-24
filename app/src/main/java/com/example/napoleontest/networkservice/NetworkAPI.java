package com.example.napoleontest.networkservice;

import com.example.napoleontest.domain.model.PostUser;
import com.example.napoleontest.domain.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;


public interface NetworkAPI {

    @Headers("Content-Type: application/json")
    @GET("users")
    Call<List<User>> getAllUsers();

    @Headers("Content-Type: application/json")
    @GET("posts")
    Call<List<PostUser>> getAllPostUser(@Query("userId") String userId);

    @Headers("Content-Type: application/json")
    @GET("posts")
    Call<List<PostUser>> getAllPost();

    @Headers("Content-Type: application/json")
    @GET("users")
    Call<List<User>> getDataPostUser();

}