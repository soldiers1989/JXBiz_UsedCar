package com.etong.android.jxappusedcar.content;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.permissions.PermissionsManager;
import com.etong.android.frame.permissions.PermissionsResultAction;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.UC_HttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberFragment;
import com.etong.android.frame.user.UC_FrameEtongApplication;
import com.etong.android.frame.user.UC_LoginActivity;
import com.etong.android.frame.user.UC_UserProvider;
import com.etong.android.frame.utils.DateUtils;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.frame.widget.dialog.UC_CancelConformDialog;
import com.etong.android.frame.widget.dialog.UC_ConformDialog;
import com.etong.android.frame.widget.three_slide_our.javabean.Et_VechileSeries;
import com.etong.android.frame.widget.three_slide_our.main_content.Et_ChooseCarType;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.utils.UC_SelectDateDialog;

import org.simple.eventbus.Subscriber;

import java.util.Calendar;


/**
 * 卖车
 * Created by Administrator on 2016/10/17.
 */

public class UC_SellCarFragment extends BaseSubscriberFragment {

    private TitleBar mTitleBar;
    private static int SELL_CAR_REQUEST_CODE = 101;
    private View view;

    private Integer catTypeId;                       // 车型的ID
    private String telPhone = "96512";               // 拨打的电话号码
    private Et_ChooseCarType getCarType;             // 选择品牌类
    private TextView telPhoneTxt;                    // 咨询电话
    private UC_ConformDialog conformDialog;          // 预约成功后确定的dialog
    private UC_CancelConformDialog mCallPhoneDialog; // 打电话的dialog
    private UC_SelectDateDialog mChangeBirthDialog;  // 选择时间的dialog
    private UC_UserProvider mUsedCarProvider;

    private TextView registTime;        // 注册时间
    private TextView selectBrand;       // 选择品牌
    private EditText mEdtMileAge;       // 行车里程
    private Button sellCarBtn;          // 预约卖车按钮
    private DrawerLayout sellCarDrawer;

