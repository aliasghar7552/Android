package com.example.aliasghar.mychat.Models;

/**
 * Created by Aliasghar on 11/12/2018.
 */

public class Requests {

    public String username;

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

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public Requests(String username, String status, String thumb) {
        this.username = username;
        this.status = status;
        this.thumb = thumb;
    }

    public String status;
    public String thumb;

    public Requests() {}

}
