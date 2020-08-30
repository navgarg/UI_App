package com.example.uiapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Ride>> {

    String currDate;
    TextView earning_tv_menu;
    TextView earning_tv_earn;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Log.d("MainActivity", "in onCreate");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        currDate = sdf.format(new Date());

        Log.d("user_id", FirebaseAuth.getInstance().getUid() + "");


        ImageView toggle = findViewById(R.id.toggle_menu);
        toggle.setElevation(0);
        ImageView earning = findViewById(R.id.earning);
        earning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, EarningsActivity.class));
            }
        });

        ImageView ride = findViewById(R.id.add_ride_field);
        ride.setOnClickListener(new View.OnClickListener() {
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

        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()) {
            getLoaderManager().initLoader(1, null, (android.app.LoaderManager.LoaderCallbacks<Object>) this);
        }else {
            Toast.makeText(MenuActivity.this, "No network found", Toast.LENGTH_LONG).show();
        }

    }

    public List<Ride> fetchRides() {

        RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "https://ysv7zypxt5.execute-api.us-west-2.amazonaws.com/dev/rides?user_id="
                + FirebaseAuth.getInstance().getUid() + "&datetime=" + currDate;


        final List<Ride> rides = new ArrayList<>();
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response: ", String.valueOf(response));
                        try {
                            JSONArray items_array = response.getJSONArray("Items");
                            for (int i = 0; i < items_array.length(); i++) {
                                JSONObject currentRide = items_array.getJSONObject(i);
                                int ride_id = currentRide.getInt("ride_id");
                                int earning = currentRide.getInt("earning");
                                int distance = currentRide.getInt("distance");
                                String duration = currentRide.getString("duration");
                                String datetime = currentRide.getString("datetime");
                                String user_id = currentRide.getString("user_id");
                                Ride ride = new Ride(earning,distance, duration, datetime, user_id ,ride_id);
                                rides.add(ride);
                            }
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

        return rides;
    }

    public class RideLoader extends AsyncTaskLoader<List<Ride>> {

    public RideLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Ride> loadInBackground() {
        return fetchRides();

    }


}

    @NonNull
    @Override
    public Loader<List<Ride>> onCreateLoader(int id, @Nullable Bundle args) {
        return new RideLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Ride>> loader, List<Ride> rides) {

        earning_tv_menu = findViewById(R.id.earnings_month_act_menu);
        earning_tv_earn = findViewById(R.id.earnings_month_act_earning);
        int earning = 0;
        for(Ride ride : rides){
            earning += ride.getEarning();
        }
        earning_tv_menu.setText("$" + earning);
        earning_tv_earn.setText("$" + earning);

    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {

        earning_tv_earn.setText("$0.00");
        earning_tv_menu.setText("$0.00");
    }
}