package com.zachtib.simplechat.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class Channel {
    public String channelId;
    public String title;
    public List<User> users;

    public Channel() {

    }

    public Channel(String channelId, String title, List<User> users) {
        this.channelId = channelId;
        this.title = title;
        this.users = users;
    }
}
