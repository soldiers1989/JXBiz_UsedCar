package com.etong.android.jxappdata;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.etong.android.frame.utils.L;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappdata.common.BaseActivity;
/**
 * 即将推出页面
 */
public class ComeSoonActivity extends BaseActivity{
private TitleBar mTitleBar;
String data;
	@Override
	protected void onInit(@Nullable Bundle savedInstanceState) {
		setContentView(R.layout.activity_come_soon);
		L.d("time end", System.currentTimeMillis()+"");
		Intent intent =getIntent();
		String title_name=intent.getStringExtra("name");
		data=intent.getStringExtra("data");
		int position=intent.getIntExtra("position",-1);
		mTitleBar =new TitleBar(this);
		mTitleBar = new TitleBar(this);
		mTitleBar.showBackButton(true);
		mTitleBar.showNextButton(false);
		mTitleBar.setTitle(title_name);
		this.myTitle=title_name;
		initView();
		
	}

	public void initView(){
		TextView tv_state_name=findViewById(R.id.tv_state_name, TextView.class);
		tv_state_name.setText(data);
	}
}
