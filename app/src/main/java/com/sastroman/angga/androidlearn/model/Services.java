package com.sastroman.angga.androidlearn.model;

/**
 * Created by Angga Nova P on 12/19/2017.
 */

import com.google.gson.annotations.SerializedName;

public class Services {
    @SerializedName("id")
    private Integer id;
    @SerializedName("uid")
    private String uid;
    @SerializedName("name")
    private String name;
    @SerializedName("details")
    private String details;
    @SerializedName("price")
    private Integer price;
    @SerializedName("type")
    private String type;
    @SerializedName("thumbnail")
    private String thumbnail;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;
    @SerializedName("status")
    private String status;

    public Services(Integer id, String uid, String name, Integer price, String type, String status,
                    String details, String thumbnail, String created_at, String updated_at) {
        this.id = id;
        this.uid = uid;
        this.name = name;
        this.price = price;
        this.type = type;
        this.status = status;
        this.details = details;
        this.thumbnail = thumbnail;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

}