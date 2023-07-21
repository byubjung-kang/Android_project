package MobileApplication.Group.Theme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TriviaQuestion {

    private String question;
    private String correctAnswer;
    private String[] incorrectAnswers;


    public String[] getShuffledOptions() {
        List<String> options = new ArrayList<>();
        options.add(correctAnswer);
        options.addAll(Arrays.asList(incorrectAnswers));


        Collections.shuffle(options);

        return options.toArray(new String[0]);
    }

    public TriviaQuestion(String question, String correctAnswer, String[] incorrectAnswers) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String[] getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public String getOption1() {
        return correctAnswer;
    }

    public String getOption2() {
        return incorrectAnswers[0];
    }

    public String getOption3() {
        return incorrectAnswers.length > 1 ? incorrectAnswers[1] : "";
    }

    public String getOption4() {
        return incorrectAnswers.length > 2 ? incorrectAnswers[2] : "";
    }
}


