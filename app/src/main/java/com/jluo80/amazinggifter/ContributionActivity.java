package com.jluo80.amazinggifter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ContributionActivity extends AppCompatActivity {

    NetworkImageView itemPicture;
    TextView itemName;
    TextView itemPrice;
    EditText contributeAmount;
    ProgressBar progressBar;
    TextView currentRatio;
    TextView itemReason;
    TextView itemDueDate;
    TextView currentProgress;
    private ImageLoader mImageLoader;
    Button contributionConfrim;
    DatabaseReference mDatabase;
    private static final String TAG = ContributionActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribution);

        // Set the toolbar of the add gifts activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        itemPicture = (NetworkImageView) findViewById(R.id.item_picture);
        itemName = (TextView) findViewById(R.id.item_name);
        itemPrice = (TextView) findViewById(R.id.item_price);
        itemReason = (TextView) findViewById(R.id.reason);
        itemDueDate = (TextView) findViewById(R.id.due_date);
        currentProgress =(TextView) findViewById(R.id.current_progress);
        contributeAmount = (EditText) findViewById(R.id.contribution_amount);

        contributionConfrim = (Button) findViewById(R.id.contributionConfirm);
        currentRatio = (TextView) findViewById(R.id.current_ratio);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        Intent intent = ContributionActivity.this.getIntent();
        String pictureUrl = intent.getStringExtra("picture_url");
        String name = intent.getStringExtra("name");
        final String price = intent.getStringExtra("price");
        String reason = intent.getStringExtra("reason");
        String dueDate = intent.getStringExtra("due_date");
        final String progress = intent.getStringExtra("progress");
        final String itemUrl = intent.getStringExtra("item_url");
        final String giftKey = intent.getStringExtra("unique_key");
        final String time = intent.getStringExtra("post_time");
        final String flag = intent.getStringExtra("me_friend_tab");

        SharedPreferences mSharedPreferences = this.getSharedPreferences("facebookLogin", Activity.MODE_PRIVATE);
//        final String contributorId = intent.getStringExtra("initiator_id");
        final String contributorId = mSharedPreferences.getString("facebookId", "");
        final String contributorName = mSharedPreferences.getString("username", "");

        mImageLoader = MySingleton.getInstance(itemName.getContext()).getImageLoader();
        itemPicture.setImageUrl(pictureUrl, mImageLoader);
        itemName.setText(name);
        itemPrice.setText("US $" + price);
        itemReason.setText(reason);
        itemDueDate.setText(dateDiff(dueDate) + " days to go");
        currentProgress.setText("US $" + progress + "/" + price);

        int ratio = ((int) (Double.parseDouble(progress) / Double.parseDouble(price) * 100));
        Log.e(TAG, ratio + " ");
        currentRatio.setText(ratio + "%");
        progressBar.setProgress(ratio);

        itemPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(itemUrl)));
            }
        });

        contributionConfrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty(contributeAmount.getText().toString())) {
                    Toast.makeText(ContributionActivity.this, "No contribution is made.", Toast.LENGTH_SHORT).show();
                } else if(Double.parseDouble(contributeAmount.getText().toString()) > Double.parseDouble(price) - Double.parseDouble(progress)) {
                    Toast.makeText(ContributionActivity.this, "Your contribution is over the price.", Toast.LENGTH_SHORT).show();
                }else {
                    double amount = Double.parseDouble(contributeAmount.getText().toString()) + Double.parseDouble(progress);
                    Log.e("AMOUNT", amount + "");
                    Log.e("CONTRIBUTE", contributeAmount.getText().toString());
                    Log.e("PROGRESS", progress);

                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.child("gift").child(giftKey).child("progress").setValue(amount);

                    String contributionKey = mDatabase.child("contribution").child(giftKey).push().getKey();
                    mDatabase.child("contribution").child(giftKey).child(contributionKey).child("amount").setValue(contributeAmount.getText().toString());
                    mDatabase.child("contribution").child(giftKey).child(contributionKey).child("contributor_id").setValue(contributorId);
                    mDatabase.child("contribution").child(giftKey).child(contributionKey).child("contributor_name").setValue(contributorName);
                    mDatabase.child("contribution").child(giftKey).child(contributionKey).child("time").setValue(time);

                    if(flag.equals("me")) {
                        Intent intent = new Intent(ContributionActivity.this, MainScreenActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        finish();
                        Intent intent = new Intent(ContributionActivity.this, FriendGiftActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

    }

    private boolean isEmpty(String content) {
        return content.trim().length() == 0;
    }

    public Long dateDiff(String str) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        Date dueDate = null;
        try {
            dueDate = dateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date currentDate = new Date();
        long diffTime = dueDate.getTime() - currentDate.getTime();
        long diffDays = diffTime / (1000 * 60 * 60 * 24);
        return diffDays;
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
}
