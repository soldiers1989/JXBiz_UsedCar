package com.etong.android.jxappme.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappme.R;

/**
 * Created by Administrator on 2016/9/1 0001.
 */
public class MeSettingActivity extends BaseSubscriberActivity {


    private TitleBar mTitleBar;
    private TextView mFontSizeTxt;
    private TextView mCacheTxt;
    private TextView mUpdateTxt;
    private ToggleButton mWifiTb;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.me_activity_setting);

        mTitleBar = new TitleBar(this);
        mTitleBar.setTitle("设置");

        initView();
    }


    private void initView() {

        mWifiTb = findViewById(R.id.me_tb_setting_wifi, ToggleButton.class);
        mFontSizeTxt = findViewById(R.id.me_txt_setting_font_size, TextView.class);
        mCacheTxt = findViewById(R.id.me_txt_setting_cache_size, TextView.class);
        mUpdateTxt = findViewById(R.id.me_txt_setting_update, TextView.class);

        addClickListener(R.id.me_txt_setting_notifications);
        addClickListener(R.id.me_txt_setting_share);
        addClickListener(R.id.me_txt_setting_invite_friends);
        addClickListener(R.id.me_rl_setting_font_size);
        addClickListener(R.id.me_rl_setting_cache);
        addClickListener(R.id.me_txt_setting_richscan);
        addClickListener(R.id.me_txt_setting_feedback);
        addClickListener(R.id.me_txt_setting_help);
        addClickListener(R.id.me_rl_setting_check_update);
        addClickListener(R.id.me_txt_setting_about_me);


        mWifiTb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 当按钮第一次被点击时候响应的事件
                if (mWifiTb.isChecked()) {
                    toastMsg("开启省流量模式");
                } else { // 当按钮再次被点击时候响应的事件
                    toastMsg("关闭省流量模式");
                }
            }
        });

    }

    @Override
    protected void onClick(View v) {
        if (v.getId() == R.id.me_txt_setting_notifications) {//消息提醒
            toastMsg("消息提醒");
        } else if (v.getId() == R.id.me_txt_setting_share) {//分享设置
            toastMsg("分享设置");
        } else if (v.getId() == R.id.me_txt_setting_invite_friends) {//邀请好友使用
            toastMsg("邀请好友使用");
        } else if (v.getId() == R.id.me_rl_setting_font_size) {//字号大小
            toastMsg("字号大小");
        } else if (v.getId() == R.id.me_rl_setting_cache) {//消息缓存
            toastMsg("消息缓存");
        } else if (v.getId() == R.id.me_txt_setting_richscan) {//扫一扫
            toastMsg("扫一扫");
        } else if (v.getId() == R.id.me_txt_setting_feedback) {//意见反馈
            toastMsg("意见反馈");
        } else if (v.getId() == R.id.me_txt_setting_help) {//支付帮助
            toastMsg("支付帮助");
        } else if (v.getId() == R.id.me_rl_setting_check_update) {//检测更新
            toastMsg("检测更新");
        } else if (v.getId() == R.id.me_txt_setting_about_me) {//关于我们
            toastMsg("关于我们");
        }
    }
}
