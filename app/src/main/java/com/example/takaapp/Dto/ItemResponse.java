package com.example.takaapp.Dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ItemResponse implements Serializable {
    @Expose
    @SerializedName("_id")
    private String _id;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("property")
    private String[] property;

    @Expose
    @SerializedName("brand")
    private String brand;

    @Expose
    @SerializedName("img")
    private String img;

    @Expose
    @SerializedName("number")
    private int number;

    @Expose
    @SerializedName("price")
    private long price;

    public ItemResponse(String _id, String name, String[] property, String brand, String img, int number, long price) {
        this._id = _id;
        this.name = name;
        this.property = property;
        this.brand = brand;
        this.img = img;
        this.number = number;
        this.price = price;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getProperty() {
        return property;
    }

    public void setProperty(String[] property) {
        this.property = property;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}
