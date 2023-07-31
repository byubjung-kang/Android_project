package MobileApplication.Group.Theme;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import MobileApplication.Group.R;
import MobileApplication.Group.databinding.ActivityFlightBinding;


public class AviationStackFlightTracker extends AppCompatActivity implements FlightAdapter.FlightClickListener {

    private RequestQueue queue;
    private FlightAdapter flightAdapter;
    private List<Flight> flightDetailsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityFlightBinding binding = ActivityFlightBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
                String url = "http://api.aviationstack.com/v1/flights?access_key=5b76bd71ac067b87be25a454d51dcad5&dep_iata=" +
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
                                        String departureAirportIata = departure.getString("iata");

                                        if (airportCode.equalsIgnoreCase(departureAirportIata)) {
                                            String airlineName = flightData.getJSONObject("airline").getString("name");
                                            String flightNumber = flightData.getJSONObject("flight").getString("number");
                                            String departureAirport = departure.getString("airport");
                                            String scheduledDepartureTime = departure.getString("scheduled");

                                            // Create a new Flight object and add it to the list
                                            Flight flight = new Flight(airlineName, flightNumber, departureAirport, scheduledDepartureTime, "");
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
        // Show the FlightDetailsFragment
        FlightDetailsFragment detailsFragment = FlightDetailsFragment.newInstance(
                flight.getAirlineName(), flight.getFlightNumber(), flight.getDestinationAirport(),
                flight.getDepartureTime(), flight.getTerminal(), flight.getGate(), flight.getDelay());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentLocation, detailsFragment)
                .addToBackStack(null)
                .commit();
    }


}
