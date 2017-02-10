package com.etong.android.jxappusedcar.content;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.UC_HttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.user.UC_FrameEtongApplication;
import com.etong.android.frame.user.UC_LoginActivity;
import com.etong.android.frame.user.UC_UserProvider;
import com.etong.android.frame.utils.DateUtils;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.frame.widget.dialog.UC_ConformDialog;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.bean.UC_CarDetail_TitleBean;
import com.etong.android.jxappusedcar.utils.UC_SelectDateDialog;

import org.simple.eventbus.Subscriber;

import java.util.Calendar;

/**
 * @param
 * @desc (预约看车界面)
 * @user sunyao
 * @createtime 2016/10/22 - 17:30
 * @return
 */
public class UC_AppointTakeCar_Activity extends BaseSubscriberActivity {

/*
  ##################################################################################################
  ##                                        类中的变量                                            ##
  ##################################################################################################
*/

    private ViewGroup takeCarTimeContent;
    private TextView takeCarTimeTxt;
    private UC_ConformDialog conformDialog;
    private TextView uc_tv_carName;
    private UC_UserProvider mProvider;
    private UC_CarDetail_TitleBean titleBean;
    private Button takeCarBtn;
    private UC_SelectDateDialog mChangeBirthDialog;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.uc_content_appoint_take_car_activity);

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
        TitleBar mTitleBar = new TitleBar(this);
        mTitleBar.setTitle("预约看车");
        mTitleBar.showNextButton(false);

        uc_tv_carName = findViewById(R.id.uc_tv_carName, TextView.class);
        takeCarTimeContent = findViewById(R.id.uc_btn_content_appoint_time_take_car, ViewGroup.class);  // 点击事件的父包裹内容
        takeCarTimeTxt = findViewById(R.id.uc_txt_appoint_time_take_car, TextView.class);               // 需要设置事件的Tv
        takeCarBtn = findViewById(R.id.uc_btn_conform_take_car, Button.class);

        addClickListener(takeCarTimeContent);
        addClickListener(takeCarBtn);

        initDialog();
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
        UC_UserProvider.initalize(HttpPublisher.getInstance());
        mProvider = UC_UserProvider.getInstance();

        Intent intent = getIntent();
        titleBean = (UC_CarDetail_TitleBean) intent.getSerializableExtra(UC_CarDetail_Activity.POST_TITLE_CAR_DETAIL);

        if (titleBean != null) {
            uc_tv_carName.setText(titleBean.getF_cartitle());
        }
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
        if (view.getId() == R.id.uc_btn_content_appoint_time_take_car) {//看车时间

            if (null == mChangeBirthDialog) {
                selectTimeDialog();//初始化选择时间的dialog
            } else {
                if (!TextUtils.isEmpty(takeCarTimeTxt.getText().toString())) {//预约看车不为空时，设置选择时间的dialog默认显示为上牌时间
                    mChangeBirthDialog.setStringDate(takeCarTimeTxt.getText().toString());
                } else {//预约看车为空时，设置选择时间的dialog默认显示为当前时间
                    final Calendar calendar = Calendar.getInstance();
                    mChangeBirthDialog.setDate(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DAY_OF_MONTH));
                }

                mChangeBirthDialog.show();
            }

        } else if (view.getId() == R.id.uc_btn_conform_take_car) {//提交
            conformAppointTakeCar();
        }
    }

    private void selectTimeDialog() {

        mChangeBirthDialog = new UC_SelectDateDialog(UC_AppointTakeCar_Activity.this, false);
        final Calendar calendar = Calendar.getInstance();
        //设置选择时间的dialog默认显示为当前时间
        mChangeBirthDialog.setDate(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DAY_OF_MONTH));

        mChangeBirthDialog.setBirthdayListener(new UC_SelectDateDialog.OnBirthListener() {
            @Override
            public void onClick(String year, String month, String day) {

                String tempDate = DateUtils.getFormatDate(Integer.valueOf(year.substring(0, year.length() - 1)), Integer.valueOf(month.substring(0, month.length() - 1)), Integer.valueOf(day.substring(0, day.length() - 1)), 0, 0);

                if (DateUtils.compareTo(tempDate,
                        DateUtils.getFormatDate(calendar.get(Calendar.YEAR),
                                (calendar.get(Calendar.MONTH) + 1),
                                calendar.get(Calendar.DAY_OF_MONTH), 0, 0)) == -1) {//CurrentTimes>tempDate(当前时间大于选择的看车时间)
                    toastMsg("请选择正确的看车时间");
                    return;
                }

//                    if (calendar.get(Calendar.YEAR) > Integer.valueOf(year.substring(0, year.length() - 1))) {
//                        toastMsg("请选择正确的看车时间");
//                        return;
//                    }
//                    if (String.valueOf(calendar.get(Calendar.YEAR)).equals(year.substring(0, year.length() - 1))
//                            && (calendar.get(Calendar.MONTH) + 1) > Integer.valueOf(month.substring(0, month.length() - 1))) {
//                        toastMsg("请选择正确的看车时间");
//                        return;
//                    }
//                    if (String.valueOf(calendar.get(Calendar.YEAR)).equals(year.substring(0, year.length() - 1))
//                            && String.valueOf(calendar.get(Calendar.MONTH) + 1).equals(month.substring(0, month.length() - 1))
//                            && calendar.get(Calendar.DAY_OF_MONTH) > Integer.valueOf(day.substring(0, day.length() - 1))) {
//                        toastMsg("请选择正确的看车时间");
//                        return;
//                    }

                takeCarTimeTxt.setText(year + month + day);
            }
        });

        mChangeBirthDialog.show();

    }




