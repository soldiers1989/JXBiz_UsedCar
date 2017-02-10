package com.etong.android.jxappfours.find_car.grand.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.etong.android.jxappfours.R;

import java.util.List;

/**
 * Created by Ellison.Sun on 2016/8/13.
 *
 * 维修详情适配器
 */
public class Find_car_MaintanceDetailAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mData;     // 传送过来的数据
    LayoutInflater mInflater;

    String [] strTitle = {
            "接车时间 ：",
            "4S店登记车牌号：",
//            "车系： ",
            "车型： ",
            "进厂里程： ",
            "维保机构： ",
            "维保类型： ",
            "维保项目：",
            "进度状态："
    };

    /**
     * 构造方法
     * @param context
     * @param data
     */
    public Find_car_MaintanceDetailAdapter(Context context, List<String> data) {
        this.mContext = context;
        this.mData = data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.find_car_content_mainten_detail_item, null);
            holder = new ViewHolder();
            holder.find_car_conten_mainten_title = (TextView) convertView.findViewById(R.id.find_car_conten_mainten_title);
            holder.find_car_conten_mainten_title_desc = (TextView) convertView.findViewById(R.id.find_car_conten_mainten_title_desc);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }

            holder.find_car_conten_mainten_title.setText(strTitle[position]);
            holder.find_car_conten_mainten_title_desc.setText(mData.get(position));

        return convertView;
    }
    static class ViewHolder {
        TextView find_car_conten_mainten_title;
        TextView find_car_conten_mainten_title_desc;
    }
}
