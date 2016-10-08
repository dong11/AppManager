package com.rex.hwong.appmanager.adapter;

import android.content.Context;

import com.rex.hwong.appmanager.R;
import com.rex.hwong.appmanager.bean.AppInfo;

import java.util.List;

/**
 * @author dong {hwongrex@gmail.com}
 * @date 16/6/29
 * @time 下午10:52
 */

public class RVAppInfo extends AutoRVAdapter {

    private List<AppInfo> mList;

    public RVAppInfo(Context context, List<AppInfo> list) {
        super(context, list);
        mList = list;
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_appinfo;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.getImageView(R.id.iv_app_icon).setImageDrawable(mList.get(position).getDrawable());
        holder.getTextView(R.id.tv_app_name).setText(mList.get(position).getName());
    }
}
