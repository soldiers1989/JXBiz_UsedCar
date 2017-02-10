package com.etong.android.jxappusedcar.content;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.UC_HttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberFragment;
import com.etong.android.frame.user.UC_UserProvider;
import com.etong.android.frame.widget.pinnedlistview.PinnedSectionListView;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.adapter.UC_SelctBrandMenuPinnedAdapter;
import com.etong.android.jxappusedcar.bean.UC_GrandBrandItemBean;
import com.etong.android.jxappusedcar.bean.UC_SelectBrandMenuPinnedBean;
import com.etong.android.jxappusedcar.bean.UC_SelectBrand_ExtraBean;
import com.etong.android.frame.utils.L;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * 品牌中点击之后显示的菜单中的具体车辆
 * Created by Ellison.Sun on 2016/8/18.
 */
public class UC_SelectBrandMenu extends BaseSubscriberFragment {

    private Context mContext;

    protected float mDensity;
    protected int mWidth;
    protected int mHeight;
    private View view;
    public static Integer vid;          // 品牌id
    public static String brandName;     // 品牌名字
    public static String brandImage;    // 品牌图片
    private ImageProvider mImageProvider;
    private UC_UserProvider mProvider;
    private HttpPublisher mHttpPublisher;
    private DrawerLayout mDrawerLayout;
    private PinnedSectionListView allSaleLv;
    private UC_SelctBrandMenuPinnedAdapter carsetMenuAdapter;                 // 全部的适配器
    private List<UC_SelectBrandMenuPinnedBean> SourceDateList;       // 保存存放ListView中Item的数据
    private ImageView brandIv;
    private TextView menuBrandName;
    private TextView noLimitCarset;

    @Override
    protected View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mContext = getActivity();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        mDensity = dm.density;
        mWidth = dm.widthPixels;
        mHeight = dm.heightPixels;
        mImageProvider = ImageProvider.getInstance();
        view = inflater.inflate(R.layout.uc_content_menu_lv_select_carset,
                container, false);

        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(getActivity());
        mProvider = UC_UserProvider.getInstance();
        UC_UserProvider.initalize(mHttpPublisher);
        initView();

        return view;
    }
/*
  ##################################################################################################
  ##                                     初始化View的方法                                         ##
  ##################################################################################################
*/
    /**
     * 初始化操作
     */
    private void initView() {

        brandIv = (ImageView) view.findViewById(R.id.uc_content_menu_lv_brand_iv);
        menuBrandName = (TextView) view.findViewById(R.id.uc_content_menu_lv_brand_name);
        // 加载车辆品牌的图片
        mImageProvider.loadImage(brandIv, brandImage, R.mipmap.uc_select_brand_loading);
        // 设置车辆品牌的名字
        if (!TextUtils.isEmpty(brandName)) {
            menuBrandName.setText(brandName);
        }

        allSaleLv = (PinnedSectionListView) view.findViewById(R.id.uc_content_menu_carset_lv);
        initHeaderAndFooter();

        // 设置数据为空的ListView显示
        View emptyListView = LayoutInflater.from(getActivity()).inflate(R.layout.default_empty_listview, null);
        ViewGroup default_empty_content = (ViewGroup) emptyListView.findViewById(R.id.default_empty_content);
        TextView default_empty_lv_textview = (TextView) emptyListView.findViewById(R.id.default_empty_lv_textview);
        default_empty_lv_textview.setText("数据请求失败，点击重试");
        default_empty_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //刷新数据
                mProvider.getCarset(vid);
            }
        });
        ((ViewGroup) allSaleLv.getParent()).addView(emptyListView);
        allSaleLv.setEmptyView(emptyListView);

        // 设置悬浮头部的适配器
        carsetMenuAdapter = new UC_SelctBrandMenuPinnedAdapter(getActivity());
        allSaleLv.setAdapter(carsetMenuAdapter);

        allSaleLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // 如果点击的是悬浮头部， 则不进行点击事件
                if (((UC_SelectBrandMenuPinnedBean) carsetMenuAdapter.
                        getItem(position - allSaleLv.getHeaderViewsCount())).type == UC_SelectBrandMenuPinnedBean.SECTION) {
                    return;
                }
                int carsetId = ((UC_SelectBrandMenuPinnedBean) carsetMenuAdapter.getItem(position - allSaleLv.getHeaderViewsCount())).getId();
                String carsetName=((UC_SelectBrandMenuPinnedBean) carsetMenuAdapter.getItem(position - allSaleLv.getHeaderViewsCount())).getName();
                Intent i = new Intent();
                UC_SelectBrand_ExtraBean putBean = new UC_SelectBrand_ExtraBean();
                putBean.setBrandId(vid);
                putBean.setBrandName(brandName);
                putBean.setCarsetId(carsetId);
                putBean.setCarsetName(carsetName);
                i.putExtra(UC_SelectBrandActivity.SELECT_BRAND_NAME, putBean);
                ((Activity)mContext).setResult(UC_SelectBrandActivity.SELECT_BRAND_RESULT_CODE, i);
                getActivity().finish();
            }
        });
    }

    public void initHeaderAndFooter() {
        View listHeader = LayoutInflater.from(mContext).inflate(R.layout.uc_content_menu_lv_select_brand_title_item, null);
//        noLimitContent = (ViewGroup) listHeader.findViewById(R.id.uc_content_menu_lv_select_brand_title_tv_content);
        noLimitCarset = (TextView) listHeader.findViewById(R.id.uc_content_menu_lv_select_brand_title_tv_item);
        noLimitCarset.setText("不限车系");
        noLimitCarset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 上个页面需要值，传空过去
                Intent i = new Intent();
                UC_SelectBrand_ExtraBean putBean = new UC_SelectBrand_ExtraBean();
                putBean.setBrandId(vid);
                putBean.setBrandName(brandName);
                i.putExtra(UC_SelectBrandActivity.SELECT_BRAND_NAME, putBean);
                ((Activity)mContext).setResult(UC_SelectBrandActivity.SELECT_BRAND_RESULT_CODE, i);
                getActivity().finish();
            }
        });
        // 将头部添加到ListView中
        allSaleLv.addHeaderView(listHeader);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

