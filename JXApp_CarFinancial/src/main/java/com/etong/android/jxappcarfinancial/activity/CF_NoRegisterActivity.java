package com.etong.android.jxappcarfinancial.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.utils.SetEmptyViewUtil;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappcarfinancial.CF_Provider;
import com.etong.android.jxappcarfinancial.R;
import com.etong.android.jxappcarfinancial.adapter.CF_RegisterAdapter;
import com.etong.android.jxappcarfinancial.bean.CF_RegisterBean;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaoxue
 * @des 没登录就跳到这一页面
 * @createtime 2016/11/18 - 15:19
 */
public class CF_NoRegisterActivity extends BaseSubscriberActivity {
    private LinearLayout make_sure;
    private TitleBar mTitleBar;
    private HttpPublisher mHttpPublisher;
    private CF_Provider mProvider;
    private ListView mListview;
    private List<CF_RegisterBean> mRegisterList;
    private CF_RegisterAdapter mAdapter;
    private int status = 1;
    private TextView cf_tv_text;
    private LinearLayout default_empty_view;
    private TextView default_empty_textview;
    private ImageView default_empty_lv_img;
    private SetEmptyViewUtil setEmptyViewUtil;
    /*
  ##################################################################################################
  ##                                        类中的变量                                            ##
  ##################################################################################################
*/


    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.cf_activity_no_register);
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(this);
        mProvider = CF_Provider.getInstance();
        mProvider.initialize(mHttpPublisher);
        Intent intent = getIntent();
        status = intent.getIntExtra("f_ordertype", -1);
        mProvider.QueryTheFinancial();//请求查询金融机构接口
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
        mRegisterList = new ArrayList<>();    //存金融机构的list

        mTitleBar = new TitleBar(this);
        mTitleBar.showNextButton(false);
        mTitleBar.showBackButton(true);
        mTitleBar.showBottomLin(false);
        mTitleBar.setTitleTextColor("#ffffff");     //设置title颜色
        mTitleBar.setmTitleBarBackground("#252E3D");//设置titlebar背景色
        mTitleBar.setTitle("绑定金融账号");
        //ListView为空或没网显示的view
        default_empty_view = findViewById(R.id.default_empty_content, LinearLayout.class);
        default_empty_textview = findViewById(R.id.default_empty_lv_textview, TextView.class);
        default_empty_lv_img = findViewById(R.id.default_empty_img, ImageView.class);
        setEmptyViewUtil = new SetEmptyViewUtil(default_empty_view, default_empty_lv_img, default_empty_textview);
        default_empty_view.setClickable(false);
        default_empty_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProvider.QueryTheFinancial();//请求查询金融机构接口
            }
        });
        cf_tv_text = findViewById(R.id.cf_tv_text, TextView.class);
        mListview = findViewById(R.id.cf_lv_register, ListView.class);
        mAdapter = new CF_RegisterAdapter(this, mRegisterList, status);
        mListview.setAdapter(mAdapter);
//        make_sure = findViewById(R.id.cf_ll_make_sure, LinearLayout.class);//确认按钮
//        addClickListener(R.id.cf_ll_make_sure);


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
//        if (view.getId() == R.id.cf_ll_make_sure) {
//            Intent intent = new Intent(this, CF_FirstBindingInfoActivity.class);
//            startActivity(intent);
//        }
    }


    /*
      ##################################################################################################
      ##                              使用的逻辑方法，以及对外公开的方法                              ##
      ##                                      请求数据、获取数据                                      ##
      ##################################################################################################
    */
    //得到查询的金融机构
    @Subscriber(tag = FrameHttpTag.QUERY_THE_FINANCIAL)
    protected void getQueryTheFinancial(HttpMethod method) {
        ShowListView();
        String errno = method.data().getString("errno");
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");
        if (!mRegisterList.isEmpty() && mRegisterList.size() != 0) {
            mRegisterList.clear();
        }
        if (!TextUtils.isEmpty(flag) && flag.equals("0")) {
//            JSONObject object=method.data().getJSONObject("data");
            JSONArray array = method.data().getJSONArray("data");
//            JSONArray array=object.getJSONArray("data");
            for (int i = 0; i < array.size(); i++) {
                CF_RegisterBean mRegisterBean = JSON.toJavaObject((JSON) array.get(i), CF_RegisterBean.class);
                mRegisterList.add(mRegisterBean);
            }
            if (mRegisterList.isEmpty()) {
                ShowNullView("亲,暂无支持的金融机构", false, setEmptyViewUtil.OtherView);
                return;
            }
            mAdapter.notifyDataSetChanged();
        } else if (flag.equals(HttpPublisher.NETWORK_ERROR)) {//服务器无响应
            ShowNullView("点击屏幕重试", true, setEmptyViewUtil.NetworkErrorView);
        } else if (flag.equals(HttpPublisher.DATA_ERROR)) {//数据请求失败
            ShowNullView("Sorry,您访问的页面找不到了......", false, setEmptyViewUtil.NoServerView);
        } else {
            if (!TextUtils.isEmpty(msg)) {
                toastMsg(msg);
            }
        }
    }


    /**
     * @desc (显示空视图)
     * @createtime 2016/12/2 - 11:43
     * @author xiaoxue
     */
    protected void ShowNullView(String text, boolean isClick, int type) {
        default_empty_view.setVisibility(View.VISIBLE);
        mListview.setVisibility(View.GONE);
        cf_tv_text.setVisibility(View.GONE);
//        default_empty_lv_img.setBackgroundResource(image);
//        default_empty_textview.setText(text);
//        default_empty_view.setClickable(isClick);
        setEmptyViewUtil.setView(type, text, isClick);
    }

    /**
     * @desc (显示ListView)
     * @createtime 2016/12/2 - 11:44
     * @author xiaoxue
     */
    protected void ShowListView() {
        cf_tv_text.setVisibility(View.VISIBLE);
        mListview.setVisibility(View.VISIBLE);
        default_empty_view.setVisibility(View.GONE);
    }
/*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/

}
