package com.rex.hwong.appmanager.utils;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.rex.hwong.appmanager.bean.AppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dong {hwongrex@gmail.com}
 * @date 16/7/3
 * @time 上午1:59
 */

public class AppUtils {

    public static List<AppInfo> getAppList(Context application) {
        List<AppInfo> list = new ArrayList<>();
        PackageManager packageManager = application.getPackageManager();
        List<PackageInfo> mAllPackages = packageManager.getInstalledPackages(0);
        for (int i = 0; i < mAllPackages.size(); i++) {
            PackageInfo packageInfo = mAllPackages.get(i);
            if (filterApp(packageInfo.applicationInfo)) {
                AppInfo info = new AppInfo();
                info.setName("" + packageInfo.applicationInfo.loadLabel(packageManager));
                info.setPackageName(packageInfo.applicationInfo.packageName);
                info.setDrawable(packageInfo.applicationInfo.loadIcon(packageManager));
                list.add(info);
            }
        }
        return list;
    }

    /**
     * 三方应用程序的过滤器
     *
     * @param info
     * @return true 三方应用 false 系统应用
     */
    public static boolean filterApp(ApplicationInfo info) {
        if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
            // 代表的是系统的应用,但是被用户升级了. 用户应用
            return true;
        } else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
            // 代表的用户的应用
            return true;
        }
        return false;
    }
}