/*
  ##################################################################################################
  ##                              使用的逻辑方法，以及对外公开的方法                              ##
  ##                                      请求数据、获取数据                                      ##
  ##################################################################################################
*/
    @Subscriber(tag = UC_HttpTag.GET_CARSET_DETAIL_BY_GRAND_ID)
    public void getCarsetDetail(HttpMethod method) {
        // 请求网络完成
        loadFinish();
        L.d("获取到所有的车辆为：", method.data().toString());
        SourceDateList = new ArrayList<UC_SelectBrandMenuPinnedBean>();

        String status = method.data().getString("status");
        String msg = method.data().getString("msg");
        if (!TextUtils.isEmpty(status) && ("true").equals(status)) {
            JSONArray jsonArr = method.data().getJSONArray("data");
            for (int i = 0; i < jsonArr.size(); i++) {
                UC_SelectBrandMenuPinnedBean mAllItem = JSON.toJavaObject(jsonArr.getJSONObject(i), UC_SelectBrandMenuPinnedBean.class);
                SourceDateList.add(mAllItem);
            }
            // 获取到数据之后设置adapter
            carsetMenuAdapter.updateDatas(SourceDateList);
        } else {
            if (!TextUtils.isEmpty(msg)) {
                mDrawerLayout.closeDrawer(Gravity.RIGHT);
                toastMsg("车辆信息暂未收录");
            }
        }
    }

    @Subscriber(tag = UC_HttpTag.POST_BRAND_TO_MENU)
    public void getCarsetName(UC_GrandBrandItemBean carsetBean) {
        this.vid = carsetBean.getF_carbrandid();        //  将获取到的id保存

        if(null!=carsetBean.getF_brand() && !TextUtils.isEmpty(carsetBean.getF_brand())){
            this.brandName = carsetBean.getF_brand();       // 获取到品牌名字
        }

        this.brandImage = carsetBean.getImage();        // 获取到品牌的ImageView

        if(!TextUtils.isEmpty(carsetBean.getImage()) && brandIv!=null) {
            // 加载车辆品牌的图片
            mImageProvider.loadImage(brandIv, brandImage, R.mipmap.uc_select_brand_loading);
        }
        if(!TextUtils.isEmpty(carsetBean.getF_brand()) && menuBrandName!=null) {
            // 设置品牌名字
            menuBrandName.setText(carsetBean.getF_brand());
        }

        mProvider.getCarset(vid);
    }

    /**
     * @desc (使用EventBus传送Drawerlayout)
     * @createtime 2016/11/2 - 16:51
     * @author xiaoxue
     */
    @Subscriber(tag="UC_SelectBrandMenu")
    public void getDrawer(DrawerLayout grand_drawerlayout){
        mDrawerLayout=grand_drawerlayout;
    }

}
