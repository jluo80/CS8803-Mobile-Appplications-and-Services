package com.jluo80.amazinggifter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.internal.ImageRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class FriendGiftActivity extends AppCompatActivity {

    private static final String TAG = FriendGiftActivity.class.getName();
    private DatabaseReference mDatabase;
    private FriendGiftRecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_gift);

        // Set the toolbar of the add gifts activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        /** Setup FloatingActionButton. */
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, AddGiftsActivity.class);
                context.startActivity(intent);
            }
        });


        final ArrayList<Gift> mGiftArray = new ArrayList<>();

        mAdapter = new FriendGiftRecyclerAdapter(this, mGiftArray);

        SharedPreferences mSharedPreferences = this.getSharedPreferences("friendFacebookId", Activity.MODE_PRIVATE);
        final String facebookId  = mSharedPreferences.getString("friendFacebookId", "");

        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference HisWishList = mDatabase.child("user/" + facebookId + "/my_gift/" + "/wish_list");
        HisWishList.addChildEventListener(new ChildEventListener() {
            /** Add new gift */
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mGiftArray.clear();
                /** com.jluo80.amazinggifter.FriendGiftFragment:-KMa77KnGU5hsF8dngVc*/
                final String uniqueKey = dataSnapshot.getKey();
                Log.e(TAG, "onChildAdded:" + uniqueKey);
                /** com.jluo80.amazinggifter.FriendGiftFragment:true */
                Log.e(TAG, "onChildAdded:" + dataSnapshot.getValue());

                DatabaseReference ref = mDatabase.child("gift").child(uniqueKey);
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot item) {

                        Log.e(TAG, "Single" + item.getKey());
                        System.out.println("test" + item.getValue());
                        Gift gift = item.getValue(Gift.class);
                        gift.setUnique_key(uniqueKey);
                        System.out.println(gift.getInitiator_id() + "friend&&&&&&&" + gift.getReceiver_id());

                        String end = gift.getDue_date();
                        String start = getCurrentDate();
                        if(gift.getProgress() <= gift.getPrice() && end.compareTo(start) >= 0) {
                            mGiftArray.add(gift);
//                            mAdapter.notifyDataSetChanged();
                            mRecyclerView.setAdapter(new FriendGiftRecyclerAdapter(FriendGiftActivity.this, mGiftArray));
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
                mGiftArray.clear();
                /** com.jluo80.amazinggifter.FriendGiftFragment:-KMa77KnGU5hsF8dngVc*/
                final String uniqueKey = dataSnapshot.getKey();
                Log.e(TAG, "onChildAdded:" + uniqueKey);
                /** com.jluo80.amazinggifter.FriendGiftFragment:true */
                Log.e(TAG, "onChildAdded:" + dataSnapshot.getValue());

                DatabaseReference ref = mDatabase.child("gift/" + uniqueKey);
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot item) {

                        Log.e(TAG, "Single" + item.getKey());
                        System.out.println("test" + item.getValue());
                        Gift gift = item.getValue(Gift.class);
                        gift.setUnique_key(uniqueKey);
                        System.out.println(gift.getInitiator_id() + "friend&&&&&&&" + gift.getReceiver_id());

                        String end = gift.getDue_date();
                        String start = getCurrentDate();
                        if(gift.getProgress() <= gift.getPrice() && end.compareTo(start) >= 0) {
                            mGiftArray.add(gift);
//                            mAdapter.notifyDataSetChanged();
                            mRecyclerView.setAdapter(new FriendGiftRecyclerAdapter(FriendGiftActivity.this, mGiftArray));
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

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(FriendGiftActivity.this));
        mRecyclerView.setAdapter(new EbayRecyclerAdapter(FriendGiftActivity.this, mGiftArray));
    }

    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("MM/dd/yy");
        return mdformat.format(calendar.getTime());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.action_refresh){
            finish();
            startActivity(getIntent());
        }
        if(id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
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

}
