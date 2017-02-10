package com.etong.android.jxappmessage.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 重写ListView，让其失去滑动特性，解决scrollview嵌套listview滑动冲突
 * Created by Administrator on 2016/8/8 0008.
 */
public class MessageNoScrollListView extends ListView {
    public MessageNoScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }
}
