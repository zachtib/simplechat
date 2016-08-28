package com.zachtib.simplechat.data;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.zachtib.simplechat.model.Channel;
import com.zachtib.simplechat.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        String name = String.format("%s and %s", user1.username, user2.username);
        Channel channel = new Channel(key, name, Arrays.asList(user1, user2));

        mDatabaseReference.child("channels").child(key).setValue(channel);
        addChannelToUser(channel, user1);
        addChannelToUser(channel, user2);

    }

    private void addChannelToUser(Channel channel, User user) {
        mDatabaseReference.child("users").child(user.uid).child("channels")
                .child(channel.channelId).setValue(channel);
    }

    public void startChannelWith(User owner, String email) {
        final User ownerRef = owner;
        mDatabaseReference.child("users").orderByChild("email").equalTo(email).limitToFirst(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snap : dataSnapshot.getChildren()) {
                            User result = snap.getValue(User.class);
                            createChannelForUsers(ownerRef, result);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
