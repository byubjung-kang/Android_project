package Data.BearImageGeneratorData;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
/**
 * @author Jeonghyeon Lee
 * @version 1.0
 */
/**
 * Data Access Object for BearImage in the database.
 */
@Dao
public interface BearImageDAO {
    /**
     * Inserts a BearImage object into the database.
     *
     * @param m The BearImage object to be inserted.
     * @return The row ID of the newly inserted BearImage.
     */
    @Insert
    public long insertImage(BearImage m);

    /**
     * Retrieves all BearImage objects stored in the database.
     *
     * @return All BearImage objects stored in the database.
     */
    @Query("Select * from BearImage")
    public List<BearImage> getAllImages();

    /**
     * Deletes a specific BearImage object from the database.
     *
     * @param m The BearImage object to be deleted.
     */
    @Delete
    void deleteMessage(BearImage m);
}
