package com.etong.android.frame.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.etong.android.frame.R;

/**
 * @author : by sunyao
 * @ClassName : EtongStatusView
 * @Description : (一个方便在多种状态切换的view)
 * @date : 2016/12/13 - 11:12
 */
public class EtongStatusView extends RelativeLayout {
    public static final int STATUS_CONTENT      = 0x00;
    public static final int STATUS_EMPTY        = 0x01;
    public static final int STATUS_ERROR        = 0x02;
    public static final int STATUS_NO_NETWORK   = 0x03;

    private static final int NULL_RESOURCE_ID   = -1;

    private View mEmptyView;
    private View mErrorView;
    private View mNoNetworkView;
    private View mContentView;
    private View mEmptyRetryView;
    private View mErrorRetryView;
    private View mNoNetworkRetryView;
    private TextView mEmptyTextView;
    private TextView mErrorTextView;
    private TextView mNoNetworkTextView;
    private int mEmptyViewResId;
    private int mErrorViewResId;
    private int mNoNetworkViewResId;
    private int mViewStatus;

    private LayoutInflater mInflater;
    private OnClickListener mOnRetryClickListener;
    private final ViewGroup.LayoutParams mLayoutParams = new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    public EtongStatusView(Context context) {
        this(context, null);
    }

    public EtongStatusView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EtongStatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray a =
                context.obtainStyledAttributes(attrs, R.styleable.EtongStatusView, defStyleAttr, 0);

        mEmptyViewResId =
                a.getResourceId(R.styleable.EtongStatusView_emptyView, R.layout.etong_default_empty_view);
        mErrorViewResId =
                a.getResourceId(R.styleable.EtongStatusView_errorView, R.layout.etong_default_error_view);
        mNoNetworkViewResId =
                a.getResourceId(R.styleable.EtongStatusView_noNetworkView, R.layout.etong_default_no_network_view);
        a.recycle();
    }

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        mInflater = LayoutInflater.from(getContext());
    }

    /** 获取当前状态 */
    public int getViewStatus(){
        return mViewStatus;
    }

    /**
     * 设置重试点击事件
     * @param onRetryClickListener 重试点击事件
     */
    public void setOnRetryClickListener(OnClickListener onRetryClickListener) {
        this.mOnRetryClickListener = onRetryClickListener;
    }

    /** 显示空视图 */
    public final void showEmpty(String text) {
        mViewStatus = STATUS_EMPTY;
        if(null == mEmptyView){
            mEmptyView = mInflater.inflate(mEmptyViewResId, null);
            mEmptyRetryView = mEmptyView.findViewById(R.id.content_empty_view);
            mEmptyTextView = (TextView)mEmptyView.findViewById(R.id.tv_empty_view_txt);
            if(null != mOnRetryClickListener && null != mEmptyRetryView){
                mEmptyRetryView.setOnClickListener(mOnRetryClickListener);
            }
            // 设置视图中TextView显示的文字
            if (null!=mEmptyTextView && !TextUtils.isEmpty(text)) {
                mEmptyTextView.setText(text);
            }
            addView(mEmptyView, 0, mLayoutParams);
        }
        showViewByStatus(mViewStatus);
    }
    /** 显示错误视图 */
    public final void showError(String text) {
        mViewStatus = STATUS_ERROR;
        if(null == mErrorView){
            mErrorView = mInflater.inflate(mErrorViewResId, null);
            mErrorRetryView = mErrorView.findViewById(R.id.content_error_view);
            mErrorTextView = (TextView)mEmptyView.findViewById(R.id.tv_error_view_txt);
            if(null != mOnRetryClickListener && null != mErrorRetryView){
                mErrorRetryView.setOnClickListener(mOnRetryClickListener);
            }
            // 设置视图中TextView显示的文字
            if (null!=mErrorTextView && !TextUtils.isEmpty(text)) {
                mErrorTextView.setText(text);
            }
            addView(mErrorView,0, mLayoutParams);
        }
        showViewByStatus(mViewStatus);
    }
    /** 显示无网络视图 */
    public final void showNoNetwork(String text) {
        mViewStatus = STATUS_NO_NETWORK;
        if(null == mNoNetworkView){
            mNoNetworkView = mInflater.inflate(mNoNetworkViewResId, null);
            mNoNetworkRetryView = mNoNetworkView.findViewById(R.id.content_no_network_view);
            mNoNetworkTextView = (TextView)mEmptyView.findViewById(R.id.tv_no_network_view_txt);
            if(null != mOnRetryClickListener && null != mNoNetworkRetryView){
                mNoNetworkRetryView.setOnClickListener(mOnRetryClickListener);
            }
            // 设置视图中TextView显示的文字
            if (null!=mNoNetworkTextView && !TextUtils.isEmpty(text)) {
                mNoNetworkTextView.setText(text);
            }
            addView(mNoNetworkView,0, mLayoutParams);
        }
        showViewByStatus(mViewStatus);
    }

    private void showViewByStatus(int viewStatus) {
        if(null != mEmptyView){
            mEmptyView.setVisibility(viewStatus == STATUS_EMPTY ? View.VISIBLE : View.GONE);
        }
        if(null != mErrorView){
            mErrorView.setVisibility(viewStatus == STATUS_ERROR ? View.VISIBLE : View.GONE);
        }
        if(null != mNoNetworkView){
            mNoNetworkView.setVisibility(viewStatus == STATUS_NO_NETWORK ? View.VISIBLE : View.GONE);
        }
    }

}
