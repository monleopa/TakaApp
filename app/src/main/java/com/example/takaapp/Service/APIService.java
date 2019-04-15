package com.example.takaapp.Service;

import com.example.takaapp.Dto.CategoryResponse;
import com.example.takaapp.Dto.UserRequestLogin;
import com.example.takaapp.Dto.UserRequestRegister;
import com.example.takaapp.Dto.UserResponse;
import com.example.takaapp.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {
//    @GET("items/{id}")
//    Call<Item> getItem(@Path("id") String id);

    @POST("users/login")
    Call<UserResponse> login(@Body UserRequestLogin user);

    @POST("users/register")
    Call<UserResponse> register(@Body UserRequestRegister user);

    @GET("categories")
    Call<List<CategoryResponse>> getAllCategory();
}