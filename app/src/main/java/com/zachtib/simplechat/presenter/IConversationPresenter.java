package com.zachtib.simplechat.presenter;

import com.zachtib.simplechat.view.IConversationView;

public interface IConversationPresenter extends BasePresenter<IConversationView> {
    void sendButtonPressed();
    void setChatId(String id);
}
