package com.etong.android.jxappfours.find_car.grand.find_car_shopcar_calc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappfours.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** 
* @ClassName    : CommercialInsuranceActivity 
* @Description  : TODO(车贷计算器的商业保险列表界面) 
*  
*/ 
public class Find_car_CommercialInsuranceActivity extends BaseSubscriberActivity {

	
	private TitleBar mTitleBar;
	private ListView mListView;
	private Find_car_CommercialInsuranceAdapter adapter;
	private List<Find_car_CommercialInsurance> itemList;
	private String guidePrice = null;
	private String totalMoney = null;
	private String checkedPositon="";
	private String mCheckedPositon="";

	@Override
	protected void onInit(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.find_car_calcu_carloan_list);

		mTitleBar = new TitleBar(this);
		mTitleBar.showBackButton(true);
		mTitleBar.setNextButton("确定");
		mTitleBar.setTitle("商业保险");
		mTitleBar.setTitleTextColor("#000000");
		mTitleBar.setmTitleBarBackground("#FFFFFF");

		mTitleBar.setNextOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//避免用户再次点进该页面不做任何操作点击确认回到上一界面导致totalMoney没有记录之前选中项的总金额和位置的情况
				if(null ==totalMoney || TextUtils.isEmpty(mCheckedPositon)){
					mCheckedPositon = "";
					Double dTotalMoney = 0.0;
					for (int i=0; i<itemList.size(); i++) {
						if (itemList.get(i).isChecked()) {
							mCheckedPositon=i+mCheckedPositon;
							dTotalMoney += Double.valueOf(itemList.get(i).getInsuranceMoney()) ;
						}
					}
					BigDecimal bd_dTotalMoney = new BigDecimal(dTotalMoney);
					totalMoney = String.valueOf(bd_dTotalMoney.setScale(0, BigDecimal.ROUND_HALF_UP).intValue());
				}
				Intent goActivity = new Intent();
				if (!TextUtils.isEmpty(totalMoney)) {
					goActivity.putExtra("commercialInsurance", totalMoney);//选中项的总金额
					goActivity.putExtra("checkedPosition", mCheckedPositon);//选中项的位置
				}
				setResult(Find_car_CalcuResultActivity.business, goActivity);
				finish();
			}
		});
		
		Intent i = getIntent();
		String nakedcarMoney = i.getStringExtra("nakedcarMoney");
		guidePrice = nakedcarMoney = nakedcarMoney.replace(",", "");	// 裸车价
		if (TextUtils.isEmpty(guidePrice)) {
			guidePrice = "0";
		}
		String checkedPositonTemp = i.getStringExtra("insuranceCheckedPositon"); //商业保险选中的位置
		if (!TextUtils.isEmpty(checkedPositonTemp)) {
			checkedPositon = checkedPositonTemp;
		}
		initView();
		
	}

	
	private void initView() {
		// TODO Auto-generated method stub
		
		mListView = findViewById(R.id.find_car_calcu_lv_car_loan, ListView.class);
		itemList = new ArrayList<Find_car_CommercialInsurance>();
			setInfo(); 
		
		adapter = new Find_car_CommercialInsuranceAdapter(this, itemList);
		mListView.setAdapter(adapter);
		
		
		adapter.setCallback(new Find_car_CommercialInsuranceAdapter.AdapterCallback() {
			@Override
			public void callBack(List<Find_car_CommercialInsurance> itemCallList) {
				final int itemSize = itemCallList.size();
				mCheckedPositon = "";
				Double dTotalMoney = 0.0;
				for (int i=0; i<itemSize; i++) {
					if (itemList.get(i).isChecked()) {
						dTotalMoney += Double.valueOf(itemList.get(i).getInsuranceMoney()) ;	
						mCheckedPositon=i+mCheckedPositon;
					}
				}
				BigDecimal bd_dTotalMoney = new BigDecimal(dTotalMoney);
				totalMoney = String.valueOf(bd_dTotalMoney.setScale(0, BigDecimal.ROUND_HALF_UP).intValue());
			}
		});
	}

	

	private void setInfo() {
		
		Find_car_CommercialInsurance cInsurance1 = new Find_car_CommercialInsurance();
		cInsurance1.setInsuranceName("交通事故责任强制保险");
		cInsurance1.setInsuranceMoney("950");
		if(checkedPositon.contains("0")){
			cInsurance1.setChecked(true);
		}
		itemList.add(cInsurance1);
		Find_car_CommercialInsurance cInsurance2 = new Find_car_CommercialInsurance();
		cInsurance2.setInsuranceName("商业第三者责任险");
		cInsurance2.setInsuranceMoney("1252");
		if(checkedPositon.contains("1")){
			cInsurance2.setChecked(true);
		}
		itemList.add(cInsurance2);
		Find_car_CommercialInsurance cInsurance3 = new Find_car_CommercialInsurance();
		cInsurance3.setInsuranceName("车辆损失险");
		Double lostInsurance = Double.valueOf(guidePrice)*0.01088+459.0;
		 BigDecimal bd_lostInsurance = new BigDecimal(lostInsurance);
		cInsurance3.setInsuranceMoney(bd_lostInsurance.setScale(0, BigDecimal.ROUND_HALF_UP).intValue()+"");
		if(checkedPositon.contains("2")){
			cInsurance3.setChecked(true);
		}
		itemList.add(cInsurance3);
		Find_car_CommercialInsurance cInsurance4 = new Find_car_CommercialInsurance();
		cInsurance4.setInsuranceName("不计免赔特约险");
		Double specialInsurance = ((lostInsurance+1252.0)*0.2);
		 BigDecimal bd_specialInsurance = new BigDecimal(specialInsurance);
		cInsurance4.setInsuranceMoney(bd_specialInsurance.setScale(0, BigDecimal.ROUND_HALF_UP).intValue()+"");
		if(checkedPositon.contains("3")){
			cInsurance4.setChecked(true);
		}
		itemList.add(cInsurance4);
	}
}
