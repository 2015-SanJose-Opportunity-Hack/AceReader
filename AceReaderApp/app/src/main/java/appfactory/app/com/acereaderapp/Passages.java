package appfactory.app.com.acereaderapp;

/**
 * Created by neerajakukday on 10/3/15.
 */
public class Passages {

    private int pid;
    private int dl;
    private String content;

    public Passages(){}

    public Passages(int pid, int dl, String content) {
        super();
        this.pid = pid;
        this.dl = dl;
        this.content = content;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getDl() {
        return dl;
    }

    public void setDl(int dl) {
        this.dl = dl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Passage [id=" + pid + ", dl=" + dl + ", content=" + content
                + "]";
    }
}
