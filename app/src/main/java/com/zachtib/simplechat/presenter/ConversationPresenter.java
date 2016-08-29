package com.zachtib.simplechat.presenter;

import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zachtib.simplechat.adapter.MessageAdapter;
import com.zachtib.simplechat.model.Message;
import com.zachtib.simplechat.view.IConversationView;

public class ConversationPresenter implements IConversationPresenter {

    IConversationView mConversationView;

    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabaseReference;

    private String mChatId;

    public ConversationPresenter(FirebaseAuth auth, FirebaseDatabase database) {
        mFirebaseUser = auth.getCurrentUser();
        mDatabaseReference = database.getReference();
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(IConversationView view) {
        mConversationView = view;
        MessageAdapter adapter = new MessageAdapter(mConversationView.getContext(),
                mDatabaseReference.child("messages").child(mChatId));
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
            }
        });
        view.getMessageRecyclerView().setAdapter(adapter);
    }

    private String getPhotoUrl() {
        if (mFirebaseUser != null) {
            if (mFirebaseUser.getPhotoUrl() != null) {
                return mFirebaseUser.getPhotoUrl().toString();
            }
        }
        return "";
    }

    @Override
    public void sendButtonPressed() {
        Message msg = new Message(mFirebaseUser.getDisplayName(),
                mConversationView.getMessageInputContents(),
                getPhotoUrl());

        mDatabaseReference.child("messages").child(mChatId)
                .push().setValue(msg);
        mConversationView.clearMessageInput();
    }

    @Override
    public void setChatId(String id) {
        mChatId = id;
    }
}
