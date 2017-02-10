package com.etong.android.jxbizusedcar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.UC_HttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.user.UC_UserProvider;
import com.etong.android.frame.utils.DateUtils;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.frame.widget.three_slide_300.javabean.Models_Contrast_VechileType;
import com.etong.android.frame.widget.three_slide_300.main_content.MC_ChooseCarType;
import com.etong.android.jxappusedcar.bean.UC_FilterDataDictionaryBean;
import com.etong.android.jxappusedcar.utils.UC_SelectDateDialog;
import com.etong.android.jxbizusedcar.R;
import com.etong.android.jxbizusedcar.bean.UC_CityName;

import org.simple.eventbus.Subscriber;

import java.util.Calendar;

/**
 * @param
 * @desc (车辆评估界面)
 * @user sunyao
 * @createtime 2016/10/21 - 11:11
 * @return
 */
public class UC_CarAssessActivity extends BaseSubscriberActivity {
/*
  ##################################################################################################
  ##                                        类中的变量                                            ##
  ##################################################################################################
*/

    private String cityId = "";            // 车辆属地的城市id
    private String modelId = "";             // 车辆评估中的   车型id
    private String regDate = "";            // 车辆评估中的   上牌时间 （2012-01）
    private String mile = "";               // 车辆评估中的   里程数
    private String zone = "";               // 车辆评估中的   城市
    private String color = "";              // 车辆评估中的   颜色

    //颜色javabean
    private UC_FilterDataDictionaryBean.MapBean selectColorBean;

    public static int CAR_ASSESS_REQUEST_CODE = 201;
    public static int CAR_ASSESS_COLOR_REQUEST_CODE = 203;
    public static int CAR_REGION_ADDR = 301;

    private TitleBar mTitleBar;

    //上牌时间
    private ViewGroup vehicleAccessTimeContent;
    private TextView assessRegistTime;

    //车辆颜色
    private ViewGroup vehicleAccessColorContent;
    private TextView assessColor;

    //车辆属地
    private ViewGroup carRegionAdd_Content;
    private TextView carRegionAdd_Txt;

    //车型品牌
    private ViewGroup carBrandContent;
    private TextView carBrandTxt;

    private DrawerLayout vehicleEvaCarDrawer;
    private MC_ChooseCarType getCarType;

    //行驶里程
    private EditText assessMileage;
    private UC_UserProvider mUsedCarProvider;

