package com.rex.hwong.appmanager;

import android.Manifest;
import android.app.AppOpsManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.rex.hwong.appmanager.service.Service1;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 0x01;

    private static final String TAG = "AppManager";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start();
    }

    /*private void show() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SYSTEM_ALERT_WINDOW) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
            Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            start();
        }
    }*/

    private void start(){
        if(isMIUI()&&!isMiuiFloatWindowOpAllowed(MainActivity.this)){
            gotoPermissionSettings(MainActivity.this);
        } else {
            startService(new Intent(MainActivity.this, Service1.class));
            finish();
        }
    }

    /**
     * 跳转到应用权限设置页面
     * @param context 传入app 或者 activity context，通过context获取应用packegename，之后通过packegename跳转制定应用
     * @return 是否是miui
     */
    private boolean gotoPermissionSettings(Context context) {
        boolean mark = isMIUI();
        if (mark) {
            // 兼容miui v5/v6  的应用权限设置页面，否则的话跳转应用设置页面（权限设置上一级页面）
            try {
                Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                localIntent.setClassName("com.miui.securitycenter","com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                localIntent.putExtra("extra_pkgname", context.getPackageName());
                context.startActivity(localIntent);
            } catch (ActivityNotFoundException e) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(),null);
                intent.setData(uri);
                context.startActivity(intent);
            }
        }
        return mark;
    }

    /**
     * 判断MIUI的悬浮窗权限
     * @param context
     * @return
     */
    private boolean isMiuiFloatWindowOpAllowed(Context context) {
        final int version = Build.VERSION.SDK_INT;

        if (version >= 19) {
            return checkOp(context, 24);  //自己写就是24 为什么是24?看AppOpsManager 即AppOpsManager.OP_SYSTEM_ALERT_WINDOW
        } else {
            if ((context.getApplicationInfo().flags & 1<<27) == 1) {
                return true;
            } else {
                return false;
            }
        }
    }

    private boolean checkOp(Context context, int op) {
        final int version = Build.VERSION.SDK_INT;

        if (version >= 19) {
            AppOpsManager manager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            try {
                if (AppOpsManager.MODE_ALLOWED == (Integer) invokeMethod(manager, "checkOp", op,
                        Binder.getCallingUid(), context.getPackageName())) {  //这儿反射就自己写吧
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                Log.e(TAG,e.getMessage());
            }
        } else {
            Log.e(TAG,"Below API 19 cannot invoke!");
        }
        return false;
    }

    /**
     * 检查手机是否是miui
     * @ref http://dev.xiaomi.com/doc/p=254/index.html
     * @return
     */
    private boolean isMIUI(){
        String device = Build.MANUFACTURER;
        System.out.println( "Build.MANUFACTURER = " + device );
        if ( device.equals( "Xiaomi" ) ) {
            System.out.println( "this is a xiaomi device" );
            return true;
        }
        else{
            return false;
        }
    }

    private int invokeMethod(AppOpsManager manager, String method, int op, int callingUid, String packageName) throws Exception{
        Class<AppOpsManager> clazz = AppOpsManager.class;
        Method dispatchMethod = clazz.getMethod(method, new Class[]{int.class, int.class, String.class});
        int mode = (Integer) dispatchMethod.invoke(manager, new Object[]{op, callingUid, packageName});
        return mode;
    }
}
