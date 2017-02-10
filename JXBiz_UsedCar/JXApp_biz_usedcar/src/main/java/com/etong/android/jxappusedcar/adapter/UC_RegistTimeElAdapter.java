package com.etong.android.jxappusedcar.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.content.UC_RegistTimeActivity;

public class UC_RegistTimeElAdapter extends BaseExpandableListAdapter{

	private Context mContext;
	private List<String> header; // 头部的String
	private HashMap<String, List<String>> child;  // 二级标题的String，包含了头部String和子孩子String

	private String currentSelectTime;

	// 构造方法
	public UC_RegistTimeElAdapter(Context context) {
		this.mContext = context;
		this.header = new ArrayList<String>();
		this.child = new HashMap<String, List<String>>();
	}

	/**
	 * @desc (更新数据的方法)
	 * @user sunyao
	 * @createtime 2016/10/19 - 17:48
	 */
	public void updateData(List<String> listDataHeader, HashMap<String, List<String>> listChildData) {
		this.header = listDataHeader;
		this.child = listChildData;
		notifyDataSetChanged();
	}
	
	@Override
	public int getGroupCount() {
		// 返回头部的个数
		return this.header.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// 返回子孩子的个数
		return this.child.get(this.header.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// 返回头部String
		return this.header.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// 返回孩子String
		return this.child.get(this.header.get(groupPosition)).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

		// 获取到头部的文字
		String headerTitle = (String) getGroup(groupPosition);
		
		// 生成Layout文件、设置文字
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.uc_regist_time_lv_item_header, parent, false);
		}
		TextView headerTxtYear = (TextView) convertView.findViewById(R.id.uc_tv_year_regist_time_lv_header);
		TextView headerTxtMon = (TextView) convertView.findViewById(R.id.uc_tv_mon_regist_time_lv_header);
		ImageView header_iv = (ImageView) convertView.findViewById(R.id.uc_iv_regist_time_lv_header);
		headerTxtYear.setText(headerTitle);
		
		// 如果一级菜单已经展开，改变ImageView的背景
		if (isExpanded) {
			headerTxtYear.setTextColor(mContext.getResources().getColor(R.color.used_car_cf1c36));
			header_iv.setBackgroundResource(R.mipmap.uc_regist_time_lv_parent_pressed);
		} else {
			headerTxtYear.setTextColor(mContext.getResources().getColor(R.color.used_car_2a2a2a));
			header_iv.setBackgroundResource(R.mipmap.uc_regist_time_lv_parent_normal);
		}

		// 如果当前选择的日期不为空时
		if(!TextUtils.isEmpty(currentSelectTime)) {
			if( headerTxtMon.getVisibility()==View.GONE) {
				try {
					int indexYear = currentSelectTime.indexOf("年");
					int indexMon = currentSelectTime.indexOf("月");
					String cYear = currentSelectTime.substring(0, indexYear);
					String cMon = currentSelectTime.substring(indexYear+1, indexMon);

					if (headerTitle.contains(cYear)) {
						headerTxtMon.setVisibility(View.VISIBLE);
						headerTxtMon.setText(cMon + "月");
					}
					if (headerTitle.contains(cYear)) {

					}
				} catch (Exception e) {

				}
			}
		}

		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		// 获取到二级的String
		final String childText = (String) getChild(groupPosition, childPosition);
		// 生成layout文件
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.uc_regist_time_lv_item_child, parent, false);
		}
		ViewGroup childContent = (ViewGroup) convertView.findViewById(R.id.uc_content_regist_time_lv_child);
		TextView child_text = (TextView) convertView.findViewById(R.id.uc_tv_regist_time_lv_child);

		final String tYear = this.header.get(groupPosition);
//		childContent.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//
////				Intent intent = new Intent();
////				intent.putExtra(UC_RegistTimeActivity.SET_YEAR, tYear);
////				intent.putExtra(UC_RegistTimeActivity.SET_MONTH, childText);
////				((Activity)mContext).setResult(UC_RegistTimeActivity.SELL_CAR_RESULT_CODE, intent);
////				((Activity)mContext).finish();
//			}
//		});

		child_text.setText(childText);
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

	// 获取到当前选择的时间
	public String getCurrentSelectTime() {
		return currentSelectTime;
	}

	// 设置当前的时间
	public void setCurrentSelectTime(String currentSelectTime) {
		this.currentSelectTime = currentSelectTime;
		notifyDataSetChanged();
	}

}
