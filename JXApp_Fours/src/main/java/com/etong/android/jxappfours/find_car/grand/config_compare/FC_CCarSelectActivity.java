package com.etong.android.jxappfours.find_car.grand.config_compare;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.grand.adapter.FC_CompareSelectCarAdapter;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_Add_VechileStyle;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_VechileType;
import com.etong.android.jxappfours.models_contrast.main_content.MC_ChooseCarType;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;

/**
 * Created by Ellison.Sun on 2016/9/1.
 * <p/>
 * 在车辆配置界面中点击添加按钮跳转过来的页面
 */
public class FC_CCarSelectActivity extends BaseSubscriberActivity {

    private ListView selectCarLv;
    private FC_CompareSelectCarAdapter selectCarAdapter;

    public static DrawerLayout drawer_layout_carseries;
    public static DrawerLayout drawer_layout_type;
    public static DrawerLayout mSelectCarDrawerLayout;
    public static int level = 0;//抽屉布局的层级
    public static boolean isStart = false;
    private ArrayList<Models_Contrast_Add_VechileStyle> allCarItem;
    private ArrayList<Models_Contrast_Add_VechileStyle> checkedCarItem;
    private MC_ChooseCarType ccarSelectCarType;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.fc_car_config_select_car_content);

        initView();
        initFragmentListener();
        initData();
    }

    /**
     * 初始化组件
     */
    private void initView() {
        TitleBar mTitleBar = new TitleBar(this);
        mTitleBar.setTitle("车型对比");
        mTitleBar.showNextButton(false);
        mTitleBar.showBackButton(true);


        mSelectCarDrawerLayout = findViewById(R.id.fours_select_car_drawerlayout, DrawerLayout.class);
        mSelectCarDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);

        // 选择车型的显示的ListView
        selectCarLv = (ListView) findViewById(R.id.fc_car_config_select_car_lv);
        selectCarAdapter = new FC_CompareSelectCarAdapter(this);
        selectCarLv.setAdapter(selectCarAdapter);

        // 添加ListView为空时候的视图
        View emptyListView = LayoutInflater.from(this).inflate(R.layout.default_empty_listview, null);
        ViewGroup default_empty_content = (ViewGroup) emptyListView.findViewById(R.id.default_empty_content);
        TextView default_empty_lv_textview = (TextView) emptyListView.findViewById(R.id.default_empty_lv_textview);
        default_empty_lv_textview.setText("车型库还空着呢！添加车型对比吧");
        ((ViewGroup)selectCarLv.getParent()).addView(emptyListView);
        selectCarLv.setEmptyView(emptyListView);

        // ListView中Item的点击事件
        selectCarLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemId = ((Models_Contrast_Add_VechileStyle)selectCarAdapter.getItem(position)).getId();
                for (int i=0; i<allCarItem.size(); i++) {
                    Models_Contrast_Add_VechileStyle carStyle = allCarItem.get(i);
                    if (itemId == carStyle.getId()) {
                        // 将选中的item的id添加到存放选中的id的List中
                        carStyle.setChecked(true);
                    }
                }
                // 返回到原来的界面
                setDataInfo();
            }
        });

        addClickListener(R.id.fours_txt_select_car_add);
    }

    /**
     * 将数据设置在
     */
    public void setDataInfo() {
        //数据是使用Intent返回
        Intent intent = new Intent();
        //把返回数据存入Intent
        intent.putExtra(Find_car_CompareContentActivity.FIND_CAR_COMPARE_ALL_ITEM, allCarItem);
        //设置返回数据
        FC_CCarSelectActivity.this.setResult(Find_car_CompareContentActivity.COMPARECONTENT_REQUEST_CODE, intent);
        //关闭Activity
        FC_CCarSelectActivity.this.finish();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        checkedCarItem = new ArrayList<Models_Contrast_Add_VechileStyle>();
        allCarItem = getIntent().getParcelableArrayListExtra(Find_car_CompareContentActivity.FIND_CAR_COMPARE_ALL_ITEM);
        if (allCarItem!=null && allCarItem.size()>0) {
            // 遍历其中的值，判断选中与不选中
            for (int i = 0; i < allCarItem.size(); i++) {
                Models_Contrast_Add_VechileStyle carStyle = allCarItem.get(i);
                if (!carStyle.isChecked()) {
                    // 将选中的item的id添加到存放选中的id的List中
                    checkedCarItem.add(carStyle);
                }
            }
        }
        selectCarAdapter.updateListData(checkedCarItem);
    }

    @Override
    protected void onClick(View view) {
        if (view.getId() == R.id.fours_txt_select_car_add) {
            if (allCarItem.size()>=12) {
                toastMsg("抱歉，最多可添加12辆车，不能再进入选车！");
                return;
            }
            selectCarType();
        }
    }

    //选择车型方法
    private void selectCarType() {
        // 打开侧滑，让组件内部处理
        ccarSelectCarType.openOneFragment();
    }

    /**
     * @desc (为当前页面添加侧滑组件)
     * @user sunyao
     * @createtime 2016/9/29 - 16:17
     *
     * @param
     * @return
     */
    private void initFragmentListener() {
        ccarSelectCarType = new MC_ChooseCarType(this, this, mSelectCarDrawerLayout, R.id.fours_select_car_framlayout);
        ccarSelectCarType.setNeedChecked(true);
    }

    /**
     * 获取到车型
     * @param add
     */
    @Subscriber(tag = MC_ChooseCarType.CAT_TYPE_TAG)
    protected void getCarModels(Models_Contrast_VechileType add) {
//        List<Models_Contrast_Add_VechileStyle> temp_car_title = new ArrayList<Models_Contrast_Add_VechileStyle>();
//        temp_car_title.add(add);
//        toastMsg(add.getTitle());
        // 将选中的车添加到List中
        Models_Contrast_Add_VechileStyle tempAddVechile = new Models_Contrast_Add_VechileStyle();
        tempAddVechile.setId(add.getVid());
        tempAddVechile.setTitle(add.getFullName());
        tempAddVechile.setChecked(true);

        allCarItem.add(tempAddVechile);
        setDataInfo();
    }
}
