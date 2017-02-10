package com.etong.android.jxappdata.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.mobstat.StatService;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.FrameUserInfo;
import com.etong.android.frame.user.FrameUserProvider;
import com.etong.android.frame.utils.InputMethodUtil;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappdata.DataContentActivity;
import com.etong.android.jxappdata.R;
import com.etong.android.jxappdata.WelcomeActivity;
import com.etong.android.jxappdata.common.BaseActivity;
import com.etong.android.jxappdata.common.CommonEvent;

import org.simple.eventbus.Subscriber;

//import com.baidu.mobstat.StatService;

/***
 * 登录页面
 *
 * @author Administrator
 */
public class LoginActivity extends BaseActivity {

    private TitleBar mTitleBar;
    private FrameUserProvider mUserProvider;
    private EditText mPhoneView;
    private EditText mPasswdView;
    private Button mVcodeView;
    private Button mLoginView;
    private TimeCounter mTimeCounter;
    private TimeCounter2 mTimeCounter2;
    private FrameUserInfo mUserInfo = new FrameUserInfo();

    private static final String PT_ERROR_VERIFY_WRONG = "PT_ERROR_VERIFY_WRONG";
    private static final String PT_ERROR_VERIFY_OVERTIME = "PT_ERROR_VERIFY_OVERTIME";
    private static final String PT_ERROR_LOGON_OVERTIME = "PT_ERROR_LOGON_OVERTIME";

    private static final String SEND_SMS_SUCCESS = "PT_ERROR_SUCCESS";// 验证码发送成功
    private static final String SEND_SMS_ERROR = "PT_ERROR_SMS_SEND";// 验证码发送失败
    private static final String SEND_SMS_REDUPLICATED = "PT_ERROR_SMS_REDUPLICATED";// 验证码重复发送
    private HttpPublisher mHttpPublisher;

