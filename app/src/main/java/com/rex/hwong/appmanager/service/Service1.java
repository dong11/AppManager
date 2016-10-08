package com.rex.hwong.appmanager.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.rex.hwong.appmanager.MyApplication;
import com.rex.hwong.appmanager.receiver.ScreenListener;
import com.rex.hwong.appmanager.utils.AppUtils;
import com.rex.hwong.appmanager.view.MyWindowManager;

/**
 * @author dong {hwongrex@gmail.com}
 * @date 16/9/20
 * @time 上午11:05
 */

public class Service1 extends Service {

    private ScreenListener screenListener;

    @Override
    public void onCreate() {
        super.onCreate();
        startListener();
        MyWindowManager.createSmallWindow(getApplicationContext());
        MyApplication.mAppList = AppUtils.getAppList(getApplicationContext());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startListener(){
        screenListener = new ScreenListener(this) ;
        screenListener.begin(new ScreenListener.ScreenStateListener() {
            @Override
            public void onScreenOn() {
                Log.i("123", "屏幕打开了");
            }

            @Override
            public void onScreenOff() {
                Log.i("123", "屏幕关闭了");
            }

            @Override
            public void onUserPresent() {
                Log.i("123", "解锁了");
            }
        });
    }
}
