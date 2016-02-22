package koala.downloaddemo.entity;

import java.io.Serializable;

/**
 * 下载的文件信息
 * Created by taoxj on 16-2-22.
 */
public class FileInfo implements Serializable{
    private int id;
    private String fileName;
    private long size;
    private String url;
    private int finished;

    public FileInfo(int id, String fileName, long size, String url, int finished) {
        this.id = id;
        this.fileName = fileName;
        this.size = size;
        this.url = url;
        this.finished = finished;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
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
        return "FileInfo{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", size=" + size +
                ", url='" + url + '\'' +
                ", finished=" + finished +
                '}';
    }
}
