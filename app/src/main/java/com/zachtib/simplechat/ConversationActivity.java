package com.zachtib.simplechat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ConversationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get extra information
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            finish();
        }

        getSupportActionBar().setTitle("test");

        setContentView(R.layout.activity_conversation);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