    // private boolean isFirstLogin = true;// 第一次登陆的标记

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        setContentView(R.layout.activity_login);
        this.myTitle = "登录";
        mTitleBar = new TitleBar(this);
        mTitleBar.showBackButton(false);
        mTitleBar.showNextButton(false);
//		mTitleBar.setTitle("登录");
        mUserProvider = FrameUserProvider.getInstance();
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(this);
        mUserProvider.initialize(mHttpPublisher);
        initView();

    }

    protected void initView() {
        mPhoneView = findViewById(R.id.login_phone, EditText.class);
        mPasswdView = findViewById(R.id.login_passwd, EditText.class);
        mVcodeView = findViewById(R.id.login_vcode, Button.class);
        mLoginView = findViewById(R.id.login_submit, Button.class);
        mVcodeView.setClickable(false);
        mVcodeView.setEnabled(false);
        mLoginView.setClickable(false);
        mLoginView.setEnabled(false);


        addClickListener(R.id.login_vcode);
        addClickListener(R.id.login_submit);

        mPhoneView.addTextChangedListener(new TextWatcher() {

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
                text = mPhoneView.getText().toString();
                if (text.length() == 11) {
                    mVcodeView.setClickable(true);
                    mVcodeView.setEnabled(true);
                } else {
                    mVcodeView.setClickable(false);
                    mVcodeView.setEnabled(false);
                }
            }
        });

        mPasswdView.addTextChangedListener(new TextWatcher() {

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
                text = mPasswdView.getText().toString();
                if (text.length() >= 6) {
                    mLoginView.setClickable(true);
                    mLoginView.setEnabled(true);
                } else {
                    mLoginView.setClickable(false);
                    mLoginView.setEnabled(false);
                }
            }
        });

        mTimeCounter = new TimeCounter(60000, 1000);
        mTimeCounter.setClickable(mVcodeView);

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
                    R.color.button_code_color));
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
                    R.color.code_number_color));
            clickable.setTextSize(15);

        }

        public void setClickable(Button clickable) {
            this.clickable = clickable;
        }
    }

    @Override
    protected void onClick(View view) {
//		switch (view.getId()) {
//		case R.id.login_vcode:
//			sendVerify();// 发送验证码
//			//百度统计按钮事件
//			StatService.onEvent(LoginActivity.this, "sendVerify", "发送验证码", 1);
//
//			break;
//		case R.id.login_submit:
//			 login();//登录
//			 StatService.onEvent(LoginActivity.this, "login", "登录", 1);
//
//
////			mUserInfo.setPhone(mPhoneView.getText().toString());
////			EtongApplication.getApplication().setUserInfo(mUserInfo);
////			addGuideImage();
////			// go2MainPage();
////			InputMethodUtil.hiddenInputMethod(getApplicationContext(),
////					mLoginView);// 关闭软键盘
//			break;
//		default:
//			break;
//		}

        if (R.id.login_vcode == view.getId()) {
            sendVerify();// 发送验证码
            //百度统计按钮事件
            StatService.onEvent(LoginActivity.this, "sendVerify", "发送验证码", 1);
        } else if (R.id.login_submit == view.getId()) {
            mUserInfo.setUserPhone(mPhoneView.getText().toString());
            FrameEtongApplication.getApplication().setUserInfo(mUserInfo);
            addGuideImage();
            // go2MainPage();
            InputMethodUtil.hiddenInputMethod(getApplicationContext(),
                    mLoginView);// 关闭软键盘
        }

    }

    protected boolean checkPhone() {
        mUserInfo.setUserPhone(mPhoneView.getText().toString());
        if (null == mUserInfo.getUserPhone() || mUserInfo.getUserPhone().equals("")) {
            toastMsg("手机号码不能为空");
            return false;
        }

        if (mUserInfo.getUserPhone().length() != 11) {
            toastMsg("手机号码无效");
            return false;
        }

        return true;
    }

    protected boolean checkPasswd() {
        mUserInfo.setPasswd(mPasswdView.getText().toString());
        if (null == mUserInfo.getPasswd() || mUserInfo.getPasswd().equals("")) {
            toastMsg("验证码不能为空");
            return false;
        }
        return true;
    }

    protected void sendVerify() {
        if (checkPhone()) {
            mTimeCounter.start();
            mUserProvider.sendVerify(mUserInfo.getUserPhone());
        }
    }

    @Subscriber(tag = CommonEvent.SEND_VERIFY)
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
            mPasswdView.requestFocus();// 获取焦点
        } else {
            toastMsg(message);
        }

    }

    protected void login() {
        loadStart("登录中...", 0);
        String phone = mPhoneView.getText().toString();
        String passwd = mPasswdView.getText().toString();

        if (checkPhone() && checkPasswd()) {
            mUserInfo.setUserPhone(phone);
            mUserInfo.setPasswd(passwd);
            mUserProvider.login(mUserInfo);
        } else {
            loadFinish();
        }
    }

    @Subscriber(tag = CommonEvent.LOGIN)
    public void onLoginFinish(HttpMethod method) {
        // 登录成功关闭当前activity
        loadFinish();
        String ptError = method.data().getString("errno");

        String message = method.data().getString("msg");

        if (null != ptError && UserProvider.USER_SUCCEED.equals(ptError)) {

            JSONObject obj = method.data().getJSONObject("data");
            String token = obj.getString("token");
//			String userId = obj.getString("userid");
//			String userPhone = obj.getString("phone");
            JSONObject object = obj.getJSONObject("object");
            String userId = object.getString("userId");
            String userPhone = obj.getString("userPhone");
            JSONArray authority = obj.getJSONArray("authority");

//			mUserInfo.setPhone(mPhoneView.getText().toString());
            mUserInfo.setUserPhone(userPhone);
            mUserInfo.setToken(token);
            mUserInfo.setUserId(userId);
            mUserInfo.setAuthority(authority);
            FrameEtongApplication.getApplication().setUserInfo(mUserInfo);

            Intent intent = new Intent(LoginActivity.this,
                    WelcomeActivity.class);
            startActivity(intent);
            finish();

            InputMethodUtil.hiddenInputMethod(this,
                    mLoginView);// 关闭软键盘
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
        } else if (null != message) {
            toastMsg(message);
        }


    }

    /**
     * @return void 返回类型
     * @Title : addGuideImage
     * @Description : 添加引导图片
     * @params 设定文件
     */
    private void addGuideImage() {
        View view = getWindow().getDecorView().findViewById(R.id.ll_login);// 查找通过setContentView上的根布局
        if (view == null)
            return;
//		if (FrameEtongApplication.getApplication().activityIsGuided(this,
//				mUserInfo.phone)) {
//			// 引导过了
//			go2MainPage();
//			return;
//		}
        ViewParent viewParent = view.getParent();
        if (viewParent instanceof FrameLayout) {
            final FrameLayout frameLayout = (FrameLayout) viewParent;

            // final ImageView guideImage = new ImageView(this);
            // @SuppressWarnings("deprecation")
            // FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
            // ViewGroup.LayoutParams.FILL_PARENT,
            // ViewGroup.LayoutParams.FILL_PARENT);
            // guideImage.setLayoutParams(params);
            // guideImage.setScaleType(ScaleType.FIT_XY);
            // guideImage.setImageResource(R.drawable.welcome);
            LayoutInflater inflater = LayoutInflater.from(this);
            View guideImage = inflater.inflate(R.layout.activity_welcome, null);
            frameLayout.addView(guideImage);// 添加引导图片
            View btn = guideImage.findViewById(R.id.bt_cancel);
            btn.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    mTimeCounter2.cancel();
                    go2MainPage();
                }
            });
            guideImage.setOnClickListener(null);
            // EtongApplication.getApplication().setIsGuided(this,
            // mUserInfo.phone);
            mTimeCounter2 = new TimeCounter2(3000, 1000);
            mTimeCounter2.start();
        }
    }

    public void go2MainPage() {
        Intent intent = new Intent(LoginActivity.this,
                DataContentActivity.class);
        startActivity(intent);
        finish();
    }

    /* 定义一个倒计时的内部类 */
    class TimeCounter2 extends CountDownTimer {
        public TimeCounter2(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            System.out.println("3s倒计时结束");
            go2MainPage();
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
        }

        public void setClickable(Button clickable) {
        }
    }

}
