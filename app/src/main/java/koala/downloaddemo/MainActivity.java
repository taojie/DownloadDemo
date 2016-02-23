package koala.downloaddemo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import koala.downloaddemo.entity.FileInfo;
import koala.downloaddemo.service.DownloadService;


public class MainActivity extends Activity {

    private TextView title;
    private ProgressBar progressBar;
    private Button startBtn;
    private Button stopBtn;
    private FileInfo fileInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = (TextView) findViewById(R.id.textView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        startBtn = (Button) findViewById(R.id.download);
        stopBtn = (Button) findViewById(R.id.cancle);

        progressBar.setMax(100);
        fileInfo = new FileInfo(1,"200711912453162_2.jpg",0,"http://pica.nipic.com/2007-11-09/200711912453162_2.jpg",0);
        //
        //http://dlsw.baidu.com/sw-search-sp/soft/52/14459/jdk_8u73_windows_i586_8.0.730.2.1455434099.exe
        title.setText(fileInfo.getFileName());
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DownloadService.class);
                intent.setAction(DownloadService.ACTION_START);
                intent.putExtra("file_info", fileInfo);
                startService(intent);
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DownloadService.class);
                intent.setAction(DownloadService.ACTON_STOP);
                intent.putExtra("file_info", fileInfo);
                startService(intent);
            }
        });

        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadService.ACTON_UPDATE);
        registerReceiver(mBroadcast, filter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcast);
    }

    BroadcastReceiver mBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(DownloadService.ACTON_UPDATE)){
                int finished = (int)intent.getLongExtra("finished",0);
                progressBar.setProgress(finished);
            }
        }
    };
}
