package com.etong.android.jxbizusedcar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.etong.android.jxbizusedcar.R;
import com.etong.android.jxbizusedcar.bean.UC_CityName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : by sunyao
 * @ClassName : UC_SelectCityMenuAdapter
 * @Description : (这里用一句话描述这个类的作用)
 * @date : 2016/10/26 - 18:15
 */

public class UC_SelectCityMenuAdapter extends BaseAdapter {

    private Context mContenxt;
    private List<UC_CityName> mDatas;

    public UC_SelectCityMenuAdapter(Context context) {
        mContenxt = context;
        mDatas = new ArrayList<UC_CityName>();
    }

    public void updateDatas(List<UC_CityName> datas){
        mDatas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UC_SelectCityMenuAdapter.UC_SelctCityHolder viewHolder = null;
        if (null == convertView) {
            viewHolder = new UC_SelectCityMenuAdapter.UC_SelctCityHolder();
            convertView= LayoutInflater.from(mContenxt).inflate(com.etong.android.jxappusedcar.R.layout.uc_content_menu_lv_header,null);
            viewHolder.carsetName =(TextView)convertView.findViewById(R.id.uc_content_menu_txt_lv_item);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (UC_SelectCityMenuAdapter.UC_SelctCityHolder) convertView.getTag();
        }
        viewHolder.carsetName.setText(mDatas.get(position).getCity_name());

        return convertView;
    }

    private static class UC_SelctCityHolder {
        public TextView carsetName;
    }
}

