package com.etong.android.jxbizusedcar.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.UC_HttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.user.UC_UserProvider;
import com.etong.android.frame.utils.ListAdapter;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappusedcar.utils.UC_AllCapTransformationMethod;
import com.etong.android.jxbizusedcar.Interface.UC_InterfaceStatus;
import com.etong.android.jxbizusedcar.R;
import com.etong.android.jxbizusedcar.bean.UC_Identify_OrderListBean;

import org.simple.eventbus.Subscriber;

import java.util.Arrays;

public class UC_AdditionalInformationActivity extends BaseSubscriberActivity implements UC_InterfaceStatus {

    private UC_Identify_OrderListBean mIdentifyOrderBean = new UC_Identify_OrderListBean();
    private String[] plateTypeDatas = {"京", "沪", "浙", "苏", "粤", "鲁", "晋", "冀", "豫", "川", "渝", "辽", "吉", "黑", "皖", "鄂", "湘", "赣", "闽", "陕", "甘", "宁", "蒙", "津", "贵", "云", "桂", "琼", "青", "新", "藏"};
    private String[] plateLetterDatas = {"A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    private Button mPlateTypeBtn;               //车牌号省份
    private Button mPlateLetterBtn;             //车牌号字母
    private Button mCompeleBtn;                 //完成
    private EditText mPlateEdt;                 //车牌号输入框
    private EditText mEngineEdt;                //发动机号输入框
    private TitleBar mTitleBar;
    private ViewGroup mPlateView;               //车牌号视图
    private ViewGroup mEngineView;              //发动机号视图
    private PopupWindow mPopup;
    private UC_UserProvider mUC_Provider;
    private int tag;

    /*
  ##################################################################################################
  ##                                        类中的变量                                            ##
  ##################################################################################################
*/


    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.uc_activity_additional_information);

        mUC_Provider = UC_UserProvider.getInstance();
        mUC_Provider.initalize(HttpPublisher.getInstance());

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
        mTitleBar.setTitle("补录信息");
        //车牌号
        mPlateView = findViewById(R.id.uc_identify_order_rl_plate, ViewGroup.class);
        mPlateTypeBtn = findViewById(R.id.uc_add_info_plate_type, Button.class);//省份
        addClickListener(mPlateTypeBtn);
        mPlateLetterBtn = findViewById(R.id.uc_add_info_plate_letter, Button.class);//字母
        addClickListener(mPlateLetterBtn);
        mPlateEdt = findViewById(R.id.uc_add_info_edt_plate, EditText.class);//数字
        mPlateEdt.setTransformationMethod(new UC_AllCapTransformationMethod());
        //发动机号
        mEngineView = findViewById(R.id.uc_identify_order_ll_engine, ViewGroup.class);
        mEngineEdt = findViewById(R.id.uc_add_info_edt_engine, EditText.class);
        mEngineEdt.setTransformationMethod(new UC_AllCapTransformationMethod());

        //完成按钮
        mCompeleBtn = findViewById(R.id.uc_add_info_btn_complete, Button.class);
        addClickListener(mCompeleBtn);

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
        Intent intent = getIntent();
        mIdentifyOrderBean = (UC_Identify_OrderListBean) intent.getSerializableExtra("UC_Identify_OrderListBean");
        tag = intent.getIntExtra("tag", 11);

        switch (mIdentifyOrderBean.getF_status()) {
            /**支付成功，补录信息（车牌号）**/
            case PAY_SUCCESS_NUMBER_PLATE:
                mPlateView.setVisibility(View.VISIBLE);
                mEngineView.setVisibility(View.GONE);
                break;
            /**支付成功，补录信息（发动机号）**/
            case PAY_SUCCESS_ENGINE_NUMBER:
                mPlateView.setVisibility(View.GONE);
                mEngineView.setVisibility(View.VISIBLE);
                break;
            /**支付成功，补录信息（车牌号&发动机号）**/
            case PAY_SUCCESS_ENGINE_PLATE:
                mPlateView.setVisibility(View.VISIBLE);
                mEngineView.setVisibility(View.VISIBLE);
                break;
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

        if (view.getId() == R.id.uc_add_info_plate_type) {//车牌号省份
            showPopupWindow(mPlateTypeBtn, mPlateTypeBtn, 0);
        } else if (view.getId() == R.id.uc_add_info_plate_letter) {//车牌号字母
            showPopupWindow(mPlateLetterBtn, mPlateLetterBtn, 1);
        } else if (view.getId() == R.id.uc_add_info_btn_complete) {//完成

            switch (mIdentifyOrderBean.getF_status()) {
                /**支付成功，补录信息（车牌号）**/
                case PAY_SUCCESS_NUMBER_PLATE:
                    onPlateComplete();
                    break;
                /**支付成功，补录信息（发动机号）**/
                case PAY_SUCCESS_ENGINE_NUMBER:
                    onEngineComplete();
                    break;
                /**支付成功，补录信息（车牌号&发动机号）**/
                case PAY_SUCCESS_ENGINE_PLATE:
                    onPlateAndEngineComplete();
                    break;
            }
        }
    }


/*
  ##################################################################################################
  ##                              使用的逻辑方法，以及对外公开的方法                              ##
  ##                                      请求数据、获取数据                                      ##
  ##################################################################################################
*/

