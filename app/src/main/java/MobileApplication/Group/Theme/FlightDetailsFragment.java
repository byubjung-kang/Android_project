package MobileApplication.Group.Theme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import MobileApplication.Group.R;

public class FlightDetailsFragment extends Fragment {

    private static final String ARG_AIRLINE_NAME = "airline_name";
    private static final String ARG_FLIGHT_NUMBER = "flight_number";
    private static final String ARG_DEPARTURE_AIRPORT = "departure_airport";
    private static final String ARG_SCHEDULED_DEPARTURE_TIME = "scheduled_departure_time";
    private static final String ARG_DESTINATION = "destination";
    private static final String ARG_TERMINAL = "terminal";
    private static final String ARG_GATE = "gate";
    private static final String ARG_DELAY = "delay";

    public static FlightDetailsFragment newInstance(String airlineName, String flightNumber, String destinationAirport,
                                                    String departureTime, String terminal, String gate, String delay) {
        FlightDetailsFragment fragment = new FlightDetailsFragment();
        Bundle args = new Bundle();
        args.putString("AIRLINE_NAME", airlineName);
        args.putString("FLIGHT_NUMBER", flightNumber);
        args.putString("DESTINATION_AIRPORT", destinationAirport);
        args.putString("DEPARTURE_TIME", departureTime);
        args.putString("TERMINAL", terminal);
        args.putString("GATE", gate);
        args.putString("DELAY", delay);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_flight_details, container, false);

       // TextView airlineTextView = rootView.findViewById(R.id.airlineTextView);
        TextView flightNumberTextView = rootView.findViewById(R.id.flightNumberTextView);
        //TextView departureAirportTextView = rootView.findViewById(R.id.departureAirportTextView);
        //TextView scheduledDepartureTimeTextView = rootView.findViewById(R.id.scheduledDepartureTimeTextView);
        TextView destinationTextView = rootView.findViewById(R.id.destinationTextView);
        TextView terminalTextView = rootView.findViewById(R.id.terminalTextView);
        TextView gateTextView = rootView.findViewById(R.id.gateTextView);
        TextView delayTextView = rootView.findViewById(R.id.delayTextView);

        Bundle args = getArguments();
        if (args != null) {
            String airlineName = args.getString(ARG_AIRLINE_NAME);
            String flightNumber = args.getString(ARG_FLIGHT_NUMBER);
            String departureAirport = args.getString(ARG_DEPARTURE_AIRPORT);
            String scheduledDepartureTime = args.getString(ARG_SCHEDULED_DEPARTURE_TIME);
            String destination = args.getString(ARG_DESTINATION);
            String terminal = args.getString(ARG_TERMINAL);
            String gate = args.getString(ARG_GATE);
            String delay = args.getString(ARG_DELAY);

         //   airlineTextView.setText("Airline: " + airlineName);
            flightNumberTextView.setText("Flight Number: " + flightNumber);
         //   departureAirportTextView.setText("Departure Airport: " + departureAirport);
          //  scheduledDepartureTimeTextView.setText("Scheduled Departure Time: " + scheduledDepartureTime);
            destinationTextView.setText("Destination: " + destination);
            terminalTextView.setText("Terminal: " + terminal);
            gateTextView.setText("Gate: " + gate);
            delayTextView.setText("Delay: " + delay);
        }

        return rootView;
    }
}
