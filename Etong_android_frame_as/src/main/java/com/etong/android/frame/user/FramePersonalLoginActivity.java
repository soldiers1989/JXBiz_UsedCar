package com.etong.android.frame.user;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.R;
import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.utils.InputMethodUtil;
import com.etong.android.frame.widget.TitleBar;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/30 0030.
 */
public class FramePersonalLoginActivity extends BaseSubscriberActivity {

    private TitleBar mTitleBar;
    private EditText loginPhone;
    private EditText loginCode;
    private LinearLayout loginFailedLayout;
    private Button loginCodeBtn;
    private Button loginBtn;
    private TimeCounter mTimeCounter;

    private static final String PERSON_PIC_HEAD_PUT = "person pic head put";
    private String mHeadPic;
    private ImageProvider mImageProvider;
    private FrameUserProvider mFrameUserProvider;

    private FrameUserInfo mFrameUserInfo = new FrameUserInfo();

    private static final String PT_ERROR_VERIFY_WRONG = "PT_ERROR_VERIFY_WRONG";
    private static final String PT_ERROR_VERIFY_OVERTIME = "PT_ERROR_VERIFY_OVERTIME";
    private static final String PT_ERROR_LOGON_OVERTIME = "PT_ERROR_LOGON_OVERTIME";

    private static final String SEND_SMS_SUCCESS = "PT_ERROR_SUCCESS";// 验证码发送成功
    private static final String SEND_SMS_ERROR = "PT_ERROR_SMS_SEND";// 验证码发送失败
    private static final String SEND_SMS_REDUPLICATED = "PT_ERROR_SMS_REDUPLICATED";// 验证码重复发送


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
    private Bitmap imageBitmap;
    private LinearLayout mLoginLayout;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.frame_activity_my_login);

        mTitleBar = new TitleBar(this);
        mTitleBar.setTitle("登录");
        mTitleBar.showBottomLin(false);

        mFrameUserProvider = FrameUserProvider.getInstance();
        mFrameUserProvider.initialize(HttpPublisher.getInstance());
        mImageProvider = ImageProvider.getInstance();
        mImageProvider.initialize(this);

        initView();
    }

    private void initView() {

        mLoginLayout = findViewById(R.id.frame_ll_login, LinearLayout.class);
        loginPhone = findViewById(R.id.frame_edt_put_tel, EditText.class);
        loginCode = findViewById(R.id.frame_edt_put_verification_code, EditText.class);
        loginCodeBtn = findViewById(R.id.frame_btn_verification_code, Button.class);
        loginBtn = findViewById(R.id.frame_btn_put_login, Button.class);

        loginFailedLayout = findViewById(R.id.frame_layout_login_failed, LinearLayout.class);

        loginCodeBtn.setClickable(false);
        loginCodeBtn.setEnabled(false);
        loginBtn.setClickable(false);
        loginBtn.setEnabled(false);

        addClickListener(R.id.frame_btn_put_login);
        addClickListener(R.id.frame_txt_login_again);
        addClickListener(R.id.frame_txt_giveup_login);

        addClickListener(loginCodeBtn);


        loginPhone.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                String text = "";
                text = loginPhone.getText().toString();
                if (text.length() == 11) {
                    loginCodeBtn.setClickable(true);
                    loginCodeBtn.setEnabled(true);
                } else {
                    loginCodeBtn.setClickable(false);
                    loginCodeBtn.setEnabled(false);
                }
            }
        });

        loginCode.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                String text = "";
                text = loginCode.getText().toString();
                if (text.length() >= 6) {
                    loginBtn.setClickable(true);
                    loginBtn.setEnabled(true);
                } else {
                    loginBtn.setClickable(false);
                    loginBtn.setEnabled(false);
                }
            }
        });

        mTimeCounter = new TimeCounter(60000, 1000);
        mTimeCounter.setClickable(loginCodeBtn);
    }


    @Override
    protected void onClick(View v) {
        if (v.getId() == R.id.frame_btn_put_login) {//登录
            login();//登录
        } else if (v.getId() == R.id.frame_btn_verification_code) {//获取验证码
            String textPhone = loginPhone.getText().toString();
            if (textPhone.matches(CM_NUM) || textPhone.matches(CT_NUM) || textPhone.matches(CU_NUM)) {
                sendVerify();// 发送验证码
            } else {
                toastMsg("请输入正确的手机号！");
            }
        } else if (v.getId() == R.id.frame_txt_login_again) {//继续登录
            showLoginView();
            toastMsg("继续登录");
        } else if (v.getId() == R.id.frame_txt_giveup_login) {//到处逛逛，稍后再说
            toastMsg("到处逛逛，稍后再说");
            finish();
        }
    }

    private void showLoginView() {
        mLoginLayout.setVisibility(View.VISIBLE);
        loginFailedLayout.setVisibility(View.GONE);
    }

    private void showLoginFailedView() {
        mLoginLayout.setVisibility(View.GONE);
        loginFailedLayout.setVisibility(View.VISIBLE);
    }

    /* 定义一个倒计时的内部类 */
    class TimeCounter extends CountDownTimer {
        private Button clickable = null;

        public TimeCounter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            clickable.setText("重新发送");
            clickable.setTextColor(getResources().getColor(
                    R.color.frame_button_code_color));
            clickable.setTextSize(14);
            clickable.setClickable(true);
            clickable.setEnabled(true);
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            clickable.setClickable(false);
            clickable.setEnabled(false);
            clickable.setText(millisUntilFinished / 1000 + "秒");
            clickable.setTextColor(getResources().getColor(
                    R.color.frame_code_number_color));
            clickable.setTextSize(15);

        }

        public void setClickable(Button clickable) {
            this.clickable = clickable;
        }
    }

    protected boolean checkPhone() {
        mFrameUserInfo.setUserPhone(loginPhone.getText().toString());
        if (null == mFrameUserInfo.getUserPhone() || mFrameUserInfo.getUserPhone().equals("")) {
            toastMsg("手机号码不能为空");
            return false;
        }

        if (mFrameUserInfo.getUserPhone().length() != 11) {
            toastMsg("手机号码无效");
            return false;
        }

        return true;
    }

    protected boolean checkPasswd() {
        mFrameUserInfo.setPasswd(loginCode.getText().toString());
        if (null == mFrameUserInfo.getPasswd() || mFrameUserInfo.getPasswd().equals("")) {
            toastMsg("验证码不能为空");
            return false;
        }
        return true;
    }

    protected void sendVerify() {
        if (checkPhone()) {
            mTimeCounter.start();
            mFrameUserProvider.sendVerify(mFrameUserInfo.getUserPhone());
        }
    }

    @Subscriber(tag = FrameCommonEvent.SEND_VERIFY)
    protected void sendVerifyFinish(HttpMethod method) {
        String errno = method.data().getString("errno");
        String flag = method.data().getString("flag");
        String message = method.data().getString("msg");
        /*if (!SEND_SMS_SUCCESS.equals(errno)) {
            toastMsg("验证码发送失败,请稍后重试!", errno);
			return;
		}
		toastMsg("验证码发送成功");
		mPasswdView.requestFocus();// 获取焦点
*/
        if (flag.equals("0")) {
            toastMsg("验证码发送成功");
            loginCode.requestFocus();// 获取焦点
        } else {
//            toastMsg(message);
            toastMsg("验证码发送失败");
        }

    }

    protected void login() {
        loadStart("登录中...", 0);
        String phone = loginPhone.getText().toString();
        String passwd = loginCode.getText().toString();

        if (checkPhone() && checkPasswd()) {
            mFrameUserInfo.setUserPhone(phone);
            mFrameUserInfo.setPasswd(passwd);
//            if(null!=mHeadPic){
//                mFrameUserInfo.setAvatar(mHeadPic);
//            }
            mFrameUserProvider.login(mFrameUserInfo);
        } else {
            loadFinish();
        }
    }

    @Subscriber(tag = FrameCommonEvent.LOGIN)
    public void onLoginFinish(HttpMethod method) {
        // 登录成功关闭当前activity
        loadFinish();


     /*   String errCode = method.data().getString("errCode");
        if (!TextUtils.isEmpty(errCode) && "4353".equals(errCode)) {
            showLoginFailedView();
            return;
        }
*/

        String ptError = method.data().getString("errno");

        String message = method.data().getString("msg");

        if (null != ptError && FrameUserProvider.USER_SUCCEED.equals(ptError)) {

            JSONObject obj = method.data().getJSONObject("data");
            String token = obj.getString("token");
            int platformID = obj.getInteger("platformID");

            if (null != obj.getJSONArray("myCars")) {
                JSONArray mycarArray = obj.getJSONArray("myCars");
                List<FrameUserInfo.Frame_MyCarItemBean> myCars = new ArrayList<FrameUserInfo.Frame_MyCarItemBean>();
                for (int i = 0; i < mycarArray.size(); i++) {
                    FrameUserInfo.Frame_MyCarItemBean mFrame_MyCarItemBean = JSON.toJavaObject(mycarArray.getJSONObject(i), FrameUserInfo.Frame_MyCarItemBean.class);
                    myCars.add(mFrame_MyCarItemBean);
                }
                mFrameUserInfo.setMyCars(myCars);
            }

            if (null != obj.getString("userID")) {
                String userId = obj.getString("userID");
                mFrameUserInfo.setUserId(userId);
            }


            if (null != obj.getString("roleID")) {
                String roleID = obj.getString("roleID");
                mFrameUserInfo.setRoleID(roleID);
            }

            if (null != obj.getString("roleName")) {
                String roleName = obj.getString("roleName");
                mFrameUserInfo.setRoleName(roleName);
            }

            if (null != obj.getString("fcustid")) {
                String fcustid = obj.getString("fcustid");
                mFrameUserInfo.setFcustid(fcustid);
            }
            if (null != obj.getString("f_name")) {
                String f_name = obj.getString("f_name");
                mFrameUserInfo.setF_name(f_name);
            } else {
                mFrameUserInfo.setF_name("");
            }
            if (null != obj.getString("f_phone")) {
                String f_phone = obj.getString("f_phone");
                mFrameUserInfo.setF_phone(f_phone);
            } else {
                mFrameUserInfo.setF_phone("");
            }
            if (null != obj.getString("f_cardId")) {
                String f_cardId = obj.getString("f_cardId");
                mFrameUserInfo.setF_cardId(f_cardId);
            } else {
                mFrameUserInfo.setF_cardId("");
            }

            if (null != obj.getString("userName")) {
                String userName = obj.getString("userName");
                mFrameUserInfo.setUserName(userName);
            } else {
                mFrameUserInfo.setUserName("");
            }
            if (null != obj.getString("sex")) {
                String sex = obj.getString("sex");
                if (sex.equals("1")) {
                    mFrameUserInfo.setUserSex("男");
                } else if (sex.equals("2")) {
                    mFrameUserInfo.setUserSex("女");
                }
            }
            if (null != obj.getString("maritalStatus")) {
                String maritalStatus = obj.getString("maritalStatus");
                if (maritalStatus.equals("1")) {
                    mFrameUserInfo.setUserMarry("未婚");
                } else if (maritalStatus.equals("2")) {
                    mFrameUserInfo.setUserMarry("已婚");
                }
            }
            if (null != obj.getString("cardID")) {
                String cardID = obj.getString("cardID");
                mFrameUserInfo.setUserIdCard(cardID);
            } else {
                mFrameUserInfo.setUserIdCard("");
            }
            if (null != obj.getString("avatar")) {
                String avatar = obj.getString("avatar");
                mFrameUserInfo.setAvatar(avatar);
            }

            if (null != obj.getString("status")) {
                int status = obj.getInteger("status");
                mFrameUserInfo.setStatus(status);
            }

            String userPhone = obj.getString("userPhone");

            if (null != obj.getJSONArray("authority")) {
                JSONArray authority = obj.getJSONArray("authority");
                mFrameUserInfo.setAuthority(authority);
            }


//			mFrameUserInfo.setUserPhone(loginPhone.getText().toString());
            mFrameUserInfo.setUserPhone(userPhone);
            mFrameUserInfo.setPlatformID(platformID);
            mFrameUserInfo.setToken(token);

            //二手车收藏数据
            JSONArray collectOldCarList = obj.getJSONArray("f_collOldCarList");
            List<String> mCollectOldCarList = new ArrayList<>();
            if (null != collectOldCarList) {
                for (int i = 0; i < collectOldCarList.size(); i++) {
                    String collectDvid = (String) collectOldCarList.getJSONObject(i).get("f_dvid");
                    mCollectOldCarList.add(collectDvid);
                }
            }
            UsedAndNewCollectCar collectOldCarBean = new UsedAndNewCollectCar();
            collectOldCarBean.setCarList(mCollectOldCarList);
            collectOldCarBean.setChanged(true);
            FrameEtongApplication.getApplication().setUsedCarCollectCache(collectOldCarBean);

            //新车收藏数据
            JSONArray collectNewCarList = obj.getJSONArray("f_collNewCarList");
            List<String> mCollectNewCarList = new ArrayList<>();
            if (null != collectNewCarList) {
                for (int i = 0; i < collectNewCarList.size(); i++) {
                    String collectVid = (String) collectNewCarList.getJSONObject(i).get("f_vid");
                    mCollectNewCarList.add(collectVid);
                }
            }
            UsedAndNewCollectCar collectNewCarBean = new UsedAndNewCollectCar();
            collectNewCarBean.setCarList(mCollectNewCarList);
            collectNewCarBean.setChanged(true);
            FrameEtongApplication.getApplication().setNewCarCollectCache(collectNewCarBean);

            //本地
//            if (null != mHeadPic) {
//                mFrameUserInfo.setAvatar(mHeadPic);
//            }

            FrameEtongApplication.getApplication().setUserInfo(mFrameUserInfo);

            //  登录成功之后、设置极光推送的别名
            FrameEtongApplication.getApplication().setJPushAlias();

            toastMsg("登录成功！");
            finish();

            InputMethodUtil.hiddenInputMethod(this,
                    loginBtn);// 关闭软键盘
//			addGuideImage();

        } else if (PT_ERROR_VERIFY_WRONG.equals(ptError)) {
            toastMsg("验证码错误");
            loadFinish();
        } else if (PT_ERROR_VERIFY_OVERTIME.equals(ptError)) {
            toastMsg("验证超时");
            loadFinish();
        } else if (PT_ERROR_LOGON_OVERTIME.equals(ptError)) {
            toastMsg("登录超时");
            loadFinish();
            showLoginFailedView();
        } else if (null != message) {
//            toastMsg(message);
            showLoginFailedView();
        }
    }

}
