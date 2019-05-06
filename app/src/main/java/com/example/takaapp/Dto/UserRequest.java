package com.example.takaapp.Dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRequest {
    @Expose
    @SerializedName("userId")
    private String userId;

    @Expose
    @SerializedName("type")
    private String type;

    public UserRequest(String userId, String type) {
        this.userId = userId;
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
