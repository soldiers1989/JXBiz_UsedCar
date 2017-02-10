package com.etong.android.jxappfours.find_car.grand.car_config;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.FramePersonalLoginActivity;
import com.etong.android.frame.user.UsedAndNewCollectCar;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.utils.SerializableObject;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.frame.widget.pinnedlistview.PinnedSectionListView;
import com.etong.android.jxappcarfinancial.Interface.CF_InterfaceOrderType;
import com.etong.android.jxappcarfinancial.activity.CF_ApplyForActivity;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.collect_search.javabean.FC_NewCarCollectBean;
import com.etong.android.jxappfours.find_car.grand.adapter.Find_car_CarConfigAdapter;
import com.etong.android.jxappfours.find_car.grand.bean.FC_CalcuCarPriceBean;
import com.etong.android.jxappfours.find_car.grand.bean.Find_car_CarConfigListItem;
import com.etong.android.jxappfours.find_car.grand.find_car_shopcar_calc.Find_car_CalcuTotalActivity;
import com.etong.android.jxappfours.find_car.grand.parameter_detail_javabean.FC_VecgileType_Detail_air_condition;
import com.etong.android.jxappfours.find_car.grand.parameter_detail_javabean.FC_VecgileType_Detail_basicparameter;
import com.etong.android.jxappfours.find_car.grand.parameter_detail_javabean.FC_VecgileType_Detail_carbody;
import com.etong.android.jxappfours.find_car.grand.parameter_detail_javabean.FC_VecgileType_Detail_chassis;
import com.etong.android.jxappfours.find_car.grand.parameter_detail_javabean.FC_VecgileType_Detail_control;
import com.etong.android.jxappfours.find_car.grand.parameter_detail_javabean.FC_VecgileType_Detail_engine;
import com.etong.android.jxappfours.find_car.grand.parameter_detail_javabean.FC_VecgileType_Detail_external_deploy;
import com.etong.android.jxappfours.find_car.grand.parameter_detail_javabean.FC_VecgileType_Detail_gearbox;
import com.etong.android.jxappfours.find_car.grand.parameter_detail_javabean.FC_VecgileType_Detail_glass;
import com.etong.android.jxappfours.find_car.grand.parameter_detail_javabean.FC_VecgileType_Detail_high_tech;
import com.etong.android.jxappfours.find_car.grand.parameter_detail_javabean.FC_VecgileType_Detail_inside_deploy;
import com.etong.android.jxappfours.find_car.grand.parameter_detail_javabean.FC_VecgileType_Detail_lamplight;
import com.etong.android.jxappfours.find_car.grand.parameter_detail_javabean.FC_VecgileType_Detail_multimedia;
import com.etong.android.jxappfours.find_car.grand.parameter_detail_javabean.FC_VecgileType_Detail_safety_device;
import com.etong.android.jxappfours.find_car.grand.parameter_detail_javabean.FC_VecgileType_Detail_seat;
import com.etong.android.jxappfours.find_car.grand.parameter_detail_javabean.FC_VecgileType_Detail_wheelbraking;
import com.etong.android.jxappfours.find_car.grand.provider.FC_GetInfo_Provider;
import com.etong.android.jxappfours.find_car.grand.view.BottomAppoint;
import com.etong.android.jxappfours.models_contrast.common.Models_Contrast_AddVechileStyle_Method;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_Add_VechileStyle;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_VechileType;
import com.etong.android.jxappfours.models_contrast.main_content.MC_MainActivity;
import com.etong.android.jxappfours.order.FO_OrderActivity;
import com.etong.android.jxappfours.vechile_details.Find_Car_VechileDetailsImageActivity;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 车辆配置界面
 */
