package com.etong.android.jxappcarfinancial.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.FramePersonalLoginActivity;
import com.etong.android.frame.user.FrameUserInfo;
import com.etong.android.frame.utils.ActivitySkipUtil;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappcarfinancial.CF_Provider;
import com.etong.android.jxappcarfinancial.Interface.CF_InterfaceOrderType;
import com.etong.android.jxappcarfinancial.R;
import com.etong.android.jxappcarfinancial.utils.CF_CancelConformDialog;

import org.simple.eventbus.Subscriber;

/**
 * @desc 申请界面(车辆撤销申请、车贷申请、车租赁申请、畅通钱包申请)
 * @createtime 2016/11/17 0017--18:52
 * @Created by wukefan.
 */
public class CF_ApplyForActivity extends BaseSubscriberActivity implements CF_InterfaceOrderType {

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

    /***
     * 判断显示哪个页面的flag
     * 3 - 车贷申请
     * 4 - 融资租赁申请
     * 5 - 畅通钱包申请
     * 6 - 车辆撤销申请
     */
    private int flagTag;

    private String mTitle;              //标题栏名称

    private TitleBar mTltleBar;
    private ImageView mBannerImg;       //banner图片
    private EditText mEdtName;          //姓名
    private EditText mEdtPhone;         //手机号码
    private EditText mEdtIdCard;        //身份证号码
    private EditText mEdtAdditional;    //附加信息
    private Button mSubmitBtn;          //提交按钮
    private View mDivider;              //粗分割线
    private CF_CancelConformDialog resultDialog;
    private HttpPublisher mHttpPublisher;
    private CF_Provider mProvider;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.cf_activity_apply_for);

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

        //初始化请求
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(this);
        mProvider = CF_Provider.getInstance();
        mProvider.initialize(mHttpPublisher);

        Intent intent = getIntent();
        flagTag = intent.getIntExtra("flag_tag", 3);        //判断显示哪个页面的flag
        mTitle = intent.getStringExtra("title");            //标题栏名称

        mTltleBar = new TitleBar(this);
        mTltleBar.setmTitleBarBackground("#252e3d");
        mTltleBar.setTitleTextColor("#ffffff");
        mTltleBar.setTitle(mTitle);
        mTltleBar.setNextButton("绑定");
        mTltleBar.setNextOnClickListener(new View.OnClickListener() {//绑定金融账号
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CF_ApplyForActivity.this, CF_NoRegisterActivity.class);
                i.putExtra("f_ordertype", flagTag - 3);
                startActivity(i);
            }
        });

        mDivider = findViewById(R.id.cf_divider_apply_for, View.class);                     //粗分割线
        mBannerImg = findViewById(R.id.cf_img_input_banner, ImageView.class);               //banner图片
        mEdtName = findViewById(R.id.cf_edt_input_name, EditText.class);                    //姓名
        mEdtPhone = findViewById(R.id.cf_edt_input_tel, EditText.class);                    //手机号码
        mEdtIdCard = findViewById(R.id.cf_edt_input_idcard, EditText.class);                //身份证号码
        mEdtAdditional = findViewById(R.id.cf_edt_input_additional, EditText.class);        //附加信息

        //提交按钮
        mSubmitBtn = findViewById(R.id.cf_btn_put_submit, Button.class);
        addClickListener(mSubmitBtn);

        ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) mBannerImg.getLayoutParams();
        lp.width = mWidth;
        //将高度设置为宽度的38 / 75
        lp.height = mWidth * 38 / 75;
        //根据屏幕大小按比例动态设置图片大小
        mBannerImg.setLayoutParams(lp);

        //默认显示图片不显示分割线
        mBannerImg.setVisibility(View.VISIBLE);
        mDivider.setVisibility(View.GONE);

        if (flagTag == VEHICLES_DRAW) {
            //车辆撤押申请设置不显示图片显示分割线
            mBannerImg.setVisibility(View.GONE);
            mDivider.setVisibility(View.VISIBLE);
        }

        initUserInfo();
        initResultDialog();
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
        if (view.getId() == R.id.cf_btn_put_submit) {//提交
            onSubmit();
        }
    }


