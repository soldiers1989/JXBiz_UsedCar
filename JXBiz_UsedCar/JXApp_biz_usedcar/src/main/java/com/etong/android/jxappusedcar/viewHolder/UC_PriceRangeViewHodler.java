package com.etong.android.jxappusedcar.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import com.etong.android.jxappusedcar.R;

/**
 * Created by Dasheng on 2016/10/16.
 */

public class UC_PriceRangeViewHodler extends RecyclerView.ViewHolder {

    public RadioButton mPriceView;

    public UC_PriceRangeViewHodler(View itemView) {
        super(itemView);

         mPriceView = (RadioButton) itemView.findViewById(R.id.used_car_rb_price);

    }
}
