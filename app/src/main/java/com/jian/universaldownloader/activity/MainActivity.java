package com.jian.universaldownloader.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jian.universaldownloader.R;
import com.jian.universaldownloader.download.DownloadUtils;
import com.jian.universaldownloader.download.OnDownloadListener;
import com.jian.universaldownloader.utils.TimeUtils;

public class MainActivity extends AppCompatActivity implements OnDownloadListener {

    private ProgressBar progressBar;
    private Button start;
    private Button pause;
    private Button resume;
    private Button cancel;
    private TextView text;

    private DownloadUtils downloadUtils;

    private static final String URL = "http://v.julyedu.com/web/course/21/7.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        start = (Button) findViewById(R.id.start);
        pause = (Button) findViewById(R.id.pause);
        resume = (Button) findViewById(R.id.resume);
        cancel = (Button) findViewById(R.id.cancel);
        text = (TextView) findViewById(R.id.text);

        downloadUtils = new DownloadUtils(URL, getExternalFilesDir("video").getAbsolutePath(), "7.mp4");
        downloadUtils.setOnDownloadListener(this);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadUtils.start();
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadUtils.pause();
            }
        });
        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadUtils.resume();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadUtils.cancel();
            }
        });
    }

    @Override
    public void onDownloadWait() {

    }

    @Override
    public void onDownloadStart() {

    }

    @Override
    public void onDownloadProgress(long length, long range_length, double progress, long speed, long remain_time) {
        progressBar.setMax((int) range_length);
        progressBar.setProgress((int) length);
        Message message = Message.obtain();
        Bundle bundle = new Bundle();
        bundle.putLong("speed", speed);
        bundle.putLong("length", length);
        bundle.putLong("range_length", range_length);
        bundle.putLong("remain_time", remain_time);
        message.setData(bundle);
        handler.sendMessage(message);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            text.setText("下载速度：" + bundle.getLong("speed") + "kb/s\n" + bundle.getLong("length") + "/" + bundle.getLong("range_length") + "\n" + "剩余时间：" + TimeUtils.second2TimeStr(bundle.getLong("remain_time")));
        }
    };

    @Override
    public void onDownloadPause() {

    }

    @Override
    public void onDownloadComplete() {

    }

    @Override
    public void onDownloadCancel() {

    }

    @Override
    public void onDownloadError(String msg) {
        text.setText(msg);
    }
}
