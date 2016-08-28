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

}
