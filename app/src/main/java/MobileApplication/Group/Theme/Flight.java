package MobileApplication.Group.Theme;

import android.content.Context;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
/**
 * Represents a flight with its associated details.
 */
@Entity
public class Flight {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo
    private String airlineName;
    @ColumnInfo
    private String flightNumber;
    @ColumnInfo
    private String departureTime;
    @ColumnInfo
    private String destinationAirport;
    @ColumnInfo
    private String departureAirport;
   // private String flightStatus;
   @ColumnInfo
   private String terminal;
    @ColumnInfo
    private String gate;
    @ColumnInfo
    private String delay;
    /**
     * Constructs a Flight object with the provided details.
     *
     * @param airlineName       The name of the airline.
     * @param flightNumber      The flight number.
     * @param destinationAirport The destination airport code.
     * @param terminal          The terminal information.
     * @param delay             The delay information.
     * @param gate              The gate information.
     */
    public Flight(String airlineName, String flightNumber, String destinationAirport, String terminal, String delay, String gate) {
        this.airlineName = airlineName;
        this.flightNumber = flightNumber;
        this.destinationAirport = destinationAirport;
        this.terminal = terminal;
        this.delay = delay;
        this.gate = gate;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(String destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }



}
