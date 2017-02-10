package com.etong.android.jxappusedcar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.javabean.UC_CarDetailJavabean;
import com.etong.android.jxappusedcar.utils.UC_ConfigItemUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : by sunyao
 * @ClassName : UC_CarDetailAdapter
 * @Description : (这里用一句话描述这个类的作用)
 * @date : 2016/12/13 - 16:25
 */

public class UC_CarDetailAdapter extends BaseAdapter {

    private final LayoutInflater mInflater;
    private Context mContext;
    private UC_CarDetailJavabean.TitleConfigBean configBean;
    private List<String> mDatas;
    private final String[] config;

    public UC_CarDetailAdapter(Context context) {
        this.mContext = context;
        config = UC_ConfigItemUtils.getCarConfigStr();
        mInflater = LayoutInflater.from(context);
        mDatas = new ArrayList<String>();
    }

    public void updateDatas(List<String> datas) {
        this.mDatas = datas;
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.used_car_details_list_item, null);
            holder = new ViewHolder();
            holder.lText = (TextView) convertView.findViewById(R.id.used_car_list_item_title);
            holder.rText = (TextView) convertView.findViewById(R.id.used_car_list_item_value);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.lText.setText(config[position] + "");
        holder.rText.setText(mDatas.get(position) + "");

        return convertView;
    }

    static class ViewHolder {
        TextView lText;
        TextView rText;
    }
}
