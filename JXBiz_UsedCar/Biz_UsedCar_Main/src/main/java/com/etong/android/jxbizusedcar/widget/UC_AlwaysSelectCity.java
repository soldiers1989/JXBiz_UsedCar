package com.etong.android.jxbizusedcar.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @author : by sunyao
 * @ClassName : UC_AlwaysSelectCity
 * @Description : (GridView在ListView中画最小高度)
 * @date : 2016/10/24 - 14:25
 */

public class UC_AlwaysSelectCity extends GridView {
    public UC_AlwaysSelectCity(Context context) {
        super(context);
    }
    public UC_AlwaysSelectCity(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
