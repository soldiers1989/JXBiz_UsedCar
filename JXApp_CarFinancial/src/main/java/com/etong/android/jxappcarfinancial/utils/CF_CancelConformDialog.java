package com.etong.android.jxappcarfinancial.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.etong.android.jxappcarfinancial.R;


/**
 * Created by Administrator on 2016/10/14.
 */

public class CF_CancelConformDialog extends Dialog {
    private TextView mTitle;
    private TextView mContent;
    private Button mConfirmButton;
    private Button mCancleButton;
    private String title;
    private String content = "内容";
    private int contentSize = 0;
    private String mConfirm = "确定";
    private String mCancle = "取消";
    private boolean mIsCanCancel = false;
    private Context mContext;


    private View.OnClickListener mConfirmListerer;
    private View.OnClickListener mCancleListener = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            CF_CancelConformDialog.this.dismiss();
        }
    };


    public CF_CancelConformDialog(Context context, boolean iscanCancel) {
        super(context, com.etong.android.frame.R.style.dialog_nor);
        this.mContext = context;
        this.mIsCanCancel = iscanCancel;
    }


    public CF_CancelConformDialog(Context context) {
        super(context, com.etong.android.frame.R.style.dialog_nor);
        this.mContext = context;
    }


    public void setConfirmButtonClickListener(String confirm, View.OnClickListener onClickListener) {
        this.mConfirm = confirm;
        this.mConfirmListerer = onClickListener;

    }

    public void setCancleButtonClickListener(String cancle, View.OnClickListener onClickListener) {
        this.mCancle = cancle;
        this.mCancleListener = onClickListener;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setCancelable(mIsCanCancel);
        this.setContentView(R.layout.cf_dialog_cancel_conform);
        mConfirmButton = (Button) this.findViewById(R.id.cf_cc_btn_sure);
        mCancleButton = (Button) this.findViewById(R.id.cf_cc_btn_cancels);
        mContent = (TextView) this.findViewById(R.id.cf_cc_tv_dialog_cotent);
        mTitle = (TextView) this.findViewById(R.id.cf_cc_tv_dialog_title);


        /**设置窗口位置 宽度*/
        getWindow().setGravity(Gravity.CENTER); //显示在正中间
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = d.getWidth() - 128; //设置dialog的宽度为当前手机屏幕的宽度
        getWindow().setAttributes(p);


        ViewTreeObserver observer = mContent.getViewTreeObserver(); // textAbstract为TextView控件
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                ViewTreeObserver obs = mContent.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (mContent.getLineCount() >= 2) {//大于一行居左显示
                    mContent.setGravity(Gravity.LEFT);
                }
            }
        });
    }


    public void setTitle(String title) {
        this.title = title;
        if (this.isShowing()) {
            mTitle.setVisibility(View.VISIBLE);
            mTitle.setText(title);
        }
    }

    public void setContent(String content) {
        this.content = content;
        if (this.isShowing()) {
            mContent.setText(content);
        }
    }

    public void setContentSize(int textSize) {
        this.contentSize = textSize;
        if (this.isShowing()) {
            mContent.setTextSize(textSize);
        }
    }

    @Override
    public void show() {
        super.show();

        if (0 != contentSize) {
            mContent.setTextSize(contentSize);
        }

        mContent.setText(content);

        if (null != title) {
            mTitle.setText(title);
            mTitle.setVisibility(View.VISIBLE);
            mContent.setBackgroundColor(mContext.getResources().getColor(com.etong.android.frame.R.color.white));
        }

        if (null != mConfirmListerer)
            mConfirmButton.setOnClickListener(mConfirmListerer);
        if (null != mCancleListener)
            mCancleButton.setOnClickListener(mCancleListener);
        if (!"".equals(mConfirm) && !mConfirm.isEmpty())
            mConfirmButton.setText(mConfirm);
        if (!"".equals(mCancle) && !mCancle.isEmpty())
            mCancleButton.setText(mCancle);
    }
}
