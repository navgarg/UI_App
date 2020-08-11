package com.example.uiapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MenuActivity extends AppCompatActivity{

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ImageView toggle = findViewById(R.id.toggle_menu);
        toggle.setElevation(0);

        ImageView earning_img = findViewById(R.id.earning);
        earning_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, EarningsActivity.class));
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

//            getLoaderManager().initLoader(1, null, (android.app.LoaderManager.LoaderCallbacks<Object>) this);

        }else {
            Toast.makeText(MenuActivity.this, "No network found", Toast.LENGTH_LONG).show();
        }

        TextView earnings_menu = findViewById(R.id.earnings_month_act_menu);
        TextView earnings_earn = findViewById(R.id.earnings_month_act_earning);
        DDB ddb = new DDB(this);
        List<Ride> rides = ddb.read();
        int earning = 0;
        for (Ride ride : rides) {
            if(ride.getMonth() == "August"){
                earning += ride.getEarning();
            }
        }
        earnings_menu.setText(Integer.toString(earning));
        earnings_earn.setText(Integer.toString(earning));
    }

}