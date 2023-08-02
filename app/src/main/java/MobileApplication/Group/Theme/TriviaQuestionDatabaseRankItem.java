package MobileApplication.Group.Theme;


/**
 * Represents an item in the ranking list with its rank, name, and score.
 * @author byubjung kang
 * @version 1.0
 */
public class TriviaQuestionDatabaseRankItem {

    /**
     * The rank of the item in the ranking list.
     */
    private int rank;

    /**
     * The name associated with the item.
     */
    private String name;

    /**
     * The score associated with the item.
     */
    private int score;

    /**
     * Creates a new TriviaQuestionDatabaseRankItem with the given rank, name, and score.
     *
     * @param rank  The rank of the item in the ranking list.
     * @param name  The name associated with the item.
     * @param score The score associated with the item.
     */
    public TriviaQuestionDatabaseRankItem(int rank, String name, int score) {
        this.rank = rank;
        this.name = name;
        this.score = score;
    }

    /**
     * Gets the rank of the item in the ranking list.
     *
     * @return The rank of the item.
     */
    public int getRank() {

        return rank;
    }

    /**
     * Sets the rank of the item in the ranking list.
     *
     * @param rank The new rank of the item.
     */
    public void setRank(int rank) {

        this.rank = rank;
    }

    /**
     * Gets the name associated with the item.
     *
     * @return The name of the item.
     */
    public String getName() {

        return name;
    }

    /**
     * Sets the name associated with the item.
     *
     * @param name The new name of the item.
     */
    public void setName(String name) {

        this.name = name;
    }

    /**
     * Gets the score associated with the item.
     *
     * @return The score of the item.
     */
    public int getScore() {

        return score;
    }

    /**
     * Sets the score associated with the item.
     *
     * @param score The new score of the item.
     */
    public void setScore(int score) {

        this.score = score;
    }
}

