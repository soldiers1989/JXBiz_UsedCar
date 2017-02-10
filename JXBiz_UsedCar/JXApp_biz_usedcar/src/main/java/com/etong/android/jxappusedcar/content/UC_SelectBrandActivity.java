package com.etong.android.jxappusedcar.content;

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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.UC_HttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.user.UC_UserProvider;
import com.etong.android.frame.widget.EtongNoScrollGridView;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.frame.widget.pinnedlistview.PinnedSectionListView;
import com.etong.android.frame.widget.sortlistview.CharacterParser;
import com.etong.android.frame.widget.sortlistview.EtongSideBar;
import com.etong.android.frame.widget.sortlistview.EtongSortModel;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.adapter.UC_SelectBrandHotAdapter;
import com.etong.android.jxappusedcar.adapter.UC_SelectGrandAdapter;
import com.etong.android.jxappusedcar.bean.UC_BrandBean;
import com.etong.android.jxappusedcar.bean.UC_GrandBrandItemBean;
import com.etong.android.jxappusedcar.utils.UC_GrandItemComparator;
import com.etong.android.frame.utils.L;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * Created by Ellison.Sun on 2016/8/8.
 *
 * 品牌界面
 */
public class UC_SelectBrandActivity extends BaseSubscriberActivity {
/*
  ##################################################################################################
  ##                                        类中的变量                                            ##
  ##################################################################################################
*/

    public static int SELECT_BRAND_RESULT_CODE = 201;
    public static String SELECT_BRAND_NAME = "select_brand_name";

    // 事件发布者
    private HttpPublisher mHttpPublisher;

    private PinnedSectionListView sortListView;
    private EtongSideBar sideBar;
    private TextView dialog;
    private UC_SelectGrandAdapter adapter;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private UC_GrandItemComparator pinyinComparator;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<UC_GrandBrandItemBean> SourceDateList;
    private DrawerLayout grand_drawerlayout;
    private View listHeader;        // ListView中的头部


    private List<UC_GrandBrandItemBean> mDatas;
    private UC_SelectBrandHotAdapter mListAdapter;
    private View emptyListView;
    private UC_UserProvider mProvider;
    private TextView unLimitedTv;       // 不限品牌的TextView
    private UC_BrandBean mUC_BrandBean;
    private String brandNameKey;


    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {

        setContentView(R.layout.uc_content_select_brand_activity);

        //得到买车传回来的
        Intent mIntent = getIntent();
        mUC_BrandBean = (UC_BrandBean) mIntent.getSerializableExtra(SELECT_BRAND_NAME);
        Map<String,String> brandMap=mUC_BrandBean.getBrand();
        if (null != brandMap && brandMap.size() != 0) {
            for (String key : brandMap.keySet()) {
                brandNameKey = key;
            }
        }
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(this);
        UC_UserProvider.initalize(mHttpPublisher);
        mProvider = UC_UserProvider.getInstance();

        initViews();
        initMenuSelectCar();
        // 从网络中获取到数据
        initHotBrandData();
        initAllBrandData();
    }
/*
  ##################################################################################################
  ##                                     初始化View的方法                                         ##
  ##################################################################################################
*/
    /**
     * 初始化进入菜单后，选择在售车型和所有车型的界面
     */
    private void initMenuSelectCar() {

        //侧滑界面的初始化，Fragment
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
//        UC_SelectBrandMenu selectCarMenu = new UC_SelectBrandMenu(this, grand_drawerlayout);
        UC_SelectBrandMenu selectCarMenu = new UC_SelectBrandMenu();
        transaction.replace(R.id.uc_content_select_brand_menu_content, selectCarMenu, "");
        transaction.commitAllowingStateLoss();
        mEventBus.post(grand_drawerlayout,"UC_SelectBrandMenu");
    }

    private void initViews() {
        TitleBar mTitleBar = new TitleBar(this);
        mTitleBar.setTitle("选择品牌");
        mTitleBar.showNextButton(false);

        // 初始化事件发布者
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(this);

        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new UC_GrandItemComparator();

        grand_drawerlayout = (DrawerLayout) findViewById(R.id.uc_content_select_grand_drawerlayout);
        sideBar = (EtongSideBar) findViewById(R.id.uc_content_select_brand_grand_sidrbar);
        sideBar.setBackgroundResource(R.color.white);
        dialog = (TextView) findViewById(R.id.uc_content_select_brand_dialog);
        sideBar.setTextView(dialog);

        grand_drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED); //关闭手势滑动，因为和左边的sidebar冲突
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
                        if ("热".equals(s)) {
                            sortListView.setSelection(0);
                        } else if ("*".equals(s)) {
                            sortListView.setSelection(0);
                        }   else if ("#".equals(s)) {
                            sortListView.setSelection(sortListView.getMaxScrollAmount());
                        }
                    }
                }
            }
        });

        listHeader = LayoutInflater.from(this).inflate(R.layout.uc_content_lv_header_hot_brand, null);
        // 显示的ListView
        sortListView = (PinnedSectionListView) findViewById(R.id.uc_content_select_brand_plv);

        // 设置数据为空的ListView显示