    @Override
    protected View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.used_car_fragment_sell_car, container, false);

        mUsedCarProvider = UC_UserProvider.getInstance();
        mUsedCarProvider.initalize(HttpPublisher.getInstance());

        initView(view);
        return view;
    }

    protected void initView(View view) {
        mTitleBar = new TitleBar(view);
        mTitleBar.setTitle("我要卖车");
        mTitleBar.showNextButton(false);
        mTitleBar.showBackButton(false);

        // 加载卖车页面的Banner图
        ImageView ivBanner = findViewById(view, R.id.uc_iv_sell_car_banner, ImageView.class);
        int value = 0;
        Class<R.mipmap> cls = R.mipmap.class;
        try {
            value = cls.getDeclaredField("uc_sell_car_banner_iv").getInt(null);
        } catch (Exception e) {
        }
        ImageProvider.getInstance().loadImage(ivBanner, "drawable://" + value, R.mipmap.uc_image_loading_two);


        // 获取到侧滑界面，并设置不能侧滑打开
        sellCarDrawer = findViewById(view, R.id.uc_drawerlayout_sell_car, DrawerLayout.class);
        sellCarDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
        // 设置页面上的监听事件
        getCarType = new Et_ChooseCarType(getActivity(), getActivity(), sellCarDrawer, R.id.uc_content_menu_sell_car_frg);
        getCarType.setNeedChecked(false);
        // 选择品牌
        selectBrand = findViewById(view, R.id.used_car_tv_brand, TextView.class);
        addClickListener(selectBrand);
        // 上牌时间
        registTime = findViewById(view, R.id.used_car_tv_registration_time, TextView.class);
        addClickListener(registTime);
        // 咨询电话
        telPhoneTxt = findViewById(view, R.id.used_car_phone_number, TextView.class);
        telPhoneTxt.setText(telPhone);
        addClickListener(view, R.id.used_car_hotline);
        // 预约卖车按钮
        sellCarBtn = findViewById(view, R.id.used_car_btn_order_sellcars, Button.class);
        addClickListener(sellCarBtn);
        // 行车里程
        mEdtMileAge = findViewById(view, R.id.used_car_et_mileage, EditText.class);
        mEdtMileAge.addTextChangedListener(new TextWatcher() {
                                               @Override
                                               public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                               }

                                               @Override
                                               public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                               }

                                               @Override
                                               public void afterTextChanged(Editable editable) {
                                                   String temp = editable.toString();
                                                   if (temp.length() > 1 && temp.lastIndexOf(".") == temp.length() - 1) {
                                                       if (temp.substring(0, temp.length() - 1).contains(".")) {
                                                           editable.delete(temp.length() - 1, temp.length());
                                                           return;
                                                       }
                                                   }
                                                   //这部分是处理如果输入框内小数点后有俩位，那么舍弃最后一位赋值，光标移动到最后
                                                   if (temp.contains(".")) {
                                                       if (temp.length() - 1 - temp.indexOf(".") > 2) {
                                                           mEdtMileAge.setText(temp.subSequence(0,
                                                                   temp.indexOf(".") + 3));
                                                           mEdtMileAge.setSelection(temp.trim().length() - 1
                                                           );
                                                       }
                                                   }
                                                   //这部分是处理如果用户输入以.开头，在前面加上0
                                                   if (temp.trim().substring(0).equals(".")) {
                                                       mEdtMileAge.setText("0" + temp);
                                                       mEdtMileAge.setSelection(2);
                                                   }

                                                   //这里处理用户 多次输入.的处理 比如输入 1..6的形式，是不可以的
                                                   if (temp.startsWith("0")
                                                           && temp.trim().length() > 1) {
                                                       if (!temp.substring(1, 2).equals(".")) {
                                                           mEdtMileAge.setText(temp.subSequence(0, 1));
                                                           mEdtMileAge.setSelection(1);
                                                           return;
                                                       }
                                                   }
//                                                     int posDot = temp.indexOf(".");
//                                                     if (posDot > 0 && temp.length() - posDot - 1 > 2) {//小数点最多两位
//                                                         editable.delete(posDot + 3, posDot + 4);
//                                                     }
                                                   temp = mEdtMileAge.getText().toString();
                                                   if (!TextUtils.isEmpty(temp)) {//整数最多4位
                                                       Double result = Double.valueOf(temp);
                                                       if (result < 10000) {
                                                           return;
                                                       } else {
                                                           mEdtMileAge.setText("");
                                                       }
                                                   }
                                               }
                                           }

        );

        initDialog();
        setCallPhone();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.used_car_tv_registration_time) {//上牌时间
            // 修改为弹出的选择时间、下面注释了页面选择时间，方便修改
            if (null == mChangeBirthDialog) {
                selectTimeDialog();//初始化选择时间的dialog
            } else {
                if (!TextUtils.isEmpty(registTime.getText().toString())) {//上牌时间不为空时，设置选择时间的dialog默认显示为上牌时间
                    mChangeBirthDialog.setStringDate(registTime.getText().toString());
                } else {//上牌时间为空时，设置选择时间的dialog默认显示为当前时间
                    Calendar calendar = Calendar.getInstance();
                    mChangeBirthDialog.setDate(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DAY_OF_MONTH));
                }

                mChangeBirthDialog.show();
            }

