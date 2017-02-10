package com.etong.android.jxappfours.find_car.grand.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.grand.bean.Find_car_GrandBrandItemBean;

import java.util.List;

public class Find_car_GrandItemAdapter extends BaseAdapter implements SectionIndexer{
	private List<Find_car_GrandBrandItemBean> list = null;
	private Context mContext;

	public Find_car_GrandItemAdapter(Context mContext, List<Find_car_GrandBrandItemBean> list) {
		this.mContext = mContext;
		this.list = list;
	}
	
	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * @param list
	 */
	public void updateListView(List<Find_car_GrandBrandItemBean> list){
		this.list = list;
		notifyDataSetChanged();
	}

	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		final Find_car_GrandBrandItemBean mContent = list.get(position);
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.grand_listview_item, null);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.grand_catalog);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.grand_title);
			/*viewHolder.grandTitleDivider = (View) view.findViewById(R.id.grand_title_divider);*/
			viewHolder.grandBottomDivider = (View) view.findViewById(R.id.grand_bottom_divider);
			viewHolder.ivPicUrl = (ImageView) view.findViewById(R.id.grand_imageview);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		
		//根据position获取分类的首字母的Char ascii值
		int section = getSectionForPosition(position);
		
		//如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
		if(position == getPositionForSection(section)){
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(mContent.getLetter());
		}else{
			viewHolder.tvLetter.setVisibility(View.GONE);
		}
	
		viewHolder.tvTitle.setText(this.list.get(position).getTitle());
		ImageProvider.getInstance().loadImage(viewHolder.ivPicUrl, this.list.get(position).getImage(),R.mipmap.fours_grand_loading);

		return view;

	}
	final static class ViewHolder {
		TextView tvLetter;				// 显示的字母
		TextView tvTitle;				// 显示的车的名字
		View grandTitleDivider;		// 顶部的分割线
		ImageView ivPicUrl;			// 图片的链接
		View grandBottomDivider;		// 底部的分割线
	}

	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	public int getSectionForPosition(int position) {
		return list.get(position).getLetter().charAt(0);
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getLetter();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * 提取英文的首字母，非英文字母用#代替。
	 * 
	 * @param str
	 * @return
	 */
	public String getAlpha(String str) {
		String  sortStr = str.trim().substring(0, 1).toUpperCase();
		// 正则表达式，判断首字母是否是英文字母
		if (sortStr.matches("[A-Z]")) {
			return sortStr;
		} else {
			return "#";
		}
	}

	@Override
	public Object[] getSections() {
		return null;
	}
}