    //选择时间dialog
    private UC_SelectDateDialog mChangeBirthDialog;


    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.uc_activity_vehicle_assess_content);
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
        mTitleBar.setTitle("车辆评估");

        // 车辆估值上面显示的图片
        ImageView ivBanner = findViewById(R.id.uc_iv_vehicle_assess_img, ImageView.class);
        int value = 0;
        Class<R.mipmap> cls = R.mipmap.class;
        try {
            value = cls.getDeclaredField("uc_sell_car_banner_iv").getInt(null);
        } catch (Exception e) {
        }
        ImageProvider.getInstance().loadImage(ivBanner, "drawable://" + value, R.mipmap.uc_image_loading_two);

        // 获取到侧滑界面，并设置不能侧滑打开
        vehicleEvaCarDrawer = findViewById(R.id.uc_drawerlayout_vehicle_evaluation, DrawerLayout.class);
        vehicleEvaCarDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
        // 设置页面上的监听事件
        getCarType = new MC_ChooseCarType(this, this, vehicleEvaCarDrawer, R.id.uc_content_menu_vehicle_evaluation);
        getCarType.setNeedChecked(false);

        // 选择车辆品牌
        carBrandContent = findViewById(R.id.uc_content_car_brand, ViewGroup.class);
        carBrandTxt = findViewById(R.id.uc_tv_car_brand, TextView.class);
        addClickListener(carBrandContent);

        // 车辆上牌时间
        vehicleAccessTimeContent = findViewById(R.id.uc_content_vehicle_access_time, ViewGroup.class);
        assessRegistTime = findViewById(R.id.uc_tv_car_assess_regist_time, TextView.class);
        addClickListener(vehicleAccessTimeContent);

        // 车辆属地
        carRegionAdd_Content = findViewById(R.id.uc_content_car_region_address, ViewGroup.class);
        carRegionAdd_Txt = findViewById(R.id.uc_txt_car_region_address, TextView.class);
        addClickListener(carRegionAdd_Content);

        //车辆颜色
        vehicleAccessColorContent = findViewById(R.id.uc_content_vehicle_access_color, ViewGroup.class);
        assessColor = findViewById(R.id.uc_tv_car_assess_color, TextView.class);
        addClickListener(vehicleAccessColorContent);

        //行驶里程
        assessMileage = findViewById(R.id.uc_edt_car_assess_mileage, EditText.class);
        addClickListener(assessMileage);
        addClickListener(R.id.btn_car_assess);

        assessMileage.addTextChangedListener(new TextWatcher() {
                                                 @Override
                                                 public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                 }

                                                 @Override
                                                 public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                                 }

                                                 @Override
                                                 public void afterTextChanged(Editable editable) {
                                                     String temp = editable.toString();

                                                     //这部分是处理如果用户输入以0开头,去掉0
                                                     if (temp.trim().substring(0).equals("0")) {
                                                         assessMileage.setText("");
                                                         return;
                                                     }

                                                     if (!TextUtils.isEmpty(temp)) {//整数最多4位
                                                         if (temp.length() > 4) {
                                                             editable.delete(temp.length() - 1, temp.length());
                                                             return;
                                                         }
                                                     }
                                                 }
                                             }
        );

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

        mUsedCarProvider = UC_UserProvider.getInstance();
        mUsedCarProvider.initalize(HttpPublisher.getInstance());
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
        Intent i = new Intent();
        if (view.getId() == R.id.uc_content_vehicle_access_time) {//上牌时间

            if (null == mChangeBirthDialog) {
                selectTimeDialog();//初始化选择时间的dialog
            } else {
                if (!TextUtils.isEmpty(assessRegistTime.getText().toString())) {//上牌时间不为空时，设置选择时间的dialog默认显示为上牌时间
                    mChangeBirthDialog.setStringDate(assessRegistTime.getText().toString());
                } else {//上牌时间为空时，设置选择时间的dialog默认显示为当前时间
                    final Calendar calendar = Calendar.getInstance();
                    mChangeBirthDialog.setDate(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DAY_OF_MONTH));
                }

                mChangeBirthDialog.show();
            }
