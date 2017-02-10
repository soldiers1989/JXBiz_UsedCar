package com.etong.android.jxappfours.order;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.text.method.ReplacementTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.FramePersonalLoginActivity;
import com.etong.android.frame.user.FrameUserInfo;
import com.etong.android.frame.utils.ActivitySkipUtil;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.widget.EtongNoScrollListView;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.grand.maintain_progress.Find_car_MaintainProgressActivity;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_VechileType;
import com.etong.android.jxappfours.models_contrast.main_content.MC_ChooseCarType;
import com.etong.android.jxappfours.order.adapter.Fours_Order_FoursStoreListAdapter;
import com.etong.android.jxappfours.order.javabeam.Fours_Order_FoursStoreBeam;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;


/**
 * 预约界面，包括试驾预约、订购预约、维保预约、询底（底价购车）
 * Created by Administrator on 2016/8/12 0012.
 */
public class FO_OrderActivity extends BaseSubscriberActivity implements Fours_Order_FoursStoreListAdapter.LCallBack {


    public static DrawerLayout drawer_layout_carseries;
    public static DrawerLayout drawer_layout_type;

    private TitleBar mTitleBar;
    private ScrollView mOrderScroll;
    private RelativeLayout mRLSelect;
    private LinearLayout mLLFirstSelect;
    private ImageView mImageLogo;
    private TextView mTitleName;
    private EditText mEtOrderName;
    private EditText mEtOrderPhone;
    private LinearLayout mOrderList;
    private TextView mOrderListTitle;
    private EtongNoScrollListView mLvOrderFours;
    private FrameLayout mVechileModelFrameLayout;
    private FrameLayout mCarListFrameLayout;
    private LinearLayout mLLMaintenance;
    private RelativeLayout mRLSelectAnother;
    private TextView mTitleNameAnother;

    private TextView mEtOrderPlate;
    private ImageProvider mImageProvider;

    private String mOrderName;
    private String vtitle = "";

    private String mOrderPhoneNum;
    private TextView mTxtNameTitle;
    private TextView mTxtPhoneTitle;
    public static DrawerLayout mOrderDrawerLayout;

    private Fours_Order_Provider mFours_Order_Provider;
    private String[] titleArray = {"试驾预约", "维保预约", "订购预约", "询底价"};
    private int flag;//1、 2、 3、 4
    private boolean isSelectCar;
    private String vid;
    public static int carsetId;
    private String carImage;
    private String storeId;
    private String bookCode;
    public static int level = 0;//抽屉布局的层级
    public static boolean isStart = false;
    public static String imageTitle;

    private Fours_Order_FoursStoreListAdapter mListAdapter;
    private List<Fours_Order_FoursStoreBeam> foursStoreList = new ArrayList<Fours_Order_FoursStoreBeam>();
    //    private String plateFirstName = "京沪浙苏粤鲁晋冀豫川渝辽吉黑皖鄂湘赣闽陕甘宁蒙津贵云桂琼青新藏";
    private String mOrderPlate;
    private boolean isShowNext;
    private boolean isHaveVid = false;
    private Fours_MyCarActivity activity;
    private ImageView mImgSelectAnother;
    private MC_ChooseCarType foChooseCarType;

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
    private Button mConfirmBtn;


    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.fours_order_activity_order_main);
        isStart = true;
        mFours_Order_Provider = Fours_Order_Provider.getInstance();
        mFours_Order_Provider.initialize(HttpPublisher.getInstance());
        mImageProvider = ImageProvider.getInstance();


        Intent intent = getIntent();
        flag = intent.getIntExtra("flag", 1);
        isSelectCar = intent.getBooleanExtra("isSelectCar", false);
        isShowNext = intent.getBooleanExtra("isShowNext", true);
