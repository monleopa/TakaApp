package com.example.takaapp.Dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderRequest {
    @Expose
    @SerializedName("user")
    private UserRequest user;

    @Expose
    @SerializedName("number")
    private int number;

    @Expose
    @SerializedName("itemId")
    private String itemId;

    public OrderRequest(UserRequest user, int number, String itemId) {
        this.user = user;
        this.number = number;
        this.itemId = itemId;
    }

    public UserRequest getUser() {
        return user;
    }

    public void setUser(UserRequest user) {
        this.user = user;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
