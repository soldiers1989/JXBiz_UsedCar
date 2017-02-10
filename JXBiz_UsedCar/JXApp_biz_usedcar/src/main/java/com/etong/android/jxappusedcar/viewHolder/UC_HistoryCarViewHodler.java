package com.etong.android.jxappusedcar.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.etong.android.jxappusedcar.R;

public class UC_HistoryCarViewHodler extends RecyclerView.ViewHolder {

   public TextView mHisCarView;
    public LinearLayout mHisCarLayout;

    public UC_HistoryCarViewHodler(View itemView) {
        super(itemView);

        mHisCarView = (TextView) itemView.findViewById(R.id.used_car_tv_his_car);
        mHisCarLayout = (LinearLayout) itemView.findViewById(R.id.used_car_ll_his_car_layout);

    }
}