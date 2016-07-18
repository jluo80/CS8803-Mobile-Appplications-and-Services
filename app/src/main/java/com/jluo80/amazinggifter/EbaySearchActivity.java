package com.jluo80.amazinggifter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

public class EbaySearchActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private static final String TAG = EbaySearchActivity.class.getName();

    public final static String EBAY_APP_ID = "JiahaoLu-AmazingG-PRD-099e85f71-bce780ef";
    public final static String EBAY_FINDING_SERVICE_URI = "http://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME="
            + "{operation}&SERVICE-VERSION={version}&SECURITY-APPNAME="
            + "{applicationId}&GLOBAL-ID={globalId}&keywords={keywords}"
            + "&paginationInput.entriesPerPage={maxresults}"
            + "&outputSelector=PictureURLLarge";
    public static final String SERVICE_VERSION = "1.0.0";
    public static final String OPERATION_NAME = "findItemsByKeywords";
    public static final String GLOBAL_ID = "EBAY-US";
    public final static int REQUEST_DELAY = 3000;
    public final static int MAX_RESULTS = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebay_search);

        // Set the toolbar of the add gifts activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Set onClickListener for the image button to find the products by keyword.
        ImageView searchImageView = (ImageView) findViewById(R.id.search);

        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchView = (EditText) findViewById(R.id.search_view);
                final String tag = searchView.getText().toString();
                String url = null;
                boolean loading = true;

                try{
                    url = createAddress(java.net.URLEncoder.encode(tag, "UTF-8"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    ArrayList<Gift> giftsArray = processResponse(response);

                                    // Initialize recycler view
                                    mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                                    mRecyclerView.setLayoutManager(new LinearLayoutManager(EbaySearchActivity.this));
                                    mRecyclerView.setAdapter(new EbayRecyclerAdapter(EbaySearchActivity.this, giftsArray));
                                } catch(Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

                // Add a request (in this example, called stringRequest) to your RequestQueue.
                MySingleton.getInstance(EbaySearchActivity.this).addToRequestQueue(stringRequest);
            }
        });
    }

    public String createAddress(String tag) {

        //substitute token
        String address = EBAY_FINDING_SERVICE_URI;
        address = address.replace("{version}", SERVICE_VERSION);
        address = address.replace("{operation}", OPERATION_NAME);
        address = address.replace("{globalId}", GLOBAL_ID);
        address = address.replace("{applicationId}", EBAY_APP_ID);
        address = address.replace("{keywords}", tag);
        address = address.replace("{maxresults}", MAX_RESULTS + "");

        return address;

    }

    public ArrayList<Gift> processResponse(String response) throws Exception {

        XPath xpath = XPathFactory.newInstance().newXPath();
        InputStream is = new ByteArrayInputStream(response.getBytes("UTF-8"));
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = domFactory.newDocumentBuilder();

        Document doc = builder.parse(is);
        XPathExpression ackExpression = xpath.compile("//findItemsByKeywordsResponse/ack");
        XPathExpression itemExpression = xpath.compile("//findItemsByKeywordsResponse/searchResult/item");

        String ackToken = (String) ackExpression.evaluate(doc, XPathConstants.STRING);
        print("ACK from ebay API :: ", ackToken);
        if (!ackToken.equals("Success")) {
            throw new Exception(" service returned an error");
        }

        NodeList nodes = (NodeList) itemExpression.evaluate(doc, XPathConstants.NODESET);

        SharedPreferences mSharedPreferences = getSharedPreferences("test", Activity.MODE_PRIVATE);
        String facebookId = mSharedPreferences.getString("facebookId", "");

        Intent intent = EbaySearchActivity.this.getIntent();
        String dueDate = intent.getStringExtra("due_date");
//        String title = intent.getStringExtra("title");
        String reason = intent.getStringExtra("reason");
        String description = intent.getStringExtra("description");
        String postTime = intent.getStringExtra("post_time");

        ArrayList<Gift> giftsArray = new ArrayList<>();
        for (int i = 0; i < nodes.getLength(); i++) {

            Node node = nodes.item(i);
            String itemId = (String) xpath.evaluate("itemId", node, XPathConstants.STRING);
            String name = (String) xpath.evaluate("title", node, XPathConstants.STRING);
            String category = (String) xpath.evaluate("primaryCategory/categoryName", node, XPathConstants.STRING);
            String itemUrl = (String) xpath.evaluate("viewItemURL", node, XPathConstants.STRING);
            String pictureUrl = (String) xpath.evaluate("pictureURLLarge", node, XPathConstants.STRING);
            String temp = (String) xpath.evaluate("sellingStatus/currentPrice", node, XPathConstants.STRING);
            double price = Double.parseDouble(temp);
            giftsArray.add(new Gift(category, dueDate, facebookId, itemId, itemUrl, name, pictureUrl, postTime, price, 0, reason, facebookId));
        }
        is.close();
        return giftsArray;
    }

    private void print(String name, String value) {
        System.out.println(name + "::" + value);
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
