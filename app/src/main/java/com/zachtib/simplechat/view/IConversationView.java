package com.zachtib.simplechat.view;

import android.content.Context;

import com.zachtib.simplechat.adapter.MessageAdapter;

public interface IConversationView extends BaseView {
    String getMessageInputContents();
    Context getContext();

    void clearMessageInput();
    void attachMessageAdapter(MessageAdapter adapter);
}
