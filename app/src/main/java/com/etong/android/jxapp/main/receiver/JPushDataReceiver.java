package com.etong.android.jxapp.main.receiver;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.receiver.BaseJPushReceiver;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.FramePersonalLoginActivity;
import com.etong.android.frame.user.FrameRepayRemindInfo;
import com.etong.android.frame.user.FrameUserInfo;
import com.etong.android.jxappcarfinancial.activity.CF_RepaymentRemindActivity;

import cn.jpush.android.api.JPushInterface;

/**
 * @author : by sunyao
 * @ClassName : JPushDataReceiver
 * @Description : (这里用一句话描述这个类的作用)
 * @date : 2016/11/9 - 19:45
 */

public class JPushDataReceiver extends BaseJPushReceiver {

    public static final String PUSH_STATUS = "msg";      //

    /**
     * @desc (用户处理自定义消息)
     * @user sunyao
     * @createtime 2016/11/17 - 14:18
     * @param
     * @return
     */
    @Override
    public void handleCustomMessage(Context context, Bundle bundle) {

    }

    @Override
    public void handNotification(Context context, Bundle bundle) {

    }

    /**
     * @desc (点击状态栏之后的操作)
     * @user sunyao
     * @createtime 2016/11/9 - 19:47
     * @param
     * @return
     */
    @Override
    public void openNotification(Context context, Bundle bundle) {
        // "pushstatus", "1"   ---       推送是否要补录信息的状态
        // "pushstatus", "2"   ---       推送通知告知报告已生成，
        if(bundle != null) {
            String extra = (String) bundle.get(JPushInterface.EXTRA_EXTRA);
            if(!TextUtils.isEmpty(extra)) {
                try {
                    // 从服务器获取到数据
                    JSONObject jsonExtra = JSON.parseObject(extra);
                    String msg = (String) jsonExtra.get(PUSH_STATUS);

                    if (!TextUtils.isEmpty(msg)) {

                        FrameRepayRemindInfo remindInfo = JSON.parseObject(msg, FrameRepayRemindInfo.class);
                        if (remindInfo != null) {
                            FrameEtongApplication.getApplication().addRepaymentRemindInfo(remindInfo);
                        }

                        //  判断用户是否有登录过，如果没有登录则进入到登录的界面
                        FrameUserInfo user = FrameEtongApplication.getApplication().getUserInfo();
                        Intent i = new Intent();
                        if(user != null) {
                            if(!CF_RepaymentRemindActivity.isFont) {
                                i.setClass(context, CF_RepaymentRemindActivity.class);
                            }
                        } else {
                            i.setClass(context, FramePersonalLoginActivity.class);
                        }
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(i);
                    }
                } catch (Exception e) {

                }
            }
        }
    }
}
