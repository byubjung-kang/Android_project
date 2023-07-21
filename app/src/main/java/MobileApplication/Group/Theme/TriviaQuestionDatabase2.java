package MobileApplication.Group.Theme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import MobileApplication.Group.R;
import MobileApplication.Group.databinding.ActivityTriviaQuestionDatabase2Binding;

public class TriviaQuestionDatabase2 extends AppCompatActivity {

    protected ActivityTriviaQuestionDatabase2Binding binding;

    private static final Map<String, String> CATEGORY_URL_MAP = new HashMap<>();
    static {
        CATEGORY_URL_MAP.put("geography", "https://opentdb.com/api.php?amount=5&category=22");
        CATEGORY_URL_MAP.put("history", "https://opentdb.com/api.php?amount=5&category=23");
        CATEGORY_URL_MAP.put("general", "https://opentdb.com/api.php?amount=5&category=9");
        CATEGORY_URL_MAP.put("celebrities", "https://opentdb.com/api.php?amount=5&category=26");
    }
    RequestQueue queue = null;

    CardView geography, history, general, celebrities;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityTriviaQuestionDatabase2Binding binding = ActivityTriviaQuestionDatabase2Binding.inflate( getLayoutInflater() );
        setContentView( binding.getRoot() );

        queue = Volley.newRequestQueue(this);

        geography = binding.geography;
        history = binding.history;
        general = binding.general;
        celebrities = binding.celebrities;

        geography.setOnClickListener(clk -> {

            showQuizFragment("geography");
            disableAllCard();
        });

        history.setOnClickListener(clk -> {

            showQuizFragment("history");
            disableAllCard();
        });

        general.setOnClickListener(clk -> {
            showQuizFragment("general");
            disableAllCard();
        });

        celebrities.setOnClickListener(clk -> {

            showQuizFragment("celebrities");
            disableAllCard();
        });


        Intent fromPrevious = getIntent();
        String userName = fromPrevious.getStringExtra("UserName");
        binding.uNameText2.setText("Player : " + userName);
    };

    private void showQuizFragment(String category) {

        String url = CATEGORY_URL_MAP.get(category);


        TriviaQuestionDatabaseFragment quizFragment = TriviaQuestionDatabaseFragment.newInstance(url);


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentFrame2, quizFragment)
                .commit();
    }

    private void disableAllCard() {
        geography.setEnabled(false);
        history.setEnabled(false);
        general.setEnabled(false);
        celebrities.setEnabled(false);
    }
}