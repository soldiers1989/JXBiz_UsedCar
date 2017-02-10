package com.etong.android.jxappcarassistant.violation_query.main_content;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappcarassistant.R;
import com.etong.android.jxappcarassistant.violation_query.adapter.CA_ViolationAdapter;
import com.etong.android.jxappcarassistant.violation_query.bean.CA_ViolationQueryBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaoxue
 * @desc (违章查询页)
 * @createtime 2016/11/23 - 14:24
 */

public class CA_ViolationOfTheQueryActivity extends BaseSubscriberActivity {
    private TitleBar mTitleBar;
    private ListView listview_violation;
    private TextView ca_tv_violation;
    private CA_ViolationQueryBean mCA_ViolationQueryBean;
    private List<CA_ViolationQueryBean.ListBean> violationList;//装违章信息的list
    /*
  ##################################################################################################
  ##                                        类中的变量                                            ##
  ##################################################################################################
*/


    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.ca_activity_violation_of_the_query);

        initData();
        initView();
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
        mTitleBar = new TitleBar(this);
        mTitleBar.setTitle("违章查询");
        mTitleBar.showNextButton(false);
        listview_violation = findViewById(R.id.ca_lv_violation, ListView.class);  //初始化ListView
        //listView 数据为空的显示
        View emptyListView = LayoutInflater.from(this).inflate(R.layout.default_empty_listview, null);
        ViewGroup default_empty_content = (ViewGroup) emptyListView.findViewById(R.id.default_empty_content);
        TextView default_empty_lv_textview = (TextView) emptyListView.findViewById(R.id.default_empty_lv_textview);
        default_empty_lv_textview.setText("暂无违章");
        ((ViewGroup) listview_violation.getParent()).addView(emptyListView);
        listview_violation.setEmptyView(emptyListView);
        //违章查询adapter
        CA_ViolationAdapter mViolationAdapter = new CA_ViolationAdapter(this, violationList);
        initHeadView();
        listview_violation.setAdapter(mViolationAdapter);
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
        violationList = new ArrayList<>();  //装违章信息的list
        //得到上页传的数据
        Intent intent = getIntent();
        mCA_ViolationQueryBean = (CA_ViolationQueryBean) intent.getSerializableExtra("CA_ViolationQueryBean");
        if (null != mCA_ViolationQueryBean) {
            if (null != mCA_ViolationQueryBean.getList()) {
                violationList.addAll(mCA_ViolationQueryBean.getList());
            }
        }

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

    /**
     * @desc (为ListView添加头部)
     * @createtime 2016/11/7 - 18:42
     * @author xiaoxue
     */
    private int score;//记分
    private int price;//罚款

    private void initHeadView() {
        View view = LayoutInflater.from(this).inflate(R.layout.ca_violation_query_head, null);
        ca_tv_violation = (TextView) view.findViewById(R.id.ca_tv_violation);
        if(null!=mCA_ViolationQueryBean){
            if (null != mCA_ViolationQueryBean.getList()) {
                for (int i = 0; i < mCA_ViolationQueryBean.getList().size(); i++) {
                    int tempScore = Integer.valueOf(mCA_ViolationQueryBean.getList().get(i).getScore());
                    int tempPrice = Integer.valueOf(mCA_ViolationQueryBean.getList().get(i).getPrice());
                    score += tempScore;
                    price += tempPrice;
                }
                ca_tv_violation.setText("您的车辆违章查询结果：\n" + "共计" + mCA_ViolationQueryBean.getList().size() + "条违法行为，扣"
                        + score + "分，罚款金额" + price + "元（人民币）");
                listview_violation.addHeaderView(view);
            }
        }
    }



/*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/

}
