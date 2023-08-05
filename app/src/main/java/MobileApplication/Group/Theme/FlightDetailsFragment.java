package MobileApplication.Group.Theme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import MobileApplication.Group.databinding.FragmentFlightDetailsBinding;


/**
 * A Fragment that displays details of a selected flight and provides options to save it to the database.
 */
public class FlightDetailsFragment extends Fragment {

    private FragmentFlightDetailsBinding binding;
    private Flight selectedFlight;

    /**
     * Constructs a FlightDetailsFragment with the selected flight.
     *
     * @param message The selected flight to display details for.
     */
    public FlightDetailsFragment(Flight message) {
        selectedFlight = message;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFlightDetailsBinding.inflate(inflater, container, false);
        super.onCreateView(inflater, container, savedInstanceState);

        if (selectedFlight != null) {
            binding.flightDetailsTextView.setText("Airline: " + selectedFlight.getAirlineName());
            binding.flightNumberTextView.setText("Flight Number: " + selectedFlight.getFlightNumber());
            binding.destinationTextView.setText("Destination: " + selectedFlight.getDestinationAirport());
            binding.terminalTextView.setText("Terminal: " + selectedFlight.getTerminal());
            binding.delayTextView.setText("Delay: " + selectedFlight.getDelay());
            binding.gateTextView.setText("Gate: " + selectedFlight.getGate());


            // Set OnClickListener for the "Save to Database" button
            binding.saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveFlightToDatabase(selectedFlight);
                }
            });


//            // Set OnClickListener for the "Delete Flight" button
//            binding.deleteButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // Call the method to delete the selected flight from the database
//                    deleteFlightFromDatabase(selectedFlight);
//                }
//            });


        }
        return binding.getRoot();
    }

    private void saveFlightToDatabase(Flight flight) {
        // Run the database insertion on an executor thread
        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute(() -> {
            // Get the FlightDao instance from the FlightDatabase
            FlightDatabase flightDatabase = Room.databaseBuilder(requireContext(), FlightDatabase.class, "flight-database").build();
            FlightDao flightDao = flightDatabase.flightDao();

            // Insert the selected flight into the database
            flightDao.insertFlight(flight);
            // Show the toast message on the main UI thread
            requireActivity().runOnUiThread(() -> {
                Toast.makeText(requireContext(), "Flight saved to database!", Toast.LENGTH_SHORT).show();
            });

        });
    }


//    private void deleteFlightFromDatabase(Flight flight) {
//        // Run the database deletion on an executor thread
//        Executor thread = Executors.newSingleThreadExecutor();
//        thread.execute(() -> {
//            // Get the FlightDao instance from the FlightDatabase
//            FlightDatabase flightDatabase = Room.databaseBuilder(requireContext(), FlightDatabase.class, "flight-database").build();
//            FlightDao flightDao = flightDatabase.flightDao();
//
//            // Delete the selected flight from the database
//            flightDao.deleteFlight(flight);
//            // Show a toast message indicating that the flight was deleted
//            getActivity().runOnUiThread(() -> {
//                Toast.makeText(requireContext(), "Flight deleted from database!", Toast.LENGTH_SHORT).show();
//            });
//        });
//    }

}