package com.etong.android.jxbizusedcar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.widget.EtongNoScrollGridView;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappusedcar.bean.UC_FilterDataDictionaryBean;
import com.etong.android.jxbizusedcar.R;
import com.etong.android.jxbizusedcar.adapter.UC_VehicleColorGridAdapter;
import com.etong.android.jxbizusedcar.bean.UC_VehicleColorJsonData;

import java.util.List;

public class UC_VehicleColorActivity extends BaseSubscriberActivity implements UC_VehicleColorGridAdapter.UC_ColorCallBack {

    /*
  ##################################################################################################
  ##                                        类中的变量                                            ##
  ##################################################################################################
*/

    private List<UC_FilterDataDictionaryBean.MapBean> mapBeanList;
    public static String SELECT_COLOR = "selectColor";
    public static int SELECT_COLOR_RESULT_CODE = 302;
    private UC_FilterDataDictionaryBean.MapBean selectColorBean;   //颜色javabean


    private EtongNoScrollGridView mGridView;
    private UC_VehicleColorGridAdapter mGridAdapter;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.uc_activity_vehicle_color);

        initView();
        initData();
    }


/*
  ##################################################################################################
  ##                                     初始化View的方法                                         ##
  ##################################################################################################
*/

    /**
     * 请使用快捷模板生成注释
     */
    private void initView() {

        TitleBar mTitleBar = new TitleBar(this);
        mTitleBar.setTitle("车辆颜色");
        mTitleBar.showNextButton(false);

        Intent mIntent = getIntent();
        selectColorBean = (UC_FilterDataDictionaryBean.MapBean) mIntent.getSerializableExtra(SELECT_COLOR);

        mGridView = (EtongNoScrollGridView) findViewById(R.id.uc_gv_vehicle_color);

        setGridData();
        mGridAdapter = new UC_VehicleColorGridAdapter(mGridView, this, UC_VehicleColorActivity.this, mapBeanList);
        mGridView.setAdapter(mGridAdapter);
        mGridAdapter.notifyDataSetChanged();
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

    /**
     * @desc 选择颜色回调
     * @createtime 2016/10/25 0025-18:48
     * @author wukefan
     */
    @Override
    public void answerColor(UC_FilterDataDictionaryBean.MapBean MapBean) {

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(UC_VehicleColorActivity.SELECT_COLOR, MapBean);
        intent.putExtras(bundle);
        setResult(UC_VehicleColorActivity.SELECT_COLOR_RESULT_CODE, intent);
        finish();
    }

    /**
     * @desc (设置颜色数据)
     * @createtime 2016/11/4 0004-11:53
     * @author wukefan
     */
    private void setGridData() {
        JSONObject object = JSONObject.parseObject(UC_VehicleColorJsonData.getJsonData());
        UC_FilterDataDictionaryBean mItemBeam = JSON.toJavaObject(object, UC_FilterDataDictionaryBean.class);
        mapBeanList = mItemBeam.getMap();
        if (null != selectColorBean) {
            selectColorBean.setSelect(true);
            mapBeanList.set(Integer.valueOf(selectColorBean.getKey()), selectColorBean);
        }
    }

/*
  ##################################################################################################
  ##                              使用的逻辑方法，以及对外公开的方法                              ##
  ##                                      请求数据、获取数据                                      ##
  ##################################################################################################
*/





/*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/

}
