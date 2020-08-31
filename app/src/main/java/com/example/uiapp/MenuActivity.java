package com.example.uiapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    String currDate;
    public static String text;
    TextView earning_tv_menu;
    TextView earning_tv_earn;
    String date = "2000-01-01 00:00:00:";
    public static String latestEarn;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        earning_tv_menu = findViewById(R.id.earnings_month_act_menu);

        Log.d("MainActivity", "in onCreate");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        currDate = sdf.format(new Date());

        Log.d("user_id", FirebaseAuth.getInstance().getUid() + "");


        ImageView toggle = findViewById(R.id.toggle_menu);
        toggle.setElevation(0);
        ImageView earning_iv = findViewById(R.id.earning);
        earning_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, EarningsActivity.class));
            }
        });

        ImageView ride_iv = findViewById(R.id.add_ride_field);
        ride_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, AddRide.class));
            }
        });


        Button logout = findViewById(R.id.logout_button);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MenuActivity.this, LoginActivity.class));
                finish();
            }
        });

        earning_tv_menu = findViewById(R.id.earnings_month_act_menu);
        earning_tv_earn = findViewById(R.id.earnings_month_act_earning);
//
//        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
//
//        if(networkInfo != null && networkInfo.isConnected()) {
//            //getLoaderManager().initLoader(1, null, (android.app.LoaderManager.LoaderCallbacks<Object>) this);
//            RideAsyncTask.execute();
//
//        }else {
//            Toast.makeText(MenuActivity.this, "No network found", Toast.LENGTH_LONG).show();
//        }

        RideAsyncTask asyncTask = new RideAsyncTask();
        asyncTask.execute();
    }

    public List<Ride> fetchRides() {

        RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "https://ysv7zypxt5.execute-api.us-west-2.amazonaws.com/dev/rides?user_id="
                + FirebaseAuth.getInstance().getUid() + "&datetime=" + "2020-06-01" + "00:00:00";
        Log.d("URL", url);


        final List<Ride> rides = new ArrayList<>();
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d("Response: ", String.valueOf(response));
                        try {
                            JSONArray items_array = response.getJSONArray("Items");
                            for (int i = 0; i < items_array.length(); i++) {
                                JSONObject currentRide = items_array.getJSONObject(i);
                                //Log.d("Object", String.valueOf(currentRide));
                                int ride_id = currentRide.getInt("ride_id");
                                int earning = currentRide.getInt("earning");
                                int distance = currentRide.getInt("distance");
                                String duration = currentRide.getString("duration");
                                String datetime = currentRide.getString("datetime");
                                String user_id = currentRide.getString("user_id");
                                Ride ride = new Ride(earning,distance, duration, datetime, user_id ,ride_id);
                                rides.add(ride);
                            }
                            //Log.d("Rides in onResponse", String.valueOf(rides));

                            int earn = 0;
                            for(Ride currRide : rides){
                                earn += currRide.getEarning();
                            }

                            Log.d("MenuActivity: Earning", String.valueOf(earn));
                            text = "$" + Integer.toString(earn);
                            Log.d("text", text);
                            earning_tv_menu.setText("text");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Response: ", String.valueOf(error));
                    }
                }
        );
        queue.add(getRequest);

        Log.d("Rides outside", String.valueOf(rides));
        return rides;
    }

    private class RideAsyncTask extends AsyncTask<Void, Void, List<Ride>> {

        @Override
        protected List<Ride> doInBackground(Void... voids) {
            return fetchRides();
        }

    }
}