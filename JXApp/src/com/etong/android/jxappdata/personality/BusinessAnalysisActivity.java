package com.etong.android.jxappdata.personality;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.data.DataContentProvider;
import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.utils.ListAdapter;
import com.etong.android.frame.widget.CircleImageView;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappdata.R;
import com.etong.android.jxappdata.SubscriberActivity;
import com.etong.android.jxappdata.common.CommonEvent;
import com.etong.android.util.AddCommaToMoney;
import com.etong.android.util.ImageUtils;
import com.etong.android.util.PopupWindowUtil;
import com.etong.android.util.SetTextColorBySymbolUtils;

import org.simple.eventbus.Subscriber;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/***
 * 4s店集团经营分析页面
 *
 * @author Administrator
 */
public class BusinessAnalysisActivity extends SubscriberActivity {
    private TitleBar mTitleBar;
    private TextView tv_sale;
    private TextView tv_sale_increase;
    private TextView tv_gross_margin;
    private TextView tv_gross_increase;
    private ListView listView;
    private PopupWindow mPopWindow;
    private int mPosition = 0;
    public int year = getYear(new Date());
    public int selectmonth = getMonth(new Date());//系统月
    String selectmonths[];
    ListAdapter<UserSale> mAdapter;
    ImageProvider mImageProvider;
    ImageView image_sale;
    ImageView image_margin;
    NumberFormat mNumberFormat = NumberFormat.getPercentInstance();
    String nSelectMonth;
    private DataContentProvider mDataContentProvider;
    private HttpPublisher mHttpPublisher;


    int sale_amount;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_business_analysis);
//		setContentView(R.layout.activity_list_item_adapter_group_run);
        mTitleBar = new TitleBar(this);
        mTitleBar = new TitleBar(this);
        mTitleBar.showBackButton(true);
        mTitleBar.showNextButton(true);
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(this);
        mDataContentProvider = DataContentProvider.getInstance();
        mDataContentProvider.initialize(mHttpPublisher);
        mTitleBar.setNextButton(selectmonth + "月");

        Intent intent = getIntent();
        mTitleBar.setTitle(intent.getStringExtra("titleName"));


        nSelectMonth = selectmonth + "月";
        mImageProvider.getInstance().initialize(this);
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


                final PopupWindowUtil popupWindow = new PopupWindowUtil(BusinessAnalysisActivity.this, items);
                popupWindow.setItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mDataContentProvider.getListData(year + "-" + String.format("%02d", Integer.valueOf((items.get(position).replace("月", "")))));

//			            		getClassInfo(items.get(position).replace("月", ""));
//			            	mDataContentProvider.getListData();

                        mTitleBar.setNextButton(items.get(position));
                        nSelectMonth = items.get(position);
                        popupWindow.dismiss();

                    }
                });
                //根据后面的数据调节窗口的宽度
                popupWindow.show(v, 2);
            }
        });
//		mTitleBar.setTitle();
        initView();

    }

    public int getYear(Date date) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    public void initView() {
        tv_sale = findViewById(R.id.tv_sale, TextView.class);
        tv_sale_increase = findViewById(R.id.tv_sale_increase, TextView.class);
        tv_gross_margin = findViewById(R.id.tv_gross_margin, TextView.class);
        tv_gross_increase = findViewById(R.id.tv_gross_increase, TextView.class);
        image_sale = findViewById(R.id.image_sale, ImageView.class);
        image_margin = findViewById(R.id.image_margin, ImageView.class);
        listView = findViewById(R.id.listView, ListView.class);

        mAdapter = new ListAdapter<UserSale>(this, R.layout.activity_list_adapter_group_run) {

            @SuppressLint("NewApi")
            @SuppressWarnings("deprecation")
            @Override
            protected void onPaint(View view, final UserSale data, int position) {
                CircleImageView head_image = (CircleImageView) view.findViewById(R.id.person_my_head);
                ImageView person_my_headimage = (ImageView) view.findViewById(R.id.person_my_headimage);


                TextView name = (TextView) view.findViewById(R.id.tv_name);
                TextView tv_salenum = (TextView) view.findViewById(R.id.tv_sale_num);//销售额
                TextView tv_margins_num = (TextView) view.findViewById(R.id.tv_margins_num);//毛利
                TextView tv_sale_trend = (TextView) view.findViewById(R.id.tv_sale_trend);//销售额增长率
                TextView tv_margins_trend = (TextView) view.findViewById(R.id.tv_margins_trend);//毛利增长率


                if (null == data.getImageIcon()) {

                    person_my_headimage.setVisibility(View.VISIBLE);
                    head_image.setVisibility(View.GONE);
                    if (null != data.getAppuser_name() && !data.getAppuser_name().equals("")) {
                        person_my_headimage.setImageBitmap(ImageUtils.nameToImage(data.getAppuser_name()));
                    } else {
                        person_my_headimage.setImageBitmap(ImageUtils.nameToImage("无"));
                    }

                } else {
                    person_my_headimage.setVisibility(View.GONE);
                    head_image.setVisibility(View.VISIBLE);
                    ImageProvider.getInstance().loadImage(head_image, data.getImageIcon());
                    head_image.setBorderColor(BusinessAnalysisActivity.this.getResources().getColor(R.color.image_border_color));
                    head_image.setBorderWidth(2);
                }
                name.setText(data.getAppuser_name());
                tv_salenum.setText(AddCommaToMoney.AddCommaToMoney(String.format("%.2f", data.getSale_amount())) + "万");
                tv_margins_num.setText(AddCommaToMoney.AddCommaToMoney(String.format("%.2f", data.getGross_profit())) + "万");


                SetTextColorBySymbolUtils.setColors(BusinessAnalysisActivity.this, data.getSale_amount_ppi(), tv_sale_trend);

                SetTextColorBySymbolUtils.setColors(BusinessAnalysisActivity.this, data.getGross_profit_ppi(), tv_margins_trend);


                view.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        try {
                            Class activity = Class.forName("com.etong.android.jxappdata.personality.BossDetailsViewActivity");
                            Intent intent = new Intent(BusinessAnalysisActivity.this, activity);
                            intent.putExtra("name", data.getAppuser_name());
                            intent.putExtra("id", data.getAppuser_id());
                            intent.putExtra("month", nSelectMonth);
//							intent.putExtra("time",data.getSale_date());
                            intent.putExtra("time", year + "-" + String.format("%02d", Integer.valueOf((nSelectMonth.replace("月", "")))));

                            startActivity(intent);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                    }
                });


            }
        };
        listView.setAdapter(mAdapter);
        mDataContentProvider.getListData(year + "-" + String.format("%02d", Integer.valueOf((nSelectMonth.replace("月", "")))));
