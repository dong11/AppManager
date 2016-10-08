package com.rex.hwong.appmanager;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.rex.hwong.appmanager.adapter.RVAppInfo;
import com.rex.hwong.appmanager.bean.AppInfo;
import com.rex.hwong.appmanager.utils.ScreenUtils;
import com.rex.hwong.appmanager.view.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class OpenAppActivity extends Activity {

    private RVAppInfo mRVAppInfo;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        View view = LayoutInflater.from(this).inflate(R.layout.activity_open_app, null);
        setContentView(view);
        view.setLayoutParams(new FrameLayout.LayoutParams(ScreenUtils.getScreenWidth(this) * 2 / 3, ScreenUtils.getScreenHeight(this) / 2));


        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        ObjectAnimator oa1 = ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f);
        oa1.setDuration(300);
        ObjectAnimator oa2 = ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f);
        oa2.setDuration(300);
        AnimatorSet as = new AnimatorSet();
        as.playTogether(oa1, oa2);

//        as.start();

        init();
    }

    private void init() {
        if (MyApplication.mAppList == null || (MyApplication.mAppList != null && MyApplication.mAppList.size() == 0)) {
            MyApplication.mAppList = getAppList();
        }

        mRVAppInfo = new RVAppInfo(this, MyApplication.mAppList);
        mRVAppInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    PackageManager packageManager = getPackageManager();
                    Intent intent = packageManager.getLaunchIntentForPackage(MyApplication.mAppList.get(position).getPackageName());
                    startActivity(intent);
//                    MyWindowManager.createSmallWindow(getApplicationContext());
                    finish();
                } catch (Exception ex) {
                    Toast.makeText(OpenAppActivity.this, String.format("没有安装s%客户端！", MyApplication.mAppList.get(position).getName()), Toast.LENGTH_SHORT).show();
                    ex.printStackTrace();
                }
            }
        });

        mRecyclerView.addItemDecoration(new SpaceItemDecoration(50));
        mRecyclerView.setAdapter(mRVAppInfo);
    }

    /**
     * 获取手机安装的第三方应用
     * @return
     */
    private List<AppInfo> getAppList() {
        List<AppInfo> list = new ArrayList<>();
        PackageManager packageManager = getPackageManager();
        List<PackageInfo> mAllPackages = packageManager.getInstalledPackages(0);
        for (int i = 0; i < mAllPackages.size(); i++) {
            PackageInfo packageInfo = mAllPackages.get(i);
            if (filterApp(packageInfo.applicationInfo)) {
                AppInfo info = new AppInfo();
//				Log.i("123", ":package path:" + packageInfo.applicationInfo.sourceDir);
                info.setName("" + packageInfo.applicationInfo.loadLabel(packageManager));
                info.setPackageName(packageInfo.applicationInfo.packageName);
                info.setDrawable(packageInfo.applicationInfo.loadIcon(packageManager));
                list.add(info);
            }
        }
        return list;
    }

    /**
     * 第三方应用程序的过滤器
     *
     * @param info
     * @return true：第三方应用  false：系统应用
     */
    public boolean filterApp(ApplicationInfo info) {
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
