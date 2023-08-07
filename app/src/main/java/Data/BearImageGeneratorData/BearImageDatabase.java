package Data.BearImageGeneratorData;

import androidx.room.Database;
import androidx.room.RoomDatabase;
/**
 * @author Jeonghyeon Lee
 * @version 1.0
 */
/**
 * Abstract class representing the Room Database for storing BearImage objects.
 */
@Database(entities = {BearImage.class}, version=1, exportSchema = false)
public abstract class BearImageDatabase extends RoomDatabase {
    /**
     * Retrieves the BearImageDAO instance.
     *
     * @return The BearImageDAO instance.
     */
    public abstract BearImageDAO cmDAO();
}
