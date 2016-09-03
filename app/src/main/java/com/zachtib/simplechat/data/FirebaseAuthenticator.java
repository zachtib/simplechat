package com.zachtib.simplechat.data;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.zachtib.simplechat.interfaces.IAuthenticator;
import com.zachtib.simplechat.model.User;

public class FirebaseAuthenticator implements IAuthenticator {

    private FirebaseAuth mAuth;

    public FirebaseAuthenticator(FirebaseAuth auth) {
        this.mAuth = auth;
    }

    @Override
    public User getUser() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public Uri getProfilePhotoUrl() {
        return null;
    }
}
