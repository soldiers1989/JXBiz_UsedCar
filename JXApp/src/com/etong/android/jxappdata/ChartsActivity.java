package com.etong.android.jxappdata;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.fragment.ChartFragment;
import com.etong.android.frame.utils.ActivitySkipUtil;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.util.SerializableObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ChartsActivity extends SubscriberActivity {
	private TitleBar mTitleBar;
	// TODO 修改为前acitivty跳转前传入
	// private RadioButton mRadioButtons[] = new RadioButton[3];
	// private String[] titleArray = { "月", "年", "全部" };
	// private boolean[] flagArray={true,false,false};
	// private boolean[] buttonVisibyArray={true,true,false};
	// private SubscriberFragment mFragments[] = new SubscriberFragment[3];
	// private String bottonTitleArray[][] = {
	// { "1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月",
	// "11月", "12月" },
	// { "2012年", "2013年", "2014年", "2015年", "2016年" },
	// { "2012年", "2013年", "2014年", "2015年", "2016年" } };
	// // private String[] urlArry=new
	// String[]{HttpUri.FINANCED_AMOUNT,HttpUri.FINANCED_NUMBER};
	private String[] titleArray;
	private boolean[] flagArray;
	private boolean[] buttonVisibyArray;
	private String bottonTitleArray[][];
	private String[] url;
	private RadioButton mRadioButtons[];
	private SubscriberFragment mFragments[];
	// fragment载入和切换manager
	private FragmentManager fragment_manager;
	private FragmentTransaction transaction;
	private int backgroud[][]={{R.drawable.radiobutton_backgroud_selector},{R.drawable.data_item_title_left_selector,R.drawable.data_item_title_right_selector},{R.drawable.data_item_title_left_selector,R.drawable.data_item_title_middle_selector,R.drawable.data_item_title_right_selector}};
	private RadioButton radiobutton;
	private RadioGroup group;
	private String titleName;
	private String[][] titleArrayList = { { "月", "年", "全部" }, { "年", "全部" }, { "全部" } };
	private boolean[][] flagArrayList = { { true, false, false },{ false, false }, { false } };
	private boolean[][] buttonVisibyArrayList = { { true, true, false }, { true, false }, { false } };
	
	JSONObject jsonObject;
	JSONArray children;
	String[] dataurl;
	int datatype;
	String[] year;
	public int selectyear = getYear(new Date());
	@Override
	protected void onInit(@Nullable Bundle savedInstanceState) {
		setContentView(R.layout.activity_data_chart);
		mTitleBar = new TitleBar(this);
		mTitleBar = new TitleBar(this);
		mTitleBar.showBackButton(true);

		mTitleBar.showNextButton(false);
		mTitleBar.showBottomLin(false);
		
		
		//得到传过来的配置
		Bundle bundle = getIntent().getExtras();
		SerializableObject serializableMap = (SerializableObject) bundle
				.get("dataMap");
		Map map = (Map) serializableMap.getObject();
	
		titleName = (String) map.get("itemName");
		try {
			
			jsonObject=(JSONObject)map.get("data");
			String[][] tempBottonTitleArray = (String[][]) map
					.get("bottonTitleArray");
			String dd =(String)jsonObject.get("dataURL");
			datatype =(int) jsonObject.get("datatype");
			if(datatype!=2){
				int starttime=jsonObject.getInteger("startTime");
				int years = selectyear-starttime+1;
				year = new String[years];
				for(int i=0;i<years;i++){
					year[i]=starttime+"年";
					starttime++;
					tempBottonTitleArray[1] = year;
				}
			}
			
			JSONArray array=JSONObject.parseArray(dd);
			
			dataurl=new String[array.size()];   
		      for(int i=0;i<array.size();i++){   
		    	  dataurl[i]=array.getString(i);   
		           
		      }   
//			dataurl= 
//			 dataurl=array.toArray();
			
			
			titleArray = titleArrayList[datatype];
//			children=jsonObject.getJSONArray("children");
			flagArray =flagArrayList[datatype];
			buttonVisibyArray =buttonVisibyArrayList[datatype];
			
			
//			titleArray = (String[]) map.get("titleArray");
//			flagArray = (boolean[]) map.get("flagArray");
//			buttonVisibyArray = (boolean[]) map.get("buttonVisibyArray");
//			url = (String[]) map.get("url");
			if (titleArray.length != tempBottonTitleArray.length) {
				bottonTitleArray = new String[titleArray.length][];
				for (int i = 0; i < titleArray.length; i++) {
					bottonTitleArray[i] = tempBottonTitleArray[i + 1];
				}
			} else {
				bottonTitleArray = tempBottonTitleArray;
			}

		
		} catch (Exception e) {
			ActivitySkipUtil.skipActivity(this, StartActivity.class);
			this.finish();
		}
		
		mTitleBar.setTitle(titleName);
//		this.myTitle=titleName;
		initView();
	}

	public int getYear(Date date) {
		Calendar cal = Calendar.getInstance();

		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}
	
	
	
	public void initView() {
		// 1.载入topbutton
		initTopButton();
		// 2.载入fragments
		initFragments();
	}

	// 1.载入topbutton
	public void initTopButton() {
		RadioGroup group = findViewById(R.id.group, RadioGroup.class);

		//初始化radiobutton和fragment
		mRadioButtons= new RadioButton[titleArray.length];
		mFragments = new SubscriberFragment[titleArray.length];
		
		for(int i=0;i<titleArray.length;i++){
//			radiobutton=new RadioButton(this);
			radiobutton =(RadioButton)LayoutInflater.from(this).inflate(R.layout.activity_radiobutton, null);
//			radioButton(radiobutton);
			mRadioButtons[i] =radiobutton;
			mRadioButtons[i].setId(i);
			mRadioButtons[i].setText(titleArray[i]);
			//设置默认选中第一个
			if(i==0){
				mRadioButtons[i].setChecked(true);
			}
			//把radiobutton添加到radiogroup里
			group.addView(radiobutton,(mWidth - getMarginWidth())/titleArray.length,getSmallHeight());

			mRadioButtons[i].setBackgroundResource(backgroud[titleArray.length-1][i]);
			
		}

		//切换监听
		group.setOnCheckedChangeListener(listener);
		

	}

	// radiobutton 切换
	RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {

			for (int i = 0; i < mRadioButtons.length; i++) {
				if (mRadioButtons[i].getId() == checkedId) {
					transaction = fragment_manager.beginTransaction();
					switchFragments(transaction, i);
					transaction.commitAllowingStateLoss();
					return;
				}
			}

		}
	};

	// 2.载入fragments
	public void initFragments() {
		fragment_manager = this.getSupportFragmentManager();
		transaction = fragment_manager.beginTransaction();
		// for(int i=0;i<titleArray.length;i++){
		// ChartFragment cft =new ChartFragment();
		// cft.urlArray=new
		// String[]{HttpUri.FINANCED_AMOUNT,HttpUri.FINANCED_NUMBER};
		// cft.months=bottonTitleArray[i];
		// cft.isNeedUpdateScrool=flagArray[i];
		// cft.fragmentIndex=i;
		// mFragments[i] =cft;
		// transaction.add(R.id.order_fragment_container,
		// mFragments[i]);
		//
		// }
		
		// 默认选中第一个fragment和第一个button
		switchFragments(transaction, 0);

		transaction.commitAllowingStateLoss();
		// transaction.show(mFragments[0]);

	}

	// 切换fragment
	private void switchFragments(FragmentTransaction transaction, int index) {
		for (int i = 0; i < titleArray.length; i++) {
			
			if (index == i) {
				if (mFragments[i] == null) {
					ChartFragment cft = new ChartFragment();
					cft.urlArray = dataurl;
					cft.months=bottonTitleArray[i];
					cft.isNeedUpdateScrool = flagArray[i];
					cft.fragmentIndex = i;
					cft.mDensity = mDensity;
					cft.mWidth = mWidth;
					cft.mHeight = mHeight;
					cft.mTitlebarName=titleName;
					cft.titleArray=titleArray[i];
					cft.isButtonVisibity = buttonVisibyArray[i];
					cft.ca=ChartsActivity.this;
					mFragments[i] = cft;
					transaction.add(R.id.order_fragment_container,
							mFragments[i]);
				}
				transaction.show(mFragments[i]);
				//
			} else {
				if (mFragments[i] != null) {
					transaction.hide(mFragments[i]);
				}
			}
		}
	}
	
	public int getSmallHeight() {
		return (int) (30 * mDensity + 0.5f);
	}
	
	@SuppressLint("ResourceAsColor")
	public void radioButton(RadioButton button){
		//button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, getSmallHeight(), 1)); 
		button.setGravity(Gravity.CENTER);
		button.setTextColor(R.color.checkbox_text_color);
		button.setButtonDrawable(getResources().getDrawable(android.R.color.transparent));//   android:button="@null"  
	
	}
	
	
	public int getMarginWidth() {
		return (int) (60 * mDensity + 0.5f);
	}
	
}
