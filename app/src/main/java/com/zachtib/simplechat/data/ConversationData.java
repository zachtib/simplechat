package com.zachtib.simplechat.data;

import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Inject;

public class ConversationData {
    @Inject
    FirebaseDatabase mDatabase;

    public ConversationData() {

    }
}
