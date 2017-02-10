package com.etong.android.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baidu.mobstat.StatService;
import com.etong.android.data.DataContentProvider;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.jxappdata.ChartsActivity;
import com.etong.android.jxappdata.EtongApplication;
import com.etong.android.jxappdata.R;
import com.etong.android.jxappdata.WebViewActivity;
import com.etong.android.jxappdata.common.BaseFragment;
import com.etong.android.jxappdata.common.CommonEvent;
import com.etong.android.jxappdata.jsbridge.BridgeHandler;
import com.etong.android.jxappdata.jsbridge.BridgeWebView;
import com.etong.android.jxappdata.jsbridge.CallBackFunction;
import com.etong.android.util.SerializableObject;
import com.etong.android.view.HotFixRecyclerView;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChartFragment extends BaseFragment {
    private BridgeWebView chartView;
    private ImageView imageIcon;
    private TextView refreshButton;
    private LinearLayoutManager linearLayoutManager;
    // private RecyclerView recyclerView;
    private HotFixRecyclerView recyclerView;// 解决RecyclerView.stopscroll为空
    // 用的HotFixRecyclerView
    private MyRecyclerAdapter myAdapter;// recyclerView适配器
    public String[] months; // 月份、年份数组

    private HttpPublisher mHttpPublisher;
    private DataContentProvider mDataContentProvider;
    public ChartsActivity ca;
    public String fragmentName = "" + UUID.randomUUID().toString()
            + (int) (1 + Math.random() * (10000 - 1 + 1));// fragment标记
    public String[] urlArray;//地址
    public boolean isNeedUpdateScrool;// 是否需要在其他fragment底部滑动后，更新本fragment底部
    public int selectyear = getYear(new Date());//系统年
    public int selectIndex;
    public int fragmentIndex;
    public float mDensity;
    public int mWidth;
    public int mHeight;
    public String mTitlebarName;
    public String titleArray;
    public boolean isButtonVisibity = true;// 判断fragment是否显示下面的adapter
    ValueCallback<Uri> mUploadMessage;

    @Override
    protected View onInit(LayoutInflater inflater, ViewGroup container,
                          Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, container,
                false);
        initView(view);
        selectIndex = firstIndex();
        updateMonth(selectyear);
        //初始化请求数据
        initFirstRequest();


        this.myTitle = mTitlebarName + titleArray;


        return view;
    }

    // ============================载入视图=================================================================
    protected void initView(View view) {
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(getActivity());
        mDataContentProvider = DataContentProvider.getInstance();
        mDataContentProvider.initialize(mHttpPublisher);
        imageIcon = findViewById(view, R.id.iv_come, ImageView.class);
        refreshButton = findViewById(view, R.id.tv_refresh, TextView.class);
        chartView = findViewById(view, R.id.chartshow_wb, BridgeWebView.class);
        chartView.getSettings().setAllowFileAccess(true);
//		chartView.setDefaultHandler(new DefaultHandler());
//
//		chartView.setWebChromeClient(new WebChromeClient() {
//
//			@SuppressWarnings("unused")
//			public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType, String capture) {
//				this.openFileChooser(uploadMsg);
//			}
//
//			@SuppressWarnings("unused")
//			public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType) {
//				this.openFileChooser(uploadMsg);
//			}
//
//			public void openFileChooser(ValueCallback<Uri> uploadMsg) {
//				mUploadMessage = uploadMsg;
////				pickFile();
//			}
//		});
        // 开启脚本支持
        chartView.getSettings().setJavaScriptEnabled(true);
        chartView.loadUrl("file:///android_asset/myechart.html");


        chartView.registerHandler("submitFromWeb", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                int version = Integer.valueOf(android.os.Build.VERSION.SDK);
                if (version < 23) {
                    Intent intent = new Intent(ca, WebViewActivity.class);
//                    Intent intent = new Intent(getActivity(), WebViewActivity.class);
                    Map map = new HashMap<>();
                    map.put("data", data);
                    final SerializableObject myMap = new SerializableObject();
                    myMap.setObject(map);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("dataMap", myMap);
                    intent.putExtras(bundle);
                    startActivity(intent);

//                    getActivity().finish();
                }

            }

        });


        addClickListener(view, R.id.tv_refresh);

        recyclerView = findViewById(view, R.id.recyclerView,
                HotFixRecyclerView.class);

        linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        if (isButtonVisibity) {
            // recyclerView适配器
            myAdapter = new MyRecyclerAdapter(getActivity(),
                    new ArrayList<String>(Arrays.asList(months)));

            recyclerView.setAdapter(myAdapter);
            // 默认选中系统年 （最后一项）
            if (!isNeedUpdateScrool) {
                myAdapter.setSelectItem(myAdapter.getItemCount() - 1);
            }
        } else {
            recyclerView.setVisibility(View.GONE);
        }

    }


    public void initFirstRequest() {

        firstRequest(formatDataDate(true), firstIndex());
    }

    // ============================adapter=============================================================================

    public class MyRecyclerAdapter extends
            RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {
        ArrayList<String> mData;

        public MyRecyclerAdapter(Context context, ArrayList<String> mData) {
            this.mData = mData;
        }

        public void add(String str) {
            mData.add(str);
            this.notifyDataSetChanged();
        }

        public void addAll(Collection<? extends String> data) {
            mData.addAll(data);
            this.notifyDataSetChanged();
        }

        public void clear(int position) {
            mData.clear();
            mPosition = position;
            // linearLayoutManager.scrollToPositionWithOffset(0, 0);//
            // 移动到（0,0）位置
            linearLayoutManager.scrollToPositionWithOffset(position,
                    (int) ((mWidth - getMarginWidth() - getSmallWidth()) / 2));//移动到中间位置

            this.notifyDataSetChanged();
        }

        // 设置选中项
        public void setSelectItem(String str) {
            for (int i = 0; i < mData.size(); i++) {
                if (str.equals(mData.get(i))) {
                    setSelectItem(i);
                    break;
                }
            }
        }

        // 设置选中项
        public void setSelectItem(int position) {
            mPosition = position;
            linearLayoutManager.scrollToPositionWithOffset(position,
                    (int) ((mWidth - getMarginWidth() - getSmallWidth()) / 2));
            // linearLayoutManager.scrollToPositionWithOffset(
            // position,
            //
            // recyclerView.getWidth() / 2
            // - viewHolder.itemView.getWidth() / 2);// recyclerView移动的距离
            this.notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        int mPosition = 0;

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {// 绑定数据
            holder.mRadio.setText(mData.get(position));
            if (mPosition == position) {
                holder.mRadio.setChecked(true);
            } else {
                holder.mRadio.setChecked(false);
            }

            holder.mRadio.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    StatService.onEvent(getContext(), "charts", "切换图表", 1);
                    linearLayoutManager
                            .scrollToPositionWithOffset(
                                    position,
                                    (int) ((mWidth - getMarginWidth() - getSmallWidth()) / 2));

                    mPosition = position;
                    selectIndex = position;


                    if (!isNeedUpdateScrool) {
                        selectyear = Integer.parseInt((months[position])
                                .substring(0, 4));
                        EventBus.getDefault().post(selectyear, "updateMonth");
                    }

                    requestData(formatDataDate(false), mPosition + 1);

                    notifyDataSetChanged();
                }
            });
        }

        ViewHolder viewHolder;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
            View view = LayoutInflater.from(arg0.getContext()).inflate(
                    R.layout.activity_chart_bottom_adapter, null);
            viewHolder = new ViewHolder(view);

            viewHolder.mRadio = (RadioButton) view
                    .findViewById(R.id.bottom_text);

            return viewHolder;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View arg0) {
                super(arg0);
            }

            RadioButton mRadio;
        }
    }

    // ============================
    // util=============================================================================
    // 获取系统时间
    public static final String getSimpDate() {

        String curTime = "";

        SimpleDateFormat formatter;

        java.util.Date currentDate = new java.util.Date();

        formatter = new SimpleDateFormat("yyyy-MM-dd");

        currentDate = Calendar.getInstance().getTime();

        curTime = formatter.format(currentDate);

        return curTime;

    }

    // 初始化选中当前年、当前月
    public int firstIndex() {
        if (isNeedUpdateScrool) {
            Date date = new Date();
            // int month=getMonth(date)+1;
            return getMonth(date);
        }
        return months.length - 1;
    }


    //格式化年月
    public String formatDataDate(boolean isNow) {
        String outDate = "";
        if (isNow) {
            Date date = new Date();
            outDate = getYear(date) + "";
            if (isNeedUpdateScrool) {
                outDate = getYear(date) + "-"
                        + String.format("%02d", getMonth(date));
            }
            return outDate;
        }
        if (isNeedUpdateScrool) {
            outDate = selectyear + "-" + String.format("%02d", selectIndex + 1);

        } else {
            outDate = selectyear + "";
        }
        return outDate;
    }

    // 自定义一个loadStart
    public void loadStartFromActivity(String str, int sec) {
        loadStart(str, sec);
    }

    //根据年判断显示几个月
    public int numOfMonthInYear(int year) {
        Date date = new Date();

        int yy = getYear(date);
        if (year == yy) {
            return getMonth(date);
            // return Integer.valueOf(month);
        }
        return 12;
    }


    @Subscriber(tag = "updateMonth")
    public void updateMonth(int year) {
        if (!isNeedUpdateScrool) {
            return;
        }
        int numOfMonth = numOfMonthInYear(year);
        if (selectIndex > (numOfMonth - 1)) {
            selectIndex = numOfMonth - 1;
        }
        if (selectyear != year) {
            selectyear = year;
            requestData(formatDataDate(false), selectIndex);
        }
        if (myAdapter.getItemCount() == numOfMonth) {
            return;
        }
        myAdapter.clear(selectIndex);
        for (int i = 0; i < numOfMonth; i++) {
            myAdapter.add(months[i]);
        }

        myAdapter.notifyDataSetChanged();
    }

    // ============================数据请求=============================================================================

	/*
     * 1.初始化请求数据 获取选中index 生成时间参数
	 */

    public void firstRequest(String time, int seletIndex) {
        requestData(time, seletIndex);

    }

    public void requestData(String time, int selectIndex) {
        loadStart("数据加载中...", 0);
        for (String url : urlArray) {
            mDataContentProvider.sendRequestData(
                    isButtonVisibity ? time : null, url, selectIndex,
                    fragmentName);
        }

    }

    // ============================数据处理=============================================================================
    // 服务器返回数据的处理
    @Subscriber(tag = CommonEvent.CHART)
    protected void setChart(HttpMethod method) {
//		loadFinish();
        if (!(fragmentName.equals(method.getParam().get("tagName").toString()))) {
            loadFinish();
            return;
        }

        // int flags = (int) method.getParam().get("ExtarTime");// 得到month参数
        // 并设置选中
        String[] strArr = method.getUrl().split("/");//截取url的后面一段  保证传给html的id的唯一
        // myAdapter.setSelectItem(flags);
        showWebView();
        // if (!EtongApplication.getApplication()
        // .isNetworkAvailable(getActivity())) {
        // toastMsg("请检查网络");
        // }
//        String errCode = method.data().getString("errCode");
//        if (errCode != null && errCode.equals("4353")) {
//            // toastMsg("请检查网络");
//            hideWebView();
//            loadFinish();
//            return;
//        }

        String errno = method.data().getString("errno");
        String message = method.data().getString("msg");
        String flag = method.data().getString("flag");
        if (!flag.equals("0")) {
            toastMsg(message);
            hideWebView();
            loadFinish();
            return;
        }
        // if(!DataContentProvider.ORDER_SUCCEED.equals(errno)){
        // toastMsg("获取数据失败!", errno);
        // return;
        // }
        JSONObject obj = method.data().getJSONObject("data");

        String startTime = obj.getString("startTime");
        String endTime = obj.getString("endTime");
        Object data = obj.get("amountDatas");


//		loadFinish();


        JSONObject object = new JSONObject();

        object.put("id", fragmentIndex + strArr[(strArr.length - 1)]);
        // object.put("height","48%");
        object.put("data", data);
        String jsonString = JSON.toJSONString(object);


//		StatService.bindJSInterface(getContext(), chartView);

        chartView.callHandler("functionInJs", jsonString,
                new CallBackFunction() {

                    @Override
                    public void onCallBack(String data) {

                    }

                });


        loadFinish();
    }

    public void showWebView() {
        imageIcon.setVisibility(View.GONE);
        refreshButton.setVisibility(View.GONE);
        chartView.setVisibility(View.VISIBLE);
    }

    public void hideWebView() {
        imageIcon.setVisibility(View.VISIBLE);
        refreshButton.setVisibility(View.VISIBLE);
        chartView.setVisibility(View.GONE);
    }

    @Override
    protected void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.tv_refresh:
//			StatService.onEvent(getContext(), "refresh", "点击刷新", 1);
//			requestData(formatDataDate(false), selectIndex);
//			if (!EtongApplication.getApplication().isNetworkAvailable(
//					getActivity())) {
//				toastMsg("无网络连接，请检查网络");
//			}
//			break;
//		default:
//			break;
//		}

        if (R.id.tv_refresh == v.getId()) {
            StatService.onEvent(getContext(), "refresh", "点击刷新", 1);
            requestData(formatDataDate(false), selectIndex);
            if (!EtongApplication.getApplication().isNetworkAvailable(
                    getActivity())) {
                toastMsg("无网络连接，请检查网络");
            }
        }
    }

    public int getYear(Date date) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    public int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    // float small_width = 63 * mDensity + 0.5f;
    // float margin_width = 60 * mDensity + 0.5f;
    public float getSmallWidth() {
        return 63 * mDensity + 0.5f;
    }

    public float getMarginWidth() {
        return 60 * mDensity + 0.5f;
    }
}
