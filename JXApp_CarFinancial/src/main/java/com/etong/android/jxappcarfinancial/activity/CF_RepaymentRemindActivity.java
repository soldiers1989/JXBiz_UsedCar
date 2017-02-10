package com.etong.android.jxappcarfinancial.activity;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.FrameRepayRemindInfo;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshBase;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshListView;
import com.etong.android.jxappcarfinancial.R;
import com.etong.android.jxappcarfinancial.adapter.CF_RecordAdapter;
import com.etong.android.jxappcarfinancial.adapter.CF_RepaymentRemindAdapter;
import java.util.List;

/**
 * @author xiaoxue
 * @desc 还款提醒
 * @createtime 2016/11/18 - 15:40
 */
public class CF_RepaymentRemindActivity extends BaseSubscriberActivity {
    //初始化控件
    private TitleBar mTitleBar;
    private PullToRefreshListView lv_record;
    private ViewGroup ll_record_details_head;
    private TextView cf_number;
    private TextView cf_time;
    private CF_RepaymentRemindAdapter mRepaymentRemindAdapter;
    private LinearLayout default_empty_view;
    private TextView default_empty_textview;
    private LinearLayout cf_ll_space;
    private TextView cf_message;
    // 供外部类或方法使用，判断当前界面是否在显示
    public static boolean isFont;
/*
  ##################################################################################################
  ##                                        类中的变量                                            ##
  ##################################################################################################
*/


    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.cf_activity_remind);

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
        mTitleBar = new TitleBar(this);
        mTitleBar.showNextButton(false);
        mTitleBar.showBackButton(true);
        mTitleBar.showBottomLin(false);
        mTitleBar.setTitleTextColor("#ffffff");     //设置title颜色
        mTitleBar.setmTitleBarBackground("#252E3D");//设置titlebar背景色
        mTitleBar.setTitle("还款提醒");

        default_empty_view = findViewById(R.id.default_empty_content, LinearLayout.class);
        default_empty_textview = findViewById(R.id.default_empty_lv_textview, TextView.class);
        default_empty_view.setClickable(false);

        cf_ll_space = findViewById(R.id.cf_ll_space, LinearLayout.class);//头部上面的灰色区域
        ll_record_details_head = (ViewGroup) findViewById(R.id.cf_ll_record_details_head); //listview 头部
        lv_record =findViewById(R.id.cf_lv_record, PullToRefreshListView.class);  //ListView
        lv_record.setMode(PullToRefreshBase.Mode.PULL_FROM_START);//下拉刷新
        lv_record.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                getRepaymentData();
            }
        });

//        lv_record.setMode(PullToRefreshBase.Mode.BOTH);
        mRepaymentRemindAdapter = new CF_RepaymentRemindAdapter(this); //还款提醒adapter
//        initHeadView();
        lv_record.setAdapter(mRepaymentRemindAdapter);
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
       getRepaymentData();
    }

    /**
     * @desc (从本地获取数据)
     * @user sunyao
     * @createtime 2016/12/6 - 11:50
     * @param
     * @return
     */
    protected void getRepaymentData() {

        final int startTime = (int) System.currentTimeMillis();
        ShowListView();

        // 获取到还款的item
        List<FrameRepayRemindInfo> infoList = FrameEtongApplication.getApplication().getRepaymentRemindInfo();
        if(infoList!=null && infoList.size()>0) {
            // 将数据传送给Adapter
            mRepaymentRemindAdapter.update(infoList);
        } else {
            ShowNullView("暂无还款提醒",false);
            return;
        }
        // 使用新的线程来操作
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (System.currentTimeMillis() - startTime >=3000) {
                            // ListView刷新完成
                            lv_record.onRefreshComplete();
                            return;
                        }
                    }
                });
            }
        }).start();

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
    private void initHeadView() {
//        View view = LayoutInflater.from(this).inflate(R.layout.cf_activity_record_details_head, null);
//        ll_record_details_head = (ViewGroup) view.findViewById(R.id.cf_ll_record_details_head);
        cf_number = (TextView) findViewById(R.id.cf_txt_number);
        cf_time = (TextView) findViewById(R.id.cf_txt_sendtime);
        cf_message = (TextView) findViewById(R.id.cf_txt_message);
        cf_number.setText("序号");
        cf_time.setText("发送时间");
        cf_message.setText("消息内容");


//        lv_record.getRefreshableView().addHeaderView(view);
    }

    /**
     * @desc (显示为空视图)
     * @createtime 2016/11/14 0014-16:11
     * @author wukefan
     */
    protected void ShowNullView(String text, boolean isClick) {
        ll_record_details_head.setVisibility(View.GONE);
        cf_ll_space.setVisibility(View.GONE);
        default_empty_view.setVisibility(View.VISIBLE);
        lv_record.setVisibility(View.GONE);
//        mNullImg.setBackgroundResource(image);
        default_empty_textview.setText(text);
        default_empty_view.setClickable(isClick);
    }

    /**
     * @desc (显示ListView)
     * @createtime 2016/11/29 - 15:49
     * @author xiaoxue
     */
    protected void ShowListView() {
        ll_record_details_head.setVisibility(View.VISIBLE);
        cf_ll_space.setVisibility(View.VISIBLE);
        initHeadView();
        lv_record.setVisibility(View.VISIBLE);
        default_empty_view.setVisibility(View.GONE);
    }


/*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/

    @Override
    protected void onStop() {
        super.onStop();
        L.d("onStop---------界面被隐藏了");
    }

    @Override
    protected void onResume() {
        super.onResume();

        isFont = true;
        L.d("onResume ------ 界面被重现");
    }

    @Override
    protected void onStart() {
        super.onStart();
        L.d("onStart ------界面被重现");
    }

    @Override
    protected void onPause() {
        super.onPause();
        isFont = false;
        L.d("onPause ------界面被隐藏");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isFont = false;
        L.d("onDestroy ------界面被隐藏");
    }

}
