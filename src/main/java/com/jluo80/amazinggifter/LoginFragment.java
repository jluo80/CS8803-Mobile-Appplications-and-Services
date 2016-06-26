package com.jluo80.amazinggifter;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


/**
 * A placeholder fragment containing a simple view.
 */
public class LoginFragment extends Fragment {

    private CallbackManager callbackManager;
//    private TextView textView;

    private AccessTokenTracker accessTokenTracker;
//    private ProfileTracker profileTracker;

    /**
     * The CallbackManager manages the callbacks into the FacebookSdk from an Activity's or
     * Fragment's onActivityResult() method.
     */
    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            GraphRequest graphRequest= GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    if (object != null)
                    {
                        Log.e("JSON Object", object.toString());
                        Log.e("GraphResponse", response.toString());
                        try
                        {
                            Intent intent = new Intent(LoginFragment.this.getActivity(), NavigationActivity.class);
                            intent.putExtra("user_id", object.getString("id"));
                            intent.putExtra("full_name", object.getString("name"));
                            startActivity(intent);
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            });
            graphRequest.executeAsync();
        }

        @Override
        public void onCancel() {
            Log.v("facebook - onCancel", "cancelled");
        }

        @Override
        public void onError(FacebookException e) {
            Log.v("facebook - onError", e.getMessage());
        }
    };

    public LoginFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        accessTokenTracker= new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {

            }
        };

//        profileTracker = new ProfileTracker() {
//            @Override
//            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
//                displayMessage(newProfile);
//            }
//        };

        accessTokenTracker.startTracking();
//        profileTracker.startTracking();
    }

    @Override
    /**
     * When system invokes fragment for the first time, it has to overwrite the OnCreateView()
     * method and return a View object.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Load fragment_main.xml
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);

//        textView = (TextView) view.findViewById(R.id.textView);

        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_friends"));

        // setFragment only if you are using it inside a Fragment.
        loginButton.setFragment(this);

        // Register a callback method when Login Button is Clicked.
        loginButton.registerCallback(callbackManager, callback);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

//    private void displayMessage(Profile profile){
//        if(profile != null){
//            textView.setText(profile.getFirstName());
//        }
//    }

    @Override
    public void onStop() {
        super.onStop();
        accessTokenTracker.stopTracking();
//        profileTracker.stopTracking();
    }

    @Override
    public void onResume() {
        super.onResume();
//        Profile profile = Profile.getCurrentProfile();
//        displayMessage(profile);
    }
}
