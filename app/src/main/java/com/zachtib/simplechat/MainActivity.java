package com.zachtib.simplechat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String ANONYMOUS = "anonymous";

    // Instance variables
    private String mUsername;
    private String mPhotoUrl;
    private SharedPreferences mSharedPreferences;
    private GoogleApiClient mGoogleApiClient;

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    // View instance variables
    private ListView mDrawerList;
    private TextView mNameTextView;
    private TextView mEmailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navView.inflateHeaderView(R.layout.nav_header_main);
        mNameTextView = (TextView) headerView.findViewById(R.id.name_textview);
        mEmailTextView = (TextView) headerView.findViewById(R.id.email_textview);

        checkFirebaseAuthentication();

        // greet our user
        ((TextView) findViewById(R.id.greeter_text)).setText(String.format("Hello, %s", mUsername));
    }

    public void checkFirebaseAuthentication() {
        // Set default username is anonymous.
        mUsername = ANONYMOUS;

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            mNameTextView.setText(mUsername);
            if (mFirebaseUser.getEmail() != null) {
                //mEmailTextView.setText(mFirebaseUser.getEmail());
            }
            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }
        }
    }
}
