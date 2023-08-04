package MobileApplication.Group.Theme;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import MobileApplication.Group.R;



public class AviationStackFlightTracker extends AppCompatActivity implements FlightAdapter.FlightClickListener {

    private RequestQueue queue;
    private FlightAdapter flightAdapter;
    private List<Flight> flightDetailsList;
//    protected ArrayList<Flight> theFlight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityFlightBinding binding = ActivityFlightBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


     setSupportActionBar(binding.myToolbar);



        // Initialize the RecyclerView and its adapter
        RecyclerView recyclerView = findViewById(R.id.resultRecyclerView);
        flightDetailsList = new ArrayList<>();
        flightAdapter = new FlightAdapter(flightDetailsList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(flightAdapter);

        flightAdapter.setFlightClickListener(this); // Set the FlightClickListener

        queue = Volley.newRequestQueue(this);

        binding.searchButton.setOnClickListener(click -> {
            String airportCode = binding.flightSearch.getText().toString();
            try {
                String url = "http://api.aviationstack.com/v1/flights?access_key=9ad126b64d23dfc794eab0d96e9c3f6d&dep_iata=" +
                        URLEncoder.encode(airportCode, "UTF-8");

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("API Response", response.toString());
                                try {
                                    JSONArray data = response.getJSONArray("data");
                                    boolean foundDepartureFlight = false;
                                    flightDetailsList.clear(); // Clear the previous flight details before adding new ones

                                    for (int i = 0; i < data.length(); i++) {
                                        JSONObject flightData = data.getJSONObject(i);
                                        JSONObject departure = flightData.getJSONObject("departure");
                                        JSONObject arrival = flightData.getJSONObject("arrival");
                                        String departureAirportIata = departure.getString("iata");

                                        if (airportCode.equalsIgnoreCase(departureAirportIata)) {
                                            String airlineName = flightData.getJSONObject("airline").getString("name");
                                            String flightNumber = flightData.getJSONObject("flight").getString("number");

                                            String departureAirport = departure.getString("airport");
                                            String departureTime = departure.getString("scheduled");
                                            String destinationAirport = arrival.getString("airport");

                                            // Extract additional flight details (gate, delay, terminal) from the JSON response
                                            String gate = departure.optString("gate", "N/A");
                                            String delay = departure.optString("delay", "N/A");
                                            String terminal = departure.optString("terminal", "N/A");

                                            // Create a new Flight object and set the additional details
                                            Flight flight = new Flight(airlineName,flightNumber,destinationAirport,terminal,delay,gate);

                                            flight.getAirlineName();
                                            flight.getFlightNumber();
                                            flight.getDestinationAirport();
                                            flight.getTerminal();
                                            flight.getDelay();
                                            flight.getGate();


                                            flightDetailsList.add(flight);
                                            foundDepartureFlight = true;

                                        }
                                    }


                                    // Notify the adapter about data changes
                                    flightAdapter.notifyDataSetChanged();

                                    if (!foundDepartureFlight) {
                                        String noFlightMessage = "No departure flight found for airport code " + airportCode;
                                        Log.d("FlightTracker", noFlightMessage);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.e("FlightTracker", "Error parsing JSON response");
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("API Error", error.toString());
                                runOnUiThread(() -> {
                                    Toast.makeText(AviationStackFlightTracker.this, "API Request Error", Toast.LENGTH_SHORT).show();
                                });
                            }
                        });

                // Add the request to the RequestQueue
                queue.add(request);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Toast.makeText(AviationStackFlightTracker.this, "Unsupported Encoding Exception", Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public void onFlightClick(Flight flight) {
        // Show the FlightDetailsFragment with the selected flight's details
        FlightDetailsFragment detailsFragment = new FlightDetailsFragment(flight);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentLocation, detailsFragment)
                .addToBackStack(null)
                .commit();
    }


//
    public void onSavedFlightsButtonClick(View view) {
            Intent intent = new Intent(this, SavedFlightsActivity.class);
            startActivity(intent);
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_help) {
            showHelpDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showHelpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Help");
        builder.setMessage("Instructions:\n\n" +
                "- Enter the airport code in the search box.\n" +
                "- Tap on a flight to view its details.\n" +
                "- Use the Save and Delete buttons to manage saved flights.");
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }



}

