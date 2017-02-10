package com.etong.android.jxappdata.personality;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.data.DataContentProvider;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.utils.ListAdapter;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappdata.R;
import com.etong.android.jxappdata.SubscriberActivity;
import com.etong.android.jxappdata.common.CommonEvent;
import com.etong.android.jxappdata.jsbridge.BridgeWebView;
import com.etong.android.jxappdata.jsbridge.CallBackFunction;
import com.etong.android.util.AddCommaToMoney;
import com.etong.android.util.PopupWindowUtil;
import com.etong.android.util.SetTextColorBySymbolUtils;

import org.simple.eventbus.Subscriber;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 某个用户4S店销售统计页面
 *
 * @author Administrator
 */
public class BossDetailsViewActivity extends SubscriberActivity {
    private TitleBar mTitleBar;
    public int selectmonth = getMonth(new Date());//系统月
    public int year = getYear(new Date());
    String selectmonths[];
    private PopupWindow mPopWindow;
    private TextView tv_sale;
    private TextView tv_sale_increase;
    private TextView tv_gross_margin;
    private TextView tv_gross_increase;
    private ListView listView;
    BridgeWebView chartView;
    LinearLayout ll_item_title;
    ImageView image_sale;
    ImageView image_margin;
    ListAdapter<UserSaleItem> mAdapter;
    NumberFormat mNumberFormat = NumberFormat.getPercentInstance();
    String month;
    private DataContentProvider mDataContentProvider;
    private HttpPublisher mHttpPublisher;
    String userId;
    String sumTime;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        setContentView(R.layout.activity_item_data);
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(this);
        mDataContentProvider = DataContentProvider.getInstance();
        mDataContentProvider.initialize(mHttpPublisher);
        mTitleBar = new TitleBar(this);
        mTitleBar = new TitleBar(this);
        mTitleBar.showBackButton(true);
        mTitleBar.showNextButton(true);
//		mTitleBar.setNextButton(selectyear+"年");
        if (selectmonth > 4) {
            selectmonths = new String[4];
            for (int i = 0; i < 4; i++) {
                selectmonths[i] = selectmonth + "月";
                selectmonth--;
            }
        } else {
            selectmonths = new String[selectmonth];
            for (int i = 0; i < selectmonths.length; i++) {
                selectmonths[i] = selectmonth + "月";
                selectmonth--;
            }
        }
        mTitleBar.setNextOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
//				StatService.onEvent(DataContentActivity.this, "more", "更多", 1);

                final List<String> items = Arrays.asList(selectmonths);


