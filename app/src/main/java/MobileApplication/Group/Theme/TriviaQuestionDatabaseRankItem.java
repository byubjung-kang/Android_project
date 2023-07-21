package MobileApplication.Group.Theme;

public class TriviaQuestionDatabaseRankItem {
    private int rank;
    private String name;
    private int score;

    public TriviaQuestionDatabaseRankItem(int rank, String name, int score) {
        this.rank = rank;
        this.name = name;
        this.score = score;
    }

    public int getRank() {

        return rank;
    }

    public void setRank(int rank) {

        this.rank = rank;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public int getScore() {

        return score;
    }

    public void setScore(int score) {

        this.score = score;
    }
}

