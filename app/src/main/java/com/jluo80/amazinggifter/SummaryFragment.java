package com.jluo80.amazinggifter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


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
        DatabaseReference myInvolved = mDatabase.child("user/" + facebookId + "/gift_for_friend");
        myInvolved.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final String uniqueKey = dataSnapshot.getKey();
                DatabaseReference ref = mDatabase.child("gift").child(uniqueKey);
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot item) {

                        Log.e(TAG, "Single" + item.getKey());
                        System.out.println("test" + item.getValue());
                        Gift gift = item.getValue(Gift.class);
                        gift.setUnique_key(uniqueKey);
                        System.out.println(gift.getInitiator_id() + "&&&&&&&" + gift.getReceiver_id());

                        String end = gift.getDue_date();
                        String start = getCurrentDate();
                        if(gift.getProgress() <= gift.getPrice() && end.compareTo(start) >= 0) {
                            mGiftArray.add(gift);
                            mAdapter.notifyDataSetChanged();
//                            mRecyclerView.setAdapter(mAdapter);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

    @Override
    public void onStart(){
        super.onStart();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume(){
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("MM/dd/yy");
        return mdformat.format(calendar.getTime());
    }
}