public class Find_car_CarConfigActivity extends BaseSubscriberActivity implements View.OnClickListener {
    private HttpPublisher mHttpPublisher;
    private FC_GetInfo_Provider mFC_GetInfo_Provider;
    private Models_Contrast_VechileType mVechileTypeDatas = new Models_Contrast_VechileType();
    private PinnedSectionListView listView;
    private List<FC_VecgileType_Detail_control> mListcontrol;
    private List<FC_VecgileType_Detail_external_deploy> mListexternal;
    private List<FC_VecgileType_Detail_high_tech> mListhigh_tech;
    private List<FC_VecgileType_Detail_inside_deploy> mListinside;
    private List<FC_VecgileType_Detail_lamplight> mListlamplight;
    private List<FC_VecgileType_Detail_engine> mListengine;
    private List<FC_VecgileType_Detail_gearbox> mListgearbox;
    private List<FC_VecgileType_Detail_chassis> mListchassis;
    private List<FC_VecgileType_Detail_basicparameter> mListbasicparameter;
    private List<FC_VecgileType_Detail_wheelbraking> mListwheelbraking;
    private List<FC_VecgileType_Detail_carbody> mListcarbody;
    private List<FC_VecgileType_Detail_glass> mListglass;
    private List<FC_VecgileType_Detail_seat> mListseat;
    private List<FC_VecgileType_Detail_air_condition> mListair_condition;
    private List<FC_VecgileType_Detail_multimedia> mListmultimedia;
    private List<FC_VecgileType_Detail_safety_device> mListsafety_device;
    private List datalist = new ArrayList();//所有参数的list
    private Find_car_CarConfigAdapter mFind_car_CarConfigAdapter;

    private TextView fc_detail_txt_config;
    private DrawerLayout carconfig_drawerlayout;        // 侧滑
    private FC_CarconfigMenu selectCarconfigMenu;       // 侧滑出来的menu
    private String carconfigItemSelect = "";
    private Button fc_detail_list_btn_comparison;

    public static boolean isCollect = false;//是否收藏
    private Button fc_detail_list_btn_collect;
    public static final String TAG = "Find_car_CarConfigActivity";
    private int isNewFlag = 1;                //1--新车 0--二手车
    private FC_NewCarCollectBean mFC_CollectBean;
    private List<FC_NewCarCollectBean> mListData = new ArrayList<>();
    private boolean isFirst = true;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {

        setContentView(R.layout.find_car_car_config);
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(this);
        mFC_GetInfo_Provider = FC_GetInfo_Provider.getInstance();
        mFC_GetInfo_Provider.initialize(mHttpPublisher);

        //得到传过来的配置
        Bundle bundle = getIntent().getExtras();
        SerializableObject serializableMap = (SerializableObject) bundle.get("dataTypeMap");
        Map map = (Map) serializableMap.getObject();
        mVechileTypeDatas = (Models_Contrast_VechileType) map.get("dataType");
        mFC_GetInfo_Provider.getVechileDetails(mVechileTypeDatas.getVid());

        initView();                     // 初始化操作
        initBottomBtn();                // 初始化底部按钮
//        initializeAdapter();            // 初始化Adapter
        initMenuSelectInfo();       // 在setContentView之前建立
    }

    /**
     * 初始化进入菜单后，选择在售车型和所有车型的界面
     */
    private void initMenuSelectInfo() {

        //侧滑界面的初始化，Fragment
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        selectCarconfigMenu = new FC_CarconfigMenu(this, carconfig_drawerlayout);
        transaction.add(R.id.find_car_config_menu_content, selectCarconfigMenu);
        transaction.commitAllowingStateLoss();

        // 监听事件
        selectCarconfigMenu.setOnItemSelectListener(new FC_CarconfigMenu.OnCarconfigItemSelectListener() {
            @Override
            public void onCarconfigItemSelect(String positionName) {
                // 判断Section名字的位置，选中
                for (int i = 0; i < mFind_car_CarConfigAdapter.getCount(); i++) {
                    if (mFind_car_CarConfigAdapter.getItem(i).type == Find_car_CarConfigListItem.SECTION) {

                        if (positionName.equals(mFind_car_CarConfigAdapter.getItem(i).tText)) {
//                            return mFind_car_CarConfigAdapter.getItem(i).listPosition;
                            listView.setSelection(mFind_car_CarConfigAdapter.getItem(i).listPosition + listView.getHeaderViewsCount());
                        }
                    }
                }
            }
        });

    }

    @Subscriber(tag = FrameHttpTag.GET_VECHILE_TYPE_DETAIL)
    public void getVechileTypeDetailData(HttpMethod method) {

        String errno = method.data().getString("errno");
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");
        String errCode = method.data().getString("errCode");

        if (flag.equals("0")) {
            JSONObject jsonArr = method.data().getJSONObject("data");

            FC_ConfigInfoUtil util = new FC_ConfigInfoUtil(jsonArr);           // 新建工具类
            datalist = util.getCompareCarInfoList();        // 获取到所有的对象，并保存到List中

            try {
                FC_VecgileType_Detail_gearbox gearbox = (FC_VecgileType_Detail_gearbox) datalist.get(6);
                FC_VecgileType_Detail_chassis chassis = (FC_VecgileType_Detail_chassis) datalist.get(7);
                fc_detail_txt_config.setText(chassis.getDrivesystem() + "/" + gearbox.getSummary());
            } catch (Exception e) {

            }

            mFind_car_CarConfigAdapter.updateListData(datalist);
            mFind_car_CarConfigAdapter.notifyDataSetChanged();
        }
//        if(!TextUtils.isEmpty(errCode) && errCode.equals("4353")){
//            fc_detail_txt_config.setText("");
//        }
    }

