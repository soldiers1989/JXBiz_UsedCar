package com.etong.android.jxappfours.find_car.collect_search.main_content;

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
import com.etong.android.frame.publisher.SharedPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.request_init.FrameHttpUri;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.utils.ListAdapter;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.collect_search.adapter.FC_HistoryListAdapter;
import com.etong.android.jxappfours.find_car.collect_search.adapter.FC_HotSearchAdapter;
import com.etong.android.jxappfours.find_car.collect_search.adapter.FC_SearchListAdapter;
import com.etong.android.jxappfours.find_car.collect_search.javabean.Find_Car_Search_Result;
import com.etong.android.frame.widget.EtongNoScrollGridView;
import com.etong.android.frame.widget.EtongNoScrollListView;
import com.etong.android.jxappfours.find_car.collect_search.utils.Find_Car_SearchProvider;
import com.lzy.okhttputils.model.HttpParams;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 搜索界面
 * Created by Administrator on 2016/8/9.
 */
public class Find_Car_SearchActivity extends BaseSubscriberActivity implements AdapterView.OnItemClickListener,
        FC_HistoryListAdapter.HistoryCallBack,FC_HotSearchAdapter.HistoryCallBack {
    private HttpPublisher mHttpPublisher;
    private SharedPublisher mSharedPublisher;
    private String USER_SHARED="user_shared";
    private ArrayAdapter<String> arrayAdapter;
    private List<String> vechileName = new ArrayList<String>();
    private AutoCompleteTextView search_text;
    private TextView cancel;
    private ListView list_result;
    private String mContentText;
//    private ListAdapter<String> hotListAdapter;
//    private ListAdapter<String> historyListAdapter;
    private ImageProvider mImageProvider;
    private String[] hotSearch={"丰田","福特","卡罗拉","众泰T600","马自达","明锐","帕萨特","途观"};
//    private ListAdapter<Find_Car_Search_Result> mListAdapter;
    private TextView  result_head;
    private Find_Car_SearchProvider mSearchProvider;
    private List<String> mHistoryList = new ArrayList<String>();
    private LinearLayout search_history;
    private LinearLayout hot_history;
    private LinearLayout ll_result_head;
    private int number=0;
    EtongNoScrollListView history_list;       // 搜索历史的ListView
    private LinearLayout default_empty_content;
    private ViewGroup more_result_content;
    private TextView default_empty_lv_textview;
    private LinearLayout fc_ll_hot_search;
    private FC_HistoryListAdapter historyListAdapter;
    private FC_SearchListAdapter mListAdapter;
    private List<Find_Car_Search_Result> list_data=new ArrayList<>();
    private FC_HotSearchAdapter hotListAdapter;
    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.find_car_activity_search);
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(this);
        // 初始化缓存
        mSharedPublisher = SharedPublisher.getInstance();
        mSharedPublisher.initialize(this);
        mSearchProvider = Find_Car_SearchProvider.getInstance();
        mSearchProvider.initialize(mHttpPublisher);
        search_text=findViewById(R.id.find_car_tct_search_content,AutoCompleteTextView.class);
        mContentText = search_text.getText().toString();

        cancel=findViewById(R.id.find_car_txt_cancel_button,TextView.class);
        more_result_content =findViewById(R.id.find_car_search_more_result_content,ViewGroup.class);
        default_empty_content =findViewById(R.id.default_empty_content,LinearLayout.class);
        default_empty_content.setVisibility(View.GONE);
        list_result = findViewById(R.id.find_car_search_more_result, ListView.class);
        //添加头部view
        View view=	LayoutInflater.from(this).inflate(R.layout.find_car_result_head_view, null);
        ll_result_head =(LinearLayout) view.findViewById(R.id.find_car_ll_result_head);
        result_head=(TextView) view.findViewById(R.id.find_car_txt_result_head);
