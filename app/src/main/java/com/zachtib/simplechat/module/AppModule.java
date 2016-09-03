package com.zachtib.simplechat.module;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.zachtib.simplechat.data.FirebaseAuthenticator;
import com.zachtib.simplechat.interfaces.IAuthenticator;
import com.zachtib.simplechat.presenter.ChannelListPresenter;
import com.zachtib.simplechat.presenter.ConversationPresenter;
import com.zachtib.simplechat.presenter.IChannelListPresenter;
import com.zachtib.simplechat.presenter.IConversationPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    public FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Provides
    @Singleton
    public FirebaseDatabase provideFirebaseDatabase() {
        return FirebaseDatabase.getInstance();
    }

    @Provides
    IConversationPresenter provideConversationPresenter(FirebaseAuth auth, FirebaseDatabase database) {
        return new ConversationPresenter(auth, database);
    }

    @Provides
    IChannelListPresenter provideChannelListPresenter() {
        return new ChannelListPresenter();
    }

    @Provides
    IAuthenticator provideAuthenticator(FirebaseAuth auth) {
        return new FirebaseAuthenticator(auth);
    }

}
