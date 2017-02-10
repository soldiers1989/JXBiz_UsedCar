package com.etong.android.jxappusedcar.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.bean.UC_CarsetMenuBean;
import com.etong.android.jxappusedcar.content.UC_SelectImageActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dasheng on 2016/10/16.
 */

public class UC_SelectBrandMenuAdapter extends BaseAdapter {

    private Context mContenxt;
    private List<UC_CarsetMenuBean> mDatas;

    public UC_SelectBrandMenuAdapter(Context context) {
        mContenxt = context;
        mDatas = new ArrayList<UC_CarsetMenuBean>();
    }

    public void updateDatas(List<UC_CarsetMenuBean> datas){
        mDatas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UC_SelectBrandMenuHolder viewHolder = null;
        if (null == convertView) {
            viewHolder = new UC_SelectBrandMenuHolder();
            convertView=LayoutInflater.from(mContenxt).inflate(R.layout.uc_content_menu_lv_header,null);
            viewHolder.carsetName =(TextView)convertView.findViewById(R.id.uc_content_menu_txt_lv_item);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (UC_SelectBrandMenuHolder) convertView.getTag();
        }
        viewHolder.carsetName.setText(mDatas.get(position).getName());

        return convertView;
    }

    private static class UC_SelectBrandMenuHolder {
        public TextView carsetName;
    }
}
