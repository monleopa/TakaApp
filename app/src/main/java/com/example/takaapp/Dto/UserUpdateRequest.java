package com.example.takaapp.Dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserUpdateRequest {
    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("phone")
    private String phone;

    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @SerializedName("avatar")
    private String avatar;

    public UserUpdateRequest(String name, String phone, String email, String avatar) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
