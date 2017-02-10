package com.etong.android.jxappfours.vechile_details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.GridView;

import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.utils.SerializableObject;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshBase;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshGridView;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.vechile_details.adapter.Find_Car_VechileDetailsImageSeeAllGridViewAdapter;
import com.etong.android.jxappfours.vechile_details.javabeam.Find_Car_VechileDetailsImageBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查看所有的GridView图片列表页
 * Created by Administrator on 2016/8/17 0017.
 */
public class Find_Car_VechileDetailsImageSeeAllActivity extends BaseSubscriberActivity {


    private TitleBar mTitleBar;
    private Find_Car_VechileDetailsImageSeeAllGridViewAdapter imageGridListAdapter;
    private PullToRefreshGridView mPullToRefreshGridView;//下拉刷新上拉加载
    private String titleName;
    private int pos;
    private String brand;

    //上拉加载的GridView的分页处理参数
    private int pagerNum;
    private int currentPager = 0;
    private int pagerSize = 30;//设置一页显示的图片个数
    private Map<Integer,List<Find_Car_VechileDetailsImageBean.PhotoListBean>> imageMap;
    private List<Find_Car_VechileDetailsImageBean.PhotoListBean> imageAllList;
    private List<Find_Car_VechileDetailsImageBean.PhotoListBean> imageList=new ArrayList<Find_Car_VechileDetailsImageBean.PhotoListBean>();
    private List<Find_Car_VechileDetailsImageBean> allList;


    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.find_car_activity_vechile_details_lookall_image);

        //得到传过来的配置
        Bundle bundle = getIntent().getExtras();
        SerializableObject serializableMap = (SerializableObject) bundle
                .get("dataMap");
        Map map = (Map) serializableMap.getObject();
        titleName = (String) map.get("titleName");
        imageAllList = (List<Find_Car_VechileDetailsImageBean.PhotoListBean>) map.get("data");
        allList = (List<Find_Car_VechileDetailsImageBean>) map.get("allData");
        pos = (int) map.get("position");
        brand=(String) map.get("brand");

        L.d("=================",imageAllList.size()+"");
        mTitleBar = new TitleBar(this);
        mTitleBar.setTitle(titleName);

        initData();
        initView();
    }

    //上拉加载的GridView的分页处理
    private void initData() {
        imageMap = new HashMap<Integer,List<Find_Car_VechileDetailsImageBean.PhotoListBean>>();
//        List<String> pagerImageList = new ArrayList<String>();
        pagerNum=imageAllList.size()/pagerSize+1;
        for(int i=0;i<pagerNum;i++){
            List  newlist = imageAllList.subList(i*pagerSize,(i==pagerNum-1?((i*pagerSize)+(imageAllList.size()%pagerSize)):((i+1)*pagerSize)));
            imageMap.put(i,newlist);
        }
        imageList.addAll(imageMap.get(currentPager));
    }

    private void initView() {

        mPullToRefreshGridView = findViewById(R.id.find_car_gv_vechile_details_image_refreshable_view,PullToRefreshGridView.class);

        imageGridListAdapter = new Find_Car_VechileDetailsImageSeeAllGridViewAdapter(brand,pos,this,imageList,allList);
        mPullToRefreshGridView.setAdapter(imageGridListAdapter);
        imageGridListAdapter.notifyDataSetChanged();
        if(pagerNum>1){
            mPullToRefreshGridView.setMode(PullToRefreshBase.Mode.PULL_UP_TO_REFRESH);
        }else{
            mPullToRefreshGridView.setMode(PullToRefreshBase.Mode.DISABLED);
        }

        mPullToRefreshGridView.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新");
        mPullToRefreshGridView.getLoadingLayoutProxy(true, false).setRefreshingLabel(
                "正在刷新...");
        mPullToRefreshGridView.getLoadingLayoutProxy(true, false).setReleaseLabel(
                "松开刷新");

        mPullToRefreshGridView.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载");
        mPullToRefreshGridView.getLoadingLayoutProxy(false, true).setRefreshingLabel(
                "正在加载更多...");
        mPullToRefreshGridView.getLoadingLayoutProxy(false, true).setReleaseLabel(
                "松开加载更多");

        mPullToRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                currentPager++;
                if(currentPager<pagerNum){
                    imageList.addAll(imageMap.get(currentPager));
                    imageGridListAdapter.notifyDataSetChanged();
                    mPullToRefreshGridView.onRefreshComplete();
                }else{
                    toastMsg("已加载全部");
                    mPullToRefreshGridView.onRefreshComplete();
                    currentPager=pagerNum;
                }
            }
        });

    }


}
