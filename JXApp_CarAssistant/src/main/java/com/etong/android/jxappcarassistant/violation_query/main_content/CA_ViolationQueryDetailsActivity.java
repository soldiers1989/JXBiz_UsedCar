package com.etong.android.jxappcarassistant.violation_query.main_content;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappcarassistant.R;
import com.etong.android.jxappcarassistant.violation_query.adapter.CA_ViolationQueryDetailsAdapter;
import com.etong.android.jxappcarassistant.violation_query.bean.CA_ViolationQueryBean;

public class CA_ViolationQueryDetailsActivity extends BaseSubscriberActivity {

    /*
  ##################################################################################################
  ##                                        类中的变量                                            ##
  ##################################################################################################
*/
    private TitleBar mTltleBar;
    private ListView mListView;

    private CA_ViolationQueryDetailsAdapter mAdapter;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.ca_activity_violation_query_details);

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
        mTltleBar = new TitleBar(this);
        mTltleBar.setTitle("违章结果详情");
        mTltleBar.showBottomLin(false);

        Intent intent = getIntent();
        CA_ViolationQueryBean.ListBean dataBean = (CA_ViolationQueryBean.ListBean) intent.getSerializableExtra("CA_ViolationQueryBean");

        mListView = findViewById(R.id.ca_vq_lv_details, ListView.class);

        mAdapter = new CA_ViolationQueryDetailsAdapter(this);
        mListView.setAdapter(mAdapter);
        
        if (null != dataBean) {
            mAdapter.updateListDatas(dataBean);
        }
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
