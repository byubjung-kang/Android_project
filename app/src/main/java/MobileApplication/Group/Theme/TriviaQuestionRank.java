package MobileApplication.Group.Theme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import MobileApplication.Group.Data.TriviaRanking;
import MobileApplication.Group.Data.TriviaRankingDAO;
import MobileApplication.Group.Data.TriviaRankingDatabase;
import MobileApplication.Group.R;

public class TriviaQuestionRank extends AppCompatActivity {

    private RecyclerView rankingRecyclerView;
    private TriviaQuestionDatabaseRankAdapter rankingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_question_rank);

        rankingRecyclerView = findViewById(R.id.rankingRecyclerView);
        rankingAdapter = new TriviaQuestionDatabaseRankAdapter();

        rankingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        rankingRecyclerView.setAdapter(rankingAdapter);

        TriviaRankingDatabase db = Room.databaseBuilder(getApplicationContext(),
                TriviaRankingDatabase.class, "ranking-database").build();
        TriviaRankingDAO rankingDao = db.rankingDao();

        new Thread(() -> {
            List<TriviaRanking> rankingList = rankingDao.getAll();


            List<TriviaQuestionDatabaseRankItem> rankItemList = new ArrayList<>();
            for (int i = 0; i < rankingList.size(); i++) {
                TriviaRanking ranking = rankingList.get(i);
                rankItemList.add(new TriviaQuestionDatabaseRankItem(i + 1, ranking.getUserName(), ranking.getScore()));
            }

            runOnUiThread(() -> {
                rankingAdapter.submitList(rankItemList);
            });
        }).start();
    }

}