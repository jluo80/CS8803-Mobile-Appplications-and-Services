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


public class MyGiftFragment extends Fragment {

    private static final String TAG = MyGiftFragment.class.getName();
    private ArrayList<Gift> mGiftArray = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private MyGiftRecyclerAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private DatabaseReference mDatabase;

    public MyGiftFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Test Refresh Problem", "MyGiftFragment onCreate");
        mAdapter = new MyGiftRecyclerAdapter(getContext(), mGiftArray);
        SharedPreferences mSharedPreferences = this.getActivity().getSharedPreferences("facebookLogin", Activity.MODE_PRIVATE);
        String facebookId = mSharedPreferences.getString("facebookId", "");

//        mGiftArray.add(new Gift("Headphones", "08/19/16", "initiator_id", "item_id", "http://www.ebay.com/itm/Sony-MDR-ZX110-ZX-Series-Headphones-Black-Brand-New-Sealed-Free-Ship-/322171519951", "name", "http://i.ebayimg.com/00/s/MTIwMFg4NDE=/z/39EAAOSwQJhUgIv-/$_1.JPG", "07-19-2016 15:13", 100, 100, "reason", "receiver_id"));

        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myWishList = mDatabase.child("user/" + facebookId + "/my_gift/" + "/wish_list");
        myWishList.addChildEventListener(new ChildEventListener() {
            /** Add new gift */
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                mGiftArray.clear();
                /** com.jluo80.amazinggifter.MyGiftFragment:-KMa77KnGU5hsF8dngVc*/
                final String uniqueKey = dataSnapshot.getKey();
                Log.e(TAG, "onChildAdded:" + uniqueKey);
                /** com.jluo80.amazinggifter.MyGiftFragment:true */
                Log.e(TAG, "onChildAdded:" + dataSnapshot.getValue());

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
                /** com.jluo80.amazinggifter.MyGiftFragment:-KMa77KnGU5hsF8dngVc*/
                final String uniqueKey = dataSnapshot.getKey();
                Log.e(TAG, "onChildAdded:" + uniqueKey);
                /** com.jluo80.amazinggifter.MyGiftFragment:true */
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("Test Refresh Problem", "MyGiftFragment onCreateView");

        View rootView =  inflater.inflate(R.layout.fragment_list_view, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MyGiftRecyclerAdapter(getContext(), mGiftArray);
        mRecyclerView.setAdapter(mAdapter);

//        mRecyclerView.setAdapter(mAdapter);
//        mGiftArray = new ArrayList<>();

//        SharedPreferences mSharedPreferences = this.getActivity().getSharedPreferences("facebookLogin", Activity.MODE_PRIVATE);
//        facebookId = mSharedPreferences.getString("facebookId", "");
//
//
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        DatabaseReference myWishList = mDatabase.child("user/" + facebookId + "/my_gift/" + "/wish_list");
//        myWishList.addChildEventListener(new ChildEventListener() {
//            /** Add new gift */
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                mGiftArray.clear();
//                /** com.jluo80.amazinggifter.MyGiftFragment:-KMa77KnGU5hsF8dngVc*/
//                final String uniqueKey = dataSnapshot.getKey();
//                Log.e(TAG, "onChildAdded:" + uniqueKey);
//                /** com.jluo80.amazinggifter.MyGiftFragment:true */
//                Log.e(TAG, "onChildAdded:" + dataSnapshot.getValue());
//
//                DatabaseReference ref = mDatabase.child("gift").child(uniqueKey);
//                ref.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot item) {
//
//                        Log.e(TAG, "Single" + item.getKey());
//                        System.out.println("test" + item.getValue());
//                        Gift gift = item.getValue(Gift.class);
//                        gift.setUnique_key(uniqueKey);
//                        System.out.println(gift.getInitiator_id() + "&&&&&&&" + gift.getReceiver_id());
//
//                        String end = gift.getDue_date();
//                        String start = getCurrentDate();
//                        if(gift.getProgress() <= gift.getPrice() && end.compareTo(start) >= 0) {
//                            mGiftArray.add(gift);
//                            mRecyclerView.setAdapter(new MyGiftRecyclerAdapter(getContext(), mGiftArray));
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
//                    }
//                });
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                Log.e(TAG, "onChildChanged:" + dataSnapshot.getKey());
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                Log.e(TAG, "onChildRemoved:" + dataSnapshot.getKey());
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
//
//            }
//        });
//
//
//        DatabaseReference fromFriendsList = mDatabase.child("user/" + facebookId + "/my_gift/" + "/from_friends");
//        fromFriendsList.addChildEventListener(new ChildEventListener() {
//            /** Add new gift */
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
////                mGiftArray.clear();
//                /** com.jluo80.amazinggifter.MyGiftFragment:-KMa77KnGU5hsF8dngVc*/
//                final String uniqueKey = dataSnapshot.getKey();
//                Log.e(TAG, "onChildAdded:" + uniqueKey);
//                /** com.jluo80.amazinggifter.MyGiftFragment:true */
//                Log.e(TAG, "onChildAdded:" + dataSnapshot.getValue());
//
//                DatabaseReference ref = mDatabase.child("gift/" + uniqueKey);
//                ref.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot item) {
//
//                        Log.e(TAG, "Single" + item.getKey());
//                        System.out.println("test" + item.getValue());
//                        Gift gift = item.getValue(Gift.class);
//                        gift.setUnique_key(uniqueKey);
//                        System.out.println(gift.getInitiator_id() + "&&&&&&&" + gift.getReceiver_id());
//
//                        String end = gift.getDue_date();
//                        String start = getCurrentDate();
//                        if(gift.getProgress() <= gift.getPrice() && end.compareTo(start) >= 0) {
//                            mGiftArray.add(gift);
//                            mRecyclerView.setAdapter(new MyGiftRecyclerAdapter(mContext, mGiftArray));
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
//                    }
//                });
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                Log.e(TAG, "onChildChanged:" + dataSnapshot.getKey());
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                Log.e(TAG, "onChildRemoved:" + dataSnapshot.getKey());
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
//
//            }
//        });

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