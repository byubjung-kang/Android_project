package MobileApplication.Group.Data;

import android.service.notification.NotificationListenerService;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * A RoomDatabase class that represents the database for TriviaRanking entities.
 * This class is used to create and access the database and provides access to the DAO interface.
 * @author byubjung kang
 * @version 1.0
 */
@Database(entities = {TriviaRanking.class}, version = 1)
public abstract class TriviaRankingDatabase extends RoomDatabase {

    /**
     * Returns the Data Access Object (DAO) interface for TriviaRanking entities.
     *
     * @return The TriviaRankingDAO object used to interact with the TriviaRanking table in the database.
     */
    public abstract TriviaRankingDAO rankingDao();

}
