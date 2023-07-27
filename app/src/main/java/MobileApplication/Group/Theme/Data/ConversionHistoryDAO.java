package MobileApplication.Group.Theme.Data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ConversionHistoryDAO {
    @Insert
    Long insertConversion(ConversionHistory c);

    @Query("SELECT * FROM ConversionHistory")
    List<ConversionHistory> getAllConversionHistory();

    @Delete
    void deleteConversion(ConversionHistory c);
}

