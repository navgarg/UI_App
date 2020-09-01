package com.example.uiapp;

import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class RideActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    TextView earn;
    TextView dist;
    TextView dur;
    TextView rideid;
    TextView datetime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        earn = findViewById(R.id.earn_ride);
        earn.setText("$" + MenuActivity.currRide.getEarning());
        dist = findViewById(R.id.dist);
        dist.setText("" + MenuActivity.currRide.getDistance());
        dur = findViewById(R.id.dur);
        dur.setText("" + MenuActivity.currRide.getDuration());
        rideid = findViewById(R.id.ride_id);
        rideid.setText("Ride ID: " + MenuActivity.currRide.getRideId());
        datetime = findViewById(R.id.datetime);
        String date_time = MenuActivity.currRide.getDatetime();
        Log.d("datetime", date_time);
        String year = date_time.substring(0, 4);
        String month = date_time.substring(5, 7);
        String date = date_time.substring(8, 10);
        switch (month){
            case "01":
                month = "January";
                break;
            case "02":
                month = "February";
                break;
            case "03":
                month = "March";
                break;
            case "04":
                month = "April";
                break;
            case "05":
                month = "May";
                break;
            case "06":
                month = "June";
                break;
            case "07":
                month = "July";
                break;
            case "08":
                month = "August";
                break;
            case "09":
                month = "September";
                break;
            case "10":
                month = "October";
                break;
            case "11":
                month = "November";
                break;
            case "12":
                month = "December";
                break;
        }
        datetime.setText(date + " " + month + ", " + year);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }
}