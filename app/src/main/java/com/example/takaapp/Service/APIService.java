package com.example.takaapp.Service;

import com.example.takaapp.Dto.CategoryResponse;
import com.example.takaapp.Dto.ItemResponse;
import com.example.takaapp.Dto.OrderBuyRequest;
import com.example.takaapp.Dto.OrderRequest;
import com.example.takaapp.Dto.OrderResponse;
import com.example.takaapp.Dto.UserRequest;
import com.example.takaapp.Dto.UserRequestLogin;
import com.example.takaapp.Dto.UserRequestRegister;
import com.example.takaapp.Dto.UserResponse;
import com.example.takaapp.Dto.UserUpdateRequest;
import com.example.takaapp.OrderBuy;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    @GET("items/get_by_category/{id}")
    Call<List<ItemResponse>> getAllItemsByCategory(@Path("id") String id);

    @POST("orders/add_to_cart")
    Call<OrderResponse> getOrder(@Body OrderRequest order);

    @POST("orders/cart")
    Call<OrderResponse> getCart(@Body UserRequest user);

    @GET("items/search/{name}")
    Call<List<ItemResponse>> getAllItemsByName(@Path("name") String name);

    @POST("users/get_user")
    Call<UserResponse> getUser(@Body UserRequest user);

    @POST("orders/order")
    Call<OrderResponse> executeOrder(@Body OrderBuyRequest orderBuyRequest);

    @PUT("users/{id}")
    Call<UserResponse> editUser(@Path("id") String id, @Body UserUpdateRequest userUpdateRequest);

    @PUT("orders/delete_from_cart/{itemId}")
    Call<OrderResponse> deleteFromCart(@Path("itemId") String itemId, @Body UserRequest userRequest);

    @GET("items/new_items/10")
    Call<List<ItemResponse>> getNewItems();

    @GET("items/best_selling/10")
    Call<List<ItemResponse>> getBestSelling();

    @POST("orders/of_user")
    Call<List<OrderResponse>> getAllOrders(@Body UserRequest user);

    @POST("orders/order_of_user/{id}")
    Call<OrderResponse> getOrderDetail(@Body UserRequest user, @Path("id") String id);
}