package com.etong.android.jxappdata;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.utils.ActivitySkipUtil;
import com.etong.android.frame.utils.ListAdapter;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappdata.common.BaseActivity;
import com.etong.android.jxappdata.common.HttpUri;
import com.etong.android.util.SerializableObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据内容页 点击进入的详细数据页面
 * 
 * @author Administrator
 * 
 */
public class DataItemActivity extends BaseActivity {
	private ListView mListView;
	private LinearLayout ll_item;
	private ImageView icon_image;
	private TextView item_title;
	// private int small_icon[] = { R.drawable.rzsj, R.drawable.yqsj,
	// R.drawable.hksj, R.drawable.rqfx };
	// private int fours_small_icon[] = { R.drawable.xssj, R.drawable.wxbysj,
	// R.drawable.gcfx, R.drawable.zzsjfx };
	private TitleBar mTitleBar;
	private String[] data;
	private String title_name;
	private String[] icon;
	private String[][] titleArray = { { "月", "年", "全部" }, { "年", "全部" },
			{ "年", "全部" }, { "全部" } };
	// private String[][] titleArray2 = { { "各店", "年", "全部" },
	// { "各店", "年", "全部" }, { "年", "全部" }, { "年", "全部" } };
	private String[][] titleArray2 = { { "年", "全部" }, { "年", "全部" },
			{ "年", "全部" }, { "年", "全部" } };

	private boolean[][] flagArray = { { true, false, false }, { false, false },
			{ false, false }, { false } };
	// private boolean[][] flagArray2 = { { true, false, false },
	// { true, false, false }, { false, false }, { false, false } };
	private boolean[][] flagArray2 = { { false, false }, { false, false },
			{ false, false }, { false, false } };

	private boolean[][] buttonVisibyArray = { { true, true, false },
			{ true, false }, { true, false }, { false } };
	// private boolean[][] buttonVisibyArray2 = { { true, true, false },
	// { true, true, false }, { true, false }, { true, false } };
	private boolean[][] buttonVisibyArray2 = { { true, false },
			{ true, false }, { true, false }, { true, false } };

	private String bottonTitleArray[][] = {
			{ "1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月",
					"11月", "12月" },
			{ "2012年", "2013年", "2014年", "2015年", "2016年" },
			{ "2012年", "2013年", "2014年", "2015年", "2016年" } };

	private String[][] url = {
			{ HttpUri.FINANCED_AMOUNT, HttpUri.FINANCED_NUMBER },
			{ HttpUri.OVERDUE_DATA }, { HttpUri.RETURNED_MONEY },
			{ HttpUri.PERSON_SEX, HttpUri.PERSON_AGE, HttpUri.PERSON_MARRIAGE } };
	private String[][] url2 = { { HttpUri.SALE_NUMBER, HttpUri.SALE_AMOUNT },
			{ HttpUri.MAINTENANCE_NUMBER, HttpUri.MAINTENANCE_AMOUNT },
			{ HttpUri.BUY_TYPE, HttpUri.BUY_BRAND },
			{ HttpUri.INCOME_DATE, HttpUri.SALENUM_DATE } };
	JSONObject jsonObject;
	List list;
	JSONArray children;
	int type;
	@Override
	protected void onInit(@Nullable Bundle savedInstanceState) {
		setContentView(R.layout.activity_data_item);
		Intent intent = getIntent();
		list=new ArrayList<>();
		
		
		
		try {
			//得到传过来的配置
			Bundle bundle = getIntent().getExtras();
			SerializableObject serializableMap = (SerializableObject) bundle
					.get("dataMap");
			Map map = (Map) serializableMap.getObject();
			title_name = (String) map.get("name");
			// int position=intent.getIntExtra("position",-1);
//		data = intent.getStringArrayExtra("data");
			jsonObject=(JSONObject)map.get("data");
			children=jsonObject.getJSONArray("children");
			icon = new String[children.size()];
			for(int i=0;i<children.size();i++){
				icon[i]= children.getJSONObject(i).getString("icon");
				String resourcename=children.getJSONObject(i).getString("resourceName");
				list.add(resourcename);
				
			}
		} catch (Exception e) {
			ActivitySkipUtil.skipActivity(this, StartActivity.class);
			this.finish();
		}

		mTitleBar = new TitleBar(this);
		mTitleBar = new TitleBar(this);
		mTitleBar.showBackButton(true);

		mTitleBar.showNextButton(false);
		mTitleBar.setTitle(title_name);
		//设置基类里的pageName
		this.myTitle=title_name;
		initView();
	}

