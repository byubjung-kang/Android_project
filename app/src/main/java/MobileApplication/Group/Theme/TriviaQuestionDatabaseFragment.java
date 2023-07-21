package MobileApplication.Group.Theme;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

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

public class TriviaQuestionDatabaseFragment extends Fragment {
    private TextView questionText;
    private Button option1, option2, option3, option4, nextButton;
    private List<TriviaQuestion> questions = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private String url;
    private int score = 0;

    public static TriviaQuestionDatabaseFragment newInstance(String url) {
        TriviaQuestionDatabaseFragment fragment = new TriviaQuestionDatabaseFragment();
        Bundle args = new Bundle();
        args.putString("url", url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString("url");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_trivia_question_fragment, container, false);

        questionText = view.findViewById(R.id.questionText);
        option1 = view.findViewById(R.id.option1);
        option2 = view.findViewById(R.id.option2);
        option3 = view.findViewById(R.id.option3);
        option4 = view.findViewById(R.id.option4);
        nextButton = view.findViewById(R.id.nextButton);

        loadQuestions();

        View.OnClickListener optionButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button clickedButton = (Button) v;
                String clickedOption = clickedButton.getText().toString();


                if (questions.get(currentQuestionIndex).getCorrectAnswer().equals(clickedOption)) {

                    Toast.makeText(getContext(), "correct answer!", Toast.LENGTH_SHORT).show();
                    score += 10;
                } else {

                    Toast.makeText(getContext(), "wrong answer.", Toast.LENGTH_SHORT).show();
                }


                disableAllOptions();
            }
        };

        option1.setOnClickListener(optionButtonClickListener);
        option2.setOnClickListener(optionButtonClickListener);
        option3.setOnClickListener(optionButtonClickListener);
        option4.setOnClickListener(optionButtonClickListener);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQuestionIndex++;
                if (currentQuestionIndex < questions.size()) {
                    showQuestion();
                } else {

                    Intent intent = new Intent(getActivity(), TriviaScore.class);

                    intent.putExtra("score", score);
                    startActivity(intent);
                }
            }
        });
        return view;
    }

    private void loadQuestions() {
        RequestQueue queue = Volley.newRequestQueue(getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray("results");
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject result = results.getJSONObject(i);

                                String question = result.getString("question");
                                String correctAnswer = result.getString("correct_answer");
                                JSONArray incorrectAnswers = result.getJSONArray("incorrect_answers");

                                String[] incorrectAnswersArray = new String[incorrectAnswers.length()];
                                for (int j = 0; j < incorrectAnswers.length(); j++) {
                                    incorrectAnswersArray[j] = incorrectAnswers.getString(j);
                                }


                                TriviaQuestion q = new TriviaQuestion(question, correctAnswer, incorrectAnswersArray);
                                questions.add(q);
                            }


                            showQuestion();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    //if error

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        queue.add(jsonObjectRequest);
    }

    private void showQuestion() {
        TriviaQuestion question = questions.get(currentQuestionIndex);
        questionText.setText(question.getQuestion());

        String[] options = question.getShuffledOptions();
        option1.setText(options[0]);
        option2.setText(options[1]);


        if (options.length == 2) {
            option3.setVisibility(View.GONE);
            option4.setVisibility(View.GONE);
        } else {
            option3.setVisibility(View.VISIBLE);
            option4.setVisibility(View.VISIBLE);
            option3.setText(options[2]);
            option4.setText(options[3]);
        }

        enableAllOptions();
    }

    private void disableAllOptions() {
        option1.setEnabled(false);
        option2.setEnabled(false);
        option3.setEnabled(false);
        option4.setEnabled(false);
    }

    private void enableAllOptions() {
        option1.setEnabled(true);
        option2.setEnabled(true);
        option3.setEnabled(true);
        option4.setEnabled(true);
    }


}