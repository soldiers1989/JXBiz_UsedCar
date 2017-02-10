package com.etong.android.jxapp.main;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;

import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.subscriber.BaseSubscriberFragment;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.FrameUserInfo;
import com.etong.android.frame.utils.ActivityStackManager;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.utils.NetBroadcastReceiver;
import com.etong.android.frame.utils.NetUtil;
import com.etong.android.jxapp.R;
import com.etong.android.jxapp.main.provider.Main_Provider;
import com.etong.android.jxappfind.content.FindMainFragment;
import com.etong.android.jxappme.view.MeMainFragment;
import com.etong.android.jxappmessage.content.MessageMainFragment;
import com.etong.android.jxappmore.view.MoreMainFragment;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

public class MainContentActivity extends BaseSubscriberActivity implements NetBroadcastReceiver.netEventHandler {

    private RadioButton mRadioButtons[] = new RadioButton[4];

    private BaseSubscriberFragment mFragments[] = new BaseSubscriberFragment[4];

    private int select = 0;// 标注当前显示页面
    private long mExitTime = 0;
    private HttpPublisher mHttpPublisher;
    private Main_Provider mMainProvider;
    private NetBroadcastReceiver myReceiver;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {

        setContentView(R.layout.activity_main_content);

        NetBroadcastReceiver.mListeners.add(MainContentActivity.this);
        // 设置主页Activity不能侧滑
        setSwipeBackEnable(false);

        // 设置极光推送的别名
        FrameEtongApplication.getApplication().setJPushAlias();

        mRadioButtons[0] = findViewById(R.id.main_bottom_find, RadioButton.class);
        mRadioButtons[1] = findViewById(R.id.main_bottom_news, RadioButton.class);
        mRadioButtons[2] = findViewById(R.id.main_bottom_more, RadioButton.class);
        mRadioButtons[3] = findViewById(R.id.main_bottom_mine, RadioButton.class);

        addClickListener(mRadioButtons);
        EventBus.getDefault().register(this);

        initData();
        switchFragment(0);
    }

    private void initData() {
        //初始化请求
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(this);
        mMainProvider = Main_Provider.getInstance();
        mMainProvider.initialize(mHttpPublisher);
        if (FrameEtongApplication.getApplication().isLogin()) {
            mMainProvider.queryUserId();//获取用户信息
        }
    }

