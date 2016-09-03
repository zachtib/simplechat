package com.zachtib.simplechat.interfaces;

import android.net.Uri;

import com.zachtib.simplechat.model.User;

public interface IAuthenticator {
    User getUser();
    String getUsername();
    String getEmail();
    String getDisplayName();
    boolean isAuthenticated();
    Uri getProfilePhotoUrl();
}
