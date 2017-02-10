package com.etong.android.jxappcarfinancial.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.FramePersonalLoginActivity;
import com.etong.android.frame.user.FrameUserInfo;
import com.etong.android.frame.utils.ActivitySkipUtil;
import com.etong.android.frame.utils.ActivityStackManager;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappcarfinancial.CF_Provider;
import com.etong.android.jxappcarfinancial.R;

import org.simple.eventbus.Subscriber;

public class CF_FirstBindingInfoActivity extends BaseSubscriberActivity {

    /**
     * @desc (未绑定用户绑定信息界面)
     * @createtime 2016/11/21 0021-9:59
     * @author wukefan
     */
    /*
  ##################################################################################################
  ##                                        类中的变量                                            ##
  ##################################################################################################
*/

    /***
     * 移动号段正则表达式
     */
    private static final String CM_NUM = "^((13[4-9])|(147)|(15[0-2,7-9])|(178)|(18[2-4,7-8]))\\d{8}|(1705)\\d{7}$";
    /***
     * 联通号段正则表达式
     */
    private static final String CU_NUM = "^((13[0-2])|(145)|(15[5-6])|(176)|(18[5,6]))\\d{8}|(1709)\\d{7}$";
    /***
     * 电信号段正则表达式
     */
    private static final String CT_NUM = "^((133)|(153)|(177)|(18[0,1,9]))\\d{8}|(1700)\\d{7}$";

    private TitleBar mTltleBar;
    private String mTitle;              //标题栏名称
    private EditText mEdtName;          //姓名
    private EditText mEdtPhone;         //手机号码
    private EditText mEdtIdCard;        //身份证号码
    private int f_institutId;           //金融机构id
    private int f_ordertype;            //申请类型
    private Button mSaveBtn;            //保存按钮
    private HttpPublisher mHttpPublisher;
    private CF_Provider mProvider;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.cf_activity_first_binding_info);

        initView();
        initData();
    }


/*
  ##################################################################################################
  ##                                     初始化View的方法                                         ##
  ##################################################################################################
*/

    /**
     * 请使用快捷模板生成注释
     */
    private void initView() {
        Intent intent = getIntent();
        f_institutId = intent.getIntExtra("f_institutId", -1);//金融机构id
        f_ordertype = intent.getIntExtra("f_ordertype", -1);//申请类型

        mTitle = "绑定金融账号";
        mTltleBar = new TitleBar(this);
        mTltleBar.setmTitleBarBackground("#252e3d");
        mTltleBar.setTitleTextColor("#ffffff");
        mTltleBar.setTitle(mTitle);

        mEdtName = findViewById(R.id.cf_edt_input_name, EditText.class);                    //姓名
        mEdtPhone = findViewById(R.id.cf_edt_input_tel, EditText.class);                    //手机号码
        mEdtIdCard = findViewById(R.id.cf_edt_input_idcard, EditText.class);                //身份证号码
        mSaveBtn = findViewById(R.id.cf_btn_input_save, Button.class);                      //保存按钮
        //去掉附加信息栏
        findViewById(R.id.cf_edt_input_additional, EditText.class).setVisibility(View.GONE);
        //显示身份证号码必填
        findViewById(R.id.cf_txt_input_mark, TextView.class).setVisibility(View.VISIBLE);

        addClickListener(R.id.cf_btn_input_save);//保存按钮
        addClickListener(R.id.cf_btn_input_cancel);//取消按钮

        initUserInfo();
    }

/*
  ##################################################################################################
  ##                                     初始化数据的方法                                         ##
  ##################################################################################################
*/

    /**
     * 请使用快捷模板生成注释
     */
    private void initData() {
        //初始化请求
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(this);
        mProvider = CF_Provider.getInstance();
        mProvider.initialize(mHttpPublisher);
    }

/*
  ##################################################################################################
  ##                                     点击事件的处理                                           ##
  ##################################################################################################
*/

    /**
     * 控件的点击事件
     */
    @Override
    protected void onClick(View view) {

        if (view.getId() == R.id.cf_btn_input_save) {//保存
            onSave();
        } else if (view.getId() == R.id.cf_btn_input_cancel) {//取消
            finish();
        }
    }


