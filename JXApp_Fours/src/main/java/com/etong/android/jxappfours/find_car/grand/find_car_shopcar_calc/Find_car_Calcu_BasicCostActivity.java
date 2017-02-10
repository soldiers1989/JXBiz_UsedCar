package com.etong.android.jxappfours.find_car.grand.find_car_shopcar_calc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.utils.ListAdapter;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappfours.R;

/** 
* @ClassName    : BasicCostActivity 
* @Description  : TODO(车贷计算器的基本费用列表界面) 
*  
*/ 
public class Find_car_Calcu_BasicCostActivity extends BaseSubscriberActivity {

	private TitleBar mTitleBar;
	private ListView mListView;
	private ListAdapter<Find_car_Calcu_BasicCost> adapter;
	private String jsonStr = "[{\"cost\":[{\"costName\":\"GPS费用\","
			+ "\"costMoney\":\"3,980.00\","
			+ "},"
			+ "{\"costName\":\"移动资费\","
			+ "\"costMoney\":\"360.00\","
			+ "},"
			+ "{\"costName\":\"上牌代办费\","
			+ "\"costMoney\":\"100.00\","
			+ "},"
			+ "{\"costName\":\"上门调查费\","
			+ "\"costMoney\":\"400.00\","
			+ "},"
			+ "{\"costName\":\"抵押费\","
			+  "\"costMoney\":\"400.00\"," + "}]}]";

	@Override
	protected void onInit(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.find_car_calcu_carloan_list);

		mTitleBar = new TitleBar(this);
		mTitleBar.showBackButton(true);
		mTitleBar.showNextButton(false);
		mTitleBar.setTitle("基本费用");
		mTitleBar.setTitleTextColor("#000000");
		mTitleBar.setmTitleBarBackground("#FFFFFF");

		initView();

	}

	private void initView() {
		// TODO Auto-generated method stub
		mListView = findViewById(R.id.find_car_calcu_lv_car_loan, ListView.class);
		adapter = new ListAdapter<Find_car_Calcu_BasicCost>(this,
				R.layout.find_car_calcu_list_item_carloan_cost) {

			@Override
			protected void onPaint(View view, Find_car_Calcu_BasicCost data, int position) {
				// TODO Auto-generated method stub

				TextView mCostName = (TextView) view.findViewById(R.id.find_car_calcu_tv_cost_name);
				TextView mCostMoney = (TextView) view.findViewById(R.id.find_car_calcu_tv_cost_money);
				
				mCostName.setText(data.getCostName());
				mCostMoney.setText(data.getCostMoney());

			
				view.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {

					}
				});
			}

		};
		mListView.setAdapter(adapter);
		getCostInfo();
	}

	public void getCostInfo() {
		JSONArray array = JSONArray.parseArray(jsonStr);
		for (Object object : array) {
			JSONObject js = (JSONObject) object;
			JSONArray costList = js.getJSONArray("cost");
			for (int i = 0; i < costList.size(); i++) {
				Find_car_Calcu_BasicCost costData = JSON.toJavaObject(
						costList.getJSONObject(i), Find_car_Calcu_BasicCost.class);
				adapter.add(costData);
			}
		}
		adapter.notifyDataSetChanged();
	}
	
}
