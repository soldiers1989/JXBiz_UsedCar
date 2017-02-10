package com.etong.android.jxappfours.find_car.grand.grand_frag;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpMethodWay;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.request_init.FrameHttpUri;
import com.etong.android.frame.subscriber.BaseSubscriberFragment;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.widget.pinnedlistview.PinnedSectionListView;
import com.etong.android.frame.widget.sortlistview.CharacterParser;
import com.etong.android.frame.widget.sortlistview.EtongSideBar;
import com.etong.android.frame.widget.sortlistview.EtongSortModel;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.grand.adapter.Find_car_AllGrandAdapter;
import com.etong.android.jxappfours.find_car.grand.adapter.Find_car_GrandBrandViewAdapter;
import com.etong.android.jxappfours.find_car.grand.adapter.Find_car_GrandTagAdapter;
import com.etong.android.jxappfours.find_car.grand.bean.Find_car_GrandBrandItemBean;
import com.etong.android.jxappfours.find_car.grand.view.FC_GrandGridView;
import com.etong.android.jxappfours.find_car.grand.view.GrandFlowTagLayout;
import com.etong.android.jxappfours.find_car.grand.view.GrandItemComparator;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ellison.Sun on 2016/8/8.
 * Created by Ellison.Sun on 2016/8/8.
 *
 * 品牌界面
 */
public class Find_car_GrandFragment extends BaseSubscriberFragment {

    public static final String GETHOTBRAND = "get_hot_brand";   // 请求热门品牌的网络TAG
    public static final String GETALLBRAND = "get_all_brand";   // 请求所有品牌的网络TAG

    // 事件发布者
    private HttpPublisher mHttpPublisher;

    private PinnedSectionListView sortListView;
    private EtongSideBar sideBar;
    private TextView dialog;
    private Find_car_AllGrandAdapter adapter;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private GrandItemComparator pinyinComparator;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<Find_car_GrandBrandItemBean> SourceDateList;
    private View view;
    private DrawerLayout grand_drawerlayout;
    private View listHeader;        // ListView中的头部


    private List<Find_car_GrandBrandItemBean> mDatas;
    private Find_car_GrandBrandViewAdapter mListAdapter;
    private View emptyListView;

    @Override
    protected View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.grand_main_content,
                container, false);
        initViews();
        initMenuSelectCar();
        // 从网络中获取到数据
        initHotBrandData();
        initAllBrandData();


        return view;
    }

    /**
     * 初始化进入菜单后，选择在售车型和所有车型的界面
     */
    private void initMenuSelectCar() {

        //侧滑界面的初始化，Fragment
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        FC_GrandFrgMenu selectCarMenu = new FC_GrandFrgMenu(getActivity(), grand_drawerlayout);
        transaction.replace(R.id.fc_grand_menu_content, selectCarMenu, "");
        transaction.commitAllowingStateLoss();

    }

    /**
     * 从网络获取数据填充所有品牌的数据
     */
    private void initAllBrandData() {
        loadStart();
        HttpMethod httpMethod = new HttpMethod(FrameHttpUri.GetAllBrand, new HashMap<>());
        httpMethod.setSetCache(true);
        httpMethod.setCacheTimeLive(1 * 60 * 60 * 24);      // 设置缓存时间为一天
        mHttpPublisher.sendRequest(httpMethod, GETALLBRAND);
    }

    /**
     *  从网络获取数据填充热门品牌的数据
     */
    private void initHotBrandData() {
        loadStart();
        HttpMethod httpMethod = new HttpMethod(FrameHttpUri.GetHotBrand, new HashMap<>());
        httpMethod.setWay(HttpMethodWay.GET);
        httpMethod.setSetCache(true);
        httpMethod.setCacheTimeLive(1 * 60 * 60 * 24);      // 设置缓存时间为一天
        mHttpPublisher.sendRequest(httpMethod, GETHOTBRAND);
    }

    private void initViews() {
        // 初始化事件发布者
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(getActivity());

        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new GrandItemComparator();

        grand_drawerlayout = (DrawerLayout) view.findViewById(R.id.grand_drawerlayout);
        sideBar = (EtongSideBar) view.findViewById(R.id.grand_sidrbar);
        sideBar.setBackgroundResource(R.color.white);
        dialog = (TextView) view.findViewById(R.id.dialog);

        sideBar.setTextView(dialog);

        grand_drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED); //关闭手势滑动，因为和左边的sidebar冲突
