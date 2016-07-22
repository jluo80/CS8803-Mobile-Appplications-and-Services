package com.jluo80.amazinggifter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;



public class MainScreenActivity extends AppCompatActivity {

    private static final String TAG = MainScreenActivity.class.getName();
    FirebaseAuth mAuth;
    private DrawerLayout mDrawerLayout;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Test Refresh Problem", "MainScreenActivity onCreate");

        FacebookSdk.sdkInitialize(getApplication().getApplicationContext());
        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_main_screen);
        /** Setup Toolbar and ActionBar. */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        /** Setup DrawerLayout. */
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        /** 1.Setup drawer header content: Title and profile picture. */
        View drawerHeader = navigationView.getHeaderView(0);
        NetworkImageView drawerPicture = (NetworkImageView) drawerHeader.findViewById(R.id.drawer_picture);
        NetworkImageView coverPicture = (NetworkImageView) drawerHeader.findViewById(R.id.cover_picture);

        /** Fetch data from SharePreferences. */
        SharedPreferences mSharedPreferences = this.getSharedPreferences("facebookLogin", Activity.MODE_PRIVATE);
        String drawerPictureUrl = mSharedPreferences.getString("picture", "");
        String coverPictureUrl = mSharedPreferences.getString("cover", "");
        ImageLoader imageLoader = MySingleton.getInstance(MainScreenActivity.this.getApplicationContext()).getImageLoader();
        drawerPicture.setImageUrl(drawerPictureUrl, imageLoader);
        coverPicture.setImageUrl(coverPictureUrl, imageLoader);


        /** 2.Setup drawer body content: Name, E-mail and birthday. */
        /** Fetch data from SharePreferences. */
        String username = mSharedPreferences.getString("username", "");
        String email = mSharedPreferences.getString("email", "");
        String birthday = mSharedPreferences.getString("birthday", "");
        Menu menu = navigationView.getMenu();
        MenuItem nav_name = menu.findItem(R.id.navigation_item_name);
        nav_name.setTitle(username);
        MenuItem nav_email = menu.findItem(R.id.navigation_item_email);
        nav_email.setTitle(email);
        MenuItem nav_birthday = menu.findItem(R.id.navigation_item_birthday);
        nav_birthday.setTitle(birthday);

        /** Setup item onSelectedListener. */
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Intent intent;

                if (menuItem.getItemId() == R.id.navigation_item_name) {
                    mDrawerLayout.closeDrawers();
                    intent = new Intent(MainScreenActivity.this, AddGiftsActivity.class);
                    startActivity(intent);
                    return true;
                }

                if (menuItem.getItemId() == R.id.navigation_item_email) {
                    mDrawerLayout.closeDrawers();
                    intent = new Intent(MainScreenActivity.this, AddGiftsActivity.class);
                    startActivity(intent);
                    return true;
                }

                if (menuItem.getItemId() == R.id.navigation_item_birthday) {
                    mDrawerLayout.closeDrawers();
                    intent = new Intent(MainScreenActivity.this, AddGiftsActivity.class);
                    startActivity(intent);
                    return true;
                }

                if (menuItem.getItemId() == R.id.navigation_item_home) {
                    mDrawerLayout.closeDrawers();
                    intent = new Intent(MainScreenActivity.this, AddGiftsActivity.class);
                    startActivity(intent);
                    return true;
                }

                if (menuItem.getItemId() == R.id.navigation_item_about_me) {
                    mDrawerLayout.closeDrawers();
                    finish();
                    String resumeUrl = "http://jiahaoluo.deercv.com/admin";
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(resumeUrl)));
                    return true;
                }

                if (menuItem.getItemId() == R.id.navigation_item_refresh) {
                    mDrawerLayout.closeDrawers();
                    finish();
                    startActivity(getIntent());
                    return true;
                }

                if (menuItem.getItemId() == R.id.navigation_item_logout) {
                    mDrawerLayout.closeDrawers();
                    facebookLogout();
                    return true;
                }
                return false;
            }
        });

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


        TabPagerAdapter adapter = new TabPagerAdapter(this, getSupportFragmentManager());
        final ViewPager viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        final TabLayout tabLayout = (TabLayout)findViewById(R.id.tablayout);
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

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position).select();
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
                fab.hide();
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
            case R.id.action_refresh:
                finish();
                startActivity(getIntent());
                return true;
            case R.id.action_logout:
                facebookLogout();
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
                return new MyGiftFragment();
            } else if (position == 1) {
                return new FriendContactFragment();
            } else {
                return new SummaryFragment();
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
                return mContext.getString(R.string.friends);
            } else {
                return mContext.getString(R.string.summary);
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

    @Override
    public void onStart() {
        super.onStart();
        Log.e("Test Refresh Problem", "MainScreenActivity onStart");
    }

    public void facebookLogout() {
        if (mAuth.getCurrentUser() != null) {
            Log.e("22", "Not sign out yet.");
        } else {
            Log.e("22", "Signed out already.");
        }
        mAuth.signOut();
        if (mAuth.getCurrentUser() != null) {
            Log.e("22", "Not sign out yet.");
        } else {
            Log.e("22", "Signed out already.");
        }
        LoginManager.getInstance().logOut();
        Log.e("facebook", "logout");
        startActivity(new Intent(MainScreenActivity.this, FacebookLoginActivity.class));
        finish();
    }
}