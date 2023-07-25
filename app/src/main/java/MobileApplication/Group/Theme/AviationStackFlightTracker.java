package MobileApplication.Group.Theme;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

import MobileApplication.Group.databinding.ActivityFlightBinding;


public class AviationStackFlightTracker extends AppCompatActivity {

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityFlightBinding binding = ActivityFlightBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        queue = Volley.newRequestQueue(this);

        binding.searchButton.setOnClickListener(click -> {
            String airportCode = binding.flightSearch.getText().toString();
            try {
                String url = "http://api.aviationstack.com/v1/flights?access_key=fe6a4de5950698ecf53a949f3d155102&dep_iata=" +
                        URLEncoder.encode(airportCode, "UTF-8");

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        response -> {
                            Log.d("API Response", response.toString());
                            try {
                                JSONArray data = response.getJSONArray("data");
                                boolean foundDepartureFlight = false;

                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject flight = data.getJSONObject(i);
                                    JSONObject departure = flight.getJSONObject("departure");
                                    String departureAirportIata = departure.getString("iata");

                                    if (airportCode.equalsIgnoreCase(departureAirportIata)) {
                                        String airlineName = flight.getJSONObject("airline").getString("name");
                                        String flightNumber = flight.getJSONObject("flight").getString("number");
                                        String departureAirport = departure.getString("airport");
                                        String scheduledDepartureTime = departure.getString("scheduled");

                                        String flightDetails = "Flight: " + airlineName + " " + flightNumber +
                                                "\nDeparture Airport: " + departureAirport +
                                                "\nScheduled Departure Time: " + scheduledDepartureTime;

                                        // Update the ResultTextView on the main UI thread
                                        runOnUiThread(() -> {
                                            binding.resultRecyclerView.setText(flightDetails);
                                            binding.resultRecyclerView.setVisibility(View.VISIBLE);
                                        });

                                        foundDepartureFlight = true;
                                        break;
                                    }
                                }

                                if (!foundDepartureFlight) {
                                    String noFlightMessage = "No departure flight found for airport code " + airportCode;
                                    // Update the ResultTextView on the main UI thread
                                    runOnUiThread(() -> {
                                        binding.resultRecyclerView.setText(noFlightMessage);
                                        binding.resultRecyclerView.setVisibility(View.VISIBLE);
                                    });
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }, error -> {
                    // Handle error
                    Log.e("API Error", error.toString());
                    // Update the ResultTextView on the main UI thread
                    runOnUiThread(() -> {
                        Toast.makeText(this, "API Request Error", Toast.LENGTH_SHORT).show();
                    });
                });

                queue.add(request);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Toast.makeText(AviationStackFlightTracker.this, "Unsupported Encoding Exception", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
