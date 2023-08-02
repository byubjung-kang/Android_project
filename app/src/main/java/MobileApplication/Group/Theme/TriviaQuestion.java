package MobileApplication.Group.Theme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Represents a trivia question with a question text, correct answer, and incorrect options.
 * @author byubjung kang
 * @version 1.0
 */
public class TriviaQuestion {

    /**
     * The text of the trivia question.
     */
    private String question;

    /**
     * The correct answer to the trivia question.
     */
    private String correctAnswer;

    /**
     * An array containing incorrect options for the trivia question.
     */
    private String[] incorrectAnswers;


    /**
     * Gets an array of shuffled options, including the correct answer and incorrect answers.
     * The order of the options is randomized each time this method is called.
     *
     * @return An array of shuffled options.
     */
    public String[] getShuffledOptions() {
        List<String> options = new ArrayList<>();
        options.add(correctAnswer);
        options.addAll(Arrays.asList(incorrectAnswers));


        Collections.shuffle(options);

        return options.toArray(new String[0]);
    }

    /**
     * Constructs a TriviaQuestion object with the specified question, correct answer, and incorrect answers.
     *
     * @param question        The trivia question.
     * @param correctAnswer   The correct answer to the trivia question.
     * @param incorrectAnswers An array containing incorrect options for the trivia question.
     */
    public TriviaQuestion(String question, String correctAnswer, String[] incorrectAnswers) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
    }

    /**
     * Gets the question text for this trivia question.
     *
     * @return The question text.
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Gets the correct answer for this trivia question.
     *
     * @return The correct answer.
     */
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * Gets an array of incorrect answers for this trivia question.
     *
     * @return An array of incorrect answers.
     */
    public String[] getIncorrectAnswers() {
        return incorrectAnswers;
    }

    /**
     * Gets option 1, which is the same as the correct answer.
     *
     * @return Option 1 text (the correct answer).
     */
    public String getOption1() {
        return correctAnswer;
    }

    /**
     * Gets option 2, which is the first incorrect answer in the array of incorrect answers.
     *
     * @return Option 2 text (the first incorrect answer).
     */
    public String getOption2() {
        return incorrectAnswers[0];
    }

    /**
     * Gets option 3, which is the second incorrect answer in the array of incorrect answers.
     * If there are no more incorrect answers available, an empty string is returned.
     *
     * @return Option 3 text or an empty string if not available.
     */
    public String getOption3() {
        return incorrectAnswers.length > 1 ? incorrectAnswers[1] : "";
    }

    /**
     * Gets option 4, which is the third incorrect answer in the array of incorrect answers.
     * If there are no more incorrect answers available, an empty string is returned.
     *
     * @return Option 4 text or an empty string if not available.
     */
    public String getOption4() {
        return incorrectAnswers.length > 2 ? incorrectAnswers[2] : "";
    }
}


