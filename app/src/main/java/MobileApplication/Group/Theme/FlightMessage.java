package MobileApplication.Group.Theme;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FlightMessage {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "Destination")
    public String Destination;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public String getTerminal() {
        return Terminal;
    }

    public void setTerminal(String terminal) {
        Terminal = terminal;
    }

    public String getGate() {
        return Gate;
    }

    public void setGate(String gate) {
        Gate = gate;
    }

    public String getDelay() {
        return Delay;
    }

    public void setDelay(String delay) {
        Delay = delay;
    }

    @ColumnInfo(name = "Terminal")
    public String Terminal;

    @ColumnInfo(name = "Gate")
    public String Gate;

    @ColumnInfo(name = "Delay")
    public String Delay;
    public FlightMessage(String Destination, String Terminal, String Gate, String Delay) {
        this.Destination = Destination;
        this.Terminal = Terminal;
      this.Gate = Gate;
      this.Delay = Delay;
    }

}

class SearchMessage extends FlightMessage {
    public SearchMessage(String destination) {
        super(destination, "", "", "");
    }
}
    class SeeFlightMessage extends FlightMessage {
        public SeeFlightMessage(String destination, String terminal, String gate, String delay) {
            super(destination, terminal, gate, delay);
        }
    }

