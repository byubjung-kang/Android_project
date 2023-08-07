package MobileApplication.Group.Theme;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * A Room Database class that provides access to the Flight DAO and manages the Flight table.
 */
@Database(entities = {Flight.class}, version = 1)
public abstract class FlightDatabase extends RoomDatabase {

        /**
         * Provides access to the Flight DAO for performing database operations.
         *
         * @return The Flight DAO instance.
         */
        public abstract FlightDao flightDao();
}
