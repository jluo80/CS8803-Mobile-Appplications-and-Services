/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jluo80.amazinggifter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.internal.ImageRequest;
import com.facebook.login.LoginFragment;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Demonstrate Firebase Authentication using a Facebook access token.
 */
public class FacebookLoginActivity extends BaseActivity implements
        View.OnClickListener {

    private static final String TAG = "FacebookLogin";

    private TextView mStatusTextView;
    private TextView mDetailTextView;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    // [START declare_auth_listener]
    private FirebaseAuth.AuthStateListener mAuthListener;
    // [END declare_auth_listener]

    private CallbackManager mCallbackManager;

    /* Used to track user logging in/out off Facebook */
    private AccessTokenTracker mAccessTokenTracker;
//    private ProfileTracker mProfileTracker;
    private LoginButton facebookLoginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_facebook_login);

        // Views
        mStatusTextView = (TextView) findViewById(R.id.status);
        mDetailTextView = (TextView) findViewById(R.id.detail);
        findViewById(R.id.button_facebook_signout).setOnClickListener(this);

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        // [START auth_state_listener]
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // [START_EXCLUDE]
                updateUI(user);
                // [END_EXCLUDE]
            }
        };
        // [END auth_state_listener]

        // [START initialize_fblogin]

        mCallbackManager = CallbackManager.Factory.create();

        mAccessTokenTracker= new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {

                Log.v("AccessTokenTracker", "oldAccessToken=" + oldToken + "||" + "newToken" + newToken);

            }
        };

//        mProfileTracker = new ProfileTracker() {
//            @Override
//            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
//                Log.v("Session Tracker", "oldProfile=" + oldProfile + "||" + "currentProfile" +newProfile);
//            }
//        };

        mAccessTokenTracker.startTracking();
//        mProfileTracker.startTracking();

        // Initialize Facebook Login button
        facebookLoginButton = (LoginButton) findViewById(R.id.button_facebook_login);
        facebookLoginButton.setReadPermissions("public_profile", "email", "user_birthday", "user_friends");
        facebookLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
//                Profile profile = Profile.getCurrentProfile();

                // JSON object: {"id":"10208340919458244","name":"Jiahao Luo"}
                // GraphResponse: {Response:  responseCode: 200, graphObject: {"id":"10208340919458244","name":"Jiahao Luo"}
                GraphRequest graphRequest= GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        if (object != null)
                        {
                            Log.e("GraphResponse", response.toString());

                            try
                            {
                                String id = object.getString("id");
                                String name = object.getString("name");
                                String email = object.getString("email");
                                String birthday = object.getString("birthday");
                                String profileImageUrl = ImageRequest.getProfilePictureUri(id, 500, 500).toString();
                                Log.e("Picture", profileImageUrl);
                                Log.e("Email = ", email);
                                Log.e("Birthday = ", birthday);
                                Intent intent = new Intent(FacebookLoginActivity.this, NavigationActivity.class);
                                intent.putExtra("id", id);
                                intent.putExtra("username", name);
                                intent.putExtra("email", email);
                                intent.putExtra("birthday", birthday);
                                intent.putExtra("picture", profileImageUrl);

                                startActivity(intent);
                                finish();
                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.v("facebook - onCancel", "cancelled");
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }

            @Override
            public void onError(FacebookException error) {
                Log.v("facebook - onError", error.getMessage());
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }
        });
        // [END initialize_fblogin]
    }

    // [START on_start_add_listener]
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    // [END on_start_add_listener]

    // [START on_stop_remove_listener]
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
            mAccessTokenTracker.stopTracking();
//            mProfileTracker.stopTracking();
        }
    }
    // [END on_stop_remove_listener]


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    // [START auth_with_facebook]
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        // [START_EXCLUDE silent]
        showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(FacebookLoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_facebook]

    public void signOut() {
        mAuth.signOut();
        LoginManager.getInstance().logOut();

        updateUI(null);
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            mStatusTextView.setText(getString(R.string.facebook_status_fmt, user.getDisplayName()));
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            findViewById(R.id.button_facebook_login).setVisibility(View.GONE);
            findViewById(R.id.button_facebook_signout).setVisibility(View.VISIBLE);
        } else {
            mStatusTextView.setText(R.string.signed_out);
            mDetailTextView.setText(null);

            findViewById(R.id.button_facebook_login).setVisibility(View.VISIBLE);
            findViewById(R.id.button_facebook_signout).setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_facebook_signout:
                signOut();
                break;
        }
    }
}
