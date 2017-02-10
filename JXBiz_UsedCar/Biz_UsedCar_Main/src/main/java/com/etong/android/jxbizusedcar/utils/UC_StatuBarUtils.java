package com.etong.android.jxbizusedcar.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * Created by Dasheng on 2016/10/14.
 */

public class UC_StatuBarUtils {

    /**
     * 全屏模式,4.4-5.0
     */
    public static void setFullScreen(Activity activity) {

        Window window = activity.getWindow();
        ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);

        //首先使 ChildView 不预留空间
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            ViewCompat.setFitsSystemWindows(mChildView, false);
        }

        int statusBarHeight = getStatusBarHeight(activity);
        //需要设置这个 flag 才能设置状态栏
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //避免多次调用该方法时,多次移除了 View
        if (mChildView != null && mChildView.getLayoutParams() != null && mChildView.getLayoutParams().height == statusBarHeight) {
            //移除假的 View.
            mContentView.removeView(mChildView);
            mChildView = mContentView.getChildAt(0);
        }
        if (mChildView != null) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mChildView.getLayoutParams();
            //清除 ChildView 的 marginTop 属性
            if (lp != null && lp.topMargin >= statusBarHeight) {
                lp.topMargin -= statusBarHeight;
                mChildView.setLayoutParams(lp);
            }
        }

    }

    private static int getStatusBarHeight(Activity activity) {

        Rect outRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        return outRect.top;
    }

}
