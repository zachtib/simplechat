package com.zachtib.simplechat.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.zachtib.simplechat.R;
import com.zachtib.simplechat.presenter.IConversationPresenter;

public class ConversationView extends Fragment implements IConversationView {

    private RecyclerView mMessageRecyclerView;
    private Button mSendButton;
    private EditText mEditText;
    private IConversationPresenter mPresenter;

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
        mMessageRecyclerView = (RecyclerView) view.findViewById(R.id.message_list);
        mEditText = (EditText) view.findViewById(R.id.messageEditText);

        mSendButton = (Button) view.findViewById(R.id.sendButton);
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
