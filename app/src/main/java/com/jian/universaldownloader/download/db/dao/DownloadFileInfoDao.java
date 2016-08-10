package com.jian.universaldownloader.download.db.dao;

import com.jian.universaldownloader.download.db.bean.DownloadFileInfo;
import com.jian.universaldownloader.download.db.constants.DBConstants;
import com.jian.universaldownloader.download.db.manager.DBManager;

import java.util.UUID;

/**
 * Created by 七月在线科技 on 2016/8/9.
 */

public class DownloadFileInfoDao {

    private static volatile DownloadFileInfoDao instances;

    public static DownloadFileInfoDao getInstances() {
        if (instances == null) {
            synchronized (DownloadFileInfoDao.class) {
                if (instances == null) {
                    instances = new DownloadFileInfoDao();
                }
            }
        }
        return instances;
    }

    public void insertOrUpdate(String url, String range_length, String downloaded_length,
                               String file_dir, String file_name, String downloaded_status,
                               String update_time) {
        if (querySimple(url) == null) {
            String sql = "insert into "
                    + DBConstants.TABLE_DOWNLOAD_FILE_INFO
                    + " values(?,?,?,?,?,?,?,?)";
            Object[] args = {UUID.randomUUID().toString(), url, range_length,
                    downloaded_length, file_dir, file_name, downloaded_status, update_time};
            DBManager.getInstances().updateByArgs(sql, args);
        } else {
            update(url, range_length, downloaded_length, file_dir, file_name, downloaded_status, update_time);
        }
    }

    public DownloadFileInfo querySimple(String url) {
        String sql = "select * from "
                + DBConstants.TABLE_DOWNLOAD_FILE_INFO
                + " where url = ?";
        String[] args = {url};
        DownloadFileInfo downloadFileInfo = DBManager.getInstances().querySimpleResult(sql, args, DownloadFileInfo.class);
        return downloadFileInfo;
    }

    public void update(String url, String range_length, String downloaded_length,
                       String file_dir, String file_name, String downloaded_status,
                       String update_time) {
        String sql = "update "
                + DBConstants.TABLE_DOWNLOAD_FILE_INFO
                + " set range_length=?,downloaded_length=?,file_dir=?,file_name=?,downloaded_status=?,update_time=? where url = ?";
        Object[] args = {range_length, downloaded_length, file_dir, file_name, downloaded_status, update_time, url};
        DBManager.getInstances().updateByArgs(sql, args);
    }

    public void delete(String url) {
        String sql = "delete from "
                + DBConstants.TABLE_DOWNLOAD_FILE_INFO
                + " where url = ?";
        Object[] args = {sql};
        DBManager.getInstances().updateByArgs(sql, args);
    }
}
