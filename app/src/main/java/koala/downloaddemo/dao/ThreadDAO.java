package koala.downloaddemo.dao;

import java.util.List;

import koala.downloaddemo.entity.ThreadInfo;

/**
 * Created by taoxj on 16-2-22.
 */
public interface ThreadDAO {

    /**
     * �����߳���Ϣ
     * @param info
     */
    public void insertThread(ThreadInfo info);

    /**
     * ɾ���߳���Ϣ
     * @param url
     * @param thread_id
     */
    public void deleteThread(String url,int thread_id);

    /**
     * �����߳���Ϣ
     * @param url
     * @param thread_id
     * @param finished
     */
    public void updateThread(String url,int thread_id,int finished);

    /**
     * ��ѯ�߳���Ϣ
     * @param url
     * @return
     */
    public List<ThreadInfo> getThreads(String url);

    /**
     * �߳���Ϣ�Ƿ����
     * @param url
     * @param thread_id
     * @return
     */
    public boolean isExist(String url,int thread_id);


}
