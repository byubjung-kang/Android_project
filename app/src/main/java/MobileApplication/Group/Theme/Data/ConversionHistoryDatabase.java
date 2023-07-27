package MobileApplication.Group.Theme.Data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities ={ConversionHistory.class}, version=1)
public abstract class ConversionHistoryDatabase extends RoomDatabase {

    public abstract  ConversionHistoryDAO chDAO();
}
