package com.etong.android.jxbizusedcar.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.UC_HttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.user.UC_UserProvider;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.frame.widget.pinnedlistview.PinnedSectionListView;
import com.etong.android.frame.widget.sortlistview.CharacterParser;
import com.etong.android.frame.widget.sortlistview.EtongSideBar;
import com.etong.android.jxbizusedcar.R;
import com.etong.android.jxbizusedcar.adapter.UC_AlwaysSelectAdapter;
import com.etong.android.jxbizusedcar.adapter.UC_RegionCity_Adapter;
import com.etong.android.jxbizusedcar.bean.UC_CityName;
import com.etong.android.jxbizusedcar.bean.UC_ProvName;
import com.etong.android.jxbizusedcar.bean.UC_RegionSel_ItemBean;
import com.etong.android.jxbizusedcar.utils.UC_CitySpUtils;
import com.etong.android.jxbizusedcar.utils.UC_RegionSelct_Comparator;
import com.etong.android.jxbizusedcar.widget.UC_AlwaysSelectCity;

import org.apache.http.util.EncodingUtils;
import org.simple.eventbus.Subscriber;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UC_Region_SelectActivity extends BaseSubscriberActivity {
/*
  ##################################################################################################
  ##                                        类中的变量                                            ##
  ##################################################################################################
*/

    public static final String UC_POST_CITYS_TO_MENU = "post_citys_to_menu";
    private EtongSideBar sideBar;           // 右侧导航滑动条
    private TextView dialog;
    private View listHeader;
    private PinnedSectionListView pinnedLv;                 // 城市列表的LV
    private UC_AlwaysSelectAdapter alwaysSelectAdapter;     // 常选城市的Adapter
    private List<UC_RegionSel_ItemBean> SourceDateList;     // 放入其中的item
    private UC_RegionCity_Adapter adapter;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private UC_RegionSelct_Comparator pinyinComparator;
    public static int UC_REGION_SELECT_RESULT_CODE = 401;
    private UC_UserProvider mProvider;
    private DrawerLayout cityDrawerLayout;
    private List<UC_ProvName> listDatas;
    private List<UC_ProvName> listProvins;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.uc_activity_uc_region_select_content);

        initView();
        initMenuSelectCar();    // 初始化侧滑
        initData();
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
        UC_SelectCityMenu selectCarMenu = new UC_SelectCityMenu();
        transaction.replace(R.id.uc_activity_uc_region_select_content_menu, selectCarMenu, "");
        transaction.commitAllowingStateLoss();

        mEventBus.post(cityDrawerLayout, "UC_SelectCityMenu");

        // 侧滑点击事件处理
        cityDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                cityDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);      // 打开手势滑动，关闭的时候可以用手势滑动关闭
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                cityDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED); //关闭手势滑动，因为和右边的sidebar冲突
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
    }

    /**
     * 请使用快捷模板生成注释
     */
    private void initView() {

        TitleBar mTitleBar = new TitleBar(this);
        mTitleBar.setTitle("选择地区");
        mTitleBar.showNextButton(false);

        // 实例化接口请求类
        mProvider = UC_UserProvider.getInstance();
        mProvider.initalize(HttpPublisher.getInstance());

        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new UC_RegionSelct_Comparator();

        cityDrawerLayout = (DrawerLayout) findViewById(R.id.uc_region_select_drawerlayout);       // 侧滑栏
        cityDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
        pinnedLv = (PinnedSectionListView) findViewById(R.id.uc_pinnedLv_region_select);
        sideBar = (EtongSideBar) findViewById(R.id.uc_pinnedLv_region_select_grand_sidrbar);
        sideBar.setBackgroundResource(R.color.white);
        dialog = (TextView) findViewById(R.id.uc_pinnedLv_region_select_txtdialog);

        // 初始化头部内容，必须在setAdapter之前执行
        initHeaderAndFooter();

        adapter = new UC_RegionCity_Adapter(this);
        pinnedLv.setAdapter(adapter);

        sideBar.setTextView(dialog);
        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new EtongSideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                if (SourceDateList != null) {
                    //该字母首次出现的位置
                    int position = adapter.getCharForPosition(s);
                    L.d("点击字母出现的位置为：", position + "----" + s);
                    if (position != -1) {
                        position = position + 1;
                        pinnedLv.setSelection(position);
                    } else {
                        if ("★".equals(s)) {
                            pinnedLv.setSelection(0);
                        } else if ("#".equals(s)) {
                            pinnedLv.setSelection(pinnedLv.getMaxScrollAmount());
                        }
                    }
                }
            }
        });

        pinnedLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // 如果点击的是悬浮头部， 则不进行点击事件
                if (((UC_RegionSel_ItemBean) adapter.
                        getItem(position - pinnedLv.getHeaderViewsCount())).type == UC_RegionSel_ItemBean.SECTION) {
                    return;
                }

                if (position != 0) {
                    // position 包含了头部的内容
                    //这里要利用adapter.getItem(position)来获取当前position所对应的对象
                    L.d("点击的位置为：", position + "--" + adapter.getItem(position - 1).getId());
                    int proId = adapter.getItem(position - 1).getId();
                    for (int i = 0; i < listProvins.size(); i++) {
                        if (proId == Integer.parseInt(listProvins.get(i).getProv_id())) {
                            mEventBus.post(listProvins.get(i).getListCity(), UC_Region_SelectActivity.UC_POST_CITYS_TO_MENU);
                        }
                    }
                    // 从右侧滑到左边
                    cityDrawerLayout.openDrawer(Gravity.RIGHT);
                }
            }
        });
    }

    /**
     * @param
     * @return
     * @desc (初始化ListView中的头部内容)
     * @user sunyao
     * @createtime 2016/10/24 - 15:12
     */
    public void initHeaderAndFooter() {

        List<UC_CityName> city =  UC_CitySpUtils.getCityName();

        listHeader = LayoutInflater.from(this).inflate(R.layout.uc_lv_header_region_select_always_city, null);

        UC_AlwaysSelectCity alwaysSelectCity = (UC_AlwaysSelectCity) listHeader.findViewById(R.id.uc_gridview_region_always_select_city);
        ViewGroup cityContent = (ViewGroup) listHeader.findViewById(R.id.uc_content_always_city);
        if (city.size() == 0) {
            cityContent.setVisibility(View.GONE);
        } else {
            alwaysSelectAdapter = new UC_AlwaysSelectAdapter(this);
            // 设置Adapter
            alwaysSelectCity.setAdapter(alwaysSelectAdapter);

            // 先写死数据、后面可能从缓存里面读取
//            List<String> list = new ArrayList<String>();
//            list.add("长沙");
//            list.add("张家界");
            alwaysSelectAdapter.updateDatas(city);

        }
        alwaysSelectCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent();
                intent.putExtra(UC_SelectCityMenu.CITY_EXTRA_SOURCE, (UC_CityName)alwaysSelectAdapter.getItem(position));
                L.d("获取到的javabean为：", ((UC_CityName)alwaysSelectAdapter.getItem(position)).toString());
                setResult(UC_SelectCityMenu.CITY_RESULT_CODE, intent);
                finish();

            }
        });
        pinnedLv.addHeaderView(listHeader);
    }

