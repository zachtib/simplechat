package com.zachtib.simplechat.model;

public class Message extends BaseModel {
    public String sender;
    public String text;
    public String profilePhotoUrl;

    public Message() {

    }

    public Message(String sender, String text, String profilePhotoUrl) {
        this.sender = sender;
        this.text = text;
        this.profilePhotoUrl = profilePhotoUrl;
    }
}
