package com.zachtib.simplechat.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.zachtib.simplechat.adapter.MessageAdapter;

public interface IConversationView extends BaseView {
    String getMessageInputContents();
    Context getContext();

    void clearMessageInput();
    void attachMessageAdapter(MessageAdapter adapter);
}
