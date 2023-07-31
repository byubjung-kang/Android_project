package MobileApplication.Group.Theme;

public class Flight {
    private String airlineName;
    private String flightNumber;
    private String departureTime;
    private String destinationAirport;
    private String departureAirport;
   // private String flightStatus;
    private String terminal;
    private String gate;
    private String delay;

    public Flight(String airlineName, String flightNumber, String departureTime, String destinationAirport, String departureAirport, String terminal, String gate, String delay) {
        this.airlineName = airlineName;
        this.flightNumber = flightNumber;
        this.departureTime = departureTime;
        this.destinationAirport = destinationAirport;
       this.departureAirport = departureAirport;
       // this.flightStatus = flightStatus;
        this.terminal = terminal;
        this.gate = gate;
        this.delay = delay;
    }

    // Constructor for the dummy flight with a single parameter
    public Flight(String message) {
        this.airlineName = message;
        this.flightNumber = "";
        this.departureTime = "";
        this.destinationAirport = "";
        this.departureAirport = "";
       // this.flightStatus = "";
        this.terminal = "";
        this.gate = "";
        this.delay = "";
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

//    public String getFlightStatus() {
//        return flightStatus;
//    }
//
//    public void setFlightStatus(String flightStatus) {
//        this.flightStatus = flightStatus;
//    }

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
