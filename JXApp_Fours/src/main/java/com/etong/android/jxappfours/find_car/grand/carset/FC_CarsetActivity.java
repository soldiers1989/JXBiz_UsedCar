package com.etong.android.jxappfours.find_car.grand.carset;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.UsedAndNewCollectCar;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappcarfinancial.Interface.CF_InterfaceOrderType;
import com.etong.android.jxappcarfinancial.activity.CF_ApplyForActivity;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.collect_search.javabean.FC_NewCarCollectBean;
import com.etong.android.jxappfours.find_car.grand.adapter.FC_CarsetDetailAdapter;
import com.etong.android.jxappfours.find_car.grand.bean.FC_InsalesListItem;
import com.etong.android.jxappfours.find_car.grand.config_compare.Find_car_CompareContentActivity;
import com.etong.android.jxappfours.find_car.grand.view.BottomAppoint;
import com.etong.android.jxappfours.find_car.grand.view.FC_GrandListView;
import com.etong.android.jxappfours.models_contrast.common.Models_Contrast_Provider;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_Add_VechileStyle;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_VechileType;
import com.etong.android.jxappfours.models_contrast.main_content.MC_MainActivity;
import com.etong.android.jxappfours.order.FO_OrderActivity;
import com.etong.android.jxappfours.vechile_details.Find_Car_VechileDetailsImageActivity;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 进入车系列表页
 */
public class FC_CarsetActivity extends BaseSubscriberActivity {

    private FC_GrandListView Lv;
    private Models_Contrast_Provider mModelsContrasProvider;
    private HttpPublisher mHttpPublisher;
    private FC_CarsetDetailAdapter allGoodsAdapter;
    //    private FC_CarsetItemAdapter allGoodsAdapter;
//    private List<Models_Contrast_VechileType> mVechileTypeInSaleList = new ArrayList<>();
//    private List<Models_Contrast_VechileType> mVechileTypeStopSaleList = new ArrayList<>();
    private List<Models_Contrast_VechileType> mVechileTypeInSaleList = new ArrayList<>();
    private List<Models_Contrast_VechileType> mVechileTypeStopSaleList = new ArrayList<>();
    private String imageUrl;
    private String titleName;
    private String carLevel;
    private Integer id;
    private int tag = 0;
    private int position = -1;
    private List<String> tempNewCarList = new ArrayList<>();

    TextView fc_carset_title_img_num;       // 顶部显示图片的数量


    public static final String TAG = "FC_CarsetActivity";
    public static boolean isCollect = false;//是否收藏
    public Button fc_detail_list_btn_collect;
    private FC_NewCarCollectBean mFC_CollectBean;
    private List<FC_NewCarCollectBean> mListData = new ArrayList<>();
    private boolean isFirst = true;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.find_car_detail_describe_content);

        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(this);
        mModelsContrasProvider = Models_Contrast_Provider.getInstance();
        mModelsContrasProvider.initialize(mHttpPublisher);

        initView();
        initBottomBtn();
    }

    /**
     * 为底部按钮添加点击事件
     */
    private void initBottomBtn() {
        BottomAppoint appoint = new BottomAppoint(this);
        appoint.setDriveOnclikListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(FC_CarsetActivity.this, "试驾预约点击", Toast.LENGTH_SHORT).show();

//                Intent i = new Intent(FC_CarsetActivity.this, Fours_Order_OrderActivity.class);
                Intent i = new Intent(FC_CarsetActivity.this, FO_OrderActivity.class);
                if (mVechileTypeInSaleList.size() != 0) {
                    i.putExtra("flag", 1);
                    i.putExtra("isSelectCar", true);
                    i.putExtra("vid", mVechileTypeInSaleList.get(0).getVid() + "");
                    i.putExtra("carsetId", id);
                    i.putExtra("carImage", mVechileTypeInSaleList.get(0).getImage());
                    i.putExtra("vTitleName", mVechileTypeInSaleList.get(0).getTitle());
                    i.putExtra("brand", mVechileTypeInSaleList.get(0).getBrand());
                } else if (mVechileTypeStopSaleList.size() != 0) {
                    i.putExtra("flag", 1);
                    i.putExtra("isSelectCar", true);
                    i.putExtra("vid", mVechileTypeStopSaleList.get(0).getVid() + "");
                    i.putExtra("carsetId", id);
                    i.putExtra("carImage", mVechileTypeStopSaleList.get(0).getImage());
                    i.putExtra("vTitleName", mVechileTypeStopSaleList.get(0).getTitle());
                    i.putExtra("brand", mVechileTypeStopSaleList.get(0).getBrand());
                }

                startActivity(i);
            }
        });
        appoint.setOrderOnclikListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(FC_CarsetActivity.this, "订购预约点击", Toast.LENGTH_SHORT).show();

