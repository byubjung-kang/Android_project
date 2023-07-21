package MobileApplication.Group.Data;

import android.service.notification.NotificationListenerService;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {TriviaRanking.class}, version = 1)
public abstract class TriviaRankingDatabase extends RoomDatabase {

    public abstract TriviaRankingDAO rankingDao();

}
