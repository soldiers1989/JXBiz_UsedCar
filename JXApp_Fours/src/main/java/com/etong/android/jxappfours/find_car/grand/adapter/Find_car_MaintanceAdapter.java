package com.etong.android.jxappfours.find_car.grand.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.etong.android.frame.utils.SerializableObject;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.grand.bean.Find_car_MaintanceItemBean;
import com.etong.android.jxappfours.find_car.grand.maintain_progress.Find_car_MaintainDetailActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ellison.Sun on 2016/8/13.
 * <p/>
 * 维修进度适配器
 */
public class Find_car_MaintanceAdapter extends BaseAdapter {

    private Context mContext;
    LayoutInflater mInflater;
    private List<Find_car_MaintanceItemBean> mData;
    private String[] mMaintanStates={"预约","已接车,待派工","已派工,在修","已完工,待结算","已结算","已收款","已出厂"};

    public Find_car_MaintanceAdapter(Context context, List<Find_car_MaintanceItemBean> mData) {
        this.mContext = context;
        this.mData = mData;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.find_car_mainten_list_item, null);
            holder = new ViewHolder();
            holder.item_serial_number = (TextView) convertView.findViewById(R.id.item_serial_number);
            holder.item_maintan_time = (TextView) convertView.findViewById(R.id.item_maintan_time);
            holder.item_plate_number = (TextView) convertView.findViewById(R.id.item_plate_number);
            holder.item_maintan_type = (TextView) convertView.findViewById(R.id.item_maintan_type);
            holder.item_maintan_state = (TextView) convertView.findViewById(R.id.item_maintan_state);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.item_maintan_state.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        holder.item_plate_number.setText(mData.get(position).getVehicleno());
        holder.item_serial_number.setText(position + 1 + "");
        holder.item_maintan_time.setText(mData.get(position).getBilldate());
        holder.item_maintan_type.setText(mData.get(position).getSvtype());
        if(mData.get(position).getStarts().equals("-1")){
            holder.item_maintan_state.setText("已作废");
        }else{
            holder.item_maintan_state.setText(mMaintanStates[Integer.valueOf(mData.get(position).getStarts())]);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext, holder.item_plate_number.getText()+"", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(mContext, Find_car_MaintainDetailActivity.class);

                Map map = new HashMap<>();
                map.put("data", mData.get(position));
                // 传递数据
                final SerializableObject myMap = new SerializableObject();
                myMap.setObject(map);// 将map数据添加到封装的myMap中
                Bundle bundle = new Bundle();
                bundle.putSerializable("dataMap", myMap);
                i.putExtras(bundle);

                mContext.startActivity(i);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView item_serial_number;
        TextView item_maintan_time;
        TextView item_plate_number;
        TextView item_maintan_type;
        TextView item_maintan_state;
    }

    public void updateDataChanged(List<Find_car_MaintanceItemBean> data) {
        this.mData = data;
        this.notifyDataSetChanged();
    }
}
