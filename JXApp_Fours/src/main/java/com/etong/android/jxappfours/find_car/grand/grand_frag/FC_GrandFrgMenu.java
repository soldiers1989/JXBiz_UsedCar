package com.etong.android.jxappfours.find_car.grand.grand_frag;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.request_init.FrameHttpUri;
import com.etong.android.frame.subscriber.BaseSubscriberFragment;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.widget.pinnedlistview.PinnedSectionListView;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.grand.adapter.FC_GrandInsalesAdapter;
import com.etong.android.jxappfours.find_car.grand.bean.FC_AllSalesListItem;
import com.etong.android.jxappfours.find_car.grand.bean.FC_InsalesListItem;
import com.etong.android.jxappfours.find_car.grand.bean.FC_InsalesTotalItem;
import com.etong.android.jxappfours.find_car.grand.carset.FC_CarsetActivity;
import com.etong.android.jxappfours.find_car.grand.provider.FC_GetInfo_Provider;

import org.simple.eventbus.Subscriber;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 品牌中点击之后显示的菜单中的具体车辆
 *
 * Created by Ellison.Sun on 2016/8/18.
 */
//public class FC_GrandFrgMenu extends Find_Car_VechileFragment {
    public class FC_GrandFrgMenu extends BaseSubscriberFragment {

    private Context mContext;

    protected float mDensity;
    protected int mWidth;
    protected int mHeight;
    private View view;
    public  static Integer vid;     // 品牌id
    private ImageProvider mImageProvider;
    private FC_GetInfo_Provider mProvider;
    private HttpPublisher mHttpPublisher;

    private String[] list = {"在售", "全部"};
    private DrawerLayout mDrawerLayout;


    PinnedSectionListView allSaleLv;
//    FC_GrandAllgoodsAdapter allAdapter;          // 全部的适配器
    FC_GrandInsalesAdapter allAdapter;          // 全部的适配器

    List<FC_InsalesTotalItem> SourceDateList;       // 保存存放ListView中Item的数据
    private RadioButton left_radiobutton;
    private RadioButton right_radiobutton;

    public FC_GrandFrgMenu() {

    }

    public FC_GrandFrgMenu(Context context, DrawerLayout drawerLayout) {
        this.mContext = context;
        this.mDrawerLayout = drawerLayout;
    }

    @Override
    protected View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        mDensity = dm.density;
        mWidth = dm.widthPixels;
        mHeight = dm.heightPixels;
        mImageProvider = ImageProvider.getInstance();
        view = inflater.inflate(R.layout.find_car_base_fragment_frg,
                container, false);

        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(getActivity());
        mProvider = FC_GetInfo_Provider.getInstance();
        mProvider.initialize(mHttpPublisher);
        initView();
        left_radiobutton.setChecked(true);
     /*   List<BaseSubscriberFragment> fragmentList=new ArrayList<>();

        FC_GrandInsaleFrg insaleFrg=new FC_GrandInsaleFrg(mContext);
//        FC_GrandInsaleFrg allFrg=new FC_GrandInsaleFrg(mContext);
        FC_GrandAllFrg allFrg=new FC_GrandAllFrg(mContext);

        fragmentList.add(insaleFrg);
        fragmentList.add(allFrg);

        getFragment(fragmentList);
        initTopButton(getActivity(),view,list);
*/
        return view;
    }


    /**
     * 初始化操作
     */
    private void initView() {
        left_radiobutton = (RadioButton)view.findViewById(R.id.find_car_left_radiobutton);
        right_radiobutton = (RadioButton)view.findViewById(R.id.find_car_right_radiobutton);
        addClickListener(view,R.id.find_car_left_radiobutton);
        addClickListener(view,R.id.find_car_right_radiobutton);

        allSaleLv =  (PinnedSectionListView) view.findViewById(R.id.fc_content_allgoods_lv);


        // 设置数据为空的ListView显示
        View emptyListView = LayoutInflater.from(getActivity()).inflate(R.layout.default_empty_listview, null);
        ViewGroup default_empty_content = (ViewGroup) emptyListView.findViewById(R.id.default_empty_content);
        TextView default_empty_lv_textview = (TextView) emptyListView.findViewById(R.id.default_empty_lv_textview);
        default_empty_lv_textview.setText("数据请求失败，点击重试");
        default_empty_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //刷新数据
                mProvider.getCarsetByGrandId(vid);
            }
        });
        ((ViewGroup)allSaleLv.getParent()).addView(emptyListView);
        allSaleLv.setEmptyView(emptyListView);




