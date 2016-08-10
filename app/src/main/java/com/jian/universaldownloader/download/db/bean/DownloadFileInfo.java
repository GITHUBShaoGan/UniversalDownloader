package com.jian.universaldownloader.download.db.bean;

import com.jian.universaldownloader.download.db.constants.DBConstants;

/**
 * Created by 七月在线科技 on 2016/8/9.
 */

public class DownloadFileInfo {

    public static final String TABLE_CREATE = "create table if not exists "
            + DBConstants.TABLE_DOWNLOAD_FILE_INFO
            + "("
            + DBConstants.DownloadFileInfo.UUID + " text primary key,"
            + DBConstants.DownloadFileInfo.URL + " text,"
            + DBConstants.DownloadFileInfo.RANGE_LENGTH + " integer,"
            + DBConstants.DownloadFileInfo.DOWNLOADED_LENGTH + " integer,"
            + DBConstants.DownloadFileInfo.FILE_DIR + " text,"
            + DBConstants.DownloadFileInfo.FILE_NAME + " text,"
            + DBConstants.DownloadFileInfo.DOWNLOADED_STATUS + " integer,"
            + DBConstants.DownloadFileInfo.UPDATE_TIME + " text"
            + ")";

    private String uuid;

    private String url;

    private String range_length;

    private String downloaded_length;

    private String file_dir;

    private String file_name;

    private String downloaded_status;

    private String update_time;

    public DownloadFileInfo() {
    }

    public DownloadFileInfo(String uuid, String url, String range_length, String downloaded_length, String file_dir, String file_name, String downloaded_status, String update_time) {
        this.uuid = uuid;
        this.url = url;
        this.range_length = range_length;
        this.downloaded_length = downloaded_length;
        this.file_dir = file_dir;
        this.file_name = file_name;
        this.downloaded_status = downloaded_status;
        this.update_time = update_time;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRange_length() {
        return range_length;
    }

    public void setRange_length(String range_length) {
        this.range_length = range_length;
    }

    public String getDownloaded_length() {
        return downloaded_length;
    }

    public void setDownloaded_length(String downloaded_length) {
        this.downloaded_length = downloaded_length;
    }

    public String getFile_dir() {
        return file_dir;
    }

    public void setFile_dir(String file_dir) {
        this.file_dir = file_dir;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getDownloaded_status() {
        return downloaded_status;
    }

    public void setDownloaded_status(String downloaded_status) {
        this.downloaded_status = downloaded_status;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    @Override
    public String toString() {
        return "DownloadFileInfo{" +
                "uuid='" + uuid + '\'' +
                ", url='" + url + '\'' +
                ", range_length='" + range_length + '\'' +
                ", downloaded_length='" + downloaded_length + '\'' +
                ", file_dir='" + file_dir + '\'' +
                ", file_name='" + file_name + '\'' +
                ", downloaded_status='" + downloaded_status + '\'' +
                ", update_time='" + update_time + '\'' +
                '}';
    }
}
