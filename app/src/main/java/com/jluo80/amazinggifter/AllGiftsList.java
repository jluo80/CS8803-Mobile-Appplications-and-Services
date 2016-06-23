package com.jluo80.amazinggifter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;



public class AllGiftsList extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_gifts_list);

        Intent intent = getIntent();
        TextView user_id_text = (TextView) findViewById(R.id.user_id);
        user_id_text.setText("User ID: " + intent.getStringExtra("user_id"));
        TextView first_name_text = (TextView) findViewById(R.id.first_name);
        first_name_text.setText("Name: " + intent.getStringExtra("full_name"));
    }
}
