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

public class MenuActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Ride>> {

    private static final int LOADER_ID = 1;
    private static final String REQUEST_URL =
            "https://thzpf4wmki.execute-api.us-west-2.amazonaws.com/Dev/trip-earning";

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

            getLoaderManager().initLoader(LOADER_ID, null, (android.app.LoaderManager.LoaderCallbacks<Object>) this);

        }else {
            Toast.makeText(MenuActivity.this, "No network found", Toast.LENGTH_LONG).show();
        }

        TextView earnings = findViewById(R.id.earnings_month);


    }

    @NonNull
    @Override
    public Loader<List<Ride>> onCreateLoader(int id, @Nullable Bundle args) {
        return new RideLoader(this, REQUEST_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Ride>> loader, List<Ride> data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Ride>> loader) {

    }

    public class RideLoader extends AsyncTaskLoader<List<Ride>> {

        private String[] mURLs;

        public RideLoader(Context context, String... urls) {
            super(context);
            mURLs = urls;
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }

        @Override
        public List<Ride> loadInBackground() {
            if (mURLs.length < 1 || mURLs[0] == null) {
                return null;
            }

            List<Ride> result = QueryUtils.fetchRideData(mURLs[0]);
            return result;

        }


    }
}