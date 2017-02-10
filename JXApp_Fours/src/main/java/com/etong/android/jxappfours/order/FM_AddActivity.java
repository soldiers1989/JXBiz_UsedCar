package com.etong.android.jxappfours.order;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.FrameUserInfo;
import com.etong.android.frame.utils.ListAdapter;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_VechileType;
import com.etong.android.jxappfours.models_contrast.main_content.MC_ChooseCarType;

import org.simple.eventbus.Subscriber;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2016/8/30 0030.
 */
public class FM_AddActivity extends BaseSubscriberActivity {

    private TitleBar mTitleBar;
    private RelativeLayout mAddCarView;
    private ImageView mImgAddCar;
    private TextView mTxtCarType;
    private Button mBtnPlate;
    private EditText mEdtPlate;
    private EditText mEdtChassis;
    private EditText mEdtEngine;
    private EditText mEdtRemark;
    private TextView mTxtBuyDate;
    private TextView mTxtInsuranceDate;

    private PopupWindow mPopup;
    private GridView mGridView;
    private String[] plateDatas = {"京", "沪", "浙", "苏", "粤", "鲁", "晋", "冀", "豫", "川", "渝", "辽", "吉", "黑", "皖", "鄂", "湘", "赣", "闽", "陕", "甘", "宁", "蒙", "津", "贵", "云", "桂", "琼", "青", "新", "藏"};
    private ListAdapter<String> mGridListAdapter;
    private LinearLayout mAddCarBtnView;

    private ImageProvider mImageProvider;
    private Fours_Order_Provider mFoursProvider;

    public static DrawerLayout drawer_layout_carseries;
    public static DrawerLayout drawer_layout_type;
    public static DrawerLayout mMyCarDrawerLayout;
    public static int level = 0;//抽屉布局的层级
    public static boolean isStart = false;
    private String vid;
    private int carsetId;
    private Calendar calendar;
    private boolean isOrderActivity;
    private String mPlate;

