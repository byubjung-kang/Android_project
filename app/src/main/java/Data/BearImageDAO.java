package Data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BearImageDAO {
    @Insert
    public long insertImage(BearImage m);

    @Query("Select * from BearImage")
    public List<BearImage> getAllImages();

    @Delete
    void deleteMessage(BearImage m);
}
