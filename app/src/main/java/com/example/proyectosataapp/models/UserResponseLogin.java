
package com.example.proyectosataapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserResponseLogin {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("user")
    @Expose
    private User user;

    /**
     * No args constructor for use in serialization
     * 
     */
    public UserResponseLogin() {
    }

    /**
     * 
     * @param user
     * @param token
     */
    public UserResponseLogin(String token, User user) {
        super();
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserResponseLogin{" +
                "token='" + token + '\'' +
                ", user=" + user +
                '}';
    }
}
