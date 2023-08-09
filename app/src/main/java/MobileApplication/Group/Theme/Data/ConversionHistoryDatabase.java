package MobileApplication.Group.Theme.Data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Represents the Room Database for storing conversion history.
 * This class is annotated with @Database, specifying the list of entities
 * and the version number of the database schema1
 */
@Database(entities ={ConversionHistory.class}, version=1, exportSchema = false)
public abstract class ConversionHistoryDatabase extends RoomDatabase {


    /**
     * Returns an instance of the DAO (Data Access Object) for accessing conversion history data.
     * @return The ConversionHistoryDAO instance.
     */
    public abstract  ConversionHistoryDAO chDAO();
}
