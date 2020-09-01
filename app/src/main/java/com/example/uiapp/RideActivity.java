package com.example.uiapp;

import androidx.fragment.app.FragmentActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RideActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public String currRideDate = "2000-01-01 00:00:00";
    Ride currRide;
    public static String earn_curr;
    public final List<Ride> rides = new ArrayList<>();
    TextView earn;
    TextView dist;
    TextView dur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        earn = findViewById(R.id.earn_ride);
        dist = findViewById(R.id.dist);
        dur = findViewById(R.id.dur);

        RideAsyncTask asyncTask = new RideAsyncTask();
        asyncTask.execute();

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }

    private class RideAsyncTask extends AsyncTask<Void, Void, List<Ride>> {

        @Override
        protected List<Ride> doInBackground(Void... voids) {
            RequestQueue queue = Volley.newRequestQueue(RideActivity.this);
            final String url = "https://ysv7zypxt5.execute-api.us-west-2.amazonaws.com/dev/rides?user_id="
                    + FirebaseAuth.getInstance().getUid() + "&datetime=" + "2020-06-01" + "00:00:00";
            Log.d("URL", url);

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

                                    if(ride.getDatetime().compareTo(currRideDate) > 0){
                                        currRideDate = ride.getDatetime();
                                        currRide = ride;
                                        Log.d("Ride", "date" + currRideDate);
                                    }

                                    earn.setText("$" + currRide.getEarning());
                                    dist.setText("" + currRide.getDistance());
                                    dur.setText("" +  currRide.getDuration());
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

    }
}