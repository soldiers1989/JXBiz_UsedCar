package com.etong.android.frame.utils;

import android.view.View;
import java.util.Calendar;

/**
 * @desc 防止多次点击的监听事件
 * @createtime 2016/11/16 - 15:51
 * @Created by xiaoxue.
 */

public abstract class EtongNoDoubleClickListener implements View.OnClickListener {

    public static final int MIN_CLICK_DELAY_TIME = 2000;
    private long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }
    protected abstract void onNoDoubleClick(View v);

}
