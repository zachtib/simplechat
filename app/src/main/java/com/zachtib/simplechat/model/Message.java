package com.zachtib.simplechat.model;

import com.google.firebase.database.ServerValue;

public class Message extends BaseModel {
    public String sender;
    public String text;
    public String profilePhotoUrl;
    public Object timeStamp;

    public Message() {

    }

    public Message(String sender, String text, String profilePhotoUrl) {
        this.sender = sender;
        this.text = text;
        this.profilePhotoUrl = profilePhotoUrl;
        this.timeStamp = ServerValue.TIMESTAMP;
    }
}
