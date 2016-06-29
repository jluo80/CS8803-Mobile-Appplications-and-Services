package com.jluo80.amazinggifter;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



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
        TextView usernameTextView = (TextView) view.findViewById(R.id.username);
        TextView birthdayTextView = (TextView) view.findViewById(R.id.birthday);
        TextView emailTextView = (TextView) view.findViewById(R.id.email);
        ImageView profilePictureImageView = (ImageView) view.findViewById(R.id.picture);
//        TextView mobileTextView = (TextView) view.findViewById(R.id.mobile);
//        TextView addressTextView = (TextView) view.findViewById(R.id.address);
//        TextView paymentTextView = (TextView) view.findViewById(R.id.payment);

        Intent intent = AboutMeFragment.this.getActivity().getIntent();
        String username = intent.getStringExtra("username");
        String birthday = intent.getStringExtra("birthday");
        String email = intent.getStringExtra("email");
        String profilePictureUrl = intent.getStringExtra("picture");

        usernameTextView.setText(username);
        birthdayTextView.setText(birthday);
        emailTextView.setText(email);
//        mobileTextView.setText(profilePictureUrl);

        try {
            Bitmap profilePicture = getFacebookProfilePicture(profilePictureUrl);
            profilePictureImageView.setImageBitmap(profilePicture);
            Log.e("set picture", "success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    public static Bitmap getFacebookProfilePicture(String url)throws IOException {
//            InputStream in = new URL(url).openConnection().getInputStream();
//            Bitmap bitMap = BitmapFactory.decodeStream(in);
//            return bitMap;
//    }

    public static Bitmap getFacebookProfilePicture(String path) throws IOException{

        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if(conn.getResponseCode() == 200){
            InputStream inputStream = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        }
        return null;
    }
}