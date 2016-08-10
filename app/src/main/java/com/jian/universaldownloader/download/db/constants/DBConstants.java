package com.jian.universaldownloader.download.db.constants;

import android.provider.BaseColumns;

/**
 * Created by 七月在线科技 on 2016/8/9.
 */

public class DBConstants {

    public static final String DB_NAME = "download_info.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_DOWNLOAD_FILE_INFO = "download_file_info";

    public class DownloadFileInfo implements BaseColumns {

        public static final String UUID = "uuid";
        public static final String URL = "url";
        public static final String RANGE_LENGTH = "range_length";
        public static final String DOWNLOADED_LENGTH = "downloaded_length";
        public static final String FILE_DIR = "file_dir";
        public static final String FILE_NAME = "file_name";
        public static final String DOWNLOADED_STATUS = "downloaded_status";
        public static final String UPDATE_TIME = "update_time";

    }

}
