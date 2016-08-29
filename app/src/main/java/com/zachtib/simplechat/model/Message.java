package com.zachtib.simplechat.model;

public class Message extends BaseModel {
    public String sender;
    public String text;
    public String profilePhotoUrl;
    public long timeStamp;

    public Message() {

    }

    public Message(String sender, String text, String profilePhotoUrl, long timeStamp) {
        this.sender = sender;
        this.text = text;
        this.profilePhotoUrl = profilePhotoUrl;
        this.timeStamp = timeStamp;
    }
}
