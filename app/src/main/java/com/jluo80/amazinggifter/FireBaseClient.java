package com.jluo80.amazinggifter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Jiahao on 7/16/2016.
 */
public class FireBaseClient {
    Context c;
    String DB_URL;
    RecyclerView rv;
    ArrayList<Gift> gifts=new ArrayList<>();
    DatabaseReference mDatabase;
    MyGiftRecyclerAdapter adapter;
    public FireBaseClient(Context c, String DB_URL, RecyclerView rv) {
        this.c = c;
        this.DB_URL = DB_URL;
        this.rv = rv;
        //INSTANTIATE
        mDatabase = FirebaseDatabase.getInstance().getReference().child(DB_URL);
    }
    //SAVE
    public void saveOnline(String name,String url)
    {
        Gift gift = new Gift();
        gift.setName(name);
        gift.setItem_url(url);
        mDatabase.child("gift").push().setValue(gift);
    }
    //RETRIEVE
    public void refreshData()
    {
        DatabaseReference myWishList = mDatabase.child("user/" + "12212312321321" + "/my_gift/" + "/wish_list");

        myWishList.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getUpdates(dataSnapshot);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getUpdates(dataSnapshot);
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
    private void getUpdates(DataSnapshot dataSnapshot)
    {
        gifts.clear();
        for(DataSnapshot ds : dataSnapshot.getChildren())
        {
            Gift gift = new Gift();
            gift.setName(ds.getValue(Gift.class).getName());
            gift.setItem_url(ds.getValue(Gift.class).getItem_url());
            gifts.add(gift);
        }
        if(gifts.size()>0)
        {
            adapter=new MyGiftRecyclerAdapter(c, gifts);
            rv.setAdapter(adapter);
        }else {
            Toast.makeText(c,"No data",Toast.LENGTH_SHORT).show();
        }
    }
}