//        allAdapter = new FC_GrandAllgoodsAdapter(getActivity());
        allAdapter = new FC_GrandInsalesAdapter(getActivity());
        allSaleLv.setAdapter(allAdapter);

        allSaleLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                Toast.makeText(getActivity(), "进入车系详情界面", Toast.LENGTH_SHORT).show();

                // 如果点击的是悬浮头部， 则不进行点击事件
                if (((FC_InsalesListItem) allAdapter.
                        getItem(position - allSaleLv.getHeaderViewsCount())).type == FC_AllSalesListItem.SECTION) {
                    return;
                }

                // 跳到车型详情
                Intent toCarsetActivity = new Intent(getActivity(), FC_CarsetActivity.class);
                toCarsetActivity.putExtra("carset_name", ((FC_InsalesListItem) allAdapter.
                        getItem(position - allSaleLv.getHeaderViewsCount())));
                startActivity(toCarsetActivity);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
//        mHttpPublisher = HttpPublisher.getInstance();
//        mHttpPublisher.initialize(getActivity());
//        mProvider = FC_GetInfo_Provider.getInstance();
//        mProvider.initialize(mHttpPublisher);
//
//        List<BaseSubscriberFragment> fragmentList=new ArrayList<>();
//
//        FC_GrandInsaleFrg insaleFrg=new FC_GrandInsaleFrg(mContext);
//        FC_GrandAllFrg allFrg=new FC_GrandAllFrg(mContext);
//
//        fragmentList.add(insaleFrg);
//        fragmentList.add(allFrg);
//
//        getFragment(fragmentList);
//        initTopButton(getActivity(),view,list);
    }

//    @Override
//    public void processing_data() {
//        mProvider.getCarsetByGrandId(vid);
//    }

    /**
     * 获取到车系的名字
     * @param carsetName
     */
    @Subscriber (tag = FrameHttpTag.GET_CARSET_BY_GRAND_ID)
    public void getCarsetNameToupdateAll(String carsetName) {
        L.d("insales", carsetName);
        String encodeName="";
        try {
            encodeName = URLEncoder.encode(carsetName, "utf-8");
            if (encodeName.contains("+")) {
                encodeName.replace("+", "20%");
            }
        } catch (Exception e) {
        }

        // 开始请求网络
//        loadStart();
//         初始化事件发布者
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(mContext);

        HttpMethod httpMethod = new HttpMethod(FrameHttpUri.GetCarsetByGrandID + encodeName+".do", new HashMap());
        mHttpPublisher.sendRequest(httpMethod, FrameHttpTag.GET_CARSET_DETAIL);
    }


    @Subscriber (tag = FrameHttpTag.GET_CARSET_DETAIL)
    public void getCarsetDetail(HttpMethod method) {
        // 请求网络完成
        loadFinish();
//        if (method.data().get(""))
        L.d("获取到所有的车辆为：", method.data().toString());
        SourceDateList = new ArrayList<FC_InsalesTotalItem>();

        String errno = method.data().getString("errno");
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");

        if (flag.equals("0")) {
            JSONArray jsonArr = method.data().getJSONArray("data");
            for (int i = 0; i < jsonArr.size(); i++) {
                FC_InsalesTotalItem mAllItem = JSON.toJavaObject(jsonArr.getJSONObject(i), FC_InsalesTotalItem.class);

//                if (mAllItem.getSalestatus().equals("在售")) {
////                    mVechileOnsellList.add(mModels_Contrast_VechileType);
//                    list1.add(mModels_Contrast_VechileType);
//
//                } else if (mModels_Contrast_VechileType.getSalestatus().equals("停售")) {
////                    mVechileStopList.add(mModels_Contrast_VechileType);
//                    list2.add(mModels_Contrast_VechileType);
//                }
                SourceDateList.add(mAllItem);
            }

            // 获取到数据之后设置adapter
            allAdapter.clear();
            left_radiobutton.setChecked(true);
            allAdapter.updateListView(SourceDateList);
            allAdapter.notifyDataSetChanged();
        } else {
            if(!TextUtils.isEmpty(msg)) {
                mDrawerLayout.closeDrawer(Gravity.RIGHT);
                toastMsg("车辆信息暂未收录");
            }
        }
    }

    @Subscriber(tag = FrameHttpTag.GET_CARSET_DETAIL_BY_GRAND_ID)
    public void getCarsetName(int carsetVid) {
        vid = carsetVid;        //  将获取到的id保存
        mProvider.getCarsetByGrandId(vid);
        L.d("获取到的车系id为：", carsetVid+"");

//        mEventBus.post(carsetVid+"", FrameHttpTag.GET_CARSET_BY_GRAND_ID);
    }

    @Override
    protected void onClick(View view) {
        super.onClick(view);
        if(view.getId()==R.id.find_car_left_radiobutton){

            allAdapter.updateListView(SourceDateList);
        }else if(view.getId()==R.id.find_car_right_radiobutton){

            allAdapter.updateListView(SourceDateList);
        }
    }
}
