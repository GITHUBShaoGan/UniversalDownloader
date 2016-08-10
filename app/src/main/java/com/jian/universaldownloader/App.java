package com.jian.universaldownloader;

import android.app.Application;
import android.content.Context;

import com.jian.universaldownloader.download.db.manager.DBManager;

/**
 * Created by 七月在线科技 on 2016/8/9.
 */

public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        setContext(getApplicationContext());
        DBManager.getInstances();
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        App.context = context;
    }
}