	public void initView() {
		mListView = findViewById(R.id.item_listView, ListView.class);
		final ListAdapter<String> adapter = new ListAdapter<String>(this,
				R.layout.activity_data_item_adapter) {

			@Override
			protected void onPaint(View view, final String data,
					final int position) {
				ll_item = (LinearLayout) view.findViewById(R.id.ll_item);
				icon_image = (ImageView) view.findViewById(R.id.iv_img);
				item_title = (TextView) view.findViewById(R.id.tv_item_title);
				icon_image.setBackgroundResource(getImageIdByName(icon[position]));
				item_title.setText(data);

				view.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {

						showItemData(data, position);

					}
				});

			}

		};
		adapter.addAll(list);
		mListView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}
	/**  
     * 根据图片名称获取R.java中对应的id  
     *   
     * @param name  
     * @return  
     */  
    public static int getImageIdByName (String name) {  
        int value = 0;  
        if (null != name) {  
            if (name.indexOf(".") != -1) {  
                name = name.substring(0, name.indexOf("."));  
            }  
            Class<com.etong.android.jxappdata.R.drawable> cls = R.drawable.class;
            try {    
                value = cls.getDeclaredField(name).getInt(null);    
            } catch (Exception e) {    
                  
            }             
        }  
        return value;  
    }  
	protected void showItemData(String data, int position) {
	
	

//		Intent intent = new Intent(DataItemActivity.this,BusinessAnalysisActivity.class); 
//		intent.putExtra("titleName", data);
//		startActivity(intent);
		
	
		type=(int)children.getJSONObject(position).get("type");
		
		if(type==0){//列表页
       	 if((int)children.getJSONObject(position).get("moduleStatus")==3){
//       		DataItemActivity mainActivity = new DataItemActivity();
    		 Intent intent = new Intent(DataItemActivity.this,DataItemActivity.class);
//    		 intent.setClass(DataItemActivity.this, mainActivity);
    		 Map map = new HashMap<>();
    		 map.put("name", data);
//    		 map.put("icon",icon);
    		 map.put("data",children.getJSONObject(position));
    		 
    		 // 传递数据
    		 final SerializableObject myMap = new SerializableObject();
    		 myMap.setObject(map);// 将map数据添加到封装的myMap中
    		 Bundle bundle = new Bundle();
    		 bundle.putSerializable("dataMap", myMap);
    		 intent.putExtras(bundle);
    		 startActivity(intent);
    		 
    	 }else if((int)children.getJSONObject(position).get("moduleStatus")==4){
    		 toastMsg("您暂无权限！");
    	 }else{
    		 Intent intent=new Intent(DataItemActivity.this,ComeSoonActivity.class);
				if((int) children.getJSONObject(position).get("moduleStatus")==1){
					intent.putExtra("name", data);
					intent.putExtra("data", "正在接入");
				}else if((int) children.getJSONObject(position).get("moduleStatus")==2){
					intent.putExtra("name", data);
					intent.putExtra("data", "尚未接入");
				}
			 startActivity(intent);
    	 }
		}
		else if(type==1){//数据页
			Intent intent = new Intent(DataItemActivity.this,
					ChartsActivity.class);
			Map map = new HashMap<>();
			map.put("itemName", data);
//		map.put("icon",small_icon[position]);
			map.put("data",children.getJSONObject(position));
			map.put("bottonTitleArray", bottonTitleArray);
			// 传递数据
			final SerializableObject myMap = new SerializableObject();
			myMap.setObject(map);// 将map数据添加到封装的myMap中
			Bundle bundle = new Bundle();
			bundle.putSerializable("dataMap", myMap);
			intent.putExtras(bundle);
			startActivity(intent);
		}else if(type==2){//个性化页面
			try {
				Class activity= Class.forName("com.etong.android.jxappdata.personality.BusinessAnalysisActivity");
				Intent intent = new Intent(DataItemActivity.this,activity);  
				intent.putExtra("titleName", data);
				startActivity(intent);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	
	}
}
