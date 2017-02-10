package com.etong.android.jxappfours.find_car.grand.view;
/**
 * Created by Ellison.Sun on 2016/8/10.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 为了GridView在ListView中画出自己的高度
 */
public class FC_GrandGridView extends GridView {
    public FC_GrandGridView(Context context) {
        super(context);

    }

    public FC_GrandGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