/*
  ##################################################################################################
  ##                                     初始化数据的方法                                         ##
  ##################################################################################################
*/

    /**
     * 请使用快捷模板生成注释
     */
    private void initData() {
        // 获取到城市列表
        mProvider.getCityList();

        String result = "";
        try {
            InputStream in = getResources().getAssets().open("city.json");
            //获取文件的字节数
            int lenght = in.available();
            //创建byte数组
            byte[] buffer = new byte[lenght];
            //将文件中的数据读到byte数组中
            in.read(buffer);
            result = EncodingUtils.getString(buffer, "UTF-8");//你的文件的编码
        } catch (Exception e) {
            e.printStackTrace();
        }

//        fillCityDatas(result);
    }

/*
  ##################################################################################################
  ##                                     点击事件的处理                                           ##
  ##################################################################################################
*/

    /**
     * 控件的点击事件
     */
    @Override
    protected void onClick(View view) {

    }


/*
  ##################################################################################################
  ##                              使用的逻辑方法，以及对外公开的方法                              ##
  ##                                      请求数据、获取数据                                      ##
  ##################################################################################################
*/

    @Subscriber(tag = UC_HttpTag.GET_CITY_LIST)
    public void getCityListData(HttpMethod httpMethod) {

        Map<String, UC_ProvName> map = new HashMap<>();             // 存放城市和对应的省份ID
        String errno = httpMethod.data().getString("errno");
        String status = httpMethod.data().getString("status");
        String msg = httpMethod.data().getString("msg");
        // 如果请求数据成功
        if (status.equals("true")) {
            // 获取到所有的城市列表
            JSONArray arrayData = httpMethod.data().getJSONObject("data").getJSONArray("city_list");

            // 先将省份的列表添加进去
            UC_ProvName provName = new UC_ProvName();
            for (int i = 0; i < arrayData.size(); i++) {
                String prov_id = (String) ((JSONObject) arrayData.get(i)).get("prov_id");        // 获取到省份的id
                String prov_name = (String) ((JSONObject) arrayData.get(i)).get("prov_name");    // 获取到省份的名字
                String city_id = (String) ((JSONObject) arrayData.get(i)).get("city_id");
                String city_name = (String) ((JSONObject) arrayData.get(i)).get("city_name");
                UC_ProvName up = new UC_ProvName(prov_id, prov_name);       // 新建省份列表
                UC_CityName uc = new UC_CityName(city_id, city_name);

                if (map.size() == 0) {
                    List<UC_CityName> cityList = new ArrayList<UC_CityName>();
                    cityList.add(uc);
                    up.setListCity(cityList);
                    map.put(prov_id, up);
                } else {
                    Iterator<String> iterator = map.keySet().iterator();
                    while (iterator.hasNext()) {   // 遍历Map中的数据，看是否保存了当前的省份值
                        String ucProId = iterator.next();   // 获取到当前的ID
                        if (ucProId.equals(prov_id)) {
                            // 如果Map中有保存了当前省份
                            UC_ProvName oldUp = map.get(ucProId);
                            List<UC_CityName> ucList = oldUp.getListCity();
                            ucList.add(uc);
                            oldUp.setListCity(ucList);
                            map.put(prov_id, oldUp);
                            break;
                        } else if (!iterator.hasNext()) {     //如果遍历到最后都没有找到节点
                            List<UC_CityName> newCityList = new ArrayList<UC_CityName>();
                            newCityList.add(uc);
                            up.setListCity(newCityList);
                            map.put(prov_id, up);
                        }
                    }
                }
            }

            listProvins = new ArrayList<UC_ProvName>();
            if (map.size() > 0) {
                Iterator<String> iterator = map.keySet().iterator();
                while (iterator.hasNext()) {   // 遍历Map中的数据，看是否保存了当前的省份值
                    UC_ProvName uPN = map.get(iterator.next());
                    listProvins.add(uPN);
                }
            }
            L.d("获取到的省份列表个数：", listProvins.size() + "");
            fillCityDatas(listProvins);
        }
    }

    /**
     * @param
     * @return
     * @desc (填充城市数据的方法)
     * @user sunyao
     * @createtime 2016/10/24 - 15:41
     */
    public void fillCityDatas(List<UC_ProvName> listDatas) {
        List<UC_RegionSel_ItemBean> datas = new ArrayList<UC_RegionSel_ItemBean>();
        for (int i = 0; i < listDatas.size(); i++) {
            UC_RegionSel_ItemBean itemBean = new UC_RegionSel_ItemBean();
            UC_ProvName ucP = listDatas.get(i);
            itemBean.setId(Integer.parseInt(ucP.getProv_id()));
            itemBean.setTitle(ucP.getProv_name());

            datas.add(itemBean);
        }
        SourceDateList = filterData(datas);
        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);
        adapter.updateDatas(SourceDateList);
    }

    /**
     * 为数据源添加数据
     *
     * @param listData
     * @return
     */
    private List<UC_RegionSel_ItemBean> filterData(List<UC_RegionSel_ItemBean> listData) {

        for (int i = 0; i < listData.size(); i++) {
            // 汉字转拼音
            String pinyin = characterParser.getSelling(listData.get(i).getTitle());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                listData.get(i).setLetter(sortString.toUpperCase());
            } else {
                listData.get(i).setLetter("#");
            }
        }
        return listData;
    }

/*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/

}
