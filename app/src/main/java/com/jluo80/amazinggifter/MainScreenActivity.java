package com.jluo80.amazinggifter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;



public class MainScreenActivity extends AppCompatActivity {

    private static final String TAG = MainScreenActivity.class.getName();
    private DrawerLayout mDrawerLayout;
    private FloatingActionButton fab;
    private DatabaseReference mDatabase;
    private ArrayList<Gift> mGiftArray;
    private RecyclerView mRecyclerView;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        /** Setup drawer header content: Title and profile picture. */
        View drawerHeader = navigationView.getHeaderView(0);
        TextView headerText = (TextView) drawerHeader.findViewById(R.id.header_text);
        final NetworkImageView drawerPicture = (NetworkImageView) drawerHeader.findViewById(R.id.drawer_picture);

        /** Fetch data from SharePreferences. */
//        Intent intent = MainScreenActivity.this.getIntent();
//        String drawerPictureUrl = intent.getStringExtra("picture");

        SharedPreferences mSharedPreferences = this.getSharedPreferences("test", Activity.MODE_PRIVATE);
        String drawerPictureUrl = mSharedPreferences.getString("picture", "");
        ImageLoader imageLoader = MySingleton.getInstance(MainScreenActivity.this.getApplicationContext()).getImageLoader();
        drawerPicture.setImageUrl(drawerPictureUrl, imageLoader);

        headerText.setText("Hello World! ");

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                Toast.makeText(MainScreenActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                return true;
            }
        });

        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, AddGiftsActivity.class);
                context.startActivity(intent);
            }
        });

//        /** THIS PART IS FOR MYGIFTFRAGMENT ONLY. */
//        mGiftArray = new ArrayList<>();
//        mGiftArray.add(new Gift("category", "8/18/2016", "initiator id", "item id", "http://www.ebay.com/itm/Dell-XPS-13-13-3-QHD-IPS-Touch-Laptop-6th-Gen-Core-i5-8GB-Ram-256GB-SSD/371681082784?hash=item5689eb3da0&_trkparms=5373%3A0%7C5374%3AFeatured","name", "http://orig02.deviantart.net/cd44/f/2016/152/2/d/placeholder_3_by_sketchymouse-da4ny84.png", "7/15/2016", 25.00, 0, "Mother's Day", "receiver id"));
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        DatabaseReference myWishList = mDatabase.child("user/" + facebookId + "/my_gift/" + "/wish_list");
//
//        myWishList.addChildEventListener(new ChildEventListener() {
//
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                /** com.jluo80.amazinggifter.MyGiftsFragment:-KMa77KnGU5hsF8dngVc*/
//                final String uniqueKey = dataSnapshot.getKey();
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
//                        gift.setUnique_key(uniqueKey);
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
//        /** THIS PART IS FOR MYGIFTFRAGMENT ONLY. */


        TabPagerAdapter adapter = new TabPagerAdapter(this, getSupportFragmentManager());
        final ViewPager viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                animateFab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void animateFab(int position) {
        switch (position) {
            case 0:
                fab.show();
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Context context = view.getContext();
                        Intent intent = new Intent(context, AddGiftsActivity.class);
                        context.startActivity(intent);
                    }
                });
                break;
            case 1:
                fab.show();
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Context context = view.getContext();
                        Intent intent = new Intent(context, AddGiftsActivity.class);
                        context.startActivity(intent);
                    }
                });
                break;

            default:
                fab.hide();
                break;
        }
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

        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                return true;
            case R.id.action_logout:
                signOut();
        }

        return super.onOptionsItemSelected(item);
    }

    static class TabPagerAdapter extends FragmentStatePagerAdapter {

        private Context mContext;

        public TabPagerAdapter(Context context, FragmentManager fm) {
            super(fm);
            mContext = context;
        }

        public Fragment getItem(int position) {
            if (position == 0) {
                return new MyGiftsFragment();
            } else if (position == 1) {
                return new FriendsGiftsFragment();
            } else {
                return new AboutMeFragment();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return mContext.getString(R.string.my_gifts);
            } else if (position == 1) {
                return mContext.getString(R.string.friends_gifts);
            } else {
                return mContext.getString(R.string.about_me);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        // .... other stuff in my onResume ....
//        this.doubleBackToExitPressedOnce = false;
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed();
//            return;
//        }
//        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, "yeah", Toast.LENGTH_SHORT).show();
//    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
    }
}
