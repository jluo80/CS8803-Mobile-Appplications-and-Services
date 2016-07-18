package com.jluo80.amazinggifter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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

/**
 * A simple {@link Fragment} subclass.
 */

public class MyGiftsFragment extends Fragment {

    private static final String TAG = MyGiftsFragment.class.getName();
    private ArrayList<Gift> mGiftArray;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private DatabaseReference mDatabase;
    private String facebookId;

    public MyGiftsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        View rootView =  inflater.inflate(R.layout.fragment_list_view, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mGiftArray = new ArrayList<>();
//        mGiftArray.add(new Gift("category", "8/18/2016", "initiator id", "item id", "http://www.ebay.com/itm/Dell-XPS-13-13-3-QHD-IPS-Touch-Laptop-6th-Gen-Core-i5-8GB-Ram-256GB-SSD/371681082784?hash=item5689eb3da0&_trkparms=5373%3A0%7C5374%3AFeatured","name", "http://orig02.deviantart.net/cd44/f/2016/152/2/d/placeholder_3_by_sketchymouse-da4ny84.png", "7/15/2016", 25.00, 0, "Mother's Day", "receiver id"));

        SharedPreferences mSharedPreferences = this.getActivity().getSharedPreferences("test", Activity.MODE_PRIVATE);
        facebookId = mSharedPreferences.getString("facebookId", "");


        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myWishList = mDatabase.child("user/" + facebookId + "/my_gift/" + "/wish_list");
        myWishList.addChildEventListener(new ChildEventListener() {
            /** Add new gift */
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                mGiftArray.clear();
                /** com.jluo80.amazinggifter.MyGiftsFragment:-KMa77KnGU5hsF8dngVc*/
                final String uniqueKey = dataSnapshot.getKey();
                Log.e(TAG, "onChildAdded:" + uniqueKey);
                /** com.jluo80.amazinggifter.MyGiftsFragment:true */
                Log.e(TAG, "onChildAdded:" + dataSnapshot.getValue());

                DatabaseReference ref = mDatabase.child("gift/" + uniqueKey);
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
                            mRecyclerView.setAdapter(new MyGiftRecyclerAdapter(getContext(), mGiftArray));
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
                Log.e(TAG, "onChildChanged:" + dataSnapshot.getKey());

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.e(TAG, "onChildRemoved:" + dataSnapshot.getKey());

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());

            }
        });


        DatabaseReference fromFriendsList = mDatabase.child("user/" + facebookId + "/my_gift/" + "/from_friends");
        fromFriendsList.addChildEventListener(new ChildEventListener() {
            /** Add new gift */
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                mGiftArray.clear();
                /** com.jluo80.amazinggifter.MyGiftsFragment:-KMa77KnGU5hsF8dngVc*/
                final String uniqueKey = dataSnapshot.getKey();
                Log.e(TAG, "onChildAdded:" + uniqueKey);
                /** com.jluo80.amazinggifter.MyGiftsFragment:true */
                Log.e(TAG, "onChildAdded:" + dataSnapshot.getValue());

                DatabaseReference ref = mDatabase.child("gift/" + uniqueKey);
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
                            mRecyclerView.setAdapter(new MyGiftRecyclerAdapter(getContext(), mGiftArray));
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
                Log.e(TAG, "onChildChanged:" + dataSnapshot.getKey());

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.e(TAG, "onChildRemoved:" + dataSnapshot.getKey());

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());

            }
        });

//        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
//        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
//                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
//                        .getDisplayMetrics()));
//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//            }
//        });

        //don't forget to cancel refresh when work is done
//        if(mSwipeRefreshLayout.isRefreshing()) {
//           mSwipeRefreshLayout.setRefreshing(false);
//        }

//        View rootView =  inflater.inflate(R.layout.fragment_list_view, container, false);
//        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mRecyclerView.setAdapter(new MyGiftRecyclerAdapter(getContext(), mGiftArray));

        return rootView;
    }



//    public void refreshData() {
//
//        DatabaseReference myWishList = mDatabase.child("user/" + facebookId + "/my_gift/" + "/wish_list");
//
//        myWishList.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                /** com.jluo80.amazinggifter.MyGiftsFragment:-KMa77KnGU5hsF8dngVc*/
//                Log.e(TAG, "onChildAdded:" + uniqueKey);
//                /** com.jluo80.amazinggifter.MyGiftsFragment:true */
//                Log.e(TAG, "onChildAdded:" + dataSnapshot.getValue());
//                getUpdates();
//
//            }
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//            }
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//            }
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
//    }

//    private void getUpdate() {
//        DatabaseReference ref = mDatabase.child("gift/" + uniqueKey);
//        ref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot item) {
//                Log.e(TAG, item.getKey());
//                System.out.println("test" + item.getValue());
//                Gift gift = item.getValue(Gift.class);
//                gift.setUnique_key(uniqueKey);
//                System.out.println(gift.getItem_url() + "-" + gift.getProgress() + "-" + gift.getReason() + "-" + gift.getPrice());
//                mGiftArray.add(gift);
//                mRecyclerView.setAdapter(new MyGiftRecyclerAdapter(getContext(), mGiftArray));
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.w(TAG, "getUser:onCancelled", databaseError.toException());
//            }
//        });
//    }

    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("MM/dd/yy");
        return mdformat.format(calendar.getTime());
    }
}