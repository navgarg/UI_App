package com.example.uiapp;

import androidx.appcompat.app.AppCompatActivity;

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
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AddRide extends AppCompatActivity {

    int int_random;
    String currDate;
    EditText dist, dur, earn;
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

        dist = findViewById(R.id.edit_text_dist);
        dur = findViewById(R.id.edit_text_dur);
        earn = findViewById(R.id.edit_text_earn);

        Button post = findViewById(R.id.post);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                distance = dist.getText().toString();
                earning = earn.getText().toString();
                duration = dur.getText().toString();
                if (!(distance.equals("")) && !(earning.equals("")) && !(duration.equals(""))) {
                    RideLoader rideLoader = new RideLoader();
                    rideLoader.execute();
                    dist.setText("");
                    dur.setText("");
                    earn.setText("");
                    Toast.makeText(AddRide.this, "Addition successful!", Toast.LENGTH_LONG).show();
                } else {
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
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("Response", response);
                            }
                        },
                        new Response.ErrorListener() {
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

        @Override
        protected Void doInBackground(Void... voids) {
            RequestQueue requestQueue = Volley.newRequestQueue(AddRide.this);
            JSONObject jsonBodyObj = new JSONObject();
            try {
                jsonBodyObj.put("trip_id", Integer.toString(int_random));
                jsonBodyObj.put("user_id", FirebaseAuth.getInstance().getUid());
                jsonBodyObj.put("distance", distance);
                jsonBodyObj.put("earning", earning);
                jsonBodyObj.put("datetime", currDate);
                jsonBodyObj.put("duration", duration);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            final String requestBody = jsonBodyObj.toString();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Response", String.valueOf(response));
                        }
                    },
                    new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Error: ", error.getMessage());
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }


                @Override
                public byte[] getBody() {
                    try {
                        return requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        Log.d("Error", "Unsupported Encoding");
                        return null;
                    }
                }


            };

            requestQueue.add(jsonObjectRequest);
            return null;
        }
    }
}