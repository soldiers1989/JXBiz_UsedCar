package com.etong.android.jxappmessage.content;

import android.content.Intent;
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
import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.utils.Etong_DateToStringUtil;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.utils.ListAdapter;
import com.etong.android.jxappmessage.R;
import com.etong.android.jxappmessage.adapter.MessageHistoryListAdapter;
import com.etong.android.jxappmessage.adapter.MessageListAdapter;
import com.etong.android.jxappmessage.javabean.MessageWebViewBean;
import com.etong.android.jxappmessage.utils.MessageNoScrollListView;
import com.etong.android.jxappmessage.utils.MessageProvider;
import com.lzy.okhttputils.model.HttpParams;

import org.simple.eventbus.Subscriber;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 搜索资讯页面
 * Created by Administrator on 2016/8/31.
 */
public class MessageSearchActivity extends BaseSubscriberActivity implements AdapterView.OnItemClickListener,MessageHistoryListAdapter.HistoryCallBack {
    private AutoCompleteTextView search_text;
    private TextView cancel;
    private ListView list_result;
    private String mContentText;
    private MessageNoScrollListView history_list;       // 搜索历史的ListView
    private LinearLayout default_empty_content;
    private ViewGroup more_result_content;
    private LinearLayout ll_result_head;
    private TextView result_head;
    private List<String> mHistoryList = new ArrayList<String>();
    private LinearLayout search_history;
//    private ListAdapter<String> historyListAdapter;
    private HttpPublisher mHttpPublisher;
    private MessageProvider mMessageProvider;
    private ImageProvider mImageProvider;
    private ArrayAdapter<String> arrayAdapter;
    private List<String> vechileName = new ArrayList<String>();
    private int number = 0;
//    private ListAdapter<MessageWebViewBean> mListAdapter;
    private TextView default_empty_lv_textview;
    private MessageHistoryListAdapter historyListAdapter;
    private MessageListAdapter mListAdapter;
    private List<MessageWebViewBean> list_data=new ArrayList<>();
    public static final int MESSAGE_SEARCH_ACTIVITY=1;
    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.message_activity_search);
        initView();
    }

    protected void initView() {
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(this);
        mMessageProvider = MessageProvider.getInstance();
        mMessageProvider.initialize(mHttpPublisher);
        mImageProvider = ImageProvider.getInstance();
        search_text = findViewById(R.id.find_car_tct_search_content, AutoCompleteTextView.class);
        mContentText = search_text.getText().toString();

        search_history = findViewById(R.id.find_car_ll_search_history, LinearLayout.class);
        cancel = findViewById(R.id.find_car_txt_cancel_button, TextView.class);
        more_result_content = findViewById(R.id.find_car_search_more_result_content, ViewGroup.class);
        default_empty_content = findViewById(R.id.default_empty_content, LinearLayout.class);
        default_empty_content.setVisibility(View.GONE);
        list_result = findViewById(R.id.find_car_search_more_result, ListView.class);
        //添加头部view
        View view = LayoutInflater.from(this).inflate(R.layout.message_result_head_view, null);
        ll_result_head = (LinearLayout) view.findViewById(R.id.find_car_ll_result_head);
        result_head = (TextView) view.findViewById(R.id.find_car_txt_result_head);
//        result_head.setText("找到"+list_result.getCount()+"条相关车系");
        ll_result_head.setVisibility(View.GONE);
        list_result.addHeaderView(view);
        list_result.setVisibility(View.GONE);
        history_list = findViewById(R.id.find_car_lv_history_list, MessageNoScrollListView.class);
        TextView clear = findViewById(R.id.find_car_txt_cancel, TextView.class);

        default_empty_lv_textview = findViewById(R.id.default_empty_lv_textview, TextView.class);

        default_empty_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //刷新数据
                mMessageProvider.MessageSearchList(mContentText, 1);
            }
        });
        addClickListener(R.id.find_car_txt_cancel);
        addClickListener(R.id.find_car_txt_cancel_button);
        //搜索历史
        historyListAdapter=new MessageHistoryListAdapter(this,MessageSearchActivity.this);
        history_list.setAdapter(historyListAdapter);

        mHistoryList = FrameEtongApplication.getApplication().getSearchMessageHistory();//获得搜索过的历史记录
        if (mHistoryList != null) {
            for (int i = 0; i < mHistoryList.size(); i++){  //外循环是循环的次数
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
            search_history.setVisibility(View.VISIBLE);
            more_result_content.setVisibility(View.GONE);
        } else {
            search_history.setVisibility(View.GONE);
            more_result_content.setVisibility(View.VISIBLE);
        }

        //输入框
        arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, vechileName);

        search_text.setAdapter(arrayAdapter);

        search_text.setThreshold(1);
        search_text.setOnItemClickListener(this);

        //对软键盘进行监听
        search_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                // TODO Auto-generated method stub
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//搜索
                    mListAdapter.clear();
                    mContentText = search_text.getText().toString();
                    if (mContentText.equals("") || mContentText.isEmpty()) {
                        toastMsg("请输入你要查询的内容");
                    } else {
                        mMessageProvider.MessageSearchList(mContentText, 1);
                    }
                }
                return false;
            }
        });
        initVechileList();

        search_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {
                mContentText = search_text.getText().toString();
                if (("").equals(mContentText) || TextUtils.isEmpty(mContentText)) {
                    number = 0;
                    mListAdapter.clear();
                    ll_result_head.setVisibility(View.GONE);
                    System.out.println("数据被清除了");
                    more_result_content.setVisibility(View.VISIBLE);
                    list_result.setVisibility(View.GONE);
                    default_empty_content.setVisibility(View.GONE);
                    if (null != mHistoryList && mHistoryList.size() != 0) {
                        more_result_content.setVisibility(View.GONE);
                        search_history.setVisibility(View.VISIBLE);
                    }
                } else {
                    //回删不请求
//                    if(number<=mContentText.length()){
//                        mMessageProvider.MessageSearchList(mContentText,true);
//                        number=mContentText.length();
//
//                        more_result_content.setVisibility(View.VISIBLE);
//                        list_result.setVisibility(View.VISIBLE);
//                    }
                    mMessageProvider.MessageSearchList(mContentText, 1);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHistoryList = FrameEtongApplication.getApplication().getSearchMessageHistory();//获得搜索过的历史记录
        if (mHistoryList != null) {
            for (int i = 0; i < mHistoryList.size(); i++) { //外循环是循环的次数
                for (int j = mHistoryList.size() - 1; j > i; j--) { //内循环是 外循环一次比较的次数
                    if (mHistoryList.get(i).equals(mHistoryList.get(j))) {
                        mHistoryList.remove(j);
                    }
                }
            }
        }
        if (null != mHistoryList&& mHistoryList.size() != 0) {
            search_history.setVisibility(View.VISIBLE);
            history_list.setAdapter(historyListAdapter);
            historyListAdapter.clear();
            historyListAdapter.update(mHistoryList);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mListAdapter.clear();
        String mContentText = search_text.getText().toString();
        if (mContentText.equals("") || mContentText.isEmpty()) {
            toastMsg("请输入你要查询的内容");
        } else {
            mMessageProvider.MessageSearchList(mContentText, 1);
        }
    }

    protected void initVechileList() {
        mImageProvider = ImageProvider.getInstance();
        //搜索出来的资讯列表的数据的adapter
        mListAdapter=new MessageListAdapter(this);
        list_result.setAdapter(mListAdapter);
    }


    @Override
    protected void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.find_car_txt_cancel) {//清空
            if (null != mHistoryList&& mHistoryList.size() != 0) {
                mHistoryList.clear();
                historyListAdapter.clear();
                FrameEtongApplication.getApplication().clearSearchMessageHistory();
            }
            more_result_content.setVisibility(View.VISIBLE);
            list_result.setVisibility(View.GONE);
            search_history.setVisibility(View.GONE);
            number = 0;
        } else if (view.getId() == R.id.find_car_txt_cancel_button) {//取消
            MessageSearchActivity.this.finish();
        }
    }


    @Subscriber(tag = FrameHttpTag.MESSAGE_SEARCH_LIST)
    protected void getMessageSearch(HttpMethod method) {
        L.d("输入框：", mContentText.toString());
        HttpParams params = method.getHttpParams();
        String key = params.urlParamsMap.get("key").get(0);
        if (!mContentText.equals(key)) {
            return;
        }
        mListAdapter.clear();
        String errno = method.data().getString("errno");
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");
        String errCode = method.data().getString("errCode");
        if (!TextUtils.isEmpty(flag) && flag.equals(HttpPublisher.NETWORK_ERROR)) {
            more_result_content.setVisibility(View.VISIBLE);
            list_result.setVisibility(View.GONE);
//            LinearLayout default_empty_content=findViewById(R.id.default_empty_content,LinearLayout.class);
            default_empty_content.setVisibility(View.VISIBLE);
            default_empty_content.setClickable(true);
            default_empty_lv_textview.setText("网络不给力，点击重试");
            return;
        }
        if (errno.equals("PT_ERROR_SUCCESS")) {
            more_result_content.setVisibility(View.VISIBLE);
            default_empty_content.setVisibility(View.GONE);
            list_result.setVisibility(View.VISIBLE);
            JSONArray jsonArr = method.data().getJSONArray("data");
            if (jsonArr.size() == 0) {
                more_result_content.setVisibility(View.VISIBLE);
                list_result.setVisibility(View.GONE);
//            LinearLayout default_empty_content=findViewById(R.id.default_empty_content,LinearLayout.class);
                default_empty_content.setVisibility(View.VISIBLE);
                TextView default_empty_lv_textview = findViewById(R.id.default_empty_lv_textview, TextView.class);
                default_empty_lv_textview.setText("数据未找到,请重新输入");
                default_empty_content.setClickable(false);
//                search_text.setText(null);
                arrayAdapter.clear();
                ll_result_head.setVisibility(View.GONE);
                list_result.setVisibility(View.GONE);
                return;
            } else {
                arrayAdapter.clear();
                for (int i = 0; i < jsonArr.size(); i++) {
                    MessageWebViewBean mMessageWebViewBean = JSON.toJavaObject(jsonArr.getJSONObject(i), MessageWebViewBean.class);
                    list_data.add(mMessageWebViewBean);
                }
                mListAdapter.updateSearch(list_data,true);
                arrayAdapter.notifyDataSetChanged();
                ll_result_head.setVisibility(View.VISIBLE);
                result_head.setText("找到" + (list_result.getCount() - 1) + "条相关资讯");
            }
        }
    }

    //历史记录的listview item 点击回调方法
    @Override
    public void answerIntent(String history_text) {
        search_text.setText(history_text);
        search_text.setSelection(search_text.getText().toString().length());
        mContentText = search_text.getText().toString();
        mMessageProvider.MessageSearchList(mContentText, 1);
    }
}
