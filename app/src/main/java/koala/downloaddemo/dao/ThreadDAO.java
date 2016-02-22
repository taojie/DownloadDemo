package koala.downloaddemo.dao;

import java.util.List;

import koala.downloaddemo.entity.ThreadInfo;

/**
 * Created by taoxj on 16-2-22.
 */
public interface ThreadDAO {

    /**
     * 插入线程信息
     * @param info
     */
    public void insertThread(ThreadInfo info);

    /**
     * 删除线程信息
     * @param url
     * @param thread_id
     */
    public void deleteThread(String url,int thread_id);

    /**
     * 更新线程信息
     * @param url
     * @param thread_id
     * @param finished
     */
    public void updateThread(String url,int thread_id,int finished);

    /**
     * 查询线程信息
     * @param url
     * @return
     */
    public List<ThreadInfo> getThreads(String url);

    /**
     * 线程信息是否存在
     * @param url
     * @param thread_id
     * @return
     */
    public boolean isExist(String url,int thread_id);


}
