package koala.downloaddemo.dao;

import java.util.List;

import koala.downloaddemo.entity.ThreadInfo;

/**
 * Created by taoxj on 16-2-22.
 */
public class ThreadDAOImpl implements ThreadDAO {
    @Override
    public void insertThread(ThreadInfo info) {

    }

    @Override
    public void deleteThread(String url, int thread_id) {

    }

    @Override
    public void updateThread(String url, int thread_id, int finished) {

    }

    @Override
    public List<ThreadInfo> getThreads(String url) {
        return null;
    }

    @Override
    public boolean isExist(String url, int thread_id) {
        return false;
    }
}
