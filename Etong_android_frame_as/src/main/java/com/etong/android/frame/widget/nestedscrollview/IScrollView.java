package com.etong.android.frame.widget.nestedscrollview;

import android.content.Context;
import android.support.v4.view.NestedScrollingChild;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * @desc (这里用一句话描述这个类的作用)
 * @createtime 2016/9/29 - 16:59
 * @Created by xiaoxue.
 */

public class IScrollView extends ScrollView {
    public IScrollView(Context context) {
        super(context);
    }

    public IScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mListener != null) {
            mListener.scrollViewListener(l, t, oldl, oldt);
        }
    }

    private IScrollViewListener mListener;
    public void setScrollViewListener(IScrollViewListener listener) {
        this.mListener = listener;
    }

}
