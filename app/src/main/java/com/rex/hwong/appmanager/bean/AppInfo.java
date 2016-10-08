package com.rex.hwong.appmanager.bean;

import android.graphics.drawable.Drawable;

/**
 * @author dong {hwongrex@gmail.com}
 * @date 16/6/29
 * @time 下午6:05
 */

public class AppInfo {
    private String mName;
    private String mPackageName;
    private Drawable mDrawable;

    public AppInfo() {
    }

    public AppInfo(String name, String packageName, Drawable drawable) {
        mName = name;
        mPackageName = packageName;
        mDrawable = drawable;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public void setPackageName(String packageName) {
        mPackageName = packageName;
    }

    public Drawable getDrawable() {
        return mDrawable;
    }

    public void setDrawable(Drawable drawable) {
        mDrawable = drawable;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "mName='" + mName + '\'' +
                ", mPackageName='" + mPackageName + '\'' +
                ", mDrawable=" + mDrawable +
                '}';
    }
}
