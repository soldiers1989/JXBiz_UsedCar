package com.etong.android.jxbizusedcar.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.jxbizusedcar.R;
import com.etong.android.jxbizusedcar.bean.UC_CityName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : by sunyao
 * @ClassName : UC_AlwaysSelectAdapter
 * @Description : (常选城市的Adapter)
 * @date : 2016/10/24 - 14:38
 */

public class UC_AlwaysSelectAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private Context context;
    private List<UC_CityName> mDatas;

    public UC_AlwaysSelectAdapter(Context context) {
        this.context = context;
        this.mDatas = new ArrayList<UC_CityName>();
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void updateDatas(List<UC_CityName> datas) {
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.uc_lv_header_item_region_select_always_city, parent, false);
            vh = new ViewHolder();
            vh.tv = (TextView) convertView.findViewById(R.id.uc_txt_header_item_region_select_always_city);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.tv.setText(mDatas.get(position).getCity_name());

        return convertView; // 返回ImageView
    }

    /*
     * 功能：获得当前选项的ID
     *
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int position) {
        //System.out.println("getItemId = " + position);
        return position;
    }

    /*
     * 功能：获得当前选项
     *
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    /*
     * 获得数量
     *
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public int getCount() {
        return mDatas.size();
    }

    class ViewHolder {
        public TextView tv;
    }
}