    // 给当前界面添加组件的中间件
    private MC_ChooseCarType addChooseCarType;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.fours_mycar_activity_add_mycar);

        mTitleBar = new TitleBar(this);
        mTitleBar.setTitle("添加爱车");
        mTitleBar.showBottomLin(false);

        mFoursProvider = Fours_Order_Provider.getInstance();
        mFoursProvider.initialize(HttpPublisher.getInstance());
        mImageProvider = ImageProvider.getInstance();
        mImageProvider.initialize(this);

        Intent i = getIntent();
        isOrderActivity = i.getBooleanExtra("isOrderActivity", false);

        initView();

        initFragmentListener();
    }

    private void initView() {
        mMyCarDrawerLayout = findViewById(R.id.fours_mycar_drawerlayout, DrawerLayout.class);

        mAddCarView = findViewById(R.id.fours_mycar_rl_mycar_add, RelativeLayout.class);
        mImgAddCar = findViewById(R.id.fours_mycar_img_mycar_add, ImageView.class);
        mTxtCarType = findViewById(R.id.fours_mycar_txt_mycar_type, TextView.class);


        mBtnPlate = findViewById(R.id.fours_mycar_btn_mycar_type, Button.class);
        mEdtPlate = findViewById(R.id.fours_mycar_edt_mycar_plate, EditText.class);
        mEdtChassis = findViewById(R.id.fours_mycar_edt_mycar_chassis, EditText.class);
        mEdtEngine = findViewById(R.id.fours_mycar_edt_mycar_engine, EditText.class);

        mTxtBuyDate = findViewById(R.id.fours_mycar_txt_mycar_buy_date, TextView.class);
        mTxtInsuranceDate = findViewById(R.id.fours_mycar_txt_mycar_insurance_date, TextView.class);

        mAddCarBtnView = findViewById(R.id.fours_mycar_rl_mycar_btns, LinearLayout.class);

        mEdtRemark = findViewById(R.id.fours_mycar_edt_mycar_remark, EditText.class);

        mMyCarDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);

        addClickListener(mBtnPlate);
        addClickListener(mTxtCarType);
        addClickListener(R.id.fours_mycar_mycar_btn_commit);
        addClickListener(R.id.fours_mycar_mycar_btn_close);
        addClickListener(R.id.fours_mycar_img_mycar_buy_date);
        addClickListener(R.id.fours_mycar_img_mycar_insurance_date);

        showAddCarView();

        mEdtPlate.setTransformationMethod(new AllCapTransformationMethod());
        mEdtChassis.setTransformationMethod(new AllCapTransformationMethod());

        mEdtPlate.addTextChangedListener(new TextWatcher() {
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
                                                         mEdtPlate.setText("");
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

        View view = LayoutInflater.from(this).inflate(R.layout.fours_mycar_popupwindow_select, null);
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        mGridView = (GridView) view.findViewById(R.id.fours_mycar_gv_mycar_plate_select);

        mGridListAdapter = new ListAdapter<String>(this, R.layout.fours_mycarr_plate_items_gv) {
            @Override
            protected void onPaint(View view, final String data, int position) {
                TextView mPlateItem = findViewById(view, R.id.fours_mycar_txt_mycar_plate_select_item, TextView.class);


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
                        mBtnPlate.setText(data);
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
    private void showPopupWindow(View v) {
//        int popupWidth = view.getMeasuredWidth();    //  获取测量后的宽度
//        int popupHeight = view.getMeasuredHeight();  //获取测量后的高度
        int[] location = new int[2];
        // 获得位置 这里的v是目标控件，就是你要放在这个v的上面还是下面
        v.getLocationOnScreen(location);
        //这里就可自定义在上方和下方了 ，这种方式是为了确定在某个位置，某个控件的左边，右边，上边，下边都可以
        mPopup.showAtLocation(v, Gravity.NO_GRAVITY, location[0] / 2, location[1] / 3 * 2 - v.getHeight() / 2 - (int) (5 * mDensity + 0.5f));
    }


    //选择日期的dialog
    private void showSelectDateDialog(final int tag) {
        Fours_MyCar_ChangeBirthDialog mChangeBirthDialog = new Fours_MyCar_ChangeBirthDialog(
                FM_AddActivity.this);

        calendar = Calendar.getInstance();
        mChangeBirthDialog.setDate(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DAY_OF_MONTH));
        mChangeBirthDialog.show();
        mChangeBirthDialog.setBirthdayListener(new Fours_MyCar_ChangeBirthDialog.OnBirthListener() {

            @Override
            public void onClick(String year, String month, String day) {
                // TODO Auto-generated method stub
                if (tag == 0) {//购买日期
                    String tempBuyData = mTxtInsuranceDate.getText().toString();
                    if (!TextUtils.isEmpty(tempBuyData) && tempBuyData.length() != 0) {
                        if (Integer.valueOf(tempBuyData.substring(0, tempBuyData.lastIndexOf("年"))) < Integer.valueOf(year.substring(0, year.length() - 1))) {
                            toastMsg("请选择正确的购买时间");
                            return;
                        }
                        if (tempBuyData.substring(0, tempBuyData.lastIndexOf("年")).equals(year.substring(0, year.length() - 1))
                                && Integer.valueOf(tempBuyData.substring(tempBuyData.lastIndexOf("年") + 1, tempBuyData.lastIndexOf("月"))) < Integer.valueOf(month.substring(0, month.length() - 1))) {
                            toastMsg("请选择正确的购买时间");
                            return;
                        }
                        if (tempBuyData.substring(0, tempBuyData.lastIndexOf("年")).equals(year.substring(0, year.length() - 1))
                                && tempBuyData.substring(tempBuyData.lastIndexOf("年") + 1, tempBuyData.lastIndexOf("月")).equals(month.substring(0, month.length() - 1))
                                && Integer.valueOf(tempBuyData.substring(tempBuyData.lastIndexOf("月") + 1, tempBuyData.lastIndexOf("日"))) < Integer.valueOf(day.substring(0, day.length() - 1))) {
                            toastMsg("请选择正确的购买时间");
                            return;
                        }
                    } else {
                        if (Integer.valueOf(year.substring(0, year.length() - 1)) > calendar.get(Calendar.YEAR)) {
                            toastMsg("请选择正确的购买时间");
                            return;
                        }
                        if (Integer.valueOf(year.substring(0, year.length() - 1)) == calendar.get(Calendar.YEAR)
                                && Integer.valueOf(month.substring(0, month.length() - 1)) > (calendar.get(Calendar.MONTH) + 1)) {
                            toastMsg("请选择正确的购买时间");
                            return;
                        }
                        if (Integer.valueOf(year.substring(0, year.length() - 1)) == calendar.get(Calendar.YEAR)
                                && Integer.valueOf(month.substring(0, month.length() - 1)) == (calendar.get(Calendar.MONTH) + 1)
                                && Integer.valueOf(day.substring(0, day.length() - 1)) > calendar.get(Calendar.DAY_OF_MONTH)) {
                            toastMsg("请选择正确的购买时间");
                            return;
                        }
                    }
                    mTxtBuyDate.setText(year + month + day);
                    return;
                }
                if (tag == 1) {//保险到期
                    String tempBuyData = mTxtBuyDate.getText().toString();
                    if (!TextUtils.isEmpty(tempBuyData) && tempBuyData.length() != 0) {
                        if (Integer.valueOf(tempBuyData.substring(0, tempBuyData.lastIndexOf("年"))) > Integer.valueOf(year.substring(0, year.length() - 1))) {
                            toastMsg("请选择正确的保险到期时间");
                            return;
                        }
                        if (tempBuyData.substring(0, tempBuyData.lastIndexOf("年")).equals(year.substring(0, year.length() - 1))
                                && Integer.valueOf(tempBuyData.substring(tempBuyData.lastIndexOf("年") + 1, tempBuyData.lastIndexOf("月"))) > Integer.valueOf(month.substring(0, month.length() - 1))) {
                            toastMsg("请选择正确的保险到期时间");
                            return;
                        }
                        if (tempBuyData.substring(0, tempBuyData.lastIndexOf("年")).equals(year.substring(0, year.length() - 1))
                                && tempBuyData.substring(tempBuyData.lastIndexOf("年") + 1, tempBuyData.lastIndexOf("月")).equals(month.substring(0, month.length() - 1))
                                && Integer.valueOf(tempBuyData.substring(tempBuyData.lastIndexOf("月") + 1, tempBuyData.lastIndexOf("日"))) > Integer.valueOf(day.substring(0, day.length() - 1))) {
                            toastMsg("请选择正确的保险到期时间");
                            return;
                        }
                    }
                    mTxtInsuranceDate.setText(year + month + day);
                    return;
                }
            }
        });
    }


    @Subscriber(tag = FrameHttpTag.ADD_MY_CAR)
    protected void qaddMyCar(HttpMethod method) {

        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");
        if (flag.equals("0")) {
//            JSONArray jsonArr = method.data().getJSONArray("data");
//            for (int i = 0; i < jsonArr.size(); i++) {
//            }
            toastMsg("添加爱车成功！");
            FrameUserInfo.Frame_MyCarItemBean mMyCarItemBean = new FrameUserInfo.Frame_MyCarItemBean();
            mMyCarItemBean.setPlate_no(mPlate.toUpperCase());
            mMyCarItemBean.setCarsetId(carsetId);
            mMyCarItemBean.setVid(Integer.valueOf(vid));
            mMyCarItemBean.setVtitle(mTxtCarType.getText().toString());

            //把我的爱车添加到缓存
            FrameUserInfo mFrameUserInfo = FrameEtongApplication.getApplication().getUserInfo();
            List<FrameUserInfo.Frame_MyCarItemBean> tempCarList = mFrameUserInfo.getMyCars();
            tempCarList.add(mMyCarItemBean);
            mFrameUserInfo.setMyCars(tempCarList);
            FrameEtongApplication.getApplication().setUserInfo(mFrameUserInfo);
            if (isOrderActivity) {
                mEventBus.post(mMyCarItemBean, "plateNo");
            }
            finish();
        } else {
            toastMsg("添加爱车失败！", msg);
        }
    }

    @Override
    protected void onClick(View v) {
        if (v.getId() == R.id.fours_mycar_btn_mycar_type) {//选择车牌号省份
            if (null == mPopup) {
                setPopupWindow();
            }
            showPopupWindow(mAddCarBtnView);
        } else if (v.getId() == R.id.fours_mycar_mycar_btn_commit) {//提交
            onCommit();
        } else if (v.getId() == R.id.fours_mycar_mycar_btn_close) {//关闭
            finish();
        } else if (v.getId() == R.id.fours_mycar_img_mycar_buy_date) {//购买日期
            showSelectDateDialog(0);
        } else if (v.getId() == R.id.fours_mycar_img_mycar_insurance_date) {//保险到期
            showSelectDateDialog(1);
        } else if (v.getId() == R.id.fours_mycar_txt_mycar_type) {//选择车型
            selectCarType();
        }
    }

    //提交判断条件
    private void onCommit() {
        String carRegex = "[\\u4e00-\\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}";

        if (mEdtPlate.getText().toString().length() != 6) {
            toastMsg("请输入完整车牌号");
            return;
        }

        String tempPlate = mBtnPlate.getText().toString() + mEdtPlate.getText().toString();
        if (!(tempPlate.toUpperCase()).matches(carRegex)) {
            toastMsg("请输入正确车牌号");
            return;
        }
        if (mEdtChassis.getText().toString().length() != 17) {
            toastMsg("请输入完整车架号");
            return;
        }
        if (mEdtEngine.getText().toString().length() != 6) {
            toastMsg("请输入完整发动机号");
            return;
        }
        if (vid == null) {
            toastMsg("请选车");
            return;
        }

        FrameUserInfo mFrameUserInfo = FrameEtongApplication.getApplication().getUserInfo();
        for (int i = 0; i < mFrameUserInfo.getMyCars().size(); i++) {
            if (mFrameUserInfo.getMyCars().get(i).getPlate_no().equals(tempPlate.toUpperCase())) {
                toastMsg("该辆车已经添加！");
                return;
            }
        }

        mPlate = mBtnPlate.getText().toString() + mEdtPlate.getText().toString();
        String mChassis = mEdtChassis.getText().toString();
        String mEngine = mEdtEngine.getText().toString();
        mFoursProvider.addMyCar(mPlate.toUpperCase(), mChassis.toUpperCase(), mEngine, vid, carsetId + "", mTxtCarType.getText().toString());
    }

    //选择车型方法
    private void selectCarType() {

        // 打开侧滑选择界面
        addChooseCarType.openOneFragment();
    }

    private void showCarView() {
        mImgAddCar.setVisibility(View.VISIBLE);
        mAddCarView.setVisibility(View.GONE);
    }

    private void showAddCarView() {
        mAddCarView.setVisibility(View.VISIBLE);
        mImgAddCar.setVisibility(View.GONE);
    }

    /**
     * @param
     * @return
     * @desc (设置当前界面添加侧滑选择车型的组件)
     * @user sunyao
     * @createtime 2016/9/29 - 10:42
     */
    private void initFragmentListener() {
        addChooseCarType = new MC_ChooseCarType(this, this, mMyCarDrawerLayout, R.id.fours_mycar_framlayout);
        addChooseCarType.setNeedChecked(false);
    }

    //获取选择的车型的数据
    @Subscriber(tag = MC_ChooseCarType.CAT_TYPE_TAG)
    private void getSelectCar(Models_Contrast_VechileType selectCar) {
        mTxtCarType.setText(selectCar.getBrand() + selectCar.getTitle());
        vid = selectCar.getVid() + "";
        carsetId = selectCar.getCarset();
        showCarView();
        mImageProvider.loadImage(mImgAddCar, selectCar.getImage(), R.mipmap.fours_carserice_loading);
    }

    //小写转大写
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