/*
  ##################################################################################################
  ##                              使用的逻辑方法，以及对外公开的方法                              ##
  ##                                      请求数据、获取数据                                      ##
  ##################################################################################################
*/

    /**
     * @desc (车贷申请 融资租赁申请 畅通钱包申请 车辆撤押申请回调)
     * @createtime 2016/11/22 0022-11:20
     * @author wukefan
     */
    @Subscriber(tag = FrameHttpTag.ADD_CAR_ORDER_FOR_APP)
    protected void addCarPayOrderResult(HttpMethod method) {
        mSubmitBtn.setClickable(true);
        String errno = method.data().getString("errno");
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");

        if (!TextUtils.isEmpty(flag) && flag.equals("0")) {
            String orderNum = method.data().getString("data");
            resultDialog.show();
        } else if (flag.equals(HttpPublisher.NETWORK_ERROR)) {
            toastMsg("提交失败，请检查网络！");
        } else {
            toastMsg(msg);
        }
    }


    /**
     * @desc (提交操作)
     * @createtime 2016/11/17 0017-18:54
     * @author wukefan
     */
    protected void onSubmit() {
        //姓名
        String textName = mEdtName.getText().toString();
        if (TextUtils.isEmpty(textName.trim())) {
            toastMsg("请输入姓名！");
            return;
        }
        //手机号码
        String textPhone = mEdtPhone.getText().toString();
        if (TextUtils.isEmpty(textPhone)) {
            toastMsg("请输入手机号码！");
            return;
        }
        if (!(textPhone.matches(CM_NUM) || textPhone.matches(CT_NUM) || textPhone.matches(CU_NUM))) {
            toastMsg("请输入正确的手机号！");
            return;
        }
        //身份证号码
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
        }
        //备注
        String additional = mEdtAdditional.getText().toString();

        mSubmitBtn.setClickable(false);
        switch (flagTag) {
            //车辆撤押申请
            case VEHICLES_DRAW:
                mProvider.addCarDrawOrderForApp(textPhone, textName, idCard, flagTag, additional);
                break;
            //其他申请
            default:
                mProvider.addCarPayOrderForApp(textPhone, textName, idCard, flagTag, additional);
                break;
        }
    }

    /**
     * @desc (点击提交后的dialog)
     */
    protected void initResultDialog() {
        resultDialog = new CF_CancelConformDialog(this);
        resultDialog.setContent("亲，您已经成功提交" + mTitle + "咯，将会有客服与您联系，记得保持手机畅通哦！谢谢~");

        resultDialog.setConfirmButtonClickListener("查看订单记录", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FrameEtongApplication.getApplication().isLogin()) {
                    ActivitySkipUtil.skipActivity(CF_ApplyForActivity.this, CF_AllOrderRecordActivity.class);
                    resultDialog.dismiss();
                    finish();
                } else {
                    toastMsg("您还未登录，请先登录~");
                    ActivitySkipUtil.skipActivity(CF_ApplyForActivity.this, FramePersonalLoginActivity.class);
                }
            }
        });
        resultDialog.setCancleButtonClickListener("继续逛逛~", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultDialog.dismiss();
                finish();
            }
        });
    }

    /**
     * @desc (当用户登录时自动带入用户信息)
     * @createtime 2016/11/25 0025-10:54
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

    @Subscriber(tag = "Bind Info")
    private void setBindInfo(FrameUserInfo mUserInfo) {
        mEdtPhone.setText(mUserInfo.getUserPhone());
        mEdtName.setText(mUserInfo.getUserName());
        mEdtIdCard.setText(mUserInfo.getUserIdCard());
    }
/*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/

}
