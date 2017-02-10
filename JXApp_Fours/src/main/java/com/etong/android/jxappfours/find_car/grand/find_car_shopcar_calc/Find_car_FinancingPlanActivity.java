package com.etong.android.jxappfours.find_car.grand.find_car_shopcar_calc;

import android.content.Intent;
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
* @ClassName    : FinancingPlanActivity 
* @Description  : TODO(车贷计算器的融资方案列表界面) 
*  
*/ 
public class Find_car_FinancingPlanActivity extends BaseSubscriberActivity {

	private TitleBar mTitleBar;
	private ListView mListView;
	private ListAdapter<Find_car_FinancingPlan> adapter;
	private String jsonStr = "[{\"plan\":[{\"planName\":\"等额本金\","
			+ "\"planContent\":\"将手续费一次性支付，银行的手续费分摊至每期还款金额中，这种模式为等额本金模式\","
			+ "},"
			+ "{\"planName\":\"等额本息\","
			+  "\"planContent\":\"将购车手续费以及银行的手续费同时分期分摊至每期还款金额中，这种模式为等额本息模式\"," + "}]}]";
	@Override
	protected void onInit(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		setContentView(R.layout.find_car_calcu_carloan_list);

		mTitleBar = new TitleBar(this);
		mTitleBar.showBackButton(true);
		mTitleBar.showNextButton(false);
		mTitleBar.setTitle("融资方案");
		mTitleBar.setTitleTextColor("#000000");
		mTitleBar.setmTitleBarBackground("#FFFFFF");
		
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		mListView = findViewById(R.id.find_car_calcu_lv_car_loan, ListView.class);
		adapter = new ListAdapter<Find_car_FinancingPlan>(this,
				R.layout.find_car_list_item_carloan_financingplan) {

			@Override
			protected void onPaint(View view, final Find_car_FinancingPlan data, int position) {
				// TODO Auto-generated method stub

				TextView mPlanName = (TextView) view.findViewById(R.id.tv_plan_name);
				TextView mPlanContent = (TextView) view.findViewById(R.id.tv_plan_content);
				
				mPlanName.setText(data.getPlanName());
				mPlanContent.setText(data.getPlanContent());

			
				view.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						int modle = 0;
						if (data.getPlanName().equals("等额本金")) {
							modle = 1;
						} else if (data.getPlanName().equals("等额本息")) {
							modle = 2;
						}
						Intent goActivity = new Intent();
						goActivity.putExtra("modle", modle);
						setResult(Find_car_CalcuResultActivity.modle, goActivity);
						finish();
					}
				});
			}

		};
		mListView.setAdapter(adapter);
		getPlanInfo();
	}
	public void getPlanInfo() {
		JSONArray array = JSONArray.parseArray(jsonStr);
		for (Object object : array) {
			JSONObject js = (JSONObject) object;
			JSONArray planList = js.getJSONArray("plan");
			for (int i = 0; i < planList.size(); i++) {
				Find_car_FinancingPlan planData = JSON.toJavaObject(
						planList.getJSONObject(i), Find_car_FinancingPlan.class);
				adapter.add(planData);
			}
		}
		adapter.notifyDataSetChanged();
	}
}
