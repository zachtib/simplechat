package com.zachtib.simplechat.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.zachtib.simplechat.ConversationActivity;
import com.zachtib.simplechat.R;
import com.zachtib.simplechat.SimpleChat;
import com.zachtib.simplechat.adapter.MessageAdapter;
import com.zachtib.simplechat.presenter.IConversationPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConversationView extends Fragment implements IConversationView {

    @BindView(R.id.message_list) RecyclerView mMessageRecyclerView;
    @BindView(R.id.sendButton) Button mSendButton;
    @BindView(R.id.messageEditText) EditText mEditText;
    @Inject IConversationPresenter mPresenter;

    public ConversationView() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((SimpleChat) getActivity().getApplication()).getAppComponent().inject(this);
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            mPresenter.setChatId(extras.getString("CHAT_ID"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conversation_view, container, false);

        ButterKnife.bind(this, view);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.sendButtonPressed();
            }
        });
        mSendButton.setEnabled(true); // TODO

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.attachView(this);
        mPresenter.onResume();
    }

    @Override
    public String getMessageInputContents() {
        return mEditText.getText().toString();
    }

    @Override
    public void clearMessageInput() {
        mEditText.setText("");
    }

    @Override
    public void attachMessageAdapter(MessageAdapter adapter) {
        mMessageRecyclerView.setAdapter(adapter);
        mMessageRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
