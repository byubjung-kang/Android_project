package MobileApplication.Group.Theme.Data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * Data Access Object (DAO) interface for accessing the ConversionHistory table in the database.
 * This interface defines methods to perform CRUD operations (Create, Read, Update, Delete) on the ConversionHistory table.
 */
@Dao
public interface ConversionHistoryDAO {
    /**
     * Insert a new ConversionHistory entry into the database.
     * @param c The ConversionHistory object to be inserted.
     * @return The ID of the newly inserted ConversionHistory entry.
     */
    @Insert
    Long insertConversion(ConversionHistory c);

    /**
     * Get all the ConversionHistory entries from the database.
     * @return A List containing all the ConversionHistory entries in the database.
     */
    @Query("SELECT * FROM ConversionHistory")
    List<ConversionHistory> getAllConversionHistory();

    /**
     * Delete a ConversionHistory entry from the database.
     * @param c The ConversionHistory object to be deleted.
     */
    @Delete
    void deleteConversion(ConversionHistory c);
}

