package com.example.takaapp.Dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderBuyRequest {
    @Expose
    @SerializedName("user")
    private  UserRequest user;

    @Expose
    @SerializedName("fullName")
    private String fullName;

    @Expose
    @SerializedName("address")
    private String address;

    @Expose
    @SerializedName("phone")
    private String phone;

    @Expose
    @SerializedName("payment")
    private String payment;

    public OrderBuyRequest(UserRequest user, String fullName, String address, String phone, String payment) {
        this.user = user;
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
        this.payment = payment;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public UserRequest getUser() {
        return user;
    }

    public void setUser(UserRequest user) {
        this.user = user;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
