package MobileApplication.Group.Theme;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * Data Access Object (DAO) interface for accessing and managing Flight data in the database.
 */
@Dao
public interface FlightDao {

    /**
     * Inserts a flight into the database.
     *
     * @param flight The flight to be inserted.
     */
    @Insert
    void insertFlight(Flight flight);

    /**
     * Retrieves all flights from the database.
     *
     * @return A list of all flights stored in the database.
     */
    @Query("SELECT * FROM Flight")
    List<Flight> getAllFlights();

    /**
     * Deletes a flight from the database.
     *
     * @param flight The flight to be deleted.
     */
    @Delete
    void deleteFlight(Flight flight);
}

