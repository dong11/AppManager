package com.rex.hwong.appmanager.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * @author dong {hwongrex@gmail.com}
 * @date 16/9/20
 * @time 上午11:06
 */

public class Service2 extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
