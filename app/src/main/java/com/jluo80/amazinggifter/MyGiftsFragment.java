package com.jluo80.amazinggifter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.database.DatabaseReference;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */

public class MyGiftsFragment extends Fragment {

    private static final String TAG = MyGiftsFragment.class.getName();
    private ArrayList<Gift> mGiftArray;
    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabase;

    public MyGiftsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mGiftArray = new ArrayList<>();
        mGiftArray.add(new Gift("category", "8/18/2016", "initiator id", "item id", "http://www.ebay.com/itm/Dell-XPS-13-13-3-QHD-IPS-Touch-Laptop-6th-Gen-Core-i5-8GB-Ram-256GB-SSD/371681082784?hash=item5689eb3da0&_trkparms=5373%3A0%7C5374%3AFeatured","name", "http://orig02.deviantart.net/cd44/f/2016/152/2/d/placeholder_3_by_sketchymouse-da4ny84.png", "7/15/2016", 25.00, 0, "Mother's Day", "receiver id"));

        mGiftArray = ((MainScreenActivity)this.getActivity()).getWishListGiftArray();



//        SharedPreferences mSharedPreferences = this.getActivity().getSharedPreferences("test", Activity.MODE_PRIVATE);
//        String facebookId = mSharedPreferences.getString("facebookId", "");
//
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        DatabaseReference myWishList = mDatabase.child("user/" + facebookId + "/my_gift/" + "/wish_list");

//        myWishList.addChildEventListener(new ChildEventListener() {
//
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                /** com.jluo80.amazinggifter.MyGiftsFragment:-KMa77KnGU5hsF8dngVc*/
//                String uniqueKey = dataSnapshot.getKey();
//                Log.e(TAG, "onChildAdded:" + uniqueKey);
//                /** com.jluo80.amazinggifter.MyGiftsFragment:true */
//                Log.e(TAG, "onChildAdded:" + dataSnapshot.getValue());
////                mGiftArray.clear();
//
//                DatabaseReference ref = mDatabase.child("gift/" + uniqueKey);
//                ref.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot item) {
//                        Log.e(TAG, item.getKey());
//                        System.out.println("hello " + item.getValue());
//                        Gift gift = item.getValue(Gift.class);
//                        System.out.println(gift.getItem_url() + "-" + gift.getProgress() + "-" + gift.getReason() + "-" + gift.getPrice());
//                        mGiftArray.add(gift);
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

        View rootView =  inflater.inflate(R.layout.fragment_list_view, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new MyGiftRecyclerAdapter(getContext(), mGiftArray));

        return rootView;
    }
}