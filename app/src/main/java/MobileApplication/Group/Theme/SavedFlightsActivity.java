package MobileApplication.Group.Theme;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import MobileApplication.Group.R;

public class SavedFlightsActivity extends AppCompatActivity implements FlightAdapter.FlightClickListener {

    private RecyclerView recyclerView;
    private FlightAdapter flightAdapter;
    private List<Flight> savedFlights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_flights);

        recyclerView = findViewById(R.id.savedFlightsRecyclerView);
        savedFlights = new ArrayList<>();


        flightAdapter = new FlightAdapter(savedFlights, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(flightAdapter);

        // Set the click listener on the adapter
        flightAdapter.setFlightClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSavedFlights();
    }

    private void loadSavedFlights() {
        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute(() -> {
            FlightDatabase flightDatabase = Room.databaseBuilder(this, FlightDatabase.class, "flight-database").build();
            FlightDao flightDao = flightDatabase.flightDao();
            List<Flight> flights = flightDao.getAllFlight();
            runOnUiThread(() -> {
                savedFlights.clear();
                savedFlights.addAll(flights);
                flightAdapter.notifyDataSetChanged();
            });
        });
    }



    @Override
    public void onFlightClick(Flight flight) {
        // Show the FlightDetailsFragment with the selected flight's details
//        FlightDetailsFragment detailsFragment = new FlightDetailsFragment(flight);
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.fragmentLocation, detailsFragment) // Use fragmentLocation2
//                .addToBackStack(null)
//                .commit();
        onFlightClickFromSavedFlight(flight);
    }

    public void onFlightClickFromSavedFlight(Flight flight) {
        // Show the FlightDetailsFragment with the selected flight's details
        FlightDetailsFragment detailsFragment = new FlightDetailsFragment(flight);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentLocation, detailsFragment) // Use fragmentLocation
                .addToBackStack(null)
                .commit();
    }

}