//        result_head.setText("找到"+list_result.getCount()+"条相关车系");
        ll_result_head.setVisibility(View.GONE);
        list_result.addHeaderView(view);
        list_result.setVisibility(View.GONE);
        hot_history=findViewById(R.id.find_car_hot_history,LinearLayout.class);
        fc_ll_hot_search =findViewById(R.id.fc_ll_hot_search,LinearLayout.class);
        search_history=findViewById(R.id.find_car_ll_search_history,LinearLayout.class);
        EtongNoScrollGridView hot_search=findViewById(R.id.find_car_gv_hot_data, EtongNoScrollGridView.class);
        history_list=findViewById(R.id.find_car_lv_history_list, EtongNoScrollListView.class);
        TextView clear=findViewById(R.id.find_car_txt_cancel,TextView.class);
        addClickListener(R.id.find_car_txt_cancel);
        addClickListener(R.id.find_car_txt_cancel_button);
        //热门搜索
        hotListAdapter=new FC_HotSearchAdapter(this,Find_Car_SearchActivity.this);
        hot_search.setAdapter(hotListAdapter);
        hotListAdapter.update(Arrays.asList(hotSearch));

        //搜索历史
        historyListAdapter=new FC_HistoryListAdapter(this,Find_Car_SearchActivity.this);
        history_list.setAdapter(historyListAdapter);

        mHistoryList= FrameEtongApplication.getApplication().getSearchHistory();//获得搜索过的历史记录
        if(mHistoryList!=null ){
            for (int i = 0; i < mHistoryList.size(); i++) { //外循环是循环的次数
                for (int j = mHistoryList.size() - 1 ; j > i; j--) {  //内循环是 外循环一次比较的次数
//                    if(mHistoryList.get(i).equals("")){
//                        mHistoryList.remove(i);
//                    }
                    if (mHistoryList.get(i).equals(mHistoryList.get(j))){
                        mHistoryList.remove(j);
                    }
                }
            }
            historyListAdapter.update(mHistoryList);
            search_history.setVisibility(View.VISIBLE);
        }else{
            search_history.setVisibility(View.GONE);
        }
        //输入框
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, vechileName);
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
                        toastMsg("请输入你要查询的车名");
                    } else {
                        mSearchProvider.SearchCar(mContentText);
                    }
                }
                return false;
            }
        });
        initVechileList();
        search_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                mContentText=	search_text.getText().toString();
                if (("").equals(mContentText) && TextUtils.isEmpty(mContentText)) {
                    number=0;
                    mListAdapter.clear();
                    ll_result_head.setVisibility(View.GONE);
                    System.out.println("数据被清除了");
                    list_result.setVisibility(View.GONE);
                    hot_history.setVisibility(View.VISIBLE);
                    fc_ll_hot_search.setVisibility(View.VISIBLE);
                    default_empty_content.setVisibility(View.GONE);
                    if(null!=mHistoryList && mHistoryList.size()!=0){
                        search_history.setVisibility(View.VISIBLE);
                    }
                }else{
                    /*//回删就不请求接口
                    if(number<=mContentText.length()){
                        mSearchProvider.SearchCar(mContentText);
                        number=mContentText.length();
                        more_result_content.setVisibility(View.VISIBLE);
                        list_result.setVisibility(View.VISIBLE);
                        hot_history.setVisibility(View.GONE);
                    }*/
                    mSearchProvider.SearchCar(mContentText);
                    more_result_content.setVisibility(View.VISIBLE);
                    list_result.setVisibility(View.VISIBLE);
                    hot_history.setVisibility(View.GONE);
                    fc_ll_hot_search.setVisibility(View.VISIBLE);
                }
            }
        });
        default_empty_lv_textview = findViewById(R.id.default_empty_lv_textview,TextView.class);
        default_empty_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //刷新数据
                mSearchProvider.SearchCar(mContentText);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        mHistoryList= FrameEtongApplication.getApplication().getSearchHistory();//获得搜索过的历史记录
        if(mHistoryList!=null ) {
            for (int i = 0; i < mHistoryList.size(); i++){  //外循环是循环的次数
                for (int j = mHistoryList.size() - 1; j > i; j--) {  //内循环是 外循环一次比较的次数
                    if (mHistoryList.get(i).equals(mHistoryList.get(j))) {
                        mHistoryList.remove(j);
                    }
                }
            }
        }
        if(null!=mHistoryList && mHistoryList.size()!=0){
            search_history.setVisibility(View.VISIBLE);
            history_list.setAdapter(historyListAdapter);
            historyListAdapter.clear();
            historyListAdapter.update(mHistoryList);
        }
    }

    protected void initVechileList() {
        mImageProvider = ImageProvider.getInstance();
        //得到搜索出来数据的adapter
        mListAdapter=new FC_SearchListAdapter(this);
        list_result.setAdapter(mListAdapter);
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            mListAdapter.clear();
            String mContentText = search_text.getText().toString();
            if (mContentText.equals("") || mContentText.isEmpty()) {
                toastMsg("请输入你要查询的车名");
            } else {
                mSearchProvider.SearchCar(mContentText);
            }
        };

    @Override
    protected void onClick(View view) {
        super.onClick(view);
        if(view.getId()==R.id.find_car_txt_cancel){//清空
            if(null!=mHistoryList && mHistoryList.size()!=0){
                mHistoryList.clear();
                FrameEtongApplication.getApplication().clearSearchHistory();
            }
            search_history.setVisibility(View.GONE);
            number=0;
        }else if(view.getId()==R.id.find_car_txt_cancel_button){//取消
            Find_Car_SearchActivity.this.finish();
        }
    }

    @Subscriber(tag= FrameHttpTag.BY_NAME_SELECT_CAR_SERIES)
    protected void getResult(HttpMethod method){
        HttpParams params=method.getHttpParams();
        String key= params.urlParamsMap.get("key").get(0);
        if(!mContentText.equals(key)){
            return;
        }
        mListAdapter.clear();
        String ptError = method.data().getString("ptError");
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");
        String succeed=method.data().getString("succeed");
        String errCode = method.data().getString("errCode");
        if(!TextUtils.isEmpty(flag) && flag.equals("0")){
            more_result_content.setVisibility(View.VISIBLE);
            default_empty_content.setVisibility(View.GONE);
            list_result.setVisibility(View.VISIBLE);
                JSONArray jsonArr = method.data().getJSONArray("data");
                for (int i=0;i<jsonArr.size();i++) {
                    Find_Car_Search_Result mFind_Car_Search_Result = JSON.toJavaObject(jsonArr.getJSONObject(i), Find_Car_Search_Result.class);
                    list_data.add(mFind_Car_Search_Result);
                }
                mListAdapter.update(list_data);
                arrayAdapter.notifyDataSetChanged();
                ll_result_head.setVisibility(View.VISIBLE);
                result_head.setText("找到"+(list_result.getCount()-1)+"条相关车系");
            } else if (!TextUtils.isEmpty(flag) && flag.equals(HttpPublisher.NETWORK_ERROR)) {
            more_result_content.setVisibility(View.VISIBLE);
            list_result.setVisibility(View.GONE);
//            LinearLayout default_empty_content=findViewById(R.id.default_empty_content,LinearLayout.class);
            default_empty_content.setVisibility(View.VISIBLE);
            default_empty_content.setClickable(true);
            default_empty_lv_textview.setText("网络不给力，点击重试");
        } else{
            more_result_content.setVisibility(View.VISIBLE);
            list_result.setVisibility(View.GONE);
//            LinearLayout default_empty_content=findViewById(R.id.default_empty_content,LinearLayout.class);
            default_empty_content.setVisibility(View.VISIBLE);
            TextView default_empty_lv_textview = findViewById(R.id.default_empty_lv_textview,TextView.class);
            default_empty_lv_textview.setText("数据未找到,请重新输入");
            default_empty_content.setClickable(false);
        }

    }

    //历史记录的listview item 点击回调方法
    @Override
    public void answerIntent(String history_text) {
        search_text.setText(history_text);
        search_text.setSelection(search_text.getText().toString().length());
        mContentText = search_text.getText().toString();
        mSearchProvider.SearchCar(mContentText);
    }

    //热门搜索的gridview item 点击回调方法
    @Override
    public void Intent(String history_text) {
        search_text.setText(history_text);
//      Editable etext = search_text.getText();
//      Selection.setSelection(etext, etext.length());
        search_text.setSelection(search_text.getText().toString().length());
        mContentText = search_text.getText().toString();
        mSearchProvider.SearchCar(mContentText);
    }
}
