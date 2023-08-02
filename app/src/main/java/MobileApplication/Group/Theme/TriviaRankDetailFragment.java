package MobileApplication.Group.Theme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;

import MobileApplication.Group.Data.TriviaRanking;
import MobileApplication.Group.Data.TriviaRankingDAO;
import MobileApplication.Group.Data.TriviaRankingDatabase;
import MobileApplication.Group.R;
import MobileApplication.Group.databinding.ActivityTriviaRankDetailFragmentBinding;


/**
 * Fragment to display the details of a player's rank, name, and score.
 * @author byubjung kang
 * @version 1.0
 */
public class TriviaRankDetailFragment extends Fragment {

    /**
     * The TextView to display the rank, name, score of the player.
     */
    TextView rankTextView, nameTextView, scoreTextView;

    /**
     * Default constructor for the TriviaRankDetailFragment.
     */
    public TriviaRankDetailFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.activity_trivia_rank_detail_fragment, container, false);

        Button removeButton = view.findViewById(R.id.remove);

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the user name
                String userName = nameTextView.getText().toString();

                // Delete the ranking
                new Thread(() -> {
                    TriviaRankingDatabase db = Room.databaseBuilder(v.getContext(),
                            TriviaRankingDatabase.class, "ranking-database").build();
                    TriviaRankingDAO rankingDao = db.rankingDao();

                    rankingDao.deleteByUserName(userName);
                }).start();

                Snackbar.make(view, "this user name has delected " , Snackbar.LENGTH_SHORT).show();

                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        rankTextView = view.findViewById(R.id.rank_text);
        nameTextView = view.findViewById(R.id.name_text);
        scoreTextView = view.findViewById(R.id.score_text);

        Bundle bundle = getArguments();
        if(bundle != null) {
            String rank = bundle.getString("rank");
            String name = bundle.getString("name");
            String score = bundle.getString("score");

            rankTextView.setText(rank);
            nameTextView.setText(name);
            scoreTextView.setText(score);
        }
        return view;
    }
}
