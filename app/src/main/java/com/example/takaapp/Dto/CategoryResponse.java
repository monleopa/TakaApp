package com.example.takaapp.Dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryResponse {
    @Expose
    @SerializedName("ID")
    private String ID;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("pic")
    private String pic;

    public CategoryResponse(String ID, String name, String pic) {
        this.ID = ID;
        this.name = name;
        this.pic = pic;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
