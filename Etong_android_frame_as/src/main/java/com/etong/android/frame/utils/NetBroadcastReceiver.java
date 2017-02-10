package com.etong.android.frame.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.etong.android.frame.user.FrameEtongApplication;

import java.util.ArrayList;

/**
 * @desc (监听网络的广播)
 * @createtime 2016/12/16 0016--14:55
 * @Created by wukefan.
 */
public class NetBroadcastReceiver extends BroadcastReceiver {
    public static ArrayList<netEventHandler> mListeners = new ArrayList<netEventHandler>();
    private static String NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(NET_CHANGE_ACTION)) {
            FrameEtongApplication.mNetWorkState = NetUtil.getNetworkState(context);
            if (mListeners.size() > 0)// 通知接口完成加载
                for (netEventHandler handler : mListeners) {
                    handler.onNetChange();
                }
        }
    }

    public static abstract interface netEventHandler {
        public abstract void onNetChange();
    }
}
