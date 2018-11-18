package com.example.aliasghar.mychat.Models;

/**
 * Created by Aliasghar on 10/7/2018.
 */

public class AllUsersModel {

    public AllUsersModel() {

    }


    public String username;


    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public AllUsersModel(String username, String status, String image, String thumb) {
        this.username = username;
        this.status = status;
        this.image = image;
        this.thumb = thumb;

    }

    public  String status;
    public String image;
    public String thumb;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
