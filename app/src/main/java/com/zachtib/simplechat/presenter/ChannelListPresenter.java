package com.zachtib.simplechat.presenter;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.zachtib.simplechat.view.IChannelListView;

public class ChannelListPresenter implements IChannelListPresenter {

    private IChannelListView mChannelListView;

    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabaseReference;

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(IChannelListView view) {
        mChannelListView = view;
    }
}
