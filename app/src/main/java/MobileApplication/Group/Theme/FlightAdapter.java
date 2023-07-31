package MobileApplication.Group.Theme;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import MobileApplication.Group.R;

public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.FlightViewHolder> {

    private List<Flight> flightList;
    private FlightClickListener flightClickListener;

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
        String flightDetails = "Flight name: " + flight.getAirlineName() + " " + flight.getFlightNumber() +
                "\nDeparture Airport: " + flight.getDepartureAirport() +
                "\nScheduled Departure Time: " + flight.getDepartureTime();
        holder.flightInfoTextView.setText(flightDetails);

        holder.itemView.setOnClickListener(v -> {
            // Handle item click here
            flightClickListener.onFlightClick(flight);
        });
    }


    @Override
    public int getItemCount() {
        return flightList.size();
    }

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

    public void setFlightClickListener(FlightClickListener flightClickListener) {
        this.flightClickListener = flightClickListener;
    }
}