//            Intent i = new Intent();
//            i.setClass(UC_SellCarFragment.this.getActivity(), UC_RegistTimeActivity.class);
//            i.putExtra(UC_RegistTimeActivity.SELECT_YEAR, ((TextView) registTime).getText().toString());
//            UC_SellCarFragment.this.startActivityForResult(i, SELL_CAR_REQUEST_CODE);
        } else if (view.getId() == R.id.used_car_tv_brand) {//品牌
            // 打开一个界面
            getCarType.openOneFragment();
            getCarType.setNeedLevel(2);
        } else if (view.getId() == R.id.used_car_btn_order_sellcars) {//预约卖车
            orderSellCar();
        } else if (view.getId() == R.id.used_car_hotline) {//电话咨询
            mCallPhoneDialog.show();
        }else if(view.getId() == R.id.titlebar_back_button){
            getActivity().finish();
        }
    }

    /**
     * @desc (判断是否按照要求填写内容)
     * @createtime 2016/11/2 - 11:58
     * @author xiaoxue
     */
    private void orderSellCar() {

        String mSelectBrand = selectBrand.getText().toString();
        if (TextUtils.isEmpty(mSelectBrand)) {
            toastMsg("请选择品牌！");
            return;
        }
        String mRegistTime = registTime.getText().toString();
        if (TextUtils.isEmpty(mRegistTime)) {
            toastMsg("请选择上牌时间！");
            return;
        }
        String mileage = mEdtMileAge.getText().toString();
        if (TextUtils.isEmpty(mileage)) {
            toastMsg("请输入行驶里程！");
            return;
        }

        if (!UC_FrameEtongApplication.getApplication().isLogin()) {
            Intent i = new Intent(getActivity(), UC_LoginActivity.class);
            i.putExtra("isShowToast", true);
            startActivity(i);
            return;
        }

        sellCarBtn.setClickable(false);
        mRegistTime = mRegistTime.replace("年", "-");
        mRegistTime = mRegistTime.replace("月", "-");
        mRegistTime = mRegistTime.replace("日", "");
        mUsedCarProvider.advanceSellcar(mSelectBrand, mRegistTime, mileage);
        loadStart(null, 0);
    }

    // 注释了从页面中选择时间的 ActivitResult
    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            // 当返回的数据为空时
            return;
        }

        if (resultCode == UC_RegistTimeActivity.SELL_CAR_RESULT_CODE) {

            // 从选择年份和月份界面获取到数据
            Bundle extras = data.getExtras();
            String year = "";
            ArrayList<String> stringArrayList = extras.getStringArrayList(UC_RegistTimeActivity.SET_YEAR);
            for (String s : stringArrayList) {
                year += s;
            }
            ((TextView) registTime).setText(year);
        }
    }*/

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
            // 隐藏侧滑页面
            if (sellCarDrawer != null) {
                sellCarDrawer.closeDrawer(Gravity.RIGHT);
            }
        }
    }

