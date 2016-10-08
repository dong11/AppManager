package com.rex.hwong.appmanager.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rex.hwong.appmanager.OpenAppActivity;
import com.rex.hwong.appmanager.R;

import java.lang.reflect.Field;

/**
 * @author dong {hwongrex@gmail.com}
 * @date 16/6/29
 * @time 上午11:38
 */

public class FloatWindowSmallView extends LinearLayout{
    /**
     * 记录小悬浮窗的宽度
     */
    public static int viewWidth;

    /**
     * 记录小悬浮窗的高度
     */
    public static int viewHeight;

    /**
     * 记录系统状态栏的高度
     */
    private static int statusBarHeight;

    /**
     * 用于更新小悬浮窗的位置
     */
    private WindowManager windowManager;

    /**
     * 小悬浮窗的参数
     */
    private WindowManager.LayoutParams mParams;

    /**
     * 记录当前手指位置在屏幕上的横坐标值
     */
    private float xInScreen;

    /**
     * 记录当前手指位置在屏幕上的纵坐标值
     */
    private float yInScreen;

    /**
     * 记录手指按下时在屏幕上的横坐标的值
     */
    private float xDownInScreen;

    /**
     * 记录手指按下时在屏幕上的纵坐标的值
     */
    private float yDownInScreen;

    /**
     * 记录手指按下时在小悬浮窗的View上的横坐标的值
     */
    private float xInView;

    /**
     * 记录手指按下时在小悬浮窗的View上的纵坐标的值
     */
    private float yInView;

    private Context mContext;

    private GestureDetectorCompat mDetector;

    public FloatWindowSmallView(final Context context) {
        super(context);
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater.from(context).inflate(R.layout.popupwindow, this);
        final View view = findViewById(R.id.popwindow);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;
        mDetector = new GestureDetectorCompat(context, new MyGestureListener());
        mContext = context;
    }

//    long time = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 手指按下时记录必要数据,纵坐标的值都需要减去状态栏高度
                xInView = event.getX();
                yInView = event.getY();
                xDownInScreen = event.getRawX();
                yDownInScreen = event.getRawY() - getStatusBarHeight();
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - getStatusBarHeight();
                break;
            case MotionEvent.ACTION_MOVE:
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - getStatusBarHeight();
                // 手指移动的时候更新小悬浮窗的位置
                updateViewPosition();
                break;
            case MotionEvent.ACTION_UP:
                // 如果手指离开屏幕时，xDownInScreen和xInScreen相等，且yDownInScreen和yInScreen相等，则视为触发了单击事件。
                /*if(time == 0){
                    time = System.currentTimeMillis();
                } else {
                    long curTime = System.currentTimeMillis();
                    if(curTime - time < 200){
                        try {
                            PackageManager packageManager = mContext.getPackageManager();
                            Intent intent = packageManager.getLaunchIntentForPackage("com.tencent.mm");
                            mContext.startActivity(intent);
                        } catch (Exception e) {
                            Toast.makeText(mContext, "没有安装微信客户端！", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                    time = curTime;
                }

                if (Math.abs(xDownInScreen - xInScreen) < 10 && Math.abs(yDownInScreen - yInScreen) < 10) {
//                    openBigWindow();
                }*/
                break;
            default:
                break;
        }
        return mDetector.onTouchEvent(event);
    }


    /**
     * 将小悬浮窗的参数传入，用于更新小悬浮窗的位置。
     *
     * @param params
     *            小悬浮窗的参数
     */
    public void setParams(WindowManager.LayoutParams params) {
        mParams = params;
    }

    /**
     * 更新小悬浮窗在屏幕中的位置。
     */
    private void updateViewPosition() {
        mParams.x = (int) (xInScreen - xInView);
        mParams.y = (int) (yInScreen - yInView);
        windowManager.updateViewLayout(this, mParams);
    }

    /**
     * 用于获取状态栏的高度。
     *
     * @return 返回状态栏高度的像素值。
     */
    private int getStatusBarHeight() {
        if (statusBarHeight == 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object o = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = (Integer) field.get(o);
                statusBarHeight = getResources().getDimensionPixelSize(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusBarHeight;
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";

        @Override
        public boolean onDown(MotionEvent event) {
            return super.onDown(event);
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {  //调用两次
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            try {
                PackageManager packageManager = mContext.getPackageManager();
                Intent intent = packageManager.getLaunchIntentForPackage("com.tencent.mm");
                mContext.startActivity(intent);
            } catch (Exception ex) {
                Toast.makeText(mContext, "没有安装微信客户端！", Toast.LENGTH_SHORT).show();
                ex.printStackTrace();
            }
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            Log.i("123", "::X::" + distanceX);
            Log.i("123", "::Y::" + distanceY);

            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
//            openBigWindow();

//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            String packageName = "com.rex.hwong.appmanager";
//            String className = "com.rex.hwong.appmanager.OpenAppActivity";
//            intent.setClassName(packageName, className);

            Intent intent = new Intent(mContext, OpenAppActivity.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
//            MyWindowManager.removeSmallWindow(mContext);
            return super.onSingleTapConfirmed(e);
        }
    }
}
