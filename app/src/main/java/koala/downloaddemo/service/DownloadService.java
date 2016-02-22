package koala.downloaddemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import org.apache.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import koala.downloaddemo.entity.FileInfo;

/**
 * Created by taoxj on 16-2-22.
 */
public class DownloadService extends Service {

    public static final String DOWNLOAD_DIR = Environment.getExternalStorageDirectory() + "/download";
    public static final String ACTION_START = "ACTION_START";
    public static final String ACTON_STOP = "ACTON_STOP";
    public static final int MSG_INIT = 1;
    private FileInfo fileInfo = null;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent.getAction().equals(ACTION_START)){
            fileInfo = (FileInfo) intent.getSerializableExtra("file_info");
            Log.i("koala",fileInfo.toString());
            new InitThread(fileInfo).start();//启动初始化线程
        }else if(intent.getAction().equals(ACTON_STOP)){
            fileInfo = (FileInfo) intent.getSerializableExtra("file_info");
            Log.i("koala",fileInfo.toString());
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_INIT:
                    FileInfo fileInfo = (FileInfo) msg.obj;
                    Log.i("koala",fileInfo.toString());
                     break;
            }
            super.handleMessage(msg);
        }
    };


    class InitThread extends Thread{
        FileInfo fileInfo;
       public InitThread(FileInfo info){
           fileInfo = info;
       }

        @Override
        public void run() {
            HttpURLConnection connection = null;
            RandomAccessFile raf = null;
            try {
                URL url = new URL(fileInfo.getUrl());
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(3000);
                int length = -1;
                if(connection.getResponseCode() == HttpStatus.SC_OK){
                    length = connection.getContentLength();
                }
                Log.i("koala",length + "");
                if(length <= 0){
                    return;
                }
                File dir = new File(DOWNLOAD_DIR);
                if(!dir.exists()){
                    dir.mkdir();
                }
                File file = new File(dir,fileInfo.getFileName());
                raf = new RandomAccessFile(file,"rwd");
                raf.setLength(length);
                fileInfo.setSize(length);

                handler.obtainMessage(MSG_INIT,fileInfo).sendToTarget();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                connection.disconnect();
                try {
                    if(raf != null){
                        raf.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
