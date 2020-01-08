package com.example.crashapp.util;

import android.util.Log;

public class MyCrashHandler implements Thread.UncaughtExceptionHandler {

    /*在虚拟机中，当一个线程如果没有显式处理（即 try catch）异常而抛出时会将该异常事件报告给该线程对象的
    java.lang.Thread.UncaughtExceptionHandler 进行处理，
    如果线程没有设置 UncaughtExceptionHandler，则默认会把异常栈信息输出到终端而使程序直接崩溃。
    所以如果我们想在线程意外崩溃时做一些处理就可以通过实现 UncaughtExceptionHandler 来满足需求。*/
    private Thread.UncaughtExceptionHandler mUncaughtExceptionHandler;

    public MyCrashHandler() {
        mUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    //当前线程的Handler处理异常信息
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Log.e("zxy", "myCrashHandler...");
        mUncaughtExceptionHandler.uncaughtException(t, e);
    }

    //设置该CrashHandler为系统默认
    public static void register() {
        Thread.setDefaultUncaughtExceptionHandler(new MyCrashHandler());
    }

}
