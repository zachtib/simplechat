package com.zachtib.simplechat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.zachtib.simplechat.presenter.ConversationPresenter;
import com.zachtib.simplechat.view.ConversationView;

import javax.inject.Inject;

public class ConversationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get extra information
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            finish();
        }

        setContentView(R.layout.activity_conversation);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
