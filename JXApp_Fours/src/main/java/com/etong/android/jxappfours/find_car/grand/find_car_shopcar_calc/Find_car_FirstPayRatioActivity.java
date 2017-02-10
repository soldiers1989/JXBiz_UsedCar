package com.etong.android.jxappfours.find_car.grand.find_car_shopcar_calc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ListView;
import android.widget.TextView;

import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.utils.ListAdapter;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappfours.R;

import java.util.Arrays;

/** 
* @ClassName    : FirstPayRatioActivity 
* @Description  : TODO(车贷计算器的首付比例列表界面) 
*  
*/ 
public class Find_car_FirstPayRatioActivity extends BaseSubscriberActivity {

	private TitleBar mTitleBar;
	private ListView mListView;
	private TextView tvFirstPayRatio;
	private String[] firstPayRatio = { "30%", "40%", "50%", "60%", "70%",
			"80%", "90%" };
	private String 	ratio = null;
	private int mPosition;
	
	@Override
	protected void onInit(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.find_car_calcu_carloan_list);

		mTitleBar = new TitleBar(this);
		mTitleBar.showBackButton(true);
		mTitleBar.showNextButton(false);
		mTitleBar.setTitle("首付比例");
		mTitleBar.setTitleTextColor("#000000");
		mTitleBar.setmTitleBarBackground("#FFFFFF");

		Intent comeActivity = getIntent();
		ratio = comeActivity.getStringExtra("ratio");
		
		initView();

	}

	@SuppressLint("ResourceAsColor")
	private void initView() {
		// TODO Auto-generated method stub
		mListView = findViewById(R.id.find_car_calcu_lv_car_loan, ListView.class);
		final ListAdapter<String> adapter = new ListAdapter<String>(this,
				R.layout.find_car_list_item_carloan) {

			@Override
			protected void onPaint(View view, final String data, final int position) {
				// TODO Auto-generated method stub

				tvFirstPayRatio = (TextView) view.findViewById(R.id.tv_content);

				tvFirstPayRatio.setText(data);
				
				if(data.equals(ratio)){
					tvFirstPayRatio.setPressed(true);
					mPosition = position;
				}
				view.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent goActivity = new Intent();
						goActivity.putExtra("firstPayRatio", data);
						setResult(Find_car_CalcuResultActivity.downpayment, goActivity);
						finish();
					}
				});
				
				view.setOnTouchListener(new OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub
						 int nPosition = position;
							if(mPosition != nPosition){
								mListView.getChildAt(mPosition).findViewById(R.id.tv_content).setPressed(false);
							}
						return false;
					}
				});
			}
			
		};
		adapter.addAll(Arrays.asList(firstPayRatio));
		mListView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}
}
