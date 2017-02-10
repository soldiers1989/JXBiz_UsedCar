package com.etong.android.jxappfours.models_contrast.main_content;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.utils.ListAdapter;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.grand.config_compare.Find_car_CompareContentActivity;
import com.etong.android.jxappfours.models_contrast.adapter.Models_Contrast_ArrayAdapter;
import com.etong.android.jxappfours.models_contrast.common.Models_Contrast_AddVechileStyle_Method;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_Add_VechileStyle;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_VechileType;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 车型对比 主界面
 * Created by Administrator on 2016/8/12.
 */
public class MC_MainActivity extends BaseSubscriberActivity {
    public static DrawerLayout drawer_layout;
    public static DrawerLayout drawer_layout_carseries;
    public static DrawerLayout drawer_layout_type;
    private LinearLayout addCar;
    private ListView contrast_list;
    private ListAdapter<Models_Contrast_Add_VechileStyle> mListAdapters;
    private ArrayList<Models_Contrast_Add_VechileStyle> car_title = new ArrayList<Models_Contrast_Add_VechileStyle>();
    private LinearLayout car_null;
    private Button delete;
    private Button clear;
    private Button contrast;
    private ImageButton back_button;
    private TextView listview_count;
    private TextView click_count;
    private Models_Contrast_ArrayAdapter mAdapter;
    public static int level = 0;//抽屉布局的层级
    public static boolean isStart = false;
    private MC_ChooseCarType getCarType;
    private ViewGroup lv_content;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {

        setContentView(R.layout.models_contrast_activity);
        isStart = true;
        initView();
        setCarInfo();

    }

