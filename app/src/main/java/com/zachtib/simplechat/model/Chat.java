package com.zachtib.simplechat.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Chat {
    public String id;
    public String name;
    public String photoUrl;

    public Chat() {

    }

    public Chat(String id, String name, String photoUrl) {
        this.id = id;
        this.name = name;
        this.photoUrl = photoUrl;
    }

    public Chat(String id, User user) {
        this(id, user.username, user.photoUrl);
    }
}
