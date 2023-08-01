package MobileApplication.Group.Theme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import MobileApplication.Group.databinding.FragmentFlightDetailsBinding;


public class FlightDetailsFragment extends Fragment {

 private FragmentFlightDetailsBinding binding;
    private Flight selectedFlight;

    public FlightDetailsFragment(Flight message) {
        // Required empty public constructor
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


        }
        return binding.getRoot();
    }

//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        // Unbind the View Binding
//        binding = null;
//    }
}