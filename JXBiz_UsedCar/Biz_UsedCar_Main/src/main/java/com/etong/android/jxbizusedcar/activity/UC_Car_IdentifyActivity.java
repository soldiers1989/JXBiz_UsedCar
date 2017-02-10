package com.etong.android.jxbizusedcar.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.UC_HttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.user.UC_UserProvider;
import com.etong.android.frame.utils.ActivitySkipUtil;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.frame.widget.dialog.UC_ConformDialog;
import com.etong.android.jxappusedcar.utils.UC_AllCapTransformationMethod;
import com.etong.android.jxbizusedcar.R;
import com.etong.android.jxbizusedcar.utils.SerializMap;

import org.simple.eventbus.Subscriber;

import java.util.HashMap;
import java.util.Map;

public class UC_Car_IdentifyActivity extends BaseSubscriberActivity {
/*
  ##################################################################################################
  ##                                        类中的变量                                            ##
  ##################################################################################################
*/

    public static String IDENTIFY_DATA = "car identify data";           // 传送过去data的key值
    public static String IDENTIFY_VIN_CODE = "car identify vin code";   // vin码
    public static String IDENTIFY_CID_CODE = "car identify cid code";   // cid码，订单主键
    private TitleBar mTitleBar;
    private UC_ConformDialog conformDialog;
    private EditText mEdtChassis;
    private UC_UserProvider mProvider;
    private String vinStr;                  // 用户输入合法的VIN码能够查询之后的字符串
    private String cidStr;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.uc_activity_car_identify_content);

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

        mTitleBar = new TitleBar(this);
        mTitleBar.setTitle("车鉴定");
        mTitleBar.showNextButton(false);

        // 首页显示的图片
        ImageView ivBanner = findViewById(R.id.uc_iv_car_identify_banner_img, ImageView.class);
        int value = 0;
        Class<R.mipmap> cls = R.mipmap.class;
        try {
            value = cls.getDeclaredField("uc_sell_car_banner_iv").getInt(null);
        } catch (Exception e) {
        }
        ImageProvider.getInstance().loadImage(ivBanner, "drawable://" + value, R.mipmap.uc_image_loading_two);


        mEdtChassis = findViewById(R.id.uc_edt_identify_input, EditText.class);

        // 实例化接口请求类
        mProvider = UC_UserProvider.getInstance();
        mProvider.initalize(HttpPublisher.getInstance());

        initDialog();
    }


    private void initDialog() {
        conformDialog = new UC_ConformDialog(this, true);
        conformDialog.setTitle("本人声明");
        conformDialog.setContent("\t\t本人在使用车鉴定查询车辆历史记录之前已经获得车主(车辆所有人)授权或者同意，并且承诺不对任何第三方透露通过车鉴定所获得的任何信息。\n\t\t本人在此郑重声明：由于上述原因造成的不良后果，本人愿意承担一切法律责任及连带责任，与车鉴定无关(更多详情请见《车鉴定服务协议》)。点击确认按钮表示同意以上声明，不同意请关闭或退出查询。");
        conformDialog.setContentSize(13);
        conformDialog.setConfirmButtonClickListener("确定", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 跳转到支付宝支付界面
                Bundle mBundle = new Bundle();
                SerializMap map = new SerializMap();
                Map<String, String> dataMap = new HashMap<String, String>();
                if (!TextUtils.isEmpty(vinStr) && !TextUtils.isEmpty(cidStr)) {
                    dataMap.put(UC_Car_IdentifyActivity.IDENTIFY_VIN_CODE, vinStr.toUpperCase());     // 将vin字符串存入到Map中
                    dataMap.put(UC_Car_IdentifyActivity.IDENTIFY_CID_CODE, cidStr);     // 将cid字符串存入到Map中
                    map.setMap(dataMap);
                }
                mBundle.putSerializable(IDENTIFY_DATA, map);
                ActivitySkipUtil.skipActivity(UC_Car_IdentifyActivity.this, UC_PayForReport.class, mBundle);

                //车鉴定操作。。。。。
//                String mChassis = mEdtChassis.getText().toString();
//                toastMsg(mChassis.toUpperCase());
                conformDialog.dismiss();
            }
        });
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

        mEdtChassis.setTransformationMethod(new UC_AllCapTransformationMethod());
        addClickListener(R.id.uc_btn_identify_check);

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
        if (view.getId() == R.id.uc_btn_identify_check) {//查询
            if (TextUtils.isEmpty(mEdtChassis.getText().toString()) || mEdtChassis.getText().toString().length() != 17) {
                toastMsg("请输入正确车架号后再查询！");
            } else {
                // 开始转圈圈
                loadStart();
                // 获取到vin码之后请求数据
                vinStr = mEdtChassis.getText().toString();
                if (!TextUtils.isEmpty(vinStr)) {
                    mProvider.checkVinIsSupport(vinStr.toUpperCase());
                }
            }
        }
    }


/*
  ##################################################################################################
  ##                              使用的逻辑方法，以及对外公开的方法                              ##
  ##                                      请求数据、获取数据                                      ##
  ##################################################################################################
*/

    /**
     * @param
     * @return
     * @desc (获取到当前VIN)
     * @user sunyao
     * @createtime 2016/11/10 - 17:14
     */
    @Subscriber(tag = UC_HttpTag.CHECK_VIN_IS_SUPPORT)
    public void getVinIsValuable(HttpMethod mHttpMethod) {
        // 圈圈停止
        loadFinish();

        String msg = mHttpMethod.data().getString("msg");
        String status = mHttpMethod.data().getString("status");
        JSONObject data = mHttpMethod.data().getJSONObject("data");

        if ("true".equals(status)) {
            if (!conformDialog.isShowing()) {
                conformDialog.show();
            }
            cidStr = data.getString("f_cid");
        } else {
            toastMsg(msg);
        }
    }
/*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/

}
