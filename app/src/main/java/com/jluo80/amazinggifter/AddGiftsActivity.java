package com.jluo80.amazinggifter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

public class AddGiftsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_add_gifts.xml layout file
        setContentView(R.layout.activity_add_gifts);

        // Set the toolbar of the add gifts activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        final TextView selectDateTextView = (TextView) findViewById(R.id.select_date);
        final EditText titleEditText = (EditText) findViewById(R.id.title_edit_text);
        final EditText descriptionEditText = (EditText) findViewById(R.id.description);

        /** Set the spinner for gift reason. */
        Spinner reasonSpinner = (Spinner) findViewById(R.id.reason_spinner);

        /** Create an ArrayAdapter using the string array and a default spinner layout. */
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.reason_array, android.R.layout.simple_spinner_item);

        /** Specify the layout to use when the list of choices appears. */
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        /** Apply the adapter to the spinner. */
        reasonSpinner.setAdapter(adapter);

        final ArrayList<String> reasonList = new ArrayList<>();
        reasonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                reasonList.add(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /** If the fab button click is from MyGift tab, then the receiverId would be the same as the initiatorId. */
        SharedPreferences mMyId = AddGiftsActivity.this.getSharedPreferences("facebookLogin", Activity.MODE_PRIVATE);
        final String initiatorId = mMyId.getString("facebookId","");
        SharedPreferences mFriendId = AddGiftsActivity.this.getSharedPreferences("friendFacebookId", Activity.MODE_PRIVATE);
        final String receiverId  = mFriendId.getString("friendFacebookId", "");

        Intent intent = AddGiftsActivity.this.getIntent();
        final String flag = intent.getStringExtra("me_friend_tab");

        Button ebaySearchButton = (Button) findViewById(R.id.ebaySearchButton);
        ebaySearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dueDate = selectDateTextView.getText().toString();
                String title = titleEditText.getText().toString();
                String reason = reasonList.get(0);

                if((isEmpty(dueDate) || isEmpty(title) || isEmpty(reason))) {
                    Toast.makeText(AddGiftsActivity.this, "Please fill out all required fields.", Toast.LENGTH_SHORT).show();
                } else if(dueDate.compareTo(getCurrentDate()) <= 0) {
                    Toast.makeText(AddGiftsActivity.this, "Due date must be in the future.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(AddGiftsActivity.this, EbaySearchActivity.class);
                    intent.putExtra("due_date", dueDate);
                    intent.putExtra("title", title);
                    intent.putExtra("reason", reason);
                    intent.putExtra("description", descriptionEditText.getText().toString());
                    intent.putExtra("post_time", getCurrentDateAndTime());
                    if(flag.equals("friend")) {
                        intent.putExtra("receiver_id", receiverId);
                    } else {
                        intent.putExtra("receiver_id", initiatorId);
                    }
                    startActivity(intent);
                }
            }
        });
    }

    public void showDatePickerDialog(View v) {
        DialogFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public String getCurrentDateAndTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("MM-dd-yyyy HH:mm");
        return mdformat.format(calendar.getTime());
    }

    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("MM/dd/yy");
        return mdformat.format(calendar.getTime());
    }

    private boolean isEmpty(String content) {
        return content.trim().length() == 0;
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
