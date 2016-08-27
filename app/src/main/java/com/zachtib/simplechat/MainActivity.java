package com.zachtib.simplechat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.zachtib.simplechat.data.DataLayer;
import com.zachtib.simplechat.model.Channel;
import com.zachtib.simplechat.model.User;

import java.security.PrivilegedAction;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.OnConnectionFailedListener {
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

    private DataLayer mDataLayer;

    // View instance variables
    private ListView mDrawerList;
    private TextView mNameTextView;
    private TextView mEmailTextView;
    private ImageView mPhotoImageView;

    // Dummy placeholder
    private User mDummy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(R.string.app_name);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navView.inflateHeaderView(R.layout.nav_header_main);
        navView.inflateMenu(R.menu.activity_main_drawer);
        navView.setNavigationItemSelectedListener(this);

        mNameTextView = (TextView) headerView.findViewById(R.id.name_textview);
        mEmailTextView = (TextView) headerView.findViewById(R.id.email_textview);
        mPhotoImageView = (ImageView) headerView.findViewById(R.id.photo_image_view);

        checkFirebaseAuthentication();
        mDataLayer = DataLayer.getInstance();
        writeNewUser();
        getListOfChannels();
    }

    private void writeNewUser() {
        Uri photoUri = mFirebaseUser.getPhotoUrl();
        String photoUrl = "";
        if (photoUri != null) {
            photoUrl = photoUri.toString();
        }

        User user = new User(mFirebaseUser.getUid(),
                mFirebaseUser.getDisplayName(),
                mFirebaseUser.getEmail(),
                photoUrl);

        mDataLayer.putUserInDatabase(user);

        //Also make a dummy user
        mDummy = new User("dummy", "Test User", "test@example.com", "");
        mDataLayer.putUserInDatabase(mDummy);

        // Now make a channel
        mDataLayer.createChannelForUsers(user, mDummy);
    }

    private void getListOfChannels() {
        Channel[] channels = mDataLayer.getChannelsForUser(mFirebaseUser.getUid());
    }

    private void checkFirebaseAuthentication() {
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
                mEmailTextView.setText(mFirebaseUser.getEmail());
            }
            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
                Glide.with(this)
                        .load(mPhotoUrl)
                        .into(mPhotoImageView);
            }
        }

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_sign_out:
                mFirebaseAuth.signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                mUsername = ANONYMOUS;
                startActivity(new Intent(this, SignInActivity.class));
                return true;
        }
        return false;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }
}
