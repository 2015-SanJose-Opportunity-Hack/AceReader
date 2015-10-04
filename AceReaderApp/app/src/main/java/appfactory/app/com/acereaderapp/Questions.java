package appfactory.app.com.acereaderapp;

/**
 * Created by neerajakukday on 10/3/15.
 */
public class Questions {


    private int qid;
    private int pid;
    private int stid;
    private String question;
    private String option_a;
    private String option_b;
    private String answer;

    public Questions(){}

    public Questions(int qid, int pid, int sid, String question, String option_a, String option_b, String answer) {
        this.qid = qid;
        this.pid = pid;
        this.stid = sid;
        this.question = question;
        this.option_a = option_a;
        this.option_b = option_b;
        this.answer = answer;
    }

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getStid() {
        return stid;
    }

    public void setStid(int stid) {
        this.stid = stid;
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

    @Override
    public String toString() {
        return "Questions [id=" + qid + ", pid=" + pid + ", question=" + question
                + "]";
    }
}
