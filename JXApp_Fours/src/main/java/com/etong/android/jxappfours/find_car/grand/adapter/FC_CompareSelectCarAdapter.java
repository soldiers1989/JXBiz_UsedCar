package com.etong.android.jxappfours.find_car.grand.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;

import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_Add_VechileStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ellison.Sun on 2016/9/5.
 *
 * 选择车型的Adapter
 */
public class FC_CompareSelectCarAdapter extends BaseAdapter {

    private Context mContext;
    private List<Models_Contrast_Add_VechileStyle> mDatas;    // 存放数据的List

    public FC_CompareSelectCarAdapter(Context context) {
        this.mContext = context;
        mDatas = new ArrayList<Models_Contrast_Add_VechileStyle>();
    }

    /**
     * 更新数据的方法
     * @param datas
     */
    public void updateListData(List<Models_Contrast_Add_VechileStyle> datas) {
        this.mDatas = datas;
        notifyDataSetChanged(); // 更新数据
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
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fc_car_config_select_car_listview_item, null);
            holder = new ViewHolder();
            holder.tvCarName  = (TextView) convertView.findViewById(R.id.fc_select_car_tv_carname);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvCarName.setText(mDatas.get(position).getTitle());

        return convertView;
    }

    class ViewHolder {
        TextView tvCarName;
    }
}
