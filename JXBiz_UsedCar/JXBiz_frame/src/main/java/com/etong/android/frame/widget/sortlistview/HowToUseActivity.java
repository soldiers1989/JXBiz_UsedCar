package com.etong.android.frame.widget.sortlistview;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.etong.android.frame.R;
import com.etong.android.frame.widget.ClearEditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HowToUseActivity extends Activity {
	private ListView sortListView;
	private EtongSideBar sideBar;
	private TextView dialog;
	private EtongSortAdapter adapter;
	
	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	private List<EtongSortModel> SourceDateList;
	
	/*
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;
	private ClearEditText mClearEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.default_sort_listview_content);
		initViews();
	}

	private void initViews() {
		//实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();
		
		pinyinComparator = new PinyinComparator();
		
		sideBar = (EtongSideBar) findViewById(R.id.default_content_sidrbar);
		dialog = (TextView) findViewById(R.id.default_content_dialog);
		sideBar.setTextView(dialog);
		
		//设置右侧触摸监听
		sideBar.setOnTouchingLetterChangedListener(new EtongSideBar.OnTouchingLetterChangedListener() {
			
			@Override
			public void onTouchingLetterChanged(String s) {
				//该字母首次出现的位置
				int position = adapter.getPositionForSection(s.charAt(0));
				if(position != -1){
					sortListView.setSelection(position);
				}
				
			}
		});
		
		sortListView = (ListView) findViewById(R.id.default_content_lv);
		sortListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//这里要利用adapter.getItem(position)来获取当前position所对应的对象
				Toast.makeText(getApplication(), ((EtongSortModel)adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
			}
		});
		
		SourceDateList = filledData(getResources().getStringArray(R.array.default_test_data));
		

		// 根据a-z进行排序源数据
		Collections.sort(SourceDateList, pinyinComparator);
		adapter = new EtongSortAdapter(this, SourceDateList);
		sortListView.setAdapter(adapter);
		
		
		// 下面是ListView上面的输入框,用来搜索过滤的方法
//		mClearEditText =  (ClearEditText) findViewById(R.id.filter_edit);
		//根据输入框输入值的改变来过滤搜索
//		mClearEditText.addTextChangedListener(new TextWatcher() {
//
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before, int count) {
//				//当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
//				filterData(s.toString());
//			}
//
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count,
//					int after) {
//
//			}
//
//			@Override
//			public void afterTextChanged(Editable s) {
//			}
//		});
	}


	/*
	 * 为ListView填充数据
	 * @param date
	 * @return
	 */
	private List<EtongSortModel> filledData(String [] date){
		List<EtongSortModel> mSortList = new ArrayList<EtongSortModel>();
		
		for(int i=0; i<date.length; i++){
			EtongSortModel sortModel = new EtongSortModel();
			sortModel.setName(date[i]);
			//汉字转换成拼音
			String pinyin = characterParser.getSelling(date[i]);
			String sortString = pinyin.substring(0, 1).toUpperCase();
			
			// 正则表达式，判断首字母是否是英文字母
			if(sortString.matches("[A-Z]")){
				sortModel.setSortLetters(sortString.toUpperCase());
			}else{
				sortModel.setSortLetters("#");
			}
			
			mSortList.add(sortModel);
		}
		return mSortList;
		
	}
	
	/*
	 * 根据输入框中的值来过滤数据并更新ListView
	 * @param filterStr
	 */
	private void filterData(String filterStr){
		List<EtongSortModel> filterDateList = new ArrayList<EtongSortModel>();
		
		if(TextUtils.isEmpty(filterStr)){
			filterDateList = SourceDateList;
		}else{
			filterDateList.clear();
			for(EtongSortModel sortModel : SourceDateList){
				String name = sortModel.getName();
				if(name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())){
					filterDateList.add(sortModel);
				}
			}
		}
		
		// 根据a-z进行排序
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}
}