    private void setCarInfo() {
        car_title.clear();
        //得到缓存的信息
       /* List<Models_Contrast_Add_VechileStyle> mList = new ArrayList<Models_Contrast_Add_VechileStyle>();
        Map map = Models_Contrast_AddVechileStyle_Method.getAdd_VechileStyle();
        Models_Contrast_Add_VechileStyle info = null;
        if (map != null) {
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                Object key = entry.getKey();
                String data = String.valueOf(map.get(Integer.valueOf(String.valueOf(key))));
                L.d("从本地获取到的数据:", data);
                info = JSON.parseObject(data, Models_Contrast_Add_VechileStyle.class);
                if (null != info) {
                    car_title.add(info);
                    contrast_list.setVisibility(View.VISIBLE);
                    car_null.setVisibility(View.GONE);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
        int i = 0;
        for (Models_Contrast_Add_VechileStyle o : car_title) {
            if (o.isChecked()) {
                i++;
            }
        }
        click_count.setText(i + "");
        listview_count.setText(contrast_list.getCount() + "");*/

        List<Models_Contrast_Add_VechileStyle> mList = new ArrayList<Models_Contrast_Add_VechileStyle>();
        Map map = Models_Contrast_AddVechileStyle_Method.getAdd_VechileStyle();
        Models_Contrast_VechileType info = null;
        if (map != null) {
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                Object key = entry.getKey();
                String data = String.valueOf(map.get(Integer.valueOf(String.valueOf(key))));
                L.d("从本地获取到的数据:", data);
                info = JSON.parseObject(data, Models_Contrast_VechileType.class);
                if (null != info) {

                    // 将数据进行转换
                    Models_Contrast_Add_VechileStyle infoVechile = new Models_Contrast_Add_VechileStyle();
                    infoVechile.setId(info.getVid());
                    infoVechile.setTitle(info.getFullName());
                    infoVechile.setChecked(info.isChecked());

                    car_title.add(infoVechile);
                    contrast_list.setVisibility(View.VISIBLE);
                    car_null.setVisibility(View.GONE);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
        int i = 0;
        for (Models_Contrast_Add_VechileStyle o : car_title) {
            if (o.isChecked()) {
                i++;
            }
        }
        click_count.setText(i + "");
        listview_count.setText(contrast_list.getCount() + "");
    }

    @Override
    protected void onResume() {
        super.onResume();
        MC_ChooseCarType.LEVEL = 1;
        setCarInfo();
        if (null != drawer_layout && null != drawer_layout_carseries && null != drawer_layout_type) {
            drawer_layout.closeDrawer(Gravity.RIGHT);
            drawer_layout_carseries.closeDrawer(Gravity.RIGHT);
            drawer_layout_type.closeDrawer(Gravity.RIGHT);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        isStart = false;
        level = 0;
    }

    protected void initView() {
        lv_content = (ViewGroup)findViewById(R.id.models_contrast_lv_content);

        back_button = findViewById(R.id.models_contrast_ib_back_button, ImageButton.class);
        click_count = findViewById(R.id.models_contrast_txt_select_count, TextView.class);
        listview_count = findViewById(R.id.models_contrast_txt_click_count, TextView.class);
        contrast_list = findViewById(R.id.models_contrast_lv_list, ListView.class);
        car_null = findViewById(R.id.models_contrast_ll_null, LinearLayout.class);

        delete = findViewById(R.id.models_contrast_rb_delete, Button.class);
        clear = findViewById(R.id.models_contrast_rb_clear, Button.class);
        contrast = findViewById(R.id.models_contrast_rb_contrast, Button.class);

        if (contrast_list.getCount() <= 0) {
            contrast_list.setVisibility(View.GONE);
            car_null.setVisibility(View.VISIBLE);
        } else {
            contrast_list.setVisibility(View.VISIBLE);
            car_null.setVisibility(View.GONE);
        }

        addCar = findViewById(R.id.models_contrast_ll_add, LinearLayout.class);
        addClickListener(R.id.models_contrast_ib_back_button);
        addClickListener(R.id.models_contrast_ll_add);
        addClickListener(R.id.models_contrast_rb_contrast);
        addClickListener(R.id.models_contrast_rb_clear);
        addClickListener(R.id.models_contrast_rb_delete);

        drawer_layout = findViewById(R.id.models_contrast_drawer_layout, DrawerLayout.class);
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);


        // 设置页面上的监听事件
        getCarType = new MC_ChooseCarType(this, this, drawer_layout, R.id.models_contrast_fly_drawer);
        getCarType.setNeedChecked(true);

        mAdapter = new Models_Contrast_ArrayAdapter(this, car_title);
        //设置回调
        mAdapter.setCallback(new Models_Contrast_ArrayAdapter.AdapterCallback() {
            @Override
            public void callBack(int isCheckedNum, Models_Contrast_Add_VechileStyle add) {
                // 将当前的javabean设置为通用的javabean
                Models_Contrast_VechileType addVechileType = new Models_Contrast_VechileType();
                addVechileType.setVid(add.getId());
                addVechileType.setFullName(add.getTitle());
                addVechileType.setChecked(add.isChecked());

                Models_Contrast_AddVechileStyle_Method.cartAdd(addVechileType);
                click_count.setText(isCheckedNum + "");
            }
        });
        contrast_list.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.models_contrast_rb_contrast) {//对比
            List<Models_Contrast_Add_VechileStyle> temp_car_title = new ArrayList<Models_Contrast_Add_VechileStyle>();
            // 将选中的item的id设置到List中
            ArrayList<Integer> idList = new ArrayList<Integer>();
            boolean isSelect = false;
            String sid = "";
            for (int i = 0; i < car_title.size(); i++) {
                if (car_title.get(i).isChecked()) {
                    temp_car_title.add(car_title.get(i));
                    idList.add(car_title.get(i).getId());
                    isSelect = true;
                }
            }

            // 只有当选中的个数大于2 时才跳转到车辆配置对比界面
            if (isSelect && temp_car_title.size() >= 2) {
                Intent intent = new Intent(MC_MainActivity.this, Find_car_CompareContentActivity.class);
                intent.putParcelableArrayListExtra(Find_car_CompareContentActivity.FIND_CAR_COMPARE_ALL_ITEM, car_title);
                intent.putExtra(Find_car_CompareContentActivity.IS_SHOW_ADD_CONTENT, true);
                startActivity(intent);
            }else{
                toastMsg("您还未选中2款及以上车型!");
            }
//            if(!isSelect && temp_car_title.size()==0){
//                toastMsg("还未选中具体车型!");
//            }
        } else if (view.getId() == R.id.models_contrast_rb_clear) {//清空
            car_title.clear();
            mAdapter.notifyDataSetChanged();
            listview_count.setText(contrast_list.getCount() + "");
            click_count.setText(0 + "");
            contrast_list.setVisibility(View.GONE);
            car_null.setVisibility(View.VISIBLE);
            Models_Contrast_AddVechileStyle_Method.clear();
        } else if (view.getId() == R.id.models_contrast_rb_delete) {//删除
            List<Models_Contrast_Add_VechileStyle> temp_car_title = new ArrayList<Models_Contrast_Add_VechileStyle>();
            boolean isSelect = false;

            for (int i = 0; i < car_title.size(); i++) {
                if (!car_title.get(i).isChecked()) {
                    temp_car_title.add(car_title.get(i));
                } else {
                    isSelect = true;
                    Models_Contrast_AddVechileStyle_Method.remove(car_title.get(i).getId());
                    lv_content.setBackgroundColor(Color.WHITE);
                }
            }

            if (!isSelect) {
//                Toast.makeText(this, "未选择任何车型", Toast.LENGTH_SHORT).show();
                toastMsg("未选择任何车型");
            }

            car_title.clear();
            car_title.addAll(temp_car_title);

            mAdapter.notifyDataSetChanged();

            listview_count.setText(contrast_list.getCount() + "");
            click_count.setText(0 + "");
            if (contrast_list.getCount() <= 0) {
                contrast_list.setVisibility(View.GONE);
                car_null.setVisibility(View.VISIBLE);
            }
        } else if (view.getId() == R.id.models_contrast_ll_add) {//添加车款
            if (car_title.size() < 12) {

                // 打开一个界面
                getCarType.openOneFragment();
            } else {
//                Toast.makeText(this, "抱歉,最多可添加12款车型,不能在进入选车", Toast.LENGTH_SHORT).show();
                toastMsg("抱歉,最多可添加12款车型,不能在进入选车");
            }
        } else if (view.getId() == R.id.models_contrast_ib_back_button) {

            back();
        }
    }




    @Subscriber(tag = MC_ChooseCarType.CAT_TYPE_TAG)
    protected void getCarModels(Models_Contrast_VechileType add) {
        Models_Contrast_Add_VechileStyle vechileStyle = new Models_Contrast_Add_VechileStyle();
        vechileStyle.setId(add.getVid());
        vechileStyle.setTitle(add.getFullName());

        List<Models_Contrast_Add_VechileStyle> temp_car_title = new ArrayList<Models_Contrast_Add_VechileStyle>();
        temp_car_title.add(vechileStyle);
        contrast_list.setVisibility(View.VISIBLE);
//        mAdapter.addAll(temp_car_title);
        car_null.setVisibility(View.GONE);

        car_title.addAll(temp_car_title);
        mAdapter.notifyDataSetChanged();
        listview_count.setText(contrast_list.getCount() + "");
    }
}