//        grand_drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED); //打开手势滑动
        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new EtongSideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                if (SourceDateList!=null) {
                    //该字母首次出现的位置
                    int position = adapter.getCharForPosition(s);
                    L.d("点击字母出现的位置为：", position + "----" + s);
                    if (position != -1) {
                        position = position + 1;
                        sortListView.setSelection(position);
                    } else {
                        if ("★".equals(s)) {
                            sortListView.setSelection(0);
                        } else if ("#".equals(s)) {
                            sortListView.setSelection(sortListView.getMaxScrollAmount());
                        }
                    }
                }
            }
        });

        listHeader = LayoutInflater.from(getActivity()).inflate(R.layout.grand_listview_header, null);
        // 显示的ListView
        sortListView = (PinnedSectionListView) view.findViewById(R.id.country_lvcountry);

        // 设置数据为空的ListView显示
        emptyListView = LayoutInflater.from(getActivity()).inflate(R.layout.default_empty_listview, null);
        ViewGroup default_empty_content = (ViewGroup) emptyListView.findViewById(R.id.default_empty_content);
        TextView default_empty_lv_textview = (TextView) emptyListView.findViewById(R.id.default_empty_lv_textview);
        default_empty_lv_textview.setText("数据请求失败，点击重试");
        default_empty_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 从网络中获取到数据
                initHotBrandData();
                initAllBrandData();
            }
        });
        ((ViewGroup)sortListView.getParent()).addView(emptyListView);
        sortListView.setEmptyView(emptyListView);

        // 初始化头部的内容
        initHeaderView();
        sortListView.addHeaderView(listHeader);
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // 如果点击的是悬浮头部， 则不进行点击事件
                if (((Find_car_GrandBrandItemBean) adapter.
                        getItem(position - sortListView.getHeaderViewsCount())).type == Find_car_GrandBrandItemBean.SECTION) {
                    return;
                }

                if (position!=0) {
                    // position 包含了头部的内容
                    //这里要利用adapter.getItem(position)来获取当前position所对应的对象
                    L.d("点击的位置为：", position+"--"+((Find_car_GrandBrandItemBean)adapter.getItem(position - 1)).getId());
                    mEventBus.post(((Find_car_GrandBrandItemBean)adapter.getItem(position - 1)).getId(), FrameHttpTag.GET_CARSET_DETAIL_BY_GRAND_ID);
                    // 从右侧滑到左边
                    grand_drawerlayout.openDrawer(Gravity.RIGHT);
                }
            }
        });
    }

    /**
     * 对HeaderView中的布局进行初始化的操作
     */
    private void initHeaderView() {

        GrandFlowTagLayout tagLayout = (GrandFlowTagLayout) listHeader.findViewById(R.id.grand_hottag_layout);
        Find_car_GrandTagAdapter tagAdapter = new Find_car_GrandTagAdapter(getActivity());
        tagLayout.setAdapter(tagAdapter);

        List<String> list = new ArrayList<String>();
//        list.add("0首付");
//        list.add("0利率");
//        list.add("降价");
//        list.add("节能型");
//        list.add("入门代步车");
//        list.add("7座大空间");
//        list.add("适合女性");
//        list.add("城市SUV");

        list.add("风朗");
        list.add("吉普");
        list.add("蒙迪欧");
        list.add("切诺基");
        list.add("马自达3");
        list.add("508");
        list.add("奔驰");
        list.add("凯美瑞");
        tagAdapter.onlyAddAll(list);

//        FC_GrandGridView flowTagLayout = (FC_GrandGridView) listHeader.findViewById(R.id.grand_hotgrid_layout);
//        flowTagLayout.setAdapter(new Find_car_GrandHotViewAdapter(mContext, list, grand_drawerlayout));

        FC_GrandGridView gridView = (FC_GrandGridView)listHeader.findViewById(R.id.grand_brandgrid_layout);
//        List<Find_car_GrandBrandItemBean> mDatas = new ArrayList<Find_car_GrandBrandItemBean>();
        mDatas = new ArrayList<Find_car_GrandBrandItemBean>();
//        mDatas.add(new Find_car_GrandBrandItemBean("大众", R.mipmap.hot_dazhong));
//        mDatas.add(new Find_car_GrandBrandItemBean("本田", R.mipmap.hot_bentian));
//        mDatas.add(new Find_car_GrandBrandItemBean("现代", R.mipmap.hot_xiandai));
//        mDatas.add(new Find_car_GrandBrandItemBean("丰田", R.mipmap.hot_fentian));
//        mDatas.add(new Find_car_GrandBrandItemBean("吉利汽车", R.mipmap.hot_jili));
//        mDatas.add(new Find_car_GrandBrandItemBean("福特", R.mipmap.hot_fute));
//        mDatas.add(new Find_car_GrandBrandItemBean("奥迪", R.mipmap.hot_aodi));
//        mDatas.add(new Find_car_GrandBrandItemBean("日产", R.mipmap.hot_richan));
//        mDatas.add(new Find_car_GrandBrandItemBean("哈弗", R.mipmap.hot_hafu));
//        mDatas.add(new Find_car_GrandBrandItemBean("起亚", R.mipmap.hot_qiya));
        mListAdapter = new Find_car_GrandBrandViewAdapter(getActivity(), mDatas, grand_drawerlayout, mEventBus);
        gridView.setAdapter(mListAdapter);

        grand_drawerlayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                grand_drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);      // 打开手势滑动，关闭的时候可以用手势滑动关闭
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                grand_drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED); //关闭手势滑动，因为和左边的sidebar冲突
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Subscriber(tag = GETHOTBRAND)
    public void fillHotBrandData(HttpMethod httpMethod) {

        String errno = httpMethod.data().getString("errno");
        String flag = httpMethod.data().getString("flag");
        String msg = httpMethod.data().getString("msg");
        mDatas.clear();
        if (flag.equals("0")) {
            JSONArray jsonArr = httpMethod.data().getJSONArray("data");
            for (int i = 0; i < jsonArr.size(); i++) {
                Find_car_GrandBrandItemBean mFind_car_GrandBrandItemBean = JSON.toJavaObject(jsonArr.getJSONObject(i), Find_car_GrandBrandItemBean.class);
                mDatas.add(mFind_car_GrandBrandItemBean);
            }
        } else {
            // 请求数据失败都设置空视图
            ((ViewGroup)sortListView.getParent()).addView(emptyListView);
            sortListView.setEmptyView(emptyListView);
        }
        mListAdapter.notifyDataSetChanged();

        loadFinish();
    }

    @Subscriber(tag = GETALLBRAND)
    public void fillAllBrandData(HttpMethod httpMethod) {

        List<Find_car_GrandBrandItemBean> listDatas = new ArrayList<Find_car_GrandBrandItemBean>();

        String errno = httpMethod.data().getString("errno");
        String flag = httpMethod.data().getString("flag");
        String msg = httpMethod.data().getString("msg");
        // 如果请求数据成功
        if (flag.equals("0")) {
            JSONArray arrayData = httpMethod.data().getJSONArray("data");
            for (int i = 0; i < arrayData.size(); i++) {
                JSONObject o = (JSONObject) arrayData.get(i);
                Find_car_GrandBrandItemBean itemBean = new Find_car_GrandBrandItemBean();
                String id = o.getString("id");
                if(!TextUtils.isEmpty(id)) {
                    itemBean.setId(Integer.parseInt(o.getString("id")));
                }
                itemBean.setLetter(o.getString("letter"));
                itemBean.setTitle(o.getString("title"));
                itemBean.setImage(o.getString("image"));

                listDatas.add(itemBean);
            }
            SourceDateList = filterData(listDatas);
            // 根据a-z进行排序源数据
            Collections.sort(SourceDateList, pinyinComparator);
            adapter = new Find_car_AllGrandAdapter(getActivity(), SourceDateList);
            sortListView.setAdapter(adapter);
        } else {
            // 请求数据失败都设置空视图
            ((ViewGroup)sortListView.getParent()).addView(emptyListView);
            sortListView.setEmptyView(emptyListView);
        }

        loadFinish();
    }


    /**
     * 为数据源添加数据
     * @param listData
     * @return
     */
    private List<Find_car_GrandBrandItemBean> filterData(List<Find_car_GrandBrandItemBean> listData) {

        for (int i=0; i<listData.size(); i++) {
            // 汉字转拼音
            String pinyin = characterParser.getSelling(listData.get(i).getTitle());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if(sortString.matches("[A-Z]")){
                listData.get(i).setLetter(sortString.toUpperCase());
            }else{
                listData.get(i).setLetter("#");
            }
        }
        return listData;
    }

    /**
     * 为ListView填充数据
     * @param date
     * @return
     */
    private List<EtongSortModel> filledData(String [] date){
        List<EtongSortModel> mSortList = new ArrayList<EtongSortModel>();

        for(int i=0; i<date.length; i++){
            EtongSortModel sortModel = new EtongSortModel();
            sortModel.setName(date[i]);
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if(sortString.matches("[A-Z]")){
                sortModel.setSortLetters(sortString.toUpperCase());
            }else{
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }
}
