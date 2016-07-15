package com.jluo80.amazinggifter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsGiftsFragment extends Fragment {

    private ArrayList<Gift> mGiftArray;
    private RecyclerView mRecyclerView;

    public FriendsGiftsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mGiftArray = new ArrayList<>();
        mGiftArray.add(new Gift("category", "8/18/2016", "initiator id", "item id", "http://www.ebay.com/itm/Dell-XPS-13-13-3-QHD-IPS-Touch-Laptop-6th-Gen-Core-i5-8GB-Ram-256GB-SSD/371681082784?hash=item5689eb3da0&_trkparms=5373%3A0%7C5374%3AFeatured","name", "http://orig02.deviantart.net/cd44/f/2016/152/2/d/placeholder_3_by_sketchymouse-da4ny84.png", "7/15/2016", 25.00, 0, "Mother's Day", "receiver id"));

        View rootView =  inflater.inflate(R.layout.fragment_list_view, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new MyGiftRecyclerAdapter(getContext(), mGiftArray));

        return rootView;
    }
}
