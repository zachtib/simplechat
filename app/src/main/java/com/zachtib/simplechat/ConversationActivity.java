package com.zachtib.simplechat;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zachtib.simplechat.adapter.MessageAdapter;
import com.zachtib.simplechat.model.Message;

public class ConversationActivity extends AppCompatActivity {

    private String mChatId;
    private String mChatName;

    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabaseReference;
    private FirebaseRecyclerAdapter<Message, MessageAdapter.MessageViewHolder> mMessageAdapter;

    private RecyclerView mMessageRecyclerView;
    private EditText mEditText;
    private Button mSendButton;

    private String mPhotoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.hide(); // TODO
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            finish();
        }

        mEditText = (EditText) findViewById(R.id.messageEditText);
        mSendButton = (Button) findViewById(R.id.sendButton);
        mSendButton.setEnabled(true); // TODO

        mChatId = extras.getString("CHAT_ID");
        mChatName = extras.getString("CHAT_NAME");

        getSupportActionBar().setTitle(mChatName);

        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mFirebaseUser.getPhotoUrl() != null) {
            mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
        }

        // Subscribe to database updates for this conversation
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mMessageAdapter = new FirebaseRecyclerAdapter<Message, MessageAdapter.MessageViewHolder>(
                Message.class,
                R.layout.item_message,
                MessageAdapter.MessageViewHolder.class,
                mDatabaseReference.child("messages").child(mChatId)
        ) {
            @Override
            protected void populateViewHolder(MessageAdapter.MessageViewHolder viewHolder, Message model, int position) {
                viewHolder.messageTextView.setText(model.text);
                viewHolder.messengerTextView.setText(model.sender);
                if (model.profilePhotoUrl != null) {
                    Glide.with(ConversationActivity.this)
                            .load(model.profilePhotoUrl)
                            .into(viewHolder.messengerImageView);
                }
            }
        };
        mMessageAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int chatCount = mMessageAdapter.getItemCount();
            }
        });

        mMessageRecyclerView = (RecyclerView) findViewById(R.id.message_list);

        mMessageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMessageRecyclerView.setAdapter(mMessageAdapter);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = new Message(
                        mFirebaseUser.getDisplayName(),
                        mEditText.getText().toString(),
                        mPhotoUrl); // TODO
                mDatabaseReference.child("messages").child(mChatId)
                        .push().setValue(message);
                mEditText.setText("");
            }
        });
    }

}