    /**
     * @desc (获取用户信息回调)
     * @createtime 2016/12/6 0006-19:17
     * @author wukefan
     */
    @Subscriber(tag = FrameHttpTag.QUERY_USER_ID)
    public void queryUserIdResult(HttpMethod method) {
        String errno = method.data().getString("errno");
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");

        if (!TextUtils.isEmpty(flag) && flag.equals("0")) {
            JSONObject objData = method.data().getJSONObject("data");
            FrameUserInfo mFrameUserInfo = FrameEtongApplication.getApplication().getUserInfo();
            //电话号码
            if (null != objData.getString("phone")) {
                String phone = objData.getString("phone");
                mFrameUserInfo.setUserPhone(phone);
            }
            //用户名字
            if (null != objData.getString("userName")) {
                String userName = objData.getString("userName");
                mFrameUserInfo.setUserName(userName);
            } else {
                mFrameUserInfo.setUserName("");
            }
            //用户头像
            if (null != objData.getString("avatar")) {
                String avatar = objData.getString("avatar");
                mFrameUserInfo.setAvatar(avatar);
            }
            //身份证号
            if (null != objData.getString("cardID")) {
                String cardID = objData.getString("cardID");
                mFrameUserInfo.setUserIdCard(cardID);
            } else {
                mFrameUserInfo.setUserIdCard("");
            }
            //性别
            if (null != objData.getString("sex")) {
                String sex = objData.getString("sex");
                if (sex.equals("1")) {
                    mFrameUserInfo.setUserSex("男");
                } else if (sex.equals("2")) {
                    mFrameUserInfo.setUserSex("女");
                }
            }
            //婚否
            if (null != objData.getString("maritalStatus")) {
                String maritalStatus = objData.getString("maritalStatus");
                if (maritalStatus.equals("1")) {
                    mFrameUserInfo.setUserMarry("未婚");
                } else if (maritalStatus.equals("2")) {
                    mFrameUserInfo.setUserMarry("已婚");
                }
            }
            //业务编码
            if (null != objData.getString("fcustid")) {
                String fcustid = objData.getString("fcustid");
                mFrameUserInfo.setFcustid(fcustid);
            }
            //绑定金融账号的姓名
            if (null != objData.getString("f_name")) {
                String f_name = objData.getString("f_name");
                mFrameUserInfo.setF_name(f_name);
            } else {
                mFrameUserInfo.setF_name("");
            }
            //绑定金融账号的手机号
            if (null != objData.getString("f_phone")) {
                String f_phone = objData.getString("f_phone");
                mFrameUserInfo.setF_phone(f_phone);
            } else {
                mFrameUserInfo.setF_phone("");
            }
            //绑定金融账号的身份证号
            if (null != objData.getString("f_cardId")) {
                String f_cardId = objData.getString("f_cardId");
                mFrameUserInfo.setF_cardId(f_cardId);
            } else {
                mFrameUserInfo.setF_cardId("");
            }

            FrameEtongApplication.getApplication().setUserInfo(mFrameUserInfo);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
        for (int i = 0; i < mRadioButtons.length; i++) {
            if (mRadioButtons[i].isChecked()) {
                switchFragment(i);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (myReceiver.isInitialStickyBroadcast()) {
            try {
                unregisterReceiver();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.main_bottom_find://发现
                switchFragment(0);
                select = 0;
                break;
            case R.id.main_bottom_news: // 资讯
                switchFragment(1);
                select = 1;
                break;
            case R.id.main_bottom_more:// 更多
                switchFragment(2);
                MoreMainFragment.isOnResume = false;
                select = 2;
                break;
            case R.id.main_bottom_mine:// 我的
//                if (login()) {
                switchFragment(3);
//                } else {
//                    Intent i = new Intent(MainContentActivity.this, MeLoginActivity.class);
//                    startActivity(i);
//                }
                select = 3;
                break;
            default:
                break;
        }
    }

    /**
     * 判断用户是否登录
     *
     * @return
     */
    private boolean login() {
//        if(MainEtongApplication.getApplication().getUserInfo() != null) {
//            return true;
//        } else {
//            return false;
//        }

        if (mFrameEtongApplication.getUserInfo() != null) {
            return true;
        } else {
            return false;
        }

    }

    private void switchFragment(int index) {
        if (index < mFragments.length) {
            int length = 4;
            FragmentManager manager = getSupportFragmentManager();

            // 1.开启事务
            FragmentTransaction transaction = manager.beginTransaction();
            if (mFragments[index] == null) {
                mFragments[0] = new FindMainFragment();
                mFragments[1] = new MessageMainFragment();
                mFragments[2] = new MoreMainFragment();
                mFragments[3] = new MeMainFragment();
                for (int i = 0; i < length; i++) {
                    transaction.add(R.id.main_content_home, mFragments[i]);
//                    if (i==3) {
//                        transaction.replace(R.id.main_content_home, mFragments[i]);
//                    }
                }
            }
            hideFragments(transaction);
            transaction.show(mFragments[index]);
            // 提交事务
            transaction.commitAllowingStateLoss();

//            mEventBus.post(0, "Refresh");
            select = index;
        }
    }

    private void hideFragments(FragmentTransaction transaction) {
        for (int i = 0; i < mFragments.length; i++) {
            if (mFragments[i] != null)
                transaction.hide(mFragments[i]);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                toastMsg("再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
//				finish();
                //ActivityStackManager.create().AppExit(this);
                ActivityStackManager.create().finishAllActivity();
                // System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //注册广播
    private void registerReceiver() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        myReceiver = new NetBroadcastReceiver();
        registerReceiver(myReceiver, filter);
    }

    //注销广播
    private void unregisterReceiver() {
        unregisterReceiver(myReceiver);
    }

    @Override
    public void onNetChange() {
        if (NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE) {
            L.d("-------------->", "没网");
        } else {
            mEventBus.post("NetWorkChanged", "NetWorkChanged");
        }
    }

}