//        flag = 2;
        mTitleBar = new TitleBar(this);
        mTitleBar.setTitle(titleArray[flag - 1]);
        initView();

        //是否从有车的界面进来
        if (isSelectCar) {
            if (flag == 3 || flag == 4) {//订购预约、询底价（底价购车）
                showSelectCarAnotherView(true);
            } else {//试驾预约、维保预约
                showSelectCarView();
                carImage = intent.getStringExtra("carImage");
                mImageProvider.loadImage(mImageLogo, carImage, R.mipmap.fours_default_img);
            }

            imageTitle = intent.getStringExtra("vTitleName");

            if (null == intent.getStringExtra("brand")) {//无车品牌
                mTitleName.setText(intent.getStringExtra("vTitleName"));
                mTitleNameAnother.setText(intent.getStringExtra("vTitleName"));
            } else {//有车品牌
                mTitleName.setText(intent.getStringExtra("brand") + intent.getStringExtra("vTitleName"));
                mTitleNameAnother.setText(intent.getStringExtra("brand") + intent.getStringExtra("vTitleName"));
            }
            mImgSelectAnother.setVisibility(View.GONE);
            vid = intent.getStringExtra("vid");
            carsetId = intent.getIntExtra("carsetId", -1);

            if (carsetId == -1) {//是否有车系id(是否可选车)
                mRLSelect.setEnabled(false);
                mRLSelectAnother.setEnabled(false);
            } else {
                mFours_Order_Provider.getVechileType(carsetId);
            }
            mFours_Order_Provider.get4sStructure(carsetId);
        } else {
            if (flag == 3 || flag == 4) {//订购预约、询底价（底价购车）
                showSelectCarAnotherView(false);
            } else {//试驾预约、维保预约
                showFirstSelectCarView();
            }
        }

        initFragmentListener();

    }


    private void initView() {
        mOrderDrawerLayout = findViewById(R.id.fours_order_drawerlayout_order, DrawerLayout.class);
        mOrderScroll = findViewById(R.id.fours_order_sv_order_scroll, ScrollView.class);
        mRLSelect = findViewById(R.id.fours_order_rl_order_selectcar, RelativeLayout.class);

        mRLSelectAnother = findViewById(R.id.fours_order_rl_order_selectcar_another, RelativeLayout.class);
        mTitleNameAnother = findViewById(R.id.fours_order_txt_order_titlename_another, TextView.class);

        mLLFirstSelect = findViewById(R.id.fours_order_ll_order_first_select, LinearLayout.class);
        mImageLogo = findViewById(R.id.fours_order_img_order_vs, ImageView.class);
        mTitleName = findViewById(R.id.fours_order_txt_order_titlename, TextView.class);
        mTxtNameTitle = findViewById(R.id.fours_order_txt_order_nametitle, TextView.class);
        mTxtPhoneTitle = findViewById(R.id.fours_order_txt_order_phonetitle, TextView.class);
        mEtOrderName = findViewById(R.id.fours_order_edit_order_name, EditText.class);
        mEtOrderPhone = findViewById(R.id.fours_order_edit_order_phone, EditText.class);
        mOrderList = findViewById(R.id.fours_order_ll_order_list, LinearLayout.class);
        mOrderListTitle = findViewById(R.id.fours_order_txt_order_list_title, TextView.class);
        mLvOrderFours = findViewById(R.id.fours_order_lv_order_fours, EtongNoScrollListView.class);

        mLLMaintenance = findViewById(R.id.fours_order_ll_maintenance_order, LinearLayout.class);
        mEtOrderPlate = findViewById(R.id.fours_order_edit_order_platenumber, TextView.class);

        mVechileModelFrameLayout = findViewById(R.id.fours_order_framlayout_order_model, FrameLayout.class);
        mCarListFrameLayout = findViewById(R.id.fours_order_framlayout_order, FrameLayout.class);

        mImgSelectAnother = findViewById(R.id.fours_order_img_another, ImageView.class);

        mConfirmBtn = findViewById(R.id.fours_order_btn_orde_confirm, Button.class);

        mOrderDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
//        mEtOrderPlate.setTransformationMethod(new AllCapTransformationMethod());


        // 设置数据为空的ListView显示
        View emptyListView = LayoutInflater.from(this).inflate(R.layout.default_empty_listview, null);
        emptyListView.setMinimumHeight(mHeight / 2);
        emptyListView.setMinimumWidth(mWidth);
        ViewGroup default_empty_content = (ViewGroup) emptyListView.findViewById(R.id.default_empty_content);
        TextView default_empty_lv_textview = (TextView) emptyListView.findViewById(R.id.default_empty_lv_textview);
        default_empty_lv_textview.setText("数据请求失败，点击重试");
        default_empty_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (carsetId != -1) {
                mFours_Order_Provider.get4sStructure(carsetId);
//                }
            }
        });
        ((ViewGroup) mLvOrderFours.getParent()).addView(emptyListView);
        mLvOrderFours.setEmptyView(emptyListView);

        if (flag == 2) {//维保预约
            mLLMaintenance.setVisibility(View.VISIBLE);
            mTxtNameTitle.setText("确认姓名 :");
            mTxtPhoneTitle.setText("确认手机号码 :");
            mEtOrderName.setHint("");
            mEtOrderPhone.setHint("");
            if (isShowNext) {
                mTitleBar.setNextButton("维保进度查询");
            }
        } else if (flag == 4 || flag == 3) {//订购预约、询底价（底价购车）
            mOrderListTitle.setText("可询价4S店");
        }

        //维保进度查询
        mTitleBar.setNextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FrameEtongApplication.getApplication().isLogin()) {
                    Intent i = new Intent(FO_OrderActivity.this, Find_car_MaintainProgressActivity.class);
                    i.putExtra("isShowNext", false);
                    startActivity(i);
                } else {
                    toastMsg("您还未登录，请登录");
                    ActivitySkipUtil.skipActivity(FO_OrderActivity.this, FramePersonalLoginActivity.class);
                }
            }
        });

        addClickListener(mLLFirstSelect);
        addClickListener(mRLSelect);
        addClickListener(mRLSelectAnother);
        addClickListener(R.id.fours_order_btn_orde_confirm);
        addClickListener(R.id.fours_order_btn_order_addcar);

        //如果用户已登录就把用户的手机号和姓名（如果用户填了姓名）显示在相应输入框内
        if (FrameEtongApplication.getApplication().isLogin()) {
            FrameUserInfo mFrameUserInfo = FrameEtongApplication.getApplication().getUserInfo();
            mEtOrderPhone.setText(mFrameUserInfo.getUserPhone());
//            mEtOrderPhone.requestFocus();
            if (null != mFrameUserInfo.getUserName() && !TextUtils.isEmpty(mFrameUserInfo.getUserName().trim())) {
                mEtOrderName.setText(mFrameUserInfo.getUserName());
                mEtOrderName.requestFocus();
            }
        }

        initListView();

    }

    private void initListView() {
        mListAdapter = new Fours_Order_FoursStoreListAdapter(this, FO_OrderActivity.this, foursStoreList);
        mLvOrderFours.setAdapter(mListAdapter);

        mLvOrderFours.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mListAdapter.setSeclection(i);
                mListAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initFragmentListener() {
        foChooseCarType = new MC_ChooseCarType(this, this, mOrderDrawerLayout, R.id.fours_order_framlayout_order);
        foChooseCarType.setNeedChecked(false);

        //一进界面就已经选中了某款车时的选车型界面
        FragmentManager manager = getSupportFragmentManager();
        // 开启事务
        FragmentTransaction transaction = manager.beginTransaction();
        Fours_Order_SelectTypeFragment fragment = new Fours_Order_SelectTypeFragment();
        transaction.add(R.id.fours_order_framlayout_order_model, fragment);
        // 提交事务
        transaction.commitAllowingStateLoss();
    }


    @Override
    protected void onClick(View v) {
        super.onClick(v);

        if (v.getId() == R.id.fours_order_ll_order_first_select) {//首次选车
            if (flag == 2) {
                if (FrameEtongApplication.getApplication().isLogin()) {
                    Intent i = new Intent(this, Fours_MyCarActivity.class);
                    i.putExtra("isOrderActivity", true);
                    startActivity(i);
                } else {
                    toastMsg("您还未登录，请登录");
                    ActivitySkipUtil.skipActivity(FO_OrderActivity.this, FramePersonalLoginActivity.class);
                }
            } else {
                mOrderDrawerLayout.openDrawer(Gravity.RIGHT);//右边
                showCarListView();
            }
        } else if (v.getId() == R.id.fours_order_rl_order_selectcar) {//试驾预约、维保预约选车
            if (flag == 2) {
                if (FrameEtongApplication.getApplication().isLogin()) {
                    Intent i = new Intent(this, Fours_MyCarActivity.class);
                    i.putExtra("isOrderActivity", true);
                    startActivity(i);
                } else {
                    toastMsg("您还未登录，请登录");
                    ActivitySkipUtil.skipActivity(FO_OrderActivity.this, FramePersonalLoginActivity.class);
                }
            } else {
                if (isSelectCar) {
                    showVechileModelView();
                    mOrderDrawerLayout.openDrawer(Gravity.RIGHT);//右边
                } else {
                    showCarListView();
                    // 默认打开当前第一个侧滑界面
                    foChooseCarType.openOneFragment();
                }
            }

        } else if (v.getId() == R.id.fours_order_rl_order_selectcar_another) {//订购预约、询底价选车
            if (isSelectCar) {
                showVechileModelView();
                mOrderDrawerLayout.openDrawer(Gravity.RIGHT);//右边
            } else {
                showCarListView();

                // 默认打开当前第一个侧滑界面
                foChooseCarType.openOneFragment();
            }

        } else if (v.getId() == R.id.fours_order_btn_orde_confirm) {//提交
            mOrderName = mEtOrderName.getText().toString();
            mOrderPhoneNum = mEtOrderPhone.getText().toString();
            mOrderPlate = mEtOrderPlate.getText().toString();
            String carRegex = "[\\u4e00-\\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}";
            if (!TextUtils.isEmpty(mOrderPhoneNum) && null != vid) {
                L.d("^^^^^^^^^^^^^^>", vid);
                if (!(mOrderPhoneNum.matches(CM_NUM) || mOrderPhoneNum.matches(CT_NUM) || mOrderPhoneNum.matches(CU_NUM))) {
                    toastMsg("请输入正确的手机号！");
                } else if (TextUtils.isEmpty(mOrderPlate) && flag == 2) {
                    toastMsg("请输入车牌号！");
                } else if (!(mOrderPlate.toUpperCase()).matches(carRegex) && flag == 2) {
                    toastMsg("请输入正确车牌号");
                } else {
                    L.d("---------->", mOrderName + "      " + mOrderPhoneNum);
                    mConfirmBtn.setClickable(false);
                    mFours_Order_Provider.putOrder(vid, mOrderPlate, mOrderPhoneNum, mOrderName, flag, storeId);
                    L.d("==================>", storeId + "");
                }
            } else if (null == vid) {
                toastMsg("请选车!");
            } else {
                toastMsg("请输入手机号!");
            }
        } else if (v.getId() == R.id.fours_order_btn_order_addcar) {//添加爱车
            if (FrameEtongApplication.getApplication().isLogin()) {
                L.d("---------->", "添加爱车");
//                Intent i = new Intent(this, Fours_MyCar_AddActivity.class);
                Intent i = new Intent(this, FM_AddActivity.class);
                i.putExtra("isOrderActivity", true);
                startActivity(i);
            } else {
                toastMsg("您还未登录，请登录");
                ActivitySkipUtil.skipActivity(FO_OrderActivity.this, FramePersonalLoginActivity.class);
            }
        }
    }


    //提交预约得到返回的数据如预约号
    @Subscriber(tag = FrameHttpTag.PUT_ID_TO_ORDER)
    protected void gCallBackData(HttpMethod method) {
        mConfirmBtn.setClickable(true);
        String flags = method.data().getString("flag");
        String error = method.data().getString("errno");

        if (flags.equals("0")) {
            bookCode = method.data().getString("data");
            if (flag == 4) {
                toastMsg("询底价成功！");
            } else {
                toastMsg("提交预约成功！");
            }
            L.d("------------------->", bookCode);
            try {
                Thread.sleep(500);
                finish();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            if (flag == 4) {
                toastMsg("询底价失败！");
            } else {
                toastMsg("提交预约失败！");
            }
        }
    }

    //获取选择的车型的数据
    @Subscriber(tag = MC_ChooseCarType.CAT_TYPE_TAG)
    protected void getSelectCar(Models_Contrast_VechileType selectCar) {
        mFours_Order_Provider.get4sStructure(selectCar.getCarset());
        carsetId = selectCar.getCarset();
        if (flag == 3 || flag == 4) {//订购预约、询底价
            mTitleNameAnother.setText(selectCar.getBrand() + selectCar.getTitle());
            mImgSelectAnother.setVisibility(View.GONE);
            showSelectCarAnotherView(true);
        } else {//试驾预约、维保预约
            showSelectCarView();
            mTitleName.setText(selectCar.getBrand() + selectCar.getTitle());
            carImage = selectCar.getImage();
            mImageProvider.loadImage(mImageLogo, carImage, R.mipmap.fours_default_img);
        }
        vid = selectCar.getVid() + "";
    }

    @Subscriber(tag = FrameHttpTag.GET_4S_STRUCTURE)
    private void get4sStructureData(HttpMethod method) {
        try {
            mOrderList.setVisibility(View.VISIBLE);
            String ptError = method.data().getString("ptError");
            String succeed = method.data().getString("succeed");
            foursStoreList.clear();
            if (!ptError.equals("PT_ERROR_SUCCESS")) {
                Fours_Order_FoursStoreBeam defaultData1 = new Fours_Order_FoursStoreBeam();
                defaultData1.setDept_id(46);
                defaultData1.setDept_name("弘高融资");
                Fours_Order_FoursStoreBeam defaultData2 = new Fours_Order_FoursStoreBeam();
                defaultData2.setDept_id(240);
                defaultData2.setDept_name("湖南云度");
                foursStoreList.add(defaultData1);
                foursStoreList.add(defaultData2);

            } else if (ptError.equals("PT_ERROR_SUCCESS")) {

                JSONArray jsonArr = method.data().getJSONArray("object");

                if (jsonArr.size() == 0) {
                    Fours_Order_FoursStoreBeam defaultData1 = new Fours_Order_FoursStoreBeam();
                    defaultData1.setDept_id(46);
                    defaultData1.setDept_name("弘高融资");
                    Fours_Order_FoursStoreBeam defaultData2 = new Fours_Order_FoursStoreBeam();
                    defaultData2.setDept_id(240);
                    defaultData2.setDept_name("湖南云度");
                    foursStoreList.add(defaultData1);
                    foursStoreList.add(defaultData2);
                }

                for (int i = 0; i < jsonArr.size(); i++) {
                    Fours_Order_FoursStoreBeam tempData = JSON.toJavaObject(jsonArr.getJSONObject(i), Fours_Order_FoursStoreBeam.class);
                    foursStoreList.add(tempData);
                }

                mListAdapter.updateDataChanged(foursStoreList);

            } else {
                toastMsg(succeed);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //显示选中的车型
    private void showSelectCarView() {
        mRLSelect.setVisibility(View.VISIBLE);
        mLLFirstSelect.setVisibility(View.GONE);
        mRLSelectAnother.setVisibility(View.GONE);
//        mOrderList.setVisibility(View.VISIBLE);
    }

    //显示选车布局
    private void showFirstSelectCarView() {
        mLLFirstSelect.setVisibility(View.VISIBLE);
        mRLSelect.setVisibility(View.GONE);
        mRLSelectAnother.setVisibility(View.GONE);
        mOrderList.setVisibility(View.GONE);
    }

    //显示试驾预约、询底价选车布局
    private void showSelectCarAnotherView(boolean isSelect) {
        mRLSelectAnother.setVisibility(View.VISIBLE);
        mLLFirstSelect.setVisibility(View.GONE);
        mRLSelect.setVisibility(View.GONE);
        if (!isSelect) {
            mOrderList.setVisibility(View.GONE);
        } else {
//            mOrderList.setVisibility(View.VISIBLE);
        }
    }


    //显示选择车型的侧滑界面
    private void showVechileModelView() {
        mVechileModelFrameLayout.setVisibility(View.VISIBLE);
        mCarListFrameLayout.setVisibility(View.GONE);
    }

    //显示选车的侧滑界面
    private void showCarListView() {
        mCarListFrameLayout.setVisibility(View.VISIBLE);
        mVechileModelFrameLayout.setVisibility(View.GONE);
    }


    //选择4S店的回调函数
    @Override
    public void answerStore(boolean isSelect, String id) {

        if (null != id) {
            L.d("---------->" + isSelect, id);
            if (isSelect) {
                storeId = id;
            } else {
                storeId = null;
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        isStart = false;
        level = 0;
    }

    @Override
    protected void onResume() {
        super.onResume();

        //如果用户已登录就把用户的手机号和姓名（如果用户填了姓名）显示在相应输入框内
        if (FrameEtongApplication.getApplication().isLogin()) {
            FrameUserInfo mFrameUserInfo = FrameEtongApplication.getApplication().getUserInfo();
            mEtOrderPhone.setText(mFrameUserInfo.getUserPhone());
//            mEtOrderPhone.requestFocus();
            if (null != mFrameUserInfo.getUserName()) {
                mEtOrderName.setText(mFrameUserInfo.getUserName());
                mEtOrderName.requestFocus();
            }
        }

        isStart = true;
        if (null != mOrderDrawerLayout && null != drawer_layout_carseries && null != drawer_layout_type) {
            mOrderDrawerLayout.closeDrawer(Gravity.RIGHT);
            drawer_layout_carseries.closeDrawer(Gravity.RIGHT);
            drawer_layout_type.closeDrawer(Gravity.RIGHT);
        }
    }

    //获取车系对应的所有车型
    @Subscriber(tag = FrameHttpTag.VECHILE_TYPE)
    protected void getVechileTypeData(HttpMethod method) {
        List<Models_Contrast_VechileType> mList = new ArrayList<Models_Contrast_VechileType>();
        String errno = method.data().getString("errno");
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");
        mList.clear();

        String errCode = method.data().getString("errCode");

        if (flag.equals("0")) {
            JSONArray jsonArr = method.data().getJSONArray("data");
            for (int i = 0; i < jsonArr.size(); i++) {
                Models_Contrast_VechileType mModels_Contrast_VechileType = JSON.toJavaObject(jsonArr.getJSONObject(i), Models_Contrast_VechileType.class);

                String tempTitle;
                String tempFullName;
                tempTitle = mModels_Contrast_VechileType.getTitle();
                tempFullName = mModels_Contrast_VechileType.getFullName();
                if (tempTitle.contains("null款")) {
                    mModels_Contrast_VechileType.setTitle(tempTitle.replace("null款", ""));
                }
                if (tempFullName.contains("null款")) {
                    mModels_Contrast_VechileType.setFullName(tempFullName.replace("null款", ""));
                }

                mList.add(mModels_Contrast_VechileType);
                if ((null == vid || vid.equals("-1")) && !isHaveVid) {
                    mTitleNameAnother.setText(mModels_Contrast_VechileType.getBrand() + mModels_Contrast_VechileType.getTitle());
                    vid = mModels_Contrast_VechileType.getVid() + "";
                    isHaveVid = true;
                }
                if (null != FO_OrderActivity.imageTitle) {
                    if (imageTitle.contains(mModels_Contrast_VechileType.getYear() + "款 " + mModels_Contrast_VechileType.getSubject())) {
                        if (vid.equals("0")) {
                            vid = mModels_Contrast_VechileType.getVid() + "";
                        }
                    }
                }
                if (mTitleNameAnother.getText().toString().equals("508")) {
                    if (vid.equals(mModels_Contrast_VechileType.getVid() + "")) {
                        mTitleNameAnother.setText(mModels_Contrast_VechileType.getBrand() + mModels_Contrast_VechileType.getTitle());
                    }
                }
                if (vtitle.contains(mModels_Contrast_VechileType.getBrand() + mModels_Contrast_VechileType.getTitle())) {
                    mImageProvider.loadImage(mImageLogo, mModels_Contrast_VechileType.getImage(), R.mipmap.fours_default_img);
                }
            }
            mEventBus.post(mList, "SelectTypeFragment");
        } else {
            toastMsg(msg);
        }
    }

    //获取选择的车型的数据
    @Subscriber(tag = "selectCarResult")
    private void getSelectVechile(Models_Contrast_VechileType selectCar) {

        mFours_Order_Provider.get4sStructure(selectCar.getCarset());
        carsetId = selectCar.getCarset();
        if (flag == 3 || flag == 4) {//订购预约、询底价
            mTitleNameAnother.setText(selectCar.getBrand() + selectCar.getTitle());
            mImgSelectAnother.setVisibility(View.GONE);
            showSelectCarAnotherView(true);
        } else {//试驾预约、维保预约
            showSelectCarView();
            mTitleName.setText(selectCar.getBrand() + selectCar.getTitle());
            carImage = selectCar.getImage();
            mImageProvider.loadImage(mImageLogo, carImage, R.mipmap.fours_default_img);
        }
        vid = selectCar.getVid() + "";
    }

    @Subscriber(tag = "plateNo")
    protected void getPlateNo(FrameUserInfo.Frame_MyCarItemBean plateNo) {
        vid = plateNo.getVid() + "";
        vtitle = plateNo.getVtitle();
        mTitleName.setText(vtitle);
        mEtOrderPlate.setText(plateNo.getPlate_no());
        mFours_Order_Provider.getVechileType(plateNo.getCarsetId());
        mFours_Order_Provider.get4sStructure(plateNo.getCarsetId());
        carsetId = plateNo.getCarsetId();
        showSelectCarView();
        isSelectCar = true;
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
