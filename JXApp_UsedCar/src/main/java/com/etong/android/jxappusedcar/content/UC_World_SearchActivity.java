package com.etong.android.jxappusedcar.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.utils.ListAdapter;
import com.etong.android.frame.utils.SetEmptyViewUtil;
import com.etong.android.frame.widget.EtongNoScrollListView;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshBase;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshListView;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.adapter.UC_World_CarList_Adapter;
import com.etong.android.jxappusedcar.adapter.UC_World_HistoryListAdapter;
import com.etong.android.jxappusedcar.javabean.UC_World_CarListJavaBean;
import com.etong.android.jxappusedcar.provider.UC_WorldProvider;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc 二手车世界搜索界面
 * @createtime 2016/10/9 0009--17:55
 * @Created by wukefan.
 */
public class UC_World_SearchActivity extends BaseSubscriberActivity implements AdapterView.OnItemClickListener,UC_World_HistoryListAdapter.HistoryCallBack{

    private AutoCompleteTextView mTxtSearch;
    private String mContentText;
    private ViewGroup mResultContent;
    private LinearLayout default_empty_content;
    private PullToRefreshListView mListViewResult;
    private LinearLayout mResultHeadLayout;
    private TextView mResultHead;
    private ArrayAdapter<String> arrayAdapter;
    private List<String> vechileName = new ArrayList<String>();
    private UC_World_CarList_Adapter mListAdapter;
    private int number;
    private List<UC_World_CarListJavaBean> mListData = new ArrayList<>();
    private HttpPublisher mHttpPublisher;
    private UC_WorldProvider mUC_WorldProvider;
    private int pageSize = 10;
    private int pageCurrent = 0;
    private boolean isInitRefresh = false;     //是否可以上拉加载
    private boolean isFirst = true;
    private TextView default_empty_textview;
    private ImageView default_empty_img;
    private SetEmptyViewUtil setEmptyViewUtil;
    private EtongNoScrollListView historyList;
    private LinearLayout searchHistory;
//    private ListAdapter<String> historyListAdapter;
    private List<String> mHistoryList;
    private UC_World_HistoryListAdapter historyListAdapter;
    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.used_car_world_activity_search);
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(this);
        mUC_WorldProvider = UC_WorldProvider.getInstance();
        mUC_WorldProvider.initialize(mHttpPublisher);
        mTxtSearch = findViewById(R.id.used_car_tct_search_content, AutoCompleteTextView.class);
        mContentText = mTxtSearch.getText().toString();

        searchHistory = findViewById(R.id.used_car_ll_search_history, LinearLayout.class);
        historyList = findViewById(R.id.used_car_lv_history_list, EtongNoScrollListView.class);

        mResultContent = findViewById(R.id.used_car_search_more_result_content, ViewGroup.class);
        default_empty_content = findViewById(R.id.default_empty_content, LinearLayout.class);
        default_empty_textview = findViewById(R.id.default_empty_lv_textview, TextView.class);
        default_empty_img = findViewById(R.id.default_empty_img, ImageView.class);
        setEmptyViewUtil = new SetEmptyViewUtil(default_empty_content, default_empty_img, default_empty_textview);
        default_empty_content.setVisibility(View.GONE);
        default_empty_content.setClickable(false);
        default_empty_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageCurrent = 0;
                mUC_WorldProvider.worldSearch(pageSize, pageCurrent, mContentText, "search");
            }
        });

        mListViewResult = findViewById(R.id.used_car_search_more_result, PullToRefreshListView.class);
        mListViewResult.setMode(PullToRefreshBase.Mode.PULL_FROM_END);//上拉

        mListViewResult.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载");
        mListViewResult.getLoadingLayoutProxy(false, true).setRefreshingLabel(
                "正在加载更多...");
        mListViewResult.getLoadingLayoutProxy(false, true).setReleaseLabel(
                "松开加载更多");
        mListViewResult.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mListViewResult.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mUC_WorldProvider.worldSearch(pageSize, ++pageCurrent, mContentText, "search"); //请求车列表
                    }
                }, 300);
            }
        });
        //添加头部view
        View view = LayoutInflater.from(this).inflate(R.layout.used_car_result_head_view, null);
        mResultHeadLayout = (LinearLayout) view.findViewById(R.id.used_car_ll_result_head);
        mResultHead = (TextView) view.findViewById(R.id.used_car_txt_result_head);