//		getClassInfo(selectmonths[0].replace("月", ""));
    }

    @Subscriber(tag = CommonEvent.DATA_INFO)
    protected void getInfo(HttpMethod method) {
        mAdapter.clear();
        @SuppressWarnings("unused")
        String errno = method.data().getString("errno");
        String message = method.data().getString("msg");
        String flag = method.data().getString("flag");
        try {
//			JSONArray array = JSONArray.parseArray(JsonData.getJsonData());
            if (null != errno && "PT_ERROR_SUCCESS".equals(errno)) {

                JSONArray dataList = method.data().getJSONArray("data");
                for (Object object : dataList) {
                    JSONObject js = (JSONObject) object;


                    UserSale data = JSON.toJavaObject(js,
                            UserSale.class);
                    data.setAppuser_name(js.getString("appuser_name"));
                    data.setAppuser_id(js.getString("appuser_id"));
                    data.setSale_amount(js.getDouble("sale_amount"));
                    data.setSale_amount_ppi(js.getDouble("sale_amount_ppi"));
                    data.setGross_profit(js.getDouble("gross_profit"));
                    data.setGross_profit_ppi(js.getDouble("gross_profit_ppi"));
                    data.setSale_date(js.getString("sale_date"));
                    mAdapter.add(data);
                }

                JSONObject data = method.data().getJSONObject("totalData");
                String totalMoneyRate = data.getString("totalMoneyRate");//总毛利同比
                String totalSalesRate = data.getString("totalSalesRate");//总销售同比
                Double totalSales = data.getDouble("totalSales");//总销售额
                Double totalMoney = data.getDouble("totalMoney");//总毛利

                tv_sale.setText(AddCommaToMoney.AddCommaToMoney(String.format("%.2f", totalSales)));
                tv_gross_margin.setText(AddCommaToMoney.AddCommaToMoney(String.format("%.2f", totalMoney) + ""));

                if (totalMoneyRate.equals("∞") && totalSalesRate.equals("∞")) {
                    tv_gross_increase.setText("∞");
                    tv_sale_increase.setText("∞");
                    image_margin.setBackgroundColor(this.getResources().getColor(R.color.transparent));
                    image_sale.setBackgroundColor(this.getResources().getColor(R.color.transparent));
                } else if (totalSalesRate.equals("∞") && !totalMoneyRate.equals("∞")) {
                    tv_sale_increase.setText("∞");
                    image_sale.setBackgroundColor(this.getResources().getColor(R.color.transparent));
                    SetTextColorBySymbolUtils.setImage(Double.parseDouble(totalMoneyRate), tv_gross_increase, image_margin, "up", "down");
                } else if (totalMoneyRate.equals("∞") && !totalSalesRate.equals("∞")) {
                    tv_gross_increase.setText("∞");
                    image_margin.setBackgroundColor(this.getResources().getColor(R.color.transparent));
                    SetTextColorBySymbolUtils.setImage(Double.parseDouble(totalSalesRate), tv_sale_increase, image_sale, "up", "down");
                } else if (!totalMoneyRate.equals("∞") && !totalSalesRate.equals("∞")) {
                    SetTextColorBySymbolUtils.setImage(Double.parseDouble(totalSalesRate), tv_sale_increase, image_sale, "up", "down");
                    SetTextColorBySymbolUtils.setImage(Double.parseDouble(totalMoneyRate), tv_gross_increase, image_margin, "up", "down");
                }

                mAdapter.notifyDataSetChanged();
            } else {
                toastMsg("请求数据失败!");
//				toastMsg(message);
            }


        } catch (Exception e) {

        }

    }


    public int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }


}
