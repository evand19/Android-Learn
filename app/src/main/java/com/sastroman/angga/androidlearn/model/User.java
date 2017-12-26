package com.sastroman.angga.androidlearn.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by User on 12/26/2017.
 */

@IgnoreExtraProperties
public class User {

    public String username;
    public String email;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

}