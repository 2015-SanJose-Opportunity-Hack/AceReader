package appfactory.app.com.acereaderapp;

import java.util.HashMap;

/**
 * Created by neerajakukday on 10/3/15.
 */
public class QuizData {

    private int noOfQuizzes;

    private int quizLevel = 1;

    private HashMap<Integer,Integer> score = new HashMap<>();

    public QuizData(Integer noOfQuizzes, HashMap<Integer,Integer> score){
        this.noOfQuizzes = noOfQuizzes;
        this.score = score;
    }

    public int getNoOfQuizzes() {
        return noOfQuizzes;
    }

    public void setNoOfQuizzes(int noOfQuizzes) {
        this.noOfQuizzes = noOfQuizzes;
    }

    public int getQuizLevel() {
        return quizLevel;
    }

    public void setQuizLevel(int quizLevel) {
        this.quizLevel = quizLevel;
    }

    public HashMap<Integer, Integer> getScore() {
        return score;
    }

    public void setScore(HashMap<Integer, Integer> score) {
        this.score = score;
    }

}
