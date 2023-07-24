package MobileApplication.Group.Theme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import MobileApplication.Group.R;
import MobileApplication.Group.databinding.ActivityFlightBinding;

import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.util.Log;


public class AviationStackFlightTracker extends AppCompatActivity {
    private RequestQueue queue;
    private List<String> airlineNames;
    private RecyclerView recyclerView;
    private FlightAdapter flightAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityFlightBinding binding = ActivityFlightBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        airlineNames = new ArrayList<>();
        recyclerView = findViewById(R.id.flightRecyclerView);
        flightAdapter = new FlightAdapter(airlineNames);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(flightAdapter);

        Cache cache = new DiskBasedCache(getCacheDir(), 1024*1024);
        Network network = new BasicNetwork(new HurlStack());

        queue = new RequestQueue(cache, network);
        queue.start();

        binding.searchButton.setOnClickListener(click -> {
            String airportCode = binding.flightSearch.getText().toString();

            String url = "http://api.aviationstack.com/v1/flights?access_key=88047607053ddc44d85be397fed16b28&dep_iata=" +
                    URLEncoder.encode(airportCode);

            StringRequest request = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


//                            airlineNames.clear();
//                            ExecutorService executor = Executors.newSingleThreadExecutor();
//                            executor.execute(() -> {
                            runOnUiThread(()->{
//                                JSONArray flightDataArray = null;


                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray flightDataArray = jsonObject.getJSONArray("data");
                                    int length = flightDataArray.length();

                                    for (int i = 0; i < length; i++) {
                                        JSONObject thisObj = flightDataArray.getJSONObject(i);
                                        JSONObject airlineObject = thisObj.getJSONObject("airline");
                                        String airlineName = airlineObject.getString("name");
                                        int notimportant = 0;
                                    }
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }

//                                for (int i = 0; i < flightDataArray.length(); i++) {
//                                    JSONObject flightData = flightDataArray.getJSONObject(i);
//                                    JSONObject airlineObject = flightData.getJSONObject("airline");
//                                    String airlineName = airlineObject.getString("name");
//
//                                    airlineNames.add(airlineName);
//                                }
//                                runOnUiThread(()->{
//                                    airlineNames.clear();
//                                    flightAdapter.notifyDataSetChanged();
//                                });

                            });


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Handle error
                    Log.e("API Error", "Error occurred during API request: " + error.getMessage());
                    // error.printStackTrace();
                }
            });

            queue.add(request);
        });
    }

    private static class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.FlightViewHolder> {
        private List<String> airlineNames;

        public FlightAdapter(List<String> airlineNames) {
            this.airlineNames = airlineNames;
        }

        @NonNull
        @Override
        public FlightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flight_item_layout, parent, false);
            return new FlightViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FlightViewHolder holder, int position) {
            String airlineName = airlineNames.get(position);
            holder.airlineNameTextView.setText(airlineName);
        }

        @Override
        public int getItemCount() {
            return airlineNames.size();
        }

        public static class FlightViewHolder extends RecyclerView.ViewHolder {
            TextView airlineNameTextView;

            public FlightViewHolder(View itemView) {
                super(itemView);
                airlineNameTextView = itemView.findViewById(R.id.airlineNameTextView);
            }
        }
    }
}

