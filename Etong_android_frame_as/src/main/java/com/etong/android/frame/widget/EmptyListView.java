package com.etong.android.frame.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.etong.android.frame.R;

/**
 * Created by Ellison.Sun on 2016/8/24.
 *
 * 数据为空的显示页面
 */
public class EmptyListView extends View {
    private Context mContext;
    private View view;      // 通过布局生成的View
    private TextView emptyTv;   // 通过改变文字来设置ListView为空时的显示
    private ViewGroup emptyContent; // 没有数据的时候content
    public EmptyListView(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public EmptyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    public EmptyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    /**
     * 初始化操作
     */
    private void initView() {

        view = LayoutInflater.from(mContext).inflate(R.layout.default_empty_listview, null);
        emptyTv = (TextView)view.findViewById(R.id.default_empty_lv_textview);
        emptyContent = (ViewGroup)view.findViewById(R.id.default_empty_content);
    }

    // 设置没有数据的显示文字
    public void setEmptyText(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        emptyTv.setText(text);
    }

    // 设置点击刷新的点击事件
    public void setRefreshOnclickListener(OnClickListener onclickListener) {
        emptyContent.setOnClickListener(onclickListener);
    }
}
