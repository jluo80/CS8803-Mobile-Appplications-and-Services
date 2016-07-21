package com.jluo80.amazinggifter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class SummaryFragment extends Fragment {

    private static final String TAG = SummaryFragment.class.getName();
    private ArrayList<Gift> mGiftArray = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private SummaryRecyclerAdapter mAdapter;
    private DatabaseReference mDatabase;

    public SummaryFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new SummaryRecyclerAdapter(getContext(), mGiftArray);

        SharedPreferences mSharedPreferences = this.getActivity().getSharedPreferences("facebookLogin", Activity.MODE_PRIVATE);
        String facebookId = mSharedPreferences.getString("facebookId", "");

//        mGiftArray.add(new Gift("Headphones", "08/19/16", "initiator_id", "item_id", "http://www.ebay.com/itm/Sony-MDR-ZX110-ZX-Series-Headphones-Black-Brand-New-Sealed-Free-Ship-/322171519951", "name", "http://i.ebayimg.com/00/s/MTIwMFg4NDE=/z/39EAAOSwQJhUgIv-/$_1.JPG", "07-19-2016 15:13", 100, 100, "reason", "receiver_id"));

        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myContribution = mDatabase.child("contribution/" + facebookId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        user = new User("name", "email", "birthday", "https://graph.facebook.com/10208340919458244/picture?height=500&width=500&migration_overrides=%7Boctober_2012%3Atrue%7D", "https://scontent.xx.fbcdn.net/t31.0-8/s720x720/13724076_10208539625705776_5275276674875357491_o.jpg");
        View rootView =  inflater.inflate(R.layout.fragment_list_view, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new SummaryRecyclerAdapter(getContext(), mGiftArray);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }
}