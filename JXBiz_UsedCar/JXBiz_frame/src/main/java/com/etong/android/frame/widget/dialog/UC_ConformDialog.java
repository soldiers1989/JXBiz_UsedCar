package com.etong.android.frame.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.etong.android.frame.R;


/**
 * Created by Administrator on 2016/10/14.
 */

public class UC_ConformDialog extends Dialog {
    private TextView mTitle;
    private TextView mContent;
    private Button mConfirmButton;
    private String title = "标题";
    private String content = "内容";
    private int contentSize = 0;
    private String mConfirm = "确定";
    private boolean mIsCanCancel = false;
    private Context mContext;


    private View.OnClickListener mConfirmListerer;
    private ImageView mCancel;


    public UC_ConformDialog(Context context, boolean isCanCancel) {
        super(context, R.style.dialog_nor);
        this.mContext = context;
        this.mIsCanCancel = isCanCancel;
    }

    public UC_ConformDialog(Context context) {
        super(context, R.style.dialog_nor);
        this.mContext = context;
    }


    public void setConfirmButtonClickListener(String confirm, View.OnClickListener onClickListener) {
        this.mConfirm = confirm;
        this.mConfirmListerer = onClickListener;

    }

    private View.OnClickListener mCancleListener = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            UC_ConformDialog.this.dismiss();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setCancelable(false);
        this.setContentView(R.layout.uc_dialog_conform);
        mConfirmButton = (Button) this.findViewById(R.id.uc_c_btn_conform);
        mContent = (TextView) this.findViewById(R.id.uc_c_tv_dialog_cotent);
        mTitle = (TextView) this.findViewById(R.id.uc_c_tv_dialog_title);
        mCancel = (ImageView) this.findViewById(R.id.uc_c_iv_cancel);

        if (mIsCanCancel) {
            mCancel.setVisibility(View.VISIBLE);
        } else {
            mCancel.setVisibility(View.GONE);
        }

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
        mTitle.setText(title);


        if (null != mConfirmListerer)
            mConfirmButton.setOnClickListener(mConfirmListerer);
        if (!"".equals(mConfirm) && !mConfirm.isEmpty())
            mConfirmButton.setText(mConfirm);
        if (null != mCancleListener)
            mCancel.setOnClickListener(mCancleListener);
    }
}
