package appfactory.app.com.acereaderapp;

import android.util.Log;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by neerajakukday on 10/3/15.
 */
public class SingletonData {

    public static final String passageDump = "There once was a rabbit named Rabi. The wolf was named Wali.  They had lived on the same plains surrounded by hills for many years. The rabbit decided it was time to become friends with the wolf. Wali was open to new ideas and fancied herself to be a leader. They lived in the same neighborhood and Rabi believed that they needed to be nice to each other. Rabi knew that this would be difficult because Wali was always chasing him and other rabbits. Wali the wolf had even eaten some of the rabbit’s friends for dinner. Rabi knew that when Wali was hungry, she acted without thinking. Rabi wanted Wali to think of the consequences of her actions on all his rabbit friends. Rabi also hoped that Wali would set an example for the other wolves that rabbits and wolves could live together as friends.";


    public static Integer updateLevel(QuizData quizData){

        double totalScoreOfAllQuizzes=0;
        double totalBaseScore = quizData.getNoOfQuizzes() * 5;
        if(quizData.getScore().size() >= 3){

            for(int i = 1; i <= quizData.getScore().size(); i++){
                totalScoreOfAllQuizzes+=quizData.getScore().get(i);
            }
            Log.d("totalScoreOfAllQuizzes",totalScoreOfAllQuizzes+"");
            double percentage = (totalScoreOfAllQuizzes/totalBaseScore)*100;
            if(percentage >= 80.0){
                quizData.setQuizLevel(quizData.getQuizLevel()+1);
            }
            Log.d("percent",percentage+"");

        }

        return quizData.getQuizLevel();
    }

    public static double waitTimeOnWord(String paragraph){

        Pattern re = Pattern.compile("[^.!?\\s][^.!?]*(?:[.!?](?!['\"]?\\s|$)[^.!?]*)*[.!?]?['\"]?(?=\\s|$)", Pattern.MULTILINE | Pattern.COMMENTS);
        Matcher reMatcher = re.matcher(paragraph);
        while (reMatcher.find()) {
            Log.e("patternOutput",reMatcher.group()+"");
        }

        return 0;

    }
}
