package com.zachtib.simplechat.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.FirebaseDatabase;
import com.zachtib.simplechat.R;
import com.zachtib.simplechat.presenter.IConversationPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConversationView extends Fragment implements IConversationView {

    @BindView(R.id.message_list) RecyclerView mMessageRecyclerView;
    @BindView(R.id.sendButton) Button mSendButton;
    @BindView(R.id.messageEditText) EditText mEditText;
    IConversationPresenter mPresenter;

    @Inject
    FirebaseDatabase mFirebaseDatabase;

    public ConversationView() {
        // Required empty public constructor
    }

    public void attachPresenter(IConversationPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conversation_view, container, false);

        ButterKnife.bind(this, view);

        mMessageRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
    public String getMessageInputContents() {
        return mEditText.getText().toString();
    }

    @Override
    public RecyclerView getMessageRecyclerView() {
        return mMessageRecyclerView;
    }

    @Override
    public void clearMessageInput() {
        mEditText.setText("");
    }
}
