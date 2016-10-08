package com.rex.hwong.appmanager;

import android.content.Context;

import com.marswin89.marsdaemon.DaemonApplication;
import com.marswin89.marsdaemon.DaemonConfigurations;
import com.rex.hwong.appmanager.bean.AppInfo;
import com.rex.hwong.appmanager.receiver.Receiver1;
import com.rex.hwong.appmanager.receiver.Receiver2;
import com.rex.hwong.appmanager.service.Service1;
import com.rex.hwong.appmanager.service.Service2;

import java.util.List;

/**
 * @author dong {hwongrex@gmail.com}
 * @date 16/9/20
 * @time 上午11:04
 */

public class MyApplication extends DaemonApplication {
    public static List<AppInfo> mAppList;

    /**
     * give the configuration to lib in this callback
     * @return
     */
    @Override
    protected DaemonConfigurations getDaemonConfigurations() {
        DaemonConfigurations.DaemonConfiguration configuration1 = new DaemonConfigurations.DaemonConfiguration(
                "com.rex.hwong.appmanager:process1",
                Service1.class.getCanonicalName(),
                Receiver1.class.getCanonicalName());

        DaemonConfigurations.DaemonConfiguration configuration2 = new DaemonConfigurations.DaemonConfiguration(
                "com.rex.hwong.appmanager:process2",
                Service2.class.getCanonicalName(),
                Receiver2.class.getCanonicalName());

        DaemonConfigurations.DaemonListener listener = new MyDaemonListener();
        //return new DaemonConfigurations(configuration1, configuration2);//listener can be null
        return new DaemonConfigurations(configuration1, configuration2, listener);
    }

    class MyDaemonListener implements DaemonConfigurations.DaemonListener{
        @Override
        public void onPersistentStart(Context context) {
        }

        @Override
        public void onDaemonAssistantStart(Context context) {
        }

        @Override
        public void onWatchDaemonDaed() {
        }
    }
}
