package com.jluo80.amazinggifter;

/**
 * Created by Jiahao on 6/30/2016.
 */
public class User {
    private String facebookId;
    private String username;
    private String email;
    private String birthday;
    private String profilePictureUrl;
    private String coverPictureUrl;
    private String mobile;
    private String addressFirst;
    private String addressSecond;
    private String location;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email, String birthday, String pictureUrl, String coverPictureUrl) {
        this.username = username;
        this.email = email;
        this.birthday = birthday;
        this.profilePictureUrl = pictureUrl;
        this.coverPictureUrl = coverPictureUrl;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getCoverPictureUrl() {
        return coverPictureUrl;
    }

    public void setCoverPictureUrl(String coverPictureUrl) {
        this.coverPictureUrl = coverPictureUrl;
    }



    /** Save them for future usage. */
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddressFirst() {
        return addressFirst;
    }

    public void setAddressFirst(String addressFirst) {
        this.addressFirst = addressFirst;
    }

    public String getAddressSecond() {
        return addressSecond;
    }

    public void setAddressSeconde(String addressSecond) {
        this.addressSecond = addressSecond;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String city) {
        this.location = location;
    }
}
