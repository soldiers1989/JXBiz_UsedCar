package com.etong.android.jxbizusedcar.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import com.etong.android.jxbizusedcar.R;

/**
 * Created by Dasheng on 2016/10/14.
 */

public class UC_MyScrollerView extends ScrollView {

    private ScrollViewListener mScrollListener;
    private View searchView, searchContent;
    private int searchHeight;
    private Context mContext;
    private int mChildHeight;

    public UC_MyScrollerView(Context context) {
        super(context);
        mContext = context;
    }

    public UC_MyScrollerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public UC_MyScrollerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){

            case MotionEvent.ACTION_UP:

                int contentHeight=getChildAt(0).getHeight();
                int scrollHeight=getHeight();

                if( mScrollListener!= null ) {
                   if( getScrollY()+scrollHeight>=contentHeight||contentHeight<=scrollHeight){
                        mScrollListener.onScrollToBottom();
                    }
                }
                break;

        }

        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (getScrollY() >= 0 && searchView != null && searchHeight != 0) {

            float scale = (float) getScrollY() / (float) searchHeight;

            if (scale < 1) {
//                searchView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.bg_edittext_gray));
                searchView.setBackgroundColor(Color.argb((int) (255 * scale), 207, 28, 54));

            } else {
                searchView.setBackgroundColor(Color.argb(240, 207, 28, 54));
//                searchContent.setBackgroundColor(Color.argb(255, 255, 255, 255));
            }
        }
    }

    public void setGradualLayout(View view) {

        if (view == null) return;
        searchView = view;
        int w = MeasureSpec.makeMeasureSpec(0,
                MeasureSpec.UNSPECIFIED);
        int h = MeasureSpec.makeMeasureSpec(0,
                MeasureSpec.UNSPECIFIED);
        searchView.measure(w, h);
        searchHeight = searchView.getMeasuredHeight();

        searchContent = view.findViewById(R.id.uc_rl_search_content);
    }

    public interface ScrollViewListener{
        void onScrollToBottom();
    }


    public void setmScrollListener(ScrollViewListener mScrollListener) {
        this.mScrollListener = mScrollListener;
    }
}