//                Intent i = new Intent(FC_CarsetActivity.this, Fours_Order_OrderActivity.class);
                Intent i = new Intent(FC_CarsetActivity.this, FO_OrderActivity.class);
                if (mVechileTypeInSaleList.size() != 0) {
                    i.putExtra("flag", 3);
                    i.putExtra("isSelectCar", true);
                    i.putExtra("vid", mVechileTypeInSaleList.get(0).getVid() + "");
                    i.putExtra("carsetId", id);
                    i.putExtra("carImage", mVechileTypeInSaleList.get(0).getImage());
                    i.putExtra("vTitleName", mVechileTypeInSaleList.get(0).getTitle());
                    i.putExtra("brand", mVechileTypeInSaleList.get(0).getBrand());
                    startActivity(i);
                } else if (mVechileTypeStopSaleList.size() != 0) {
                    i.putExtra("flag", 3);
                    i.putExtra("isSelectCar", true);
                    i.putExtra("vid", mVechileTypeStopSaleList.get(0).getVid() + "");
                    i.putExtra("carsetId", id);
                    i.putExtra("carImage", mVechileTypeStopSaleList.get(0).getImage());
                    i.putExtra("vTitleName", mVechileTypeStopSaleList.get(0).getTitle());
                    i.putExtra("brand", mVechileTypeStopSaleList.get(0).getBrand());
                }
            }
        });

        /**
         * TODO 点击车贷申请
         */
        appoint.setCreditOnclikListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(FC_CarsetActivity.this, "车贷申请点击", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(FC_CarsetActivity.this, CF_ApplyForActivity.class);
                i.putExtra("flag_tag", CF_InterfaceOrderType.VEHICLES_LOAN);
                i.putExtra("title", "车贷申请");
                startActivity(i);
            }
        });
    }

    /**
     * 初始化操作
     */
    private void initView() {
        // 设置TitleBar
        TitleBar titleBar = new TitleBar(this);
        titleBar.setmTitleBarBackground("#FFFFFF");
        titleBar.setTitleTextColor("#000000");
        titleBar.setNextButton("对比");

        titleBar.setNextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 进入选择车辆对比界面
                Intent i = new Intent(FC_CarsetActivity.this, MC_MainActivity.class);
                startActivity(i);
            }
        });

        // 从上个界面获取到值转换成Javabean
        Intent intent = getIntent();
        FC_InsalesListItem insalesListItem = (FC_InsalesListItem) intent.getSerializableExtra("carset_name");

        ImageView fc_carset_title_iv = findViewById(R.id.fc_carset_title_iv, ImageView.class);
        TextView fc_carset_title_carlevel = findViewById(R.id.fc_carset_title_carlevel, TextView.class);
        ViewGroup configContent = findViewById(R.id.find_car_detail_list_descr_title_config_content, ViewGroup.class);      // 参数配件点击按钮
        fc_carset_title_img_num = findViewById(R.id.fc_carset_title_img_num, TextView.class);
        final TextView stopsales = findViewById(R.id.find_car_carset_tv_stopsales, TextView.class);
        final View stopView = findViewById(R.id.find_car_carset_view_stopsales, View.class);
        final TextView insales = findViewById(R.id.find_car_carset_tv_insales, TextView.class);
        final View insalesView = findViewById(R.id.find_car_carset_view_insales, View.class);

        TextView fc_carset_full_name_tv = findViewById(R.id.fc_carset_full_name_tv, TextView.class);        // 下面显示的名字
        TextView fc_carset_guid_price_tv = findViewById(R.id.fc_carset_guid_price_tv, TextView.class);      // 厂商指导价
        if (insalesListItem != null) {
            titleBar.setTitle(insalesListItem.getpTitle());                                       // 标题栏显示的名字
            ImageProvider.getInstance().loadImage(fc_carset_title_iv, insalesListItem.getImage(), R.mipmap.fours_carserice_loading);  // 图片显示
            fc_carset_full_name_tv.setText(insalesListItem.getFullName());                          // 汽车全称

            fc_carset_guid_price_tv.setText(FC_OutFormatUtil.setPriceValue(insalesListItem.getMinguide()
                    , insalesListItem.getMaxguide()));   // 最高、最低指导价

            fc_carset_title_carlevel.setText(FC_OutFormatUtil.setCarLelOutValue(insalesListItem.getCarlevel()
                    , insalesListItem.getMinOut(), insalesListItem.getMaxOut()));

            id = insalesListItem.getId();                   // 车辆ID
            titleName = insalesListItem.getFullName();      // 车辆全称

            // 在加载数据的时候显示提示信息
            loadStart(null, 0);
            mModelsContrasProvider.getVechileType(id);//请求车型列表页
        }


        insales.setSelected(true);
        Lv = (FC_GrandListView) findViewById(R.id.lv_test);

       /* // 设置数据为空的ListView显示
        View emptyListView = LayoutInflater.from(this).inflate(R.layout.default_empty_listview, null);
        ViewGroup default_empty_content = (ViewGroup) emptyListView.findViewById(R.id.default_empty_content);
        TextView default_empty_lv_textview = (TextView) emptyListView.findViewById(R.id.default_empty_lv_textview);
        default_empty_lv_textview.setText("暂无内容");
       *//* default_empty_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //刷新数据
                mModelsContrasProvider.getVechileType(vid);//请求车型列表页
            }
        });*//*
        ((ViewGroup)Lv.getParent()).addView(emptyListView);
        Lv.setEmptyView(emptyListView);*/


        allGoodsAdapter = new FC_CarsetDetailAdapter(this);
        Lv.setAdapter(allGoodsAdapter);

        //点击listview item的回调
        allGoodsAdapter.setmItemOnClickListener(new FC_CarsetDetailAdapter.ItemOnClickListener() {
            @Override
            public void itemOnClickListener(int position) {
                fc_detail_list_btn_collect = (Button) Lv.getChildAt(position).findViewById(R.id.fc_detail_list_btn_collect);
                FC_CarsetActivity.this.position = position;
            }
        });

        Lv.setFocusable(false);         // 不让ListView占在最上面
        Lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(FC_CarsetActivity.this, "进入车型详情界面", Toast.LENGTH_SHORT).show();

