package com.zachtib.simplechat.module;

import com.zachtib.simplechat.ConversationActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(ConversationActivity activity);
}
