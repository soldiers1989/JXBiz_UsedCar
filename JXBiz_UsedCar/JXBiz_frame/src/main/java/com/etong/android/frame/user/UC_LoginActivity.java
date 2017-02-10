package com.etong.android.frame.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.R;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.UC_HttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.utils.MD5Utils;
import com.etong.android.frame.widget.TitleBar;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 登录
 * Created by Administrator on 2016/10/11.
 */

public class UC_LoginActivity extends BaseSubscriberActivity {

    /**
     * 移动号段正则表达式
     */
    private static final String CM_NUM = "^((13[4-9])|(147)|(15[0-2,7-9])|(178)|(18[2-4,7-8]))\\d{8}|(1705)\\d{7}$";
    /**
     * 联通号段正则表达式
     */
    private static final String CU_NUM = "^((13[0-2])|(145)|(15[5-6])|(176)|(18[5,6]))\\d{8}|(1709)\\d{7}$";
    /**
     * 电信号段正则表达式
     */
    private static final String CT_NUM = "^((133)|(153)|(177)|(18[0,1,9]))\\d{8}|(1700)\\d{7}$";

    private static final String PHONE_REGISTER_EMPTY = "手机号码不能为空";
    private static final String PHONE_REGISTER_WRONG = "手机号码输入有误";
    private EditText mTelPhone;//手机号码输入框
    private EditText mVerCode;//验证码输入框
    private Button mGetVerCode;//获取验证码按钮
    private Button mBtnLogin;//登录按钮
    private TimeCount time;//倒计时类
    private TitleBar mTitleBar;
    private UC_UserProvider mProvider;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.uc_activity_login_content);

        mProvider = UC_UserProvider.getInstance();
        mProvider.initalize(HttpPublisher.getInstance());
        initView();
    }

    private void initView() {

        mTitleBar = new TitleBar(this);
        mTitleBar.setTitle("登录");

        Intent intent = getIntent();
        boolean isShowToast = intent.getBooleanExtra("isShowToast", false);

        if (isShowToast) {
            toastMsg("你还未登录，请先登录~");
        }

        mTelPhone = findViewById(R.id.uc_et_telphone, EditText.class);
        mVerCode = findViewById(R.id.uc_et_verification_code_input, EditText.class);
        mGetVerCode = findViewById(R.id.uc_et_verification_code_get, Button.class);
        mBtnLogin = findViewById(R.id.uc_btn_login, Button.class);
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象
        addClickListener(mGetVerCode);
        addClickListener(mBtnLogin);
        mBtnLogin.setEnabled(false);
        mBtnLogin.setClickable(false);

    }

    @Override
    protected void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.uc_et_verification_code_get) {
            String phone = mTelPhone.getText().toString().trim();
            if (checkPhoneInput(phone)) {
                mBtnLogin.setEnabled(true);
                mBtnLogin.setClickable(true);
                time.start();//开始计时
                Map<String, String> map = new HashMap<>();
                map.put("f_phone", phone);
                mProvider.getVerificationCode(map);
            }
        } else if (view.getId() == R.id.uc_btn_login) {

            if (TextUtils.isEmpty(mVerCode.getText().toString())) {
                toastMsg("请输入验证码~");
                return;
            }
            Map<String, String> m = new HashMap<>();
            m.put("f_phone", mTelPhone.getText().toString().trim());
            m.put("f_code", mVerCode.getText().toString().trim());
            m.put("f_org_id", "001");
            // 获取到唯一机器码
            String uniqueId = UC_FrameEtongApplication.getApplication().getUniqueId();
            if (!TextUtils.isEmpty(uniqueId)) {
                m.put("f_machinecode", uniqueId);
                String md5Key = MD5Utils.MD5(uniqueId);
                m.put("f_key", md5Key);
            }
            mProvider.login(m);
        }

    }


    //检测输入的手机号
    private boolean checkPhoneInput(String phone) {
        if (phone.isEmpty() || phone.equals("") || null == phone) {
            toastMsg(PHONE_REGISTER_EMPTY);
            return false;
        }
        if (!(phone.matches(CM_NUM) || phone.matches(CT_NUM) || phone.matches(CU_NUM)) || phone.length() != 11) {
            toastMsg(PHONE_REGISTER_WRONG);
            return false;
        }
        return true;
    }

    //获取验证码回调
    @Subscriber(tag = UC_HttpTag.GET_VER_CODE)
    public void getVerCode(HttpMethod method) {
        String data = method.data().getString("data");
        String msg = method.data().getString("msg");
        String status = method.data().getString("status");

        if ("true".equals(status)) {
            toastMsg("验证码发送成功");
        } else {
            toastMsg("验证码发送失败");
        }

    }

    //登录回调
    @Subscriber(tag = UC_HttpTag.LOGIN)
    public void login(HttpMethod method) {
        String data = method.data().getString("data");
        String msg = method.data().getString("msg");
        String status = method.data().getString("status");
        if (status.equals("true")) {
            JSONObject object = method.data().getJSONObject("data");
            UC_User user = JSON.toJavaObject(object, UC_User.class);
//            mProvider.saveUserInfo(object);

            //收藏数据
            JSONArray collectList = object.getJSONArray("collectList");
            List<String> mCollectList = new ArrayList<>();
            if (null != collectList) {
                for (int i = 0; i < collectList.size(); i++) {
                    int collectId = (int) collectList.getJSONObject(i).get("f_collectid");
                    mCollectList.add(collectId + "");
                }
            }
            UC_CollectOrScannedBean collectBean = new UC_CollectOrScannedBean();
            collectBean.setCarList(mCollectList);
            collectBean.setChanged(true);
            UC_FrameEtongApplication.getApplication().setCollectCache(collectBean);

            //浏览记录数据
            JSONArray historyList = object.getJSONArray("historyList");
            List<String> mHistoryList = new ArrayList<>();
            if (null != historyList) {
                for (int i = 0; i < historyList.size(); i++) {
                    String vehicleidId = (String) historyList.getJSONObject(i).get("f_vehicleid");
                    mHistoryList.add(vehicleidId);
                }
            }
            UC_CollectOrScannedBean historyBean = new UC_CollectOrScannedBean();
            historyBean.setCarList(mHistoryList);
            historyBean.setChanged(true);
            UC_FrameEtongApplication.getApplication().setHistoryCache(historyBean);

            UC_FrameEtongApplication.getApplication().setUserInfo(user);
//            ActivitySkipUtil.skipActivity(UC_LoginActivity.this, UC_HomePageFragment.class);
            toastMsg("登录成功~");
            finish();
        } else if (status.equals(HttpPublisher.NETWORK_ERROR)) {
            toastMsg("登录失败，请检查网络~");
        } else {
            toastMsg(msg);
        }

        // 在登录之后设置别名
        UC_FrameEtongApplication.getApplication().setJPushAlias();
    }

    //倒计时类
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            mGetVerCode.setText("重新验证");
            mGetVerCode.setEnabled(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            mGetVerCode.setEnabled(false);
            mGetVerCode.setText(millisUntilFinished / 1000 + "秒");
        }
    }
}
