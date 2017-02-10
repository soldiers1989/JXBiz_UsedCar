package com.etong.android.jxappusedcar.content;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.adapter.UC_RegistTimeElAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc (上牌时间的Activity)
 * @user sunyao
 * @createtime 2016/10/19 - 16:39
 */
public class UC_RegistTimeActivity extends BaseSubscriberActivity {
    /*
      ##################################################################################################
      ##                                        类中的变量                                            ##
      ##################################################################################################
    */
    private ExpandableListView registTimeElv;           // 二级ListView
    private UC_RegistTimeElAdapter registTimeAdapter;   // 适配器
    private TitleBar mTitleBar;
    public static String SET_YEAR;
    public static String SET_MONTH;
    private String mSelectYear="2016年1月9号";


    public static String SELECT_YEAR = "Current_Select_Year_Request";
    public static int SELL_CAR_RESULT_CODE = 102;
    private List<String> listYear = null;                  // 传送到选择时间界面的年份
    private HashMap<String, List<String>> listMon = null;  // 传送到选择时间界面的月份

    @Override
    protected void onInit(Bundle bundle) {
        setContentView(R.layout.uc_activity_uc__regist_time_content);

        initView();
        initData();
        setComponentListenter();
    }


/*
  ##################################################################################################
  ##                                     初始化View的方法                                         ##
  ##################################################################################################
*/

    /**
     * @desc (在使用组件之前的初始化操作，获取到界面上的传值)
     * @user sunyao
     * @createtime 2016/10/21 - 10:38
     * @param
     * @return
     */
    private void initView() {

        mTitleBar = new TitleBar(this);
        mTitleBar.showNextButton(false);
        mTitleBar.setTitle("上牌时间");

        registTimeElv = findViewById(R.id.uc_regist_time_lv_content, ExpandableListView.class);
        registTimeElv.setGroupIndicator(null);
        registTimeAdapter = new UC_RegistTimeElAdapter(this);
        registTimeElv.setAdapter(registTimeAdapter);

        Intent mIntent = getIntent();
        mSelectYear = mIntent.getStringExtra(SELECT_YEAR);
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
        Calendar instance = Calendar.getInstance();
        int currentYear =instance.get(Calendar.YEAR);
        int currentMon = instance.get(Calendar.MONTH);
        currentMon += 1;

        listYear = new ArrayList<String>();
        for (int i=0; i<10; i++) {
            listYear.add(currentYear--+"年");
        }

        listMon = new HashMap<String, List<String>>();
        List<String> totalMoth = null;
        for (int i = 0; i< listYear.size(); i++) {
            int tMon = -1;
            if (0 == i) {
                tMon = currentMon;
            } else {
                tMon = 12;
            }
            totalMoth = new ArrayList<String>();
            for (int j=1; j<=tMon; j++) {
                totalMoth.add(j+"月");
            }
            listMon.put(listYear.get(i), totalMoth);
        }

        // 将当前选中的日期传入
        registTimeAdapter.setCurrentSelectTime(mSelectYear);
        registTimeAdapter.updateData(listYear, listMon);
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

    public void setComponentListenter() {

        registTimeElv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String tYear="";
                String childText = "";
                try {
                    tYear = getListYear().get(groupPosition);
                    childText = getListMon().get(getListYear().get(groupPosition)).get(childPosition);
                } catch (Exception e) {
                }

                ArrayList list = new ArrayList();
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                list.add(tYear);
                list.add(childText);

                bundle.putStringArrayList(UC_RegistTimeActivity.SET_YEAR, list);

//                bundle.putString(UC_RegistTimeActivity.SET_YEAR, tYear);
//                bundle.putString(UC_RegistTimeActivity.SET_MONTH, childText);
                intent.putExtras(bundle);
//                intent.putExtra(UC_RegistTimeActivity.SET_YEAR, tYear);
//                intent.putExtra(UC_RegistTimeActivity.SET_MONTH, childText);
                setResult(UC_RegistTimeActivity.SELL_CAR_RESULT_CODE, intent);
                finish();

                return true;
            }
        });
    }



/*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/


    public List<String> getListYear() {
        return listYear;
    }

    public void setListYear(List<String> listYear) {
        this.listYear = listYear;
    }

    public HashMap<String, List<String>> getListMon() {
        return listMon;
    }

    public void setListMon(HashMap<String, List<String>> listMon) {
        this.listMon = listMon;
    }

}
