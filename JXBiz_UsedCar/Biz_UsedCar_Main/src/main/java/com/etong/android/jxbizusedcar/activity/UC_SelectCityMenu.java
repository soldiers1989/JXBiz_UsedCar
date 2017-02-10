package com.etong.android.jxbizusedcar.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.subscriber.BaseSubscriberFragment;
import com.etong.android.frame.user.UC_UserProvider;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.widget.sortlistview.CharacterParser;
import com.etong.android.jxbizusedcar.R;
import com.etong.android.jxbizusedcar.adapter.UC_SelectCityMenuAdapter;
import com.etong.android.jxbizusedcar.bean.UC_CityName;
import com.etong.android.jxbizusedcar.utils.UC_CitySpUtils;
import com.etong.android.jxbizusedcar.utils.UC_City_Comparator;

import org.simple.eventbus.Subscriber;

import java.util.Collections;
import java.util.List;

/**
 * 品牌中点击之后显示的菜单中的具体车辆
 * Created by Ellison.Sun on 2016/8/18.
 */
public class UC_SelectCityMenu extends BaseSubscriberFragment {

    public static final String CITY_EXTRA_SOURCE = "select_city_result_code";
    public static final int CITY_RESULT_CODE = 24;

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
    private ListView cityLv;
    private List<UC_CityName> SourceDateList;       // 保存存放ListView中Item的数据
    private UC_SelectCityMenuAdapter cityAdapter;

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private UC_City_Comparator pinyinComparator;
    private List<UC_CityName> fromAcitivyDatas;


    @Override
    protected View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.mContext = getActivity();

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        mDensity = dm.density;
        mWidth = dm.widthPixels;
        mHeight = dm.heightPixels;
        mImageProvider = ImageProvider.getInstance();
        view = inflater.inflate(R.layout.uc_drawerlayout_select_city_content,
                container, false);

        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(getActivity());
        mProvider = UC_UserProvider.getInstance();
        UC_UserProvider.initalize(mHttpPublisher);
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new UC_City_Comparator();

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

        cityLv = (ListView) view.findViewById(R.id.uc_lv_content_select_city);

        // 设置数据为空的ListView显示
        View emptyListView = LayoutInflater.from(getActivity()).inflate(R.layout.default_empty_listview, null);
        ViewGroup default_empty_content = (ViewGroup) emptyListView.findViewById(R.id.default_empty_content);
        TextView default_empty_lv_textview = (TextView) emptyListView.findViewById(R.id.default_empty_lv_textview);
        default_empty_lv_textview.setText("暂无城市");
        ((ViewGroup) cityLv.getParent()).addView(emptyListView);
        cityLv.setEmptyView(emptyListView);

        cityAdapter = new UC_SelectCityMenuAdapter(mContext);
        // 设置悬浮头部的适配器
        cityLv.setAdapter(cityAdapter);

        cityLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // 点击之后将城市添加到缓存中
                UC_CityName cityName = (UC_CityName)cityAdapter.getItem(position);
                UC_CitySpUtils.addCityName(cityName);

                Intent intent = new Intent();
                intent.putExtra(CITY_EXTRA_SOURCE, cityName);
                L.d("获取到的javabean为：", ((UC_CityName)cityAdapter.getItem(position)).toString());
                ((Activity)mContext).setResult(CITY_RESULT_CODE, intent);
                ((Activity)mContext).finish();
            }
        });
    }

/*
  ##################################################################################################
  ##                              使用的逻辑方法，以及对外公开的方法                              ##
  ##                                      请求数据、获取数据                                      ##
  ##################################################################################################
*/

    @Subscriber(tag = UC_Region_SelectActivity.UC_POST_CITYS_TO_MENU)
    public void getCityListFromActivity(List<UC_CityName> list) {
        L.d("从Acitivty获取的数据为：", list.toString());

        // 将获取到的数据放入全局变量中
        fromAcitivyDatas = list;

        SourceDateList = filterData(list);
        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);
        cityAdapter.updateDatas(SourceDateList);
    }


    /**
     * 为数据源添加数据
     *
     * @param listData
     * @return
     */
    private List<UC_CityName> filterData(List<UC_CityName> listData) {

        for (int i = 0; i < listData.size(); i++) {
            // 汉字转拼音
            String pinyin = characterParser.getSelling(listData.get(i).getCity_name());
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

    @Subscriber(tag = "UC_SelectCityMenu")
    private void getDrawerLayout(DrawerLayout drawerLayout) {
        this.mDrawerLayout = drawerLayout;
    }
}
