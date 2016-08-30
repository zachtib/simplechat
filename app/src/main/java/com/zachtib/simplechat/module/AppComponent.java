package com.zachtib.simplechat.module;

import com.zachtib.simplechat.view.ConversationView;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(ConversationView activity);
}