/*
  ##################################################################################################
  ##                              使用的逻辑方法，以及对外公开的方法                              ##
  ##                                      请求数据、获取数据                                      ##
  ##################################################################################################
*/

    /**
     * @desc (绑定金融账号回调)
     * @createtime 2016/11/22 0022-11:20
     * @author wukefan
     */
    @Subscriber(tag = FrameHttpTag.BIND_FINANCIAL_AGENT)
    public void bindFinancialAgentResult(HttpMethod method) {
        mSaveBtn.setClickable(true);
        String errno = method.data().getString("errno");
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");

        if (!TextUtils.isEmpty(flag) && flag.equals("0")) {

            JSONObject data = method.data().getJSONObject("data");
            String fcustid = (String) data.get("fcustid");
            String f_name = (String) data.get("f_name");
            String f_phone = (String) data.get("f_phone");
            String f_cardId = (String) data.get("f_cardId");
            FrameUserInfo mUser = FrameEtongApplication.getApplication().getUserInfo();
            mUser.setFcustid(fcustid);
            mUser.setF_name(f_name);
            mUser.setF_phone(f_phone);
            mUser.setF_cardId(f_cardId);
            FrameEtongApplication.getApplication().setUserInfo(mUser);

            FrameUserInfo tempUser = new FrameUserInfo();
            tempUser.setUserPhone(mEdtPhone.getText().toString());
            tempUser.setUserName(mEdtName.getText().toString());
            tempUser.setUserIdCard(mEdtIdCard.getText().toString());
            mEventBus.post(tempUser, "Bind Info");
            toastMsg("保存成功~");
            ActivityStackManager.create().finishActivity(CF_NoRegisterActivity.class);
            finish();
        } else if (flag.equals(HttpPublisher.NETWORK_ERROR)) {
            toastMsg("保存失败，请检查网络！");
        } else {
            toastMsg(msg);
        }
    }

    /**
     * @desc (保存操作)
     * @createtime 2016/11/17 0017-18:54
     * @author wukefan
     */
    protected void onSave() {
        String textName = mEdtName.getText().toString();
        if (TextUtils.isEmpty(textName.trim())) {
            toastMsg("请输入姓名！");
            return;
        }

        String textPhone = mEdtPhone.getText().toString();
        if (TextUtils.isEmpty(textPhone)) {
            toastMsg("请输入手机号码！");
            return;
        }

        if (!(textPhone.matches(CM_NUM) || textPhone.matches(CT_NUM) || textPhone.matches(CU_NUM))) {
            toastMsg("请输入正确的手机号！");
            return;
        }

        String idCard = mEdtIdCard.getText().toString();
        if (!TextUtils.isEmpty(idCard)) {
            if (idCard.length() < 18) {
                toastMsg("请输入完整身份证号");
                return;
            } else {
                String regex = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$|^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X|x)$";
                if (!idCard.matches(regex)) {
                    toastMsg("请输入正确的身份证号");
                    return;
                }
            }
        } else {
            toastMsg("请输入身份证号！");
            return;
        }

        mSaveBtn.setClickable(false);
        if (FrameEtongApplication.getApplication().isLogin()) {
            mProvider.bindFinancialAgent(textPhone, textName, idCard, f_institutId, f_ordertype);
        } else {
            toastMsg("您还未登录，请先登录~");
            ActivitySkipUtil.skipActivity(CF_FirstBindingInfoActivity.this, FramePersonalLoginActivity.class);
            mSaveBtn.setClickable(true);
        }
    }


    /**
     * @desc (当用户登录时自动带入用户信息)
     * @createtime 2016/11/30 0025-15:54
     * @author wukefan
     */
    protected void initUserInfo() {
        if (FrameEtongApplication.getApplication().isLogin()) {
            FrameUserInfo mUser = FrameEtongApplication.getApplication().getUserInfo();
            if (null != mUser.getF_phone() && !TextUtils.isEmpty(mUser.getF_phone())) {
                mEdtPhone.setText(mUser.getF_phone());//手机号码
                mEdtName.setText(mUser.getF_name());//姓名
                mEdtIdCard.setText(mUser.getF_cardId());//身份证号码
                return;
            }
            mEdtPhone.setText(mUser.getUserPhone());//手机号码
            if (null != mUser && !TextUtils.isEmpty(mUser.getUserName())) {
                mEdtName.setText(mUser.getUserName());//姓名
                mEdtName.requestFocus();
            }
            if (null != mUser && !TextUtils.isEmpty(mUser.getUserIdCard())) {
                mEdtIdCard.setText(mUser.getUserIdCard());//身份证号码
            }
        }
    }
/*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/

}