/*
  ##################################################################################################
  ##                              使用的逻辑方法，以及对外公开的方法                              ##
  ##                                      请求数据、获取数据                                      ##
  ##################################################################################################
*/

    /**
     * @desc (初始化预约看车成功之后弹出的弹出框)
     * @createtime 2016/10/25 0025-15:01
     * @author wukefan
     */
    private void initDialog() {
        conformDialog = new UC_ConformDialog(this, false);
        conformDialog.setTitle("感谢");
        conformDialog.setContent("您的看车信息已提交，工作人员将马上与您取得联系，请确保您的联系方式可用！");
        conformDialog.setContentSize(13);
        conformDialog.setConfirmButtonClickListener("确定", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conformDialog.dismiss();
                takeCarTimeTxt.setText("");
            }
        });
    }

    /**
     * @desc 提交操作
     * @createtime 2016/11/1 0001-9:38
     * @author wukefan
     */
    private void conformAppointTakeCar() {

        String mTakeCarTime = takeCarTimeTxt.getText().toString();
        if (TextUtils.isEmpty(mTakeCarTime)) {
            toastMsg("请选择看车时间！");
            return;
        }

        if (!UC_FrameEtongApplication.getApplication().isLogin()) {
            Intent i = new Intent(UC_AppointTakeCar_Activity.this, UC_LoginActivity.class);
            i.putExtra("isShowToast", true);
            startActivity(i);
            return;
        }

        takeCarBtn.setClickable(false);
        mTakeCarTime = mTakeCarTime.replace("年", "-");
        mTakeCarTime = mTakeCarTime.replace("月", "-");
        mTakeCarTime = mTakeCarTime.replace("日", "");

        mProvider.advanceBuycar(titleBean.getF_dvid(), titleBean.getF_cartitle(), mTakeCarTime);
        loadStart(null, 0);
    }

    /**
     * @desc (预约看车接口回调)
     * @createtime 2016/11/1 0001-9:38
     * @author wukefan
     */
    @Subscriber(tag = UC_HttpTag.ADVANCE_BUY_CAR)
    public void appointTakeCarResult(HttpMethod method) {
        String msg = method.data().getString("msg");
        String status = method.data().getString("status");
        takeCarBtn.setClickable(true);
        loadFinish();
        if ("true".equals(status)) {
            conformDialog.show();
        } else if (status.equals(HttpPublisher.NETWORK_ERROR)) {
            toastMsg("预约失败，请检查网络");
        } else if("false".equals(status)){
            toastMsg("您的看车信息已提交，无需再次提交！");
        }
    }


/*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/

}
