package MobileApplication.Group.Theme;

public class Flight {
    private String airlineName;
    private String flightNumber;
    private String departureTime;
    private String destinationAirport;
    private String flightStatus;

    public Flight(String airlineName, String flightNumber, String departureTime, String destinationAirport, String flightStatus) {
        this.airlineName = airlineName;
        this.flightNumber = flightNumber;
        this.departureTime = departureTime;
        this.destinationAirport = destinationAirport;
        this.flightStatus = flightStatus;
    }

    // Constructor for the dummy flight with a single parameter
    public Flight(String message) {
        this.airlineName = message;
        this.flightNumber = "";
        this.departureTime = "";
        this.destinationAirport = "";
        this.flightStatus = "";
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

    public String getFlightStatus() {
        return flightStatus;
    }

    public void setFlightStatus(String flightStatus) {
        this.flightStatus = flightStatus;
    }
}
