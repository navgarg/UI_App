package com.example.uiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class EarningsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earnings);

        ImageView ride = findViewById(R.id.ride);
        ride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EarningsActivity.this, RideActivity.class));
            }
        });

        TextView earning = findViewById(R.id.earnings_month_act_earning);
        earning.setText(MenuActivity.text);

        TextView earn = findViewById(R.id.earnings_ride_act_earning);
        earn.setText(MenuActivity.curr_earn);
    }
}