/*    @Subscriber(tag = Et_ChooseCarType.ETONG_CAT_TYPE_TAG)
    public void setGetCarType(Et_VechileType carType) {
        if(carType != null) {
            // 获取到车型的ID
            this.catTypeId = carType.getId();

            selectBrand.setText(carType.getName());
        }
    }*/

    // 侧滑选择车系
    @Subscriber(tag = Et_ChooseCarType.ETONG_CAT_SERIES_TAG)
    public void setGetCarType(Et_VechileSeries carSeries) {
        if (carSeries != null) {
            // 获取到车系的ID
            this.catTypeId = carSeries.getId();

            selectBrand.setText(carSeries.getName());
        }
    }


    //预约卖车接口回调
    @Subscriber(tag = UC_HttpTag.ADVANCE_SELL_CAR)
    public void orderSellCarResult(HttpMethod method) {
        String msg = method.data().getString("msg");
        String status = method.data().getString("status");
        loadFinish();
        sellCarBtn.setClickable(true);
        if (status.equals("true")) {
            conformDialog.show();
        } else if (status.equals(HttpPublisher.NETWORK_ERROR)) {
            toastMsg("预约失败，请检查网络");
        } else {
            toastMsg(msg);
        }
    }


    /**
     * @desc (初始化预约卖车成功之后弹出的弹出框)
     * @createtime 2016/10/25 0025-15:01
     * @author wukefan
     */
    private void initDialog() {
        conformDialog = new UC_ConformDialog(getActivity(), false);
        conformDialog.setTitle("感谢");
        conformDialog.setContent("您的卖车信息已提交，工作人员将马上与您取得联系，请确保您的联系方式可用！");
        conformDialog.setContentSize(13);
        conformDialog.setConfirmButtonClickListener("确定", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                catTypeId = null;
                registTime.setText("");
                selectBrand.setText("");
                mEdtMileAge.setText("");
                conformDialog.dismiss();
            }
        });
    }

    /**
     * @desc 选择时间
     * @createtime 2016/10/28 0028-17:29
     * @author wukefan
     */
    private void selectTimeDialog() {
        mChangeBirthDialog = new UC_SelectDateDialog(getActivity(), false);
        Calendar calendar = Calendar.getInstance();
        //设置选择时间的dialog默认显示为当前时间
        mChangeBirthDialog.setDate(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DAY_OF_MONTH));

        mChangeBirthDialog.setBirthdayListener(new UC_SelectDateDialog.OnBirthListener() {
            @Override
            public void onClick(String year, String month, String day) {

                String tempDate = DateUtils.getFormatDate(Integer.valueOf(year.substring(0, year.length() - 1)), Integer.valueOf(month.substring(0, month.length() - 1)), Integer.valueOf(day.substring(0, day.length() - 1)), 0, 0);

                if (DateUtils.compareTo(tempDate, DateUtils.getCurrentTimes()) == 1) {//tempDate>CurrentTimes(选择的上牌时间大于当前时间)
                    toastMsg("请选择正确的上牌时间");
                    return;
                }
//                if (calendar.get(Calendar.YEAR) < Integer.valueOf(year.substring(0, year.length() - 1))) {
//                    toastMsg("请选择正确的上牌时间");
//                    return;
//                }
//                if (String.valueOf(calendar.get(Calendar.YEAR)).equals(year.substring(0, year.length() - 1))
//                        && (calendar.get(Calendar.MONTH) + 1) < Integer.valueOf(month.substring(0, month.length() - 1))) {
//                    toastMsg("请选择正确的上牌时间");
//                    return;
//                }
//                if (String.valueOf(calendar.get(Calendar.YEAR)).equals(year.substring(0, year.length() - 1))
//                        && String.valueOf(calendar.get(Calendar.MONTH) + 1).equals(month.substring(0, month.length() - 1))
//                        && calendar.get(Calendar.DAY_OF_MONTH) < Integer.valueOf(day.substring(0, day.length() - 1))) {
//                    toastMsg("请选择正确的上牌时间");
//                    return;
//                }
                registTime.setText(year + month + day);
            }

        });
        mChangeBirthDialog.show();
    }

    /**
     * @desc (设置打电话的dialog)
     * @createtime 2016/11/4 0004-14:19
     * @author wukefan
     */
    private void setCallPhone() {
        mCallPhoneDialog = new UC_CancelConformDialog(getActivity(), false);
        mCallPhoneDialog.setTitle("提示");
        mCallPhoneDialog.setContent("确定拨打电话：" + telPhone + "吗？");
        mCallPhoneDialog.setContentSize(15);
        mCallPhoneDialog.setConfirmButtonClickListener("确定", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //确定操作
                PermissionsManager.getInstance()
                        .requestPermissionsIfNecessaryForResult(
                                UC_SellCarFragment.this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                new PermissionsResultAction() {

                                    @Override
                                    public void onGranted() {
                                        callPhoneDialog();
                                    }

                                    @Override
                                    public void onDenied(String permission) {
                                        toastMsg("授权失败，无法完成操作！");
                                        return;
                                    }
                                });
//                mCallPhoneDialog.dismiss();
            }
        });
    }

    /**
     * @desc 打电话
     * @createtime 2016/10/24 - 11:23
     * @author xiaoxue
     */
    private void callPhoneDialog() {

        try {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri
                    .parse("tel:"
                            + telPhone));
            startActivity(intent);
        } catch (SecurityException e) {
        }
        mCallPhoneDialog.dismiss();
    }

    //二手车页跳到卖车页显示不显示返回键的处理方法
    public static final String SELL_CAR_BACK_TITLE = "back title";
    public void showBackButton() {
        mTitleBar.showBackButton(true);
        addClickListener(view,R.id.titlebar_back_button);
    }
    public void hideBackButton() {
        mTitleBar.showBackButton(false);
    }
}


