//        EmptyListView emptyListView = new EmptyListView(this);
        emptyListView = LayoutInflater.from(this).inflate(R.layout.default_empty_listview, null);
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

        // 在添加HeaderView或者FooterView之后才能设置Adapter
        adapter = new UC_SelectGrandAdapter(this);
        sortListView.setAdapter(adapter);

        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // 如果点击的是悬浮头部， 则不进行点击事件
                if (((UC_GrandBrandItemBean) adapter.
                        getItem(position - sortListView.getHeaderViewsCount())).type == UC_GrandBrandItemBean.SECTION) {
                    return;
                }

                if (position!=0) {
                    // position 包含了头部的内容
                    //这里要利用adapter.getItem(position)来获取当前position所对应的对象
                    L.d("点击的位置为：", position+"--"+((UC_GrandBrandItemBean)adapter.getItem(position - 1)).getF_carbrandid());
                    mEventBus.post(((UC_GrandBrandItemBean)adapter.getItem(position - 1)), UC_HttpTag.POST_BRAND_TO_MENU);
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

        EtongNoScrollGridView gridView = (EtongNoScrollGridView)listHeader.findViewById(R.id.uc_content_lv_header_hot_brandgrid_layout);
        mDatas = new ArrayList<UC_GrandBrandItemBean>();
        mListAdapter = new UC_SelectBrandHotAdapter(this, mDatas, grand_drawerlayout, mEventBus);
        gridView.setAdapter(mListAdapter);

        unLimitedTv = (TextView) listHeader.findViewById(R.id.uc_content_lv_select_brand_item_detail_tv);
        unLimitedTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 上个页面需要值，传空过去
                Intent i = new Intent();
                setResult(UC_SelectBrandActivity.SELECT_BRAND_RESULT_CODE, i);
                UC_SelectBrandActivity.this.finish();
            }
        });

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

/*
  ##################################################################################################
  ##                              使用的逻辑方法，以及对外公开的方法                              ##
  ##                                      请求数据、获取数据                                      ##
  ##################################################################################################
*/

    /**
     * 从网络获取数据填充所有品牌的数据
     */
    private void initAllBrandData() {
        loadStart();
        mProvider.getAllBrand();
    }

    /**
     *  从网络获取数据填充热门品牌的数据
     */
    private void initHotBrandData() {
        loadStart();
        mProvider.getHotBrand("001");
    }

    @Subscriber(tag = UC_HttpTag.GET_HOT_BRAND)
    public void fillHotBrandData(HttpMethod httpMethod) {

        String status = httpMethod.data().getString("status");
        mDatas.clear();
        if (status.equals("true")) {
            JSONArray jsonArr = httpMethod.data().getJSONArray("data");
            for (int i = 0; i < jsonArr.size(); i++) {
                UC_GrandBrandItemBean mUC_GrandBrandItemBean = JSON.toJavaObject(jsonArr.getJSONObject(i), UC_GrandBrandItemBean.class);
                mDatas.add(mUC_GrandBrandItemBean);
            }
        } else {
            // 请求数据失败都设置空视图
            ((ViewGroup)sortListView.getParent()).addView(emptyListView);
            sortListView.setEmptyView(emptyListView);
        }
        mListAdapter.updateListDatas(mDatas);

        loadFinish();
    }

    @Subscriber(tag = UC_HttpTag.GET_ALL_BRAND)
    public void fillAllBrandData(HttpMethod httpMethod) {
        List<UC_GrandBrandItemBean> listDatas = new ArrayList<UC_GrandBrandItemBean>();

        String errno = httpMethod.data().getString("errno");
        String status = httpMethod.data().getString("status");
        String msg = httpMethod.data().getString("msg");
        // 如果请求数据成功
        if (status.equals("true")) {
            JSONArray arrayData = httpMethod.data().getJSONArray("data");
            for (int i = 0; i < arrayData.size(); i++) {
                JSONObject o = (JSONObject) arrayData.get(i);
                UC_GrandBrandItemBean itemBean = new UC_GrandBrandItemBean();
                String id = o.getString("f_carbrandid");
                if(!TextUtils.isEmpty(id)) {
                    itemBean.setF_carbrandid(Integer.parseInt(o.getString("f_carbrandid")));
                }
                itemBean.setLetter(o.getString("letter"));
                itemBean.setF_brand(o.getString("f_brand"));
                itemBean.setImage(o.getString("image"));

                listDatas.add(itemBean);
            }
            SourceDateList = filterData(listDatas);
            // 根据a-z进行排序源数据
            Collections.sort(SourceDateList, pinyinComparator);

            adapter.updateListDatas(SourceDateList);
        } else {
            // 请求数据失败都设置空视图
            ((ViewGroup)sortListView.getParent()).addView(emptyListView);
            sortListView.setEmptyView(emptyListView);
        }

        for (int i=0; i<SourceDateList.size(); i++) {
            // 遍历List中的数据，和上个页面的字段进行比较
            if (!TextUtils.isEmpty(brandNameKey) &&
                    brandNameKey.equals((adapter.getItem(i).getF_brand()))) {
                sortListView.setSelection(i);

                mEventBus.post(((UC_GrandBrandItemBean)adapter.getItem(i)), UC_HttpTag.POST_BRAND_TO_MENU);
                grand_drawerlayout.openDrawer(Gravity.RIGHT);
            }
        }
        loadFinish();
    }


    /**
     * 为数据源添加数据
     * @param listData
     * @return
     */
    private List<UC_GrandBrandItemBean> filterData(List<UC_GrandBrandItemBean> listData) {

        for (int i=0; i<listData.size(); i++) {
            // 汉字转拼音
            String pinyin = characterParser.getSelling(listData.get(i).getF_brand());
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