//                Intent toCarsetActivity = new Intent(getActivity(), FC_CarsetActivity.class);
//                startActivity(toCarsetActivity);
            }
        });
        insales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopsales.setSelected(false);
                stopView.setVisibility(View.INVISIBLE);
                insales.setSelected(true);
                insalesView.setVisibility(View.VISIBLE);

                //  使得数据进行变化, 在售页面
                allGoodsAdapter.updateListView(mVechileTypeInSaleList);
//                allGoodsAdapter.updateDataChanged(mVechileTypeInSaleList);
            }
        });

        stopsales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopsales.setSelected(true);
                stopView.setVisibility(View.VISIBLE);
                insales.setSelected(false);
                insalesView.setVisibility(View.INVISIBLE);
                //  使得数据进行变化, 停售页面
                allGoodsAdapter.updateListView(mVechileTypeStopSaleList);
//                allGoodsAdapter.updateDataChanged(mVechileTypeStopSaleList);
            }
        });

        // 获取到车系列表中所有车型的ID，跳转到车辆配置对比界面
        configContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mVechileTypeInSaleList != null && mVechileTypeInSaleList.size() > 0) {
                    ArrayList<Models_Contrast_Add_VechileStyle> tempId = new ArrayList<Models_Contrast_Add_VechileStyle>();
                    for (int i = 0; i < mVechileTypeInSaleList.size(); i++) {
                        Models_Contrast_Add_VechileStyle vechileStyle = new Models_Contrast_Add_VechileStyle();
                        vechileStyle.setId(((Models_Contrast_VechileType) mVechileTypeInSaleList.get(i)).getVid());
                        vechileStyle.setTitle(((Models_Contrast_VechileType) mVechileTypeInSaleList.get(i)).getTitle());
                        vechileStyle.setChecked(true);
                        tempId.add(vechileStyle);
                    }

                    // 跳转到车辆配置对比界面
                    Intent i = new Intent(FC_CarsetActivity.this, Find_car_CompareContentActivity.class);
                    i.putParcelableArrayListExtra(Find_car_CompareContentActivity.FIND_CAR_COMPARE_ALL_ITEM, tempId);
                    startActivity(i);
                }
            }
        });
        addClickListener(fc_carset_title_iv);
    }

    @Override
    protected void onClick(View view) {

        if (view.getId() == R.id.fc_carset_title_iv) {
//            Toast.makeText(FC_CarsetActivity.this, "跳转到图片界面", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(FC_CarsetActivity.this, Find_Car_VechileDetailsImageActivity.class);
            i.putExtra("title", titleName);    // 车系名
            i.putExtra("carsetId", id);     // 传过去车系id
            i.putExtra("flag", 0);        // 传过去车系
            if (mVechileTypeInSaleList.size() != 0) {
                i.putExtra("brand", mVechileTypeInSaleList.get(0).getBrand());
            } else if (mVechileTypeStopSaleList.size() != 0) {
                i.putExtra("brand", mVechileTypeStopSaleList.get(0).getBrand());
            }
            startActivity(i);
        }
    }


    @Subscriber(tag = FrameHttpTag.VECHILE_TYPE)
    protected void getVechileTypeData(HttpMethod method) {
        // 在加载完数据后隐藏
        loadFinish();
        String errno = method.data().getString("errno");
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");
        int picNum = 0;
        if (flag.equals("0")) {
            mVechileTypeInSaleList.clear();
            mVechileTypeStopSaleList.clear();
            JSONArray jsonArr = method.data().getJSONArray("data");
            for (int i = 0; i < jsonArr.size(); i++) {
                Models_Contrast_VechileType mModels_Contrast_VechileType = JSON.toJavaObject(jsonArr.getJSONObject(i), Models_Contrast_VechileType.class);
                // 不要让程序挂掉，继续执行
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

                try {
                    picNum = picNum + mModels_Contrast_VechileType.getImageNum();
                } catch (Exception e) {

                }
                if (mModels_Contrast_VechileType.getSalestatus().equals("在售")) {
                    mVechileTypeInSaleList.add(mModels_Contrast_VechileType);
                } else if (mModels_Contrast_VechileType.getSalestatus().equals("停售")) {
                    mVechileTypeStopSaleList.add(mModels_Contrast_VechileType);
                }
            }
            Collections.sort(mVechileTypeInSaleList, new MyComp());
            Collections.sort(mVechileTypeStopSaleList, new MyComp());
            allGoodsAdapter.updateListView(mVechileTypeInSaleList);
        } else {
            toastMsg(msg);
        }
        fc_carset_title_img_num.setText(picNum + "");
    }

