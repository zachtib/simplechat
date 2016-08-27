package com.zachtib.simplechat.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zachtib.simplechat.model.Channel;
import com.zachtib.simplechat.model.User;

import java.util.ArrayList;
import java.util.Arrays;

public class DataLayer {
    private static DataLayer _instance;

    public static synchronized DataLayer getInstance() {
        if (_instance == null) {
            _instance = new DataLayer();
        }
        return _instance;
    }

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    private DataLayer() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
    }

    public void putUserInDatabase(User user) {
        mDatabaseReference.child("users").child(user.uid).setValue(user);
    }

    public Channel[] getChannelsForUser(String uid) {
        return null;
    }

    public void createChannelForUsers(User user1, User user2) {
        String key = mDatabaseReference.child("channels").push().getKey();
        Channel channel = new Channel(key, "", Arrays.asList(user1, user2));

        mDatabaseReference.child("channels").child(key).setValue(channel);

    }
}
