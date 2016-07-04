package com.jluo80.amazinggifter;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutMeFragment extends Fragment {

    public AboutMeFragment() {
        // Required empty public constructor
    }

    TextView usernameTextView;
    TextView birthdayTextView;
    TextView emailTextView;
    ImageView profilePictureImageView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_about_me, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // About me content
        usernameTextView = (TextView) view.findViewById(R.id.username);
        birthdayTextView = (TextView) view.findViewById(R.id.birthday);
        emailTextView = (TextView) view.findViewById(R.id.email);
        ImageView profilePictureImageView = (ImageView) view.findViewById(R.id.profile_picture);
//        TextView mobileTextView = (TextView) view.findViewById(R.id.mobile);
//        TextView addressTextView = (TextView) view.findViewById(R.id.address);
//        TextView paymentTextView = (TextView) view.findViewById(R.id.payment);

        // Fetch data from intent
        Intent intent = AboutMeFragment.this.getActivity().getIntent();
        String username = intent.getStringExtra("username");
        String birthday = intent.getStringExtra("birthday");
        String email = intent.getStringExtra("email");
        String profilePictureUrl = intent.getStringExtra("picture");

        // Setup data to specific view
        usernameTextView.setText(username);
        birthdayTextView.setText(birthday);
        emailTextView.setText(email);
        ImageLoadTask profilePictureLoad = new ImageLoadTask(profilePictureUrl, profilePictureImageView);
        profilePictureLoad.execute();
    }

}