//        mResultHead.setText("找到"+mListViewResult.getCount()+"条相关车系");
        mResultHeadLayout.setVisibility(View.GONE);
        mListViewResult.getRefreshableView().addHeaderView(view);
        mListViewResult.setVisibility(View.GONE);

        addClickListener(R.id.used_car_txt_cancel);
        addClickListener(R.id.used_car_txt_cancel_button);

        //搜索历史
        historyListAdapter=new UC_World_HistoryListAdapter(this,UC_World_SearchActivity.this);
        historyList.setAdapter(historyListAdapter);

        mHistoryList = FrameEtongApplication.getApplication().getSearchUsedCarHistory();//获得搜索过的历史记录
        if (mHistoryList != null) {
            for (int i = 0; i < mHistoryList.size(); i++) { //外循环是循环的次数
                for (int j = mHistoryList.size() - 1; j > i; j--){  //内循环是 外循环一次比较的次数
//                    if(mHistoryList.get(i).equals("")){
//                        mHistoryList.remove(i);
//                    }
                    if (mHistoryList.get(i).equals(mHistoryList.get(j))) {
                        mHistoryList.remove(j);
                    }
                }
            }
            historyListAdapter.update(mHistoryList);
            searchHistory.setVisibility(View.VISIBLE);
            mResultContent.setVisibility(View.GONE);
        } else {
            searchHistory.setVisibility(View.GONE);
            mResultContent.setVisibility(View.VISIBLE);
        }

        //输入框
        arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, vechileName);

        mTxtSearch.setAdapter(arrayAdapter);

        mTxtSearch.setThreshold(1);
        mTxtSearch.setOnItemClickListener(this);


        //对软键盘进行监听
        mTxtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                // TODO Auto-generated method stub
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//搜索
                    mListAdapter.clear();


                    mContentText = mTxtSearch.getText().toString();

                    if (mContentText.equals("") || mContentText.isEmpty()) {
                        toastMsg("请输入你要查询的车名");

                    } else {
                        pageCurrent = 0;
                        mUC_WorldProvider.worldSearch(pageSize, pageCurrent, mContentText, "search");
//                        setData1();
                    }
                }
                return false;
            }
        });
        //搜索adapter
        mListAdapter = new UC_World_CarList_Adapter(this);
        mListViewResult.setAdapter(mListAdapter);


        mTxtSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mContentText = mTxtSearch.getText().toString();

                if (("").equals(mContentText) && TextUtils.isEmpty(mContentText)) {
                    number = 0;
                    mListAdapter.clear();
                    mResultHeadLayout.setVisibility(View.GONE);
                    System.out.println("数据被清除了");
                    mResultContent.setVisibility(View.VISIBLE);
                    mListViewResult.setVisibility(View.GONE);
                    default_empty_content.setVisibility(View.GONE);
                    if (null != mHistoryList && mHistoryList.size() != 0) {
                        mResultContent.setVisibility(View.GONE);
                        searchHistory.setVisibility(View.VISIBLE);
                    }

                } else {
                    pageCurrent = 0;
                    mUC_WorldProvider.worldSearch(pageSize, pageCurrent, mContentText, "search");
                    ShowListView();
                }


            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mListAdapter.clear();

        String mContentText = mTxtSearch.getText().toString();

        if (mContentText.equals("") || mContentText.isEmpty()) {
            toastMsg("请输入你要查询的车名");
        } else {
//            mSearchProvider.SearchCar(mContentText);
//            setData1();
            pageCurrent = 0;
            mUC_WorldProvider.worldSearch(pageSize, pageCurrent, mContentText, "search"); //请求车列表
        }
    }



    @Override
    protected void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.used_car_txt_cancel_button) {//取消
            UC_World_SearchActivity.this.finish();
        } else if (view.getId() == R.id.used_car_txt_cancel) {//清空
            if (null != mHistoryList && mHistoryList.size() != 0) {
                mHistoryList.clear();
                historyListAdapter.clear();
                FrameEtongApplication.getApplication().clearSearchUsedCarHistory();
            }
            searchHistory.setVisibility(View.GONE);
            ShowListView();
            mListViewResult.setVisibility(View.GONE);
            number = 0;


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHistoryList = FrameEtongApplication.getApplication().getSearchUsedCarHistory();//获得搜索过的历史记录
        if (mHistoryList != null) {
            for (int i = 0; i < mHistoryList.size(); i++){  //外循环是循环的次数
                for (int j = mHistoryList.size() - 1; j > i; j--) {  //内循环是 外循环一次比较的次数
                    if (mHistoryList.get(i).equals(mHistoryList.get(j))) {
                        mHistoryList.remove(j);
                    }
                }
            }
        }
        if (null != mHistoryList && mHistoryList.size() != 0) {
            searchHistory.setVisibility(View.VISIBLE);
            historyList.setAdapter(historyListAdapter);
            historyListAdapter.clear();
            historyListAdapter.update(mHistoryList);
        }

    }

    /**
     * @desc (处理搜索出来的数据)
     * @createtime 2016/11/30 - 11:26
     * @author xiaoxue
     */
    @Subscriber(tag = FrameHttpTag.CARLIST + "search")
    protected void getCarList(HttpMethod method) {
        ShowListView();
        String errno = method.data().getString("errno");
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");
        if (!TextUtils.isEmpty(flag) && flag.equals("0")) {
//            JSONObject data = method.data().getJSONObject("data");
//            JSONArray carlist = data.getJSONArray("carList");
            JSONArray array = method.data().getJSONArray("data");
            if (pageCurrent == 0) {
                mListData.clear();
            }
            if (0 != array.size()) {
                //添加数据
                for (int i = 0; i < array.size(); i++) {
                    UC_World_CarListJavaBean mCF_LoanListBean = JSON.toJavaObject((JSON) array.get(i), UC_World_CarListJavaBean.class);
                    mListData.add(mCF_LoanListBean);
                }
                if (!mListData.isEmpty()) {
                    mListViewResult.setMode(PullToRefreshBase.Mode.PULL_FROM_END);//上拉
                }
                mListAdapter.updateSearchCarList(mListData, true);
                //显示头部
                mResultHeadLayout.setVisibility(View.VISIBLE);
                mResultHead.setText("找到" + (mListData.size()) + "条相关车系");
                //判断全部数据是否加载完
                if (array.size() < pageSize) {
                    if (pageCurrent != 0) {
                        toastMsg("已加载全部");
                    }
                    mListViewResult.onRefreshComplete();
                    mListViewResult.setMode(PullToRefreshBase.Mode.DISABLED);
                }
            } else {
                if (array.size() == 0) {
                    //判断全部数据是否加载完
                    if (pageCurrent != 0) {
                        toastMsg("已加载全部");
                        mListViewResult.onRefreshComplete();
                        mListViewResult.setMode(PullToRefreshBase.Mode.DISABLED);
                    } else {
                        ShowNullView("数据未找到,请重新输入", false, setEmptyViewUtil.OtherView);
                        mListViewResult.onRefreshComplete();
                    }
                }
            }
        } else if (flag.equals(HttpPublisher.NETWORK_ERROR)) {//网络
            ShowNullView("亲,网络不给力哦\n点击屏幕重试", true, setEmptyViewUtil.NetworkErrorView);
        } else if (flag.equals(HttpPublisher.DATA_ERROR)) {//服务
            ShowNullView("亲,您访问的页面找不到了", true, setEmptyViewUtil.NoServerView);
        }
        mListViewResult.onRefreshComplete();
    }

    /**
     * @desc (显示为空视图)
     * @createtime 2016/11/14 0014-16:11
     * @author wukefan
     */
    protected void ShowNullView(String text, boolean isClick, int type) {
        default_empty_content.setVisibility(View.VISIBLE);
        mResultContent.setVisibility(View.VISIBLE);
        mListViewResult.setVisibility(View.GONE);
//        mNullImg.setBackgroundResource(image);
//        default_empty_textview.setText(text);
//        default_empty_content.setClickable(isClick);
        setEmptyViewUtil.setView(type, text, isClick);
    }

    /**
     * @desc (显示listview视图)
     * @createtime 2016/11/14 0014-16:11
     * @author wukefan
     */
    protected void ShowListView() {
        mResultContent.setVisibility(View.VISIBLE);
        mListViewResult.setVisibility(View.VISIBLE);
        default_empty_content.setVisibility(View.GONE);
    }

    //历史记录的listview item 点击回调方法
    @Override
    public void answerIntent(String history_text) {
        mTxtSearch.setText(history_text);
        mTxtSearch.setSelection(mTxtSearch.getText().toString().length());
        mContentText = mTxtSearch.getText().toString();
        pageCurrent = 0;
        mUC_WorldProvider.worldSearch(pageSize, pageCurrent, mContentText, "search");
    }
}
