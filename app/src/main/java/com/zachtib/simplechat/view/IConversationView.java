package com.zachtib.simplechat.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

public interface IConversationView extends BaseView {
    String getMessageInputContents();
    Context getContext();
    RecyclerView getMessageRecyclerView();

    void clearMessageInput();
}
