package com.jluo80.amazinggifter;

/**
 * Created by Jiahao on 6/30/2016.
 */
public class User {
    private String username;
    private String email;
    private String birthday;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email, String birthday) {
        this.username = username;
        this.email = email;
        this.birthday = birthday;
    }
}
