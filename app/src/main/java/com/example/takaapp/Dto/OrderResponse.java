package com.example.takaapp.Dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class OrderResponse {
    @Expose
    @SerializedName("_id")
    private String _id;

    @Expose
    @SerializedName("status")
    private String status;

    @Expose
    @SerializedName("ofUser")
    private String ofUser;

    @Expose
    @SerializedName("date")
    private Date date;

    @Expose
    @SerializedName("total")
    private long total;

    @Expose
    @SerializedName("items")
    private List<ItemResponse> items;

    public OrderResponse(String _id, String status, String ofUser, Date date, long total, List<ItemResponse> items) {
        this._id = _id;
        this.status = status;
        this.ofUser = ofUser;
        this.date = date;
        this.total = total;
        this.items = items;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOfUser() {
        return ofUser;
    }

    public void setOfUser(String ofUser) {
        this.ofUser = ofUser;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<ItemResponse> getItems() {
        return items;
    }

    public void setItems(List<ItemResponse> items) {
        this.items = items;
    }
}