    //补录信息
    @Subscriber(tag = UC_HttpTag.REGET_REPORT)
    public void regetReportResult(HttpMethod method) {
        mCompeleBtn.setClickable(true);
        String status = method.data().getString("status");
        String msg = method.data().getString("msg");

        if (status.equals("true")) {
            this.finish();
            mEventBus.post("", tag + "");
            toastMsg(msg);
        } else if (status.equals(HttpPublisher.NETWORK_ERROR)) {//服务器无响应
            toastMsg("提交失败，请检查网络重试~");
        } else if (status.equals(HttpPublisher.DATA_ERROR)) {//数据请求失败
            toastMsg("提交失败，请重试~");
        }
    }

    /**
     * @param flag 0-车牌号省份 1-车牌号字母
     * @param btn  要设置点击结果的按钮
     * @desc (选择车牌省份字母的popupwindow)
     * @createtime 2016/11/11 0011-9:25
     * @author wukefan
     */
    private void showPopupWindow(View v, final Button btn, int flag) {

        View view = LayoutInflater.from(this).inflate(R.layout.uc_popupwindow_plate_select, null);
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        GridView mGridView = (GridView) view.findViewById(R.id.uc_gv_plate_select);

        ListAdapter<String> mGridListAdapter = new ListAdapter<String>(this, R.layout.uc_plate_items_gv) {
            @Override
            protected void onPaint(View view, final String data, int position) {
                TextView mPlateItem = findViewById(view, R.id.uc_txt_plate_select_item, TextView.class);

                ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) mPlateItem.getLayoutParams();
                //将宽度设置为屏幕的1/9
                lp.width = (mWidth - (int) (50 * mDensity + 0.5f)) / 9;
                //将高度设置为宽度的21/18
                lp.height = ((mWidth - (int) (50 * mDensity + 0.5f)) / 9) * 21 / 18;
                //根据屏幕大小按比例动态设置图片大小
                mPlateItem.setLayoutParams(lp);

                mPlateItem.setText(data);

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btn.setText(data);
                        mPopup.dismiss();
                    }
                });
            }
        };

        mGridView.setAdapter(mGridListAdapter);
        if (flag == 0) {//显示车牌号省份
            mGridListAdapter.addAll(Arrays.asList(plateTypeDatas));
        } else if (flag == 1) {//显示车牌号字母
            mGridListAdapter.addAll(Arrays.asList(plateLetterDatas));
        }
        mGridListAdapter.notifyDataSetChanged();

        mPopup = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, false);
        // 允许点击外部消失
        mPopup.setBackgroundDrawable(new BitmapDrawable());//注意这里如果不设置，下面的setOutsideTouchable(true);允许点击外部消失会失效
        mPopup.setOutsideTouchable(true);   //设置外部点击关闭ppw窗口
        // 设置此参数获得焦点，否则无法点击
        mPopup.setFocusable(true);
        //显示在屏幕底部
        mPopup.showAtLocation(v, Gravity.BOTTOM, 0, 0);
    }

    /**
     * @desc (补录信息（车牌号）点击完成操作)
     * @createtime 2016/11/11 0011-9:25
     * @author wukefan
     */
    private void onPlateComplete() {
        String carRegex = "[\\u4e00-\\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}";
        String tempPlate = mPlateTypeBtn.getText().toString() + mPlateLetterBtn.getText().toString() + mPlateEdt.getText().toString();

        if (mPlateEdt.getText().toString().length() != 5) {
            toastMsg("请输入完整车牌号");
            return;
        }
        if (!(tempPlate.toUpperCase()).matches(carRegex)) {
            toastMsg("请输入正确车牌号");
            return;
        }

//        mUC_Provider.reGetReport("LSVN141Z4C2028669", mIdentifyOrderBean.getF_cid() + "", tempPlate, null);
        mUC_Provider.reGetReport(mIdentifyOrderBean.getF_vin(), mIdentifyOrderBean.getF_cid() + "", tempPlate, null);
    }

    /**
     * @desc (补录信息（发动机号）点击完成操作)
     * @createtime 2016/11/11 0011-9:25
     * @author wukefan
     */
    private void onEngineComplete() {
        String tempEngine = mEngineEdt.getText().toString();

        if (mEngineEdt.getText().toString().length() != 6) {
            toastMsg("请输入完整发动机号");
            return;
        }
        mUC_Provider.reGetReport(mIdentifyOrderBean.getF_vin(), mIdentifyOrderBean.getF_cid() + "", null, tempEngine);
    }

    /**
     * @desc (补录信息（车牌号&发动机号）点击完成操作)
     * @createtime 2016/11/11 0011-9:25
     * @author wukefan
     */
    private void onPlateAndEngineComplete() {
        String carRegex = "[\\u4e00-\\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}";
        String tempPlate = mPlateTypeBtn.getText().toString() + mPlateLetterBtn.getText().toString() + mPlateEdt.getText().toString();
        String tempEngine = mEngineEdt.getText().toString();

        if (mPlateEdt.getText().toString().length() != 5) {
            toastMsg("请输入完整车牌号");
            return;
        }
        if (!(tempPlate.toUpperCase()).matches(carRegex)) {
            toastMsg("请输入正确车牌号");
            return;
        }

        if (tempEngine.length() != 6) {
            toastMsg("请输入完整发动机号");
            return;
        }

        mCompeleBtn.setClickable(false);
        mUC_Provider.reGetReport(mIdentifyOrderBean.getF_vin(), mIdentifyOrderBean.getF_cid() + "", tempPlate, tempEngine);
    }

/*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/

}
