package com.zachtib.simplechat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.zachtib.simplechat.presenter.ConversationPresenter;
import com.zachtib.simplechat.view.ConversationView;

import javax.inject.Inject;

public class ConversationActivity extends AppCompatActivity {
    @Inject ConversationPresenter mPresenter;
    ConversationView mView;

    @Inject
    FirebaseDatabase database;

    @Inject
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((SimpleChat) getApplication()).getAppComponent().inject(this);

        // Get extra information
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            finish();
        }

        setContentView(R.layout.activity_conversation);

        mView = (ConversationView) getSupportFragmentManager()
                .findFragmentById(R.id.conversation_fragment);

        mView.attachPresenter(mPresenter);
        mPresenter.setChatId(extras.getString("CHAT_ID"));
        mPresenter.attachView(mView);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
