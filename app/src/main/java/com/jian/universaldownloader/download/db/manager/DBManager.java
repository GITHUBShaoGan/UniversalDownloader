package com.jian.universaldownloader.download.db.manager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jian.universaldownloader.App;
import com.jian.universaldownloader.download.db.constants.DBConstants;
import com.jian.universaldownloader.download.db.helper.DBHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 七月在线科技 on 2016/8/9.
 */

public class DBManager {

    private static volatile DBManager instances;
    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private Cursor cursor;

    public static DBManager getInstances() {
        if (instances == null) {
            synchronized (DBManager.class) {
                if (instances == null) {
                    instances = new DBManager();
                }
            }
        }
        return instances;
    }

    public DBManager() {
        dbHelper = new DBHelper(App.getContext(), DBConstants.DB_NAME, null, DBConstants.DB_VERSION);
        database = dbHelper.getWritableDatabase();
    }

    /**
     * 更新数据库记录
     *
     * @param sql
     * @param args
     */
    public void updateByArgs(String sql, Object[] args) {
        try {
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    if (args[i] == null) {
                        args[i] = "";
                    }
                }
                database.execSQL(sql, args);
            } else {
                database.execSQL(sql);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询单条记录
     *
     * @param sql
     * @param args
     * @param cls
     * @param <T>
     * @return
     */
    public <T> T querySimpleResult(String sql, String[] args, Class<T> cls) {
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                if (args[i] == null) {
                    args[i] = "";
                }
            }
        }
        T result = null;
        try {
            cursor = database.rawQuery(sql, args);
            int col_len = cursor.getColumnCount();
            while (cursor.moveToNext()) {
                result = cls.newInstance();
                for (int i = 0; i < col_len; i++) {
                    String col_name = cursor.getColumnName(i);
                    String col_value = cursor.getString(cursor.getColumnIndex(col_name));
                    if (col_value == null) {
                        col_value = "";
                    }
                    Field field = cls.getDeclaredField(col_name);
                    field.setAccessible(true);
                    field.set(result, col_value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 查询多条记录
     *
     * @param sql
     * @param args
     * @param cls
     * @param <T>
     * @return
     */
    public <T> List<T> queryMoreResult(String sql, String[] args, Class<T> cls) {
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                if (args[i] == null) {
                    args[i] = "";
                }
            }
        }
        List<T> list = new ArrayList<>();
        try {
            cursor = database.rawQuery(sql, args);
            int col_len = cursor.getColumnCount();
            while (cursor.moveToNext()) {

                T result = cls.newInstance();
                for (int i = 0; i < col_len; i++) {
                    String col_name = cursor.getColumnName(i);
                    String col_value = cursor.getString(cursor.getColumnIndex(col_name));
                    if (col_value == null) {
                        col_value = "";
                    }
                    Field field = cls.getDeclaredField(col_name);
                    field.setAccessible(true);
                    field.set(result, col_value);
                }
                list.add(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 释放数据库资源
     */
    public void releaseDB() {
        if (cursor != null) {
            cursor.close();
            cursor = null;
        }
        if (database != null) {
            database.close();
            database = null;
        }
    }

}
