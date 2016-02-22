package koala.downloaddemo.entity;

/**
 * Created by taoxj on 16-2-22.
 */
public class ThreadInfo {

    private int id;
    private int start;
    private int end;
    private String url;
    private int finished;

    public ThreadInfo(int id, int start, int end, String url, int finished) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.url = url;
        this.finished = finished;
    }

    public ThreadInfo(){
        super();
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "ThreadInfo{" +
                "id=" + id +
                ", start=" + start +
                ", end=" + end +
                ", url='" + url + '\'' +
                ", finished=" + finished +
                '}';
    }
}
