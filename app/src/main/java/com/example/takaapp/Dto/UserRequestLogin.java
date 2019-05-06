package com.example.takaapp.Dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRequestLogin {
    @Expose
    @SerializedName("userId")
    private String userId;

    @Expose
    @SerializedName("type")
    private String type;

    @Expose
    @SerializedName("avatar")
    private String avatar;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("username")
    private String username;

    @Expose
    @SerializedName("password")
    private String password;

    public UserRequestLogin(String userId, String type, String avatar, String name, String username, String password) {
        this.userId = userId;
        this.type = type;
        this.avatar = avatar;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
