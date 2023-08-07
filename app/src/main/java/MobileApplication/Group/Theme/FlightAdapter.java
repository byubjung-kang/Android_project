package MobileApplication.Group.Theme;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import MobileApplication.Group.R;

/**
 * An adapter for displaying flight information in a RecyclerView.
 */
public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.FlightViewHolder> {

    private List<Flight> flightList;
    private FlightClickListener flightClickListener;

    /**
     * Constructs a FlightAdapter with the provided flight list and click listener.
     *
     * @param flightList         The list of flights to display.
     * @param flightClickListener The listener for handling flight item clicks.
     */
    public FlightAdapter(List<Flight> flightList, FlightClickListener flightClickListener) {
        this.flightList = flightList;
        this.flightClickListener = flightClickListener;
    }

    @NonNull
    @Override
    public FlightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flight_item_layout, parent, false);
        return new FlightViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightViewHolder holder, int position) {
        Flight flight = flightList.get(position);
        String flightDetails = "Flight # : " + flight.getFlightNumber() + " - " + flight.getAirlineName();

        holder.flightInfoTextView.setText(flightDetails);

        holder.itemView.setOnClickListener(v -> {
            // Handle item click here
            if (flightClickListener != null) {
                flightClickListener.onFlightClick(flight);
                if (v.getContext() instanceof MainActivity) {
                    ((AviationStackFlightTracker) v.getContext()).onFlightClick(flight);
                } else if (v.getContext() instanceof SavedFlightsActivity) {
                    ((SavedFlightsActivity) v.getContext()).onFlightClickFromSavedFlight(flight);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return flightList.size();
    }

    /**
     * ViewHolder class for holding flight item views.
     */
    public class FlightViewHolder extends RecyclerView.ViewHolder {
        TextView flightInfoTextView;

        public FlightViewHolder(View itemView) {
            super(itemView);
            flightInfoTextView = itemView.findViewById(R.id.flightInfoTextView);
        }
    }

    // Interface to handle flight item clicks
    public interface FlightClickListener {
        void onFlightClick(Flight flight);
    }

    /**
     * Sets the flight click listener.
     *
     * @param flightClickListener The listener for handling flight item clicks.
     */
    public void setFlightClickListener(FlightClickListener flightClickListener) {
        this.flightClickListener = flightClickListener;
    }
}