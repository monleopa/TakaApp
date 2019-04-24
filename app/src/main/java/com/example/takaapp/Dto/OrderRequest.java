package com.example.takaapp.Dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderRequest {
    @Expose
    @SerializedName("user")
    private UserRequestLogin user;

    @Expose
    @SerializedName("number")
    private int number;

    @Expose
    @SerializedName("itemId")
    private String itemId;

    public OrderRequest(UserRequestLogin user, int number, String itemId) {
        this.user = user;
        this.number = number;
        this.itemId = itemId;
    }

    public UserRequestLogin getUser() {
        return user;
    }

    public void setUser(UserRequestLogin user) {
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
