package com.zachtib.simplechat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zachtib.simplechat.presenter.ConversationPresenter;
import com.zachtib.simplechat.view.ConversationView;

public class ConversationActivity extends AppCompatActivity {
    private ConversationPresenter mPresenter;
    private ConversationView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get extra information
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            finish();
        }

        setContentView(R.layout.activity_conversation);

        mPresenter = new ConversationPresenter(extras.getString("CHAT_ID"));
        mView = (ConversationView) getSupportFragmentManager()
                .findFragmentById(R.id.conversation_fragment);

        mView.attachPresenter(mPresenter);
        mPresenter.attachView(mView);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
