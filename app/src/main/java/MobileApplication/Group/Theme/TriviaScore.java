package MobileApplication.Group.Theme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import MobileApplication.Group.Data.TriviaRanking;
import MobileApplication.Group.Data.TriviaRankingDAO;
import MobileApplication.Group.Data.TriviaRankingDatabase;
import MobileApplication.Group.R;
import MobileApplication.Group.databinding.ActivityTriviaScoreBinding;

public class TriviaScore extends AppCompatActivity {


    protected ActivityTriviaScoreBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTriviaScoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.goMain.setOnClickListener(clk -> {
            Intent nextPage = new Intent(TriviaScore.this, TriviaQuestionDatabase.class);
            startActivity( nextPage );
        });

        binding.goRank.setOnClickListener(clk -> {
            Intent nextPage = new Intent(TriviaScore.this, TriviaQuestionRank.class);
            startActivity( nextPage );
        });

        int score = getIntent().getIntExtra("score", 0);

        SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
        String userName = sharedPreferences.getString("UserName", "");


        TextView scoreTextView = findViewById(R.id.scoreText);
        scoreTextView.setText(score +" / 50");

        TriviaRankingDatabase db = Room.databaseBuilder(getApplicationContext(),
                TriviaRankingDatabase.class, "ranking-database").build();
        TriviaRankingDAO rankingDao = db.rankingDao();


        new Thread(() -> {
            TriviaRanking existingRanking = rankingDao.findByName(userName);
            if (existingRanking == null) {

                TriviaRanking ranking = new TriviaRanking();
                ranking.setUserName(userName);
                ranking.setScore(score);
                rankingDao.insert(ranking);
            } else {

                existingRanking.setScore(Math.max(existingRanking.getScore(), score));
                rankingDao.update(existingRanking);
            }
        }).start();
            //reset database
//        new Thread(() -> {
//            rankingDao.deleteAll();
//        }).start();
//

    }
}