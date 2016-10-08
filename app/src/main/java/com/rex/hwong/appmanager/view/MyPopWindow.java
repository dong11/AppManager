package com.rex.hwong.appmanager.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.rex.hwong.appmanager.R;

/**
 * @author dong {hwongrex@gmail.com}
 * @date 16/7/2
 * @time 下午7:53
 */

public class MyPopWindow {
    private PopupWindow mPopupWindow;
    private Context mContext;
    private View mPopView;
    private View viewParent;

    public MyPopWindow(Context context) {
        mContext = context;

        viewParent = ((Activity)context).findViewById(android.R.id.content);

        mPopView = LayoutInflater.from(context).inflate(R.layout.activity_open_app, null);

        mPopupWindow = new PopupWindow(mPopView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
//        mPopupWindow.setAnimationStyle(R.style.popwin_anim_style);
//
    }

    public void show(){
        mPopupWindow.showAtLocation(viewParent, Gravity.CENTER, 0, 0);
    }

    public void dismiss(){
        mPopupWindow.dismiss();
    }
}
