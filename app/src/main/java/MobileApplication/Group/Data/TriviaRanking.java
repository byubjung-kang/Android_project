package MobileApplication.Group.Data;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TriviaRanking {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String userName;
    private int score;

    @NonNull
    public String getUserName() {

        return userName;
    }

    public void setUserName(@NonNull String userName) {

        this.userName = userName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {

        this.score = score;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }
}