//    /**
//     * 获取到车系图片的数量
//     *
//     * @param httpMethod
//     */
//    @Subscriber(tag = FrameHttpTag.GET_CARSET_PIC_NUM)
//    public void getCarsetPicNum(HttpMethod httpMethod) {
//        String errno = httpMethod.data().getString("errno");
//        String flag = httpMethod.data().getString("flag");
//        String msg = httpMethod.data().getString("msg");
//
//        int picNum = 0;
//        if (flag.equals("0")) {
//            JSONArray jsonArr = httpMethod.data().getJSONArray("data");
//            for (int i = 0; i < jsonArr.size(); i++) {
//                // 获取到每一个数组
//                JSONObject o = (JSONObject) jsonArr.get(i);
//                JSONArray j = (JSONArray) o.get("photoList");
//
//                picNum = picNum + j.size();
//            }
//
//            allGoodsAdapter.updateDataChanged(mVechileTypeInSaleList);
//        } else {
//            toastMsg(msg);
//        }
//
//        fc_carset_title_img_num.setText(picNum + "");
//    }

    public class MyComp implements Comparator<Models_Contrast_VechileType> {
        @Override
        public int compare(Models_Contrast_VechileType o1, Models_Contrast_VechileType o2) {
            return o1.getOutputVol().compareTo(o2.getOutputVol());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 在加载数据的时候显示提示信息
        if (!isFirst) {
            if (null != FrameEtongApplication.getApplication().getNewCarCollectCache().getCarList()
                    && FrameEtongApplication.getApplication().getNewCarCollectCache().getCarList().size() != 0
                    && FrameEtongApplication.getApplication().getNewCarCollectCache().isChanged()) {

                if (!(tempNewCarList.size() == FrameEtongApplication.getApplication().getNewCarCollectCache().getCarList().size()
                        && tempNewCarList.containsAll(FrameEtongApplication.getApplication().getNewCarCollectCache().getCarList()))) {

                    mModelsContrasProvider.getVechileType(id);//请求车型列表页
                    loadStart(null, 0);
                    tempNewCarList = FrameEtongApplication.getApplication().getNewCarCollectCache().getCarList();
                }
            }
            allGoodsAdapter.notifyDataSetChanged();
        } else {
            isFirst = false;
        }
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
                mFC_CollectBean = JSON.toJavaObject((JSON) data.get(i), FC_NewCarCollectBean.class);
                mListData.add(mFC_CollectBean);
            }
            //设置收藏状态
            if (position != -1) {
                if (mFC_CollectBean.getF_collectstatus().equals("1")) {
                    setCollectCache(true, vid);
                    setCollectState(true);
                } else if (mFC_CollectBean.getF_collectstatus().equals("0")) {
                    setCollectCache(false, vid);
                    setCollectState(false);
                }
                allGoodsAdapter.setCollectState(position, mFC_CollectBean.getF_collectstatus());
            }
            toastMsg(msg);
        } else if (flag.equals(HttpPublisher.NETWORK_ERROR)) {
            if (null != msg && !"".equals(msg) && !TextUtils.isEmpty(msg)) {
                toastMsg(msg);
            } else {
                toastMsg("收藏失败");
            }
        } else if (flag.equals(HttpPublisher.DATA_ERROR)) {
            if (null != msg && !"".equals(msg) && !TextUtils.isEmpty(msg)) {
                toastMsg(msg);
            } else {
                toastMsg("收藏失败");
            }
        }
    }


    /**
     * @desc (设置收藏缓存)
     * @createtime 2016/12/14 0014-16:52
     * @author wukefan
     */
    private void setCollectCache(boolean isCollect, String vid) {

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
        tempNewCarList = FrameEtongApplication.getApplication().getNewCarCollectCache().getCarList();

    }


    /**
     * @desc (设置收藏状态)
     * @createtime 2016/12/10 0010-15:56
     * @author wukefan
     */
    private void setCollectState(boolean isCollect) {
        if (isCollect) {
            fc_detail_list_btn_collect.setText("已收藏");
            fc_detail_list_btn_collect.setSelected(true);
        } else {
            fc_detail_list_btn_collect.setText("收藏");
            fc_detail_list_btn_collect.setSelected(false);
        }


        this.isCollect = isCollect;
    }
}
