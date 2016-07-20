package com.jluo80.amazinggifter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class AboutMeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private User user;

    public AboutMeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        user = new User("name", "email", "birthday", "https://graph.facebook.com/10208340919458244/picture?height=500&width=500&migration_overrides=%7Boctober_2012%3Atrue%7D", "https://scontent.xx.fbcdn.net/t31.0-8/s720x720/13724076_10208539625705776_5275276674875357491_o.jpg");
        View rootView =  inflater.inflate(R.layout.fragment_list_view, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new AboutMeRecyclerAdapter(getContext(), user));

        return rootView;
    }
}