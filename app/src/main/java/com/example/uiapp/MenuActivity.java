package com.example.uiapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MenuActivity extends AppCompatActivity {

          //  "https://thzpf4wmki.execute-api.us-west-2.amazonaws.com/Dev/trip-earning?Trip_ID=1";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ImageView toggle = findViewById(R.id.toggle_menu);
        toggle.setElevation(0);
        ImageView earning = findViewById(R.id.earning);
        earning.setOnClickListener(new View.OnClickListener() {
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
            //getLoaderManager().initLoader(LOADER_ID, null, );
        }else {
            Toast.makeText(MenuActivity.this, "No network found", Toast.LENGTH_LONG).show();
        }
    }
}