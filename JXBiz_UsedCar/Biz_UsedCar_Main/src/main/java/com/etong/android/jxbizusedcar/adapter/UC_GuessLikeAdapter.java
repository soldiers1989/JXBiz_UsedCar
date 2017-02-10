package com.etong.android.jxbizusedcar.adapter;

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
import com.etong.android.jxappusedcar.content.UC_CarDetail_Activity;
import com.etong.android.jxbizusedcar.R;
import com.etong.android.jxbizusedcar.bean.UC_HomePageCar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @desc 猜你喜欢的adapter
 * @createtime 2016/11/14 - 14:50
 * @Created by xiaoxue.
 */

public class UC_GuessLikeAdapter extends BaseAdapter {
    private List<UC_HomePageCar> mList;
    private Context mContext;
    private boolean isChageTime = false;

    public UC_GuessLikeAdapter(Context mContext) {
        mList = new ArrayList<>();
        this.mContext = mContext;
    }

    public void updateData(List<UC_HomePageCar> datas, boolean isChageTime) {
        mList = datas;
        this.isChageTime = isChageTime;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        UC_GuessLikeViewHolder holder = null;
        if (null == convertView) {
            holder = new UC_GuessLikeViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.uc_item_home_pager, null);
            holder.mUseTimeView = (TextView) convertView.findViewById(R.id.uc_tv_use_time);
            holder.mCarPriceView = (TextView) convertView.findViewById(R.id.uc_tv_car_price);
            holder.mCarProduceView = (TextView) convertView.findViewById(R.id.uc_tv_car_introduce);
            holder.mCarShowView = (ImageView) convertView.findViewById(R.id.uc_iv_show_pic);
            holder.mItemLayout = (LinearLayout) convertView.findViewById(R.id.uc_ll_car_item);
            convertView.setTag(holder);
        } else {
            holder = (UC_GuessLikeViewHolder) convertView.getTag();
        }
        try {
            ImageProvider.getInstance().loadImage(holder.mCarShowView, mList.get(position).getImage().trim(), R.mipmap.uc_select_image_loading);
        } catch (Exception e) {
        }

        String registerdate = null;
        if (!isChageTime) {
            registerdate = mList.get(position).getF_registerdate();
        } else {
            try {
                registerdate = mTimeConvert(mList.get(position).getF_registerdate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        holder.mUseTimeView.setText(registerdate + "  " + mList.get(position).getF_mileage() + "万公里");
        holder.mCarProduceView.setText(mList.get(position).getF_cartitle());
        holder.mCarPriceView.setText(mList.get(position).getF_price() + "万");
//        try {
//            holder.mUseTimeView.setText(mTimeConvert(mList.get(position).getF_registerdate()) + "  " + mList.get(position).getF_mileage() + "万公里");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        holder.mItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击车辆跳转到车辆详情页面
//                    Toast.makeText(mContext,position+"",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(mContext, UC_CarDetail_Activity.class);
                i.putExtra(UC_CarDetail_Activity.CAR_DETAIL_DVID, mList.get(position).getF_dvid());
                mContext.startActivity(i);
            }
        });

        return convertView;
    }


    public String mTimeConvert(String time) throws ParseException {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月d日");
        return sdf2.format(sdf1.parse(time));
    }

    private static class UC_GuessLikeViewHolder {
        public TextView mUseTimeView, mCarPriceView, mCarProduceView;
        public ImageView mCarShowView;
        public LinearLayout mItemLayout;

    }
}