                final PopupWindowUtil popupWindow = new PopupWindowUtil(BossDetailsViewActivity.this, items);
                popupWindow.setItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//			            	getClassInfo(items.get(position).replace("月", ""));
                        mDataContentProvider.getContentListData(year + "-" + String.format("%02d", Integer.valueOf((items.get(position).replace("月", "")))), userId);
                        sumTime = year + "-" + String.format("%02d", Integer.valueOf((items.get(position).replace("月", ""))));
                        mTitleBar.setNextButton(items.get(position));
                        popupWindow.dismiss();

                    }
                });
                //根据后面的数据调节窗口的宽度
                popupWindow.show(v, 2);
            }
        });
        Intent intent = getIntent();
        userId = intent.getStringExtra("id");
        month = intent.getStringExtra("month");
        sumTime = intent.getStringExtra("time");
        mTitleBar.setNextButton(month);
        mTitleBar.setTitle(intent.getStringExtra("name"));
        initView();
    }

    public void initView() {
//		ll_item_title=findViewById(R.id.ll_item_title, LinearLayout.class);

        tv_sale = findViewById(R.id.tv_sale, TextView.class);
        tv_sale_increase = findViewById(R.id.tv_sale_increase, TextView.class);
        tv_gross_margin = findViewById(R.id.tv_gross_margin, TextView.class);
        tv_gross_increase = findViewById(R.id.tv_gross_increase, TextView.class);
        image_sale = findViewById(R.id.image_sale, ImageView.class);
        image_margin = findViewById(R.id.image_margin, ImageView.class);
        listView = findViewById(R.id.listView, ListView.class);
        chartView = findViewById(R.id.chartshow_wb, BridgeWebView.class);
        chartView.getSettings().setAllowFileAccess(true);
        chartView.getSettings().setJavaScriptEnabled(true);
        chartView.loadUrl("file:///android_asset/myechart.html");

        mAdapter = new ListAdapter<UserSaleItem>(this, R.layout.activity_list_item_adapter_group_run) {

            @SuppressLint("NewApi")
            @SuppressWarnings("deprecation")
            @Override
            protected void onPaint(View view, final UserSaleItem data, int position) {


                TextView name = (TextView) view.findViewById(R.id.tv_company_name);
                TextView tv_salenum = (TextView) view.findViewById(R.id.tv_sale_num);//销售额
                TextView tv_margins_num = (TextView) view.findViewById(R.id.tv_margins_num);//毛利
                TextView tv_sale_trend = (TextView) view.findViewById(R.id.tv_sale_trend);//销售额增长率
                TextView tv_margins_trend = (TextView) view.findViewById(R.id.tv_margins_trend);//毛利增长率


                name.setText(data.getDept_name());
                tv_salenum.setText(AddCommaToMoney.AddCommaToMoney(String.format("%.2f", data.getSale_amount())) + "万");
                tv_margins_num.setText(AddCommaToMoney.AddCommaToMoney(String.format("%.2f", data.getGross_profit())) + "万");

                SetTextColorBySymbolUtils.setColors(BossDetailsViewActivity.this, data.getSale_amount_ppi(), tv_sale_trend);
                SetTextColorBySymbolUtils.setColors(BossDetailsViewActivity.this, data.getGross_profit_ppi(), tv_margins_trend);

            }
        };
        listView.setAdapter(mAdapter);
//		getClassInfo(month.replace("月", ""));
        mDataContentProvider.getContentListData(sumTime, userId);
    }


    public int getListMinHeight() {
        View view = getLayoutInflater().inflate(R.layout.activity_item_data, null);
        ll_item_title = (LinearLayout) view.findViewById(R.id.ll_item_title);
        //调用测量方法, 调用了该方法之后才能通过getMeasuredHeight()等方法获取宽高
        ll_item_title.measure(0, 0);
        int ll_height = ll_item_title.getMeasuredHeight();
        int listMinHeight = (int) (mHeight - ll_height - getMarginHeight());
        return listMinHeight;

    }

    public float getMarginHeight() {
        return (42 + 5 + 22) * mDensity + 0.5f;//标题栏+scrollview距离上边的5dp+差距22dp
    }

    @Subscriber(tag = CommonEvent.DATA_CONTENT_INFO)
    public void getInfo(HttpMethod method) {
        mAdapter.clear();
        String[] strArr = method.getUrl().split("/");
        String errno = method.data().getString("errno");
        String message = method.data().getString("msg");
        String flag = method.data().getString("flag");
        try {
            if (null != errno && "PT_ERROR_SUCCESS".equals(errno)) {
                JSONObject datas = method.data().getJSONObject("data");

                JSONArray echarts = datas.getJSONArray("echarts");


                int i = 0;
                for (Object obj : echarts) {

                    JSONObject object = new JSONObject();

                    object.put("id", userId + strArr[(strArr.length - 1)] + i);
                    i++;
                    // object.put("height","48%");
                    object.put("data", obj);
                    String jsonString = JSON.toJSONString(object);
                    chartView.callHandler("functionInJs", jsonString,
                            new CallBackFunction() {

                                @Override
                                public void onCallBack(String data) {

                                }

                            });
                }


                JSONArray dataList = datas.getJSONArray("dataList");

                for (Object object : dataList) {
                    JSONObject js = (JSONObject) object;
                    UserSaleItem data = JSON.toJavaObject(js,
                            UserSaleItem.class);
                    data.setDept_name(js.getString("dept_name"));
                    data.setSale_amount(js.getDouble("sale_amount"));
                    data.setSale_amount_ppi(js.getDouble("sale_amount_ppi"));
                    data.setGross_profit(js.getDouble("gross_profit"));
                    data.setGross_profit_ppi(js.getDouble("gross_profit_ppi"));
//				data.setSale_date(js.getString("sale_date"));
                    mAdapter.add(data);
                }
                setListViewHeightBasedOnChildren(listView);
                mAdapter.notifyDataSetChanged();

                String totalMoneyRate = datas.getString("totalMoneyRate");//总毛利同比
                String totalSalesRate = datas.getString("totalSalesRate");//总销售同比
                Double totalSales = datas.getDouble("totalSales");//总销售额
                Double totalMoney = datas.getDouble("totalMoney");//总毛利

                tv_sale.setText(AddCommaToMoney.AddCommaToMoney(String.format("%.2f", totalSales)));
                tv_gross_margin.setText(AddCommaToMoney.AddCommaToMoney(String.format("%.2f", totalMoney)));


                if (totalMoneyRate.equals("∞") && totalSalesRate.equals("∞")) {
                    tv_gross_increase.setText("∞");
                    tv_sale_increase.setText("∞");
                } else if (totalSalesRate.equals("∞") && !totalMoneyRate.equals("∞")) {
                    tv_sale_increase.setText("∞");
                    SetTextColorBySymbolUtils.setImage(Double.parseDouble(totalMoneyRate), tv_gross_increase, image_margin, "up_gray", "down_gray");
                } else if (totalMoneyRate.equals("∞") && !totalSalesRate.equals("∞")) {
                    tv_gross_increase.setText("∞");
                    SetTextColorBySymbolUtils.setImage(Double.parseDouble(totalSalesRate), tv_sale_increase, image_sale, "up_gray", "down_gray");
                } else if (!totalMoneyRate.equals("∞") && !totalSalesRate.equals("∞")) {
                    SetTextColorBySymbolUtils.setImage(Double.parseDouble(totalSalesRate), tv_sale_increase, image_sale, "up_gray", "down_gray");
                    SetTextColorBySymbolUtils.setImage(Double.parseDouble(totalMoneyRate), tv_gross_increase, image_margin, "up_gray", "down_gray");
                }

//			SetTextColorBySymbolUtils.setImage(Double.parseDouble(totalSalesRate), tv_sale_increase, image_sale,"up_gray","down_gray");
//			SetTextColorBySymbolUtils.setImage(Double.parseDouble(totalMoneyRate), tv_gross_increase, image_margin,"up_gray","down_gray");


            } else {
                toastMsg("请求数据失败!");
//				toastMsg(message);
            }


        } catch (Exception e) {

        }


    }

    public int getYear(Date date) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }


    /**
     * @param listView 要设置高度的ListView
     * @return void 返回类型
     * @throws
     * @Title : setListViewHeightBasedOnChildren
     * @Description : TODO(重新设置ListView的高度以解决ScrollView中的ListView显示不正常的问题)
     * @params
     */
    public void setListViewHeightBasedOnChildren(ListView listView) {
        android.widget.ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        if (params.height < getListMinHeight()) {
            params.height = getListMinHeight();
        }
        listView.setLayoutParams(params);

    }

    public int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }
}
