package com.example.uiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AddRide extends AppCompatActivity {

    int int_random;
    String currDate;
    String distance = "", earning = "", duration = "";
    final String url = "https://ysv7zypxt5.execute-api.us-west-2.amazonaws.com/dev/rides";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ride);

        Random rand = new Random();
        int upper = 10000;
        int_random = rand.nextInt(upper);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        currDate = sdf.format(new Date());

        EditText dist = findViewById(R.id.edit_text_dist);
        EditText dur = findViewById(R.id.edit_text_dur);
        EditText earn = findViewById(R.id.edit_text_earn);

        distance = dist.getText().toString();
        earning = earn.getText().toString();
        duration = dur.getText().toString();

        distance = "7";
        earning = "300";
        duration = "3h 4m";

        Log.d("Values", distance + " - " + earning + " - " + duration + " - " + FirebaseAuth.getInstance().getUid());

        Button post = findViewById(R.id.post);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(distance.equals("")) && !(earning.equals("")) && !(duration.equals(""))){
                    //TODO: kick off loader
                    RideLoader rideLoader = new RideLoader();
                    rideLoader.execute();
                }
                else{
                    Toast.makeText(AddRide.this, "Fill all fields", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(AddRide.this);
                StringRequest dr = new StringRequest(Request.Method.DELETE, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                Log.d("Response", response);
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Error.Response", String.valueOf(error));
                            }
                        }
                );
                queue.add(dr);
            }
        });



    }
    public class RideLoader extends AsyncTask<Void, Void, Void> {

        public RideLoader() {
            super();
        }

//        @Override
//        public Void loadInBackground() {
//            RequestQueue queue = Volley.newRequestQueue(AddRide.this);
//
//            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
//                    new Response.Listener<String>()
//                    {
//                        @Override
//                        public void onResponse(String response) {
//                            Log.d("Response", response);
//                        }
//                    },
//                    new Response.ErrorListener()
//                    {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            Log.d("Error.Response", String.valueOf(error));
//                        }
//                    }
//            ) {
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<>();
//                    params.put("trip_id", Integer.toString(int_random));
//                    params.put("user_id", FirebaseAuth.getInstance().getUid());
//                    params.put("distance", distance);
//                    params.put("earning", earning);
//                    params.put("datetime", currDate);
//                    params.put("duration", duration);
//                    return super.getParams();
//                }
//
//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<>();
//                    params.put("Content-Type", "application.json");
//                    return params;
//                }
//            };
//            queue.add(postRequest);
//            return null;
//        }

        @Override
        protected Void doInBackground(Void... voids) {
            RequestQueue queue = Volley.newRequestQueue(AddRide.this);

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            Log.d("Response", response);
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Error.Response", String.valueOf(error));
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("trip_id", Integer.toString(int_random));
                    params.put("user_id", FirebaseAuth.getInstance().getUid());
                    params.put("distance", distance);
                    params.put("earning", earning);
                    params.put("datetime", currDate);
                    params.put("duration", duration);
                    return super.getParams();
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type", "application.json");
                    return params;
                }
            };
            queue.add(postRequest);
            return null;
        }
    }
}