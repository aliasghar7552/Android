package com.example.aliasghar.mychat.Models;

/**
 * Created by Aliasghar on 11/11/2018.
 */

public class Chats {

    public Chats() {

    }

    public Chats(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String status;
}
