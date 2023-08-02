package MobileApplication.Group.Data;




import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * The Data Access Object (DAO) interface for the TriviaRanking entity.
 * This interface defines methods to interact with the TriviaRanking table in the database.
 * @author byubjung kang
 * @version 1.0
 */
@Dao
public interface TriviaRankingDAO {

    /**
     * Retrieves a list of the top 10 rankings (highest scores) from the TriviaRanking table,
     * ordered by score in descending order.
     *
     * @return A list of TriviaRanking objects representing the top 10 rankings.
     */
    @Query("SELECT * FROM TriviaRanking ORDER BY score DESC LIMIT 10")
    List<TriviaRanking> getAll();

    /**
     * Inserts a new TriviaRanking object into the TriviaRanking table.
     *
     * @param ranking The TriviaRanking object to be inserted.
     */
    @Insert
    void insert(TriviaRanking ranking);

    /**
     * Retrieves a TriviaRanking object from the TriviaRanking table based on the given userName.
     *
     * @param userName The userName associated with the TriviaRanking to retrieve.
     * @return The TriviaRanking object with the specified userName, or null if not found.
     */
    @Query("SELECT * FROM TriviaRanking WHERE userName = :userName LIMIT 1")
    TriviaRanking findByName(String userName);

    /**
     * Updates an existing TriviaRanking object in the TriviaRanking table.
     *
     * @param ranking The TriviaRanking object to be updated.
     */
    @Update
    void update(TriviaRanking ranking);

    /**
     * Deletes an existing TriviaRanking object from the TriviaRanking table.
     *
     * @param ranking The TriviaRanking object to be deleted.
     */
    @Delete
    void delete(TriviaRanking ranking);

    /**
     * Deletes all records from the TriviaRanking table.
     * Use with caution as this operation removes all rankings from the table.
     */
    @Query("DELETE FROM TriviaRanking")
    void deleteAll();

    /**
     * Deletes a TriviaRanking object from the TriviaRanking table based on the given userName.
     *
     * @param userName The userName associated with the TriviaRanking to be deleted.
     */
    @Query("DELETE FROM TriviaRanking WHERE userName = :userName")
    void deleteByUserName(String userName);



}
