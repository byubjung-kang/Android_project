package MobileApplication.Group.Theme;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import MobileApplication.Group.Theme.Flight;
import MobileApplication.Group.Theme.FlightDao;

@Database(entities = {Flight.class}, version = 1)
public abstract class FlightDatabase extends RoomDatabase {

        public abstract FlightDao flightDao();

}
