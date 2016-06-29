package com.jluo80.amazinggifter;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.ImageRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import org.json.JSONException;
import org.json.JSONObject;


public class LoginFragment extends Fragment {

    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;

    public static final String PROFILE_USER_ID = "USER_ID";
    public static final String PROFILE_FIRST_NAME = "PROFILE_FIRST_NAME";
    public static final String PROFILE_LAST_NAME = "PROFILE_LAST_NAME";
    public static final String PROFILE_IMAGE_URL = "PROFILE_IMAGE_URL";

    SharedPreferences pref;

    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            Profile profile = Profile.getCurrentProfile();

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
                            Intent intent = new Intent(LoginFragment.this.getActivity(), NavigationActivity.class);
                            intent.putExtra("id", id);
                            intent.putExtra("username", name);
                            intent.putExtra("email", email);
                            intent.putExtra("birthday", birthday);
                            intent.putExtra("picture", profileImageUrl);

                            startActivity(intent);
                            getActivity().finish();
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
        callbackManager = CallbackManager.Factory.create();
        accessTokenTracker= new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {

                Log.v("AccessTokenTracker", "oldAccessToken=" + oldToken + "||" + "newToken" + newToken);

            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                Log.v("Session Tracker", "oldProfile=" + oldProfile + "||" + "currentProfile" +newProfile);
            }
        };

        accessTokenTracker.startTracking();
        profileTracker.startTracking();
        LoginManager.getInstance().registerCallback(callbackManager, callback);

    }

    @Override
    /**
     * When system invokes fragment for the first time, it has to overwrite the OnCreateView()
     * method and return a View object.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Load fragment_login.xml
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set up Login Button.
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);

        // setFragment only if you are using it inside a Fragment.
        loginButton.setFragment(this);

        loginButton.setReadPermissions("public_profile", "email", "user_birthday", "user_friends");
        // Register a callback method when Login Button is Clicked.
        loginButton.registerCallback(callbackManager, callback);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onStop() {
        super.onStop();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }


    @Override
    public void onResume() {
        super.onResume();
//        Profile profile = Profile.getCurrentProfile();
        AppEventsLogger.activateApp(this.getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this.getActivity());
    }
}
