package com.jian.universaldownloader.download;

/**
 * Created by 七月在线科技 on 2016/8/9.
 */

public interface OnDownloadListener {

    void onDownloadWait();

    void onDownloadStart();

    void onDownloadProgress(long downloadedLength, long range_length, double progress,long speed,long remain_time);

    void onDownloadPause();

    void onDownloadComplete();

    void onDownloadCancel();

    void onDownloadError(String msg);

}
