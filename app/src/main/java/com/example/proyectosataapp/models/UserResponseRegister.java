package com.example.proyectosataapp.models;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.InputStream;

public class UserResponseRegister {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("picture")
    @Expose
    private String picture;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("validated")
    @Expose
    private Boolean validated;
    private Bitmap pictureBitMap;

    /**
     * No args constructor for use in serialization
     *
     */
    public UserResponseRegister() {
    }

    /**
     *
     * @param createdAt
     * @param role
     * @param name
     * @param id
     * @param email
     * @param picture
     * @param updatedAt
     */
    public UserResponseRegister(String id, String name, String email, String role, String picture, String createdAt, String updatedAt,Boolean validated) {
        super();
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.picture = picture;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.validated = validated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Bitmap getPictureBitMap() {
        return pictureBitMap;
    }

    public void setPictureBitMap(Bitmap pictureBitMap) {
        this.pictureBitMap = pictureBitMap;
    }

    public Boolean getValidated() {
        return validated;
    }

    public void setValidated(Boolean validated) {
        this.validated = validated;
    }

    @Override
    public String toString() {
        return "UserResponseRegister{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", picture='" + picture + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", pictureBitMap='" + pictureBitMap + '\'' +
                '}';
    }
}