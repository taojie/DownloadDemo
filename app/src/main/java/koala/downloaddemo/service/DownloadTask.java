package koala.downloaddemo.service;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;

import org.apache.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import koala.downloaddemo.dao.ThreadDAO;
import koala.downloaddemo.dao.ThreadDAOImpl;
import koala.downloaddemo.entity.FileInfo;
import koala.downloaddemo.entity.ThreadInfo;

/**
 * Created by taoxj on 16-2-23.
 */
public class DownloadTask {
    private FileInfo fileInfo;
    private Context mContext;
    private ThreadDAO dao = null;
    private int mFinished = 0;
    public boolean isPause = false;

    public DownloadTask(FileInfo fileInfo, Context mContext) {
        this.fileInfo = fileInfo;
        this.mContext = mContext;
        this.dao = new ThreadDAOImpl(mContext);
    }

    public void download() {
        List<ThreadInfo> threads = dao.getThreads(fileInfo.getUrl());
        ThreadInfo info = null;
        if (threads.size() == 0) {
            info = new ThreadInfo(0, 0, (int) fileInfo.getSize(), fileInfo.getUrl(), 0);
        } else {
            info = threads.get(0);
        }
        Log.i("koala", info.toString());
        DownloadThread downloadThread = new DownloadThread(info);
        downloadThread.start();

    }

    class DownloadThread extends Thread {
        private ThreadInfo threadInfo = null;

        public DownloadThread(ThreadInfo threadInfo) {
            this.threadInfo = threadInfo;
        }

        @Override
        public void run() {
            if (!dao.isExist(threadInfo.getUrl(), threadInfo.getId())) {
                dao.insertThread(threadInfo);
                Log.i("koala", "新建下载线程 ");
            }
            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            InputStream input = null;
            try {
                URL url = new URL(threadInfo.getUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(3000);

                //设置下载范围
                int start = threadInfo.getStart() + threadInfo.getFinished();
                conn.setRequestProperty("Range", "bytes=" + start + "-" + threadInfo.getEnd());

                File file = new File(DownloadService.DOWNLOAD_DIR, fileInfo.getFileName());
                raf = new RandomAccessFile(file, "rwd");
                //设置跳过多少个字节
                raf.seek(start);

                //开始下载
                mFinished = threadInfo.getFinished();
                if (conn.getResponseCode() == HttpStatus.SC_PARTIAL_CONTENT) {
                    byte[] buffer = new byte[1024 * 4];
                    int length = -1;
                    input = conn.getInputStream();


                    long time = System.currentTimeMillis();
                    Intent intent = new Intent(DownloadService.ACTON_UPDATE);
                    while ((length = input.read(buffer)) != -1) {
                        raf.read(buffer, 0, length);
                        mFinished += length;
                        Log.i("koala", "finished = " + mFinished);
                        dao.updateThread(threadInfo.getUrl(), threadInfo.getId(), mFinished);
                        if (System.currentTimeMillis() - time > 500 || mFinished == fileInfo.getSize()) {
                            //发送广播更新ui
                            time = System.currentTimeMillis();
                            intent.putExtra("finished", mFinished * 100 / fileInfo.getSize());
                            mContext.sendBroadcast(intent);
                        }
                        if (isPause) {
                            return;
                        }
                    }
                    dao.deleteThread(threadInfo.getUrl(), threadInfo.getId());
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    conn.disconnect();
                    raf.close();
                    if (input != null) {
                        input.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
