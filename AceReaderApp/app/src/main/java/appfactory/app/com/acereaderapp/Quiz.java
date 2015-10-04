package appfactory.app.com.acereaderapp;

/**
 * Created by neerajakukday on 10/4/15.
 */
public class Quiz {

    private String question;
    private String option_a;
    private String option_b;
    private String answer;

    public Quiz(){}

    public Quiz(String question, String option_a, String option_b, String answer) {
        this.question = question;
        this.option_a = option_a;
        this.option_b = option_b;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption_a() {
        return option_a;
    }

    public void setOption_a(String option_a) {
        this.option_a = option_a;
    }

    public String getOption_b() {
        return option_b;
    }

    public void setOption_b(String option_b) {
        this.option_b = option_b;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
