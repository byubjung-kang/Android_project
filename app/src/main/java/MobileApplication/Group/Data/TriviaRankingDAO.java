package MobileApplication.Group.Data;




import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TriviaRankingDAO {

    @Query("SELECT * FROM TriviaRanking ORDER BY score DESC LIMIT 10")
    List<TriviaRanking> getAll();

    @Insert
    void insert(TriviaRanking ranking);

    @Query("SELECT * FROM TriviaRanking WHERE userName = :userName LIMIT 1")
    TriviaRanking findByName(String userName);

    @Update
    void update(TriviaRanking ranking);

    @Delete
    void delete(TriviaRanking ranking);

    @Query("DELETE FROM TriviaRanking")
    void deleteAll();

    @Query("DELETE FROM TriviaRanking WHERE userName = :userName")
    void deleteByUserName(String userName);



}
