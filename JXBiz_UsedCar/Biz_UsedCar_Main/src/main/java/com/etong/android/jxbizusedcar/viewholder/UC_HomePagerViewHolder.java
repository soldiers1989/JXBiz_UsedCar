package com.etong.android.jxbizusedcar.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.etong.android.jxbizusedcar.R;

/**
 * Created by Dasheng on 2016/10/11.
 */

public class UC_HomePagerViewHolder extends RecyclerView.ViewHolder {

    public TextView mUseTimeView,mCarPriceView,mCarProduceView;
    public ImageView mCarShowView;
    public LinearLayout mItemLayout;

    public UC_HomePagerViewHolder(View itemView) {
        super(itemView);

        mUseTimeView = (TextView) itemView.findViewById(R.id.uc_tv_use_time);
        mCarPriceView = (TextView) itemView.findViewById(R.id.uc_tv_car_price);
        mCarProduceView = (TextView) itemView.findViewById(R.id.uc_tv_car_introduce);
        mCarShowView = (ImageView) itemView.findViewById(R.id.uc_iv_show_pic);
        mItemLayout = (LinearLayout) itemView.findViewById(R.id.uc_ll_car_item);

    }
}
