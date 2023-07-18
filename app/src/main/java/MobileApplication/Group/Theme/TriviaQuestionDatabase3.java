package MobileApplication.Group.Theme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import MobileApplication.Group.R;

public class TriviaQuestionDatabase3 extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_question_database3);


//        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        adapter = new QuizAdapter(questions);
//        recyclerView.setAdapter(adapter);
//
//        RequestQueue queue = Volley.newRequestQueue(this);
//        String url = "https://opentdb.com/api.php?amount=10&category=22&type=multiple";
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
//                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            JSONArray jsonArray = response.getJSONArray("results");
//
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject jsonQuestion = jsonArray.getJSONObject(i);
//
//                                String question = jsonQuestion.getString("question");
//                                String correctAnswer = jsonQuestion.getString("correct_answer");
//                                JSONArray incorrectAnswersJson = jsonQuestion.getJSONArray("incorrect_answers");
//
//                                List<String> answers = new ArrayList<>();
//                                for (int j = 0; j < incorrectAnswersJson.length(); j++) {
//                                    answers.add(incorrectAnswersJson.getString(j));
//                                }
//                                answers.add(correctAnswer);
//
//                                QuizQuestion quizQuestion = new QuizQuestion(question, answers, correctAnswer);
//                                questions.add(quizQuestion);
//                            }
//
//                            adapter.notifyDataSetChanged();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                });
//
//        queue.add(jsonObjectRequest);



    }
}
