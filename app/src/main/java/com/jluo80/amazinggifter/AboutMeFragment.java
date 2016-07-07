package com.jluo80.amazinggifter;


import android.content.Intent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;


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
        NetworkImageView profilePictureImageView = (NetworkImageView) view.findViewById(R.id.profile_picture);
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
//        ImageLoadTask profilePictureLoad = new ImageLoadTask(profilePictureUrl, profilePictureImageView);
//        profilePictureLoad.execute();

        ImageLoader imageLoader = MySingleton.getInstance(this.getActivity().getApplicationContext()).getImageLoader();

        profilePictureImageView.setImageUrl(profilePictureUrl, imageLoader);
    }
}