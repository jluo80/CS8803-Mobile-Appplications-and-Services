package com.jluo80.amazinggifter;


import android.app.Activity;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */

public class AboutMeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private User user;
    private DatabaseReference mDatabase;

    public AboutMeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /** Fetch data from intent. (No need to retrieve from Firebase, since we can retrieve once
         * the user login)
         * */

//        Intent intent = AboutMeFragment.this.getActivity().getIntent();
//        String username = intent.getStringExtra("username");
//        String birthday = intent.getStringExtra("birthday");
//        String email = intent.getStringExtra("email");
//        String profilePictureUrl = intent.getStringExtra("picture");
//        String coverPictureUrl = intent.getStringExtra("cover");

        SharedPreferences mSharedPreferences = this.getActivity().getSharedPreferences("test", Activity.MODE_PRIVATE);
        String username = mSharedPreferences.getString("username", "");
        String birthday = mSharedPreferences.getString("birthday", "");
        String email = mSharedPreferences.getString("email", "");
        String profilePictureUrl = mSharedPreferences.getString("picture", "");
        String coverPictureUrl = mSharedPreferences.getString("cover", "");

        /** Assign parameters to the user object. */
        user = new User(username, email, birthday, profilePictureUrl, coverPictureUrl);
        Log.e("user", user.getName() + user.getBirthday() + user.getProfilePictureUrl() + user.getCoverPictureUrl());
        /** Set up mRecyclerView and the AboutMeRecyclerAdapter. */
        View rootView =  inflater.inflate(R.layout.fragment_list_view, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new AboutMeRecyclerAdapter(getContext(), user));

        return rootView;
    }
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//
//        mDatabase.child("user").addValueEventListener(
//                new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        // Get user value
//                        User user = dataSnapshot.getValue(User.class);
//                        Log.e("VALUE", user.getName());
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
////                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
//                    }
//                });
//    }
}