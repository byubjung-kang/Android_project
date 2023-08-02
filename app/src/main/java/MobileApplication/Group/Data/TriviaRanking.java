package MobileApplication.Group.Data;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


/**
 * Entity class representing a single ranking entry in the ranking database.
 * @author byubjung kang
 * @version 1.0
 */
@Entity
public class TriviaRanking {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    private int id;

    @NonNull
    @ColumnInfo(name="userName")
    private String userName;

    @ColumnInfo(name="score")
    private int score;

    @NonNull

    /**
     * Get the username associated with this ranking entry.
     *
     * @return The username.
     */
    public String getUserName() {

        return userName;
    }

    /**
     * Set the username for this ranking entry.
     *
     * @param userName The username to set.
     */
    public void setUserName(@NonNull String userName) {

        this.userName = userName;
    }

    /**
     * Get the score associated with this ranking entry.
     *
     * @return The score.
     */
    public int getScore() {

        return score;
    }

    /**
     * Set the score for this ranking entry.
     *
     * @param score The score to set.
     */
    public void setScore(int score) {

        this.score = score;
    }

    /**
     * Get the ID of this ranking entry.
     *
     * @return The ID.
     */
    public int getId() {

        return id;
    }

    /**
     * Set the ID for this ranking entry.
     *
     * @param id The ID to set.
     */
    public void setId(int id) {

        this.id = id;
    }
}
