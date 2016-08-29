package com.zachtib.simplechat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zachtib.simplechat.adapter.ChatAdapter;
import com.zachtib.simplechat.adapter.UserAdapter;
import com.zachtib.simplechat.data.DataLayer;
import com.zachtib.simplechat.model.Channel;
import com.zachtib.simplechat.model.Chat;
import com.zachtib.simplechat.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

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
    private DatabaseReference mDatabaseReference;

    // View instance variables
    private ListView mDrawerList;
    private TextView mNameTextView;
    private TextView mEmailTextView;
    private ImageView mPhotoImageView;
    private RecyclerView mChatRecyclerView;


    // Dummy placeholder
    private User mUser;
    private User mDummy;

    private FirebaseRecyclerAdapter<Chat, ChatAdapter.ChatViewHolder> mChatAdapter;

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
        mChatRecyclerView = (RecyclerView) findViewById(R.id.chat_list);

        findViewById(R.id.fab).setOnClickListener(this);

        checkFirebaseAuthentication();
        mDataLayer = DataLayer.getInstance();
        setupDatabase();
        if (mFirebaseUser != null) {
            writeNewUser();
            getListOfChannels();
        }
    }

    private void setupDatabase() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }

    private void writeNewUser() {
        Uri photoUri = mFirebaseUser.getPhotoUrl();
        String photoUrl = "";
        if (photoUri != null) {
            photoUrl = photoUri.toString();
        }

        mUser = new User(mFirebaseUser.getUid(),
                mFirebaseUser.getDisplayName(),
                mFirebaseUser.getEmail(),
                photoUrl);

        DatabaseReference userRef = mDatabaseReference
                .child("users")
                .child(mUser.uid);

        userRef.updateChildren(mUser.getMapping());
    }

    private void getListOfChannels() {
        mChatAdapter = new FirebaseRecyclerAdapter<Chat, ChatAdapter.ChatViewHolder>(
                Chat.class,
                R.layout.item_chat,
                ChatAdapter.ChatViewHolder.class,
                mDatabaseReference.child("users").child(mUser.uid).child("chats")) {
            @Override
            protected void populateViewHolder(final ChatAdapter.ChatViewHolder viewHolder, Chat model, int position) {
                viewHolder.messageTextView.setText(model.name);
                viewHolder.messengerTextView.setText(model.id);
                viewHolder.chatId = model.id;
                 viewHolder.getView().setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         Log.d(TAG, "Pressed conversation with id " + viewHolder.chatId);

                         Intent intent = new Intent(getBaseContext(), ConversationActivity.class);
                         intent.putExtra("CHAT_ID", viewHolder.chatId);
                         startActivity(intent);
                     }
                 });

                if (model.photoUrl != null) {
                    Glide.with(MainActivity.this)
                            .load(model.photoUrl)
                            .into(viewHolder.messengerImageView);
                }
            }
        };
        mChatAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int chatCount = mChatAdapter.getItemCount();
            }
        });

        mChatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mChatRecyclerView.setAdapter(mChatAdapter);


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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                //showNewChannelDialog();
                getAllUsersNotMe();
                break;
        }
    }

    private void showNewChannelDialog() {
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        new AlertDialog.Builder(this)
                .setTitle("New Chat")
                .setMessage("Enter the email address of the person you want to chat with")
                .setView(input)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String email = String.valueOf(input.getText());
                        searchForUsers(email);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void searchForUsers(String email) {

    }

    private void getAllUsersNotMe() {
        mDatabaseReference.child("users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<User> results = new ArrayList<>();
                        for (DataSnapshot snap : dataSnapshot.getChildren()) {
                            User u = snap.getValue(User.class);
                            if (!u.uid.equals(mUser.uid)) {
                                results.add(u);
                            }
                        }
                        showUserSelectionDialog(results);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, databaseError.getMessage());
                    }
                });
    }

    private void showUserSelectionDialog(final List<User> users) {
        UserAdapter adapter = new UserAdapter(MainActivity.this, users);
        new AlertDialog.Builder(this)
                .setTitle("Select User")
                .setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d(TAG, "Pressed " + i);
                        createChatWithUser(users.get(i));
                    }
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void createChatWithUser(User user) {
        Log.d(TAG, "Creating a chat with " + user.username);

        String id = mDatabaseReference.child("chats").push().getKey();
        String name = mUsername + " to " + user.username;

        mDatabaseReference.child("chats").child(id).setValue(new Chat(id, name, null));
        mDatabaseReference.child("users").child(mUser.uid).child("chats").child(id)
                .setValue(new Chat(id, user));
        mDatabaseReference.child("users").child(user.uid).child("chats").child(id)
                .setValue(new Chat(id, mUser));
    }
}