    /**
     * 为底部按钮添加点击事件
     */
    private void initBottomBtn() {
        BottomAppoint appoint = new BottomAppoint(this);
        appoint.setDriveOnclikListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(Find_car_CarConfigActivity.this, "试驾预约点击", Toast.LENGTH_SHORT).show();

//                Intent i = new Intent(Find_car_CarConfigActivity.this, Fours_Order_OrderActivity.class);
                Intent i = new Intent(Find_car_CarConfigActivity.this, FO_OrderActivity.class);
                i.putExtra("flag", 1);
                i.putExtra("isSelectCar", true);
                i.putExtra("vid", mVechileTypeDatas.getVid() + "");
                i.putExtra("carsetId", mVechileTypeDatas.getCarset());
                i.putExtra("carImage", mVechileTypeDatas.getImage());
                i.putExtra("vTitleName", mVechileTypeDatas.getTitle());
                i.putExtra("brand", mVechileTypeDatas.getBrand());
                startActivity(i);
            }
        });
        appoint.setOrderOnclikListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(Find_car_CarConfigActivity.this, "订购预约点击", Toast.LENGTH_SHORT).show();

//                Intent i = new Intent(Find_car_CarConfigActivity.this, Fours_Order_OrderActivity.class);
                Intent i = new Intent(Find_car_CarConfigActivity.this, FO_OrderActivity.class);
                i.putExtra("flag", 3);
                i.putExtra("isSelectCar", true);
                i.putExtra("vid", mVechileTypeDatas.getVid() + "");
                i.putExtra("carsetId", mVechileTypeDatas.getCarset());
                i.putExtra("carImage", mVechileTypeDatas.getImage());
                i.putExtra("vTitleName", mVechileTypeDatas.getTitle());
                i.putExtra("brand", mVechileTypeDatas.getBrand());
                startActivity(i);
            }
        });
        appoint.setCreditOnclikListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(Find_car_CarConfigActivity.this, "车贷申请点击", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Find_car_CarConfigActivity.this, CF_ApplyForActivity.class);
                i.putExtra("flag_tag", CF_InterfaceOrderType.VEHICLES_LOAN);
                i.putExtra("title", "车贷申请");
                startActivity(i);
                startActivity(i);
            }
        });
    }

    /**
     * 初始化的操作
     */
    private void initView() {
        TitleBar titleBar = new TitleBar(this);
        titleBar.setNextButton("对比");
        titleBar.setTitle(mVechileTypeDatas.getBrand());
        titleBar.setTitleTextColor("#000000");
        titleBar.setmTitleBarBackground("#FFFFFF");
        titleBar.setNextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 进入选择车辆对比界面
                Intent i = new Intent(Find_car_CarConfigActivity.this, MC_MainActivity.class);
                startActivity(i);
            }
        });

        carconfig_drawerlayout = (DrawerLayout) findViewById(R.id.fc_car_config_drawerlayout);
        carconfig_drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED); //关闭手势滑动，因为和左边的sidebar冲突

        listView = (PinnedSectionListView) findViewById(R.id.find_car_config_listview);

        // 添加header或者footer之前，需要在setAdapter
        initializeHeaderAndFooter();    // 初始化ListView中的Header和footer

        mFind_car_CarConfigAdapter = new Find_car_CarConfigAdapter(this, datalist);
        listView.setAdapter(mFind_car_CarConfigAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Find_car_CarConfigListItem item = (Find_car_CarConfigListItem) mFind_car_CarConfigAdapter.getItem(position - listView.getHeaderViewsCount());
                if (item != null) {

                    // 只有SECTION的item才能点击
                    if (item.type == Find_car_CarConfigListItem.SECTION) {
//                        Toast.makeText(Find_car_CarConfigActivity.this, "Item " + (position-listView.getHeaderViewsCount()) + ": " + item.tText, Toast.LENGTH_SHORT).show();

                        selectCarconfigMenu.updateSelectPosition(item.tText);
                        carconfig_drawerlayout.openDrawer(Gravity.RIGHT);       // 从右边打开侧滑菜单

                    } else if (item.type == Find_car_CarConfigListItem.ITEM) {
                        // 当点击的为Item条目时
//                        Toast.makeText(Find_car_CarConfigActivity.this, "Item " + (position-listView.getHeaderViewsCount()) + ": " + item.lText, Toast.LENGTH_SHORT).show();
                    }
                } else {
//                    Toast.makeText(Find_car_CarConfigActivity.this, "Item " + position, Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 设置DrawerLayout不能侧滑出来，但是可以侧滑关闭
        carconfig_drawerlayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                carconfig_drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);      // 打开手势滑动，关闭的时候可以用手势滑动关闭
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                carconfig_drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED); //关闭手势滑动，因为和左边的sidebar冲突
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });

    }

    /**
     * 初始化ListView中的头部
     */
    private void initializeHeaderAndFooter() {

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.find_car_detail_one_descr_title, null);
        ImageView fc_detail_one_title_iv = (ImageView) view.findViewById(R.id.fc_detail_one_title_iv);
        TextView fc_detail_image_num = (TextView) view.findViewById(R.id.fc_detail_image_num);
        fc_detail_txt_config = (TextView) view.findViewById(R.id.fc_detail_txt_config);
        TextView fc_detail_txt_cartype_title = (TextView) view.findViewById(R.id.fc_detail_txt_cartype_title);
        TextView fc_txt_guid_price = (TextView) view.findViewById(R.id.fc_txt_guid_price);
        TextView fc_txt_reference_price = (TextView) view.findViewById(R.id.fc_txt_reference_price);
        TextView fc_txt_fullName = (TextView) view.findViewById(R.id.fc_txt_fullName);

        fc_detail_list_btn_collect = (Button) view.findViewById(R.id.fc_detail_list_btn_collect);//收藏
        Button fc_detail_list_btn_calculate = (Button) view.findViewById(R.id.fc_detail_list_btn_calculate);//购车计算
        fc_detail_list_btn_comparison = (Button) view.findViewById(R.id.fc_detail_list_btn_comparison);//加入对比
        Button fc_detail_list_btn_ask = (Button) view.findViewById(R.id.fc_detail_list_btn_ask);//询底价

        ImageProvider.getInstance().loadImage(fc_detail_one_title_iv, mVechileTypeDatas.getImage(), R.mipmap.fours_carserice_loading);
        if (null == mVechileTypeDatas.getImageNum()) {
            fc_detail_image_num.setText("0");
        } else {
            fc_detail_image_num.setText(mVechileTypeDatas.getImageNum() + "");
        }

        fc_txt_guid_price.setText(mVechileTypeDatas.getPrices() + "万");
        fc_txt_reference_price.setText(mVechileTypeDatas.getPrices() + "万");


        fc_detail_txt_cartype_title.setText(mVechileTypeDatas.getTitle());
        fc_txt_fullName.setText(mVechileTypeDatas.getBrand() + mVechileTypeDatas.getCarsetTitle() + mVechileTypeDatas.getTitle());

        if (mVechileTypeDatas.getF_collectstatus().equals("1")) {
            fc_detail_list_btn_collect.setSelected(true);
            fc_detail_list_btn_collect.setText("已收藏");
        } else {
            fc_detail_list_btn_collect.setSelected(false);
            fc_detail_list_btn_collect.setText("收藏");
        }
        if (getComparisonVidList().contains(mVechileTypeDatas.getVid())) {
            fc_detail_list_btn_comparison.setSelected(true);
            fc_detail_list_btn_comparison.setText("已加入");
        } else {
            fc_detail_list_btn_comparison.setSelected(false);
            fc_detail_list_btn_comparison.setText("加入对比");
        }

        fc_detail_one_title_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(Find_car_CarConfigActivity.this, "跳转到图片界面", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(Find_car_CarConfigActivity.this, Find_Car_VechileDetailsImageActivity.class);
                i.putExtra("title", mVechileTypeDatas.getBrand() + mVechileTypeDatas.getCarsetTitle());    // 车型名
                i.putExtra("id", mVechileTypeDatas.getVid());     // 传过去车型id
                i.putExtra("carsetId", mVechileTypeDatas.getCarset());     // 传过去车系id
                i.putExtra("flag", 1);        // 传过去车型
                i.putExtra("brand", mVechileTypeDatas.getBrand());
                startActivity(i);
            }
        });

        fc_detail_list_btn_ask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Find_car_CarConfigActivity.this, Fours_Order_OrderActivity.class);
                Intent intent = new Intent(Find_car_CarConfigActivity.this, FO_OrderActivity.class);
                intent.putExtra("vid", mVechileTypeDatas.getVid() + "");
                intent.putExtra("flag", 4);
                intent.putExtra("carsetId", mVechileTypeDatas.getCarset());
                intent.putExtra("carImage", mVechileTypeDatas.getImage());
                intent.putExtra("vTitleName", mVechileTypeDatas.getTitle());
                intent.putExtra("brand", mVechileTypeDatas.getBrand());
                intent.putExtra("isSelectCar", true);
                startActivity(intent);
            }
        });

        fc_detail_list_btn_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                L.d("-------------->", fc_detail_list_btn_collect.isSelected() + "");
                if (FrameEtongApplication.getApplication().isLogin()) {
                    mFC_GetInfo_Provider.clickCollection(String.valueOf(mVechileTypeDatas.getVid()),
                            mVechileTypeDatas.getImage(), String.valueOf(mVechileTypeDatas.getImageNum()), mVechileTypeDatas.getManu(),
                            mVechileTypeDatas.getFullName(), mVechileTypeDatas.getTitle(), String.valueOf(mVechileTypeDatas.getCarset()), mVechileTypeDatas.getCarsetTitle(),
                            String.valueOf(mVechileTypeDatas.getPrices()), mVechileTypeDatas.getBrand(), TAG);//点击收藏
                    return;
                } else {
                    toastMsg("您还未登录，请登录");
                    Intent intent = new Intent();
                    intent.setClass(Find_car_CarConfigActivity.this, FramePersonalLoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        fc_detail_list_btn_comparison.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!fc_detail_list_btn_comparison.isSelected()) {
                    if (getComparisonVidList().size() >= 12) {
                        toastMsg("对比的个数已满");
                        return;
                    }
//                    Models_Contrast_Add_VechileStyle add = new Models_Contrast_Add_VechileStyle();
//                    add.setTitle(mVechileTypeDatas.getFullName());
//                    add.setId(mVechileTypeDatas.getVid());
//                    Models_Contrast_AddVechileStyle_Method.cartAdd(add);
                    Models_Contrast_AddVechileStyle_Method.cartAdd(mVechileTypeDatas);
                    fc_detail_list_btn_comparison.setSelected(true);
                    fc_detail_list_btn_comparison.setText("已加入");
                } else {
                    Models_Contrast_AddVechileStyle_Method.remove(mVechileTypeDatas.getVid());
                    fc_detail_list_btn_comparison.setSelected(false);
                    fc_detail_list_btn_comparison.setText("加入对比");
                }
            }
        });


        // 点击购车计算跳转到购车计算页面中
        fc_detail_list_btn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FC_CalcuCarPriceBean bean = new FC_CalcuCarPriceBean();
                bean.setCarName(mVechileTypeDatas.getFullName());
                bean.setCarPrice((int) ((mVechileTypeDatas.getPrices()) * 10000));

                Intent i = new Intent(Find_car_CarConfigActivity.this, Find_car_CalcuTotalActivity.class);
                i.putExtra(FrameHttpTag.TRANSFER_CAR_PRICE_INFO, bean);
                startActivity(i);
            }
        });


        // 将头布局添加到ListView中
        try {
            listView.addHeaderView(view);
        } catch (Exception e) {
        }
    }


    @Override
    public void onClick(View view) {
//        Toast.makeText(this, "Item: " + view.getTag() , Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取到对比的Item
     *
     * @return
     */
    private List<Integer> getComparisonVidList() {
        //得到车型对比缓存的信息的vid
        List<Integer> mVidList = new ArrayList<Integer>();
        List<Models_Contrast_Add_VechileStyle> mList = new ArrayList<Models_Contrast_Add_VechileStyle>();
        Map map = Models_Contrast_AddVechileStyle_Method.getAdd_VechileStyle();
        Models_Contrast_VechileType info = null;
        if (map != null) {
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                Object key = entry.getKey();
                String data = String.valueOf(map.get(Integer.valueOf(String.valueOf(key))));
                info = JSON.parseObject(data, Models_Contrast_VechileType.class);
                if (null != info) {
                    mVidList.add(info.getVid());
                }
            }
        }
        return mVidList;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isFirst) {
            if (getComparisonVidList().contains(mVechileTypeDatas.getVid())) {
                fc_detail_list_btn_comparison.setSelected(true);
                fc_detail_list_btn_comparison.setText("已加入");
            } else {
                fc_detail_list_btn_comparison.setSelected(false);
                fc_detail_list_btn_comparison.setText("加入对比");
            }

            UsedAndNewCollectCar collectNewCar = FrameEtongApplication.getApplication().getNewCarCollectCache();
            if (null != collectNewCar.getCarList() && collectNewCar.getCarList().size() != 0) {
                if (collectNewCar.getCarList().contains(mVechileTypeDatas.getVid() + "")) {
                    setCollectState(true);
                } else {
                    setCollectState(false);
                }
            }

        } else {
            isFirst = false;
        }
    }

    /**
     * @desc (设置收藏状态)
     * @createtime 2016/12/10 0010-15:56
     * @author wukefan
     */
    protected void setCollectState(boolean isCollect) {
        if (isCollect) {
            fc_detail_list_btn_collect.setText("已收藏");
            fc_detail_list_btn_collect.setSelected(true);
        } else {
            fc_detail_list_btn_collect.setText("收藏");
            fc_detail_list_btn_collect.setSelected(false);
        }
        this.isCollect = isCollect;
    }

    /**
     * @desc (点击收藏处理)
     * @createtime 2016/12/2 - 14:57
     * @author xiaoxue
     */
    @Subscriber(tag = FrameHttpTag.CLICKCOLLECTION_NEW + TAG)
    protected void clickCollection(HttpMethod method) {
        String errno = method.data().getString("errno");
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");
        String vid = (String) method.getParam().get("vid");

        if (!TextUtils.isEmpty(flag) && flag.equals("0")) {
            JSONArray data = method.data().getJSONArray("data");
            for (int i = 0; i < data.size(); i++) {
//                FC_NewCarCollectBean mUC_CollectBean = JSON.toJavaObject(data, FC_NewCarCollectBean.class);
                mFC_CollectBean = JSON.toJavaObject((JSON) data.get(i), FC_NewCarCollectBean.class);
                mListData.add(mFC_CollectBean);
            }
            //根据状态设置是否收藏到缓存
            if (mFC_CollectBean.getF_collectstatus().equals("1")) {
                setCollectCache(true, vid);//添加到缓存
                setCollectState(true);
            } else if (mFC_CollectBean.getF_collectstatus().equals("0")) {
                setCollectCache(false, vid);//移除缓存
                setCollectState(false);
            }
            toastMsg(msg);
        } else if (flag.equals(HttpPublisher.NETWORK_ERROR)) {
            if (null != msg && !"".equals(msg) && !TextUtils.isEmpty(msg)) {
                toastMsg(msg);
            }
        } else if (flag.equals(HttpPublisher.DATA_ERROR)) {
            if (null != msg && !"".equals(msg) && !TextUtils.isEmpty(msg)) {
                toastMsg(msg);
            }
        }
    }

    /**
     * @desc (设置收藏缓存)
     * @createtime 2016/12/14 0014-16:52
     * @author wukefan
     */
    protected void setCollectCache(boolean isCollect, String vid) {

        UsedAndNewCollectCar collectNewCar = FrameEtongApplication.getApplication().getNewCarCollectCache();
        if (isCollect) { //设置收藏缓存
            if (null != collectNewCar.getCarList()) {//收藏缓存有数据
                if (!collectNewCar.getCarList().contains(vid)) {
                    List<String> collectNewCarList = collectNewCar.getCarList();
                    collectNewCarList.add(vid);
                    collectNewCar.setCarList(collectNewCarList);
                }
            } else {//收藏缓存没数据
                List<String> collectList = new ArrayList<>();
                collectList.add(vid);
                collectNewCar.setCarList(collectList);
            }
        } else {//移除收藏缓存
            if (null != collectNewCar.getCarList()) {
                if (collectNewCar.getCarList().contains(vid)) {
                    List<String> collectList = collectNewCar.getCarList();
                    collectList.remove(vid);
                    collectNewCar.setCarList(collectList);
                }
            }
        }
        collectNewCar.setChanged(true);
        FrameEtongApplication.getApplication().setNewCarCollectCache(collectNewCar);
    }
}
