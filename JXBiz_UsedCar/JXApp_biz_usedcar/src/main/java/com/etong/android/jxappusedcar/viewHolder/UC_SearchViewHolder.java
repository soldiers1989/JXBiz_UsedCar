package com.etong.android.jxappusedcar.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.etong.android.jxappusedcar.R;

/**
 * Created by Dasheng on 2016/10/11.
 */

public class UC_SearchViewHolder extends RecyclerView.ViewHolder {

    public TextView mHotCarNameView;

    public UC_SearchViewHolder(View itemView) {
        super(itemView);

        mHotCarNameView = (TextView) itemView.findViewById(R.id.used_car_tv_hot_car);


    }
}
