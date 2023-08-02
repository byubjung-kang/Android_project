package MobileApplication.Group.Theme;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;

import androidx.room.Query;

import java.util.List;

@Dao
public interface FlightDao {

    @Insert
    void insertFlight(Flight fight);

                        //@Entity
    @Query("Select * from Flight")
    List<Flight> getAllFlight();
    @Delete
    void deleteFlight(Flight flight);

}
