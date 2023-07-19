package MobileApplication.Group.Theme;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import MobileApplication.Group.R;
import MobileApplication.Group.databinding.ActivityFlightBinding;

import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



public class AviationStackFlightTracker extends AppCompatActivity {
    private static final String BASE_URL = "http://api.aviationstack.com/v1/flights";
    private static final String ACCESS_KEY = "88047607053ddc44d85be397fed16b28";

    private EditText airportCodeText;
    protected ActivityFlightBinding binding;
    private ChatRoomViewModel chatModel;
    private ArrayList<FlightMessage> messages;
    private FlightAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFlightBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();

        if (messages == null) {
            chatModel.messages.setValue(messages = new ArrayList<>());
        }

        airportCodeText = binding.textInput;

        binding.searchButton.setOnClickListener(v -> {
            String airportCode = airportCodeText.getText().toString();
            if (!airportCode.isEmpty()) {
                String searchUrl = BASE_URL + "?access_key=" + ACCESS_KEY + "&dep_iata=" + airportCode;
                new FlightSearchTask().execute(searchUrl);
            }
        });

        adapter = new FlightAdapter(messages);
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
        binding.recycleView.setAdapter(adapter);
    }

    private class FlightSearchTask extends AsyncTask<String, Void, List<FlightMessage>> {
        @Override
        protected List<FlightMessage> doInBackground(String... urls) {
            String searchUrl = urls[0];
            List<FlightMessage> flightList = new ArrayList<>();

            try {
                URL url = new URL(searchUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                flightList = parseFlightData(response.toString());

                reader.close();
                inputStream.close();
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return flightList;
        }

        @Override
        protected void onPostExecute(List<FlightMessage> flightList) {
            super.onPostExecute(flightList);
            messages.clear();
            messages.addAll(flightList);
            adapter.notifyDataSetChanged();
        }

        private List<FlightMessage> parseFlightData(String jsonData) {
            List<FlightMessage> flights = new ArrayList<>();

            try {
                JSONObject jsonObject = new JSONObject(jsonData);
                JSONArray data = jsonObject.getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject flightObject = data.getJSONObject(i);
                    String destination = flightObject.getJSONObject("arrival").getString("iata");
                    String terminal = flightObject.has("terminal") ? flightObject.getString("terminal") : "N/A";
                    String gate = flightObject.has("gate") ? flightObject.getString("gate") : "N/A";
                    String delay = flightObject.getJSONObject("departure").optString("delay", "");


                    FlightMessage flight = new FlightMessage(destination, terminal, gate, delay);
                    flights.add(flight);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return flights;
        }
    }

    private static class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.FlightViewHolder> {
        private List<FlightMessage> flightList;

        public FlightAdapter(List<FlightMessage> flights) {
            flightList = flights;
        }

        @NonNull
        @Override
        public FlightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // Inflate the item layout for each row
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seeflight_message, parent, false);
            return new FlightViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FlightViewHolder holder, int position) {
            // Bind the flight data to the view holder
            FlightMessage flight = flightList.get(position);
            holder.destinationTextView.setText(flight.getDestination());
            holder.terminalTextView.setText(flight.getTerminal());
            holder.gateTextView.setText(flight.getGate());
            holder.delayTextView.setText(flight.getDelay());
        }

        @Override
        public int getItemCount() {
            return flightList.size();
        }

        static class FlightViewHolder extends RecyclerView.ViewHolder {
            TextView destinationTextView;
            TextView terminalTextView;
            TextView gateTextView;
            TextView delayTextView;

            public FlightViewHolder(@NonNull View itemView) {
                super(itemView);
                destinationTextView = itemView.findViewById(R.id.Destination);
                terminalTextView = itemView.findViewById(R.id.Terminal);
                gateTextView = itemView.findViewById(R.id.Gate);
                delayTextView = itemView.findViewById(R.id.Delay);
            }
        }
    }
}
