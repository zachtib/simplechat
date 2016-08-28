package com.zachtib.simplechat.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Chat {
    public String id;
    public String name;

    public Chat() {

    }

    public Chat(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
