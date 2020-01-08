package com.example.crashapp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;
import android.view.View;

import com.example.crashapp.gen.DaoMaster;
import com.example.crashapp.gen.DaoSession;
import com.example.crashapp.ui.MainActivity;
import com.example.crashapp.util.MyCrashHandler;
import com.example.crashapp.util.MyOpenHelper;
import com.github.yuweiguocn.library.greendao.MigrationHelper;
import com.zxy.recovery.callback.RecoveryCallback;
import com.zxy.recovery.core.Recovery;

import org.greenrobot.greendao.database.Database;

public class MyApplication extends Application {
    private static final String TAG = "Crash";
    private static Context mContext;
    private static final String DATABASE_NAME = "test.db";
    // 是否加密
    public static final boolean ENCRYPTED = false;
    private static final String ENCRYPT_PWD = "sd";
    private static DaoSession daoSession;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }



    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        Recoveryinit();
        initGreenDAO();
    }

    //Recovery防宕机机制
    private void Recoveryinit() {
        Recovery.getInstance()
                .debug(true)
                .recoverInBackground(false)
                .recoverStack(true)
                .mainPage(MainActivity.class)
                .recoverEnabled(true)
                .callback(new MyCrashCallback())
                .silent(false, Recovery.SilentMode.RECOVER_ACTIVITY_STACK)
//                .skip(MainActivity.class)
                .init(this);
        MyCrashHandler.register();
    }

    static final class MyCrashCallback implements RecoveryCallback {
        @Override
        public void stackTrace(String exceptionMessage) {
            Log.e("Recoveryinit", "exceptionMessage:" + exceptionMessage);
        }

        @Override
        public void cause(String cause) {
            Log.e("Recoveryinit", "cause:" + cause);
        }

        @Override
        public void exception(String exceptionType, String throwClassName, String throwMethodName, int throwLineNumber) {
            Log.e("Recoveryinit", "exceptionClassName:" + exceptionType);
            Log.e("Recoveryinit", "throwClassName:" + throwClassName);
            Log.e("Recoveryinit", "throwMethodName:" + throwMethodName);
            Log.e("Recoveryinit", "throwLineNumber:" + throwLineNumber);
        }

        @Override
        public void throwable(Throwable throwable) {

        }
    }

    /**
     * 初始化greendao---------------------------------数据库---------------------
     */
    private void initGreenDAO() {

        MigrationHelper.DEBUG = false; //如果你想查看日志信息，请将DEBUG设置为true
        MyOpenHelper helper = new MyOpenHelper(this, DATABASE_NAME,
                null);
        Database database = null;
        if (ENCRYPTED) {
            database = helper.getEncryptedWritableDb(ENCRYPT_PWD);
        } else {
            database = helper.getWritableDb();
//            helper.getWritableDatabase();//SQLiteDatabase
        }
        DaoMaster daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
    }
    public static DaoSession getDaoSession() {
        return daoSession;
    }
    //------------------------------------------------结束------------------------
    public static Context getInstance() {
        return mContext;
    }
}
