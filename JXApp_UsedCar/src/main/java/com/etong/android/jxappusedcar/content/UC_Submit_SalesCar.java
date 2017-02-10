package com.etong.android.jxappusedcar.content;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ReplacementTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.FrameUserInfo;
import com.etong.android.frame.utils.ActivitySkipUtil;
import com.etong.android.frame.utils.ActivityStackManager;
import com.etong.android.frame.utils.ListAdapter;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_VechileType;
import com.etong.android.jxappfours.models_contrast.main_content.MC_ChooseCarType;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.provider.UC_WorldProvider;

import org.simple.eventbus.Subscriber;

import java.util.Arrays;

/**
 * @author : by sunyao
 * @ClassName : UC_Submit_SalesCar
 * @Description : (提交预约卖车之前将信息补充完整)
 * @date : 2016/10/6 - 16:42
 */

public class UC_Submit_SalesCar extends BaseSubscriberActivity {

    private TitleBar mTitleBar;
    private DrawerLayout submitSalesCarDl;
    private MC_ChooseCarType salesCarType;
    private ViewGroup carTypeContent;
    private TextView carInfoTxt;
    private boolean isSelectMyCar;
    private TextView selectPlate;
    private TextView detailTitle;
    private Button plateBtn;
    private EditText plateEdt;
    private EditText mNameEdt;
    private EditText mPhpneEdt;
    private EditText mRemarkEdt;
    private LinearLayout selectPlateLayout;
    private FrameUserInfo.Frame_MyCarItemBean mMyCarItemBean;
    private String[] plateDatas = {"京", "沪", "浙", "苏", "粤", "鲁", "晋", "冀", "豫", "川", "渝", "辽", "吉", "黑", "皖", "鄂", "湘", "赣", "闽", "陕", "甘", "宁", "蒙", "津", "贵", "云", "桂", "琼", "青", "新", "藏"};
    private PopupWindow mPopup;
    private LinearLayout salesCarView;
    private String vid;
    private String carsetId;
    private String carset;
    private String brand;

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
    private ScrollView scrollView;
    private LinearLayout resultLayout;
    private HttpPublisher mHttpPublisher;
    private UC_WorldProvider mProvider;
    private Button mSubmitBtn;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.used_car_submit_sales_car);

        //初始化请求
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(this);
        mProvider = UC_WorldProvider.getInstance();
        mProvider.initialize(mHttpPublisher);

        Intent i = getIntent();
        isSelectMyCar = i.getBooleanExtra("isSelectMyCar", false);
        initView();
        selectPlateLayout.setVisibility(View.GONE);
        if (isSelectMyCar) {
            mMyCarItemBean = (FrameUserInfo.Frame_MyCarItemBean) i.getSerializableExtra("MyCar");
            vid = mMyCarItemBean.getVid() + "";
            carsetId = mMyCarItemBean.getCarsetId() + "";
            selectPlateLayout.setVisibility(View.VISIBLE);
            selectPlate.setText(mMyCarItemBean.getPlate_no());
            detailTitle.setText("亲，请在选择爱车后将车辆信息补充完整，并点击提交，即可完成预约申请哦~");
            carInfoTxt.setText(mMyCarItemBean.getVtitle());
            plateBtn.setText(mMyCarItemBean.getPlate_no().substring(0, 1));
            plateEdt.setText(mMyCarItemBean.getPlate_no().substring(1));
            plateEdt.requestFocus();
            plateBtn.setEnabled(false);
            plateEdt.setEnabled(false);
            carTypeContent.setClickable(false);
        }

        setUserInfo();

        initFragmentListener();
    }

    /**
     * @param
     * @return
     * @desc (初始化侧滑出来的三个页面)
     * @user sunyao
     * @createtime 2016/10/6 - 17:37
     */
    private void initFragmentListener() {
        salesCarType = new MC_ChooseCarType(this, this, submitSalesCarDl, R.id.used_car_select_car_info_fragment);
        salesCarType.setNeedChecked(false);
    }

    /**
     * @param
     * @return
     * @desc (初始化必要的组件)
     * @user sunyao
     * @createtime 2016/10/6 - 17:29
     */
    private void initView() {
        mTitleBar = new TitleBar(this);
        mTitleBar.setTitle("预约卖车");
        mTitleBar.showNextButton(false);

        scrollView = findViewById(R.id.used_car_select_sv_scroll, ScrollView.class);

        resultLayout = findViewById(R.id.used_car_select_ll_result, LinearLayout.class);

        salesCarView = findViewById(R.id.used_car_select_ll, LinearLayout.class);

        detailTitle = findViewById(R.id.used_car_select_txt_detail_title, TextView.class);
        selectPlateLayout = findViewById(R.id.used_car_select_ll_plate, LinearLayout.class);
        selectPlate = findViewById(R.id.used_car_submit_car_number_text, TextView.class);
        plateBtn = findViewById(R.id.used_car_select_btn_type, Button.class);
        plateEdt = findViewById(R.id.used_car_select_edt_plate, EditText.class);
        mNameEdt = findViewById(R.id.used_car_select_edt_name, EditText.class);
        mPhpneEdt = findViewById(R.id.used_car_select_edt_phone, EditText.class);
        mRemarkEdt = findViewById(R.id.used_car_select_edt_remark, EditText.class);
        mSubmitBtn = findViewById(R.id.used_car_select_btn_commit, Button.class);

        carInfoTxt = (TextView) findViewById(R.id.used_car_select_car_car_info_text);
        // 初始化侧滑组件
        submitSalesCarDl = (DrawerLayout) findViewById(R.id.used_car_select_car_info_drawerlayout);
        submitSalesCarDl.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);

        carTypeContent = (ViewGroup) findViewById(R.id.used_car_select_car_car_info_content);

        addClickListener(carTypeContent);
        addClickListener(plateBtn);
        addClickListener(R.id.used_car_select_btn_commit);
        addClickListener(R.id.used_car_select_btn_continue);

        plateEdt.setTransformationMethod(new AllCapTransformationMethod());

        plateEdt.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                            }

                                            @Override
                                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                            }

                                            @Override
                                            public void afterTextChanged(Editable editable) {
                                                String temp = editable.toString();
                                                char[] temC = temp.toCharArray();
                                                if (temp.length() == 1) {//第一个字符为大写字母
                                                    int mid = temC[0];
                                                    if ((mid >= 65 && mid <= 90) || (mid >= 97 && mid <= 122)) {//字母
                                                        return;
                                                    } else {
                                                        plateEdt.setText("");
                                                    }
                                                }
                                            }
                                        }

        );
    }

    /**
     * @desc 设置选择车牌省份的popupwindow
     */
    private void setPopupWindow() {

        View view = LayoutInflater.from(this).inflate(R.layout.used_car_mycar_popupwindow_select, null);
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        GridView mGridView = (GridView) view.findViewById(R.id.used_car_mycar_gv_mycar_plate_select);

        ListAdapter<String> mGridListAdapter = new ListAdapter<String>(this, R.layout.used_car_mycarr_plate_items_gv) {
            @Override
            protected void onPaint(View view, final String data, int position) {
                TextView mPlateItem = findViewById(view, R.id.used_car_mycar_txt_mycar_plate_select_item, TextView.class);

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
                        plateBtn.setText(data);
                        mPopup.dismiss();
                    }
                });
            }
        };

        mGridView.setAdapter(mGridListAdapter);
        mGridListAdapter.addAll(Arrays.asList(plateDatas));
        mGridListAdapter.notifyDataSetChanged();

        mPopup = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, false);
        // 允许点击外部消失
        mPopup.setBackgroundDrawable(new BitmapDrawable());//注意这里如果不设置，下面的setOutsideTouchable(true);允许点击外部消失会失效
        mPopup.setOutsideTouchable(true);   //设置外部点击关闭ppw窗口
        // 设置此参数获得焦点，否则无法点击
        mPopup.setFocusable(true);
    }

    /**
     * @desc 显示选择车牌省份的popupwindow
     */
    protected void showPopupWindow() {
        //显示PopupWindow
        View rootview = LayoutInflater.from(UC_Submit_SalesCar.this).inflate(R.layout.used_car_submit_sales_car, null);
        mPopup.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
//        int popupWidth = view.getMeasuredWidth();    //  获取测量后的宽度
//        int popupHeight = view.getMeasuredHeight();  //获取测量后的高度
//        int[] location = new int[2];
//
//        //mPopup.showAsDropDown(mBtnPlate);
//        // 获得位置 这里的v是目标控件，就是你要放在这个v的上面还是下面
//        v.getLocationOnScreen(location);
//        //这里就可自定义在上方和下方了 ，这种方式是为了确定在某个位置，某个控件的左边，右边，上边，下边都可以
//        mPopup.showAtLocation(v, Gravity.NO_GRAVITY, location[0] / 2, location[1] / 3 * 2 - v.getHeight() / 2 - (int) (5 * mDensity + 0.5f));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.used_car_select_car_car_info_content) {//车型
            submitSalesCarDl.openDrawer(Gravity.RIGHT);
        } else if (view.getId() == R.id.used_car_select_btn_commit) {//提交
            onCommit();
        } else if (view.getId() == R.id.used_car_select_btn_type) {//车牌号省份
            if (null == mPopup) {
                setPopupWindow();
            }
            showPopupWindow();
        } else if (view.getId() == R.id.used_car_select_btn_continue) {//继续逛逛
            ActivityStackManager.create().finishActivity(UC_SelectWay.class);
            ActivityStackManager.create().finishActivity(UC_Select_CarNumber.class);
            ActivitySkipUtil.skipActivity(UC_Submit_SalesCar.this, UC_World_ListActivity.class);
            finish();
        }
    }


    @Subscriber(tag = MC_ChooseCarType.CAT_TYPE_TAG)
    protected void getCarTypeInfo(Models_Contrast_VechileType selectCar) {
        if (selectCar != null) {
            carInfoTxt.setText(selectCar.getFullName());
            vid = selectCar.getVid() + "";
            carsetId = selectCar.getCarset() + "";
            carset = selectCar.getCarsetTitle();
            brand = selectCar.getBrand();
        }
    }

    /**
     * @desc 提交判断条件
     */
    protected void onCommit() {
        String carRegex = "[\\u4e00-\\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}";

        String mPhpne = mPhpneEdt.getText().toString();
        String mName = mNameEdt.getText().toString();

        if (null == vid) {
            toastMsg("请选车！");
            return;
        }

        String tempPlate = plateBtn.getText().toString() + plateEdt.getText().toString();
        if (!(tempPlate.toUpperCase()).matches(carRegex)) {
            toastMsg("请输入正确车牌号！");
            return;
        }

        if (TextUtils.isEmpty(mName.trim())) {
            toastMsg("请输入姓名！");
            return;
        }

        if (!(mPhpne.matches(CM_NUM) || mPhpne.matches(CT_NUM) || mPhpne.matches(CU_NUM))) {
            toastMsg("请输入正确的手机号！");
            return;
        }

        mSubmitBtn.setClickable(false);
        mProvider.orderSellCar(mPhpne, mName, carInfoTxt.getText().toString(), vid, carset, carsetId, brand,
                tempPlate.toUpperCase(), mRemarkEdt.getText().toString());
    }

    /**
     * @desc (预约卖车接口回调)
     * @createtime 2016/11/25 0025-13:50
     * @author wukefan
     */
    @Subscriber(tag = FrameHttpTag.ORDER_SELL_CAR)
    public void orderSellCarResult(HttpMethod method) {
        mSubmitBtn.setClickable(true);
        String errno = method.data().getString("errno");
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");

        if (!TextUtils.isEmpty(flag) && flag.equals("0")) {
            String orderNum = method.data().getString("data");
            toastMsg("提交成功~");
            resultLayout.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        } else if (flag.equals(HttpPublisher.NETWORK_ERROR)) {
            toastMsg("提交失败，请检查网络！");
        } else {
            toastMsg(msg);
        }
    }

    protected void setUserInfo() {
        if (FrameEtongApplication.getApplication().isLogin()) {
            FrameUserInfo mUserInfo = FrameEtongApplication.getApplication().getUserInfo();
            mPhpneEdt.setText(mUserInfo.getUserPhone());
            if (mUserInfo.getUserName() != null) {
                mNameEdt.setText(mUserInfo.getUserName());
            }
        }
    }

    /**
     * @desc 小写转大写
     */
    public class AllCapTransformationMethod extends ReplacementTransformationMethod {

        @Override
        protected char[] getOriginal() {
            char[] aa = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
            return aa;
        }

        @Override
        protected char[] getReplacement() {
            char[] cc = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
            return cc;
        }

    }
}
