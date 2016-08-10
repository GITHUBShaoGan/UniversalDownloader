package com.jian.universaldownloader.download;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.jian.universaldownloader.download.db.bean.DownloadFileInfo;
import com.jian.universaldownloader.download.db.dao.DownloadFileInfoDao;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 七月在线科技 on 2016/8/9.
 */

public class DownloadUtils {

    private String url;
    private String file_dir;
    private String file_name;
    private long range_length;
    private long downloaded_length;
    private int status;
    private double progress;
    private long speed;
    private long remainTime;

    private OnDownloadListener onDownloadListener;

    public DownloadUtils(String url, String file_dir, String file_name) {
        this.url = url;
        this.file_dir = file_dir;
        this.file_name = file_name;
    }

    public long getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(long remainTime) {
        this.remainTime = remainTime;
    }

    public long getSpeed() {
        return speed;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }

    public void setOnDownloadListener(OnDownloadListener onDownloadListener) {
        this.onDownloadListener = onDownloadListener;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public void start() {
        onDownloadListener.onDownloadStart();
        status = Status.START;
        new DownloadThread().start();
    }

    public void pause() {
        status = Status.PAUSE;
    }

    public void resume() {
        new DownloadThread().start();
    }

    public void cancel() {
        status = Status.CANCEL;
    }

    private class DownloadThread extends Thread {

        private long length;

        public long getLength() {
            return length;
        }

        public void setLength(long length) {
            this.length = length;
        }

        @Override
        public void run() {
            super.run();
            try {
                new CalculateSpeedThread().start();
                DownloadFileInfo downloadFileInfo = DownloadFileInfoDao.getInstances().querySimple(url);
                URL url = new URL(DownloadUtils.this.url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept",
                        "image/gif, image/jpeg, image/pjpeg, image/pjpeg, "
                                + "application/x-shockwave-flash, application/xaml+xml, "
                                + "application/vnd.ms-xpsdocument, application/x-ms-xbap, "
                                + "application/x-ms-application, application/vnd.ms-excel, "
                                + "application/vnd.ms-powerpoint, application/msword, */*");
                conn.setRequestProperty("Accept-Language", "zh-CN");
                conn.setRequestProperty("Charset", "UTF-8");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("Accept-Encoding", "identity");
                range_length = conn.getContentLength();
                if (downloadFileInfo == null) {
                    DownloadFileInfoDao.getInstances().insertOrUpdate(url.toString(), range_length + "", 0 + "", file_dir, file_name, Status.WAIT + "", System.currentTimeMillis() + "");
                }
                downloadFileInfo = DownloadFileInfoDao.getInstances().querySimple(DownloadUtils.this.url);
                RandomAccessFile randomAccessFile = new RandomAccessFile(file_dir + File.separator + file_name, "rw");
                randomAccessFile.seek(Long.valueOf(downloadFileInfo.getDownloaded_length()));
                InputStream inputStream = conn.getInputStream();
                inputStream.skip(Long.valueOf(downloadFileInfo.getDownloaded_length()));
                length = Long.valueOf(downloadFileInfo.getDownloaded_length());
                byte[] buffer = new byte[2048];
                int readLength = 0;
                status = Status.PROGRESS;
                while (length < range_length && (readLength = inputStream.read(buffer)) != -1) {
                    length += readLength;
                    downloaded_length = length;
                    setProgress(length * 100 / range_length);
                    onDownloadListener.onDownloadProgress(length, range_length, getProgress(), getSpeed(), getRemainTime());
                    DownloadFileInfoDao.getInstances().update(DownloadUtils.this.url, range_length + "", length + "", file_dir, file_name, Status.PROGRESS + "", System.currentTimeMillis() + "");
                    randomAccessFile.write(buffer, 0, readLength);
                    if (status == Status.PAUSE) {
                        onDownloadListener.onDownloadPause();
                        DownloadFileInfoDao.getInstances().update(DownloadUtils.this.url, range_length + "", length + "", file_dir, file_name, Status.PAUSE + "", System.currentTimeMillis() + "");
                        break;
                    }
                    if (status == Status.CANCEL) {
                        onDownloadListener.onDownloadCancel();
                        DownloadFileInfoDao.getInstances().update(DownloadUtils.this.url, range_length + "", length + "", file_dir, file_name, Status.CANCEL + "", System.currentTimeMillis() + "");
                        break;
                    }
                }
                randomAccessFile.close();
                inputStream.close();
                conn.disconnect();
                status = Status.COMPLETE;
                DownloadFileInfoDao.getInstances().update(DownloadUtils.this.url, range_length + "", length + "", file_dir, file_name, Status.COMPLETE + "", System.currentTimeMillis() + "");
                onDownloadListener.onDownloadComplete();
            } catch (Exception e) {
                e.printStackTrace();
                status = Status.ERROR;
                Message message = Message.obtain();
                message.what = Status.ERROR;
                Bundle bundle = new Bundle();
                bundle.putString("error", e.getLocalizedMessage());
                message.setData(bundle);
                handler.sendMessage(message);

            }
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Status.ERROR:
                    Bundle bundle = msg.getData();
                    String error = bundle.getString("error");
                    onDownloadListener.onDownloadError(error);
                    break;
            }
        }
    };

    private class CalculateSpeedThread extends Thread {

        @Override
        public void run() {
            super.run();
            while (true) {
                try {
                    long preLength = downloaded_length;
                    this.sleep(1000);
                    long endLength = downloaded_length;
                    long speed = (endLength - preLength) / 1024;
                    setSpeed(speed);
                    setRemainTime((range_length - downloaded_length) / speed / 1024);
                } catch (Exception e) {

                }
            }
        }

    }
}