//            i.setClass(UC_CarAssessActivity.this, UC_RegistTimeActivity.class);
//            i.putExtra(UC_RegistTimeActivity.SELECT_YEAR, assessRegistTime.getText().toString());
//            startActivityForResult(i, CAR_ASSESS_REQUEST_CODE);
        } else if (view.getId() == R.id.uc_content_car_region_address) {//车辆属地
            i.setClass(UC_CarAssessActivity.this, UC_Region_SelectActivity.class);
            startActivityForResult(i, CAR_REGION_ADDR);
        } else if (view.getId() == R.id.uc_content_car_brand) {//选择品牌
            // 打开一个界面
            getCarType.openOneFragment();
        } else if (view.getId() == R.id.uc_content_vehicle_access_color) {//车辆颜色
            i.setClass(UC_CarAssessActivity.this, UC_VehicleColorActivity.class);
            i.putExtra(UC_VehicleColorActivity.SELECT_COLOR, selectColorBean);
            startActivityForResult(i, CAR_ASSESS_COLOR_REQUEST_CODE);
        } else if (view.getId() == R.id.btn_car_assess) {//开始评估
            startAssess();
        }
    }

    /*
      ##################################################################################################
      ##                              使用的逻辑方法，以及对外公开的方法                              ##
      ##                                      请求数据、获取数据                                      ##
      ##################################################################################################
    */

    private void startAssess() {
        if (TextUtils.isEmpty(carBrandTxt.getText().toString())) {
            toastMsg("请选择车辆品牌！");
            return;
        }
        String registTime = assessRegistTime.getText().toString();
        if (TextUtils.isEmpty(registTime)) {
            toastMsg("请选择上牌时间！");
            return;
        }
        if (TextUtils.isEmpty(carRegionAdd_Txt.getText().toString())) {
            toastMsg("请选择车辆属地！");
            return;
        }
        String color = assessColor.getText().toString();
        if (TextUtils.isEmpty(color)) {
            toastMsg("请选择颜色！");
            return;
        }
        String mileage = assessMileage.getText().toString();
        if (TextUtils.isEmpty(mileage)) {
            toastMsg("请输入行驶里程！");
            return;
        }

        registTime = registTime.replace("年", "-");
        registTime = registTime.replace("月", "-");
        registTime = registTime.replace("日", "");
        mUsedCarProvider.getUsedCarPrice(modelId, registTime, mileage, cityId, color);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            // 当返回的数据为空时
            return;
        }

      /*  if (resultCode == UC_RegistTimeActivity.SELL_CAR_RESULT_CODE) {

            // 从选择年份和月份界面获取到数据
            Bundle extras = data.getExtras();
            String year = "";
            ArrayList<String> stringArrayList = extras.getStringArrayList(UC_RegistTimeActivity.SET_YEAR);
            for (String s : stringArrayList) {
                year += s;
            }
            assessRegistTime.setText(year);
        } else */
        if (resultCode == UC_SelectCityMenu.CITY_RESULT_CODE) {
            // 从选择地区页面获取到数据
            UC_CityName citySource = (UC_CityName) data.getSerializableExtra(UC_SelectCityMenu.CITY_EXTRA_SOURCE);
            if (citySource != null) {
                cityId = citySource.getCity_id();

                if (!TextUtils.isEmpty(citySource.getCity_name())) {
                    carRegionAdd_Txt.setText(citySource.getCity_name());
                }
            }
        } else if (resultCode == UC_VehicleColorActivity.SELECT_COLOR_RESULT_CODE) {
            // 从选择颜色界面获取到数据
            Bundle extras = data.getExtras();
            selectColorBean = (UC_FilterDataDictionaryBean.MapBean) extras.getSerializable(UC_VehicleColorActivity.SELECT_COLOR);

            assessColor.setText(selectColorBean.getValue());
        }
    }

    /**
     * @param
     * @return
     * @desc (从最后的车型中获取到车辆的名字和id)
     * @user sunyao
     * @createtime 2016/10/27 - 16:47
     */
    @Subscriber(tag = MC_ChooseCarType.CAT_TYPE_TAG)
    protected void getCarModels(Models_Contrast_VechileType add) {
        if (add != null) {
            String longModelName = add.getModel_name();        // 获取到车型的长名
            if (!TextUtils.isEmpty(longModelName)) {
                carBrandTxt.setText(longModelName);
            }
            if (!TextUtils.isEmpty(add.getModel_id())) {
                modelId = add.getModel_id();                // 获取到车型的id
            }
        }
    }


    /**
     * 获取车辆估值结果
     *
     * @param method
     */
    @Subscriber(tag = UC_HttpTag.GET_USED_CAR_PRICE)
    private void getUsedCarPriceResult(HttpMethod method) {
        String msg = method.data().getString("msg");
        String status = method.data().getString("status");
        if (status.equals("true")) {
            JSONObject object = method.data().getJSONObject("data");
            String statu = object.getString("status");
            if (statu.equals("1")) {
                JSONObject eval_prices = object.getJSONObject("eval_prices");
                String report_url = object.getString("report_url");
                Intent i = new Intent(UC_CarAssessActivity.this, UC_CarAssessResultWebActivity.class);
                i.putExtra("resultUrl", report_url);
                startActivity(i);
                finish();
                toastMsg("估值成功~");
            } else if (statu.equals("0")) {
                String error_msg = object.getString("error_msg");
                toastMsg(error_msg);
            }
        } else {
            toastMsg(msg);
        }
    }


    /**
     * @desc 选择时间
     * @createtime 2016/10/28 0028-17:29
     * @author wukefan
     */
    private void selectTimeDialog() {
        mChangeBirthDialog = new UC_SelectDateDialog(UC_CarAssessActivity.this, false);
        final Calendar calendar = Calendar.getInstance();
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

                assessRegistTime.setText(year + month + day);
            }

        });

        mChangeBirthDialog.show();
    }

/*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/

}
